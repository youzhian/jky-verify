package com.jky.verify.common.shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class CustomSessionManager extends DefaultWebSessionManager {

    private static String sessionIdKey = "authorization";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";
    /**
     * 构造函数
     */
    public CustomSessionManager() {
        super();
    }

    /**
     * 重写sessionId的获取方式
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //如果请求头中有 Authorization 则其值为sessionId
        String sessionId = WebUtils.toHttp(request).getHeader(sessionIdKey);
        if (!StringUtils.isEmpty(sessionId)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            //不会把sessionid放在URL后
            request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, Boolean.FALSE);
            return sessionId;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }
}
