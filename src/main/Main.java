package main;

import server.RequestBalancer;
import server.ServerListeningSocket;

public class Main {

	public static void main(String[] args) {
				
		try {
			RequestBalancer balancer = new RequestBalancer();
			ServerListeningSocket server = new ServerListeningSocket();
			
			server.setBalancer(balancer);
			server.claimPort(12345);
			server.activate();
			
		} catch (Exception e) {
			System.out.println("Failed to start");
			System.out.println(e.getStackTrace());
			e.printStackTrace();
		}
		
	}

}
