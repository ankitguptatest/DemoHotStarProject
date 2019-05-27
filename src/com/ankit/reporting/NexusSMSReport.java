package com.ankit.reporting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import com.ankit.Selenium.TestSuites;

public class NexusSMSReport extends ReportingSession{
	
    public  OutputStream masterHtmlFile;
    public  PrintStream masterPrintHtml;
    public  String nexusSMSReportFile ="";
    public  String NexusSMSReport = "NexusSMSReport";
    
    
	public void openNexusReportFile(TestSuites ts, String browserName) {
		try {
			nexusSMSReportFile = testSuitesPath+File.separator+NexusSMSReport+".html";
			masterHtmlFile = new FileOutputStream(new File(nexusSMSReportFile), true);
			masterPrintHtml = new PrintStream(masterHtmlFile);
			ts.setPageLoadReportFile(nexusSMSReportFile);
			createPageLoadHeaderHtml(browserName);
			System.out.println("NexusSMS report is: "+nexusSMSReportFile);
		}catch (Exception ex){
		 ex.printStackTrace();
		}
	}
	
	private void createPageLoadHeaderHtml(String db){
		// creating html table
		masterPrintHtml.println("<html>");
		masterPrintHtml.println("<title>nexusSMS Report</title>");
		masterPrintHtml.println("<head></head>");
		masterPrintHtml.println("<body>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<h2 align='center'>");
		masterPrintHtml.println("<span style='color:grey'>"+db+"</span>");
		
		 String data="<html>\n" +
                 "<head>\n" +
                 "<style>\n" +
                 "#customers {\n" +
                 "    font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;\n" +
                 "    border-collapse: collapse;\n" +
                 "    width: 70%;\n" +
                 "}\n" +
                 "\n" +
                 "#customers td, #customers th {\n" +
                 "    border: 1px solid #ddd;\n" +
                 "    padding: 8px;\n" +
                 "}\n" +
                 "\n" +
                 "#customers tr:nth-child(even){background-color: #f2f2f2;}\n" +
                 "\n" +
                 "#customers tr:hover {background-color: #ddd;}\n" +
                 "\n" +
                 "#customers th {\n" +
                 "    padding-top: 12px;\n" +
                 "    padding-bottom: 12px;\n" +
                 "    text-align: left;\n" +
                 "    background-color: #003f91;\n" +
                 "    color: white;\n" +
                 "}\n" +
                 "</style>\n" +
                 "</head>\n" +
                 "<body>\n" +
                 "\n";
	}
	
	/**
	 * 
	 * @param params pass as many as key and value which you want to add in report Header
	 *        pass value
	 *        @author ankitgupta
	 */
	public void createHeader(String...params){
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
//		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:100%;word-break:break-all'>");
		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:100%;'>");
		masterPrintHtml.println("<tbody style='font-weight: 400;' >");
		masterPrintHtml.println("<tr style='background-color:#92DAEA;text-align:center' >");
		for(String keyAndValue : params) {
			masterPrintHtml.append("<th bgcolor='#92DAEA' valign='top' align='left'>"+keyAndValue);
//			masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>" + keyAndValue + "</font>");
			masterPrintHtml.append("</th>");
		}
		masterPrintHtml.append("</tr>");
	}
	
	public void addPageLoadData(String...params){
		masterPrintHtml.println("<tr>");
		for(String keyAndValue : params) {
		masterPrintHtml.println("<td width='auto' valign='top' align='left'>"+keyAndValue);
//		masterPrintHtml.println("<font color='#00001b' face='Verdana' size='2'>" + keyAndValue + "</font>");
//		masterPrintHtml.println("<font style='font-family: your-chosen-font; size='2'>" + keyAndValue + "</font>");
//		masterPrintHtml.println("<font style=”text-decoration:none; color='#00001b' face='Verdana' size='2'>" + keyAndValue + "</font>");
		masterPrintHtml.println("</td>");
		}
		masterPrintHtml.println("</tr>");
	}
	
//	/**
//	 * 
//	 * @param params pass as many as key and value which you want to add in report Header
//	 *        pass value as "Key=value";
//	 *        @author ankitgupta
//	 */
//	public void printPageLoadBusinessInfromation(String header, String column1,String column2,String...params){
//		masterPrintHtml.println("<BR>");
//		masterPrintHtml.println("<BR>");
//		masterPrintHtml.println("<BR>");
//		masterPrintHtml.println("<BR>");
//		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:100%;word-break:break-all'>");
//		masterPrintHtml.println("<tbody>");
//		//Setting the header for the table
//		masterPrintHtml.println("<tr style='background-color:#92DAEA;text-align:center' >");
//		//		masterPrintHtml.println("<td width='auto' valign='middle' align='center'>");
//		//		masterPrintHtml.println("<b><font color='Black' face='Verdana' size='4'>Business Information</font></b>");
//		masterPrintHtml.println("<th colspan='2'><b><font color='Black' face='Verdana' size='5'>"+header+"</b><br></th>");
//		masterPrintHtml.println("</tr>");
//		//		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:30%;'>");
//		//		masterPrintHtml.println("<tbody>");
//		for(String keyAndValue : params) {
//			String key = keyAndValue.split("=")[0];
//			String value = keyAndValue.split("=")[1];
//			//		for (Map.Entry<String, String> entry : map.entrySet()){
//			//			String key = entry.getKey();
//			//			String value =  entry.getValue();
//			masterPrintHtml.append("<tr>");
//			masterPrintHtml.append("<td width='57%' bgcolor='' valign='top' align='left'>");
//			masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>" + key + "</font>");
//			masterPrintHtml.append("</td>");
//			masterPrintHtml.append("<td width='43%' bgcolor='' valign='top' align='left'>");
//			masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>" + value + "</font>");
//			masterPrintHtml.append("</td>");
//			masterPrintHtml.append("</tr>");
//		}
//		masterPrintHtml.append("<tr>");
//		masterPrintHtml.append("<th width='auto' bgcolor='' valign='top' align='justify'>");
//		masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>  </font>");
//		masterPrintHtml.append("</th>");
//		masterPrintHtml.append("<th width='auto'  bgcolor='' valign='top' align='justify'>");
//		masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>  </font>");
//		masterPrintHtml.append("</th>");
//		masterPrintHtml.append("</tr>");
//		masterPrintHtml.println("<tr style='background-color:#92DAEA;'>");
//		masterPrintHtml.println("<th width='auto' valign='middle' align='center'>");
//		masterPrintHtml.println("<b><font color='Black' face='Verdana' size='4'>"+column1+"</font></b>");
//		masterPrintHtml.println("</th>");
//		masterPrintHtml.println("<th width='auto' valign='middle' align='center'>");
//		masterPrintHtml.println("<b><font color='Black' face='Verdana' size='4'>"+column2+"</font></b>");
//		masterPrintHtml.println("</th>");
//		masterPrintHtml.println("</tr>");
//	}
//	
	
	public void completePageLoadTable(){
		masterPrintHtml.println("</tbody>");
		masterPrintHtml.println("</table>");
	}
	
	public void closePageLoadReport(){
		masterPrintHtml.println("</body>");
		masterPrintHtml.println("</html>");
		masterPrintHtml.close();
	}
	

}
