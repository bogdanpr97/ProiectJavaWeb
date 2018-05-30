package proiectpao.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnUtils {
	public static Connection getMySQLConnection()
	         throws ClassNotFoundException, SQLException {
	     // Datele de conectare la baza
		 String hostName = "sql7.freemysqlhosting.net:3306";
	     String dbName = "sql7240304";
	     String userName = "sql7240304";
	     String password = "SXftPwgRp3";
	     return getMySQLConnection(hostName, dbName, userName, password);
	 }
	  
	 public static Connection getMySQLConnection(String hostName, String dbName,
	         String userName, String password) throws SQLException,
	         ClassNotFoundException {
	    
	     Class.forName("com.mysql.jdbc.Driver");
	  
	     String connectionURL = "jdbc:mysql://" + hostName + "/" + dbName;
	  
	     Connection conn = DriverManager.getConnection(connectionURL, userName,
	             password);
	     return conn;
	 }
}
