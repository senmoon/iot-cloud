package org.iot.iotsso.model;

import org.iot.iotcommon.model.User;

public class UserLogin extends User {
    private String token;

    public UserLogin(String token) {
        this.token = token;
    }

    public UserLogin(String id, String name, String account, String password, String number, String prefix, String email, Long createTime, Long updateTime, String orgId, Boolean enabled, String token) {
        super(id, name, account, password, number, prefix, email, createTime, updateTime, orgId, enabled);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "token='" + token + '\'' +
                '}';
    }
}
