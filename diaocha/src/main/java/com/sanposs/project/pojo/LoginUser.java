package com.sanposs.project.pojo;

/**
 * 登录用户的DTO
 * @author aaron
 *
 */
public class LoginUser {
	
	// 登录的token
	private String token;

	// 用户标识
	private String userId;

	// 用户名
	private String username;

	// 昵称
	private String nickname;
	
	// 头像
	private String headPic;
	
	// 电话
	private String phone;
	
	// 密码
	private String password;

	
	// 登录时间
	private long loginTime;


	// 用户OpenId
	private String openid;
	
	//用户类型
	private Integer userType;


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

}
