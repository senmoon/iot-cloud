package org.iot.iotredis.service.consumer;

import org.iot.iotcommon.config.BaseResult;
import org.iot.iotcommon.model.User;
import org.iot.iotredis.service.consumer.fallback.UserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "user", fallback = UserFallback.class)
public interface UserService {
    @RequestMapping(value = "/user/selectByEntity", method = RequestMethod.POST)
    BaseResult<User> selectByEntity(@RequestBody User user);
}
