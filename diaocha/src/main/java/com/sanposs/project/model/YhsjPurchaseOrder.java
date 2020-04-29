package com.sanposs.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_purchase_order")
public class YhsjPurchaseOrder implements Serializable{
    /**
     * 采购单id
     */
    @Id
    @Column(name = "purchase_id")
    private String purchaseId;

    /**
     * 采购编号
     */
    @Column(name = "purchase_no")
    private String purchaseNo;

    /**
     * 采购单状态，1未处理，2已处理，3已完成
     */
    @Column(name = "purchase_status")
    private Integer purchaseStatus;

    /**
     * 下单人id
     */
    @Column(name = "purchase_create_user_id")
    private String purchaseCreateUserId;

    /**
     * 下单人名称
     */
    @Column(name = "purchase_create_user_name")
    private String purchaseCreateUserName;

    /**
     * 下单时间
     */
    @Column(name = "purchase_create_time")
    private Date purchaseCreateTime;

    /**
     * 手机号
     */
    @Column(name = "purchase_phone")
    private String purchasePhone;

    /**
     * 下单地址
     */
    @Column(name = "purchase_address")
    private String purchaseAddress;

    /**
     * 备注
     */
    @Column(name = "purchase_remark")
    private String purchaseRemark;

    /**
     * 是否删除，1是，否
     */
    @Column(name = "purchase_is_delete")
    private Integer purchaseIsDelete;

    /**
     * 更新时间
     */
    @Column(name = "purchase_update_time")
    private Date purchaseUpdateTime;

    /**
     * 地址主键
     */
    @Column(name = "purchase_address_id")
    private String purchaseAddressId;

    /**
     * 订单总额
     */
    @Column(name = "purchase_sumprice")
    private BigDecimal purchaseSumprice;

    /**
     * 获取采购单id
     *
     * @return purchase_id - 采购单id
     */
    public String getPurchaseId() {
        return purchaseId;
    }

    /**
     * 设置采购单id
     *
     * @param purchaseId 采购单id
     */
    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    /**
     * 获取采购编号
     *
     * @return purchase_no - 采购编号
     */
    public String getPurchaseNo() {
        return purchaseNo;
    }

    /**
     * 设置采购编号
     *
     * @param purchaseNo 采购编号
     */
    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    /**
     * 获取采购单状态，1未处理，2已处理，3已完成
     *
     * @return purchase_status - 采购单状态，1未处理，2已处理，3已完成
     */
    public Integer getPurchaseStatus() {
        return purchaseStatus;
    }

    /**
     * 设置采购单状态，1未处理，2已处理，3已完成
     *
     * @param purchaseStatus 采购单状态，1未处理，2已处理，3已完成
     */
    public void setPurchaseStatus(Integer purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    /**
     * 获取下单人id
     *
     * @return purchase_create_user_id - 下单人id
     */
    public String getPurchaseCreateUserId() {
        return purchaseCreateUserId;
    }

    /**
     * 设置下单人id
     *
     * @param purchaseCreateUserId 下单人id
     */
    public void setPurchaseCreateUserId(String purchaseCreateUserId) {
        this.purchaseCreateUserId = purchaseCreateUserId;
    }

    /**
     * 获取下单人名称
     *
     * @return purchase_create_user_name - 下单人名称
     */
    public String getPurchaseCreateUserName() {
        return purchaseCreateUserName;
    }

    /**
     * 设置下单人名称
     *
     * @param purchaseCreateUserName 下单人名称
     */
    public void setPurchaseCreateUserName(String purchaseCreateUserName) {
        this.purchaseCreateUserName = purchaseCreateUserName;
    }

    /**
     * 获取下单时间
     *
     * @return purchase_create_time - 下单时间
     */
    public Date getPurchaseCreateTime() {
        return purchaseCreateTime;
    }

    /**
     * 设置下单时间
     *
     * @param purchaseCreateTime 下单时间
     */
    public void setPurchaseCreateTime(Date purchaseCreateTime) {
        this.purchaseCreateTime = purchaseCreateTime;
    }

    /**
     * 获取手机号
     *
     * @return purchase_phone - 手机号
     */
    public String getPurchasePhone() {
        return purchasePhone;
    }

    /**
     * 设置手机号
     *
     * @param purchasePhone 手机号
     */
    public void setPurchasePhone(String purchasePhone) {
        this.purchasePhone = purchasePhone;
    }

    /**
     * 获取下单地址
     *
     * @return purchase_address - 下单地址
     */
    public String getPurchaseAddress() {
        return purchaseAddress;
    }

    /**
     * 设置下单地址
     *
     * @param purchaseAddress 下单地址
     */
    public void setPurchaseAddress(String purchaseAddress) {
        this.purchaseAddress = purchaseAddress;
    }

    /**
     * 获取备注
     *
     * @return purchase_remark - 备注
     */
    public String getPurchaseRemark() {
        return purchaseRemark;
    }

    /**
     * 设置备注
     *
     * @param purchaseRemark 备注
     */
    public void setPurchaseRemark(String purchaseRemark) {
        this.purchaseRemark = purchaseRemark;
    }

    /**
     * 获取是否删除，1是，否
     *
     * @return purchase_is_delete - 是否删除，1是，否
     */
    public Integer getPurchaseIsDelete() {
        return purchaseIsDelete;
    }

    /**
     * 设置是否删除，1是，否
     *
     * @param purchaseIsDelete 是否删除，1是，否
     */
    public void setPurchaseIsDelete(Integer purchaseIsDelete) {
        this.purchaseIsDelete = purchaseIsDelete;
    }

    /**
     * 获取更新时间
     *
     * @return purchase_update_time - 更新时间
     */
    public Date getPurchaseUpdateTime() {
        return purchaseUpdateTime;
    }

    /**
     * 设置更新时间
     *
     * @param purchaseUpdateTime 更新时间
     */
    public void setPurchaseUpdateTime(Date purchaseUpdateTime) {
        this.purchaseUpdateTime = purchaseUpdateTime;
    }

    /**
     * 获取地址主键
     *
     * @return purchase_address_id - 地址主键
     */
    public String getPurchaseAddressId() {
        return purchaseAddressId;
    }

    /**
     * 设置地址主键
     *
     * @param purchaseAddressId 地址主键
     */
    public void setPurchaseAddressId(String purchaseAddressId) {
        this.purchaseAddressId = purchaseAddressId;
    }

    /**
     * 获取订单总额
     *
     * @return purchase_sumprice - 订单总额
     */
    public BigDecimal getPurchaseSumprice() {
        return purchaseSumprice;
    }

    /**
     * 设置订单总额
     *
     * @param purchaseSumprice 订单总额
     */
    public void setPurchaseSumprice(BigDecimal purchaseSumprice) {
        this.purchaseSumprice = purchaseSumprice;
    }
}