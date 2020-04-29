package com.sanposs.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_product")
public class YhsjProduct implements Serializable{
    /**
     * 产品主键
     */
    @Id
    private String proId;

    /**
     * 产品名称
     */
    @Column(name = "pro_name")
    private String proName;

    /**
     * 产品价格
     */
    @Column(name = "pro_price")
    private BigDecimal proPrice;

    /**
     * 产品促销价
     */
    @Column(name = "pro_sale_price")
    private BigDecimal proSalePrice;

    /**
     * 产品样本小图片路径
     */
    @Column(name = "pro_min_img_url")
    private String proMinImgUrl;

    /**
     * 产品样本大图片路径
     */
    @Column(name = "pro_max_img_url")
    private String proMaxImgUrl;

    /**
     * 产品库存
     */
    @Column(name = "pro_num")
    private Integer proNum;

    /**
     * 产品父分类
     */
    @Column(name = "pro_type_parent")
    private String proTypeParent;

    /**
     * 产品分类
     */
    @Column(name = "pro_type")
    private String proType;

    /**
     * 0为不显示,1为显示
     */
    @Column(name = "pro_is_show")
    private Integer proIsShow;

    /**
     * 0为删除，1为未删除
     */
    @Column(name = "pro_is_delete")
    private Integer proIsDelete;

    /**
     * 产品生产场地
     */
    @Column(name = "factory_area")
    private String factoryArea;

    /**
     * 产品创建人
     */
    @Column(name = "create_user_id")
    private String createUserId;

    /**
     * 产品创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 产品排序
     */
    @Column(name = "pro_sort")
    private String proSort;

    /**
     * 月销量
     */
    @Column(name = "pro_month_count")
    private Integer proMonthCount;

    /**
     * 评价数
     */
    @Column(name = "pro_evaluate_count")
    private Integer proEvaluateCount;

    /**
     * 收藏量
     */
    @Column(name = "pro_collections")
    private Integer proCollections;

    /**
     * 赠送积分
     */
    @Column(name = "pro_points")
    private Integer proPoints;

    /**
     * 产品详情
     */
    @Column(name = "pro_detail_detail_remark")
    private String proDetailDetailRemark;

    @Column(name = "pro_is_member")
    private Integer proIsMember;

    @Column(name = "pro_profit")
    private BigDecimal proProfit;

    /**
     * 商品简介
     */
    @Column(name = "pro_summary")
    private String proSummary;

    /**
     * 总销售量
     */
    @Column(name = "pro_total_count")
    private Integer proTotalCount;

    /**
     * 产品详情图片
     */
    @Column(name = "pro_detail_img")
    private String proDetailImg;

    /**
     * 是否推荐
     */
    @Column(name = "pro_is_nominate")
    private Integer proIsNominate;

    /**
     * 运费模板id
     */
    @Column(name = "template_id")
    private String templateId;

    /**
     * 重量
     */
    @Column(name = "pro_weight")
    private String proWeight;

    /**
     * 获取产品主键
     *
     * @return pro_id - 产品主键
     */
    public String getProId() {
        return proId;
    }

    /**
     * 设置产品主键
     *
     * @param proId 产品主键
     */
    public void setProId(String proId) {
        this.proId = proId;
    }

    /**
     * 获取产品名称
     *
     * @return pro_name - 产品名称
     */
    public String getProName() {
        return proName;
    }

    /**
     * 设置产品名称
     *
     * @param proName 产品名称
     */
    public void setProName(String proName) {
        this.proName = proName;
    }

    /**
     * 获取产品价格
     *
     * @return pro_price - 产品价格
     */
    public BigDecimal getProPrice() {
        return proPrice;
    }

    /**
     * 设置产品价格
     *
     * @param proPrice 产品价格
     */
    public void setProPrice(BigDecimal proPrice) {
        this.proPrice = proPrice;
    }

    /**
     * 获取产品促销价
     *
     * @return pro_sale_price - 产品促销价
     */
    public BigDecimal getProSalePrice() {
        return proSalePrice;
    }

