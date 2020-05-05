package org.iot.iotuser.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.iot.iotcommon.model.Org;
import org.iot.iotcommon.model.PageCondition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface OrgService {
    List<Org> selectList();

    Org selectById(@RequestParam("id") String id);

    Org insert(@RequestBody Org org);

    Org updateById(@RequestBody Org org);

    boolean deleteById(@RequestParam("id") String id);

    boolean deleteBatchIds(List<String> list);

    IPage<Org> selectPage(@RequestBody PageCondition pageCondition);
}
