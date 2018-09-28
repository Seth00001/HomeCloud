package protocols;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import dbEngine.DataBaseWrapper;

public class HTTPMessageProcessor {
	
	private static DataBaseWrapper DBManager;
	
	public static String getResponse(HTTPAdapter args) throws SQLException {
		if(DBManager == null) {
			DBManager = new DataBaseWrapper("jdbc:sqlite:D:\\Hosted_Databases\\SQLiteDB.db");
		}
		
		System.out.println("URL: " + args.URL);
		
		String DBRequest = String.format("SELECT * FROM HTTPOperationRegistry WHERE URL = '%s'", args.URL);
		ResultSet operationSet = DBManager.ExecuteQuery(DBRequest);
		

		String response = "";
		String operation = "";
		if(operationSet.next()) {
			operation = operationSet.getString("OperationKey");
		}
		
		switch(operation) {
			case "testOperation":{
				response = testOperation(args);
				break;
			}
			
			case "GAME.getDialogResult":{
				response = GAME_getDialogResult(args);
				break;
			}
			
			case "GAME.setDialogResult":{
				response = GAME_setDialogResult(args);
				break;
			}
			case "Resources\\Nana\\oops.nobigdeal.com.ua.html":{
				response = MainDomainPage(operation);
				break;
			}
			
			
			default:{
				response = defaultOption(args);
			}
		}
		
		return response;
	}
	
	public static String defaultOption(HTTPAdapter args) {
		String result = "<html>\r\n" + 
				"   <head>\r\n" + 
				"      <title>HelloSite</title>\r\n" + 
				"   </head>\r\n" + 
				"   <body>\r\n" + 
				"      Hello world! from default\r\n" + 
				"   </body>\r\n" + 
				"</html>";
		
		return result;
	}
	
	public static String testOperation(HTTPAdapter args) {
		String result = "<html>\r\n" + 
				"   <head>\r\n" + 
				"      <title>HelloSite</title>\r\n" + 
				"   </head>\r\n" + 
				"   <body>\r\n" + 
				"      Hello world! from testOperation\r\n" + 
				"   </body>\r\n" + 
				"</html>";
		
		return result;
	}
	
	public static String GAME_getDialogResult(HTTPAdapter args) {
		String response = "0";
		String select = String.format(
				"SELECT * " + 
				"FROM DialogResponses " +
				"WHERE questionId = %s " + 
				"LIMIT 3 OFFSET 0", args.URLArgs.get("questionId"));
		
		System.out.println(System.currentTimeMillis());

		try{
			ResultSet set = DBManager.ExecuteQuery(select);
			ArrayList<Integer> answers = new ArrayList<Integer>();
			while(set.next()) {
				answers.add(set.getInt("responseId"));
			}
			
			if(answers.size() > 1) {
				response = String.format("%s", answers.get((int) Math.round(Math.random() *( answers.size() - 1))));
			}
			else {
				response = "0";
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public static String GAME_setDialogResult(HTTPAdapter args) {
		String response = "0";
		
		String select = String.format(
				"SELECT *\r\n" + 
				"FROM DialogResponses\r\n" + 
				"WHERE questionId = %s \r\n" + 
				"ORDER BY timestampLabel desc \r\n " + 
				"LIMIT 1 OFFSET 2", args.URLArgs.get("questionId"));
		
		String insert = String.format(
				"INSERT INTO DialogResponses\r\n" + 
				"(questionId, responseId, timestampLabel)\r\n" + 
				"VALUES\r\n" + 
				"(%s, %s, DATETIME('NOW'))", 
				args.URLArgs.get("questionId"), 
				args.URLArgs.get("responseId"));
		
		
		int count = (int) Math.round(Math.random() * 2);
			
		try{
			DBManager.ExecuteScript(insert);
			ResultSet set = DBManager.ExecuteQuery(select);
			if(set.next()) {
				String datetime = set.getString("timestampLabel");
				
				System.out.println(datetime + " "  + args.URLArgs.get("questionId"));
				
//				System.out.println(String.format("DELETE FROM DialogResponses WHERE timestampLabel < '%s' AND questionId = %s", 
//						datetime, 
//						args.URLArgs.get("questionId")));
				
				DBManager.ExecuteScript(String.format("DELETE FROM DialogResponses WHERE timestampLabel < '%s' AND questionId = %s", 
						datetime, 
						args.URLArgs.get("questionId")));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			response = String.format("%s", e.hashCode());
		}
		
		return response;
	}
	
	public static String MainDomainPage(String path) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("");
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("D://Eclipse Workspaces//HomeCloud//HomeCloud//Resources//Nana//oops.nobigdeal.com.ua.html")))){
			
			System.out.println();
			Iterator<String> i = reader.lines().iterator();
			while(i.hasNext()) {
				builder.append(i.next());
			}
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(builder.toString());
		return builder.toString();
	}
	
}
