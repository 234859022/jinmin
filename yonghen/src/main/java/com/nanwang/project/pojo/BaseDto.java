package com.nanwang.project.pojo;

import java.io.Serializable;

public class BaseDto implements Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3523712514322821691L;

	/**
     * 开始页数
     */
    private Integer pageNo = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;

    /**
     * 排序(mmGrid组件排序字段与方向)
     */
    private String sort;

    /**
     * 排序字段
     */
    private String sortName;

    /**
     * 排序方向
     */
    private String orderName;

    /**
     * 获取pageNo
     * @param pageNo pageNo
    */
    public Integer getPageNo() {
        return pageNo;
    }

    /**
     * 设置pageNo
     * @param pageNo pageNo
    */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 获取pageSize
     * @param pageSize pageSize
    */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置pageSize
     * @param pageSize pageSize
    */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
        String[] sortArr = sort.split("\\.");
        int iLength = sortArr.length;
        if (iLength > 1) {
            this.orderName = sortArr[iLength - 1];
            this.sortName = sort.substring(0, sort.lastIndexOf("."));
        }
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
}
