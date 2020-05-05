package org.iot.iotredis.service;

import org.iot.iotcommon.model.User;

public interface RedisService {
    void put(String key, Object value, long seconds);

    Object get(String key);

    boolean hasToken(String token);

    User getUserByToken(String token);
}
