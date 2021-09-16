package com.jky.verify.common.aspect;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jky.verify.common.annotation.Signature;
import com.jky.verify.common.enums.StatusEnum;
import com.jky.verify.common.exception.VerifyErrorException;
import com.jky.verify.common.properties.AppSignProperties;
import com.jky.verify.common.util.AESUtil;
import com.jky.verify.common.util.Constant;
import com.jky.verify.common.util.DateUtils;
import com.jky.verify.common.util.SignatureUtil;
import com.jky.verify.modules.common.bean.SysAccount;
import com.jky.verify.modules.common.mapper.SysAccountMapper;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 签名注解的切面
 * @author youzhian
 */
@Aspect
@Component
public class SignatureAspect {

    @Resource
    SysAccountMapper sysAccountMapper;
    @Autowired
    AppSignProperties appSignProperties;
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(SignatureAspect.class);
    /**
     * 切入点
     */
    @Pointcut("@annotation(com.jky.verify.common.annotation.Signature)")
    public void signaturePoint(){
    }

    /**
     * 请求之前
     * @param joinPoint
     * @return
     */
    @Before(value="signaturePoint() && @annotation(signature)")
    public Object before(JoinPoint joinPoint, Signature signature){
        //获取request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //是否启用简单模式
        boolean isSimple = signature.isSimple();
        //从请求头中获取验签的必要参数
        String appId = request.getHeader(Constant.APPID_KEY);
        String nonce = request.getHeader(Constant.NONCE_KEY);
        String timestamp = request.getHeader(Constant.TIMESTAMP_KEY);
        String sign = request.getHeader(Constant.SIGN_KEY);
        //开启简单模式
        if(isSimple){
            //验证签名
            boolean verifyResult = verifySignBySimple(appId,sign);
            if(!verifyResult){
                throw new VerifyErrorException("签名验证不通过");
            }
            return null;
        }
        //要进行验签的map
        Map<String,String> params = new HashMap<>();

        params.put(Constant.APPID_KEY,appId);
        params.put(Constant.NONCE_KEY,nonce);
        params.put(Constant.TIMESTAMP_KEY,timestamp);
        params.put(Constant.SIGN_KEY,sign);
        //若开启非默认模式
        if(!signature.isDefault()){
            //验证签名
            boolean verifyResult = verifySign(appId,params);
            if(!verifyResult){
                throw new VerifyErrorException("签名验证不通过");
            }
            return null;
        }
        //1.这里获取到所有的参数值的数组
        Object[] args = joinPoint.getArgs();
        org.aspectj.lang.Signature signature2 = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature2;
        //要验证的字段
        List<String> keys = Arrays.asList(signature.validFieldNames());
        //需要校验的keys的前缀集合
        Set<String> keysOver = getOverflow(keys);
        //要排除验证的字段
        List<String> excludes = Arrays.asList(signature.excludeFieldNames());
        //非展开的对象
        List<String> unSpread = Arrays.asList(signature.unSpread());
        //要排除的前缀
        Set<String> excludesOver = getOverflow(excludes);
        //2.最关键的一步:通过这获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();
        if(parameterNames != null){

            for(int i=0;i<parameterNames.length;i++){
                //参数名
                String name = parameterNames[i];

                //如果是自定要排除的字段
                if(excludes.contains(name)){
                    continue;
                }
                //若指定了参与签名的key，则判断当前参数名是否在keys集合内
                if(!keys.isEmpty() && !(keys.contains(name) || keysOver.contains(name))){
                    continue;
                }
                //参数值
                Object value = args[i];
                if(value != null){
                    String valueStr = null;
                    //如果是String类型
                    if(value instanceof String){
                        if(StringUtils.isNotBlank(String.valueOf(value))){
                            valueStr = String.valueOf(value);
                        }
                    }else if(value instanceof Date){
                        valueStr = DateUtils.format((Date)value,DateUtils.DATE_TIME_PATTERN);
                    }else if(isBaseType(value)){
                        valueStr = String.valueOf(value);
                    }else if(value.getClass().isArray()){
                        //若是数组
                        Object[] array = (Object[]) value;
                        valueStr = new JSONArray(Arrays.asList(array)).toJSONString();
                    }else {
                        JSONObject json = (JSONObject) JSONObject.toJSON(value);
                        if(!json.isEmpty()){
                            JSONObject json2 = new JSONObject();
                            for(String k : json.keySet()){

                                String qname = name + "." + k;
                                //若在排除的列表内
                                if(excludesOver.contains(name) && excludes.contains(qname)){
                                    continue;
                                }
                                if(!keys.isEmpty() && !keys.contains(qname)){
                                    continue;
                                }
                                if(json.get(k) == null || StringUtils.isBlank(json.getString(k))){
                                    continue;
                                }
                                if(json.get(k) instanceof Date){
                                    json.put(k, DateUtils.format(json.getDate(k),DateUtils.DATE_TIME_PATTERN));
                                }
                                //名字在非展开项时
                                if(unSpread.contains(name)){
                                    json2.put(k,json.get(k));
                                }else{
                                    if(json.get(k).getClass().isArray()){
                                        json.put(k,json.getJSONArray(k).toJSONString());
                                    }
                                    params.put(k,json.getString(k));
                                }
                            }
                            if(!json2.isEmpty()){
                                valueStr = json2.toJSONString();
                            }
                        }
                    }
                    if(StringUtils.isNotBlank(valueStr)){
                        params.put(name,valueStr);
                    }

                }
            }
        }
        //验证签名
        boolean veifyResult = verifySign(appId,params);
        if(!veifyResult){
            throw new VerifyErrorException("签名验证不通过");
        }

        return null;
    }

