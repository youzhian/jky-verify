package com.jky.verify.modules.login.controller;


import com.jky.verify.modules.common.controller.BaseController;
import com.jky.verify.modules.login.bean.LoginUser;
import com.jky.verify.modules.user.service.IUserInfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController extends BaseController {

    @Autowired
    private IUserInfoService userService;

    @GetMapping("unauthc")
    public Object unauth(){
        return error(String.valueOf(HttpStatus.UNAUTHORIZED.value()),"会话已过期，请重新登录");
    }

    @GetMapping("forbidden")
    public Object forbidden() {
        String msg = "权限不足";
        return error(String.valueOf(HttpStatus.FORBIDDEN.value()),msg);
    }
    @GetMapping("login")
    public Object login(LoginUser user){
        if(StringUtils.isNotBlank(user.getLoginName()) && StringUtils.isNotBlank(user.getPassword())){
            try {
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(),user.getPassword());
                if(!subject.isAuthenticated()){
                    //进行登录验证
                    subject.login(token);
                }
                return success("登录成功",subject.getSession().getId());
            }catch (UnknownAccountException e){
                return error("账号不正确");
            }catch (AuthenticationException e){
                return error("账号或密码不正确");
            }catch (AuthorizationException e){
                return error("没有权限访问");
            }
        }
        return error("用户名密码不能为空");
    }

    @GetMapping("logout")
    public Object logout(){
        SecurityUtils.getSubject().logout();
        return success("登出成功");
    }
}
