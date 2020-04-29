package com.sanposs.project.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_user")
public class YhsjUser implements Serializable {
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 手机号
     */
    @Column(name = "user_phone")
    private String userPhone;

    /**
     * 真实姓名
     */
    @Column(name = "user_real_name")
    private String userRealName;

    @Column(name = "user_password")
    private String userPassword;

    /**
     * 微信用户的unionid
     */
    @Column(name = "user_weixin_unionid")
    private String userWeixinUnionid;

    /**
     * 微信openid
     */
    @Column(name = "user_weixin_openId")
    private String userWeixinOpenid;

    /**
     * 注册渠道 1、iso 2、android 3、web网站4、微信5、手机m站
     */
    @Column(name = "user_reg_channel")
    private Integer userRegChannel;

    /**
     * 头像路径
     */
    @Column(name = "user_head_pic")
    private String userHeadPic;

    /**
     * 用户类型 1普通用户 2 会员 3分销商
     */
    @Column(name = "user_type")
    private Integer userType;

    /**
     * 详情
     */
    @Column(name = "user_content")
    private String userContent;

    /**
     * 创建时间
     */
    @Column(name = "user_create_time")
    private Date userCreateTime;

    /**
     * 是否删除,0为删除，其他为未删除
     */
    @Column(name = "user_is_delete")
    private Integer userIsDelete;

    /**
     * 邀请码
     */
    @Column(name = "user_invite_code")
    private String userInviteCode;

    /**
     * 我的邀请码
     */
    @Column(name = "user_myinvite_code")
    private String userMyinviteCode;

    /**
     * 身份证号
     */
    @Column(name = "user_identity")
    private String userIdentity;

    /**
     * 1旧系统用户，2新系统用户，12两个系统都是的用户，
     */
    @Column(name = "user_status")
    private String userStatus;

    /**
     * 上级用户id
     */
    @Column(name = "user_parent_id")
    private String userParentId;

    /**
     * 上级用户名称
     */
    @Column(name = "user_parent_name")
    private String userParentName;

    /**
     * 上级用户头像
     */
    @Column(name = "user_parent_headpic")
    private String userParentHeadpic;

    /**
     * 详细地址
     */
    @Column(name = "user_address_detail")
    private String userAddressDetail;

    /**
     * 经度
     */
    @Column(name = "user_lat")
    private Double userLat;

    /**
     * 纬度
     */
    @Column(name = "user_lnt")
    private Double userLnt;

    @Column(name = "user_xcx_openId")
    private String userXcxOpenid;

    @Column(name = "user_level")
    private Integer userLevel;

    @Column(name = "user_channel")
    private Integer userChannel;

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
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取手机号
     *
     * @return user_phone - 手机号
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * 设置手机号
     *
     * @param userPhone 手机号
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * 获取真实姓名
     *
     * @return user_real_name - 真实姓名
     */
    public String getUserRealName() {
        return userRealName;
    }

    /**
     * 设置真实姓名
     *
     * @param userRealName 真实姓名
     */
    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    /**
     * @return user_password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * 获取微信用户的unionid
     *
     * @return user_weixin_unionid - 微信用户的unionid
     */
    public String getUserWeixinUnionid() {
        return userWeixinUnionid;
    }

    /**
     * 设置微信用户的unionid
     *
     * @param userWeixinUnionid 微信用户的unionid
     */
    public void setUserWeixinUnionid(String userWeixinUnionid) {
        this.userWeixinUnionid = userWeixinUnionid;
    }

    /**
     * 获取微信openid
     *
     * @return user_weixin_openId - 微信openid
     */
    public String getUserWeixinOpenid() {
        return userWeixinOpenid;
    }

    /**
     * 设置微信openid
     *
     * @param userWeixinOpenid 微信openid
     */
    public void setUserWeixinOpenid(String userWeixinOpenid) {
        this.userWeixinOpenid = userWeixinOpenid;
    }

    /**
     * 获取注册渠道 1、iso 2、android 3、web网站4、微信5、手机m站
     *
     * @return user_reg_channel - 注册渠道 1、iso 2、android 3、web网站4、微信5、手机m站
     */
    public Integer getUserRegChannel() {
        return userRegChannel;
    }

    /**
     * 设置注册渠道 1、iso 2、android 3、web网站4、微信5、手机m站
     *
     * @param userRegChannel 注册渠道 1、iso 2、android 3、web网站4、微信5、手机m站
     */
    public void setUserRegChannel(Integer userRegChannel) {
        this.userRegChannel = userRegChannel;
    }

    /**
     * 获取头像路径
     *
     * @return user_head_pic - 头像路径
     */
    public String getUserHeadPic() {
        return userHeadPic;
    }

    /**
     * 设置头像路径
     *
     * @param userHeadPic 头像路径
     */
    public void setUserHeadPic(String userHeadPic) {
        this.userHeadPic = userHeadPic;
    }

    /**
     * 获取用户类型 1普通用户 2 会员 3分销商
     *
     * @return user_type - 用户类型 1普通用户 2 会员 3分销商
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置用户类型 1普通用户 2 车间主管 3 老板
     *
     * @param userType 用户类型 1普通用户 2 车间主管 3 老板
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 获取详情
     *
     * @return user_content - 详情
     */
    public String getUserContent() {
        return userContent;
    }

