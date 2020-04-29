package com.sanposs.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_shopping_cat")
public class YhsjShoppingCat implements Serializable{
    /**
     * 购物车id
     */
    @Id
    @Column(name = "shopping_cat_id")
    private String shoppingCatId;

    /**
     * 商品id
     */
    @Column(name = "pro_id")
    private String proId;

    /**
     * 商品名称
     */
    @Column(name = "pro_name")
    private String proName;

    /**
     * 商品价格
     */
    @Column(name = "pro_price")
    private BigDecimal proPrice;

    /**
     * 购买数量
     */
    @Column(name = "pro_num")
    private Integer proNum;

    /**
     * 商品重量
     */
    @Column(name = "pro_weight")
    private BigDecimal proWeight;

    /**
     * 规格id
     */
    @Column(name = "scale_id")
    private String scaleId;

    /**
     * 规格名称
     */
    @Column(name = "scale_label")
    private String scaleLabel;

    /**
     * 0未选择 1是选中提交
     */
    @Column(name = "shopping_cat_state")
    private Integer shoppingCatState;

    /**
     * 创建时间
     */
    @Column(name = "shopping_cat_create_time")
    private Date shoppingCatCreateTime;

    /**
     * 创建用户id
     */
    @Column(name = "shopping_cat_create_user_id")
    private String shoppingCatCreateUserId;

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
     * 生产明细id
     */
    @Column(name = "produce_detail_id")
    private String produceDetailId;

    /**
     * 获取购物车id
     *
     * @return shopping_cat_id - 购物车id
     */
    public String getShoppingCatId() {
        return shoppingCatId;
    }

    /**
     * 设置购物车id
     *
     * @param shoppingCatId 购物车id
     */
    public void setShoppingCatId(String shoppingCatId) {
        this.shoppingCatId = shoppingCatId;
    }

    /**
     * 获取商品id
     *
     * @return pro_id - 商品id
     */
    public String getProId() {
        return proId;
    }

    /**
     * 设置商品id
     *
     * @param proId 商品id
     */
    public void setProId(String proId) {
        this.proId = proId;
    }

    /**
     * 获取商品名称
     *
     * @return pro_name - 商品名称
     */
    public String getProName() {
        return proName;
    }

    /**
     * 设置商品名称
     *
     * @param proName 商品名称
     */
    public void setProName(String proName) {
        this.proName = proName;
    }

    /**
     * 获取商品价格
     *
     * @return pro_price - 商品价格
     */
    public BigDecimal getProPrice() {
        return proPrice;
    }

    /**
     * 设置商品价格
     *
     * @param proPrice 商品价格
     */
    public void setProPrice(BigDecimal proPrice) {
        this.proPrice = proPrice;
    }

    /**
     * 获取购买数量
     *
     * @return pro_num - 购买数量
     */
    public Integer getProNum() {
        return proNum;
    }

    /**
     * 设置购买数量
     *
     * @param proNum 购买数量
     */
    public void setProNum(Integer proNum) {
        this.proNum = proNum;
    }

    /**
     * 获取商品重量
     *
     * @return pro_weight - 商品重量
     */
    public BigDecimal getProWeight() {
        return proWeight;
    }

    /**
     * 设置商品重量
     *
     * @param proWeight 商品重量
     */
    public void setProWeight(BigDecimal proWeight) {
        this.proWeight = proWeight;
    }

    /**
     * 获取规格id
     *
     * @return scale_id - 规格id
     */
    public String getScaleId() {
        return scaleId;
    }

    /**
     * 设置规格id
     *
     * @param scaleId 规格id
     */
    public void setScaleId(String scaleId) {
        this.scaleId = scaleId;
    }

    /**
     * 获取规格名称
     *
     * @return scale_label - 规格名称
     */
    public String getScaleLabel() {
        return scaleLabel;
    }

    /**
     * 设置规格名称
     *
     * @param scaleLabel 规格名称
     */
    public void setScaleLabel(String scaleLabel) {
        this.scaleLabel = scaleLabel;
    }

    /**
     * 获取0未选择 1是选中提交
     *
     * @return shopping_cat_state - 0未选择 1是选中提交
     */
    public Integer getShoppingCatState() {
        return shoppingCatState;
    }

    /**
     * 设置0未选择 1是选中提交
     *
     * @param shoppingCatState 0未选择 1是选中提交
     */
    public void setShoppingCatState(Integer shoppingCatState) {
        this.shoppingCatState = shoppingCatState;
    }

    /**
     * 获取创建时间
     *
     * @return shopping_cat_create_time - 创建时间
     */
    public Date getShoppingCatCreateTime() {
        return shoppingCatCreateTime;
    }

    /**
     * 设置创建时间
     *
     * @param shoppingCatCreateTime 创建时间
     */
    public void setShoppingCatCreateTime(Date shoppingCatCreateTime) {
        this.shoppingCatCreateTime = shoppingCatCreateTime;
    }

    /**
     * 获取创建用户id
     *
     * @return shopping_cat_create_user_id - 创建用户id
     */
    public String getShoppingCatCreateUserId() {
        return shoppingCatCreateUserId;
    }

    /**
     * 设置创建用户id
     *
     * @param shoppingCatCreateUserId 创建用户id
     */
    public void setShoppingCatCreateUserId(String shoppingCatCreateUserId) {
        this.shoppingCatCreateUserId = shoppingCatCreateUserId;
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

    /**
     * 获取生产明细id
     *
     * @return produce_detail_id - 生产明细id
     */
    public String getProduceDetailId() {
        return produceDetailId;
    }

    /**
     * 设置生产明细id
     *
     * @param produceDetailId 生产明细id
     */
    public void setProduceDetailId(String produceDetailId) {
        this.produceDetailId = produceDetailId;
    }
}