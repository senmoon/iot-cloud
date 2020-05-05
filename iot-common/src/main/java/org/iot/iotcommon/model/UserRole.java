package org.iot.iotcommon.model;

import java.io.Serializable;

// 关联表
public class UserRole implements Serializable {
    private String uid; // user.id
    private String roleId; // role.id

    public UserRole() {
    }

    public UserRole(String uid, String roleId) {
        this.uid = uid;
        this.roleId = roleId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "uid='" + uid + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
