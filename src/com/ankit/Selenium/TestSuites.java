package com.ankit.Selenium;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.openqa.selenium.WebDriver;

import com.ankit.reporting.NexusSMSReport;
import com.ankit.reporting.PageLoadReport;
import com.ankit.reporting.SuiteReporting;
import com.ankit.reporting.TestReporting;
import com.ankit.restAssured.api.ApiDefaultConfig;
import com.ankit.restAssured.api.BaseApiDefaultConfig;

import io.restassured.response.Response;

public class TestSuites implements Cloneable{
	private SuiteReporting suitReport = null;
	private PageLoadReport pageLoadReportObj = null;
	private NexusSMSReport nexusSMSReportObj = null;
	private SeleniumDriver sd = null;
	private TestReporting testReport = null;
	private String testCaseFileName = "";
	public long mtestStartTime = 0;
	public long mtestCompleteTime = 0;
	public int failedCount = 0;
	private WebDriver driver = null;
	private FindLocators fl = new FindLocators();
	private String individulaSuiteReportFolder ="";
	private String individulaSuiteReportFile = "";
	private String pageLoadReportFile= "";
	private  long mStartTime = 0;
	private  String startTime = "";
	private int loopCount = 0;
	private boolean browserConsoleLog = false;
	public String baseURl = "";
	public String publicAPIbaseURl = "";
	public BaseApiDefaultConfig apiClassObj = null;
	public String sessionToken = "";
	public HashMap<String, String> map = null;
	public boolean sendPageLoadReportOfLinks = false;
	
	
	
	public void setPageLoadReportFile(String pageLoadReportFile){
		this.pageLoadReportFile = pageLoadReportFile;
	}
	
	public String getPageLoadReportFile(String pageLoadReportFile){
		return this.pageLoadReportFile;
	}
	
	public void setBrowserConsoleLogs(String browserConsoleLogs){
		
		if ((browserConsoleLogs != null) && browserConsoleLogs.equalsIgnoreCase("true")){
			this.browserConsoleLog = true; 
		}
	}
	
	public boolean getBrowserConsoleLogs(){
		return this.browserConsoleLog;
	}
	
	public void setSuiteStartTime(String s){
		startTime = s;
	}
	
	public String getSuiteStartTime(){
	return startTime;	
	}
	
	public void setSuiteTimeInMiliSecond(long l){
		mStartTime = l;
	}
	
	public long getSuiteTimeInMiliSecond(){
		return mStartTime;
	}
	
	public FindLocators getFindLocator(){
		return fl;
	}
	
	public void setSuiteReportingObject(SuiteReporting s){
		suitReport = s;
	}
	
	public SuiteReporting getSuiteReportingObject(){
		return suitReport;
	}
	
	
	public void setPageLoadReportingObject(PageLoadReport s){
		pageLoadReportObj = s;
	}
	
	public PageLoadReport getPageLoadReportingObject(){
		return pageLoadReportObj;
	}
	
	public void setNexusReportingObject(NexusSMSReport s){
		nexusSMSReportObj = s;
	}
	
	public NexusSMSReport getNexusReportingObject(){
		return nexusSMSReportObj;
	}
	
	
	public void setLoopCount(int count){
		loopCount = count;
	}
	
	public int getLoopCount(){
		return loopCount;
	}
	
	public void increaseCount(){
		loopCount++;
	}
	
	public void setIndividulaSuiteReportFolder(String path){
		individulaSuiteReportFolder = path;
	}
	
	public String getIndividulaSuiteReportFolder(){
		return individulaSuiteReportFolder;
	}
	
	public void setIndividualSuiteReportFileName(String s){
		individulaSuiteReportFile =s;
	}
	
	public String getIndividualSuiteReportFileName(){
		return individulaSuiteReportFile;
	}
	
	public void setSeleniumDriver(SeleniumDriver s ){
		sd = s;
	}
	
	
	public SeleniumDriver getSeleniumDriver(){
		return sd;
	}
	
	public void setDriver(WebDriver d ){
		driver = d;
	}
	
	
	public WebDriver getDriver(){
		return driver;
	}
	
	public void setTestReporting(TestReporting t){
		testReport = t;
	}
	
	
	public TestReporting getTestReporting(){
		return testReport;
	}
	
	
	public void setTestCaseFileName(String t){
		 testCaseFileName = t;
	}
	
	public String getTestCaseFileName(){
		 return testCaseFileName;
	}
	
	
	public int getReviewCount(ApiDefaultConfig apiClassObj, Response response){
		int reviews = 0;
		if (response != null){
			if ((response.getStatusCode() == HttpStatus.SC_OK)){
				String count = apiClassObj.getValueFromJson(response, "originalReviewCount");
				reviews =  Integer.parseInt(count);
			}
		}
		return reviews;
	}
	
	public Object clone()  {
        try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
    }
	
}
