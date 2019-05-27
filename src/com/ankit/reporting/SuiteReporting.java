package com.ankit.reporting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import com.ankit.JavaUtility.JavaWrappers;
import com.ankit.Selenium.TestSuites;

public class SuiteReporting extends ReportingSession {
	
	
//    public static OutputStream masterHtmlFile;
//    public static PrintStream masterPrintHtml;
//	public static int totalPassTestCase = 0;
//	public static int totalSkipTestCase = 0;
//	public static int totalFailTestCase = 0;
//	public static String SuiteFilePath ="";
//	public static long mStartTime = 0;
//	public static String startTime = "";
//	public static String suiteName = "";
	
    public  OutputStream masterHtmlFile;
    public  PrintStream masterPrintHtml;
	public  int totalPassTestCase = 0;
	public  int totalSkipTestCase = 0;
	public  int totalFailTestCase = 0;
	public  String SuiteFilePath ="";
//	public  long mStartTime = 0;
//	public  String startTime = "";
	public  String suiteName = "";
//	public  TestReporting testReporting = null;
	private int apiRow = 0;
    
	public void initilizeSuiteReport() throws IOException{
//		String systemUserName = System.getProperty("user.home");
//		concludeReportPath = JavaWrappers.createDir(systemUserName, reportFolder).getAbsolutePath();
//		JavaWrappers.createDir(concludeReportPath, "TestSuites").renameTo(new File(concludeReportPath,"TestSuites"));
//		testSuitesPath = concludeReportPath+File.separator+"TestSuites";
//		System.out.println("report path; "+testSuitesPath);
		
	}
	public void openSuiteFile(TestSuites ts) {
		try {
			masterHtmlFile = new FileOutputStream(new File(ts.getIndividulaSuiteReportFolder() + "/"+suiteName+".html"), true);
			SuiteFilePath = ts.getIndividulaSuiteReportFolder()+File.separator+suiteName+".html";
			masterPrintHtml = new PrintStream(masterHtmlFile);
		}catch (Exception ex){
		 ex.printStackTrace();
		}
	}
	
	
	/**
	 * Call method to add the test case into the file
	 * @param strPassFail
	 * @param testCaseName
	 * @param partialResultFileLink
	 * @param sPassPercent
	 * @param testDataId
	 */
	public void addTestToSuiteFile(String strPassFail, String testCaseName,String partialResultFileLink, String totalExecutionTime) {
//		openMasterFile();
		
		masterPrintHtml.println("<tr>");
		masterPrintHtml
				.println("<td width='22%' bgcolor='' valign='top' align='justify' ><font color='#000000' face='Tahoma' size='2'>"
						+ testCaseName + "</font></td>");

		String value = strPassFail.toUpperCase();
		String imageLink ="";
		if (value.equals("PASS"))
			imageLink = "<a href=\"" + "./TestCases/"+ partialResultFileLink + "\">"+value+"</a>";
		else{
			imageLink = "<a href=\"" + "./TestCases/"+ partialResultFileLink + "\"><font color='#FF7373'>"+value+"</font></a>";
		}		
		System.out.println("Adding test case "+ testCaseName+" file into Suite: "+partialResultFileLink+" with status of "+strPassFail);
		if (strPassFail.toUpperCase() == "PASS") {
			this.totalPassTestCase = this.totalPassTestCase + 1;
			masterPrintHtml
					.println("<td width='18%' bgcolor='' valign='middle' align='center'><b><font color='#000000' face='Tahoma' size='2'>"
							+imageLink + "</font></b></td>");
		} else if (strPassFail.toUpperCase() == "FAIL"){
			this.totalFailTestCase = this.totalFailTestCase + 1;
			masterPrintHtml
					.println("<td width='18%' bgcolor='' valign='middle' align='center'><b><font color='red' face='Tahoma' size='2'>"
							+imageLink + "</font></b></td>");
		}else if (strPassFail.toUpperCase() == "SKIP"){
			this.totalSkipTestCase = this.totalSkipTestCase + 1;
			masterPrintHtml
					.println("<td width='18%' bgcolor='' valign='middle' align='center'><b><font color='red' face='Tahoma' size='2'>"
							+imageLink + "</font></b></td>");
		}
//		masterPrintHtml
//				.println("<td width='20%' bgcolor='#FFFFDC' valign='top' align='justify' ><font color='#000000' face='Tahoma' size='2'>"
//						+ "<a href=\"" + ((!(baseTestCaseUrl==null) && !baseTestCaseUrl.equals(""))?baseTestCaseUrl:"") +
//						partialResultFileLink + "</font></td>");
		masterPrintHtml
				.println("<td width='13%' bgcolor='' valign='middle' align='center' ><font color='#000000' face='Tahoma' size='2'>"
						+ totalExecutionTime + "</font></td>");
         System.out.println(this.totalPassTestCase);
	}
	
	
	public void addTestToSuiteFile_AggregationAPI(String source, String testCaseName,String partialResultFileLink, String percentage, String totalExecutionTime) {
//		openMasterFile();
//		if (source.contains("<br>")){
			if (source.split("<br>").length > 1){	
			source = "Blocked URLs<br/><button id=\"expandCollapse"+apiRow+"\" onclick=\"toggle_visibility('response"+apiRow+"','expandCollapse"+apiRow+"');\">+</button>"+
			"<div id=\"response"+apiRow+"\" style=\"display:none;\">"+
			source+
			"</div>";
			apiRow++;
		}
		String imageLink = "<a href=\"" + "./TestCases/"+ partialResultFileLink + "\">"+testCaseName+"</a>";
		masterPrintHtml.println("<tr>");
		masterPrintHtml
				.println("<td width='auto' bgcolor='' valign='top' align='center' ><font color='#000000' face='Tahoma' size='2'><b>"
						+ imageLink + "</b></font></td>");

//		String value = strPassFail.toUpperCase();
//		String imageLink ="";
//		if (value.equals("PASS"))
//			imageLink = "<a href=\"" + "./TestCases/"+ partialResultFileLink + "\">"+value+"</a>";
//		else{
//			imageLink = "<a href=\"" + "./TestCases/"+ partialResultFileLink + "\"><font color='#FF7373'>"+value+"</font></a>";
//		}		
		
			this.totalPassTestCase = this.totalPassTestCase + 1;
			masterPrintHtml
					.println("<td width='auto' bgcolor='' valign='top' align='justify'><b><font color='#000000' face='Tahoma' size='2'>"
							+source + "</font></b></td>");
//		masterPrintHtml
//				.println("<td width='20%' bgcolor='#FFFFDC' valign='top' align='justify' ><font color='#000000' face='Tahoma' size='2'>"
//						+ "<a href=\"" + ((!(baseTestCaseUrl==null) && !baseTestCaseUrl.equals(""))?baseTestCaseUrl:"") +
//						partialResultFileLink + "</font></td>");
			masterPrintHtml
			.println("<td width='auto' bgcolor='' valign='middle' align='center' ><font color='#000000' face='Tahoma' size='2'><b>"
					+ percentage + "%</font></b></td>");
		masterPrintHtml
				.println("<td width='auto' bgcolor='' valign='middle' align='center' ><font color='#000000' face='Tahoma' size='2'>"
						+ totalExecutionTime + "</font></td>");
         System.out.println(this.totalPassTestCase);
	}
	
	
//	public void addTestToSuiteFile_BaseAPI(String strPassFail, String testCaseName,String partialResultFileLink, String percentage, String totalExecutionTime) {
////		openMasterFile();
////		if (source.contains("<br>")){
//			if (source.split("<br>").length > 1){	
//			source = "Blocked URLs<br/><button id=\"expandCollapse"+apiRow+"\" onclick=\"toggle_visibility('response"+apiRow+"','expandCollapse"+apiRow+"');\">+</button>"+
//			"<div id=\"response"+apiRow+"\" style=\"display:none;\">"+
//			source+
//			"</div>";
//			apiRow++;
//		}
//		String imageLink = "<a href=\"" + "./TestCases/"+ partialResultFileLink + "\">"+testCaseName+"</a>";
//		masterPrintHtml.println("<tr>");
//		masterPrintHtml
//				.println("<td width='auto' bgcolor='' valign='top' align='center' ><font color='#000000' face='Tahoma' size='2'><b>"
//						+ imageLink + "</b></font></td>");
//
////		String value = strPassFail.toUpperCase();
////		String imageLink ="";
////		if (value.equals("PASS"))
////			imageLink = "<a href=\"" + "./TestCases/"+ partialResultFileLink + "\">"+value+"</a>";
////		else{
////			imageLink = "<a href=\"" + "./TestCases/"+ partialResultFileLink + "\"><font color='#FF7373'>"+value+"</font></a>";
////		}		
//		
//			this.totalPassTestCase = this.totalPassTestCase + 1;
//			masterPrintHtml
//					.println("<td width='auto' bgcolor='' valign='top' align='justify'><b><font color='#000000' face='Tahoma' size='2'>"
//							+source + "</font></b></td>");
////		masterPrintHtml
////				.println("<td width='20%' bgcolor='#FFFFDC' valign='top' align='justify' ><font color='#000000' face='Tahoma' size='2'>"
////						+ "<a href=\"" + ((!(baseTestCaseUrl==null) && !baseTestCaseUrl.equals(""))?baseTestCaseUrl:"") +
////						partialResultFileLink + "</font></td>");
//			masterPrintHtml
//			.println("<td width='auto' bgcolor='' valign='middle' align='center' ><font color='#000000' face='Tahoma' size='2'><b>"
//					+ percentage + "%</font></b></td>");
//		masterPrintHtml
//				.println("<td width='auto' bgcolor='' valign='middle' align='center' ><font color='#000000' face='Tahoma' size='2'>"
//						+ totalExecutionTime + "</font></td>");
//         System.out.println(this.totalPassTestCase);
//	}
	
	
	/**
	 * Create a header functionality for master file html
	 * @author Ankit 
	 */
	public  String createSuiteFileHeader(TestSuites ts, String name, String browserName){
		try {
			totalFailTestCase = 0;
			totalPassTestCase = 0;
			totalSkipTestCase = 0;
//			startTime = JavaWrappers.getCurrentTime("HH:mm:ss");
//			mStartTime = System.currentTimeMillis();
			suiteName = name;
//			suiteName =JavaWrappers.getAlphabetOnlyFmString(name);
//			File tempDir = new File(testSuitesPath+File.separator+suiteName);
//			if(!tempDir.exists()){
			JavaWrappers.createDir(testSuitesPath, suiteName).renameTo(new File(testSuitesPath,suiteName));
//			JavaWrappers.createDirIfNotExist(testSuitesPath, suiteName).renameTo(new File(testSuitesPath,suiteName));
			String individulaSuitePath = testSuitesPath+File.separator+suiteName;
			ts.setIndividulaSuiteReportFolder(individulaSuitePath);
			JavaWrappers.createDir(ts.getIndividulaSuiteReportFolder(), "TestCases").renameTo(new File(individulaSuitePath,"TestCases"));
//			JavaWrappers.createDirIfNotExist(ts.getIndividulaSuiteReportFolder(), "TestCases").renameTo(new File(individulaSuitePath,"TestCases"));
			String testReport = ts.getIndividulaSuiteReportFolder()+File.separator+"TestCases";
			String screenShot =  testReport+File.separator+"ScreenShot";
			String logsFolder =  testReport+File.separator+"Logs";
			if (!(new File(screenShot).exists()))
			JavaWrappers.createDir(testReport, "ScreenShot").renameTo(new File(testReport,"ScreenShot"));
//			JavaWrappers.createDirIfNotExist(testReport, "ScreenShot").renameTo(new File(testReport,"ScreenShot"));
			if (!(new File(logsFolder).exists())){
			JavaWrappers.createDir(testReport, "Logs").renameTo(new File(testReport,"Logs"));
//			JavaWrappers.createDirIfNotExist(testReport, "Logs").renameTo(new File(testReport,"Logs"));
			}
			String orgSuiteFile = "TestSuites"+File.separator+suiteName+File.separator+suiteName+".html";
			
			ts.setIndividualSuiteReportFileName(orgSuiteFile);
			openSuiteFile(ts);
			createSuiteHtml(browserName); 
//			}
//			else{
//				String individulaSuitePath = testSuitesPath+File.separator+suiteName;
//				ts.setIndividulaSuiteReportFolder(individulaSuitePath);
//				String orgSuiteFile = "TestSuites"+File.separator+suiteName+File.separator+suiteName+".html";
//				ts.setIndividualSuiteReportFileName(orgSuiteFile);
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ts.getIndividulaSuiteReportFolder();
	}
	
	
	
	/**
	 * Create a header functionality for master file html
	 * @author Ankit 
	 */
	public  String createAPISuiteFileHeader(TestSuites ts, String name){
		try {
			totalFailTestCase = 0;
			totalPassTestCase = 0;
			totalSkipTestCase = 0;
//			startTime = JavaWrappers.getCurrentTime("HH:mm:ss");
//			mStartTime = System.currentTimeMillis();
			suiteName = name;
//			suiteName =JavaWrappers.getAlphabetOnlyFmString(name);
//			File tempDir = new File(testSuitesPath+File.separator+suiteName);
//			if(!tempDir.exists()){
			JavaWrappers.createDir(testSuitesPath, suiteName).renameTo(new File(testSuitesPath,suiteName));
//			JavaWrappers.createDirIfNotExist(testSuitesPath, suiteName).renameTo(new File(testSuitesPath,suiteName));
			String individulaSuitePath = testSuitesPath+File.separator+suiteName;
			ts.setIndividulaSuiteReportFolder(individulaSuitePath);
			JavaWrappers.createDir(ts.getIndividulaSuiteReportFolder(), "TestCases").renameTo(new File(individulaSuitePath,"TestCases"));
//			JavaWrappers.createDirIfNotExist(ts.getIndividulaSuiteReportFolder(), "TestCases").renameTo(new File(individulaSuitePath,"TestCases"));
			String testReport = ts.getIndividulaSuiteReportFolder()+File.separator+"TestCases";
			String screenShot =  testReport+File.separator+"ScreenShot";
			String logsFolder =  testReport+File.separator+"Logs";
			if (!(new File(screenShot).exists()))
			JavaWrappers.createDir(testReport, "ScreenShot").renameTo(new File(testReport,"ScreenShot"));
//			JavaWrappers.createDirIfNotExist(testReport, "ScreenShot").renameTo(new File(testReport,"ScreenShot"));
			if (!(new File(logsFolder).exists())){
			JavaWrappers.createDir(testReport, "Logs").renameTo(new File(testReport,"Logs"));
//			JavaWrappers.createDirIfNotExist(testReport, "Logs").renameTo(new File(testReport,"Logs"));
			}
			String orgSuiteFile = "TestSuites"+File.separator+suiteName+File.separator+suiteName+".html";
			
			ts.setIndividualSuiteReportFileName(orgSuiteFile);
			openSuiteFile(ts);
			createAPISuiteHtml(); 
//			}
//			else{
//				String individulaSuitePath = testSuitesPath+File.separator+suiteName;
//				ts.setIndividulaSuiteReportFolder(individulaSuitePath);
//				String orgSuiteFile = "TestSuites"+File.separator+suiteName+File.separator+suiteName+".html";
//				ts.setIndividualSuiteReportFileName(orgSuiteFile);
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ts.getIndividulaSuiteReportFolder();
	}
	
	
	private void createAPISuiteHtml(){
		// creating html table
		masterPrintHtml.println("<html>");
		masterPrintHtml.println("<title>API Test Script-Report</title>");
		masterPrintHtml.println("<head></head>");
		masterPrintHtml.println("<body>");
		masterPrintHtml.println("<script type=\"text/javascript\">"+

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
		
		// mentioning Suite name and Application on top of the table
		masterPrintHtml.println("<h2 align='center'>");
		masterPrintHtml.println("<span style='color:grey'>"+suiteName+"</span>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
		//creating table for TestCases and their results
//		masterPrintHtml.println("<table border='0' width='100%' height='45'>");
		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:100%;'>");
		masterPrintHtml.println("<tbody>");
		
		//Setting the header for the table
//		masterPrintHtml.println("<tr bgcolor='#b3b3ff'>");
		masterPrintHtml.println("<tr bgcolor='#92DAEA'>");
		masterPrintHtml.println("<td width='auto' valign='middle' align='center'>");
		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>TestCaseName</font></b>");
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("<td width='auto' valign='middle' align='center'>");
		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>Status</font></b>");
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("<td width='auto' valign='middle' align='center'>");
//		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>Blocked percentage</font></b>");
//		masterPrintHtml.println("</td>");
		masterPrintHtml.println("<td width='auto' valign='middle' align='center'>");
		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>TimeTaken</font></b>");
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("</tr>");
	}
	
	
	private void createSuiteHtml(String browserName){
		// creating html table
		masterPrintHtml.println("<html>");
		masterPrintHtml.println("<title>Selenium Automation Test Script-Report</title>");
		masterPrintHtml.println("<head></head>");
		masterPrintHtml.println("<body>");
		
		// mentioning Suite name and Application on top of the table
		masterPrintHtml.println("<h2 align='center'>");
		masterPrintHtml.println("<span style='color:grey'>"+suiteName+"</span>");
		masterPrintHtml.println("Execution Report on");
		masterPrintHtml.println("<span style='color:grey'>"+browserName+"</span>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
		//creating table for TestCases and their results
//		masterPrintHtml.println("<table border='0' width='100%' height='45'>");
		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:100%;'>");
		masterPrintHtml.println("<tbody>");
		
		//Setting the header for the table
//		masterPrintHtml.println("<tr bgcolor='#b3b3ff'>");
		masterPrintHtml.println("<tr bgcolor='#92DAEA'>");
		masterPrintHtml.println("<td width='auto' valign='middle' align='center'>");
		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>TestCaseName</font></b>");
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("<td width='auto' valign='middle' align='center'>");
		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>Status</font></b>");
		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("<td width='auto' valign='top' align='center'>");
//		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>TestFile</font></b>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("</td>");
		masterPrintHtml.println("<td width='auto' valign='middle' align='center'>");
		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>TimeTaken</font></b>");
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("</tr>");
	}
	
	/**
	 * create footer part of the master file html
	 * @author Ankit
	 */
	public void completeSuiteFileFooter(TestSuites ts,ConcludeReport conReport){
		openSuiteFile(ts);
		long mEndTime = System.currentTimeMillis();
		int testCaseTotal = this.totalPassTestCase + this.totalFailTestCase;
		System.out.println(testCaseTotal);
		masterPrintHtml.println("</tbody>");
		masterPrintHtml.println("</table>");
		masterPrintHtml.println("<br>");
		
		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:100%;'>");
		masterPrintHtml.println("<tbody>");
		masterPrintHtml.println("<tr style='background-color:#92DAEA;text-align:center'>");
		masterPrintHtml.println("<th class='tg-031e' colspan='6'><b><font color='Black' face='Tahoma' size='3'>TestCases Execution Details</b><br></th>");
		masterPrintHtml.println("</tr>");
		masterPrintHtml.println("<tr style='background-color:#D7E0E1;'>");
		masterPrintHtml.println("<td class='tg-9hbo' style='text-align:center'><b><font color='Black' face='Tahoma' size='2'>Total Test Cases</b></td>");
		masterPrintHtml.println("<td class='tg-9hbo' style='text-align:center'><b><font color='Black' face='Tahoma' size='2'>Passed</b></td>");
		masterPrintHtml.println("<td class='tg-9hbo' style='text-align:center'><b><font color='Black' face='Tahoma' size='2'>Failed</b></td>");
		masterPrintHtml.println("<td class='tg-9hbo' style='text-align:center'><b><font color='Black' face='Tahoma' size='2'>Start Time</b></td>");
		masterPrintHtml.println("<td class='tg-9hbo' style='text-align:center'><b><font color='Black' face='Tahoma' size='2'>End Time</b></td>");
		masterPrintHtml.println("<td class='tg-9hbo' style='text-align:center'><b><font color='Black' face='Tahoma' size='2'>Total Execution Time</b></td>");
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("<tr>");
		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:center'><font color='Black' face='Tahoma' size='2'>"+testCaseTotal+"</td>");
		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:center'><font color='Black' face='Tahoma' size='2'>"+this.totalPassTestCase+"</td>");
		masterPrintHtml.println("<td class='tg-baqh' style='text-align:center'><font color='Black' face='Tahoma' size='2'>"+this.totalFailTestCase+"</td>");
		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:center'><font color='Black' face='Tahoma' size='2'>"+ts.getSuiteStartTime()+"</td>");
		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:center'><font color='Black' face='Tahoma' size='2'>"+JavaWrappers.getCurrentTime("HH:m:ss")+"</td>");
		String totalTimeTaken  = JavaWrappers.getTime(ts.getSuiteTimeInMiliSecond(),mEndTime);
		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:center'><font color='Black' face='Tahoma' size='2'>"+totalTimeTaken+"</td>");
		masterPrintHtml.println("</tr>");
		
		
		
		
		
		
		
		
		
//		masterPrintHtml.println("<table border='0' width='50%' height='30'>");
//		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:50%;'>");
//		masterPrintHtml.println("<tbody>");
//		masterPrintHtml.println("<tr bgcolor='#92DAEA'>");
//		masterPrintHtml.println("<td width='auto' colspan='2' style='text-align:center'>");
//		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='3'>TestCaseExecution Details</b></font>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("</tr>");
//		masterPrintHtml.println("<tr>");
//		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Total TestCases</b></font>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		masterPrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+testCaseTotal+"</font>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("<tr>");
//		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Total Passed TestCases</b></font>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		masterPrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+this.totalPassTestCase+"</font>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("<tr>");
//		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Total Failed TestCases</b></font>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		masterPrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+this.totalFailTestCase+"</font>");
//		masterPrintHtml.println("</td>");
////		masterPrintHtml.println("<tr bgcolor='#FFFFDC'>");
////		masterPrintHtml.println("<td width='auto' valigh='top' align='left'>");
////		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Execution Date(DD:MM:YYYY)</b></font>");
////		masterPrintHtml.println("</td>");
////		masterPrintHtml.println("<td width='auto' valigh='top' align='left'>");
////		masterPrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+JavaWrappers.getCurrentDate("dd:MMM:YYYY")+"</font>");
////		masterPrintHtml.println("</td>");
////		masterPrintHtml.println("</tr>");
//		masterPrintHtml.println("<tr>");
//		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Execution Start Time(HH:MM:SS)</b></font>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		masterPrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+this.startTime+"</font>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("</tr>");
//		masterPrintHtml.println("<tr>");
//		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Execution End Time(HH:MM:ss)</b></font>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		masterPrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+JavaWrappers.getCurrentTime("HH:m:ss")+"</font>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("</tr>");
//		masterPrintHtml.println("<tr>");
//		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		masterPrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Total Execution Time(HH:MM:SS)</b></font>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		String totalTimeTaken  = JavaWrappers.getTime(this.mStartTime,mEndTime);
//		masterPrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+totalTimeTaken+"</font>");
//		masterPrintHtml.println("</td>");
//		masterPrintHtml.println("</tr>");
		masterPrintHtml.println("</tbody>");
		masterPrintHtml.println("</table>");
		masterPrintHtml.println("</body>");
		masterPrintHtml.println("</html>");
        masterPrintHtml.close();
//        conReport.addSuiteToConsolidate(this.totalPassTestCase, this.totalFailTestCase, suiteName, SuiteFilePath, totalTimeTaken);
        conReport.addSuiteToConsolidate(this.totalPassTestCase, this.totalFailTestCase,suiteName , ts.getIndividualSuiteReportFileName(), totalTimeTaken);
        
	}
	
	
}
