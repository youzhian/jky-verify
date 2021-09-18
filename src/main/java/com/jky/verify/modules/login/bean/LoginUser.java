package com.jky.verify.modules.login.bean;

import lombok.Data;

@Data
public class LoginUser {

    private Integer id;

    private String loginName;

    private String password;

    private String name;

    private Integer enterpriseId;

    private String enterpriseName;
    /**
     * 企业应用key
     */
    private String appKey;
    /**
     * 企业秘钥
     */
    private String appSecret ;
}
