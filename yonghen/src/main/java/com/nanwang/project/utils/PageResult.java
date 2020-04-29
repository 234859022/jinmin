package com.nanwang.project.utils;

import java.util.List;

public class PageResult<T> {
	/**
	 * Current page
	 */
	private Integer page;
	
	private Integer size;
	
	/**
	 * Total pages
	 */
	private Integer total;
	
	/**
	 * Total number of records
	 */
	private Integer records;
	
	/**
	 * Contains the actual data
	 */
	private List<T> rows;
	
	public PageResult(){
		
	}
	
	public PageResult(int page,int size,int total,int records,List<T> rows){
		this.page=page;
		this.size=size;
		this.total=total;
		this.records=records;
		this.rows=rows;
	}
	
	public PageResult(int page,int size,int records,List<T> rows){
		this.page=page;
		this.size=size;
		this.records=records;
		this.rows=rows;
		
		if(records%size==0){
			this.total=records/size;
		}else{
			this.total=records/size+1;
		}
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
	
}
