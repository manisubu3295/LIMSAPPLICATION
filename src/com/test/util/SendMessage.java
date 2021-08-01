package com.test.util;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;


public class SendMessage {
	
	// Set the JNDI context factory for a JBOSS/ WildFly Server.
    public final static String JNDI_FACTORY="org.jboss.ejb.client.naming"; 
 
    // Set the JMS context factory.
    public final static String JMS_FACTORY="jms/RemoteConnectionFactory";
 
    // Set the queue.
    public final static String QUEUE="jms/JMSDemoQueue";
      
    // Set Wildfly URL.
    public final static String WildflyURL="http-remoting://localhost:8080";

	public static void main(String[] args) throws Exception {
		
	    //1) Create and start a connection 
	    Properties properties = new Properties();
	    properties.put(Context.URL_PKG_PREFIXES, JNDI_FACTORY);
	    properties.put("jboss.naming.client.ejb.context", "true");
	    properties.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.remote.client.InitialContextFactory");
	    properties.put(Context.PROVIDER_URL, WildflyURL);
	     
	    Context ic=new InitialContext(properties); 
	     
	    //3) get the Queue object  
	    Queue t=(Queue)ic.lookup(QUEUE);  
	    
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

	}

}
