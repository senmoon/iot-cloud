package org.iot.iotcommon.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Permission implements Serializable {
    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "类型")
    private Integer type;
    @ApiModelProperty(value = "权限名称")
    private String name;
    @ApiModelProperty(value = "父节点id")
    private String parentId;
    @ApiModelProperty("权限")
    private String permission;
    @ApiModelProperty("启用")
    private Boolean enabled;
    @ApiModelProperty("权限地址")
    private String url;

    public Permission() {
    }

    public Permission(String id, Integer type, String name, String parentId, String permission, Boolean enabled, String url) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.parentId = parentId;
        this.permission = permission;
        this.enabled = enabled;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", permission='" + permission + '\'' +
                ", enabled=" + enabled +
                ", url='" + url + '\'' +
                '}';
    }
}
