package org.iot.iotuser.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.iot.iotcommon.model.Permission;
import org.iot.iotcommon.model.PageCondition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PermissionService {
    List<Permission> selectList();

    Permission selectById(@RequestParam("id") String id);

    Permission insert(@RequestBody Permission permission);

    Permission updateById(@RequestBody Permission permission);

    boolean deleteById(@RequestParam("id") String id);

    boolean deleteBatchIds(List<String> list);

    IPage<Permission> selectPage(@RequestBody PageCondition pageCondition);
}
