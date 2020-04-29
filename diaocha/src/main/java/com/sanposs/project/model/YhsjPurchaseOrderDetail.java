package com.sanposs.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "yhsj_purchase_order_detail")
public class YhsjPurchaseOrderDetail implements Serializable{
    /**
     * 采购单详情id
     */
    @Id
    @Column(name = "detail_id")
    private String detailId;

    /**
     * 采购单id
     */
    @Column(name = "purchase_id")
    private String purchaseId;

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
     * 重量
     */
    @Column(name = "product_weight")
    private BigDecimal productWeight;

    /**
     * 数量
     */
    @Column(name = "product_count")
    private Integer productCount;

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
     * 状态，1未处理 ，2未处理吹膜，3已吹膜处理 4.已吹膜完成 5.未处理封切，6已封切处理 7.已封切完成
     */
    @Column(name = "detail_statue")
    private Integer detailStatue;

    /**
     * 是否删除，1是，否
     */
    @Column(name = "detail_is_delete")
    private Integer detailIsDelete;

    /**
     * 吹膜机器id
     */
    @Column(name = "blown_machine_id")
    private String blownMachineId;

    /**
     * 吹膜机器名称
     */
    @Column(name = "blown_machine_name")
    private String blownMachineName;

    @Column(name = "blown_time")
    private Date blownTime;

    /**
     * 封切机器id
     */
    @Column(name = "cut_machine_id")
    private String cutMachineId;

    /**
     * 封切机器名称
     */
    @Column(name = "cut_machine_name")
    private String cutMachineName;

    @Column(name = "cut_time")
    private Date cutTime;
    
    /**
     * 生产明细id
     */
    @Column(name = "produce_detail_id")
    private String produceDetailId;

    /**
     * 实际生产重量
     */
    @Column(name = "actual_weight")
    private BigDecimal actualWeight;

    /**
     * 获取采购单详情id
     *
     * @return detail_id - 采购单详情id
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 设置采购单详情id
     *
     * @param detailId 采购单详情id
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

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
     * 获取状态，1未处理 ，2未处理吹膜，3已吹膜处理 4.已吹膜完成 5.未处理封切，6已封切处理 7.已封切完成
     *
     * @return detail_statue - 状态，1未处理 ，2未处理吹膜，3已吹膜处理 4.已吹膜完成 5.未处理封切，6已封切处理 7.已封切完成
     */
    public Integer getDetailStatue() {
        return detailStatue;
    }

    /**
     * 设置状态，1未处理 ，2未处理吹膜，3已吹膜处理 4.已吹膜完成 5.未处理封切，6已封切处理 7.已封切完成
     *
     * @param detailStatue 状态，1未处理 ，2未处理吹膜，3已吹膜处理 4.已吹膜完成 5.未处理封切，6已封切处理 7.已封切完成
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

    /**
     * 获取吹膜机器id
     *
     * @return blown_machine_id - 吹膜机器id
     */
    public String getBlownMachineId() {
        return blownMachineId;
    }

    /**
     * 设置吹膜机器id
     *
     * @param blownMachineId 吹膜机器id
     */
    public void setBlownMachineId(String blownMachineId) {
        this.blownMachineId = blownMachineId;
    }

    /**
     * 获取吹膜机器名称
     *
     * @return blown_machine_name - 吹膜机器名称
     */
    public String getBlownMachineName() {
        return blownMachineName;
    }

    /**
     * 设置吹膜机器名称
     *
     * @param blownMachineName 吹膜机器名称
     */
    public void setBlownMachineName(String blownMachineName) {
        this.blownMachineName = blownMachineName;
    }

    /**
     * @return blown_time
     */
    public Date getBlownTime() {
        return blownTime;
    }

    /**
     * @param blownTime
     */
    public void setBlownTime(Date blownTime) {
        this.blownTime = blownTime;
    }

    /**
     * 获取封切机器id
     *
     * @return cut_machine_id - 封切机器id
     */
    public String getCutMachineId() {
        return cutMachineId;
    }

    /**
     * 设置封切机器id
     *
     * @param cutMachineId 封切机器id
     */
    public void setCutMachineId(String cutMachineId) {
        this.cutMachineId = cutMachineId;
    }

    /**
     * 获取封切机器名称
     *
     * @return cut_machine_name - 封切机器名称
     */
    public String getCutMachineName() {
        return cutMachineName;
    }

    /**
     * 设置封切机器名称
     *
     * @param cutMachineName 封切机器名称
     */
    public void setCutMachineName(String cutMachineName) {
        this.cutMachineName = cutMachineName;
    }

    /**
     * @return cut_time
     */
    public Date getCutTime() {
        return cutTime;
    }

    /**
     * @param cutTime
     */
    public void setCutTime(Date cutTime) {
        this.cutTime = cutTime;
    }

    /**
     * 获取实际生产重量
     *
     * @return actual_weight - 实际生产重量
     */
    public BigDecimal getActualWeight() {
        return actualWeight;
    }

    /**
     * 设置实际生产重量
     *
     * @param actualWeight 实际生产重量
     */
    public void setActualWeight(BigDecimal actualWeight) {
        this.actualWeight = actualWeight;
    }

    /**
     * 生产明细id
     */
	public String getProduceDetailId() {
		return produceDetailId;
	}

    /**
     * 生产明细id
     */
	public void setProduceDetailId(String produceDetailId) {
		this.produceDetailId = produceDetailId;
	}
    
    
}