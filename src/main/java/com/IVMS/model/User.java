package com.IVMS.model;

public class User {
	private String cn;
	private String description;
	private String mail;
	private String mobile;
	private String sAMAccountName;
	
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getsAMAccountName() {
		return sAMAccountName;
	}
	public void setsAMAccountName(String sAMAccountName) {
		this.sAMAccountName = sAMAccountName;
	}
	
	public String toString() {
		return "User [cn=" + cn + ", description=" + description + ", mail=" + mail + ", mobile=" + mobile
				+ ", sAMAccountName=" + sAMAccountName + "]";
	}
	
}
