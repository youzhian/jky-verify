package com.jky.verify.common.shiro;

import com.jky.verify.modules.enterprise.bean.EnterpriseInfo;
import com.jky.verify.modules.enterprise.service.IEnterpriseInfoService;
import com.jky.verify.modules.login.bean.LoginUser;
import com.jky.verify.modules.user.bean.UserInfo;
import com.jky.verify.modules.user.service.IUserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 自定义realm
 * @author youzhian
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private IUserInfoService userService;

    @Resource
    private IEnterpriseInfoService enterpriseInfoService;
    /**
     * 设置权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LoginUser user = (LoginUser) principalCollection.getPrimaryPrincipal();
        //用户信息不为空，则有所有访问权限
        if(user != null){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.addStringPermission("*");
            return simpleAuthorizationInfo;
        }
        return null;
    }

    /**
     * 登录验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if(authenticationToken == null){
            return null;
        }
        UsernamePasswordToken upt = (UsernamePasswordToken) authenticationToken;

        String loginName = upt.getUsername();
        //加密过后的密码
        String password = new String(upt.getPassword());

        UserInfo u = userService.getUserByLoginName(loginName);
        if(u == null){
            throw new UnknownAccountException("账号不存在");
        }
        if(!u.getPassword().equals(password)){
            throw new AuthenticationException("账号密码不正确");
        }

        LoginUser user = new LoginUser();
        user.setId(u.getId());
        user.setLoginName(u.getLoginName());
        user.setName(u.getName());
        user.setPassword(u.getPassword());

        if(u.getEnterpriseId() != null){
            EnterpriseInfo ei = enterpriseInfoService.getById(u.getEnterpriseId());
            if(ei != null){
                user.setEnterpriseId(ei.getId());
                user.setEnterpriseName(ei.getEnterpriseName());
                user.setAppKey(ei.getAppKey());
                user.setAppSecret(ei.getAppSecret());
            }
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),getName());

        return simpleAuthenticationInfo;
    }
}
