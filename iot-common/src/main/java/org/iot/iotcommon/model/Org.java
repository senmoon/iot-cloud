package org.iot.iotcommon.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Org implements Serializable {
    @ApiModelProperty(value = "主键Id")
    private String id;
    @ApiModelProperty(value = "启用")
    private Boolean enabled;
    @ApiModelProperty(value = "组织描述")
    private String description;
    @ApiModelProperty(value = "组织名称")
    private String name;
    @ApiModelProperty(value = "上级组织id")
    private String parentId;
    @ApiModelProperty(value = "创建时间")
    private Long createTime;
    @ApiModelProperty(value = "变更时间")
    private Long updateTime;

    public Org() {
    }

    public Org(String id, Boolean enabled, String description, String name, String parentId, Long createTime, Long updateTime) {
        this.id = id;
        this.enabled = enabled;
        this.description = description;
        this.name = name;
        this.parentId = parentId;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Org{" +
                "id='" + id + '\'' +
                ", enabled=" + enabled +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
