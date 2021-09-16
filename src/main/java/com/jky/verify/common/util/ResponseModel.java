package com.jky.verify.common.util;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * 返回类的模板类
 * @author youzhian
 */
@Data
public class ResponseModel {

    private static String success_code = "200";
    private static String success_msg = "请求成功";

    private static String error_code = "500";

    private static String error_msg = "请求失败";

    private String code;

    private String msg;

    private Object data;

    /**
     * 成功时的返回方法
     * @param msg 提示消息，空时为默认值
     * @param data 要返回的数据
     * @return
     */
    public static ResponseModel success(String msg,Object data){
        ResponseModel model = new ResponseModel();
        model.setCode(success_code);
        model.setMsg(msg);
        if(StringUtils.isBlank(msg)){
            model.setMsg(success_msg);
        }
        if(data != null){
            model.setData(data);
        }
        return model;
    }

    /**
     * 错误消息
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ResponseModel error(String code,String msg,Object data){
        ResponseModel model = new ResponseModel();
        model.setCode(error_code);
        model.setMsg(error_msg);
        if(StringUtils.isNotBlank(code)){
            model.setCode(code);
        }
        if(StringUtils.isNotBlank(msg)){
            model.setMsg(msg);
        }
        if(data != null){
            model.setData(data);
        }
        return model;
    }
}
