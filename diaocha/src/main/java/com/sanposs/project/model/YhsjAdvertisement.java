package com.sanposs.project.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_advertisement")
public class YhsjAdvertisement implements Serializable{
    /**
     * 主键
     */
    @Id
    @Column(name = "advertisement_id")
    private String advertisementId;

    /**
     * 类型  1、iso 2、android 3、web网站4、微信5、手机m站
     */
    @Column(name = "advertisement_channel")
    private Integer advertisementChannel;

    /**
     * 名称

     */
    @Column(name = "advertisement_name")
    private String advertisementName;

    /**
     * 详情

     */
    @Column(name = "advertisement_desc")
    private String advertisementDesc;

    /**
     * 链接地址

     */
    @Column(name = "advertisement_url")
    private String advertisementUrl;

    /**
     * 图片路径

     */
    @Column(name = "advertisement_image_url")
    private String advertisementImageUrl;

    /**
     * 排序

     */
    @Column(name = "advertisement_sort")
    private String advertisementSort;

    /**
     * 状态1为可用，0为不可用

     */
    @Column(name = "advertisement_status")
    private Integer advertisementStatus;

    /**
     * 创建时间

     */
    @Column(name = "advertisement_create_time")
    private Date advertisementCreateTime;

    /**
     * 创建人

     */
    @Column(name = "advertisement_create_user")
    private String advertisementCreateUser;

    /**
     * 公司id
     */
    @Column(name = "company_id")
    private String companyId;

    /**
     * 公司名称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 获取主键
     *
     * @return advertisement_id - 主键
     */
    public String getAdvertisementId() {
        return advertisementId;
    }

    /**
     * 设置主键
     *
     * @param advertisementId 主键
     */
    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }

    /**
     * 获取类型  1、iso 2、android 3、web网站4、微信5、手机m站
     *
     * @return advertisement_channel - 类型  1、iso 2、android 3、web网站4、微信5、手机m站
     */
    public Integer getAdvertisementChannel() {
        return advertisementChannel;
    }

    /**
     * 设置类型  1、iso 2、android 3、web网站4、微信5、手机m站
     *
     * @param advertisementChannel 类型  1、iso 2、android 3、web网站4、微信5、手机m站
     */
    public void setAdvertisementChannel(Integer advertisementChannel) {
        this.advertisementChannel = advertisementChannel;
    }

    /**
     * 获取名称

     *
     * @return advertisement_name - 名称

     */
    public String getAdvertisementName() {
        return advertisementName;
    }

    /**
     * 设置名称

     *
     * @param advertisementName 名称

     */
    public void setAdvertisementName(String advertisementName) {
        this.advertisementName = advertisementName;
    }

    /**
     * 获取详情

     *
     * @return advertisement_desc - 详情

     */
    public String getAdvertisementDesc() {
        return advertisementDesc;
    }

    /**
     * 设置详情

     *
     * @param advertisementDesc 详情

     */
    public void setAdvertisementDesc(String advertisementDesc) {
        this.advertisementDesc = advertisementDesc;
    }

    /**
     * 获取链接地址

     *
     * @return advertisement_url - 链接地址

     */
    public String getAdvertisementUrl() {
        return advertisementUrl;
    }

    /**
     * 设置链接地址

     *
     * @param advertisementUrl 链接地址

     */
    public void setAdvertisementUrl(String advertisementUrl) {
        this.advertisementUrl = advertisementUrl;
    }

    /**
     * 获取图片路径

     *
     * @return advertisement_image_url - 图片路径

     */
    public String getAdvertisementImageUrl() {
        return advertisementImageUrl;
    }

    /**
     * 设置图片路径

     *
     * @param advertisementImageUrl 图片路径

     */
    public void setAdvertisementImageUrl(String advertisementImageUrl) {
        this.advertisementImageUrl = advertisementImageUrl;
    }

    /**
     * 获取排序

     *
     * @return advertisement_sort - 排序

     */
    public String getAdvertisementSort() {
        return advertisementSort;
    }

    /**
     * 设置排序

     *
     * @param advertisementSort 排序

     */
    public void setAdvertisementSort(String advertisementSort) {
        this.advertisementSort = advertisementSort;
    }

    /**
     * 获取状态1为可用，0为不可用

     *
     * @return advertisement_status - 状态1为可用，0为不可用

     */
    public Integer getAdvertisementStatus() {
        return advertisementStatus;
    }

    /**
     * 设置状态1为可用，0为不可用

     *
     * @param advertisementStatus 状态1为可用，0为不可用

     */
    public void setAdvertisementStatus(Integer advertisementStatus) {
        this.advertisementStatus = advertisementStatus;
    }

    /**
     * 获取创建时间

     *
     * @return advertisement_create_time - 创建时间

     */
    public Date getAdvertisementCreateTime() {
        return advertisementCreateTime;
    }

    /**
     * 设置创建时间

     *
     * @param advertisementCreateTime 创建时间

     */
    public void setAdvertisementCreateTime(Date advertisementCreateTime) {
        this.advertisementCreateTime = advertisementCreateTime;
    }

    /**
     * 获取创建人

     *
     * @return advertisement_create_user - 创建人

     */
    public String getAdvertisementCreateUser() {
        return advertisementCreateUser;
    }

    /**
     * 设置创建人

     *
     * @param advertisementCreateUser 创建人

     */
    public void setAdvertisementCreateUser(String advertisementCreateUser) {
        this.advertisementCreateUser = advertisementCreateUser;
    }

    /**
     * 获取公司id
     *
     * @return company_id - 公司id
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 设置公司id
     *
     * @param companyId 公司id
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取公司名称
     *
     * @return company_name - 公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置公司名称
     *
     * @param companyName 公司名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}