package org.iot.iotsso.service.consumer.fallback;

import org.iot.iotcommon.model.User;
import org.iot.iotsso.service.consumer.SsoRedisService;
import org.springframework.stereotype.Service;

@Service
public class SsoRedisFallback implements SsoRedisService {
    @Override
    public String put(String key, String value, long seconds) {
        return null;
    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public boolean hasToken(String token) {
        return false;
    }

    @Override
    public User getUserByToken(String token) {
        return null;
    }
}
