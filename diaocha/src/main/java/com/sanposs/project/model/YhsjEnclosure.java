package com.sanposs.project.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_enclosure")
public class YhsjEnclosure implements Serializable{
    @Id
    @Column(name = "enclosure_id")
    private String enclosureId;

    /**
     * 附件地址
     */
    @Column(name = "enclosure_url")
    private String enclosureUrl;

    /**
     * 图片（附件）名称

     */
    @Column(name = "enclosure_name")
    private String enclosureName;

    /**
     * 后缀

     */
    @Column(name = "enclosure_suffix")
    private String enclosureSuffix;

    /**
     * 上传时间

     */
    @Column(name = "enclosure_time")
    private Date enclosureTime;

    /**
     * 排序

     */
    @Column(name = "enclosure_sort")
    private String enclosureSort;

    /**
     * 类型：1为产品2为其他
     */
    @Column(name = "enclosure_type")
    private Integer enclosureType;

    /**
     * 服务上user_id
     */
    @Column(name = "enclosure_parent_id")
    private String enclosureParentId;

    /**
     * @return enclosure_id
     */
    public String getEnclosureId() {
        return enclosureId;
    }

    /**
     * @param enclosureId
     */
    public void setEnclosureId(String enclosureId) {
        this.enclosureId = enclosureId;
    }

    /**
     * 获取附件地址
     *
     * @return enclosure_url - 附件地址
     */
    public String getEnclosureUrl() {
        return enclosureUrl;
    }

    /**
     * 设置附件地址
     *
     * @param enclosureUrl 附件地址
     */
    public void setEnclosureUrl(String enclosureUrl) {
        this.enclosureUrl = enclosureUrl;
    }

    /**
     * 获取图片（附件）名称

     *
     * @return enclosure_name - 图片（附件）名称

     */
    public String getEnclosureName() {
        return enclosureName;
    }

    /**
     * 设置图片（附件）名称

     *
     * @param enclosureName 图片（附件）名称

     */
    public void setEnclosureName(String enclosureName) {
        this.enclosureName = enclosureName;
    }

    /**
     * 获取后缀

     *
     * @return enclosure_suffix - 后缀

     */
    public String getEnclosureSuffix() {
        return enclosureSuffix;
    }

    /**
     * 设置后缀

     *
     * @param enclosureSuffix 后缀

     */
    public void setEnclosureSuffix(String enclosureSuffix) {
        this.enclosureSuffix = enclosureSuffix;
    }

    /**
     * 获取上传时间

     *
     * @return enclosure_time - 上传时间

     */
    public Date getEnclosureTime() {
        return enclosureTime;
    }

    /**
     * 设置上传时间

     *
     * @param enclosureTime 上传时间

     */
    public void setEnclosureTime(Date enclosureTime) {
        this.enclosureTime = enclosureTime;
    }

    /**
     * 获取排序

     *
     * @return enclosure_sort - 排序

     */
    public String getEnclosureSort() {
        return enclosureSort;
    }

    /**
     * 设置排序

     *
     * @param enclosureSort 排序

     */
    public void setEnclosureSort(String enclosureSort) {
        this.enclosureSort = enclosureSort;
    }

    /**
     * 获取类型：1为产品2为其他
     *
     * @return enclosure_type - 类型：1为产品2为其他
     */
    public Integer getEnclosureType() {
        return enclosureType;
    }

    /**
     * 设置类型：1为产品2为其他
     *
     * @param enclosureType 类型：1为产品2为其他
     */
    public void setEnclosureType(Integer enclosureType) {
        this.enclosureType = enclosureType;
    }

    /**
     * 获取服务上user_id
     *
     * @return enclosure_parent_id - 服务上user_id
     */
    public String getEnclosureParentId() {
        return enclosureParentId;
    }

    /**
     * 设置服务上user_id
     *
     * @param enclosureParentId 服务上user_id
     */
    public void setEnclosureParentId(String enclosureParentId) {
        this.enclosureParentId = enclosureParentId;
    }
}