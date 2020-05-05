package org.iot.iotuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.iot.iotcommon.model.PageCondition;
import org.iot.iotcommon.utils.RsaUtil;
import org.iot.iotuser.mapper.UserMapper;
import org.iot.iotcommon.model.User;
import org.iot.iotuser.service.UserService;
import org.iot.iotuser.service.consumer.UserRedisService;
import org.iot.iotuser.utils.PageWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRedisService userRedisService;

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
    public User selectById(String id) {
        return userMapper.selectById(id);
    }

    @Override
    public User selectByEntity(User user) {
        // 账户是唯一的，所以...
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", user.getAccount());
        List<User> users = userMapper.selectList(wrapper);
        if (users.size() > 0) return users.get(0);
        return null;
    }

    private String getUid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private String getUuid() {
        String uid = getUid();
        User per = userMapper.selectById(uid);
        while (per != null) {
            uid = getUid();
            per = userMapper.selectById(uid);
        }
        return uid;
    }

    private void encodePassword(User user) {
        Map<String, String> keyMap = RsaUtil.getRsaMap(1024, "RSA", user.getAccount());
        String encryptPwd = null;
        try {
            String publicKey = keyMap.get(RsaUtil.PublicKeyPrefix + user.getAccount());
            encryptPwd = RsaUtil.encrypt(user.getPassword(), publicKey);
            // 缓存到redis里，不然，添加之后，就不能根据`加密后的密码`和`account`找到匹配的用户信息，而且，缓存的时间得设置的足够大
            // 相当于把锁给锁上，然后，把钥匙丢了。因此，要保留钥匙
            String privateKey = RsaUtil.PrivateKeyPrefix + user.getAccount();
            System.out.println("privateKey：" + privateKey);
            System.out.println("keyMap.get(privateKey)：" + keyMap.get(privateKey));
            userRedisService.put(privateKey, keyMap.get(privateKey), Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setPassword(encryptPwd);
    }

    @Override
    public User insert(User user) {
        user.setId(getUuid());
        long millis = System.currentTimeMillis();
        user.setCreateTime(millis);
        user.setUpdateTime(millis);
        // 加密
        encodePassword(user);
        int result = userMapper.insert(user);
        if (result == 0) return null;
        return user;
    }

    @Override
    public User updateById(User user) {
        user.setUpdateTime(System.currentTimeMillis());
        int i = userMapper.updateById(user);
        if (i == 0) return null;
        return selectById(user.getId());
    }

    @Override
    public boolean deleteById(String id) {
        return userMapper.deleteById(id) == 1;
    }

    @Override
    public boolean deleteBatchIds(List<String> list) {
        return userMapper.deleteBatchIds(list) > 0;
    }

    @Override
    public IPage<User> selectPage(PageCondition pageCondition) {
        PageWrapper<User> instance = new PageWrapper<User>(pageCondition).getInstance();
        QueryWrapper<User> wrapper = instance.getQueryWrapper();
        if (wrapper == null) return null;
        return userMapper.selectPage(instance.getPage(), wrapper);
    }

    private String getUserFallback() {
        System.out.println("当前系统时间为：" + new Date(System.currentTimeMillis()));
        return null;
    }
}
