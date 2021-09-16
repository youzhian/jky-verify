package com.jky.verify.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jky.verify.modules.login.bean.LoginUser;
import com.jky.verify.modules.user.bean.UserInfo;


import java.util.List;

public interface IUserService extends IService<UserInfo> {

    List<UserInfo> queryUsers();

    LoginUser getLoginUserByPassword(String userName, String password);

    UserInfo getUserByLoginName(String userName);
}
