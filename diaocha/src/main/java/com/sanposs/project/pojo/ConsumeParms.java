package com.sanposs.project.pojo;

import com.sanposs.project.utils.BaseParams;

/**
 * Created by Administrator on 2018/12/21/021.
 * 消费pojo
 */
public class ConsumeParms extends BaseParams {

    /**
     * 创建用户
     */
    private String parkuserid;

    /**
     * 停车位ID
     */
    private String ParkPlaceId;

    /**
     * 车牌
     */
    private String ParkCarNo;

    /**
     * 订单支付金额
     */
    private Double orderpaymoney;


    /**
     * 订单名称
     */
    private String ordername;

    public String getParkuserid() {
        return parkuserid;
    }

    public void setParkuserid(String parkuserid) {
        this.parkuserid = parkuserid;
    }

    public String getParkPlaceId() {
        return ParkPlaceId;
    }

    public void setParkPlaceId(String parkPlaceId) {
        ParkPlaceId = parkPlaceId;
    }

    public String getParkCarNo() {
        return ParkCarNo;
    }

    public void setParkCarNo(String parkCarNo) {
        ParkCarNo = parkCarNo;
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
}
