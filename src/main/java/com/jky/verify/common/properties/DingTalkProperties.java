package com.jky.verify.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 钉钉配置类
 * @author youzhian
 */
@Component
@Data
@ConfigurationProperties(prefix = "dingtalk")
public class DingTalkProperties {
    /**
     * 网关
     */
    private String gateway;
    /**
     * appKey
     */
    private String appKey;
    /**
     *  appSecret
     */
    private String appSecret;
}
