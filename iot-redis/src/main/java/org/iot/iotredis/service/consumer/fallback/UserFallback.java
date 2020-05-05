package org.iot.iotredis.service.consumer.fallback;

import org.iot.iotcommon.config.BaseResult;
import org.iot.iotcommon.model.User;
import org.iot.iotredis.service.consumer.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserFallback implements UserService {
    @Override
    public BaseResult<User> selectByEntity(User user) {
        return null;
    }
}
