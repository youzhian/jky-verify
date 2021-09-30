package com.jky.verify.modules.user.service;

import com.jky.verify.modules.user.bean.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author youzhian
 * @since 2021-09-18
 */
public interface IUserInfoService extends IService<UserInfo> {

    UserInfo getUserByLoginName(String userName);
}
