package org.iot.iotredis.service.impl;

import org.iot.iotcommon.config.BaseResult;
import org.iot.iotcommon.model.User;
import org.iot.iotredis.service.RedisService;
import org.iot.iotredis.service.consumer.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    private final static Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserService userService;

    @Value("${spring.application.name}")
    private String name;

    @Value("${server.port}")
    private String port;
//
//    @Autowired(required = false)
//    public void setRedisTemplate(RedisTemplate redisTemplate) {
//        RedisSerializer stringSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(stringSerializer);
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setHashValueSerializer(stringSerializer);
//        this.redisTemplate = redisTemplate;
//    }

    @Override
    public void put(String key, Object value, long seconds) {
        log.info("RedisService.put -> key: " + key + ", value: " + value + ", seconds: " + seconds);
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        Object result = redisTemplate.opsForValue().get(key);
        log.info("RedisService.get -> name: " + name + ", port: " + port + ", result: " + result);
        return result;
    }

    @Override
    public boolean hasToken(String token) {
        Set<String> keys = stringRedisTemplate.keys("*");
        boolean bool = false;
        assert keys != null;
        for (String key : keys) {
            String o = String.valueOf(redisTemplate.opsForValue().get(key));
            System.out.println(o);
            if (o != null) {
                bool = true;
                break;
            }
        }
        return bool;
    }

    @Override
    public User getUserByToken(String token) {
        Set<String> keys = stringRedisTemplate.keys("*");
        User user = null;
        assert keys != null;
        for (String key : keys) {
            String o = String.valueOf(redisTemplate.opsForValue().get(key));
            log.info("getUserByToken: " + o);
            if (o == null) continue;
            if (!o.equals(token)) continue;
            User u = new User();
            u.setAccount(key.replace("token-", ""));
            BaseResult<User> result = userService.selectByEntity(u); // 没有开启缓存，开启缓存后可以直接从redis拿数据
            user = result.getData();
            log.info(user.toString());
            if (user.getId() != null) {
                break;
            }
        }
        return user;
    }
}
