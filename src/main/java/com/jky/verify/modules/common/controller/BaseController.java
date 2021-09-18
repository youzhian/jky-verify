package com.jky.verify.modules.common.controller;


import com.jky.verify.common.util.ResponseModel;
import com.jky.verify.modules.login.bean.LoginUser;
import org.apache.shiro.SecurityUtils;

/**
 * controller基类
 * @author youzhian
 */
public class BaseController {

    protected ResponseModel success(){
        return success(null);
    }
    protected ResponseModel success(String msg){
        return success(msg,null);
    }
    protected ResponseModel success(Object data){
        return success(null,data);
    }
    protected ResponseModel success(String msg,Object data){
        return ResponseModel.success(msg,data);
    }

    protected ResponseModel error(){
        return error(null);
    }

    protected ResponseModel error(String msg){
        return error(msg,null);
    }
    protected ResponseModel error(String code,String msg){
        return error(code,msg,null);
    }
    protected ResponseModel error(String msg,Object data){
        return error(null,msg,data);
    }

    protected ResponseModel error(String code,String msg,Object data){
        return ResponseModel.error(code,msg,data);
    }

    /**
     * 获取用户信息对象
     * @return
     */
    protected LoginUser getUser() {
        return (LoginUser) SecurityUtils.getSubject().getPrincipal();
    }
}
