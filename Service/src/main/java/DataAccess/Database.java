package DataAccess;

import java.sql.*;

public class Database {
   // JDBC driver name and database URL
   //static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost/DistRestSample?autoReconnect=true&useSSL=false";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "class";
   
   private final Connection _connection;
   
   public Database() throws ClassNotFoundException, SQLException {
	    Class.forName(JDBC_DRIVER);
	    _connection = DriverManager.getConnection(DB_URL,USER,PASS);
	    _connection.setAutoCommit(false);
	    _connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
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
		   _connection.commit();
	   } catch(Exception e){
		   _connection.rollback();
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
