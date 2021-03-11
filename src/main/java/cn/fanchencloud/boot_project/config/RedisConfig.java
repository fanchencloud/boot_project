package cn.fanchencloud.boot_project.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Created by handsome programmer.
 *
 * @author chen
 * @User: chen
 * @Date: 2021/3/11
 * @Time: 8:58
 * @Description:
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    // Key 过期时间: 1day = 86400s
    private final Duration EXPIRE_DATE = Duration.ofDays(1);

    // Spring Cache 配置类
    @Bean(name="cacheManager")
    public CacheManager cacheManager(RedisConnectionFactory factory){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl( EXPIRE_DATE )         // 设置缓存的过期时间
                .computePrefixWith(cacheName -> cacheName + ":")    // 无该行代码，则Spring Cache 默认使用::用作命名空间的分隔符
                .serializeKeysWith( RedisSerializationContext.SerializationPair.fromSerializer( getKeySerializer() ) )  // 设置Key序列化器
                .serializeValuesWith( RedisSerializationContext.SerializationPair.fromSerializer( getValueSerializer() ) ) // 设置Value序列化器
                .disableCachingNullValues();

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults( redisCacheConfiguration )
                .build();
        log.info(" 自定义Spring Cache Manager配置完成 ... ");
        return redisCacheManager;
    }

    // Redis 配置类
    // 自定义的RedisTemplate的Bean名称必须为 redisTemplate。当方法名不为 redisTemplate时，可通过name显示指定bean名称，@Bean(name="redisTemplate")
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 设置String Key序列化器
        template.setKeySerializer( getKeySerializer() );
        template.setValueSerializer( getValueSerializer() );
        // 设置Hash Key序列化器
        template.setHashKeySerializer( getKeySerializer() );
        template.setHashValueSerializer( getValueSerializer() );
        log.info("自定义RedisTemplate配置完成 ... ");
        return template;
    }

    // key 采用String序列化器
    private RedisSerializer<String> getKeySerializer() {
        return new StringRedisSerializer();
    }

    // value 采用Json序列化器
    private RedisSerializer<Object> getValueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}
