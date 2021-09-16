package com.jky.verify.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据中心rpc相关配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "value.datacenter.rpc")
public class RpcProperties {
    /**
     * 无需登录的请求地址配置
     */
    private List<String> anons;
}
