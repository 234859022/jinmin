package com.nanwang.project.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "E_TYPE")
public class EType   implements Serializable{
    @Id
    @Column(name = "TYPE_ID")
    private String typeId;

    @Column(name = "TYPE_NAME")
    private String typeName;

    @Column(name = "TYPE_DETAIL")
    private String typeDetail;

    @Column(name = "TYPE_CREATE_TIME")
    private Date typeCreateTime;

    @Column(name = "TYPE_CREATE_USER")
    private String typeCreateUser;

    @Column(name = "TYPE_SORT")
    private Integer typeSort;

    @Column(name = "TYPE_STATUS")
    private Integer typeStatus;

    /**
     * @return TYPE_ID
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * @param typeId
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
     * @return TYPE_NAME
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * @return TYPE_DETAIL
     */
    public String getTypeDetail() {
        return typeDetail;
    }

    /**
     * @param typeDetail
     */
    public void setTypeDetail(String typeDetail) {
        this.typeDetail = typeDetail;
    }

    /**
     * @return TYPE_CREATE_TIME
     */
    public Date getTypeCreateTime() {
        return typeCreateTime;
    }

    /**
     * @param typeCreateTime
     */
    public void setTypeCreateTime(Date typeCreateTime) {
        this.typeCreateTime = typeCreateTime;
    }

    /**
     * @return TYPE_CREATE_USER
     */
    public String getTypeCreateUser() {
        return typeCreateUser;
    }

    /**
     * @param typeCreateUser
     */
    public void setTypeCreateUser(String typeCreateUser) {
        this.typeCreateUser = typeCreateUser;
    }

    /**
     * @return TYPE_SORT
     */
    public Integer getTypeSort() {
        return typeSort;
    }

    /**
     * @param typeSort
     */
    public void setTypeSort(Integer typeSort) {
        this.typeSort = typeSort;
    }

    /**
     * @return TYPE_STATUS
     */
    public Integer getTypeStatus() {
        return typeStatus;
    }

    /**
     * @param typeStatus
     */
    public void setTypeStatus(Integer typeStatus) {
        this.typeStatus = typeStatus;
    }
}