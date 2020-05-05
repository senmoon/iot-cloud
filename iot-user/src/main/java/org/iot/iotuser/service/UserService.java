package org.iot.iotuser.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.iot.iotcommon.model.PageCondition;
import org.iot.iotcommon.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserService {
    List<User> selectList();

    User selectById(@RequestParam("id") String id);

    User selectByEntity(@RequestBody User user);

    User insert(@RequestBody User user);

    User updateById(@RequestBody User user);

    boolean deleteById(@RequestParam("id") String id);

    boolean deleteBatchIds(List<String> list);

    IPage<User> selectPage(@RequestBody PageCondition pageCondition);
}
