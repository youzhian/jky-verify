package com.jky.verify.modules.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jky.verify.modules.login.bean.LoginUser;
import com.jky.verify.modules.user.bean.UserInfo;
import com.jky.verify.modules.user.mapper.UserMapper;
import com.jky.verify.modules.user.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<UserInfo> queryUsers() {

        List<UserInfo> list = userMapper.selectList(null);

        return list;
    }

    @Override
    public LoginUser getLoginUserByPassword(String userName, String password) {
        UserInfo user = userMapper.selectOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getLoginName,userName).eq(UserInfo::getPassword,password));
        if(user != null){
            LoginUser loginUser = new LoginUser();
            loginUser.setId(user.getId());
            loginUser.setLoginName(user.getLoginName());
            loginUser.setName(user.getName());
            loginUser.setPassword(user.getPassword());
            return loginUser;
        }
        return null;
    }

    @Override
    public UserInfo getUserByLoginName(String userName) {
        if(StringUtils.isNotBlank(userName)){
           return userMapper.selectOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getLoginName,userName));
        }
        return null;
    }
}
