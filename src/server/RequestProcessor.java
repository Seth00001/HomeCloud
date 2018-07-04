package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import protocols.MessageParser;

public class RequestProcessor extends Thread{
	
	private Socket socket;
	private int timeout = 100;
	
	//#region get/set
	
	@Deprecated
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	//#endregion
	
	//#region static distinguish contract type
	public static boolean isHttp(ArrayList<Integer> data) {
		
		char[] charData = new char[data.size()];
		String stringData = "";
		for(int i = 0; i < data.size(); i++) {
			stringData += String.copyValueOf(Character.toChars(data.get(i)));
		}
		
		String[] parts = stringData.split(" ");
		

		if(parts.length >= 3) {
			if(!parts[2].split("/")[0].equals("HTTP")) {
				return false;
			}
		}
		else {
			return false;
		}
		return true;
	}
	
	//#endregion
	
	//#region constructors
	
	public RequestProcessor() {
		super();
	}
	
	public RequestProcessor(Runnable runnable) {
		super(runnable);
	}
	
	//#endregion
	
	//#region threading
	
	@Deprecated
	public void start() {
		super.start();
	}
	
	public void start(Socket socket) { 
		this.socket = socket;
		super.start();
	}
	
	public void run() {
		int waitCounter = 0;
		
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			
			if(input.available() == 0) {
				return;
			}
			
			new MessageParser().parseMessage(input, socket); 
			
			//#region
			
//			for(int i = 0; i < 50;i++)
//			{
//				try {
//					if(input.available() > 0) {
//						data.add((int) input.readByte());
//						//System.out.print(Character.toChars(data.get(i)));
//						waitCounter = 0;
//					}
//					else {
//						Thread.currentThread().join(1);
//						waitCounter++;
//						if(waitCounter >= timeout) {
//							break;
//						}
//					}
//					
//				}
//				catch(Exception e) {
//					break;
//				}
//			}
//			
//			try {
//				Thread.currentThread().join(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			//System.out.println("HTTP check");
//			
//			System.out.println(isHttp(data));
//			if(isHttp(data)) {
//				httpInput(data, input, socket);
//			}
//			
			//#endregion
			
			input.close();
		}
		catch(IOException e) {
			//There is nothing to do on failure. Error logging?
			e.printStackTrace();
		}
		finally {
			//cleaning the queue of RequestBalancer can be here
		}
	}
	
	//#endregion
	
	//#region parsing contract types
	//removed
	public void httpInput(ArrayList<Integer> readedBytes, DataInputStream stream, Socket connection) {
		
		for(Integer i : readedBytes) {
			System.out.print(Character.toChars(i));
		}
		
		try {
			while(stream.available() > 0) {
				System.out.print(Character.toChars(stream.readByte()));
			}
		} catch (IOException e1) {
		}
		
		System.out.println("message got!");
		
		try {
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			
			String message = 
					"<html>\r\n" + 
					"   <head>\r\n" + 
					"      <title>HelloSite</title>\r\n" + 
					"   </head>\r\n" + 
					"   <body>\r\n" + 
					"      Hello world!\r\n" + 
					"   </body>\r\n" + 
					"</html>";
			
			
			String response = 
					"HTTP/1.1 200 OK\r\n" + 
					"Server: localhost:12345\r\n" + 
					"Date: Sat, 08 Mar 2014 22:53:46 GMT\r\n" + 
					"Content-Type: text/html\r\n" + 
					"Content-Length: " + message.length() + "\r\n" + 
					"Last-Modified: Sat, 08 Mar 2014 22:53:30 GMT\r\n" + 
					"Connection: keep-alive\r\n" +  
					"\r\n" + message;
			
//			out.writeUTF(response);
			
			out.writeBytes(response);
			

			out.flush();
			out.close();
			
			System.out.println("Responded!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//#endregion 
	
	
}
