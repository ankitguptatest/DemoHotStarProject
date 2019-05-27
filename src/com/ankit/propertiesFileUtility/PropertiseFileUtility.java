package com.ankit.propertiesFileUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.ankit.JavaUtility.MachineSearch;


public class PropertiseFileUtility {
	
	private Properties propObj = null;
	
//	public static String configParameter = ObjectRepository
	
	/**
	 * This method will return all the properties file data into Map with
	 * Key and value pair.
	 * @author Ankit
	 * @param path of the properties file
	 * @return HashMap which contains all the properties file data
	 * @throws Exception 
	 */
	public Map<String, String> getConfig(String dir, String fileName){
		String path = new MachineSearch().serachMachineForFile(dir,fileName);
		HashMap<String, String> map = new HashMap<String, String>();
		try{
		FileInputStream fisObj = new FileInputStream(path);
		propObj = new Properties();
		propObj.load(fisObj);
		map = (HashMap<String, String>) getElements();
		if (map.size() > 0)
			System.out.println("Config File has been copied "+path);
		}catch(Exception e){
			System.out.println("Unable to load properties file: "+path);
			e.printStackTrace();
			System.exit(0);
		}
		return map;
	}
	
	/**
	 * This method will read all the keys and values from the properties file
	 * and save in the hashMap
	 * @author Ankit
	 * @return properties file value into HashMap in Key and value pair
	 */
	private Map<String, String> getElements(){
		Enumeration<?> eKeys = propObj.keys();
		HashMap<String, String> map = new HashMap<String, String>();
		while(eKeys.hasMoreElements()) {
			String key = (String)eKeys.nextElement();
			String value = (String)propObj.get(key);
			map.put(key, value);
		}
//		System.out.println(map);
		return map;
	}
	
	
	/**
	 * Get the wait properties file and load into HashMap
	 * @author Ankit
	 * @return return wait config in HashMap 
	 * @throws Exception 
	 */
	public HashMap<String, Integer> loadWaitConfig(){
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			String current = new java.io.File( "." ).getCanonicalPath();
			System.out.println(current);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		map = (HashMap<String, String>) new PropertiseFileUtility().getConfig(System.getProperty("user.dir")+File.separator+"src", "WaitsConfigFile.properties");
		HashMap<String, Integer> mapInteger = new HashMap<String, Integer>();
		for (String key: map.keySet()) {
			//		System.out.println("key : " + key);
			//		System.out.println("value : " + map.get(key));
			int value = Integer.parseInt(map.get(key));
			mapInteger.put(key, value);
		}
		return mapInteger;
	}

}
