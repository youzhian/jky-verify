package com.jky.verify.common.util.jackyun;

import lombok.Data;

/**
 * 第三方平台校验token
 *
 * @author yongfei.zheng
 * @date 2020/7/24 17:51.
 */
@Data
public class Token {
    /**
     * 第三方系统的AppId
     */
    private String appId;
    /**
     * 第三方系统的名称
     */
    private String platformName;
    /**
     * 吉客云会员ID
     */
    private String memberId;
    /**
     * 吉客云会员名
     */
    private String memberName;
    /**
     * 吉客云用户ID
     */
    private String userId;
    /**
     * 吉客云用户名
     */
    private String userName;
    /**
     * 第三方系统用户名, 允许为空
     */
    private String account;
    /**
     * 吉客云用户手机号
     */
    private String mobile;
    /**
     * 吉客云用户真实姓名
     */
    private String realName;


    @Override
    public String toString() {
        return "Token{" +
                "appId='" + appId + '\'' +
                ", platformName='" + platformName + '\'' +
                ", memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", mobile='" + mobile + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}