package com.test.dto;

import java.util.Date;

public class User {
	int _id				=	0;
	String	firstName	=	"";
	String	lastName	=	"";
	String	email		=	"";
	String 	mobile		=	"";
	String 	country		=	"";
	String 	dob			=	"";
	String 	gender		=	"";
	String 	maritalStatus=	"";
	String 	password	=	"";
	String 	mailStatus	=	"";
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMailStatus() {
		return mailStatus;
	}
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}


}
