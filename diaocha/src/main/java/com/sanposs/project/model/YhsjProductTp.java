package com.sanposs.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_product_tp")
public class YhsjProductTp implements Serializable{
    @Id
    @Column(name = "tp_id")
    private String tpId;

    /**
     * 规格名称 
     */
    @Column(name = "tp_name")
    private String tpName;

    /**
     * 会员价
     */
    @Column(name = "tp_sale_price")
    private BigDecimal tpSalePrice;

    /**
     * 非会员价
     */
    @Column(name = "tp_price")
    private BigDecimal tpPrice;

    /**
     * 库存
     */
    @Column(name = "tp_num")
    private Integer tpNum;

    /**
     * 成本价
     */
    @Column(name = "tp_cost_price")
    private BigDecimal tpCostPrice;

    /**
     * 重量
     */
    @Column(name = "tp_weight")
    private String tpWeight;

    /**
     * 编码
     */
    @Column(name = "tp_no")
    private String tpNo;

    /**
     * 创建时间
     */
    @Column(name = "tp_create_time")
    private Date tpCreateTime;

    /**
     * 创建人
     */
    @Column(name = "tp_create_user")
    private String tpCreateUser;

    @Column(name = "tp_remark")
    private String tpRemark;

    /**
     * 产品id
     */
    @Column(name = "pro_id")
    private String proId;

    @Column(name = "tp_sort")
    private Integer tpSort;

    /**
     * @return tp_id
     */
    public String getTpId() {
        return tpId;
    }

    /**
     * @param tpId
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
     * 获取会员价
     *
     * @return tp_sale_price - 会员价
     */
    public BigDecimal getTpSalePrice() {
        return tpSalePrice;
    }

    /**
     * 设置会员价
     *
     * @param tpSalePrice 会员价
     */
    public void setTpSalePrice(BigDecimal tpSalePrice) {
        this.tpSalePrice = tpSalePrice;
    }

    /**
     * 获取非会员价
     *
     * @return tp_price - 非会员价
     */
    public BigDecimal getTpPrice() {
        return tpPrice;
    }

    /**
     * 设置非会员价
     *
     * @param tpPrice 非会员价
     */
    public void setTpPrice(BigDecimal tpPrice) {
        this.tpPrice = tpPrice;
    }

    /**
     * 获取库存
     *
     * @return tp_num - 库存
     */
    public Integer getTpNum() {
        return tpNum;
    }

    /**
     * 设置库存
     *
     * @param tpNum 库存
     */
    public void setTpNum(Integer tpNum) {
        this.tpNum = tpNum;
    }

    /**
     * 获取成本价
     *
     * @return tp_cost_price - 成本价
     */
    public BigDecimal getTpCostPrice() {
        return tpCostPrice;
    }

    /**
     * 设置成本价
     *
     * @param tpCostPrice 成本价
     */
    public void setTpCostPrice(BigDecimal tpCostPrice) {
        this.tpCostPrice = tpCostPrice;
    }

    /**
     * 获取重量
     *
     * @return tp_weight - 重量
     */
    public String getTpWeight() {
        return tpWeight;
    }

    /**
     * 设置重量
     *
     * @param tpWeight 重量
     */
    public void setTpWeight(String tpWeight) {
        this.tpWeight = tpWeight;
    }

    /**
     * 获取编码
     *
     * @return tp_no - 编码
     */
    public String getTpNo() {
        return tpNo;
    }

    /**
     * 设置编码
     *
     * @param tpNo 编码
     */
    public void setTpNo(String tpNo) {
        this.tpNo = tpNo;
    }

    /**
     * 获取创建时间
     *
     * @return tp_create_time - 创建时间
     */
    public Date getTpCreateTime() {
        return tpCreateTime;
    }

    /**
     * 设置创建时间
     *
     * @param tpCreateTime 创建时间
     */
    public void setTpCreateTime(Date tpCreateTime) {
        this.tpCreateTime = tpCreateTime;
    }

    /**
     * 获取创建人
     *
     * @return tp_create_user - 创建人
     */
    public String getTpCreateUser() {
        return tpCreateUser;
    }

    /**
     * 设置创建人
     *
     * @param tpCreateUser 创建人
     */
    public void setTpCreateUser(String tpCreateUser) {
        this.tpCreateUser = tpCreateUser;
    }

    /**
     * @return tp_remark
     */
    public String getTpRemark() {
        return tpRemark;
    }

    /**
     * @param tpRemark
     */
    public void setTpRemark(String tpRemark) {
        this.tpRemark = tpRemark;
    }

    /**
     * 获取产品id
     *
     * @return pro_id - 产品id
     */
    public String getProId() {
        return proId;
    }

    /**
     * 设置产品id
     *
     * @param proId 产品id
     */
    public void setProId(String proId) {
        this.proId = proId;
    }

    /**
     * @return tp_sort
     */
    public Integer getTpSort() {
        return tpSort;
    }

    /**
     * @param tpSort
     */
    public void setTpSort(Integer tpSort) {
        this.tpSort = tpSort;
    }
}