package com.gseem.base.cache.aop;

import com.gseem.base.cache.CacheKeyGenerator;
import com.gseem.base.cache.annotation.CacheLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 分布式锁拦截器
 * @author feiyue
 * @date 2019/11/9
 */
@Aspect
@Configuration
public class LockMethodInterceptor {

    @Autowired
    public LockMethodInterceptor(StringRedisTemplate lockRedisTemplate, CacheKeyGenerator cacheKeyGenerator) {
        this.lockRedisTemplate = lockRedisTemplate;
        this.cacheKeyGenerator = cacheKeyGenerator;
    }

    private final StringRedisTemplate lockRedisTemplate;
    private final CacheKeyGenerator cacheKeyGenerator;


    @Around("execution(public * *(..)) && @annotation(com.gseem.base.cache.annotation.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if (StringUtils.isEmpty(lock.prefix())) {
            throw new RuntimeException("lock key can't be null...");
        }
        final String lockKey = cacheKeyGenerator.getLockKey(pjp);
        try {
            //key不存在才能设置成功
            final Boolean success = lockRedisTemplate.opsForValue().setIfAbsent(lockKey, "");
            if (success) {
                lockRedisTemplate.expire(lockKey, lock.expire(), lock.timeUnit());
            } else {
                //应该抛出一个自定义的 CacheLockException 异常;
                throw new RuntimeException("请勿重复请求");
            }
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException("系统异常");
            }
        } finally {
            //演示的话需要注释该代码;实际应该放开
            lockRedisTemplate.delete(lockKey);
        }
    }
}