package com.sanposs.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "yhsj_delivery_order")
public class YhsjDeliveryOrder implements Serializable{
    /**
     * 发货单id
     */
    @Id
    @Column(name = "delivery_id")
    private String deliveryId;

    /**
     * 发货编号
     */
    @Column(name = "delivery_no")
    private String deliveryNo;

    /**
     * 采购编号
     */
    @Column(name = "purchase_no")
    private String purchaseNo;

    /**
     * 发货单状态，1已发货，2已收货，3已收款，4...
     */
    @Column(name = "delivery_status")
    private Integer deliveryStatus;

    /**
     * 总价
     */
    @Column(name = "delivery_total_price")
    private BigDecimal deliveryTotalPrice;

    /**
     * 总重
     */
    @Column(name = "delivery_total_weight")
    private BigDecimal deliveryTotalWeight;

    /**
     * 收货人id
     */
    @Column(name = "delivery_user_id")
    private String deliveryUserId;

    /**
     * 收货人名称
     */
    @Column(name = "delivery_user_name")
    private String deliveryUserName;

    /**
     * 发货时间
     */
    @Column(name = "delivery_create_time")
    private Date deliveryCreateTime;

    /**
     * 手机号
     */
    @Column(name = "delivery_phone")
    private String deliveryPhone;

    /**
     * 收货地址
     */
    @Column(name = "delivery_address")
    private String deliveryAddress;

    /**
     * 备注
     */
    @Column(name = "delivery_remark")
    private String deliveryRemark;

    /**
     * 是否删除，1是，否
     */
    @Column(name = "delivery_is_delete")
    private Integer deliveryIsDelete;

    /**
     * 更新时间
     */
    @Column(name = "delivery_update_time")
    private Date deliveryUpdateTime;

    /**
     * 操作人id
     */
    @Column(name = "delivery_operation_id")
    private String deliveryOperationId;

    /**
     * 操作人名称
     */
    @Column(name = "delivery_operation_name")
    private String deliveryOperationName;

    /**
     * 支付状态
     */
    @Column(name = "delivery_pay_type")
    private String deliveryPayType;

    /**
     * 支付宝金额
     */
    @Column(name = "delivery_zfb_amount")
    private BigDecimal deliveryZfbAmount;

    /**
     * 微信金额
     */
    @Column(name = "delivery_wx_amount")
    private BigDecimal deliveryWxAmount;

    /**
     * 银行卡金额
     */
    @Column(name = "delivery_yhk_amount")
    private BigDecimal deliveryYhkAmount;

    /**
     * 获取发货单id
     *
     * @return delivery_id - 发货单id
     */
    public String getDeliveryId() {
        return deliveryId;
    }

    /**
     * 设置发货单id
     *
     * @param deliveryId 发货单id
     */
    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    /**
     * 获取发货编号
     *
     * @return delivery_no - 发货编号
     */
    public String getDeliveryNo() {
        return deliveryNo;
    }

    /**
     * 设置发货编号
     *
     * @param deliveryNo 发货编号
     */
    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
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
     * 获取发货单状态，1已发货，2已收货，3已收款，4...
     *
     * @return delivery_status - 发货单状态，1已发货，2已收货，3已收款，4...
     */
    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    /**
     * 设置发货单状态，1已发货，2已收货，3已收款，4...
     *
     * @param deliveryStatus 发货单状态，1已发货，2已收货，3已收款，4...
     */
    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    /**
     * 获取总价
     *
     * @return delivery_total_price - 总价
     */
    public BigDecimal getDeliveryTotalPrice() {
        return deliveryTotalPrice;
    }

    /**
     * 设置总价
     *
     * @param deliveryTotalPrice 总价
     */
    public void setDeliveryTotalPrice(BigDecimal deliveryTotalPrice) {
        this.deliveryTotalPrice = deliveryTotalPrice;
    }

    /**
     * 获取总重
     *
     * @return delivery_total_weight - 总重
     */
    public BigDecimal getDeliveryTotalWeight() {
        return deliveryTotalWeight;
    }

    /**
     * 设置总重
     *
     * @param deliveryTotalWeight 总重
     */
    public void setDeliveryTotalWeight(BigDecimal deliveryTotalWeight) {
        this.deliveryTotalWeight = deliveryTotalWeight;
    }

    /**
     * 获取收货人id
     *
     * @return delivery_user_id - 收货人id
     */
    public String getDeliveryUserId() {
        return deliveryUserId;
    }

    /**
     * 设置收货人id
     *
     * @param deliveryUserId 收货人id
     */
    public void setDeliveryUserId(String deliveryUserId) {
        this.deliveryUserId = deliveryUserId;
    }

    /**
     * 获取收货人名称
     *
     * @return delivery_user_name - 收货人名称
     */
    public String getDeliveryUserName() {
        return deliveryUserName;
    }

