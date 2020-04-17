package org.iot.iotuser.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.iot.iotuser.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserService {
    List<User> selectList();
    User selectById(@RequestParam("id") Long id);
    User insert(@RequestBody User user);
    User updateById(@RequestBody User user);
    boolean deleteById(@RequestParam("id") Long id);
    IPage<User> selectPage(@RequestParam int current, @RequestParam int size);
}
