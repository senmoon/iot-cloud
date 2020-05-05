package org.iot.iotcommon.model;

//import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class RolePermission implements Serializable {
    //@ApiModelProperty("角色id")
    private String roleId;
    //@ApiModelProperty("权限id")
    private String permissionId;

    public RolePermission() {
    }

    public RolePermission(String roleId, String permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "RolePermission{" +
                "roleId='" + roleId + '\'' +
                ", permissionId='" + permissionId + '\'' +
                '}';
    }
}
