package org.iot.iotuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.iot.iotuser.mapper.UserMapper;
import org.iot.iotuser.model.User;
import org.iot.iotuser.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @HystrixCommand(fallbackMethod = "getUserFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), //失败率达到多少后跳闸
    })
    @Override
    public List<User> selectList() {
        return userMapper.selectList(null);
    }

    @Override
    public User selectById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User insert(User user) {
        int result = userMapper.insert(user);
        if (result == 0) return null;
        return userMapper.selectById(user.getId());
    }

    @Override
    public User updateById(User user) {
        int i = userMapper.updateById(user);
        if (i == 0) return null;
        return userMapper.selectById(user.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        int i = userMapper.deleteById(id);
        if (i == 0) return false;
        return i == 1;
    }

    @Override
    public IPage<User> selectPage(int current, int size) {
        Page<User> page = new Page<>(current, size);
        return userMapper.selectPage(page, new QueryWrapper<>());
    }

    private String getUserFallback(@RequestParam("id") Integer id) {
        return "当前系统时间为：" + new Date(System.currentTimeMillis()) + "，服务不到位 o(╥﹏╥)o  id：" + id;
    }
}
