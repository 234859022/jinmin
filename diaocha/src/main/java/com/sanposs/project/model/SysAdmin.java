package com.sanposs.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_admin")
public class SysAdmin {
    @Id
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 手机号
     */
    @Column(name = "mobile_phone")
    private String mobilePhone;

    /**
     * 密码
     */
    private String password;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人ID
     */
    @Column(name = "create_admin_id")
    private String createAdminId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 是否删除1是0否
     */
    @Column(name = "is_delete")
    private Boolean isDelete;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取姓名
     *
     * @return real_name - 姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置姓名
     *
     * @param realName 姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取手机号
     *
     * @return mobile_phone - 手机号
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * 设置手机号
     *
     * @param mobilePhone 手机号
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人ID
     *
     * @return create_admin_id - 创建人ID
     */
    public String getCreateAdminId() {
        return createAdminId;
    }

    /**
     * 设置创建人ID
     *
     * @param createAdminId 创建人ID
     */
    public void setCreateAdminId(String createAdminId) {
        this.createAdminId = createAdminId;
    }

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取是否删除1是0否
     *
     * @return is_delete - 是否删除1是0否
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除1是0否
     *
     * @param isDelete 是否删除1是0否
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}