package com.gseem.controller;

import com.gseem.base.cache.annotation.CacheLimit;
import com.gseem.base.cache.annotation.LimitType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流
 * @author feiyue
 * @date 2019/11/9
 */
@RestController
@RequestMapping("/cacheLimit")
public class CacheLimitController {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    /**
     * 自定义key的限流
     * @return
     */
    @CacheLimit(key = "testCustom", period = 10, count = 2, name="resource", prefix = "limit_")
    @GetMapping("/testCustom")
    public int testCustom() {
        // 意味着10S内最多可以访问2次
        return ATOMIC_INTEGER.incrementAndGet();
    }


    /**
     * ip限流
     * @return
     */
    @CacheLimit(key = "testIp", period = 10, count = 2,limitType = LimitType.IP,
            name="resourceIp", prefix = "limit_")
    @GetMapping("/testIp")
    public int testIp() {
        // 意味着10S内最多可以访问2次
        return ATOMIC_INTEGER.incrementAndGet();
    }


}
