package com.jky.verify.modules.user.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class UserInfo {

    private Long id;

    private String LoginName;

    private String name;

    private String password;
}
