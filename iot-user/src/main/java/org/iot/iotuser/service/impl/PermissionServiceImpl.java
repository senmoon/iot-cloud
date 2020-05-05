package org.iot.iotuser.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.iot.iotcommon.model.Permission;
import org.iot.iotcommon.model.PageCondition;
import org.iot.iotuser.mapper.PermissionMapper;
import org.iot.iotuser.service.PermissionService;
import org.iot.iotuser.utils.PageWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionMapper mapper;

    @HystrixCommand(fallbackMethod = "getOrgFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), //失败率达到多少后跳闸
    })
    @Override
    public List<Permission> selectList() {
        return mapper.selectList(null);
    }

    @Override
    public Permission selectById(String id) {
        return mapper.selectById(id);
    }

    private String getUid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private String getUuid() {
        String uid = "";
        Permission per = mapper.selectById(getUid());
        while (per != null) {
            uid = getUid();
            per = mapper.selectById(uid);
        }
        return uid;
    }

    @Override
    public Permission insert(Permission permission) {
        permission.setId(getUuid());
        int result = mapper.insert(permission);
        if (result == 0) return null;
        return mapper.selectById(permission.getId());
    }

    @Override
    public Permission updateById(Permission permission) {
        int i = mapper.updateById(permission);
        if (i == 0) return null;
        return mapper.selectById(permission.getId());
    }

    @Override
    public boolean deleteById(String id) {
        return mapper.deleteById(id) == 1;
    }

    @Override
    public boolean deleteBatchIds(List<String> list) {
        return mapper.deleteBatchIds(list) > 0;
    }

    @Override
    public IPage<Permission> selectPage(PageCondition pageCondition) {
        PageWrapper<Permission> instance = new PageWrapper<Permission>(pageCondition).getInstance();
        return mapper.selectPage(instance.getPage(), instance.getQueryWrapper());
    }

    private String getOrgFallback() {
        System.out.println("当前系统时间为：" + new Date(System.currentTimeMillis()));
        return null;
    }
}
