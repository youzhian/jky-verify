package com.jky.verify.common.enums;


/**
 * @description: 管理redis键（例如系统名）
 * @author: liuxz
 * @create: 2019-12-19 08:56
 **/
public enum RedisSystemKeyEnum {
    /**
     * 数据中心
     **/
    DATACENTER("dataCenter", "数据中心"),
    ;

    /**
     * key,模块
     */
    private String key;

    /**
     * value，模块对应的汉字
     */
    private String value;

    /**
     * 构造方法
     *
     * @param key
     * @param value
     */
    private RedisSystemKeyEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
