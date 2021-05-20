import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;



public class Client extends Thread{ //threading

	
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	String msg = "from client"; //Default message
//	int clientNum = -88;
	String clientNum = "-88"; //Start with the number
	int option = -100; // Start with this option
	
	private Consumer<Serializable> callback;
	
	public msgInfo msgData; //Instance
	public String random; //Random
	
	Client(Consumer<Serializable> call,String message,String receiver, int op){
	
//		System.out.println("enters here 7");

//		msg = "";
		callback = call;
		msg = message;
		clientNum = receiver;
		option = op;
//		for(int i =0; i<receiver.size(); i++)
//		{
//			clientNum.add(receiver.get(i));
//
//		}
		msgData = new msgInfo(msg,clientNum,option);
		
	}
	
	public void run() {
		
		try {
		socketClient= new Socket("127.0.0.1",5595); //Choose this socket
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {
				Serializable random = (Serializable) in.readObject();
					callback.accept(random.toString());
//				if(random!=null){
//					callback.accept(random);
//				}else {
//					msgData = (msgInfo) in.readObject(); //sending to server instance of info class
//					System.out.println("message in client: " + msgData.getMsg());
//					callback.accept(msgData.getMsg());
//				}
			}
			catch(Exception e) {}
		}
	
    }
	/* Send this to person
	 * 
	 */
	public void send(msgInfo data) {
		
//		System.out.println("enters here 6");

		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
