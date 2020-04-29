package com.sanposs.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_user_address")
public class YhsjUserAddress {
    @Id
    @Column(name = "address_id")
    private String addressId;

    @Column(name = "address_privince")
    private String addressPrivince;

    @Column(name = "address_city")
    private String addressCity;

    @Column(name = "address_town")
    private String addressTown;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "address_create_time")
    private Date addressCreateTime;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_is_default")
    private Integer userIsDefault;

    @Column(name = "user_phone")
    private String userPhone;

    /**
     * @return address_id
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     * @param addressId
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    /**
     * @return address_privince
     */
    public String getAddressPrivince() {
        return addressPrivince;
    }

    /**
     * @param addressPrivince
     */
    public void setAddressPrivince(String addressPrivince) {
        this.addressPrivince = addressPrivince;
    }

    /**
     * @return address_city
     */
    public String getAddressCity() {
        return addressCity;
    }

    /**
     * @param addressCity
     */
    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    /**
     * @return address_town
     */
    public String getAddressTown() {
        return addressTown;
    }

    /**
     * @param addressTown
     */
    public void setAddressTown(String addressTown) {
        this.addressTown = addressTown;
    }

    /**
     * @return address_detail
     */
    public String getAddressDetail() {
        return addressDetail;
    }

    /**
     * @param addressDetail
     */
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    /**
     * @return address_create_time
     */
    public Date getAddressCreateTime() {
        return addressCreateTime;
    }

    /**
     * @param addressCreateTime
     */
    public void setAddressCreateTime(Date addressCreateTime) {
        this.addressCreateTime = addressCreateTime;
    }

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
     * @return user_is_default
     */
    public Integer getUserIsDefault() {
        return userIsDefault;
    }

    /**
     * @param userIsDefault
     */
    public void setUserIsDefault(Integer userIsDefault) {
        this.userIsDefault = userIsDefault;
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
}