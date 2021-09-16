package com.jky.verify;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类
 * @author youzhian
 */
@MapperScan("com.jky.verify.modules.*.mapper")
@SpringBootApplication
public class VerifyApplication {
    /**
     * 入口
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(VerifyApplication.class,args);
    }
}
