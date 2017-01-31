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
	    SetCommit();
	    SetIsolationLevel(Connection.TRANSACTION_SERIALIZABLE);
   }
   
   private void SetCommit() throws SQLException {
	   _connection.setAutoCommit(false);
	   boolean mode = _connection.getAutoCommit();
	   if(mode) {
		   throw new RuntimeException("Failed to set autocommit");
	   }
   }
   
   private void SetIsolationLevel(int level) throws SQLException {
	   _connection.setTransactionIsolation(level);
	   int setLevel = _connection.getTransactionIsolation();
	   if(setLevel != level) {
		   throw new RuntimeException("failed to set isolation level");
	   }
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
		   throw new SQLException(e.getMessage(), e);
	   } finally {
		   stm.close();
	   }
   }
   
   public void RunNonQuery(String statement) throws SQLException {
	   RunNonQuery(statement, new Object[0]);
   }
   
   public void RunNonQuery(String statement, Object[] args) throws SQLException {
	   PreparedStatement stm = _connection.prepareStatement(statement);
	   for(int i = 1; i <= args.length; i++) {
		   Object value = args[i-1];
		   if(value instanceof String) {
			   stm.setString(i, (String)value);
		   } else if(value instanceof Long) {
			   stm.setLong(i, (long)value);
		   } else {
			   throw new RuntimeException("Unexpected type: " + value.getClass().getSimpleName());
		   }
	   }
	   try {
		   int rowsUpdated = stm.executeUpdate();
		   if(rowsUpdated <= 0) {
			   throw new SQLException("updated rows: " + rowsUpdated);
		   }
		   _connection.commit();
	   } catch(Exception e) {
		   _connection.rollback();
		   throw new SQLException(e.getMessage(), e);
	   } finally {
		   stm.close();
	   }
   }

	public void Close() throws SQLException {
		_connection.close();
	}
}
