package server;

import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dbEngine.DataBaseWrapper;

public class RequestBalancer {
	//should have done a pool here, but I did not
	//simple redirection is enough for pre-alpha
	//private ArrayList<RequestProcessor> processors;
	
	
	public static HashMap<String, String> webpages;
	
	public RequestBalancer() throws Exception {
		
		webpages = new HashMap<String, String>();
		
		try {
			DataBaseWrapper wrapper = new DataBaseWrapper("jdbc:sqlite:Data//sysdatabase.db");
			
			String getEndpointsScript = "SELECT *\r\n" + 
					" FROM ListEndpoints le\r\n" + 
					"	LEFT JOIN ListWebPages lw ON (le.id = lw.endpointId)\r\n" + 
					" WHERE le.endpointTypeId = 1\r\n" + 
					"	AND lw.filePath IS NOT NULL";
			
			ResultSet set = wrapper.ExecuteQuery(getEndpointsScript);
			
			while(set.next()) {
				 webpages.put(set.getString("URL"), set.getString("filePath") );
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to initialize basic operations");
		}
		
	}
	
	public void beginProcessing(Socket socket) {
		RequestProcessor proc = new RequestProcessor();
		proc.start(socket);
		
		//processors.add(proc);
		
	}
	
	
	
	
	public class Operation{
		
		
		
		
		
	}
	
}
