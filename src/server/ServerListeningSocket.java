package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListeningSocket {

	private boolean opened;
	private int port = 12345;
	private ServerSocket server;
	private RequestBalancer balancer;
	
	//#region get/set
	
	public void setBalancer(RequestBalancer bal) {
		balancer = bal;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	//#endregion
	
	//#region constructors
	
	public ServerListeningSocket(){	
		
	}
	
	//#endregion
	
	//#region work management
	
	public void claimPort() throws Exception {
		server = new ServerSocket(port);
	}
	
	public void claimPort(int port) throws Exception {
		this.port = port;
		server = new ServerSocket(port);
	}
	
	public void activate() {
		opened = true;
		while(opened) {
			try {								
				Socket s = server.accept();
				
				balancer.beginProcessing(s);

			} catch (IOException e) {
				e.printStackTrace();
				if(server == null 
				|| server.isClosed()
				|| !server.isBound()) {
					opened = false;
				}
			}
		}	
	}
	
	//#endregion
	
}
