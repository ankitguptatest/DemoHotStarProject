package com.ankit.reporting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.ankit.DataMaps.CommonDataMaps;
import com.ankit.JavaUtility.JavaWrappers;
import com.ankit.Selenium.TestSuites;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import io.netty.util.internal.SystemPropertyUtil;
import io.restassured.response.Response;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.Har;


public class TestReporting extends SuiteReporting{
	
	public OutputStream htmlfile, logfile,proxyFile;
	public PrintStream printhtml, printlog,printProxyLog;
	public String testCaseName = "";
	private String logFileName ="";
	private String proxyFileName="";
	private String testReport ="";
//	private String screenShot = "";
	private String env = "";
	private String testCasePath ="";
	private int apiRow = 0;
	
	public String initilizeTestReport(TestSuites ts, String testName) throws IOException{
		env = CommonDataMaps.masterConfigValues.get("Env");
//		this.testCasePath = JavaWrappers.createDir(testSuitePath, testName).getAbsolutePath();
		this.testReport = ts.getIndividulaSuiteReportFolder()+File.separator+"TestCases";
		this.testCaseName = testName;
		String name = openfile();
		header();
		return name;
	}
	
	public String initilizeApiTestReport(TestSuites ts, String testName) throws IOException{
		env = CommonDataMaps.masterConfigValues.get("Env");
//		this.testCasePath = JavaWrappers.createDir(testSuitePath, testName).getAbsolutePath();
		this.testReport = ts.getIndividulaSuiteReportFolder()+File.separator+"TestCases";
		this.testCaseName = testName;
		String name = openfile();
		header();
		return name;
	}
	
	
	private String openfile() {
		String fileName = "";
		try {
			fileName = this.testCaseName+"_"+JavaWrappers.getCurrentTime("HH_mm_ss")+".html";
			logFileName = this.testCaseName+"_"+JavaWrappers.getCurrentTime("HH_mm_ss")+".txt";
			proxyFileName = this.testCaseName+"_"+JavaWrappers.getCurrentTime("HH_mm_ss")+".har";
			String logFile = this.testReport+File.separator+"Logs"+File.separator+ logFileName;
//			String proxyLogFile = this.testReport+File.separator+"Logs"+File.separator+ proxyFileName;
			String testCaseFile = this.testReport+File.separator+fileName;
			htmlfile = new FileOutputStream(new File(testCaseFile), true);
			printhtml = new PrintStream(htmlfile);
			if (CommonDataMaps.masterConfigValues.get("consoleLogs").equalsIgnoreCase("true")){
				String logPath = this.testReport+File.separator+"Logs";
				logfile = new FileOutputStream(new File(logFile),true);
//				proxyFile = new FileOutputStream(new File(proxyLogFile),true);
				printlog = new PrintStream(logfile);
//				printProxyLog = new PrintStream(proxyFile);
//				System.setOut(printlog);
//				System.setErr(printlog);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fileName;
	}
	
	
	public String addConsoleLogsToFile(TestSuites ts){
		Capabilities cap = ((RemoteWebDriver) ts.getDriver()).getCapabilities();
		String fileLink = "";
		String browserName = cap.getBrowserName().toLowerCase();
		if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("ch")){
			LogEntries logs = ts.getDriver().manage().logs().get("browser");
			for (LogEntry entry : logs) {
				//	            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
				if (entry.getLevel() == entry.getLevel().SEVERE)
					printlog.append(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage()+" "+ System.getProperty("line.separator"));
			}
			printlog.append(System.getProperty("line.separator"));
			printlog.append(System.getProperty("line.separator"));
			printlog.append("Performance Logs: "+ System.getProperty("line.separator"));
			logs = ts.getDriver().manage().logs().get(LogType.PERFORMANCE);
			for (LogEntry entry : logs) {
                 if(entry.toString().contains("\"type\":\"XHR\"") & entry.toString().contains("\"url\":\"https://birdeye.com/")) {
//				if (entry.getLevel() == entry.getLevel().SEVERE) {
					printlog.append(new Date(entry.getTimestamp()) + " " + entry.toString() +" "+ System.getProperty("line.separator"));
					printlog.append(System.getProperty("line.separator"));
					printlog.append(System.getProperty("line.separator"));
				}
			}
			printlog.close();
			
			//add proxy log:
//			BrowserMobProxy proxy = ts.getSeleniumDriver().getProxyObject(ts.getSeleniumDriver());
//			Har harObject = proxy.getHar();
//			// Write HAR Data in a File
////			File harFile = new File(pro);
//			try {
//				harObject.writeTo(proxyFile);
//			} catch (IOException ex) {
//				 System.out.println (ex.toString());
//			     System.out.println("Could not find file " + proxyFileName);
//			}
			
			fileLink = "<a href=\"" + "../TestCases/Logs/"+ logFileName+"\"><b>Browser_ConsoleLogs</b></a>";
			printhtml.println("</tbody>");
			printhtml.println("</table>");
			printhtml.println("<br>");
			printhtml.println("<h3 align='center'> <span style=\"color:blue\">"+fileLink+"</span> </h3>");
			printhtml.println("<br>");
			completeReport();
		}
		return fileLink;
	}
	
	
	
	public void addTestSteps(TestSuites ts, String taskPerformed, String info, String strPassFail) throws Exception {
		boolean snapshotPermitted = CommonDataMaps.masterConfigValues.get("ScreenShot").equalsIgnoreCase("true");
//		try {
			String value =  strPassFail.toUpperCase();
			String nameOfScreenShot ="";
			String imgLink = "";
			if(snapshotPermitted || strPassFail.equalsIgnoreCase("FAIL") || strPassFail.equalsIgnoreCase("WARNING") || strPassFail.startsWith("WAR")) {
				nameOfScreenShot = captureScreenShot(ts);
				if (value.equals("PASS")) {
				imgLink = "<a href=\"" + "../TestCases/ScreenShot/"+ nameOfScreenShot + "\">"+value+"</a>";
				}
				else if(nameOfScreenShot.equalsIgnoreCase("UnableToCapture")) {
					System.out.println("Unable to capture screenshot");
					value = "UnableToCapture";
					// adding this scenario when driver is in TIME OUT state
//					if(!strPassFail.equalsIgnoreCase("PASS"))
						strPassFail = "FAIL";
				}
				else{
					imgLink = "<a href=\"" + "../TestCases/ScreenShot/"+ nameOfScreenShot + "\"><font color='#FF7373'>"+value+"</a>";
				}
			}
			else {
				imgLink = value;
			}
			printhtml.append("<tr>");

			printhtml
			.append("<td width='35%' bgcolor='' valign='top' align='justify' ><font color='#000000' face='Tahoma' size='2'>"
					+ taskPerformed + "</font></td>");
			printhtml
			.append("<td width='42%' bgcolor='' valign='top' align='justify' ><font color='#000000' face='Tahoma' size='2'>"
					+ info + "</font></td>");
			if (strPassFail.toUpperCase().equals("PASS")) {
				printhtml
				.append("<td width='10%' bgcolor='' valign='middle' align='center'><b><font color='#000000' face='Tahoma' size='2'>"
						+imgLink+ "</font></b></td>");
			} else if (strPassFail.toUpperCase().equals("FAIL")) {
				printhtml
				.append("<td width='10%' bgcolor='' valign='middle' align='center'><b><font color='#FF7373' face='Tahoma' size='2'>"
						+imgLink+ "</font></b></td>");
//				throw new Exception("failures came");
			} else if (strPassFail.equals("")){
				printhtml
				.append("<td width='10%' bgcolor='' valign='middle' align='center'><b><font color='#FF7373' face='Tahoma' size='2'></font></td>");	
			}
			else {
				printhtml
				.append("<td width='10%' bgcolor='' valign='middle' align='center'><b><font color='#FF7373' face='Tahoma' size='2'>"
						+imgLink+"</font></b></td>");
			}
			printhtml
			.append("<td width='13%' bgcolor='' valign='middle' align='center' ><font color='#000000' face='Tahoma' size='2'>"
					+ JavaWrappers.getCurrentTime("HH:mm:ss") + "</font></td>");	
			printhtml.append("</tr>");
			
			if (strPassFail.toUpperCase().equals("FAIL")){
				throw new Exception("failures came");
			}
	}
	
	
	
	/*
	 * API test steps reporting file
	 * 
	 */
	public synchronized void addApiTestSteps(TestSuites ts, String requestDetails, Response rs, String strPassFail) throws Exception {
//		try {
			String value =  strPassFail.toUpperCase();
			String imgLink = value;
			printhtml.append("<tr>");
			requestDetails = requestDetails.replaceAll("\n", "<br/>");
			printhtml
			.append("<td width='35%' bgcolor='' valign='top' align='' ><font color='#000000' face='Tahoma' size='2'>"
					+ requestDetails + "</font></td>");
			if (rs == null){
				printhtml
				.append("<td width='42%' bgcolor='' valign='top' align='justify' ><font color='#000000' face='Tahoma' size='2'>"
						+ "<b>Api Response is NULL</b>");
				printhtml.append("</font></td>");
			}
			else{
			printhtml
			.append("<td width='42%' bgcolor='#D3D3D3' valign='top' align='justify' ><font color='#000000' face='Tahoma' size='2'>"
					+ "<b>Api Response_"+rs.getStatusCode()+", Time taken: "+rs.getTime()+" millisec</b>");
			formatResponse(rs);
			printhtml.append("</font></td>");
			}
			if (strPassFail.toUpperCase().equals("PASS")) {
				printhtml
				.append("<td width='10%' bgcolor='' valign='middle' align='center'><b><font color='#000000' face='Tahoma' size='2'>"
						+imgLink+ "</font></b></td>");
			} else if (strPassFail.toUpperCase().equals("FAIL")) {
				printhtml
				.append("<td width='10%' bgcolor='' valign='middle' align='center'><b><font color='#FF7373' face='Tahoma' size='2'>"
						+imgLink+ "</font></b></td>");
			} else {
				printhtml
				.append("<td width='10%' bgcolor='' valign='middle' align='center'><b><font color='#FF7373' face='Tahoma' size='2'>"
						+imgLink+"</font></b></td>");
			}
			printhtml
			.append("<td width='13%' bgcolor='' valign='middle' align='center' ><font color='#000000' face='Tahoma' size='2'>"
					+ JavaWrappers.getCurrentTime("HH:mm:ss") + "</font></td>");	
			printhtml.append("</tr>");
//			if (strPassFail.toUpperCase().equals("FAIL")){
//				throw new Exception("failures came");
//			}
	}
	
	
	
	
	/*
	 * API test steps reporting file
	 * 
	 */
	public synchronized void addApiTestSteps(TestSuites ts, String taskPerformed, String info, String strPassFail) throws Exception {
		try {
			String value =  strPassFail.toUpperCase();
			String imgLink = "";
			imgLink = value;
			printhtml.append("<tr>");
			printhtml
			.append("<td width='35%' bgcolor='' valign='top' align='justify' ><font color='#000000' face='Tahoma' size='2'>"
					+ taskPerformed + "</font></td>");
			printhtml
			.append("<td width='42%' bgcolor='' valign='top' align='justify' ><font color='#000000' face='Tahoma' size='2'>"
					+ info + "</font></td>");
			if (strPassFail.toUpperCase().equals("PASS")) {
				printhtml
				.append("<td width='10%' bgcolor='' valign='middle' align='center'><b><font color='#000000' face='Tahoma' size='2'>"
						+imgLink+ "</font></b></td>");
			} else if (strPassFail.toUpperCase().equals("FAIL")) {
				printhtml
				.append("<td width='10%' bgcolor='' valign='middle' align='center'><b><font color='#FF7373' face='Tahoma' size='2'>"
						+imgLink+ "</font></b></td>");
			} else {
				printhtml
				.append("<td width='10%' bgcolor='' valign='middle' align='center'><b><font color='#FF7373' face='Tahoma' size='2'>"
						+imgLink+"</font></b></td>");
			}
			printhtml
			.append("<td width='13%' bgcolor='' valign='middle' align='center' ><font color='#000000' face='Tahoma' size='2'>"
					+ JavaWrappers.getCurrentTime("HH:mm:ss") + "</font></td>");	
			printhtml.append("</tr>");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (strPassFail.toUpperCase().equals("FAIL")){
			throw new Exception("failures came");
		}
	}
	
	public void formatResponse(Response rs){
		try {
			String json_String_to_print = rs.getBody().asString();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			try{
				json_String_to_print = gson.toJson(jp.parse(json_String_to_print));
			}catch (Exception e) {
				json_String_to_print = rs.getStatusLine();
			}
//			System.out.println(gson.toJson(jp.parse(json_String_to_print)));
//			apiResponse = apiResponse.replaceAll("\n", "<br/>");
//			apiResponse = apiResponse.replaceAll("\"", "\\\\\"");
			if (json_String_to_print == null || json_String_to_print.isEmpty()){
				json_String_to_print =" ";
			}
//			if (json_String_to_print.length() > 10){
			printhtml.append("<br/><button id=\"expandCollapse"+apiRow+"\" onclick=\"toggle_visibility('response"+apiRow+"','expandCollapse"+apiRow+"');\">+</button>"+
			"<div id=\"response"+apiRow+"\" style=\"display:none;\">"+
			json_String_to_print+
			"</div>");
//			}
//			else{
//				printhtml.append("<br/>"+json_String_to_print);
//			}
//			printhtml.append("<div data-role=\"collapsible\"><h1>+</h1><p>Status Code"+rs.getStatusCode()+"<br>"+gson.toJson(jp.parse(json_String_to_print))+"</p> </div>");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		apiRow++;
	}

	
	
	public void addTestSteps(TestSuites ts, String taskPerformed, String info, String strPassFail, boolean screenshot) {
		boolean snapshotPermitted = CommonDataMaps.masterConfigValues.get("ScreenShot").equalsIgnoreCase("true");
		try {
			String value =  strPassFail.toUpperCase();
			String nameOfScreenShot ="";
			String imgLink = "";
//			System.out.println(ts.getClass().getName());
			if (taskPerformed.equalsIgnoreCase("Completed Test") && strPassFail.equalsIgnoreCase("FAIL") && ts.getBrowserConsoleLogs()){
				Thread.sleep(CommonDataMaps.waitConfigValues.get("second_100"));
			}
			if((snapshotPermitted || strPassFail.equalsIgnoreCase("FAIL")) && screenshot) {
				nameOfScreenShot = captureScreenShot(ts);
				if(nameOfScreenShot.equalsIgnoreCase("UnableToCapture")) {
					System.out.println("Unable to capture screenshot");
					value = "UnableToCapture";
	                  // adding this scenario when driver is in TIME OUT state
                    strPassFail = "FAIL";
				}
				else {
//				imgLink = "<a href=\"" + "../TestCase/ScreenShot/"+ nameOfScreenShot + "\">"+value+"</a>";
				imgLink = "<a href=\"" + "../TestCases/ScreenShot/"+ nameOfScreenShot + "\">"+value+"</a>";
				}
			}
			else {
				imgLink = value;
			}
//			System.out.println("Name of screenshot: "+nameOfScreenShot);
			printhtml.append("<tr>");

			printhtml
			.append("<td width='35%' bgcolor='' valign='top' align='justify' ><font color='#000000' face='Tahoma' size='2'>"
					+ taskPerformed + "</font></td>");
			printhtml
			.append("<td width='42%' bgcolor='' valign='top' align='justify' ><font color='#000000' face='Tahoma' size='2'>"
					+ info + "</font></td>");
			if (strPassFail.toUpperCase().equals("PASS")) {
				printhtml
				.append("<td width='18%' bgcolor='' valign='middle' align='center'><b><font color='#000000' face='Tahoma' size='2'>"
						+imgLink+ "</font></b></td>");
			} else if (strPassFail.toUpperCase().equals("FAIL")) {
				printhtml
				.append("<td width='10%' bgcolor='' valign='middle' align='center'><b><font color='#FF7373' face='Tahoma' size='2'>"
						+imgLink+ "</font></b></td>");
			} else {
				printhtml
				.append("<td width='10%' bgcolor='' valign='middle' align='center'><b><font color='#FF7373' face='Tahoma' size='2'>"
						+imgLink+"</font></b></td>");
			}
			printhtml
			.append("<td width='13%' bgcolor='' valign='middle' align='center' ><font color='#000000' face='Tahoma' size='2'>"
					+ JavaWrappers.getCurrentTime("HH:mm:ss") + "</font></td>");	
			printhtml.append("</tr>");

		} catch (Exception ex) {
			System.out.println("Error while reporting: "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void header() {
		try {
//			String complementryInfo = " for <span style=\"color:red\">"+suiteReporting.getFlavor()+"</span>";
			printhtml.println("</table>");
			printhtml.println("<html>");
			printhtml.println("<title> Test Case Report </title>");
			printhtml.println("<head></head>");
			printhtml.println("<body>");
			printhtml.println("<script type=\"text/javascript\">"+

    "function toggle_visibility(id,id1) {"+
    "var e = document.getElementById(id);"+


       "if(e.style.display == 'block') {"+
       "document.getElementById(id1).innerHTML = \"+\";"+
       "e.style.display = 'none';"+

       "}"+
       "else {"+
       "document.getElementById(id1).innerHTML = \"-\";"+
       "e.style.display = 'block';"+

       "}"+
       "}"+
					"</script>");
//			printhtml.println("<font face='Tahoma'size='2'>");
//			printhtml.println("<h3 align='right' ><font color='#000000' face='Tahoma' size='3'></font></h3>");
//			printhtml.println("<h3 align='center'>Environment : <span style=\"color:grey\">"+env+"</span> </h3>");
			printhtml.println("<h3 align='center'>TestCase : <span style=\"color:grey\">"+testCaseName+"</span> </h3>");
			printhtml.println("<br>");
//			printhtml.println("<table border='0' width='100%' height='47'>");
			printhtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:100%;word-break: break-all'>");
			printhtml.println("<tr bgcolor='#92DAEA'>");
			printhtml
			.println("<td width='35%' bgcolor='' align='center'><b><font color='#000000' face='Tahoma' size='2'>TaskPerformed</font></b></td>");
			printhtml
			.println("<td width='42%' bgcolor=''align='center'><b><font color='#000000' face='Tahoma' size='2'>Info</font></b></td>");
			printhtml
			.println("<td width='10%' bgcolor='' align='center'><b><font color='#000000' face='Tahoma' size='2'>Pass/Fail</font></b></td>");
			printhtml
			.println("<td width='13%' bgcolor='' align='center'><b><font color='#000000' face='Tahoma' size='2'>CurrentTime</font></b></td>");
			printhtml.println("</tr>");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void completeReport(){
       printhtml.close();		
	}

}