    /**
     * 设置产品促销价
     *
     * @param proSalePrice 产品促销价
     */
    public void setProSalePrice(BigDecimal proSalePrice) {
        this.proSalePrice = proSalePrice;
    }

    /**
     * 获取产品样本小图片路径
     *
     * @return pro_min_img_url - 产品样本小图片路径
     */
    public String getProMinImgUrl() {
        return proMinImgUrl;
    }

    /**
     * 设置产品样本小图片路径
     *
     * @param proMinImgUrl 产品样本小图片路径
     */
    public void setProMinImgUrl(String proMinImgUrl) {
        this.proMinImgUrl = proMinImgUrl;
    }

    /**
     * 获取产品样本大图片路径
     *
     * @return pro_max_img_url - 产品样本大图片路径
     */
    public String getProMaxImgUrl() {
        return proMaxImgUrl;
    }

    /**
     * 设置产品样本大图片路径
     *
     * @param proMaxImgUrl 产品样本大图片路径
     */
    public void setProMaxImgUrl(String proMaxImgUrl) {
        this.proMaxImgUrl = proMaxImgUrl;
    }

    /**
     * 获取产品库存
     *
     * @return pro_num - 产品库存
     */
    public Integer getProNum() {
        return proNum;
    }

    /**
     * 设置产品库存
     *
     * @param proNum 产品库存
     */
    public void setProNum(Integer proNum) {
        this.proNum = proNum;
    }

    /**
     * 获取产品父分类
     *
     * @return pro_type_parent - 产品父分类
     */
    public String getProTypeParent() {
        return proTypeParent;
    }

    /**
     * 设置产品父分类
     *
     * @param proTypeParent 产品父分类
     */
    public void setProTypeParent(String proTypeParent) {
        this.proTypeParent = proTypeParent;
    }

    /**
     * 获取产品分类
     *
     * @return pro_type - 产品分类
     */
    public String getProType() {
        return proType;
    }

    /**
     * 设置产品分类
     *
     * @param proType 产品分类
     */
    public void setProType(String proType) {
        this.proType = proType;
    }

    /**
     * 获取0为不显示,1为显示
     *
     * @return pro_is_show - 0为不显示,1为显示
     */
    public Integer getProIsShow() {
        return proIsShow;
    }

    /**
     * 设置0为不显示,1为显示
     *
     * @param proIsShow 0为不显示,1为显示
     */
    public void setProIsShow(Integer proIsShow) {
        this.proIsShow = proIsShow;
    }

    /**
     * 获取0为删除，1为未删除
     *
     * @return pro_is_delete - 0为删除，1为未删除
     */
    public Integer getProIsDelete() {
        return proIsDelete;
    }

    /**
     * 设置0为删除，1为未删除
     *
     * @param proIsDelete 0为删除，1为未删除
     */
    public void setProIsDelete(Integer proIsDelete) {
        this.proIsDelete = proIsDelete;
    }

    /**
     * 获取产品生产场地
     *
     * @return factory_area - 产品生产场地
     */
    public String getFactoryArea() {
        return factoryArea;
    }

    /**
     * 设置产品生产场地
     *
     * @param factoryArea 产品生产场地
     */
    public void setFactoryArea(String factoryArea) {
        this.factoryArea = factoryArea;
    }

    /**
     * 获取产品创建人
     *
     * @return create_user_id - 产品创建人
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置产品创建人
     *
     * @param createUserId 产品创建人
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取产品创建时间
     *
     * @return create_date - 产品创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置产品创建时间
     *
     * @param createDate 产品创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取产品排序
     *
     * @return pro_sort - 产品排序
     */
    public String getProSort() {
        return proSort;
    }

    /**
     * 设置产品排序
     *
     * @param proSort 产品排序
     */
    public void setProSort(String proSort) {
        this.proSort = proSort;
    }

    /**
     * 获取月销量
     *
     * @return pro_month_count - 月销量
     */
    public Integer getProMonthCount() {
        return proMonthCount;
    }

