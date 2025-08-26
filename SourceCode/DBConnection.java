
package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private static final String URL ="jdbc:mysql://localhost:3306/farmconnect"; // database created
	private final static String USER = "root";                                  //Database name
	private final static String PASSWORD="newpassword";                         //Database password
	
	 public static Connection getConnection() {              //connection open
	        Connection conn = null;
	        try {
	            conn = DriverManager.getConnection(URL, USER, PASSWORD);
	        } catch (Exception e) {
	            System.out.println("Database connection failed: " + e.getMessage());
	        }
	        return conn;
	    }

}
