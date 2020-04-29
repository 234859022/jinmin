package com.nanwang.project.pojo;

import javax.persistence.Column;
import java.util.Date;

/**
 * 用户停车记录
 */
public class ParkingLogParams {
    /**
     *
     */
    private static final long serialVersionUID = 9193604856602157888L;

    /**
     * 进场时间
     */
    private String parkdate;

    /**
     * 出场时间
     */
    private String leavedate;

    /**
     * 车位名称
     */
    private String placename;


    /**
     * 具体位置
     */
    private String placeaddr;

    /**
     * 创建日期
     */
    private String createdate;

    /**
     * 关联车辆ID
     */
    private String carid;

    /**
     * 车牌号
     */
    private String platenum;


    /**
     * 所属用户ID
     */
    private String userid;


    /**
     * 所属租户ID
     */
    private String rentuserid;

    /**
     * 所属车锁ID
     */
    private String lockid;

    /**
     * 停车状态 1 停车中状态，2 停车结束
     */
    private Integer logstate;

    public String getParkdate() {
        return parkdate;
    }

    public void setParkdate(String parkdate) {
        this.parkdate = parkdate;
    }

    public String getLeavedate() {
        return leavedate;
    }

    public void setLeavedate(String leavedate) {
        this.leavedate = leavedate;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getPlaceaddr() {
        return placeaddr;
    }

    public void setPlaceaddr(String placeaddr) {
        this.placeaddr = placeaddr;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getPlatenum() {
        return platenum;
    }

    public void setPlatenum(String platenum) {
        this.platenum = platenum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRentuserid() {
        return rentuserid;
    }

    public void setRentuserid(String rentuserid) {
        this.rentuserid = rentuserid;
    }

    public String getLockid() {
        return lockid;
    }

    public void setLockid(String lockid) {
        this.lockid = lockid;
    }

    public Integer getLogstate() {
        return logstate;
    }

    public void setLogstate(Integer logstate) {
        this.logstate = logstate;
    }



}
