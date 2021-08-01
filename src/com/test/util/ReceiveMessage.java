package com.test.util;
import java.util.Properties;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;


public class ReceiveMessage {

	// Set the JNDI context factory for a JBOSS/ WildFly Server.
	public final static String JNDI_FACTORY="org.jboss.ejb.client.naming"; 

	// Set the JMS context factory.
	public final static String JMS_FACTORY="jms/RemoteConnectionFactory";

	// Set the queue.
	public final static String QUEUE="jms/JMSDemoQueue";

	// Set Wildfly URL.
	public final static String WildflyURL="http-remoting://localhost:8080";

	public static void main(String[] args) throws Exception {

		Properties properties = new Properties();
		properties.put(Context.URL_PKG_PREFIXES, JNDI_FACTORY);
		properties.put("jboss.naming.client.ejb.context", "true");
		properties.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.remote.client.InitialContextFactory");
		properties.put(Context.PROVIDER_URL, WildflyURL);

		Context ctx=new InitialContext(properties); 

		// lookup the queue object
		Queue queue = (Queue) ctx.lookup(QUEUE);

		// lookup the queue connection factory
		QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.
				lookup(JMS_FACTORY);

		// create a queue connection
		QueueConnection queueConn = connFactory.createQueueConnection();

		// create a queue session
		QueueSession queueSession = queueConn.createQueueSession(false,
				Session.AUTO_ACKNOWLEDGE);

		// create a queue receiver
		QueueReceiver queueReceiver = queueSession.createReceiver(queue);

		// start the connection
		queueConn.start();

		// receive a message
		TextMessage message = (TextMessage) queueReceiver.receive();

		// print the message
		System.out.println("received: " + message.getText());

		// close the queue connection
		queueConn.close();
	}

}
