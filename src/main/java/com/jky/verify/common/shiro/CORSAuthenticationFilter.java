package com.jky.verify.common.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 自定义shiro拦截器，默认不拦截options的请求
 * @author youzhian
 *
 */
public class CORSAuthenticationFilter extends UserFilter {
	
	public CORSAuthenticationFilter() {
		super();
	}
	
	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		//Always return true if the request's method is OPTIONS
        if (request instanceof HttpServletRequest) {
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
        }
        boolean result = super.isAccessAllowed(request, response, mappedValue);
        //父类验证通过,则判断用户是否有权限访问url
        if(result && !isLoginRequest(request, response)) {
        	Subject subject = getSubject(request, response);
    		String url = getPathWithinApplication(request);
    		result = subject.isPermitted(url);
        }
		
		return result;
	}
	
	@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		
		Subject subject = SecurityUtils.getSubject();
		//判断是否已登录
		if(subject.isAuthenticated()) {
			WebUtils.redirectToSavedRequest(request, response, "/index");
			
	        return false;
		}else {
			return super.onAccessDenied(request, response);
		}
    }
}
