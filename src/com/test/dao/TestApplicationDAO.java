package com.test.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.test.dto.MailQueue;
import com.test.dto.Register;
import com.test.dto.ResponseMail;
import com.test.dto.StudentDto;
import com.test.dto.User;
import com.test.util.BulkEmailSender;
import com.test.util.EmailUtil;

import jdk.internal.org.jline.utils.Log;

public class TestApplicationDAO {

	ArrayList<User> tempVal = new ArrayList<User>();

	BulkEmailSender bulkMailSender = new BulkEmailSender();
	boolean mailExist = false;

	public ArrayList callDB() {
		System.out.println("inside");

		// TestDTO testDto=new TestDTO();
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stuinfo", "root", "");
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT  id,student_name, student_email, section, subjects, dob, gender FROM sdudetails WHERE 1");
			

			while (rs.next()) {
				StudentDto stuInfo = new StudentDto();
				stuInfo.set_id(rs.getInt("id"));
				stuInfo.setStudent_name(rs.getString("student_name"));
				stuInfo.setStudent_email(rs.getString("student_email"));
				stuInfo.setSection(rs.getString("section"));
				// tempVal.add(stuInfo);
				System.out.println(stuInfo.getStudent_name());
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return tempVal;

	}

	public ArrayList<User> getUserDetails() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = getConnection();

			String sql = "SELECT _id,firstName,lastName,email,mobile,country,dob,gender from user";
			System.out.println(sql);
			con = getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.set_id(rs.getInt("_id"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setEmail(rs.getString("email"));
				user.setMobile(rs.getString("mobile"));
				user.setCountry(rs.getString("country"));
				user.setDob(rs.getString("dob"));
				user.setGender(rs.getString("gender"));

				tempVal.add(user);
				System.out.println(user.getFirstName());
			}
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempVal;
	}

	public int register(User userValue) {
		int rs = 0;
		PreparedStatement ps = null;
		Connection con = null;
		try {
			boolean checkMailExists = checkMailExists(userValue.getEmail());
			System.out.println("checkMailExists : " + checkMailExists);
			if (!checkMailExists) {
				String sql = "INSERT INTO user(firstName, lastName, email, mobile, country, dob,gender,maritalStatus,password) "
						+ " VALUES(?,?,?,?,?,?,?,?,?)";
				System.out.println(sql);
				con = getConnection();
				ps = con.prepareStatement(sql);
				ps.setString(1, userValue.getFirstName());
				ps.setString(2, userValue.getLastName());
				ps.setString(3, userValue.getEmail());
				ps.setString(4, userValue.getMobile());
				ps.setString(5, userValue.getCountry());
				ps.setString(6, userValue.getDob());
				ps.setString(7, userValue.getGender());
				ps.setString(8, userValue.getMaritalStatus());
				ps.setString(9, userValue.getPassword());
				rs = ps.executeUpdate();
				con.close();
			} else {
				rs = 2;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	protected boolean checkMailExists(String mailId) {
		boolean ret = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String checkMailAddress = "select email from user where email=?";
			ps = con.prepareStatement(checkMailAddress);
			ps.setString(1, mailId);
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ret;
	}

	protected boolean checkPassword(String email, String pass) {
		boolean ret = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String checkMailAddress = "select email,password from user where email=? and password=?";
			ps = con.prepareStatement(checkMailAddress);
			ps.setString(1, email);
			ps.setString(2, pass);
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ret;
	}

	protected Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mailscheduler" + "", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public int login(User userValue) {
		int rs = 0;
		PreparedStatement ps = null;
		Connection con = null;
		try {
			boolean checkMailExists = checkMailExists(userValue.getEmail());
			boolean checkPass = checkPassword(userValue.getEmail(), userValue.getPassword());
			System.out.println("checkMailExists : " + checkMailExists);
			if (checkMailExists) {
				if (checkPass) {
					rs = 1;
				} else {
					rs = 3;
				}
			} else {
				rs = 2;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public int sendMail(MailQueue mailQueue) {
		System.out.println("inside sendmail");
		int rs = 0;
		System.out.println(mailQueue + "in DAo");
		Connection con = null;
		PreparedStatement ps = null;
		List<User> userValue = new ArrayList<User>();
		String sql = "";
		System.out.println(mailQueue.getBody() + "inside sendmail dao");
		try {
			con = getConnection();
			mailQueue.getBody();
			mailQueue.getSubject();
			userValue = mailQueue.getUserDetails();
			for (User user : userValue) {
				sql = "INSERT INTO mailqueue(userId, name, subject, body, email, mobile) VALUES(?,?,?,?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setInt(1, user.get_id());
				ps.setString(2, user.getFirstName() + " " + user.getLastName());
				ps.setString(3, mailQueue.getSubject());
				ps.setString(4, mailQueue.getBody());
				ps.setString(5, user.getEmail());
				ps.setString(6, user.getMobile());

				rs = ps.executeUpdate();
			}
			boolean valid = true;
//			while(valid) {
//			autoMailTrigger();
//			}
			System.out.println(rs + "<==rs");
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}

	public ArrayList<ResponseMail> mailDetails(String mailDetails) {

		System.out.println("inside sendmail");
		ResultSet rs=null;
		System.out.println(mailDetails.toString());
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "";
		ResponseMail mailStatus = new ResponseMail();
		ArrayList<ResponseMail> mailQueuedata=new ArrayList<ResponseMail>();
		
		try {
			con = getConnection();
			User u  = new User();
			if (mailDetails.equals("all")) {
				System.out.println("inside ! ==");
				;
				 sql = "select _id,name,email,subject,body,ismailsend from mailqueue";
				con = getConnection();
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					mailStatus=new ResponseMail();
					mailStatus.setBody(rs.getString("body"));
					mailStatus.setSubject(rs.getString("subject"));
					mailStatus.set_id(rs.getInt("_id"));
					mailStatus.setName(rs.getString("name"));
					mailStatus.setEmail(rs.getString("email"));
					mailStatus.setIsMailSend(rs.getString("ismailSend"));
					
					if(mailStatus.getIsMailSend().equals("1")) {
						mailStatus.setIsMailSend("Success");
					}else {
						mailStatus.setIsMailSend("Failed");
					}
					
					mailQueuedata.add(mailStatus);
				}
				System.out.println("1:"+mailQueuedata.get(0));
				System.out.println("2:"+mailQueuedata.get(1));
			} else {
				System.out.println("inside  ==");
				;
				 sql = "select _id,name,email,subject,body,ismailsend from mailqueue where ismailSend=?";
				// sql = "select _id,name,email,subject,body,ismailsend from mailqueue";
					con = getConnection();
					ps = con.prepareStatement(sql);
					ps.setString(1, mailDetails);
					rs = ps.executeQuery();
				while (rs.next()) {
					mailStatus=new ResponseMail();
					mailStatus.setBody(rs.getString("body"));
					mailStatus.setSubject(rs.getString("subject"));
					mailStatus.set_id(rs.getInt("_id"));
					mailStatus.setName(rs.getString("name"));
					mailStatus.setEmail(rs.getString("email"));
					mailStatus.setIsMailSend(rs.getString("ismailSend"));
					
					if(mailStatus.getIsMailSend().equals("1")) {
						mailStatus.setIsMailSend("Success");
					}else {
						mailStatus.setIsMailSend("Failed");
					}
					
					mailQueuedata.add(mailStatus);
				
					
				}
			}

			System.out.println(rs + "<==rs");
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mailQueuedata;	
	}

	public boolean autoMailTrigger() {
		boolean isMailSend = false;
		// create a list of emails
		ResultSet rs;
		final String fromEmail = "manisubu3295@gmail.com"; // requires valid gmail id
		final String password = "8056931190"; // correct password for gmail id
		String toEmail = ""; // can be any email id
		System.out.println("TLSEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		props.put("mail.smtp.port", "587"); // TLS Port
		props.put("mail.smtp.auth", "true"); // enable authentication
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stuinfo" + "", "root", "");

			String sql = "select id,student_email from sendmail where ismailSend=0";
			List<String> emails = new ArrayList<String>();
			System.out.println(sql);
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				StudentDto stuInfo = new StudentDto();
				stuInfo.set_id(rs.getInt("id"));
				toEmail = rs.getString("student_email");
				isMailSend = EmailUtil.sendEmail(session, toEmail, "TLSEmail Testing Subject", "TLSEmail Testing Body");
				System.out.println("Manikandan Subramaniyan" + isMailSend);
				if (isMailSend) {
					System.out.println("Insid IsmailSend");
					sql = "update sendmail set ismailSend='1' where student_email='" + toEmail + "' " + "and id='"
							+ stuInfo.get_id() + "'";
					System.out.println(sql);
					stmt.executeUpdate(sql);
				}
			}

//			
//			emails.add("chithrapushparaj@gmail.com ");
//			emails.add("rakhivkulkarni3388@gmail.com ");
//			emails.add("tyahoorajan@gmail.com");
			// emails.add("email4@email.com");
			// emails.add("email5@email.com");

			// email subject
			String subject = "Test Email";

			// message which is to be sent
			String message = "Test Email Message";

			// send the email to multiple recipients
			// isMailSend= BulkEmailSender.sendBulkEmail(subject, emails, message);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isMailSend;
	}

	public boolean isMailExist() {
		return mailExist;
	}

	public void setMailExist(boolean mailExist) {
		this.mailExist = mailExist;
	}

//	public static void main(String[] args) {
//		Register register=new Register();
//		callDB(register);
//	}

}
