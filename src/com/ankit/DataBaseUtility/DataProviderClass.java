package com.ankit.DataBaseUtility;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;

import com.ankit.DataMaps.CommonDataMaps;
import com.ankit.propertiesFileUtility.PropertiseFileUtility;
import com.ankit.restAssured.api.ApiDefaultConfig;

public class DataProviderClass {
	
	
	/**
	 * This annotation bind all the test data using DataProvider functionality 
	 * @author Ankit
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	public @interface TestData{
		String[] value() default "";
	}

	
	@DataProvider
	public static Object[][] getTestData(Method testName) throws Exception{
		
		TestData test = testName.getAnnotation(TestData.class);
		String[] array = test.value();
	    String annotationData = array[0];
	    String[] data = annotationData.split("=");
	    String testSheetAndName = data[0];
	    String[] arr = testSheetAndName.split(":");
	    String sheetName = arr[0];
	    String testCaseName = arr[1];
	    String testDatas = data[1];
	    DataBasefileConnections ds = new DataBasefileConnections();
	    return ds.getDataFromExcel(sheetName.trim(), testCaseName.trim(), testDatas);    
	}
	
	
	@DataProvider
	public static Object[][] getAggregationsIP(Method testName) throws Exception{
		
		TestData test = testName.getAnnotation(TestData.class);
		String[] array = test.value();
	    String annotationData = array[0];
	    String[] data = annotationData.split(":");
	    String query = data[0];
	    String testData = data[1];
	    MySqlDataProviderClass ds = new MySqlDataProviderClass();
	    return ds.getDataFromMysql(query, testData);    
	}
	
	
	@DataProvider
	public static Object[][] getAggregationsUrls(Method testName) throws Exception{
		String projDir = System.getProperty("user.dir")+File.separator+"src";
		HashMap<String, String> aggregationUrlConfigValues = new HashMap<String,String>();
		PropertiseFileUtility prop = new PropertiseFileUtility();
		TestData test = testName.getAnnotation(TestData.class);
		String[] array = test.value();
		String annotationData = array[0];
		String propFileName = CommonDataMaps.masterConfigValues.get("AggregationFileName");
		String[][] obj = null;
//		aggregationUrlConfigValues = (HashMap<String, String>) prop.getConfig(projDir,annotationData);
		aggregationUrlConfigValues = (HashMap<String, String>) prop.getConfig(projDir,propFileName);
		obj = new String[aggregationUrlConfigValues.size()][3];
		int i = 0;
		ApiDefaultConfig apiClassObj = new ApiDefaultConfig();
		for (String keySet : aggregationUrlConfigValues.keySet()){
			//			System.out.println("Key is: "+ keySet);
			//			System.out.println("value is: "+aggregationUrlConfigValues.get(keySet));
			String []  fullKey = keySet.split("\\.");
			String source = fullKey[0];
			String sourceId = fullKey[2];
			sourceId = sourceId.split("_")[1];
			//			System.out.println(source+"  "+sourceId);
//			if (apiClassObj.checkgetRequest(aggregationUrlConfigValues.get(keySet),  HttpStatus.SC_OK)){
				obj[i][0] = aggregationUrlConfigValues.get(keySet);
				obj[i][1] = sourceId;
				obj[i][2] = source;
				i++;
//			}
		}
		return obj;
	}
	
	
}
