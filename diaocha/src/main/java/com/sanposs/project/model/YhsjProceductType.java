package com.sanposs.project.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_proceduct_type")
public class YhsjProceductType implements Serializable{
    /**
     * 产品类型id
     */
    @Id
    @Column(name = "type_id")
    private String typeId;

    /**
     * 产品类型名称
     */
    @Column(name = "type_name")
    private String typeName;

    /**
     * 产品上级分类
     */
    @Column(name = "type_parent")
    private String typeParent;

    /**
     * 创建时间
     */
    @Column(name = "type_create_date")
    private Date typeCreateDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标志
     */
    @Column(name = "type_delete_flag")
    private String typeDeleteFlag;

    /**
     * 分类图标
     */
    @Column(name = "type_log_img")
    private String typeLogImg;

    /**
     * 分类显示排序
     */
    @Column(name = "type_show_sort")
    private String typeShowSort;

    /**
     * 获取产品类型id
     *
     * @return type_id - 产品类型id
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * 设置产品类型id
     *
     * @param typeId 产品类型id
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取产品类型名称
     *
     * @return type_name - 产品类型名称
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 设置产品类型名称
     *
     * @param typeName 产品类型名称
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 获取产品上级分类
     *
     * @return type_parent - 产品上级分类
     */
    public String getTypeParent() {
        return typeParent;
    }

    /**
     * 设置产品上级分类
     *
     * @param typeParent 产品上级分类
     */
    public void setTypeParent(String typeParent) {
        this.typeParent = typeParent;
    }

    /**
     * 获取创建时间
     *
     * @return type_create_date - 创建时间
     */
    public Date getTypeCreateDate() {
        return typeCreateDate;
    }

    /**
     * 设置创建时间
     *
     * @param typeCreateDate 创建时间
     */
    public void setTypeCreateDate(Date typeCreateDate) {
        this.typeCreateDate = typeCreateDate;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取删除标志
     *
     * @return type_delete_flag - 删除标志
     */
    public String getTypeDeleteFlag() {
        return typeDeleteFlag;
    }

    /**
     * 设置删除标志
     *
     * @param typeDeleteFlag 删除标志
     */
    public void setTypeDeleteFlag(String typeDeleteFlag) {
        this.typeDeleteFlag = typeDeleteFlag;
    }

    /**
     * 获取分类图标
     *
     * @return type_log_img - 分类图标
     */
    public String getTypeLogImg() {
        return typeLogImg;
    }

    /**
     * 设置分类图标
     *
     * @param typeLogImg 分类图标
     */
    public void setTypeLogImg(String typeLogImg) {
        this.typeLogImg = typeLogImg;
    }

    /**
     * 获取分类显示排序
     *
     * @return type_show_sort - 分类显示排序
     */
    public String getTypeShowSort() {
        return typeShowSort;
    }

    /**
     * 设置分类显示排序
     *
     * @param typeShowSort 分类显示排序
     */
    public void setTypeShowSort(String typeShowSort) {
        this.typeShowSort = typeShowSort;
    }
}