package org.iot.iotcommon.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class User implements Serializable {
    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "用户名")
    private String name;
    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "电话")
    private String number;
    @ApiModelProperty(value = "电话前缀")
    private String prefix;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "创建时间")
    private Long createTime;
    @ApiModelProperty(value = "变更时间")
    private Long updateTime;
    @ApiModelProperty(value = "组织序号")
    private String orgId;
    @ApiModelProperty(value = "启用")
    private Boolean enabled;

    public User() {
    }

    public User(String id, String name, String account, String password, String number, String prefix, String email, Long createTime, Long updateTime, String orgId, Boolean enabled) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.number = number;
        this.prefix = prefix;
        this.email = email;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.orgId = orgId;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", number='" + number + '\'' +
                ", prefix='" + prefix + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", orgId='" + orgId + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
