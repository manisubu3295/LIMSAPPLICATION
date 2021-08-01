package com.test.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentDto implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String student_name		=	"";
String student_email	=	"";
String section			=	"";
int _id;
String dob				=	"";
String gender					=	"";
String subjects="";
String mailStatus="";

public String getMailStatus() {
	return mailStatus;
}
public void setMailStatus(String mailStatus) {
	this.mailStatus = mailStatus;
}
public String getSubjects() {
	return subjects;
}
public void setSubjects(String subjects) {
	this.subjects = subjects;
}
public int get_id() {
	return _id;
}
public void set_id(int _id) {
	this._id = _id;
}
//List subjects	=new ArrayList();
public String getStudent_name() {
	return student_name;
}
public void setStudent_name(String student_name) {
	this.student_name = student_name;
}
public String getStudent_email() {
	return student_email;
}
public void setStudent_email(String student_email) {
	this.student_email = student_email;
}
public String getSection() {
	return section;
}
public void setSection(String section) {
	this.section = section;
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

/*
 * public List getSubjects() { return subjects; } public void setSubjects(List
 * subjects) { this.subjects = subjects; }
 */


}
