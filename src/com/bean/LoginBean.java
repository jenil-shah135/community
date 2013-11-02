package com.bean;

public class LoginBean {
	private Integer loginID;
	private String username;
	private String password;
	private Integer userType;
	private FamilyBean family;
	public Integer getLoginID() {
		return loginID;
	}
	public void setLoginID(Integer loginID) {
		this.loginID = loginID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public FamilyBean getFamily() {
		return family;
	}
	public void setFamily(FamilyBean family) {
		this.family = family;
	}
}