    /**
     * 判断是否是基本数据类型
     * @param value
     * @return
     */
    private boolean isBaseType(Object value){
        if(value != null){
            if(value instanceof Integer
                    || value instanceof Long
                    || value instanceof Double
                    || value instanceof Float
                    || value instanceof BigDecimal
                    || value instanceof Character
                    || value instanceof Boolean
                    || value instanceof Short
                    || value instanceof Byte){

                return true;
            }
        }
        return false;
    }

    /**
     * 获取前缀的key
     * @param keys
     * @return
     */
    public Set<String> getOverflow(List<String> keys){
        Set<String> over = new HashSet<>();
        if(keys != null && !keys.isEmpty()){
            for(String key:keys){
                if(StringUtils.isBlank(key)){
                    continue;
                }
                //如果存在.则认为是有对象的属性
                if(key.indexOf(".") >-1){
                    String prefix = key.split(".")[0];
                    over.add(prefix);
                }
            }
        }
        return over;
    }
    /**
     * 验证签名信息，通过返回true
     *
     * @param appId  appId
     * @param params 参与了签名加密的参数和sign，params中应该有appId、nonce、timestamp、sign
     * @return 验证通过返回true
     */
    public boolean verifySign(String appId, Map<String, String> params) {
        if(StringUtils.isNotBlank(appId) && params != null && !params.isEmpty()){
            if(!params.containsKey(Constant.APPID_KEY)
                    || !params.containsKey(Constant.NONCE_KEY)
                    ||!params.containsKey(Constant.TIMESTAMP_KEY) || !params.containsKey(Constant.SIGN_KEY)){
                throw new VerifyErrorException("参数不正确，params中必须包含appId、nonce、timestamp、sign");
            }
            //根据appId查询相应的账号信息
            SysAccount sysAccount = sysAccountMapper.selectOne(new QueryWrapper<SysAccount>().lambda()
                    .eq(SysAccount::getAppId,appId));

            if(sysAccount == null){
                throw new VerifyErrorException("账号不存在，appId["+appId+"]");
            }else if(!StatusEnum.Available.getKey().equals(sysAccount.getStatus())){
                throw new VerifyErrorException("当前账号不可用，appId["+appId+"]");
            }
            //验证签名
            return SignatureUtil.verify(params, AESUtil.decrypt(sysAccount.getAppId(),sysAccount.getAppSecret()));
        }
        return false;
    }

    /**
     * 简单模式验证签名，通过返回true
     *
     * @param appId appId
     * @param sign  需验证的签名
     * @return 验证通过返回true
     */
    public boolean verifySignBySimple(String appId, String sign) {
        if(StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(sign)){
            //直接从配置文件中读取，减少数据库查询
            if(appSignProperties.getAppSigns() != null){
                if(sign.equals(appSignProperties.getAppSigns().get(appId))){
                    return true;
                }else if(appSignProperties.getAppSigns().containsKey(appId)){
                    return false;
                }
            }
            //根据appId查询相应的账号信息
            SysAccount sysAccount = sysAccountMapper.selectOne(new QueryWrapper<SysAccount>().lambda()
                    .eq(SysAccount::getAppId,appId));

            if(sysAccount == null){
                throw new VerifyErrorException("账号不存在，appId["+appId+"]");
            }else if(!StatusEnum.Available.getKey().equals(sysAccount.getStatus())){
                throw new VerifyErrorException("当前账号不可用，appId["+appId+"]");
            }
            Map<String,String> params = new HashMap<>();
            params.put("appId",appId);
            params.put("sign",sign);
            //验证签名
            return SignatureUtil.verify(params, AESUtil.decrypt(sysAccount.getAppId(),sysAccount.getAppSecret()));
        }
        return false;
    }
}
