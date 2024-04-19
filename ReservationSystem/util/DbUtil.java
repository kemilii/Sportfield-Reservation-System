package reservationSystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DbUtil {
	
    private static ResourceBundle bundle = ResourceBundle.getBundle("reservationSystem/resources/db"); //这里的名字要补齐！！
    
    /**
     * Register the driver.
     */
    static {
    	try {
			Class.forName(bundle.getString("driver"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Get database connection object.
     * @return Return connection object.
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException{
    	String url = bundle.getString("url");
    	String user = bundle.getString("user");
    	String password = bundle.getString("password");
        return DriverManager.getConnection(url, user, password);
    }
    
    /**
     * Release resources.
     * @param conn 
     * @param st 
     * @param rs 
     */
	public static void close(Connection conn, Statement st, ResultSet rs){ //PrepareStatement is the subclass of Statement.
		if(st!=null) try {
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
