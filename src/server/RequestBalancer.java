package server;

import java.net.Socket;
import java.util.ArrayList;

public class RequestBalancer {
	//should have done a pool here, but I did not
	//simple redirection is enough for pre-alpha
	//private ArrayList<RequestProcessor> processors;
	
	public RequestBalancer() {
		
	}
	
	public void beginProcessing(Socket socket) {
		RequestProcessor proc = new RequestProcessor();
		proc.start(socket);
		
		//processors.add(proc);
		
	}
	
	
}
