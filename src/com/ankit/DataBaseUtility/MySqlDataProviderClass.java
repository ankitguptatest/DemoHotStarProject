package com.ankit.DataBaseUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ankit.DataMaps.CommonDataMaps;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;


public class MySqlDataProviderClass {
	
	public Connection con = null;
	
	public MySqlDataProviderClass(){
		 DataBasefileConnections ds = new DataBasefileConnections();
		 con = (Connection) DataBasefileConnections.connectionObj;
	}
	
	
	public  MySqlDataProviderClass(String mysql,String portNumber, String userName, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//		String userName = CommonDataMaps.masterConfigValues.get("DataBaseUserName");
			//		String password = CommonDataMaps.masterConfigValues.get("DataBasePassword");
			//		String portNumber = CommonDataMaps.masterConfigValues.get("DataBasePortNumber");
			String schema = CommonDataMaps.masterConfigValues.get("DataBaseSchema");
			mysql = "jdbc:mysql://"+mysql+":"+portNumber;
			con = DriverManager.getConnection(mysql,userName,password);
			System.out.println("Mysql connection: "+mysql+" "+userName+" "+password);
		} catch (ClassNotFoundException e) {
			System.out.println("Unable to connect with Mysql "+mysql);
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Unable to connect with Mysql "+mysql);
			e.printStackTrace();
		}
	}
	

	public List<String> getQueryData(String query, String column) throws SQLException{
		List<String> ls = new ArrayList<String>(); 
		//		System.out.println("schema name:  "+con.getSchema());
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while((rs != null) && (rs.next())){
			System.out.println(rs.getString(column));
			String data = rs.getString(column);
			if(data != null && !data.isEmpty())
				ls.add(data);
		}
		System.out.println(query+"\n :data: "+ls);
		return ls;
	}
	
	public Object[][] getDataFromMysql(String query, String testData) throws Exception{
		
		Statement st = null;
    	 String[] reqHeaders = testData.split(",");
    	 String[][] obj = null;
    	 ResultSet rs = null;
    	 try {
    		 st = con.createStatement();
    		 System.out.println("Query is: "+query);
    		 rs = st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL exception occured "+e.getMessage());
			System.out.println("Exception occured for: "+query);
			System.exit(0);			
		}catch(Exception e){
    		 System.out.println("Another Exception ocured rather than SQL Exception");
    		 e.printStackTrace();
//    		 System.exit(0);    		 
    	 }
//    	 System.out.println("row: "+rs.getCount());
    	 obj = new String[getRowNum(query)][reqHeaders.length];
    	 int row =0;
    	 try {
    		 while((rs != null) && (rs.next())){
				 for(int col =0; col <reqHeaders.length; col++){
					 String head = reqHeaders[col];
					 obj[row][col] = rs.getString(head.trim());
//					 System.out.println(rs.getString(head.trim()));
				 }
				 row++;
			 }
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("SQL exception while fetching data :"+e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("Another exception occured while fetching data :"+e.getMessage());
		}
    	 return obj;
	}
	
	public int getRowNum(String query) throws SQLException{
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		int numberRow = 0;
		while(rs.next()){
	        numberRow++;
	    }
		return numberRow;
	}
	
	
}
