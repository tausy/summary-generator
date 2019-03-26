package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public  class ConnectionDAO {


    public static Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(Constants.dbUrl,Constants.dbUser,Constants.dbPassword);
            return conn;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

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
