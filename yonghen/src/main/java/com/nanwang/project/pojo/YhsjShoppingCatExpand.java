package com.nanwang.project.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

public class YhsjShoppingCatExpand {

	/**
     * 购物车id
     */
    private String shopping_cat_id;

    /**
     * 商品id
     */
    private String pro_id;

    /**
     * 商品名称
     */
    private String pro_name;

    /**
     * 商品价格
     */
    private BigDecimal pro_price;

    /**
     * 购买数量
     */
    private Integer pro_num;

    /**
     * 商品重量
     */
    private BigDecimal pro_weight;

    /**
     * 规格id
     */
    private String scale_id;

    /**
     * 规格名称
     */
    private String scale_label;

    /**
     * 0未选择 1是选中提交
     */
    private Integer shopping_cat_state;

    /**
     * 创建时间
     */
    private Date shopping_cat_create_time;

    /**
     * 创建用户id
     */
    private String shopping_cat_create_user_id;

    /**
     * 公司id
     */
    private String company_id;

    /**
     * 公司名称
     */
    private String company_name;
    
    /**
     * 生产明细id
     */
    private String produce_detail_id;

	public String getShopping_cat_id() {
		return shopping_cat_id;
	}

	public void setShopping_cat_id(String shopping_cat_id) {
		this.shopping_cat_id = shopping_cat_id;
	}

	public String getPro_id() {
		return pro_id;
	}

	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public BigDecimal getPro_price() {
		return pro_price;
	}

	public void setPro_price(BigDecimal pro_price) {
		this.pro_price = pro_price;
	}

	public Integer getPro_num() {
		return pro_num;
	}

	public void setPro_num(Integer pro_num) {
		this.pro_num = pro_num;
	}

	public BigDecimal getPro_weight() {
		return pro_weight;
	}

	public void setPro_weight(BigDecimal pro_weight) {
		this.pro_weight = pro_weight;
	}

	public String getScale_id() {
		return scale_id;
	}

	public void setScale_id(String scale_id) {
		this.scale_id = scale_id;
	}

	public String getScale_label() {
		return scale_label;
	}

	public void setScale_label(String scale_label) {
		this.scale_label = scale_label;
	}

	public Integer getShopping_cat_state() {
		return shopping_cat_state;
	}

	public void setShopping_cat_state(Integer shopping_cat_state) {
		this.shopping_cat_state = shopping_cat_state;
	}

	public Date getShopping_cat_create_time() {
		return shopping_cat_create_time;
	}

	public void setShopping_cat_create_time(Date shopping_cat_create_time) {
		this.shopping_cat_create_time = shopping_cat_create_time;
	}

	public String getShopping_cat_create_user_id() {
		return shopping_cat_create_user_id;
	}

	public void setShopping_cat_create_user_id(String shopping_cat_create_user_id) {
		this.shopping_cat_create_user_id = shopping_cat_create_user_id;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getProduce_detail_id() {
		return produce_detail_id;
	}

	public void setProduce_detail_id(String produce_detail_id) {
		this.produce_detail_id = produce_detail_id;
	}
	
	
}
