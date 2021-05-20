import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */


public class Server{

	int count = 1;	//Start with one
	int countClient = 1; //Set the first client as 1

	ArrayList<ClientThread> clients = new ArrayList<ClientThread>(); //What they will see
	
	ArrayList<String> clientName = new ArrayList<String>(); //Their names

	TheServer server;
	private Consumer<Serializable> callback;
	
//	Object lock = new Object();

	
//	Object lock = new Object();
//	Object lock2 = new Object();
//	
	boolean flag=false;
	Socket connection;
	public static msgInfo clientTOserver;
	Server(){

	}
	
	public ArrayList<String> getClientName(){
		return clientName;
	}
	
	//Constructor
	Server(Consumer<Serializable> call){
	
		callback = call; 
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread{ //threading
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(5595);){ //Make sure it connects here
		    System.out.println("Server is waiting for a client!");
		    callback.accept("Server is waiting for a client!");

		  
			
		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), countClient);
				callback.accept("client has connected to server: " + "client #" + countClient);
				clients.add(c);
				clientName.add(Integer.toString(countClient));
				System.out.println("client added" + Integer.toString(countClient));

				callback.accept("Total number of clients: " + clients.size());
				
		    	System.out.println("list");
		    	System.out.println(clientName);
		    	System.out.println("--------");


				c.start();
				
