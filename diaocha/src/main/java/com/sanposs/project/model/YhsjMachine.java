package com.sanposs.project.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "yhsj_machine")
public class YhsjMachine implements Serializable{
    /**
     * 机器id
     */
    @Id
    @Column(name = "machine_id")
    private String machineId;

    /**
     * 机器名称
     */
    @Column(name = "machine_name")
    private String machineName;

    /**
     * 机器类型：1、吹膜机器，2、封切机器
     */
    @Column(name = "machine_type")
    private Integer machineType;

    /**
     * 状态
     */
    @Column(name = "machine_status")
    private Integer machineStatus;

    /**
     * 创建人
     */
    @Column(name = "machine_create_user")
    private String machineCreateUser;

    /**
     * 创建时间
     */
    @Column(name = "machine_create_time")
    private Date machineCreateTime;

    /**
     * 是否删除，1是，否
     */
    @Column(name = "machine_is_delete")
    private Integer machineIsDelete;

    /**
     * 是否故障，1是，0否
     */
    @Column(name = "machine_is_fault")
    private Integer machineIsFault;

    /**
     * 故障原因
     */
    @Column(name = "machine_fault_reason")
    private String machineFaultReason;

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
     * 获取状态
     *
     * @return machine_status - 状态
     */
    public Integer getMachineStatus() {
        return machineStatus;
    }

    /**
     * 设置状态
     *
     * @param machineStatus 状态
     */
    public void setMachineStatus(Integer machineStatus) {
        this.machineStatus = machineStatus;
    }

    /**
     * 获取创建人
     *
     * @return machine_create_user - 创建人
     */
    public String getMachineCreateUser() {
        return machineCreateUser;
    }

    /**
     * 设置创建人
     *
     * @param machineCreateUser 创建人
     */
    public void setMachineCreateUser(String machineCreateUser) {
        this.machineCreateUser = machineCreateUser;
    }

    /**
     * 获取创建时间
     *
     * @return machine_create_time - 创建时间
     */
    public Date getMachineCreateTime() {
        return machineCreateTime;
    }

    /**
     * 设置创建时间
     *
     * @param machineCreateTime 创建时间
     */
    public void setMachineCreateTime(Date machineCreateTime) {
        this.machineCreateTime = machineCreateTime;
    }

    /**
     * 获取是否删除，1是，否
     *
     * @return machine_is_delete - 是否删除，1是，否
     */
    public Integer getMachineIsDelete() {
        return machineIsDelete;
    }

    /**
     * 设置是否删除，1是，否
     *
     * @param machineIsDelete 是否删除，1是，否
     */
    public void setMachineIsDelete(Integer machineIsDelete) {
        this.machineIsDelete = machineIsDelete;
    }

    /**
     * 获取是否故障，1是，0否
     *
     * @return machine_is_fault - 是否故障，1是，0否
     */
    public Integer getMachineIsFault() {
        return machineIsFault;
    }

    /**
     * 设置是否故障，1是，0否
     *
     * @param machineIsFault 是否故障，1是，0否
     */
    public void setMachineIsFault(Integer machineIsFault) {
        this.machineIsFault = machineIsFault;
    }

    /**
     * 获取故障原因
     *
     * @return machine_fault_reason - 故障原因
     */
    public String getMachineFaultReason() {
        return machineFaultReason;
    }

    /**
     * 设置故障原因
     *
     * @param machineFaultReason 故障原因
     */
    public void setMachineFaultReason(String machineFaultReason) {
        this.machineFaultReason = machineFaultReason;
    }
}