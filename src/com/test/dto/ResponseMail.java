package com.test.dto;

public class ResponseMail {
	private int _id=0;
	private String userId="";
	private String name="";
	private String subject="";
	private String body="";
	private String email="";
	private String mobile="";
	private String isMailSend="";
	
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIsMailSend() {
		return isMailSend;
	}
	public void setIsMailSend(String isMailSend) {
		this.isMailSend = isMailSend;
	}
	

}
