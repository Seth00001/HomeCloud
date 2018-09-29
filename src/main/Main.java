package main;

import java.sql.*;

import dbEngine.DataBaseWrapper;
import server.RequestBalancer;
import server.ServerListeningSocket;

public class Main {

	public static void main(String[] args) {
				
		try {
			RequestBalancer balancer = new RequestBalancer();
			ServerListeningSocket server = new ServerListeningSocket();
			
			server.setBalancer(balancer);
			server.claimPort(32627);  
			server.activate();
			
		} catch (Exception e) {
			System.out.println("Failed to start");
			System.out.println(e.getStackTrace());
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		/*
		String path = "jdbc:sqlite:D:\\Hosted_Databases\\SQLiteDB.db";
		
		String statement = "SELECT * FROM TestTable";
		
		try {
			
//			Class.forName("org.sqlite.SQLiteDataSource");
			
			
			Connection con = DriverManager.getConnection(path);
			Statement smt = con.createStatement();
			ResultSet set = smt.executeQuery(statement);
			System.out.println(set.toString());
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		*/
		
		/*
		try {
			String path = "jdbc:sqlite:D:\\Hosted_Databases\\SQLiteDB.db";
			
			DataBaseWrapper wrapper = new DataBaseWrapper(path);
			
			String statement = "SELECT *\r\n" + 
					"FROM DialogResponses";
			
			ResultSet set = wrapper.ExecuteQuery(statement);
			
			System.out.println(set.next());
			
			
			
			
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		*/
		
		
		
		
		
	}

}
