package com.ankit.reporting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import com.ankit.DataMaps.CommonDataMaps;
import com.ankit.JavaUtility.JavaWrappers;


public class ConcludeReport extends ReportingSession{
	
	private String reportFolder = "SeleniumReport";
    public static OutputStream concludeHtmlFile;
    public static PrintStream concludePrintHtml;
    public static long mreportStartTime = 0;
    public static int totalPassedTestCase =0;
    public static int totalFailedTestCase =0;
    private String reportStartTime = "";
    
    
    
	public String initilizeConcludeReport(String reportType,String newreportFolder) throws IOException{
		String systemUserName = System.getProperty("user.home");
		reportFolder = newreportFolder;
		concludeReportPath = JavaWrappers.createDir(systemUserName, reportFolder).getAbsolutePath();
		createdFolderName = concludeReportPath.substring(concludeReportPath.indexOf(reportFolder), concludeReportPath.length());
		JavaWrappers.createDir(concludeReportPath, "TestSuites").renameTo(new File(concludeReportPath,"TestSuites"));
		testSuitesPath = concludeReportPath+File.separator+"TestSuites";
		System.out.println("report path; "+testSuitesPath);
		mreportStartTime =  System.currentTimeMillis();
		reportStartTime = JavaWrappers.getCurrentTime("HH:mm:ss");
		createConcludeFileHeader(reportType);
		return concludeReportFile;
	}
	
