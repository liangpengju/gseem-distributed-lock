package com.gseem.base.cache;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Key生成策略
 * key生成器
 * @author feiyue
 * @date 2019/11/9
 */
public interface CacheKeyGenerator {

    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint pjp);
}