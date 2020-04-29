package com.sanposs.project.core;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 统一API响应结果封装
 */
public class Result {
    private int code;
    private String message;
    private Object data;
    private int totalCount;
    private List items;//分页列表
	private Map<String, Object> attributes;// 其他参数
    
    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
     
    
}