    /**
     * 设置收货人名称
     *
     * @param deliveryUserName 收货人名称
     */
    public void setDeliveryUserName(String deliveryUserName) {
        this.deliveryUserName = deliveryUserName;
    }

    /**
     * 获取发货时间
     *
     * @return delivery_create_time - 发货时间
     */
    public Date getDeliveryCreateTime() {
        return deliveryCreateTime;
    }

    /**
     * 设置发货时间
     *
     * @param deliveryCreateTime 发货时间
     */
    public void setDeliveryCreateTime(Date deliveryCreateTime) {
        this.deliveryCreateTime = deliveryCreateTime;
    }

    /**
     * 获取手机号
     *
     * @return delivery_phone - 手机号
     */
    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    /**
     * 设置手机号
     *
     * @param deliveryPhone 手机号
     */
    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    /**
     * 获取收货地址
     *
     * @return delivery_address - 收货地址
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * 设置收货地址
     *
     * @param deliveryAddress 收货地址
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * 获取备注
     *
     * @return delivery_remark - 备注
     */
    public String getDeliveryRemark() {
        return deliveryRemark;
    }

    /**
     * 设置备注
     *
     * @param deliveryRemark 备注
     */
    public void setDeliveryRemark(String deliveryRemark) {
        this.deliveryRemark = deliveryRemark;
    }

    /**
     * 获取是否删除，1是，否
     *
     * @return delivery_is_delete - 是否删除，1是，否
     */
    public Integer getDeliveryIsDelete() {
        return deliveryIsDelete;
    }

    /**
     * 设置是否删除，1是，否
     *
     * @param deliveryIsDelete 是否删除，1是，否
     */
    public void setDeliveryIsDelete(Integer deliveryIsDelete) {
        this.deliveryIsDelete = deliveryIsDelete;
    }

    /**
     * 获取更新时间
     *
     * @return delivery_update_time - 更新时间
     */
    public Date getDeliveryUpdateTime() {
        return deliveryUpdateTime;
    }

    /**
     * 设置更新时间
     *
     * @param deliveryUpdateTime 更新时间
     */
    public void setDeliveryUpdateTime(Date deliveryUpdateTime) {
        this.deliveryUpdateTime = deliveryUpdateTime;
    }

    /**
     * 获取操作人id
     *
     * @return delivery_operation_id - 操作人id
     */
    public String getDeliveryOperationId() {
        return deliveryOperationId;
    }

    /**
     * 设置操作人id
     *
     * @param deliveryOperationId 操作人id
     */
    public void setDeliveryOperationId(String deliveryOperationId) {
        this.deliveryOperationId = deliveryOperationId;
    }

    /**
     * 获取操作人名称
     *
     * @return delivery_operation_name - 操作人名称
     */
    public String getDeliveryOperationName() {
        return deliveryOperationName;
    }

    /**
     * 设置操作人名称
     *
     * @param deliveryOperationName 操作人名称
     */
    public void setDeliveryOperationName(String deliveryOperationName) {
        this.deliveryOperationName = deliveryOperationName;
    }

    /**
     * 获取支付状态
     *
     * @return delivery_pay_type - 支付状态
     */
    public String getDeliveryPayType() {
        return deliveryPayType;
    }

    /**
     * 设置支付状态
     *
     * @param deliveryPayType 支付状态
     */
    public void setDeliveryPayType(String deliveryPayType) {
        this.deliveryPayType = deliveryPayType;
    }

    /**
     * 获取支付宝金额
     *
     * @return delivery_zfb_amount - 支付宝金额
     */
    public BigDecimal getDeliveryZfbAmount() {
        return deliveryZfbAmount;
    }

    /**
     * 设置支付宝金额
     *
     * @param deliveryZfbAmount 支付宝金额
     */
    public void setDeliveryZfbAmount(BigDecimal deliveryZfbAmount) {
        this.deliveryZfbAmount = deliveryZfbAmount;
    }

    /**
     * 获取微信金额
     *
     * @return delivery_wx_amount - 微信金额
     */
    public BigDecimal getDeliveryWxAmount() {
        return deliveryWxAmount;
    }

    /**
     * 设置微信金额
     *
     * @param deliveryWxAmount 微信金额
     */
    public void setDeliveryWxAmount(BigDecimal deliveryWxAmount) {
        this.deliveryWxAmount = deliveryWxAmount;
    }

    /**
     * 获取银行卡金额
     *
     * @return delivery_yhk_amount - 银行卡金额
     */
    public BigDecimal getDeliveryYhkAmount() {
        return deliveryYhkAmount;
    }

    /**
     * 设置银行卡金额
     *
     * @param deliveryYhkAmount 银行卡金额
     */
    public void setDeliveryYhkAmount(BigDecimal deliveryYhkAmount) {
        this.deliveryYhkAmount = deliveryYhkAmount;
    }
}