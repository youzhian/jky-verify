package com.jky.verify.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * aes加解密配置
 *
 * @author liuxz
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "wofang.aes")
public class AesProperties {
    /**
     * AES 密钥
     */
    private String key;

    /**
     * AES算法
     */
    private String algorithm;

}