	public String initilizeAPIConcludeReport(String reportType) throws IOException{
		String systemUserName = System.getProperty("user.home");
		reportFolder = reportType+"Report";
		concludeReportPath = JavaWrappers.createDir(systemUserName, reportFolder).getAbsolutePath();
		createdFolderName = concludeReportPath.substring(concludeReportPath.indexOf(reportFolder), concludeReportPath.length());
		JavaWrappers.createDir(concludeReportPath, "TestSuites").renameTo(new File(concludeReportPath,"TestSuites"));
		testSuitesPath = concludeReportPath+File.separator+"TestSuites";
		System.out.println("report path; "+testSuitesPath);
		mreportStartTime =  System.currentTimeMillis();
		reportStartTime = JavaWrappers.getCurrentTime("HH:mm:ss");
		createAPIConcludeFileHeader(reportType);
		return concludeReportFile;
	}
	
	
	public void createAPIConcludeFileHeader(String reportType){
		try {
			openConcludeFile();
			// creating html table
			String hStyle = "style='background-color:#768f90; padding:15px 0; border:1px solid'";
			concludePrintHtml.println("<html>");
			concludePrintHtml.println("<title>"+reportType+" Report</title>");
			concludePrintHtml.println("<head></head>");
			concludePrintHtml.println("<body>");
			
			// mentioning Suite name and Application on top of the table
			concludePrintHtml.println("<h2 align='center'>");
			concludePrintHtml.println("<span style='color:black'><u>"+reportType+" Report</u></span>");
			concludePrintHtml.println("<BR>");
			concludePrintHtml.println("<h3 align='center'>");
			concludePrintHtml.println("<BR>");
			concludePrintHtml.println("<BR>");
			//creating table for TestCases and their results
//			concludePrintHtml.println("<table border='0' width='100%' height='45'>");
			concludePrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:100%;'>");
			concludePrintHtml.println("<tbody>");
			
			//Setting the header for the table
			concludePrintHtml.println("<tr bgcolor='#92DAEA'>");
			concludePrintHtml.println("<td width='auto' valign='middle' align='center'>");
			concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>TestSuiteName</font></b>");
			concludePrintHtml.println("</td>");
			concludePrintHtml.println("<td width='auto' valign='middle' align='center'>");
			concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>PASS</font></b>");
			concludePrintHtml.println("</td>");
			concludePrintHtml.println("</td>");
			concludePrintHtml.println("<td width='auto' valign='middle' align='center'>");
			concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>FAIL</font></b>");
			concludePrintHtml.println("</td>");
//			concludePrintHtml.println("</td>");
//			concludePrintHtml.println("<td width='auto' valign='top' align='center'>");
//			concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>TestFile</font></b>");
//			concludePrintHtml.println("</td>");
//			concludePrintHtml.println("</td>");
			concludePrintHtml.println("<td width='auto' valign='middle' align='center'>");
			concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>TimeTaken</font></b>");
			concludePrintHtml.println("</td>");
			concludePrintHtml.println("</tr>");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void createConcludeFileHeader(String reportType){
		try {
			openConcludeFile();
			// creating html table
			String hStyle = "style='background-color:#768f90; padding:15px 0; border:1px solid'";
			concludePrintHtml.println("<html>");
			concludePrintHtml.println("<title>WebSite Automation Report</title>");
			concludePrintHtml.println("<head></head>");
			concludePrintHtml.println("<body>");
			
			// mentioning Suite name and Application on top of the table
			concludePrintHtml.println("<h2 align='center'>");
			concludePrintHtml.println("<span style='color:black'><u>"+reportType+ " Report</u></span>");
			System.out.println("In conclude report "+CommonDataMaps.masterConfigValues.get("url"));
//			if(CommonDataMaps.masterConfigValues.get("url").contains("demo") || 
//					CommonDataMaps.masterConfigValues.get("url").contains("staging")){
			concludePrintHtml.println("<BR>");
			concludePrintHtml.println("<h3 align='center'>");
			concludePrintHtml.println("<span style='color:black'><u>Environment</u></span>");
			concludePrintHtml.println("<span style='color:black'>"+": "+CommonDataMaps.masterConfigValues.get("url")+"</span>");
//		    }
			concludePrintHtml.println("<BR>");
			concludePrintHtml.println("<BR>");
			//creating table for TestCases and their results
//			concludePrintHtml.println("<table border='0' width='100%' height='45'>");
			concludePrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:100%;'>");
			concludePrintHtml.println("<tbody>");
			
			//Setting the header for the table
			concludePrintHtml.println("<tr bgcolor='#92DAEA'>");
			concludePrintHtml.println("<td width='35%' valign='middle' align='center'>");
			concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>TestSuiteName</font></b>");
			concludePrintHtml.println("</td>");
			concludePrintHtml.println("<td width='20%' valign='middle' align='center'>");
			concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>PASS</font></b>");
			concludePrintHtml.println("</td>");
			concludePrintHtml.println("</td>");
			concludePrintHtml.println("<td width='20%' valign='middle' align='center'>");
			concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>FAIL</font></b>");
			concludePrintHtml.println("</td>");
//			concludePrintHtml.println("</td>");
//			concludePrintHtml.println("<td width='auto' valign='top' align='center'>");
//			concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>TestFile</font></b>");
//			concludePrintHtml.println("</td>");
//			concludePrintHtml.println("</td>");
			concludePrintHtml.println("<td width='25%' valign='middle' align='center'>");
			concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='4'>TimeTaken</font></b>");
			concludePrintHtml.println("</td>");
			concludePrintHtml.println("</tr>");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    
	
	public void addSuiteToConsolidate(int totalPass, int totalFail, String testSuiteName,String partialResultFileLink, String totalExecutionTime) {
//		openMasterFile();
		System.out.println("Adding Completed suite  "+testSuiteName);
		String dStyle = "style='background-color:#f8f8e9; padding:10px 5px; border:1px solid'";
		totalPassedTestCase = totalPassedTestCase +totalPass;
		totalFailedTestCase = totalFailedTestCase+totalFail;
       String value = totalPass+"/"+totalFail;
//      String file = new File(partialResultFileLink).getAbsolutePath();
//		String imageLink = "<a href=\"" + ((!(baseTestCaseUrl==null) && !baseTestCaseUrl.equals(""))?baseTestCaseUrl:"")""
//"<a href=\"" + "../TestCases/ScreenShot/"+ nameOfScreenShot + "\">"+value+"</a>";
       
//		String imageLink = "<a href='" +partialResultFileLink +"'>"+testSuiteName+" </a>";
		
		String imageLink = "<a style=”text-decoration:none; href='" +partialResultFileLink +"'>"+testSuiteName+" </a>";
		
//		String imageLink = "<a  href="+"/" + "./"+ partialResultFileLink+">"+value+"</a>";
//       String imageLink = "<a  href=\""+"./"+ partialResultFileLink+"\" style=\"text-decoration:none;\" id=\""+ testSuiteName +"\" onfocus=\"demoDisplay1('"+ testSuiteName + "')\">"+value+"</a>";
		concludePrintHtml.println("<tr>");
		concludePrintHtml
				.println("<td width='35%' bgcolor='' valign='top' align='justify' ><b><font color='#FF7373' face='Tahoma' size='2'>"
						+imageLink + "</font></b></td>");
//		.println("<td "+dStyle+" width='22%' bgcolor='#FFFFDC' valign='top' align='justify' ><font color='#000000' face='Tahoma' size='2'>"
//				+ testSuiteName + "</font></td>");
//		concludePrintHtml
//		.println("<td width='18%' bgcolor='#FFFFDC' valign='middle' align='center'><b><font color='#FF7373' face='Tahoma' size='2'>"
//				+ "<a href="+"\\\\"+file+">"+value+"</a></font></b></td>");
		concludePrintHtml
		.println("<td width='20%' bgcolor='' valign='middle' align='center'><b><font color='#000000' face='Tahoma' size='2'>"
				+totalPass+ "</font></b></td>");
		concludePrintHtml
		.println("<td width='20%' bgcolor='' valign='middle' align='center'><b><font color='#FF7373' face='Tahoma' size='2'>"
				+totalFail+ "</font></b></td>");
		concludePrintHtml
		.println("<td width='25%' bgcolor='' valign='middle' align='center' ><font color='#000000' face='Tahoma' size='2'>"
				+ totalExecutionTime + "</font></td>");	
//		concludePrintHtml
//		.println("<td "+dStyle+" width='13%' bgcolor='#FFFFDC' valign='middle' align='center' ><font color='#000000' face='Tahoma' size='2'>"
//				+ totalExecutionTime + "</font></td>");	
	}
    
	public void openConcludeFile() {
		try {
			concludeHtmlFile = new FileOutputStream(new File(concludeReportPath + "/"+"ConcludeReport.html"), true);
			concludeReportFile = concludeReportPath + "/"+"ConcludeReport.html";
			concludePrintHtml = new PrintStream(concludeHtmlFile);
		}catch (Exception ex){
		 ex.printStackTrace();
		}
	}
	
	public void completeConcludeFileFooter(){
		openConcludeFile();
		long mEndTime = System.currentTimeMillis();
		concludePrintHtml.println("</tbody>");
		concludePrintHtml.println("</table>");
		concludePrintHtml.println("<br>");
//		concludePrintHtml.println("<table border='0' width='50%' height='30'>");
		concludePrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:100%;'>");
		concludePrintHtml.println("<tbody>");
		concludePrintHtml.println("<tr style='background-color:#92DAEA;text-align:center'>");
		concludePrintHtml.println("<th class='tg-031e' colspan='6'><b><font color='Black' face='Tahoma' size='3'>TestSuites Execution Details</b><br></th>");
		concludePrintHtml.println("</tr>");
//		concludePrintHtml.println("<tr bgcolor='#92DAEA'>");
//		concludePrintHtml.println("<td width='auto' colspan='2' style='text-align:center'>");
//		concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='3'>TestSuites Summary</b></font>");
//		concludePrintHtml.println("</td>");
//		concludePrintHtml.println("</tr>");
//		concludePrintHtml.println("<tr bgcolor='#FFFFDC'>");
//		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
//		concludePrintHtml.println("<tr>");
//		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Date(DD:MM:YYYY)</b></font>");
//		concludePrintHtml.println("</td>");
////		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
//		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		concludePrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+JavaWrappers.getCurrentDate("dd:MMM:YYYY")+"</font>");
//		concludePrintHtml.println("</td>");
//		concludePrintHtml.println("</tr>");
//		concludePrintHtml.println("<tr bgcolor='#FFFFDC'>");
//		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
		concludePrintHtml.println("<tr style='background-color:#D7E0E1;'>");
		concludePrintHtml.println("<td class='tg-9hbo' style='text-align:center'><b><font color='Black' face='Tahoma' size='2'>Total Test Cases</b></td>");
		concludePrintHtml.println("<td class='tg-9hbo' style='text-align:center'><b><font color='Black' face='Tahoma' size='2'>Passed</b></td>");
		concludePrintHtml.println("<td class='tg-9hbo' style='text-align:center'><b><font color='Black' face='Tahoma' size='2'>Failed</b></td>");
		concludePrintHtml.println("<td class='tg-9hbo' style='text-align:center'><b><font color='Black' face='Tahoma' size='2'>Start Time</b></td>");
		concludePrintHtml.println("<td class='tg-9hbo' style='text-align:center'><b><font color='Black' face='Tahoma' size='2'>End Time</b></td>");
		concludePrintHtml.println("<td class='tg-9hbo' style='text-align:center'><b><font color='Black' face='Tahoma' size='2'>Total Execution Time</b></td>");
		concludePrintHtml.println("</td>");
		concludePrintHtml.println("<tr>");
		int total = totalPassedTestCase+totalFailedTestCase;
		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:center'><font color='Black' face='Tahoma' size='2'>"+total+"</td>");
		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:center'><font color='Black' face='Tahoma' size='2'>"+totalPassedTestCase+"</td>");
		concludePrintHtml.println("<td class='tg-baqh' style='text-align:center'><font color='Black' face='Tahoma' size='2'>"+totalFailedTestCase+"</td>");
		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:center'><font color='Black' face='Tahoma' size='2'>"+reportStartTime+"</td>");
		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:center'><font color='Black' face='Tahoma' size='2'>"+JavaWrappers.getCurrentTime("HH:m:ss")+"</td>");
		String totalTimeTaken  = JavaWrappers.getTime(this.mreportStartTime,mEndTime);
		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:center'><font color='Black' face='Tahoma' size='2'>"+totalTimeTaken+"</td>");
		concludePrintHtml.println("</tr>");
		
//		concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Total Test Cases</b></font>");
//		concludePrintHtml.println("</td>");
////		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
//		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		
//		concludePrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+total+"</font>");
//		concludePrintHtml.println("</td>");
////		concludePrintHtml.println("<tr bgcolor='#FFFFDC'>");
////		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
//		concludePrintHtml.println("<tr>");
//		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Total Passed TestCases</b></font>");
//		concludePrintHtml.println("</td>");
////		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
//		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		concludePrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+totalPassedTestCase+"</font>");
//		concludePrintHtml.println("</td>");
////		concludePrintHtml.println("<tr bgcolor='#FFFFDC'>");
////		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
//		concludePrintHtml.println("<tr>");
//		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Total Failed TestCases</b></font>");
//		concludePrintHtml.println("</td>");
////		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
//		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		concludePrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+totalFailedTestCase+"</font>");
//		concludePrintHtml.println("</td>");
////		concludePrintHtml.println("<tr bgcolor='#FFFFDC'>");
////		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
//		concludePrintHtml.println("<tr>");
//		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Execution Start Time(HH:MM:SS)</b></font>");
//		concludePrintHtml.println("</td>");
////		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
//		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		concludePrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+reportStartTime+"</font>");
//		concludePrintHtml.println("</td>");
//		concludePrintHtml.println("</tr>");
////		concludePrintHtml.println("<tr bgcolor='#FFFFDC'>");
////		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
//		concludePrintHtml.println("<tr>");
//		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Execution End Time(HH:MM:ss)</b></font>");
//		concludePrintHtml.println("</td>");
////		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
//		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		concludePrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+JavaWrappers.getCurrentTime("HH:m:ss")+"</font>");
//		concludePrintHtml.println("</td>");
//		concludePrintHtml.println("</tr>");
////		concludePrintHtml.println("<tr bgcolor='#FFFFDC'>");
////		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
//		concludePrintHtml.println("<tr>");
//		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		concludePrintHtml.println("<b><font color='Black' face='Tahoma' size='2'>Total Execution Time(HH:MM:SS)</b></font>");
//		concludePrintHtml.println("</td>");
////		concludePrintHtml.println("<td width='auto' valigh='top' align='left'>");
//		concludePrintHtml.println("<td class='tg-yw4l' style='text-align:left'>");
//		
//		concludePrintHtml.println("<font color='Black' face='Tahoma' size='2'>"+totalTimeTaken+"</font>");
//		concludePrintHtml.println("</td>");
//		concludePrintHtml.println("</tr>");
		concludePrintHtml.println("</tbody>");
		concludePrintHtml.println("</table>");
		concludePrintHtml.println("</body>");
		concludePrintHtml.println("</html>");
        concludePrintHtml.close();
	}
}
