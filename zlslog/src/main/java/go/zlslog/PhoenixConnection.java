package go.zlslog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.netflix.config.DynamicPropertyFactory;

class PhoenixConnection {
	 private static PhoenixConnection instance = null; 
	
	 public static PhoenixConnection getInstance() {  
	        if (instance == null) {  
	            synchronized (PhoenixConnection.class) {  
	                if (instance == null) {  
	                    instance = new PhoenixConnection();  
	                }  
	            }  
	        }  
	        return instance;  
	    }  
	
	
	public static Connection GetConnection() throws SQLException {
		// mysql
		Logger logger = Logger.getLogger(PhoenixConnection.class);
		try {
			Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("class for name error:");
		}  
		Connection  conn = DriverManager.getConnection("jdbc:phoenix:zookeeperIp:2181");  
		conn.setAutoCommit(true);
		return conn;

	}
}
