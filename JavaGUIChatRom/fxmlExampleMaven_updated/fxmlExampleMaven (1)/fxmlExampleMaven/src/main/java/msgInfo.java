import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class msgInfo implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	ArrayList<Integer> clientName = new ArrayList<Integer>();

	public String msg = "from msgInfo";
//	public int receiver = -93;
	public String receiver;

	public int option;
//	static int numClients = 3;
	
	msgInfo(String message,String client, int method)
	{
		msg = message;
		receiver = client;
		option = method;
//		clientName.add(Integer.parseInt(receiver));
//		
		for(int i =0; i<receiver.length(); i++)
		{
			char c = receiver.charAt(i); 
//			System.out.println("value msginfo: " + c);
			if(c != ',')
			{	
				int value = Character.getNumericValue(c);
				clientName.add(value);
//				System.out.println("value in setR: " + Integer.toString(receiver.get(i)));
			}
		}
			
//		for(int j = 0; j<receiver.length(); j++)
//		{
//			clientName.add(Integer.parseInt(receiver));
//			
//		}
//		

//		System.out.println(receiver.get(i));

	
		
//		setReceiver(receiver);
//		for(int i =0; i<client.size(); i++)
//		{
//			receiver.add(client.get(i));
//
//		}

	}
	public void clear()
	{
		msg = "from msgInfo";
//		receiver = -93;
		 receiver = "44";
		option = -93;
		clientName.clear();
	}
	
//	public void setNumClients(int val)
//	{
//		numClients = val;
//	}
//	
//	public int getNumClients()
//	{
//		return numClients;
//	}
//	
	
	
	public void setMsg(String message)
	{
		System.out.println("message in msginfo: " + message);
		msg = message;
	}
	public String getMsg()
	{
		return msg;
	}

//	public void setReceiver(String s)
//	{
//		clientName.add(Integer.valueOf(s));
//		
////		receiver.clear();
////		for(int i =0; i<s.length(); i++)
////		{
////			char c = s.charAt(i); 
//////			System.out.println("value msginfo: " + c);
////			if(c != ',')
////			{	
////				int value = Character.getNumericValue(c);
////				receiver.add(value);
//////				System.out.println("value in setR: " + Integer.toString(receiver.get(i)));
////			}
////		}
////		
////		System.out.println("receiver in msginfo");
////		for(int i =0; i<receiver.size(); i++)
////		{
////			System.out.println(receiver.get(i));
////
////		}
////		
////		
//		
//	}
//	public int getReceiver()
//	{
//		return receiver;
//	}
	
	public void setOption(int val)
	{
		option = val;
		
	}
	public int getOption()
	{
		return option;
	}
	
	
	
}
