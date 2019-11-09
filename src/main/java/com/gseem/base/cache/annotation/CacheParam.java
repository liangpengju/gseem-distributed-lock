package com.gseem.base.cache.annotation;

import java.lang.annotation.*;

/**
 * Cache锁的参数
 * @author feiyue
 * @date 2019/11/9
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {

    /**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
}
