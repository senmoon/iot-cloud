package org.iot.iotcommon.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Role implements Serializable {
    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "启用")
    private Boolean enabled;
    @ApiModelProperty(value = "角色描述")
    private String description;
    @ApiModelProperty(value = "角色")
    private String role;

    public Role() {
    }

    public Role(String id, Boolean enabled, String description, String role) {
        this.id = id;
        this.enabled = enabled;
        this.description = description;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", enabled=" + enabled +
                ", description='" + description + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
