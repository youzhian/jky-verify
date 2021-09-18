package com.jky.verify.common.generator;

/**
 * 生成器相关配置
 * @author youzhian
 */
public class GenneratorConfig {

    /**
     * 代码创建者名称
     */
    public static String authorName = "youzhian";
    /**
     * 数据库驱动类名
     */
    public static String driverName = "com.mysql.cj.jdbc.Driver";
    /**
     * 数据库连接地址
     */
    public static String dataUrl = "jdbc:mysql://localhost:3306/verify?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&serverTimezone=Asia/Shanghai";
    /**
     * 数据库用户名
     */
    public static String userName = "wdc";
    /**
     * 数据库密码
     */
    public static String password = "w0fng_client";
    /**
     * 生成代码目录的父目录
     */
    public static String moudelParent = "com.jky.verify.modules";
    /**
     * 生成mybatis相关xml的根目录
     */
    public static String mapperXmlDir = "/src/main/resources/mapper";
    /**
     * 实体类的包名
     */
    public static String entityPath = "bean";
    /**
     * controller的包名
     */
    public static String controllerPath = "controller";
    /**
     * 是否在生成时重新覆盖文件
     */
    public static boolean fileOverride = true;
    /**
     * 是否覆盖mapper和mapper.xml
     */
    public static boolean mapperFileOverride = false;
    /**
     * 是否在mapper中生成baseColumnList
     */
    public static boolean baseColumnList = true;
    /**
     * 是否在mapper中生成baseResultMap
     */
    public static boolean baseResultMap = true;

}
