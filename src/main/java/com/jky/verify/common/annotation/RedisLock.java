package com.jky.verify.common.annotation;



import com.jky.verify.common.enums.RedisSystemKeyEnum;

import java.lang.annotation.*;

/**
 * @description: Redis锁（拦截器可以处理）
 * @author: liuxz
 * @create: 2019-12-19 10:07
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {
    /**
     * 系统名
     * @return
     */
    RedisSystemKeyEnum system() default RedisSystemKeyEnum.DATACENTER;

    /**分布式锁名称*/
    String key() default "distributed-lock-redisLock";
    /**
     * 过期时间（-1则是默认值）
     * 单位：毫秒
     * @return
     */
    long expire() default -1L;

    /**
     * 其它拓展
     * @return
     */
    String extern() default  "";

    /**
     * 错误信息
     * @return
     */
    String msg() default "";
}
