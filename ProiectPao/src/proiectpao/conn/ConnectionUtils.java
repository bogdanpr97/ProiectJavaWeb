package proiectpao.conn;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
	public static Connection getConnection() 
            throws ClassNotFoundException, SQLException {
      // (You can change to use another database.)
       return MySQLConnUtils.getMySQLConnection();
  }
   
    public static void closeQuietly(Connection conn) {
       try {
           conn.close();
       } catch (Exception e) {
    	  
       }
   }
  
}
