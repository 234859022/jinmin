package com.nanwang.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "E_USER")
public class EUser  implements Serializable{
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_post")
    private String userPost;

    @Column(name = "user_create_time")
    private Date userCreateTime;

    @Column(name = "user_sex")
    private Integer userSex;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_remark")
    private String userRemark;

    @Column(name = "user_create_user")
    private String userCreateUser;

    @Column(name = "user_entry_time")
    private String userEntryTime;

    @Column(name = "user_total_score")
    private BigDecimal userTotalScore;

    @Column(name = "user_all_score")
    private BigDecimal userAllScore;

    @Column(name = "user_count")
    private Integer userCount;

    @Column(name = "user_status")
    private Integer userStatus;

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return user_post
     */
    public String getUserPost() {
        return userPost;
    }

    /**
     * @param userPost
     */
    public void setUserPost(String userPost) {
        this.userPost = userPost;
    }

    /**
     * @return user_create_time
     */
    public Date getUserCreateTime() {
        return userCreateTime;
    }

    /**
     * @param userCreateTime
     */
    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    /**
     * @return user_sex
     */
    public Integer getUserSex() {
        return userSex;
    }

    /**
     * @param userSex
     */
    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    /**
     * @return user_phone
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * @param userPhone
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * @return user_remark
     */
    public String getUserRemark() {
        return userRemark;
    }

    /**
     * @param userRemark
     */
    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    /**
     * @return user_create_user
     */
    public String getUserCreateUser() {
        return userCreateUser;
    }

    /**
     * @param userCreateUser
     */
    public void setUserCreateUser(String userCreateUser) {
        this.userCreateUser = userCreateUser;
    }

    /**
     * @return user_entry_time
     */
    public String getUserEntryTime() {
        return userEntryTime;
    }

    /**
     * @param userEntryTime
     */
    public void setUserEntryTime(String userEntryTime) {
        this.userEntryTime = userEntryTime;
    }

    /**
     * @return user_total_score
     */
    public BigDecimal getUserTotalScore() {
        return userTotalScore;
    }

    /**
     * @param userTotalScore
     */
    public void setUserTotalScore(BigDecimal userTotalScore) {
        this.userTotalScore = userTotalScore;
    }

    /**
     * @return user_all_score
     */
    public BigDecimal getUserAllScore() {
        return userAllScore;
    }

    /**
     * @param userAllScore
     */
    public void setUserAllScore(BigDecimal userAllScore) {
        this.userAllScore = userAllScore;
    }

    /**
     * @return user_count
     */
    public Integer getUserCount() {
        return userCount;
    }

    /**
     * @param userCount
     */
    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    /**
     * @return user_status
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}