package com.gseem.base.config;

import com.gseem.base.utils.RedisUtil;
import com.gseem.base.utils.FastJson2JsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import com.gseem.base.utils.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * redis配置
 * @author feiyue
 * @date 2019/11/9
 */
@Configuration
@PropertySource("classpath:redis.properties")
@Slf4j
public class RedisConfig {

    @Value("${redis.hostName}")
    private String hostName;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.maxIdle}")
    private Integer maxIdle;

    @Value("${redis.timeout}")
    private Integer timeout;

    @Value("${redis.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Value("${redis.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${redis.numTestsPerEvictionRun}")
    private Integer numTestsPerEvictionRun;

    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.testWhileIdle}")
    private boolean testWhileIdle;

    /**
     * Jedis配置
     * @author feiyue
     * @Date 2019/11/9 19:28
     * @Param
     * @return
     **/
    @Bean
    public JedisConnectionFactory JedisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration ();
        redisStandaloneConfiguration.setHostName(hostName);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofMillis(timeout));
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
        return factory;
    }

   /**
    * 实例化 RedisTemplate 对象
    * @author feiyue
    * @date 2019/11/9 19:29
    * @param
    * @return
    **/
    @Bean
    public RedisTemplate functionDomainRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("RedisTemplate实例化成功！");
        RedisTemplate redisTemplate = new RedisTemplate();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 引入自定义fastjson序列化
     * @author feiyue
     * @date 2019/11/9 19:30
     * @param
     * @return
     **/
    @Bean
    public RedisSerializer fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<Object>(Object.class);
    }

   /**
    * 设置数据存入 redis 的序列化方式,并开启事务
    * @author feiyue
    * @date 2019/11/9 19:30
    * @param
    * @return
    **/
    private void initDomainRedisTemplate(RedisTemplate redisTemplate, RedisConnectionFactory factory) {
        //如果不配置Serializer，那么存储的时候缺省使用String，
        // 如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer());
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(factory);
    }

    /**
     * 注入封装RedisTemplate
     * @author feiyue
     * @date 2019/11/9 19:31
     * @param
     * @return
     **/
    @Bean(name = "redisUtil")
    public RedisUtil redisUtil(RedisTemplate redisTemplate) {
        log.info("RedisUtil注入redisTemplate成功！");
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }

}
