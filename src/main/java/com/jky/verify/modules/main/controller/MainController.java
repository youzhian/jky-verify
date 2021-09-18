package com.jky.verify.modules.main.controller;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jky.verify.common.util.Constant;
import com.jky.verify.common.util.jackyun.Token;
import com.jky.verify.modules.common.controller.BaseController;
import com.jky.verify.modules.enterprise.bean.EnterpriseInfo;
import com.jky.verify.modules.enterprise.service.IEnterpriseInfoService;
import com.jky.verify.modules.login.bean.LoginUser;
import com.jky.verify.modules.user.bean.UserInfo;
import com.jky.verify.modules.user.service.IUserInfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.jky.verify.common.util.jackyun.JwtUtil;

import javax.annotation.Resource;

/**
 * 主页controller
 * @author youzhian
 */
@Controller
public class MainController extends BaseController {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Resource
    private IEnterpriseInfoService enterpriseInfoService;

    @Resource
    private IUserInfoService userInfoService;

    @GetMapping(value = {"/index"})
    public Object index(){
        ModelAndView mv = new ModelAndView();
        LoginUser user = getUser();
        if(user == null){
            mv.setViewName("error");
            mv.addObject("errorMsg","登录失败，请联系管理员");
            return mv;
        }
        mv.setViewName("index");
        return mv;
    }

    /**
     * 吉客云-松之光免密登录
     * @param token
     * @return
     */
    @GetMapping("/szg/login")
    public Object login(String token){

        ModelAndView mv = new ModelAndView();
        boolean isError = false;
        Subject subject = SecurityUtils.getSubject();
        //若已登录，则无需重复登录
        if(subject.isAuthenticated()){
            mv.setViewName("redirect:/index");
            return mv;
        }
        /**
         * token不为空时
         */
        if(StringUtils.isNotBlank(token)){
            //根据松之光查询企业信息
            EnterpriseInfo ei  = enterpriseInfoService.getOne(new QueryWrapper<EnterpriseInfo>()
                    .lambda().eq(EnterpriseInfo::getEnterpriseKey, Constant.SONG_ZHI_GUANG_KEY));
            if(ei != null){
                try {
                    //解析token
                    Token to = JwtUtil.parseClaimsJwt(token, ei.getAppSecret());
                    //appId相同
                    if(to != null && ei.getAppKey().equals(to.getAppId())){
                        String loginName = "";
                        String password = "";
                        if(StringUtils.isNotBlank(to.getAccount())){
                            //查询用户信息
                            UserInfo u = userInfoService.getOne(new QueryWrapper<UserInfo>()
                                    .lambda().eq(UserInfo::getLoginName,to.getAccount()).eq(UserInfo::getEnterpriseId,ei.getId()));
                            //企业ID相同
                            if(u == null){
                                //随便获取一个用户
                                u = userInfoService.getOne(new QueryWrapper<UserInfo>()
                                        .lambda().eq(UserInfo::getEnterpriseId,ei.getId()).last("limit 1"));
                            }
                            if(u != null){
                                loginName = u.getLoginName();
                                password = u.getPassword();
                            }
                        }

                        UsernamePasswordToken upt = new UsernamePasswordToken(loginName,password);
                        //尚未登录，则进行登录操作
                        if(!subject.isAuthenticated()){
                            subject.login(upt);
                        }
                        mv.addObject("sessionId",subject.getSession().getId());
                    }

                }catch (SignatureVerificationException e){
                    logger.error("解析token失败",e);
                    mv.addObject("errorMsg","token不正确,登录失败,请联系管理员");
                    isError = true;
                }catch (TokenExpiredException e){
                    logger.error("解析token失败",e);
                    mv.addObject("errorMsg","token不正确,登录失败,请联系管理员");
                    isError = true;
                }catch (Exception e){
                    logger.error(e.getMessage(),e);
                    isError = true;
                    mv.addObject("errorMsg","登录失败,请联系管理员");
                }

            }
        }else{
            mv.addObject("errorMsg","token为null,登录失败,请联系管理员");
            isError = true;
        }
        if(isError){
            mv.setViewName("error");
            return mv;
        }

        mv.setViewName("redirect:/index");
        return mv;
    }

    /**
     * 核放出区
     * @return
     */
    @GetMapping("/initOut")
    public Object initDealOutArea(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("dealInfo");
        return mv;
    }

}
