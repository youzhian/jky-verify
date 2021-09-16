package com.jky.verify.common.annotation;

import java.lang.annotation.*;

/**
 * 验证签名的注解
 * @author youzhian
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Signature {
    /**
     * 是否启用简单模式，即只需传入appId、sign即可，
     * 并根据appId以及对应的秘钥对appId进行加密验签
     * @return
     */
    boolean isSimple() default false;

    /**
     * 默认模式，优先级低于isSimple。默认值为true，默认使用
     * @return
     */
    boolean isDefault() default true;
    /**
     * 要进行验证的字段名
     * @return
     */
    String [] validFieldNames() default {};

    /**
     * 要进行排除的字段名
     * @return
     */
    String [] excludeFieldNames() default {};

    /**
     * 不展开的对象参数（仅对对象参数生效）
     * @return
     */
    String [] unSpread() default {};
}
