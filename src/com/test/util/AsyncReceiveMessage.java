package com.test.util;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.MessageListener;
import javax.jms.JMSException;
import javax.jms.ExceptionListener;
import javax.jms.QueueSession;
import javax.jms.QueueReceiver;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;

public class AsyncReceiveMessage implements MessageListener, ExceptionListener
{

	// Set the JNDI context factory for a JBOSS/ WildFly Server.
	public final static String JNDI_FACTORY="org.jboss.ejb.client.naming"; 

	// Set the JMS context factory.
	public final static String JMS_FACTORY="jms/RemoteConnectionFactory";

	// Set the queue.
	public final static String QUEUE="jms/JMSDemoQueue";

	// Set Wildfly URL.
	public final static String WildflyURL="http-remoting://localhost:8080";
	
	public static void main(String[] args) throws Exception
	{
		// get the initial context
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

		// set an asynchronous message listener
		AsyncReceiveMessage asyncReceiver = new AsyncReceiveMessage();
		queueReceiver.setMessageListener(asyncReceiver);

		// set an asynchronous exception listener on the connection
		queueConn.setExceptionListener(asyncReceiver);

		// start the connection
		queueConn.start();

		// wait for messages
		System.out.print("waiting for messages");
		for (int i = 0; i < 10; i++) {
			Thread.sleep(10000);
			System.out.print(".");
		}
		System.out.println();

		// close the queue connection
		queueConn.close();
	}

	/**
       This method is called asynchronously by JMS when a message arrives
       at the queue. Client applications must not throw any exceptions in
       the onMessage method.
       @param message A JMS message.
	 */
	public void onMessage(Message message)
	{
		TextMessage msg = (TextMessage) message;
		try {
			System.out.println("received: " + msg.getText());
		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}

	/**
       This method is called asynchronously by JMS when some error occurs.
       When using an asynchronous message listener it is recommended to use
       an exception listener also since JMS have no way to report errors
       otherwise.
       @param exception A JMS exception.
	 */
	public void onException(JMSException exception)
	{
		System.err.println("an error occurred: " + exception);
	}
}