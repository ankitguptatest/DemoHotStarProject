package com.ankit.DataBaseUtility;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.ankit.DataMaps.CommonDataMaps;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;




public class DataBasefileConnections {

	
	public static Object connectionObj;
	
	public DataBasefileConnections(){
		connectDB();
	}
	
	public void connectDB(){
		String databaseName = CommonDataMaps.masterConfigValues.get("DataBaseName");
		String databaseURL = CommonDataMaps.masterConfigValues.get("DataBaseUrl");
		if (databaseName.equalsIgnoreCase("Excel")){
			try {
				connectionObj = (Connection)new Fillo().getConnection(databaseURL);
			} catch (FilloException e) {
				System.out.println("Unable to connect with Excel "+databaseURL);
				e.printStackTrace();
				System.exit(0);
			}
		}
		else if (databaseName.equalsIgnoreCase("MSAccess")){
			String sDriver ="net.ucanaccess.jdbc.UcanaccessDriver";
			try {
				Class.forName(sDriver);
				connectionObj = DriverManager.getConnection(databaseURL);
			} catch (ClassNotFoundException e) {
				System.out.println("Unable to connect with MS Access "+databaseURL);
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("Unable to connect with MS Access "+databaseURL);
				e.printStackTrace();
			}
		}
		
		else if (databaseName.equalsIgnoreCase("mysql")){
			 try {
				Class.forName("com.mysql.jdbc.Driver");
				String userName = CommonDataMaps.masterConfigValues.get("DataBaseUserName");
				String password = CommonDataMaps.masterConfigValues.get("DataBasePassword");
				String portNumber = CommonDataMaps.masterConfigValues.get("DataBasePortNumber");
				String schema = CommonDataMaps.masterConfigValues.get("DataBaseSchema");
					databaseURL = "jdbc:mysql://"+databaseURL+":"+portNumber;
				connectionObj = DriverManager.getConnection(databaseURL,userName,password);
				System.out.println("Mysql connection: "+databaseURL+" "+userName+" "+password);
			} catch (ClassNotFoundException e) {
				System.out.println("Unable to connect with Mysql "+databaseURL);
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("Unable to connect with Mysql "+databaseURL);
				e.printStackTrace();
			}
		}
		else if (databaseName.equalsIgnoreCase("Oracle")){
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				String userName = CommonDataMaps.masterConfigValues.get("DataBaseUserName");
				String password = CommonDataMaps.masterConfigValues.get("DataBasePassword");
				databaseURL = "jdbc:oracle:thin:@"+databaseURL;
				connectionObj = DriverManager.getConnection(databaseURL,userName,password);
			} catch (ClassNotFoundException e) {
				System.out.println("Unable to connect with Oracle "+databaseURL);
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("Unable to connect with Oracle "+databaseURL);
				e.printStackTrace();
			}
		}
		else{
			System.out.println("No database matched : passed databaseName is: "+databaseName);
		}
	}
	
	
     public Object[][] getDataFromExcel(String sheetName, String testCaseName, String data) throws Exception{
    	 
    	 String query =  "Select * from "+sheetName+" where TestCaseName='"+testCaseName+"'";
    	 Connection con = (Connection) connectionObj;
    	 String[] reqHeaders = data.split(",");
    	 String[][] obj = null;
    	 Recordset rs = null;
    	 try {
			rs  = con.executeQuery(query);
		} catch (FilloException e) {
			e.printStackTrace();
			System.out.println("Fillo exception occured "+e.getMessage());
			System.out.println("Exception occured for: "+query);
//			System.exit(0);			
		}catch(Exception e){
    		 System.out.println("Another Exception ocured rather than Fillo Exception");
    		 System.out.println("Exception occured for: "+query);
    		 e.printStackTrace();
//    		 System.exit(0);    		 
    	 }
    	 try {
//    		 System.out.println("row: "+rs.getCount());
        	 obj = new String[rs.getCount()][reqHeaders.length];
        	 int row =0;
			while(rs.next()){
				 List<String> headerLineNames = rs.getFieldNames();
				 for(int col =0; col <reqHeaders.length; col++){
					 String head = reqHeaders[col];
					 obj[row][col] = rs.getField(head.trim());
				 }
				 row++;
			 }
			System.out.println("Total row for "+testCaseName+" is: "+row);
		} catch (FilloException e) {
			e.printStackTrace();
			throw new Exception("Fille exception while fetching data :"+e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("Another exception occured while fetching data :"+e.getMessage());
		}
    	 return obj;
     }	
     
     
     
     
	
}
