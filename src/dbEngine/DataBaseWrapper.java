package dbEngine;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class DataBaseWrapper {

	private Connection connection;
	
	public DataBaseWrapper(String path) throws SQLException{		
			connection = DriverManager.getConnection(path);
	}
	
	public ResultSet ExecuteQuery(String SQL) throws SQLException{
		Statement smt = connection.createStatement();
		ResultSet set = smt.executeQuery(SQL);
		
		return set;
	}
	
	public void ExecuteScript(String SQL) throws SQLException {
		Statement smt = connection.createStatement();
		smt.execute(SQL);
	}
	
	
	
}
