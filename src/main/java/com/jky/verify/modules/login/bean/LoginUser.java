package com.jky.verify.modules.login.bean;

import lombok.Data;

@Data
public class LoginUser {

    private Long id;

    private String loginName;

    private String password;

    private String name;
}
