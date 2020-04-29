package com.sanposs.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_produce_detail")
public class YhsjProduceDetail {
    /**
     * 主键
     */
    @Id
    @Column(name = "produce_detail_id")
    private String produceDetailId;

    /**
     * 规格id
     */
    @Column(name = "produce_detail_spec_id")
    private String produceDetailSpecId;

    /**
     * 规格名称
     */
    @Column(name = "produce_detail_spec_name")
    private String produceDetailSpecName;

    /**
     * 客户id
     */
    @Column(name = "produce_detail_user_id")
    private String produceDetailUserId;

    /**
     * 客户名称
     */
    @Column(name = "produce_detail_user_name")
    private String produceDetailUserName;

    /**
     * 颜色
     */
    @Column(name = "produce_detail_color_type")
    private String produceDetailColorType;

    /**
     * 机器id
     */
    @Column(name = "produce_detail_machine_id")
    private String produceDetailMachineId;

    /**
     * 机器名称
     */
    @Column(name = "produce_detail_machine_name")
    private String produceDetailMachineName;

    /**
     * 切
     */
    @Column(name = "produce_detail_qie")
    private String produceDetailQie;

    /**
     * 个
     */
    @Column(name = "produce_detail_ge")
    private String produceDetailGe;

    /**
     * 重量及数量
     */
    @Column(name = "produce_detail_sl_zl")
    private String produceDetailSlZl;

    /**
     * 吹塑重量
     */
    @Column(name = "produce_detail_cszl")
    private String produceDetailCszl;

    /**
     * 产量
     */
    @Column(name = "produce_detail_cl")
    private String produceDetailCl;

    /**
     * 小包
     */
    @Column(name = "produce_detail_xb")
    private String produceDetailXb;

    /**
     * 大包
     */
    @Column(name = "produce_detail_db")
    private String produceDetailDb;

    /**
     * 写
     */
    @Column(name = "produce_detail_xie")
    private String produceDetailXie;

    /**
     * 冲刀
     */
    @Column(name = "produce_detail_cd")
    private String produceDetailCd;

    /**
     * 配料
     */
    @Column(name = "produce_detail_pl")
    private String produceDetailPl;

    @Column(name = "produce_detail_create_time")
    private Date produceDetailCreateTime;

    @Column(name = "produce_detail_create_user_id")
    private String produceDetailCreateUserId;

    /**
     * 生产日期月份
     */
    @Column(name = "produce_detail_month")
    private String produceDetailMonth;

    /**
     * 生产日期日期
     */
    @Column(name = "produce_detail_day")
    private String produceDetailDay;

    /**
     * 获取主键
     *
     * @return produce_detail_id - 主键
     */
    public String getProduceDetailId() {
        return produceDetailId;
    }

    /**
     * 设置主键
     *
     * @param produceDetailId 主键
     */
    public void setProduceDetailId(String produceDetailId) {
        this.produceDetailId = produceDetailId;
    }

    /**
     * 获取规格id
     *
     * @return produce_detail_spec_id - 规格id
     */
    public String getProduceDetailSpecId() {
        return produceDetailSpecId;
    }

    /**
     * 设置规格id
     *
     * @param produceDetailSpecId 规格id
     */
    public void setProduceDetailSpecId(String produceDetailSpecId) {
        this.produceDetailSpecId = produceDetailSpecId;
    }

    /**
     * 获取规格名称
     *
     * @return produce_detail_spec_name - 规格名称
     */
    public String getProduceDetailSpecName() {
        return produceDetailSpecName;
    }

    /**
     * 设置规格名称
     *
     * @param produceDetailSpecName 规格名称
     */
    public void setProduceDetailSpecName(String produceDetailSpecName) {
        this.produceDetailSpecName = produceDetailSpecName;
    }

    /**
     * 获取客户id
     *
     * @return produce_detail_user_id - 客户id
     */
    public String getProduceDetailUserId() {
        return produceDetailUserId;
    }

    /**
     * 设置客户id
     *
     * @param produceDetailUserId 客户id
     */
    public void setProduceDetailUserId(String produceDetailUserId) {
        this.produceDetailUserId = produceDetailUserId;
    }

    /**
     * 获取客户名称
     *
     * @return produce_detail_user_name - 客户名称
     */
    public String getProduceDetailUserName() {
        return produceDetailUserName;
    }

    /**
     * 设置客户名称
     *
     * @param produceDetailUserName 客户名称
     */
    public void setProduceDetailUserName(String produceDetailUserName) {
        this.produceDetailUserName = produceDetailUserName;
    }

    /**
     * 获取颜色
     *
     * @return produce_detail_color_type - 颜色
     */
    public String getProduceDetailColorType() {
        return produceDetailColorType;
    }

    /**
     * 设置颜色
     *
     * @param produceDetailColorType 颜色
     */
    public void setProduceDetailColorType(String produceDetailColorType) {
        this.produceDetailColorType = produceDetailColorType;
    }

    /**
     * 获取机器id
     *
     * @return produce_detail_machine_id - 机器id
     */
    public String getProduceDetailMachineId() {
        return produceDetailMachineId;
    }

    /**
     * 设置机器id
     *
     * @param produceDetailMachineId 机器id
     */
    public void setProduceDetailMachineId(String produceDetailMachineId) {
        this.produceDetailMachineId = produceDetailMachineId;
    }

    /**
     * 获取机器名称
     *
     * @return produce_detail_machine_name - 机器名称
     */
    public String getProduceDetailMachineName() {
        return produceDetailMachineName;
    }

