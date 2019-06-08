package com.sumgen.DB;

import java.io.InputStream;
import java.io.StringReader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.sumgen.Config.Constants;

public class DBHandler {

	public boolean saveSummaryToDB(String summary) {
		boolean saveSuccess = false;
		try {
			Class.forName(Constants.DB_DRIVER_CLASS);
			Connection connection = DriverManager.getConnection(Constants.DB_CONNECTION_STRING, Constants.DB_USER_NAME, Constants.DB_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement("insert into summaries(id, summary, generation_time) values(sumgen.nextval, ?, systimestamp)");
			
			preparedStatement.setClob(1, new StringReader(summary));
			
		//executes the prepared statement
        int success = preparedStatement.executeUpdate();
        if(success == 1)
            saveSuccess = true;
        else
            saveSuccess = false;        
	} catch (Exception e) {
		e.printStackTrace();
	}
		return saveSuccess;
	}
	
	public static void main(String...s) {
		ArrayList<String> data = new DBHandler().fetchSummariesFromDB();
		System.out.println("Size:"+data.size());
		for(String str: data) {
			System.out.println(str);
		}
	}

	public ArrayList<String> fetchSummariesFromDB() {
		// TODO Auto-generated method stub
		ArrayList<String> data = new ArrayList<String>();
          try{
          Class.forName(Constants.DB_DRIVER_CLASS);
          Connection connection = DriverManager.getConnection(Constants.DB_CONNECTION_STRING, Constants.DB_USER_NAME, Constants.DB_PASSWORD);
          Statement stmnt = connection.createStatement();
			
          ResultSet rst = stmnt.executeQuery("select id, summary, to_char(generation_time,'DD-MM-YYYY HH24:mm:ss') from summaries");
          while(rst.next()){ 
                String ID = String.valueOf(rst.getInt(1));
                
                String summary="";
                Clob aclob = rst.getClob(2);
                InputStream ip = aclob.getAsciiStream();
                for (int c = ip.read(); c != -1; c = ip.read()) {
                    summary += (char)c;
                }
                ip.close();    
                String timestamp = rst.getString(3);    
                String dataRow = ID+"|"+summary+"|"+timestamp;
                data.add(dataRow);		
          }
          rst.close();
          stmnt.close();
          connection.close();
          }catch(Exception ex){ System.out.println(ex.getMessage()); }
          return data;
	}
}
