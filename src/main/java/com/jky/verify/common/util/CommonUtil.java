package com.jky.verify.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 常用工具类
 *
 * @author zhengYuyang
 */

public class CommonUtil {

    public static final String MOBILE_REGULAR = "^1[0-9]{10}$";

    public static final String mobile_reg = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0,1,6,7,]))|(18[0-2,5-9])|(19[0-9]))\\d{8}$";

    public static boolean checkMobile(String mobile) {
        if (mobile == null) {
            return false;
        }
        if (!mobile.matches(MOBILE_REGULAR)) {
            return false;
        }
        return true;
    }

    /**
     * 检查是否是手机号
     * @param mobile
     * @return
     */
    public static boolean checkMobile2(String mobile){
        if(StringUtils.isNotBlank(mobile)){
            return mobile.matches(mobile_reg);
        }

        return false;
    }

    /**
     * 判断电子邮箱格式
     */
    public static boolean emailFormat(String email) {
        boolean tag = true;
        if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            tag = false;
        }
        return tag;
    }

    /**
     * 判断是否是中文和通用字符串
     *
     * @param str 要校验的字符串
     * @return 符合条件返回true
     */
    public static boolean validStr(String str) {
        String regex = "^[?.\\s\u4e00-\u9fa5]+$";
        if (StringUtils.isNotBlank(str)) {
            if (!str.matches(regex)) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        String UNDERLINE = "_";
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将带有下划线的字符串转为驼峰命名
     *
     * @param key 要进行转换的key
     * @return
     */
    public static String underLineToCamel(String key) {
        if (StringUtils.isNotBlank(key) && key.length() > 2) {
            StringBuilder sb = new StringBuilder();
            //切割成多个字符串
            String[] words = key.split("");
            sb.append(words[0]);
            //从第二个字符开始
            for (int i = 1; i < words.length; i++) {
                if ("_".equals(words[i]) && i != words.length - 1) {
                    words[i + 1] = words[i + 1].toUpperCase();
                } else {
                    sb.append(words[i]);
                }
            }
            return sb.toString();
        }
        return key;
    }

    /**
     * JSONObject转换为vo类
     *
     * @param jsonObj json数据
     * @return
     */
    public static <T> T jsonObjToVo(JSONObject jsonObj, Class<T> clazz) {
        //转为vo类
        if (jsonObj != null) {
            JSONObject data = new JSONObject();

            //遍历json对象，将带下划线的属性转为驼峰命名法
            for (JSONObject.Entry<String, Object> e : jsonObj.entrySet()) {
                String key = e.getKey();
                if (key.contains("_")) {
                    data.put(underLineToCamel(key), e.getValue());
                } else {
                    data.put(key, e.getValue());
                }
            }
            return data.toJavaObject(clazz);
        }
        return null;
    }

    /**
     * 对手机号加星号处理
     *
     * @param phone 手机号
     * @return
     */
    public static String asteriskPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return phone;
        }
        if (phone.length() < 7) {
            return phone;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }


    /**
     * 将map转bean(基于fastjson)
     *
     * @param map  map
     * @param type 类型
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, TypeReference<T> type) {
        String jsonStr = JSON.toJSONString(map);
        return JSON.parseObject(jsonStr, type);
    }

    /**
     * 将bean转map(基于fastjson)
     *
     * @param object bean
     * @return
     */
    public static Map<String, Object> beanToMap(Object object) {
        String jsonStr = JSON.toJSONString(object);
        return JSON.parseObject(jsonStr);
    }

    /**
     * 匹配是否包含字母
     *
     * @param str
     * @return
     */
    public static boolean hasLetter(String str) {
        String regex = ".*[a-zA-Z]+.*";
        if (StringUtils.isNotBlank(str)) {
            if (!str.matches(regex)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String key = "_1233__a232_";
        System.out.println(underLineToCamel(key));
        System.out.println(hasLetter("1a2"));
        String s1 = "18789517705";
        String s2 = "28120005777";
        System.out.println(s1+"是否是手机号："+checkMobile2(s1));
        System.out.println(s2+"是否是手机号："+checkMobile2(s2));
    }
}
