package com.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class BulkEmailSender {

	public static void main(String[] args) {
		// create a list of emails
		List<String> emails = new ArrayList<String>();
		
		emails.add("dshree050gmail.com");
		emails.add("email4@email.com");
		//emails.add("email5@email.com");
		
		// email subject
		String subject = "Test Email";
		
		// message which is to be sent
		String message = "Test Email Message";
		
		// send the email to multiple recipients
		//sendBulkEmail(subject, emails, message);
	}

	public  boolean sendBulkEmail(final String subject, final List<String> emailToAddresses,
			final String emailBodyText) {
		boolean isMailSend=false;
		// from email address
		final String username = "manisubu3295@gmail.com";

		// make sure you put your correct password
		final String password = "8056931190";

		// smtp email server
		final String smtpHost = "smtp.gmail.com";

		// We will put some properties for smtp configurations
		Properties props = new Properties();

		// do not change - start
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
		// do not change - end

		// we authentcate using your email and password and on successful
		// we create the session
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		String emails = null;

		try {
			// we create new message
			Message message = new MimeMessage(session);

			// set the from 'email address'
			message.setFrom(new InternetAddress(username));

			// set email subject
			message.setSubject(subject);

			// set email message
			// this will send html mail to the intended recipients
			// if you do not want to send html mail then you do not need to wrap the message
			// inside html tags
			String content = "<html>\n<body>\n";
			content += emailBodyText + "\n";
			content += "\n";
			content += "</body>\n</html>";
			message.setContent(content, "text/html");

			// form all emails in a comma separated string
			StringBuilder sb = new StringBuilder();

			int i = 0;
			for (String email : emailToAddresses) {
				sb.append(email);
				i++;
				if (emailToAddresses.size() > i) {
					sb.append(", ");
				}
			}

			emails = sb.toString();

			// set 'to email address'
			// you can set also CC or TO for recipient type
			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(sb.toString()));

			System.out.println("Sending Email to " + emails + " from " + username + " with Subject - " + subject);

			// send the email
			Transport.send(message);
			isMailSend=true;
			System.out.println("Email successfully sent to " + emails);
		} catch (MessagingException e) {
			System.out.println("Email sending failed to " + emails);
			isMailSend=false;
			System.out.println(e);
		}
		return isMailSend;
	}

}