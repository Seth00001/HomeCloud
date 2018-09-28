package protocols;

import java.util.HashMap;
import java.util.Map;

public class HTTPAdapter {
	
	public String method;
	public String URL;
	public Map<String, String> URLArgs;
	public String protocolVersion;
	public String body;
	public Map<String, String> data;
	
	
	public HTTPAdapter() {
		data = new HashMap<String, String>();
		URLArgs = new HashMap<String, String>();
	}
	
	public HTTPAdapter(String message) {
		this();
		
		String[] rows = message.split("\r\n");
		String[] temp;

		try {
			temp = rows[0].split(" ");
			method = temp[0];
			
			URL = temp[1].split("\\?")[0];
			
			System.out.println(URL);
			
			
			if(temp[1].split("\\?").length > 1) {
				String[] urlParts = temp[1].split("\\?")[1].split("&");
				
				System.out.println(temp[1].split("\\?")[1].split("&").length);
				
				for(int i = 0; i < urlParts.length; i++) {
					try {
						URLArgs.put(urlParts[i].split("=")[0], urlParts[i].split("=")[1]);
						System.out.println(String.format("%s  %s", urlParts[i].split("=")[0], urlParts[i].split("=")[1]));
					}
					catch(Exception e) {
						System.out.println(String.format("Cannot parse URL args. %s", urlParts[i]));
					}
		
				}
			}
			
			
			
			protocolVersion = temp[2];
		
			for(int i = 1; i < rows.length; i++) {
				if(!rows[i].equals("")) {
					temp = rows[i].split(": ");
					data.put(temp[0], temp[1]);
					
				}
				else {
					body = rows[i + 1];
					break;
				}
			}

		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		
		builder.append(method + " " + URL + " " + protocolVersion + "\r\n");
		
		System.out.println(data.keySet().size());
		
		for(String s : data.keySet()) {
			builder.append(s + ": " + data.get(s) + "\r\n");
		}
		
		builder.append("\r\n" + (body == null ? "": body));
		
		return builder.toString();
	}
	
}
