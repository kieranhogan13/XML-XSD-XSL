//Import the JMS API classes.
import javax.jms.ConnectionFactory;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.MessageProducer;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.naming.*;
import javax.swing.JOptionPane;

import java.util.*;

public class HelloWorldMessage {

	/**
	 * Main method.
	 *
	 * @param args  not used
	 *
	 */	
	public static void main(String[] args) {

		try {

			ConnectionFactory myConnFactory;
			Queue myQueue;
			Hashtable env;
			Context ctx = null;
			env = new Hashtable();
	
			// Step 1...
			// Store the environment variable that tell JNDI which initial context
			// to use and where to find the provider.
			env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory"); 
			env.put(Context.PROVIDER_URL, "jnp://localhost:1099");
			
			// Create the initial context.
			ctx = new InitialContext(env);

			// Step 2...
			// Lookup my connection factory from the admin object store.
			// The name used here must match the lookup name
			// used when the admin object was stored.
			myConnFactory = (javax.jms.ConnectionFactory) ctx.lookup("java:/ConnectionFactory");
						
			// Lookup my queue from the admin object store.
			// The name I search for here must match the lookup name used when
			// the admin object was stored.
			myQueue = (javax.jms.Queue)ctx.lookup("queue/A");


			//Step 3...
			//Get connection
			Connection myConn = myConnFactory.createConnection();


			//Step 4:
			//Create a session within the connection.
			Session mySess = myConn.createSession(false, Session.AUTO_ACKNOWLEDGE);


			//Step 6:
			//Create a message producer.
			MessageProducer myMsgProducer = mySess.createProducer(myQueue);


			//Step 7:
			//Create and send a message to the queue.
			TextMessage myTextMsg = mySess.createTextMessage();
			myTextMsg.setText("Hello World");			
			System.out.println("Sending Message: " + myTextMsg.getText());
			myMsgProducer.send(myTextMsg);


			//Step 8:
			//Create a message consumer.
			MessageConsumer myMsgConsumer = mySess.createConsumer(myQueue);


			//Step 9:
			//Start the Connection created in step 3.
			myConn.start();


			//Step 10:
			//Receive a message from the queue.
			Message msg = myMsgConsumer.receive();


			//Step 11:
			//Retreive the contents of the message.
			if (msg instanceof TextMessage) {
				TextMessage txtMsg = (TextMessage) msg;
				System.out.println("Read Message: " + txtMsg.getText());
			}

			//Step 12:
			//Close the session and connection resources.
			mySess.close();
			myConn.close();

		} catch (Exception jmse) {
			System.out.println("Exception occurred : " + jmse.toString());
			jmse.printStackTrace();
		}
	}
}