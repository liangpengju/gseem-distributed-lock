package com.gseem.base.cache.annotation;

/**
 * 限流类型
 * @author feiyue
 * @date 2019/11/9
 */
public enum LimitType {
    /**
     * 自定义key
     */
    CUSTOMER,
    /**
     * 根据请求者IP
     */
    IP;
}