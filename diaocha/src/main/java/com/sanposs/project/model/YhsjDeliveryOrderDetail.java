package com.sanposs.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "yhsj_delivery_order_detail")
public class YhsjDeliveryOrderDetail implements Serializable{
    /**
     * 发货单详情id
     */
    @Id
    @Column(name = "detail_id")
    private String detailId;

    /**
     * 发货单id
     */
    @Column(name = "delivery_id")
    private String deliveryId;

    /**
     * 产品id
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 价格
     */
    @Column(name = "product_price")
    private BigDecimal productPrice;

    /**
     * 数量
     */
    @Column(name = "product_count")
    private Integer productCount;

    /**
     * 重量
     */
    @Column(name = "product_weight")
    private BigDecimal productWeight;

    /**
     * 总价
     */
    @Column(name = "product_total_price")
    private BigDecimal productTotalPrice;

    /**
     * 规格id
     */
    @Column(name = "tp_id")
    private String tpId;

    /**
     * 规格名称
     */
    @Column(name = "tp_name")
    private String tpName;

    /**
     * 状态
     */
    @Column(name = "detail_statue")
    private Integer detailStatue;

    /**
     * 是否删除，1是，否
     */
    @Column(name = "detail_is_delete")
    private Integer detailIsDelete;
    
    @Column(name = "produce_detail_id")
    private String produceDetailId;

    /**
     * 获取发货单详情id
     *
     * @return detail_id - 发货单详情id
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 设置发货单详情id
     *
     * @param detailId 发货单详情id
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

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
     * 获取产品id
     *
     * @return product_id - 产品id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置产品id
     *
     * @param productId 产品id
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 获取产品名称
     *
     * @return product_name - 产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置产品名称
     *
     * @param productName 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取价格
     *
     * @return product_price - 价格
     */
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * 设置价格
     *
     * @param productPrice 价格
     */
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * 获取数量
     *
     * @return product_count - 数量
     */
    public Integer getProductCount() {
        return productCount;
    }

    /**
     * 设置数量
     *
     * @param productCount 数量
     */
    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    /**
     * 获取重量
     *
     * @return product_weight - 重量
     */
    public BigDecimal getProductWeight() {
        return productWeight;
    }

    /**
     * 设置重量
     *
     * @param productWeight 重量
     */
    public void setProductWeight(BigDecimal productWeight) {
        this.productWeight = productWeight;
    }

    /**
     * 获取总价
     *
     * @return product_total_price - 总价
     */
    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    /**
     * 设置总价
     *
     * @param productTotalPrice 总价
     */
    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    /**
     * 获取规格id
     *
     * @return tp_id - 规格id
     */
    public String getTpId() {
        return tpId;
    }

    /**
     * 设置规格id
     *
     * @param tpId 规格id
     */
    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    /**
     * 获取规格名称
     *
     * @return tp_name - 规格名称
     */
    public String getTpName() {
        return tpName;
    }

    /**
     * 设置规格名称
     *
     * @param tpName 规格名称
     */
    public void setTpName(String tpName) {
        this.tpName = tpName;
    }

    /**
     * 获取状态
     *
     * @return detail_statue - 状态
     */
    public Integer getDetailStatue() {
        return detailStatue;
    }

    /**
     * 设置状态
     *
     * @param detailStatue 状态
     */
    public void setDetailStatue(Integer detailStatue) {
        this.detailStatue = detailStatue;
    }

    /**
     * 获取是否删除，1是，否
     *
     * @return detail_is_delete - 是否删除，1是，否
     */
    public Integer getDetailIsDelete() {
        return detailIsDelete;
    }

    /**
     * 设置是否删除，1是，否
     *
     * @param detailIsDelete 是否删除，1是，否
     */
    public void setDetailIsDelete(Integer detailIsDelete) {
        this.detailIsDelete = detailIsDelete;
    }

	public String getProduceDetailId() {
		return produceDetailId;
	}

	public void setProduceDetailId(String produceDetailId) {
		this.produceDetailId = produceDetailId;
	}
    
    
}