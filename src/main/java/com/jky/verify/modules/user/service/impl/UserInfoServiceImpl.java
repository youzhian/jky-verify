package com.jky.verify.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jky.verify.modules.user.bean.UserInfo;
import com.jky.verify.modules.user.mapper.UserInfoMapper;
import com.jky.verify.modules.user.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author youzhian
 * @since 2021-09-18
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Override
    public UserInfo getUserByLoginName(String userName) {
        if(StringUtils.isNotBlank(userName)){
            UserInfo userInfo = getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getLoginName,userName));
            return userInfo;
        }
        return null;
    }
}
