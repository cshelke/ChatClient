package client;
import java.net.*;
import java.security.*;
import java.io.*;
import javax.crypto.*;

public class Client implements Runnable
{  
   private Socket socket              = null;
   private Thread thread              = null;
   private DataInputStream  console   = null;
   private DataOutputStream streamOut = null;
   private ClientThread client    = null;
   private Key key;

   public Client(String serverName, int serverPort)
   {  
	  System.out.println("Establishing connection. Please wait ...");
      try
      {  
    	  socket = new Socket("localhost", serverPort);
         System.out.println("Connected to Cyril's server: ");// + socket);
         System.out.println("<= Welcome to Cyril's chat server");
         System.out.println("<= Login Name?");
         //System.out.print("=> ");
         start();
      }
      catch(UnknownHostException uh)
      {  
    	  System.out.println("Unknown Host! " + uh.getMessage()); 
      }
      catch(IOException ioe)
      {  
    	  System.out.println("Unexpected exception: " + ioe.getMessage());
      }
   }
   
   public static void main(String args[])
   {  
	   Client client = null;
      if (args.length != 2)
    	  System.out.println("run as> java Client <server> <port>");
      else
         client = new Client(args[0], Integer.parseInt(args[1]));
   }
   
   public void run()
   {  
	   while (thread != null)
      {  
	   try
         {  
		   streamOut.writeUTF(SecurityHandlerClient.encryptTheMessage(console.readLine(),this.key));
            streamOut.flush();
         }
         catch(IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ioe)
         {  
        	 System.out.println("Sending error: " + ioe.getMessage());
            stop();
         }
      }
   }
   
   public void handle(String msg) throws InvalidKeyException, 
   NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
   {  
	   String[] sp = msg.split(" ");
	   if(sp.length>=2) //initial message where the server sends the key (plain text)
	   {
		   if(sp[1].equals("/key"))
		    	  this.key = SecurityHandlerClient.extractKey(sp[2]);
	   }
	   else // message is encrypted
		 {
		   String decryptedMsg = SecurityHandlerClient.decryptTheMessage(msg, this.key);
		   String[] abc = decryptedMsg.split(" ");
		   if (abc[1].equals("/quit"))
	      {  
			   System.out.println("Disconnected from the chat server");
	         stop();
	      }
		   else
		         System.out.println(decryptedMsg);
		}
      
	   	
   }
   
   public void start() throws IOException
   {  
	   console   = new DataInputStream(System.in);
      streamOut = new DataOutputStream(socket.getOutputStream());
      if (thread == null)
      {  
    	  client = new ClientThread(this, socket);
         thread = new Thread(this);                   
         thread.start();
      }
   }
   
   
   public void stop()
   {  
	   if (thread != null)
      {  
		   thread.stop();  
         thread = null;
      }
      try
      {  
    	  if (console   != null)  
    		  console.close();
         if (streamOut != null)  
        	 streamOut.close();
         if (socket    != null)  
        	 socket.close();
      }
      catch(IOException ioe)
      {  
    	  System.out.println("Error closing ..."); }
      		client.close();  
      		client.stop();
   }
}