				countClient++;
				count++;
				
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}


		class ClientThread extends Thread{
			
			Object lock = new Object();
			Object lock2 = new Object();

			Socket connection;
			int count2; //added
//			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count2 = count;
		//		updateClients1("client" + count);
			}
			
			
			public void updateClients2(String msgData) 
			{
				synchronized(lock2){
				System.out.println("in updtelient 2"); //Test
				System.out.println("index find : " + Integer.toString(count2)); //Test
		    	int index = clientName.indexOf(Integer.toString(count2)); //Show who it is
		    	System.out.println("index: " + Integer.toString(index)); //Test
		    	System.out.println("before"); //Test
		    	System.out.println(clientName); //Test
		    	
				msgData = "This is client: " + Integer.toString(count2) + "\n"; //Set this up
				
				try {
					clients.get(index).out.writeObject(msgData);  //If we are able do
				}
				
				
					catch(Exception e) {}
				}//sync
			}//
		

			
			public void updateClients1(String msgData) {
				synchronized(lock2){
//					msgData = "This is client: " + Integer.toString(count2) + "\n";
				for(int i = 0; i < clientName.size(); i++) { //loops through client list here
					msgData = msgData + " client: " + clientName.get(i) + "\n"; //Send from/to
				}
					
					for(int i = 0; i < clients.size(); i++) { //loops through client list here
						ClientThread t = clients.get(i);
						try {
	
							t.out.writeObject(msgData);
						}
						catch(Exception e) {}
					}
				}//sync
			}//
			
			
			public void updateClients(msgInfo msgData) {
				
				synchronized(lock2){
					if(msgData.getOption() == 1) //specific client
					{
						int clientNumber = msgData.clientName.get(0);
						for(String i: clientName) {
							if(clientNumber== Integer.valueOf(i)) {
								flag=true;
								//					System.out.println("enters here 1");
								System.out.println("client to send msg to in server: " + Integer.toString(clientNumber));

						    	
						    	System.out.println("index find : " + Integer.toString(clientNumber));
			
						    	int index = clientName.indexOf(Integer.toString(clientNumber));

								try {
									
									clients.get(index).out.writeObject(msgData.msg); 
									}
									
									catch(Exception e) {}
							}
								
						}
//						if(flag==false) {
//							
//							try {
//								
//								clients.get(count2).out.writeObject("Non-Reachable Receiver"); 
//								}
//								
//								catch(Exception e) {}
//						}
					}
					
					
//	//					System.out.println("enters here 1");
//						System.out.println("client to send msg to in server: " + Integer.toString(clientNumber));
//	
//				    	
//				    	//
//				    	
//				    	System.out.println("index find : " + Integer.toString(clientNumber));
//	
//				    	int index = clientName.indexOf(Integer.toString(clientNumber));
////				    	System.out.println("index: " + Integer.toString(index));
////				    	System.out.println("before");
////				    	System.out.println(clientName);
//	
//						try {
//							
//							clients.get(index).out.writeObject(msgData.msg); 
//							}
//							
//							catch(Exception e) {}
//					}
		
					
					if(msgData.getOption() == 2)//groupChat
					{

						for(int j = 0; j<msgData.clientName.size(); j++)
						{
					    	System.out.println("index to find: " + Integer.toString(msgData.clientName.get(j)));
							int index = clientName.indexOf(Integer.toString(msgData.clientName.get(j)));
					    	System.out.println("index: " + Integer.toString(index));

							
							try {
								clients.get(index).out.writeObject(msgData.msg); 
								}
								
								catch(Exception e) {}
						}
					}
			
					
					if(msgData.getOption() == 2)//groupChat
					{
								for(int j = 0; j<msgData.clientName.size(); j++)
								{
//							    	System.out.println("index to find: " + Integer.toString(msgData.clientName.get(j)));
									
									for(String i: clientName) {
										if(msgData.clientName.get(j)  == Integer.valueOf(i)) {
											int index = clientName.indexOf(Integer.toString(msgData.clientName.get(j)));		
											try {
												clients.get(index).out.writeObject(msgData.msg); 
												}
												
												catch(Exception e) {}
										}
											
									}
								}
					}
								
												
									
//									
//									int index = clientName.indexOf(Integer.toString(msgData.clientName.get(j)));
////							    	System.out.println("index: " + Integer.toString(index));
//			
//									
//									try {
//										clients.get(index).out.writeObject(msgData.msg); 
//										}
//										
//										catch(Exception e) {}
//								}
//							}
//					}
//					
					if(msgData.getOption() == 3 && msgData.clientName.get(0) == 0) //send to everyone
					{
//						System.out.println("enters 2");
						for(int i = 0; i < clients.size(); i++) { //loops through client list here
							ClientThread t = clients.get(i);
							try {
								t.out.writeObject(msgData.msg);
							}
							catch(Exception e) {}
						}
						
					} 
//				}//sync
				}// sync new
			}//updateclients
			
			public void run(){
				
				synchronized(lock){ //Make sure we synch
				//Similar to what we had before
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);

				}

				catch(Exception e) {
					System.out.println("Streams not open");
				}
				
				updateClients2("");
				updateClients1("---------------------\n" + "New list of avaiable clients: \n");


				 while(true) {
					    try {
					    	clientTOserver = (msgInfo) in.readObject(); //Receiving
					    	
					    	if(clientTOserver.clientName.size() == 1)
					    	{
					    		if(clientName.contains(Integer.toString(clientTOserver.clientName.get(0))) || clientTOserver.clientName.get(0) == 0 )
					    		{
						    		callback.accept("client #"+count2+" said: "+ clientTOserver.getMsg()  + " to "+ Integer.toString(clientTOserver.clientName.get(0)));
					    		}
					    		else
					    		{
						    		callback.accept("client #" + Integer.toString(clientTOserver.clientName.get(0)) + " does not exist try a different one ");
					    		}
					    		
					    	}
					    	else
					    	{
					    		for(int j = 0; j<clientTOserver.clientName.size(); j++)
					    		{
					    			if(clientName.contains(Integer.toString(clientTOserver.clientName.get(j))))
						    		{
					    				System.out.println("in more clientList");
							    		String sento = " ";
							    		for(int i = 0; i<clientTOserver.clientName.size(); i++)
							    		{
							    			sento = sento + Integer.toString(clientTOserver.clientName.get(i)) + ", ";
							    		}
							    		System.out.println("SENTO" + sento);
										callback.accept("client #"+count2+" said: "+ clientTOserver.getMsg()  + " to " + sento);
						    		}
					    			else
					    			{
							    		callback.accept("client #" + Integer.toString(clientTOserver.clientName.get(j)) + " does not exist try a different one ");
					    			}
					    		}
					    		

					    	}
					    	updateClients(clientTOserver);
					    	}
					    catch(Exception e) { //Client has left
					    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count2+ "....closing down!");
					    	callback.accept("Client #"+count2+" has left the server!"); //Add
					
//					    	System.out.println("index find : " + Integer.toString(count2));
					    	//get the index to output
					    	int index = clientName.indexOf(Integer.toString(count2));
//					    	System.out.println("index: " + Integer.toString(index));

//					    	
//					    	System.out.println("before");
//					    	System.out.println(clientName);
							clientName.remove(index);
//					    	System.out.println("after");
//					    	System.out.println(clientName);
//					    	
					    	count2--;
					    	count--;
					    	clients.remove(this); //each client should have a unique idetification number
					    	updateClients1("---------------------\n" + "New list of avaiable clients: \n");					    	break;
					    }
					}
				}//sync
			
			}//end of run
			
		
			
		}//end of client thread
}


	
	

	
