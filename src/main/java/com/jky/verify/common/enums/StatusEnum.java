package com.jky.verify.common.enums;

/**
 * 状态枚举类
 * @author youzhian
 */
public enum StatusEnum {

    Available("1","可用"),
    NotAvailable("0","不可用");

    String key;
    String value;

    StatusEnum(String key, String value){
        this.key = key;
        this.value = value;
    }
    /**
     * 获取 key
     *
     * @return key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * 设置 key
     *
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取 value
     *
     * @return value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * 设置 value
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
