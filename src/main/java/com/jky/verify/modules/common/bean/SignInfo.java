package com.jky.verify.modules.common.bean;

import lombok.Data;

/**
 * 签名公共类
 */
@Data
public class SignInfo {

    private String appId;

    private String nonce;

    private String timestamp;

    private String sign;
}
