package org.iot.iotredis.service.controller;

import org.iot.iotcommon.config.BaseResult;
import org.iot.iotcommon.model.User;
import org.iot.iotredis.service.RedisService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@RestController
public class RedisController {
    @Resource
    private RedisService redisService;

    @RequestMapping(value = "put", method = RequestMethod.POST)
    public void put(String key, String value, long seconds) {
        redisService.put(key, value, seconds);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public String get(String key) {
        Object o = redisService.get(key);
        if (o != null) return String.valueOf(o);
        return null;
    }

    @RequestMapping(value = "hasToken", method = RequestMethod.POST)
    public Boolean hasToken(String key) {
        return redisService.hasToken(key);
    }

    @RequestMapping(value = "getUserByToken", method = RequestMethod.POST)
    public User getUserByToken(@RequestBody String token) {
        User user = redisService.getUserByToken(token);
        if (user != null) return user;
        return null;
    }

    @Cacheable("redis-user")
    @RequestMapping(value = "/redis/get", method = RequestMethod.GET)
    public BaseResult<User> getRedisUser() {
        long time = new Date().getTime();
        User user = new User("1", "redis", "redis", "redis", "13112345678", null, null, time, time, "test", false);
        System.out.println("啊~哈! ----------->>>----------");
        return BaseResult.successWithData(user);
    }

    @RequestMapping("/uid")
    String uid(HttpSession session) {
        // test for session-redis
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}
