package com.nanwang.project.model;

import java.io.Serializable;

import javax.persistence.*;

@Table(name = "yhsj_advertisement")
public class YhsjAdvertisement  implements Serializable{
    @Id
    @Column(name = "advertisement_id")
    private String advertisementId;

    @Column(name = "advertisement_channel")
    private String advertisementChannel;

    @Column(name = "advertisement_name")
    private String advertisementName;

    @Column(name = "advertisement_desc")
    private String advertisementDesc;

    @Column(name = "advertisement_url")
    private String advertisementUrl;

    @Column(name = "advertisement_image_url")
    private String advertisementImageUrl;

    @Column(name = "advertisement_sort")
    private String advertisementSort;

    @Column(name = "advertisement_status")
    private Integer advertisementStatus;

    @Column(name = "advertisementCreateUser")
    private String advertisementcreateuser;

    /**
     * @return advertisement_id
     */
    public String getAdvertisementId() {
        return advertisementId;
    }

    /**
     * @param advertisementId
     */
    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }

    /**
     * @return advertisement_channel
     */
    public String getAdvertisementChannel() {
        return advertisementChannel;
    }

    /**
     * @param advertisementChannel
     */
    public void setAdvertisementChannel(String advertisementChannel) {
        this.advertisementChannel = advertisementChannel;
    }

    /**
     * @return advertisement_name
     */
    public String getAdvertisementName() {
        return advertisementName;
    }

    /**
     * @param advertisementName
     */
    public void setAdvertisementName(String advertisementName) {
        this.advertisementName = advertisementName;
    }

    /**
     * @return advertisement_desc
     */
    public String getAdvertisementDesc() {
        return advertisementDesc;
    }

    /**
     * @param advertisementDesc
     */
    public void setAdvertisementDesc(String advertisementDesc) {
        this.advertisementDesc = advertisementDesc;
    }

    /**
     * @return advertisement_url
     */
    public String getAdvertisementUrl() {
        return advertisementUrl;
    }

    /**
     * @param advertisementUrl
     */
    public void setAdvertisementUrl(String advertisementUrl) {
        this.advertisementUrl = advertisementUrl;
    }

    /**
     * @return advertisement_image_url
     */
    public String getAdvertisementImageUrl() {
        return advertisementImageUrl;
    }

    /**
     * @param advertisementImageUrl
     */
    public void setAdvertisementImageUrl(String advertisementImageUrl) {
        this.advertisementImageUrl = advertisementImageUrl;
    }

    /**
     * @return advertisement_sort
     */
    public String getAdvertisementSort() {
        return advertisementSort;
    }

    /**
     * @param advertisementSort
     */
    public void setAdvertisementSort(String advertisementSort) {
        this.advertisementSort = advertisementSort;
    }

    /**
     * @return advertisement_status
     */
    public Integer getAdvertisementStatus() {
        return advertisementStatus;
    }

    /**
     * @param advertisementStatus
     */
    public void setAdvertisementStatus(Integer advertisementStatus) {
        this.advertisementStatus = advertisementStatus;
    }

    /**
     * @return advertisementCreateUser
     */
    public String getAdvertisementcreateuser() {
        return advertisementcreateuser;
    }

    /**
     * @param advertisementcreateuser
     */
    public void setAdvertisementcreateuser(String advertisementcreateuser) {
        this.advertisementcreateuser = advertisementcreateuser;
    }
}