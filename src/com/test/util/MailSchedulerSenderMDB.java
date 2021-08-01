package com.test.util;


import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;




public class MailSchedulerSenderMDB {



	public static void main(String[] args) throws Exception {
		try {
			// Set the JNDI context factory for a JBOSS/ WildFly Server.
		    final  String JNDI_FACTORY="org.jboss.ejb.client.naming"; 
		 
		    // Set the JMS context factory.
		    final  String JMS_FACTORY="jms/RemoteConnectionFactory";
		 
		    // Set the queue.
		    final  String QUEUE="jms/JMSDemoQueue";
		      
		    // Set Wildfly URL.
		    final  String WildflyURL="http-remoting://localhost:8080";
	    //1) Create and start a connection 
	    Properties properties = new Properties();
	    properties.put(Context.URL_PKG_PREFIXES, JNDI_FACTORY);
	    properties.put("jboss.naming.client.ejb.context", "true");
	    properties.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.remote.client.InitialContextFactory");
	    properties.put(Context.PROVIDER_URL, WildflyURL);
	     
	    Context ic=new InitialContext(properties); 
	     System.out.println("in");
	    //3) get the Queue object  
	    Queue t=(Queue)ic.lookup(QUEUE);  
	    System.out.println("innnnn");
	    ConnectionFactory f=(ConnectionFactory)ic.lookup(JMS_FACTORY) ;   
	     
	    Connection con=f.createConnection();  
	    con.start();  
	     
	    //2) create queue session  
	    Session ses=con.createSession(false, Session.AUTO_ACKNOWLEDGE);
	     
	    //4)create QueueSender object         
	    MessageProducer producer =ses.createProducer(t);  
	    
	    //5) create TextMessage object  
	     TextMessage msg=ses.createTextMessage();  
	     msg.setText("hi.............");
	      
	    //6) send message  
	     producer.send(msg);  
	    System.out.println("Message successfully sent to a WildFly Queue.");  
	 
	    //7) connection close 
	    con.close();
		}catch(Exception e) {
			
		}
	}


}

