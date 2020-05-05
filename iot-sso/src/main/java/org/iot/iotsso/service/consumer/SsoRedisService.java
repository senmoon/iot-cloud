package org.iot.iotsso.service.consumer;

import org.iot.iotcommon.model.User;
import org.iot.iotsso.service.consumer.fallback.SsoRedisFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "redis", fallback = SsoRedisFallback.class)
public interface SsoRedisService {
    @RequestMapping(value = "put", method = RequestMethod.POST)
    String put(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("seconds") long seconds);

    @RequestMapping(value = "get", method = RequestMethod.GET)
    String get(@RequestParam("key") String key);

    @RequestMapping(value = "hasToken", method = RequestMethod.POST)
    boolean hasToken(@RequestBody String token);

    @RequestMapping(value = "getUserByToken", method = RequestMethod.POST)
    User getUserByToken(@RequestBody String token);
}
