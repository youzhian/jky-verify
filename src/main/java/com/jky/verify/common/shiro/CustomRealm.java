package com.jky.verify.common.shiro;

import com.jky.verify.modules.user.bean.UserInfo;
import com.jky.verify.modules.user.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义realm
 * @author youzhian
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;
    /**
     * 设置权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserInfo user = (UserInfo) principalCollection.getPrimaryPrincipal();
        if(user != null){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.addRole("admin");
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
        if(authenticationToken.getPrincipal() == null){
            return null;
        }

        String userName = (String)authenticationToken.getPrincipal();
        UserInfo user = userService.getUserByLoginName(userName);
        if(user == null){
            throw new UnknownAccountException("账户不存在");
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),getName());

        return simpleAuthenticationInfo;
    }
}
