package DataAccess;

import java.sql.*;


public class Database {
	  // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/EMP";

	   //  Database credentials
	   static final String USER = "username";
	   static final String PASS = "password";
	   
	   private final Connection _connection;
	   
	   public Database() throws ClassNotFoundException, SQLException {
		   	//register JDBC driver
		    Class.forName("com.mysql.jdbc.Driver");
		    _connection = DriverManager.getConnection(DB_URL,USER,PASS);
	   }
	   
	   public void ExecuteReader(String statement, IRowReader reader) throws SQLException {
		   Statement stm = _connection.createStatement();
		   try{
			   ResultSet set = stm.executeQuery(statement);
			   try {
				   while(set.next()) {
					   	reader.Read(set);
				   }
				   
			   } finally {
				   set.close();
			   }
		   } finally {
			   stm.close();
		   }
	   }
	   
	   public void RunNonQuery(String statement) throws SQLException {
		   Statement stm = _connection.createStatement();
		   try {
			   ResultSet set = stm.executeQuery(statement);
			   set.close();
		   } finally {
			   stm.close();
		   }
	   }

	public void Close() throws SQLException {
		_connection.close();
	}
}
