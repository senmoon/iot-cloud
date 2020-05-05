package org.iot.iotredis.config;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public KeyGenerator keyGenerator() {
        return (Object target, Method method, Object... params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        return redisTemplate;
    }

    @Bean
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {

            @Override
            public void handleCacheGetError(@Nullable RuntimeException e, @Nullable Cache cache, @Nullable Object o) {
                System.out.println("handleCacheGetError");
            }

            @Override
            public void handleCachePutError(@Nullable RuntimeException e, @Nullable Cache cache, @Nullable Object o, @Nullable Object o1) {
                System.out.println("handleCachePutError");
            }

            @Override
            public void handleCacheEvictError(@Nullable RuntimeException e, @Nullable Cache cache, @Nullable Object o) {
                System.out.println("handleCacheEvictError");
            }

            @Override
            public void handleCacheClearError(@Nullable RuntimeException e, @Nullable Cache cache) {
                System.out.println("handleCacheClearError");
            }
        };
    }
}
