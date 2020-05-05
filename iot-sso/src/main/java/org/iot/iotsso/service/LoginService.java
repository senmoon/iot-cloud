package org.iot.iotsso.service;

import org.iot.iotcommon.model.User;
import org.iot.iotsso.model.UserLogin;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    String getPublicKey(String account) throws Exception;

    UserLogin login(User user, HttpServletRequest request);

    User jump(String token);

    boolean logout(HttpServletRequest request);
}
