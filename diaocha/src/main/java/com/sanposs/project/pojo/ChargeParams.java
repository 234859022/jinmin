package com.sanposs.project.pojo;

import com.sanposs.project.utils.BaseParams;

/**
 * 充值完成传递对象
 */
public class ChargeParams  extends BaseParams {

    /**
     * 订单支付方  微信充值的账户
     */
    private String orderpayeraccount;

    /**
     * 关联充值流水记录
     */
    private String chargflow;

    /**
     * 订单支付金额
     */
    private Double orderpaymoney;

    /**
     * 订单名称
     */
    private String ordername;

    /**
     * 创建用户
     */
    private String parkuserid;

    /**
     * 内容信息
     */
    private String content;
    public String getOrderpayeraccount() {
        return orderpayeraccount;
    }

    public void setOrderpayeraccount(String orderpayeraccount) {
        this.orderpayeraccount = orderpayeraccount;
    }

    public String getChargflow() {
        return chargflow;
    }

    public void setChargflow(String chargflow) {
        this.chargflow = chargflow;
    }

    public Double getOrderpaymoney() {
        return orderpaymoney;
    }

    public void setOrderpaymoney(Double orderpaymoney) {
        this.orderpaymoney = orderpaymoney;
    }

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public String getParkuserid() {
        return parkuserid;
    }

    public void setParkuserid(String parkuserid) {
        this.parkuserid = parkuserid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