    /**
     * 设置月销量
     *
     * @param proMonthCount 月销量
     */
    public void setProMonthCount(Integer proMonthCount) {
        this.proMonthCount = proMonthCount;
    }

    /**
     * 获取评价数
     *
     * @return pro_evaluate_count - 评价数
     */
    public Integer getProEvaluateCount() {
        return proEvaluateCount;
    }

    /**
     * 设置评价数
     *
     * @param proEvaluateCount 评价数
     */
    public void setProEvaluateCount(Integer proEvaluateCount) {
        this.proEvaluateCount = proEvaluateCount;
    }

    /**
     * 获取收藏量
     *
     * @return pro_collections - 收藏量
     */
    public Integer getProCollections() {
        return proCollections;
    }

    /**
     * 设置收藏量
     *
     * @param proCollections 收藏量
     */
    public void setProCollections(Integer proCollections) {
        this.proCollections = proCollections;
    }

    /**
     * 获取赠送积分
     *
     * @return pro_points - 赠送积分
     */
    public Integer getProPoints() {
        return proPoints;
    }

    /**
     * 设置赠送积分
     *
     * @param proPoints 赠送积分
     */
    public void setProPoints(Integer proPoints) {
        this.proPoints = proPoints;
    }

    /**
     * 获取产品详情
     *
     * @return pro_detail_detail_remark - 产品详情
     */
    public String getProDetailDetailRemark() {
        return proDetailDetailRemark;
    }

    /**
     * 设置产品详情
     *
     * @param proDetailDetailRemark 产品详情
     */
    public void setProDetailDetailRemark(String proDetailDetailRemark) {
        this.proDetailDetailRemark = proDetailDetailRemark;
    }

    /**
     * @return pro_is_member
     */
    public Integer getProIsMember() {
        return proIsMember;
    }

    /**
     * @param proIsMember
     */
    public void setProIsMember(Integer proIsMember) {
        this.proIsMember = proIsMember;
    }

    /**
     * @return pro_profit
     */
    public BigDecimal getProProfit() {
        return proProfit;
    }

    /**
     * @param proProfit
     */
    public void setProProfit(BigDecimal proProfit) {
        this.proProfit = proProfit;
    }

    /**
     * 获取商品简介
     *
     * @return pro_summary - 商品简介
     */
    public String getProSummary() {
        return proSummary;
    }

    /**
     * 设置商品简介
     *
     * @param proSummary 商品简介
     */
    public void setProSummary(String proSummary) {
        this.proSummary = proSummary;
    }

    /**
     * 获取总销售量
     *
     * @return pro_total_count - 总销售量
     */
    public Integer getProTotalCount() {
        return proTotalCount;
    }

    /**
     * 设置总销售量
     *
     * @param proTotalCount 总销售量
     */
    public void setProTotalCount(Integer proTotalCount) {
        this.proTotalCount = proTotalCount;
    }

    /**
     * 获取产品详情图片
     *
     * @return pro_detail_img - 产品详情图片
     */
    public String getProDetailImg() {
        return proDetailImg;
    }

    /**
     * 设置产品详情图片
     *
     * @param proDetailImg 产品详情图片
     */
    public void setProDetailImg(String proDetailImg) {
        this.proDetailImg = proDetailImg;
    }

    /**
     * 获取是否推荐
     *
     * @return pro_is_nominate - 是否推荐
     */
    public Integer getProIsNominate() {
        return proIsNominate;
    }

    /**
     * 设置是否推荐
     *
     * @param proIsNominate 是否推荐
     */
    public void setProIsNominate(Integer proIsNominate) {
        this.proIsNominate = proIsNominate;
    }

    /**
     * 获取运费模板id
     *
     * @return template_id - 运费模板id
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * 设置运费模板id
     *
     * @param templateId 运费模板id
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * 获取重量
     *
     * @return pro_weight - 重量
     */
    public String getProWeight() {
        return proWeight;
    }

    /**
     * 设置重量
     *
     * @param proWeight 重量
     */
    public void setProWeight(String proWeight) {
        this.proWeight = proWeight;
    }
}