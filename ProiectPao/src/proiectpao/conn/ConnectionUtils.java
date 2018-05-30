package proiectpao.conn;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
	public static Connection getConnection() 
            throws ClassNotFoundException, SQLException {
      // Data de baze pe care o folosim
       return MySQLConnUtils.getMySQLConnection();
  }
   
    public static void closeQuietly(Connection conn) {
       try {
           conn.close();
       } catch (Exception e) {
    	  
       }
   }
  
}
