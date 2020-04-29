package com.sanposs.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_machine_task")
public class YhsjMachineTask implements Serializable{
    /**
     * 机器任务id
     */
    @Id
    @Column(name = "task_id")
    private String taskId;

    /**
     * 机器id
     */
    @Column(name = "machine_id")
    private String machineId;

    /**
     * 机器名称
     */
    @Column(name = "machine_name")
    private String machineName;

    /**
     * 采购编号
     */
    @Column(name = "purchase_no")
    private String purchaseNo;

    /**
     * 采购单明细id
     */
    @Column(name = "purchase_detail_id")
    private String purchaseDetailId;

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
     * 开始时间
     */
    @Column(name = "task_start_time")
    private Date taskStartTime;

    /**
     * 结束时间
     */
    @Column(name = "task_end_time")
    private Date taskEndTime;

    /**
     * 状态,1、进行中，2未处理，3已完成
     */
    @Column(name = "task_status")
    private Integer taskStatus;

    /**
     * 是否删除，1是，否
     */
    @Column(name = "task_is_delete")
    private Integer taskIsDelete;

    /**
     * 创建时间
     */
    @Column(name = "task_create_time")
    private Date taskCreateTime;

    /**
     * 创建人
     */
    @Column(name = "task_create_user")
    private String taskCreateUser;

    /**
     * 创建人ID
     */
    @Column(name = "task_create_userid")
    private String taskCreateUserid;

    /**
     * 机器类型：1、吹膜机器，2、封切机器
     */
    @Column(name = "machine_type")
    private Integer machineType;

    /**
     * 实际生产重量
     */
    @Column(name = "actual_weight")
    private BigDecimal actualWeight;

    /**
     * 获取机器任务id
     *
     * @return task_id - 机器任务id
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 设置机器任务id
     *
     * @param taskId 机器任务id
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取机器id
     *
     * @return machine_id - 机器id
     */
    public String getMachineId() {
        return machineId;
    }

    /**
     * 设置机器id
     *
     * @param machineId 机器id
     */
    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    /**
     * 获取机器名称
     *
     * @return machine_name - 机器名称
     */
    public String getMachineName() {
        return machineName;
    }

    /**
     * 设置机器名称
     *
     * @param machineName 机器名称
     */
    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    /**
     * 获取采购编号
     *
     * @return purchase_no - 采购编号
     */
    public String getPurchaseNo() {
        return purchaseNo;
    }

    /**
     * 设置采购编号
     *
     * @param purchaseNo 采购编号
     */
    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    /**
     * 获取采购单明细id
     *
     * @return purchase_detail_id - 采购单明细id
     */
    public String getPurchaseDetailId() {
        return purchaseDetailId;
    }

    /**
     * 设置采购单明细id
     *
     * @param purchaseDetailId 采购单明细id
     */
    public void setPurchaseDetailId(String purchaseDetailId) {
        this.purchaseDetailId = purchaseDetailId;
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
     * 获取开始时间
     *
     * @return task_start_time - 开始时间
     */
    public Date getTaskStartTime() {
        return taskStartTime;
    }

    /**
     * 设置开始时间
     *
     * @param taskStartTime 开始时间
     */
    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    /**
     * 获取结束时间
     *
     * @return task_end_time - 结束时间
     */
    public Date getTaskEndTime() {
        return taskEndTime;
    }

    /**
     * 设置结束时间
     *
     * @param taskEndTime 结束时间
     */
    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    /**
     * 获取状态,1、进行中，2未处理，3已完成
     *
     * @return task_status - 状态,1、进行中，2未处理，3已完成
     */
    public Integer getTaskStatus() {
        return taskStatus;
    }

    /**
     * 设置状态,1、进行中，2未处理，3已完成
     *
     * @param taskStatus 状态,1、进行中，2未处理，3已完成
     */
    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * 获取是否删除，1是，否
     *
     * @return task_is_delete - 是否删除，1是，否
     */
    public Integer getTaskIsDelete() {
        return taskIsDelete;
    }

    /**
     * 设置是否删除，1是，否
     *
     * @param taskIsDelete 是否删除，1是，否
     */
    public void setTaskIsDelete(Integer taskIsDelete) {
        this.taskIsDelete = taskIsDelete;
    }

    /**
     * 获取创建时间
     *
     * @return task_create_time - 创建时间
     */
    public Date getTaskCreateTime() {
        return taskCreateTime;
    }

    /**
     * 设置创建时间
     *
     * @param taskCreateTime 创建时间
     */
    public void setTaskCreateTime(Date taskCreateTime) {
        this.taskCreateTime = taskCreateTime;
    }

    /**
     * 获取创建人
     *
     * @return task_create_user - 创建人
     */
    public String getTaskCreateUser() {
        return taskCreateUser;
    }

    /**
     * 设置创建人
     *
     * @param taskCreateUser 创建人
     */
    public void setTaskCreateUser(String taskCreateUser) {
        this.taskCreateUser = taskCreateUser;
    }

    /**
     * 获取创建人ID
     *
     * @return task_create_userid - 创建人ID
     */
    public String getTaskCreateUserid() {
        return taskCreateUserid;
    }

    /**
     * 设置创建人ID
     *
     * @param taskCreateUserid 创建人ID
     */
    public void setTaskCreateUserid(String taskCreateUserid) {
        this.taskCreateUserid = taskCreateUserid;
    }

    /**
     * 获取机器类型：1、吹膜机器，2、封切机器
     *
     * @return machine_type - 机器类型：1、吹膜机器，2、封切机器
     */
    public Integer getMachineType() {
        return machineType;
    }

    /**
     * 设置机器类型：1、吹膜机器，2、封切机器
     *
     * @param machineType 机器类型：1、吹膜机器，2、封切机器
     */
    public void setMachineType(Integer machineType) {
        this.machineType = machineType;
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
}