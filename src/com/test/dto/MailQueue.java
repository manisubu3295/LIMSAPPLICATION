package com.test.dto;

	import java.util.ArrayList;
	import java.util.List;

public class MailQueue {
	
	User test=new User();
	
	
		private String subject;
		private String body;
		List<User> userDetails=new ArrayList<User>();
		private String mailSend;
		
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
		public List<User> getUserDetails() {
			return userDetails;
		}
		public void setUserDetails(List<User> userDetails) {
			this.userDetails = userDetails;
		}
		public String getMailSend() {
			return mailSend;
		}
		public void setMailSend(String mailSend) {
			this.mailSend = mailSend;
		}
		
	


}
