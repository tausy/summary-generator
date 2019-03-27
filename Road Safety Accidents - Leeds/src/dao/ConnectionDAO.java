package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Class to handle Database
 * 
 * @author
 *
 */
public class ConnectionDAO {

	/**
	 * Method to get connection handle to DB
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		Connection conn;
		try {
			conn = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPassword);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method to execute the query in Database
	 * 
	 * @param query
	 * @return void
	 */
	public static void executeQuery(String query) {
		Connection conn = getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