    /**
     * 设置机器名称
     *
     * @param produceDetailMachineName 机器名称
     */
    public void setProduceDetailMachineName(String produceDetailMachineName) {
        this.produceDetailMachineName = produceDetailMachineName;
    }

    /**
     * 获取切
     *
     * @return produce_detail_qie - 切
     */
    public String getProduceDetailQie() {
        return produceDetailQie;
    }

    /**
     * 设置切
     *
     * @param produceDetailQie 切
     */
    public void setProduceDetailQie(String produceDetailQie) {
        this.produceDetailQie = produceDetailQie;
    }

    /**
     * 获取个
     *
     * @return produce_detail_ge - 个
     */
    public String getProduceDetailGe() {
        return produceDetailGe;
    }

    /**
     * 设置个
     *
     * @param produceDetailGe 个
     */
    public void setProduceDetailGe(String produceDetailGe) {
        this.produceDetailGe = produceDetailGe;
    }

    /**
     * 获取重量及数量
     *
     * @return produce_detail_sl_zl - 重量及数量
     */
    public String getProduceDetailSlZl() {
        return produceDetailSlZl;
    }

    /**
     * 设置重量及数量
     *
     * @param produceDetailSlZl 重量及数量
     */
    public void setProduceDetailSlZl(String produceDetailSlZl) {
        this.produceDetailSlZl = produceDetailSlZl;
    }

    /**
     * 获取吹塑重量
     *
     * @return produce_detail_cszl - 吹塑重量
     */
    public String getProduceDetailCszl() {
        return produceDetailCszl;
    }

    /**
     * 设置吹塑重量
     *
     * @param produceDetailCszl 吹塑重量
     */
    public void setProduceDetailCszl(String produceDetailCszl) {
        this.produceDetailCszl = produceDetailCszl;
    }

    /**
     * 获取产量
     *
     * @return produce_detail_cl - 产量
     */
    public String getProduceDetailCl() {
        return produceDetailCl;
    }

    /**
     * 设置产量
     *
     * @param produceDetailCl 产量
     */
    public void setProduceDetailCl(String produceDetailCl) {
        this.produceDetailCl = produceDetailCl;
    }

    /**
     * 获取小包
     *
     * @return produce_detail_xb - 小包
     */
    public String getProduceDetailXb() {
        return produceDetailXb;
    }

    /**
     * 设置小包
     *
     * @param produceDetailXb 小包
     */
    public void setProduceDetailXb(String produceDetailXb) {
        this.produceDetailXb = produceDetailXb;
    }

    /**
     * 获取大包
     *
     * @return produce_detail_db - 大包
     */
    public String getProduceDetailDb() {
        return produceDetailDb;
    }

    /**
     * 设置大包
     *
     * @param produceDetailDb 大包
     */
    public void setProduceDetailDb(String produceDetailDb) {
        this.produceDetailDb = produceDetailDb;
    }

    /**
     * 获取写
     *
     * @return produce_detail_xie - 写
     */
    public String getProduceDetailXie() {
        return produceDetailXie;
    }

    /**
     * 设置写
     *
     * @param produceDetailXie 写
     */
    public void setProduceDetailXie(String produceDetailXie) {
        this.produceDetailXie = produceDetailXie;
    }

    /**
     * 获取冲刀
     *
     * @return produce_detail_cd - 冲刀
     */
    public String getProduceDetailCd() {
        return produceDetailCd;
    }

    /**
     * 设置冲刀
     *
     * @param produceDetailCd 冲刀
     */
    public void setProduceDetailCd(String produceDetailCd) {
        this.produceDetailCd = produceDetailCd;
    }

    /**
     * 获取配料
     *
     * @return produce_detail_pl - 配料
     */
    public String getProduceDetailPl() {
        return produceDetailPl;
    }

    /**
     * 设置配料
     *
     * @param produceDetailPl 配料
     */
    public void setProduceDetailPl(String produceDetailPl) {
        this.produceDetailPl = produceDetailPl;
    }

    /**
     * @return produce_detail_create_time
     */
    public Date getProduceDetailCreateTime() {
        return produceDetailCreateTime;
    }

    /**
     * @param produceDetailCreateTime
     */
    public void setProduceDetailCreateTime(Date produceDetailCreateTime) {
        this.produceDetailCreateTime = produceDetailCreateTime;
    }

    /**
     * @return produce_detail_create_user_id
     */
    public String getProduceDetailCreateUserId() {
        return produceDetailCreateUserId;
    }

    /**
     * @param produceDetailCreateUserId
     */
    public void setProduceDetailCreateUserId(String produceDetailCreateUserId) {
        this.produceDetailCreateUserId = produceDetailCreateUserId;
    }

    /**
     * 获取生产日期月份
     *
     * @return produce_detail_month - 生产日期月份
     */
    public String getProduceDetailMonth() {
        return produceDetailMonth;
    }

    /**
     * 设置生产日期月份
     *
     * @param produceDetailMonth 生产日期月份
     */
    public void setProduceDetailMonth(String produceDetailMonth) {
        this.produceDetailMonth = produceDetailMonth;
    }

    /**
     * 获取生产日期日期
     *
     * @return produce_detail_day - 生产日期日期
     */
    public String getProduceDetailDay() {
        return produceDetailDay;
    }

    /**
     * 设置生产日期日期
     *
     * @param produceDetailDay 生产日期日期
     */
    public void setProduceDetailDay(String produceDetailDay) {
        this.produceDetailDay = produceDetailDay;
    }
}