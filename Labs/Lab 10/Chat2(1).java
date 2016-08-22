import javax.jms.*;
import javax.naming.*;

import java.io.*;
import java.util.Hashtable;
import java.util.Random;
 
/**
 * Chat2 just adds a check to see if the message received was sent by this client...
  */
public class Chat2 implements javax.jms.MessageListener{
    private TopicSession pubSession;
    private TopicSession subSession;
    private TopicPublisher publisher;
    private TopicConnection connection;
    private int sessionId;
    private String name = "Anonymous";
        
         
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* Constructor. Establish JMS publisher and subscriber */
    public Chat2() throws Exception {
   	    	
        // Obtain a JNDI connection
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");                
		env.put(Context.PROVIDER_URL, "jnp://localhost:1099");
 
        InitialContext jndi = new InitialContext(env);
 
        // Look up a JMS connection factory
        TopicConnectionFactory conFactory =
        (TopicConnectionFactory)jndi.lookup("TopicConnectionFactory");
 
        // Create a JMS connection
        TopicConnection connection =
        //conFactory.createTopicConnection(username,password);
        conFactory.createTopicConnection();
 
        // Create two JMS session objects
        TopicSession pubSession =
        connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        TopicSession subSession =
        connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
 
        // Look up a JMS topic
        Topic chatTopic = (Topic)jndi.lookup("topic/testTopic");
 
        // Create a JMS publisher and subscriber
        TopicPublisher publisher = pubSession.createPublisher(chatTopic);
        TopicSubscriber subscriber = subSession.createSubscriber(chatTopic);
 
        // Set a JMS message listener
        subscriber.setMessageListener(this);
 
        // Initialise the Chat application
        set(connection, pubSession, publisher);
 
        // Start the JMS connection; allows messages to be delivered
        connection.start( );
 
    }
    
    /* Initialise the instance variables */
    public void set(TopicConnection con, TopicSession pubSess, TopicPublisher pub) {
    	Random random = new Random();
    	sessionId = random.nextInt();
    	
    	this.connection = con;
        this.pubSession = pubSess;
        this.publisher = pub;
    }
    
    /* Receive message from topic subscriber */
    public void onMessage(Message message) {
        try {
        	if (message.getIntProperty("clientId") != sessionId){
        		TextMessage textMessage = (TextMessage) message;
        		String text = textMessage.getText( );
        		System.out.println(text);
        	}
        } catch (JMSException jmse){ jmse.printStackTrace( ); }
    }
    
    /* Create and send message using topic publisher */
    protected void writeMessage(String text) throws JMSException {
        TextMessage message = pubSession.createTextMessage( );
        message.setIntProperty("clientId", sessionId);
        message.setText(name + "> " + text + "\n");
        publisher.publish(message);
    }
    
    /* Close the JMS connection */
    public void close( ) throws JMSException {
        connection.close( );
    }
    
    /* Run the Chat client */
    public static void main(String [] args){
        try{
            System.out.println("Starting Chat client...");
  
            Chat2 chat = new Chat2();
            
            // Read from command line
            BufferedReader commandLine = new 
              java.io.BufferedReader(new InputStreamReader(System.in));
 
            System.out.println("Ready to chat...\n");
            // Loop until the word "exit" is typed
            while(true){
                String s = commandLine.readLine( );
                if (s.equalsIgnoreCase("exit")){
                    chat.close( ); // close down connection
                    System.exit(0);// exit program
                }
                if (s.startsWith("myname=")){
                    chat.setName (s.substring(7));
                    System.out.println("Name set to: " + s.substring(7));
                }
                else 
                    chat.writeMessage(s);
            }
        } catch (Exception e){ e.printStackTrace( ); }
    }
}

