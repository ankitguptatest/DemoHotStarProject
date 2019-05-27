package com.ankit.DataMaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ankit.reporting.SuiteReporting;

public class CommonDataMaps {
	
	/**
	 * TestFrameWork MasterFile {@link HashMap} of type (String, String)
	 */
	public static HashMap<String, String> masterConfigValues = new HashMap<String, String>();
	
	
	/**
	 * Common wait for Selenium and Appium {@link HashMap} of type (String, Integer)
	 */
	public static HashMap<String, Integer> waitConfigValues = new HashMap<String, Integer>();
	
	
	/**
	 * Have all the data from the object repository contains as PageName, Page Element name, locators and their value.
	 * of type HashMap({String, HashMap{String, HashMap{String, String}}})
	 */
	public static HashMap<String, HashMap<String,HashMap<String, String>>> objectRepoMapValues = new HashMap<String, HashMap<String,HashMap<String, String>>>();

	
	/**
	 * Have all the data from the MailConfig.properties file
	 * HashMap({String, String})
	 */
	public static HashMap<String,String> mailConfig = new HashMap<String, String>();
	
	
	/**
	 * It will have all the add new business URls from where we can add new customers data in out plateform.
	 * All the data came from "https://birdeye.com/sitemap.xml"
	 */
	public static List<String> addNewBusinessUrls = new ArrayList<String>();
	
	/**
	 * WebDriver and ReportObject {@link HashMap} of type (WebDriver, Integer)
	 */
//	public static HashMap<String, Integer> waitConfigValues = new HashMap<String, Integer>();
	
	public static HashMap<String, SuiteReporting> classObj = new HashMap<String, SuiteReporting>();
	
}
