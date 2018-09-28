package protocols;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageParser {
	public void parseMessage(DataInputStream input, Socket connection) throws IOException {
		
		int length = input.available() < 100 ? input.available() : 100;

		ArrayList<Byte> bytes = new ArrayList<Byte>(length);
		
		for(int i = 0; i < length; i++){
			bytes.add(input.readByte());
		}
		
		//#region 
		//is HTTP
		
		StringBuilder builder = new StringBuilder();
		
		for(Byte b : bytes) {
			builder.append(Character.toChars(b));
		}

		String message = builder.toString();

		if(
				message.contains("GET")
				|| message.contains("POST")
				|| message.contains("PUT")
				|| message.contains("DELETE")
				|| message.contains("HEAD")
				|| message.contains("CONNECT")
				|| message.contains("OPTIONS")
				|| message.contains("extension-method")
		) {

			while(input.available() > 0) {
				builder.append((Character.toChars(input.readByte())[0]));
			}
			
			message = String.valueOf(builder.toString());
			
			//System.out.println(message);
			
			HTTPAdapter httpMessage = new HTTPAdapter(message);

			
//			System.out.println(httpMessage.toString());
			
			
			try {
				DataOutputStream out = new DataOutputStream(connection.getOutputStream());
				
				String responseMessage = HTTPMessageProcessor.getResponse(httpMessage);
				
				
				String response = 
						"HTTP/1.1 200 OK\r\n" + 
						"Server: localhost:12345\r\n" + 
						"Date: Sat, 08 Mar 2014 22:53:46 GMT\r\n" + 
						"Content-Type: text/html\r\n" + 
						"Content-Length: " + responseMessage.length() + "\r\n" + 
						"Last-Modified: Sat, 08 Mar 2014 22:53:30 GMT\r\n" + 
						"Connection: keep-alive\r\n" +  
						"\r\n" + responseMessage;
				
//				out.writeUTF(response);
				
				out.writeBytes(response);
				

				out.flush();
				out.close();
				
				System.out.println("Responded!");
				
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			return; //exit after recognition
		}
		
		//#endgerion
		
		
		
		
		
		

	}
}
