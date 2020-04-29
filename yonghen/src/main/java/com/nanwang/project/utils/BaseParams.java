package com.nanwang.project.utils;

import java.io.Serializable;

import javax.persistence.Transient;
/**
 * 请求的基本参数
 *
 * @author cguangcong@linewell.com
 * @since 2017-06-20
 *
 */
public class BaseParams implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8116230898355165433L;

	/**
	 * 登陆票据
	 */
	@Transient
	private String token;// 票据


	/**
	 * 获取票据
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 设置票据
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}

}
