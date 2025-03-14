//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DatabaseUtils {
//    private static final String connStr = "jdbc:sqlserver://DESKTOP-SAVDUA5\\SQLEXPRESS;Database=servEase;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true;"; 
//    
//    public static void main(String[] args) {
//        try (Connection connection = DriverManager.getConnection(connStr)) {
//            System.out.println("Connection established.");
//        } catch (SQLException e) {
//            System.out.println("Error connecting to the DB.");
//            e.printStackTrace();
//        }
//    }
//}
//public static void main(String[] args) throws ClassNotFoundException, SQLException {
//	
//	Class.forName("com.mysql.cj.jdbc.Driver");
//	
//	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SDA_SQL","root","heamestm13");
//	System.out.println("Connection established.");
//	
//
//}DESKTOP-33MNSMN\SQLEXPRESS
//private static final String CONN_STR = "jdbc:sqlserver://DESKTOP-SAVDUA5\\SQLEXPRESS;Database=servEase;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true;";
	//jdbc:sqlserver://DESKTOP-SAVDUA5\SQLEXPRESS;Database=servEase;IntegratedSecurity=true;

//public class DatabaseUtils {
//    
////	private static final String CONN_STR = "jdbc:sqlserver://DESKTOP-33MNSMN\\SQLEXPRESS;Database=servEase;IntegratedSecurity=true;encrypt=true;trustServerCertificate=true;loginTimeout=30;\r\n"
////			+ "";
////    public static Connection getConnection() throws SQLException {
////        return DriverManager.getConnection(CONN_STR);
////    }
//public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		
//		Class.forName("com.mysql.cj.jdbc.Driver");
//		
//		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/servEase","root","heamestm13");
//		System.out.println("Connection established.");
//		
//
//	}
//}
package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {
    // Database credentials and URL
    private static final String URL = "jdbc:mysql://localhost:3306/servEase";
    private static final String USER = "root";
    private static final String PASSWORD = "22327654#";

    // Static block to load the driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found: " + e.getMessage());
        }
    }

    // Static method to get the connection
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error establishing connection: " + e.getMessage());
        }
        return connection;
    }
}