    /**
     * 设置详情
     *
     * @param userContent 详情
     */
    public void setUserContent(String userContent) {
        this.userContent = userContent;
    }

    /**
     * 获取创建时间
     *
     * @return user_create_time - 创建时间
     */
    public Date getUserCreateTime() {
        return userCreateTime;
    }

    /**
     * 设置创建时间
     *
     * @param userCreateTime 创建时间
     */
    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    /**
     * 获取是否删除,0为删除，其他为未删除
     *
     * @return user_is_delete - 是否删除,0为删除，其他为未删除
     */
    public Integer getUserIsDelete() {
        return userIsDelete;
    }

    /**
     * 设置是否删除,0为删除，其他为未删除
     *
     * @param userIsDelete 是否删除,0为删除，其他为未删除
     */
    public void setUserIsDelete(Integer userIsDelete) {
        this.userIsDelete = userIsDelete;
    }

    /**
     * 获取邀请码
     *
     * @return user_invite_code - 邀请码
     */
    public String getUserInviteCode() {
        return userInviteCode;
    }

    /**
     * 设置邀请码
     *
     * @param userInviteCode 邀请码
     */
    public void setUserInviteCode(String userInviteCode) {
        this.userInviteCode = userInviteCode;
    }

    /**
     * 获取我的邀请码
     *
     * @return user_myinvite_code - 我的邀请码
     */
    public String getUserMyinviteCode() {
        return userMyinviteCode;
    }

    /**
     * 设置我的邀请码
     *
     * @param userMyinviteCode 我的邀请码
     */
    public void setUserMyinviteCode(String userMyinviteCode) {
        this.userMyinviteCode = userMyinviteCode;
    }

    /**
     * 获取身份证号
     *
     * @return user_identity - 身份证号
     */
    public String getUserIdentity() {
        return userIdentity;
    }

    /**
     * 设置身份证号
     *
     * @param userIdentity 身份证号
     */
    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    /**
     * 获取1旧系统用户，2新系统用户，12两个系统都是的用户，
     *
     * @return user_status - 1旧系统用户，2新系统用户，12两个系统都是的用户，
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     * 设置1旧系统用户，2新系统用户，12两个系统都是的用户，
     *
     * @param userStatus 1旧系统用户，2新系统用户，12两个系统都是的用户，
     */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * 获取上级用户id
     *
     * @return user_parent_id - 上级用户id
     */
    public String getUserParentId() {
        return userParentId;
    }

    /**
     * 设置上级用户id
     *
     * @param userParentId 上级用户id
     */
    public void setUserParentId(String userParentId) {
        this.userParentId = userParentId;
    }

    /**
     * 获取上级用户名称
     *
     * @return user_parent_name - 上级用户名称
     */
    public String getUserParentName() {
        return userParentName;
    }

    /**
     * 设置上级用户名称
     *
     * @param userParentName 上级用户名称
     */
    public void setUserParentName(String userParentName) {
        this.userParentName = userParentName;
    }

    /**
     * 获取上级用户头像
     *
     * @return user_parent_headpic - 上级用户头像
     */
    public String getUserParentHeadpic() {
        return userParentHeadpic;
    }

    /**
     * 设置上级用户头像
     *
     * @param userParentHeadpic 上级用户头像
     */
    public void setUserParentHeadpic(String userParentHeadpic) {
        this.userParentHeadpic = userParentHeadpic;
    }

    /**
     * 获取详细地址
     *
     * @return user_address_detail - 详细地址
     */
    public String getUserAddressDetail() {
        return userAddressDetail;
    }

    /**
     * 设置详细地址
     *
     * @param userAddressDetail 详细地址
     */
    public void setUserAddressDetail(String userAddressDetail) {
        this.userAddressDetail = userAddressDetail;
    }

    /**
     * 获取经度
     *
     * @return user_lat - 经度
     */
    public Double getUserLat() {
        return userLat;
    }

    /**
     * 设置经度
     *
     * @param userLat 经度
     */
    public void setUserLat(Double userLat) {
        this.userLat = userLat;
    }

    /**
     * 获取纬度
     *
     * @return user_lnt - 纬度
     */
    public Double getUserLnt() {
        return userLnt;
    }

    /**
     * 设置纬度
     *
     * @param userLnt 纬度
     */
    public void setUserLnt(Double userLnt) {
        this.userLnt = userLnt;
    }

    /**
     * @return user_xcx_openId
     */
    public String getUserXcxOpenid() {
        return userXcxOpenid;
    }

    /**
     * @param userXcxOpenid
     */
    public void setUserXcxOpenid(String userXcxOpenid) {
        this.userXcxOpenid = userXcxOpenid;
    }

    /**
     * @return user_level
     */
    public Integer getUserLevel() {
        return userLevel;
    }

    /**
     * @param userLevel
     */
    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * @return user_channel
     */
    public Integer getUserChannel() {
        return userChannel;
    }

    /**
     * @param userChannel
     */
    public void setUserChannel(Integer userChannel) {
        this.userChannel = userChannel;
    }
}