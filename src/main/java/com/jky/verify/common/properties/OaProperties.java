package com.jky.verify.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * oa相关配置类
 * @author youzhian
 */
@Component
@Data
@ConfigurationProperties(prefix = "oa")
public class OaProperties {
    /**
     * 获取城市与战区的url
     */
    private String zoneAndCityUrl;
    /**
     * 根据城市或战区获取人员信息的url
     */
    private String staffZoneAndCityUrl;
}
