package com.ankit.reporting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import com.ankit.Selenium.TestSuites;
import com.ankit.Selenium.SeleniumWrappers;


public class PageLoadReport extends ReportingSession{
	
    public  OutputStream masterHtmlFile;
    public  PrintStream masterPrintHtml;
    public  String pageLoadReportFile ="";
    public  String pageLoadReport = "PageLoadReport";
    
    
	public void openPageLoadReportFile(TestSuites ts, String browserName) {
		try {
			pageLoadReportFile = testSuitesPath+File.separator+pageLoadReport+".html";
			masterHtmlFile = new FileOutputStream(new File(pageLoadReportFile), true);
			masterPrintHtml = new PrintStream(masterHtmlFile);
			ts.setPageLoadReportFile(pageLoadReportFile);
			createPageLoadHeaderHtml(browserName);
			System.out.println("pageLoadReport is: "+pageLoadReportFile);
		}catch (Exception ex){
		 ex.printStackTrace();
		}
	}
	
	private void createPageLoadHeaderHtml(String browserName){
		// creating html table
		masterPrintHtml.println("<html>");
		masterPrintHtml.println("<title>Selenium Page Load Report</title>");
		masterPrintHtml.println("<head></head>");
		masterPrintHtml.println("<body>");
		
		// mentioning Suite name and Application on top of the table
//		masterPrintHtml.println("<h2 align='center'>");
//		masterPrintHtml.println("<span style='color:grey'>"+pageLoadReport+"</span>");
//		masterPrintHtml.println("Execution Report on");
//		masterPrintHtml.println("<span style='color:grey'>"+browserName+"</span>");
//		masterPrintHtml.println("<BR>");
//		masterPrintHtml.println("<BR>");
	}
	
	public void printPageLoadBusinessInfromation(HashMap<String, String> map){
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:100%;word-break:break-all'>");
		masterPrintHtml.println("<tbody>");
		//Setting the header for the table
		masterPrintHtml.println("<tr style='background-color:#92DAEA;text-align:center' >");
//		masterPrintHtml.println("<td width='auto' valign='middle' align='center'>");
//		masterPrintHtml.println("<b><font color='Black' face='Verdana' size='4'>Business Information</font></b>");
		masterPrintHtml.println("<th colspan='2'><b><font color='Black' face='Verdana' size='5'>Business Information</b><br></th>");
		masterPrintHtml.println("</tr>");
//		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:30%;'>");
//		masterPrintHtml.println("<tbody>");
		for (Map.Entry<String, String> entry : map.entrySet()){
			String key = entry.getKey();
			String value =  entry.getValue();
		masterPrintHtml.append("<tr>");
		masterPrintHtml.append("<td width='57%' bgcolor='' valign='top' align='left'>");
		masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>" + key + "</font>");
		masterPrintHtml.append("</td>");
		masterPrintHtml.append("<td width='43%' bgcolor='' valign='top' align='left'>");
		masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>" + value + "</font>");
		masterPrintHtml.append("</td>");
		masterPrintHtml.append("</tr>");
		}
		masterPrintHtml.append("<tr>");
		masterPrintHtml.append("<th width='auto' bgcolor='' valign='top' align='justify'>");
		masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>  </font>");
		masterPrintHtml.append("</th>");
		masterPrintHtml.append("<th width='auto'  bgcolor='' valign='top' align='justify'>");
		masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>  </font>");
		masterPrintHtml.append("</th>");
		masterPrintHtml.append("</tr>");
		masterPrintHtml.println("<tr style='background-color:#92DAEA;'>");
		masterPrintHtml.println("<th width='auto' valign='middle' align='center'>");
		masterPrintHtml.println("<b><font color='Black' face='Verdana' size='4'>Module/Page</font></b>");
		masterPrintHtml.println("</th>");
		masterPrintHtml.println("<th width='auto' valign='middle' align='center'>");
		masterPrintHtml.println("<b><font color='Black' face='Verdana' size='4'>Load Time</font></b>");
		masterPrintHtml.println("</th>");
		masterPrintHtml.println("</tr>");
	}
	
	
	/**
	 * 
	 * @param params pass as many as key and value which you want to add in report Header
	 *        pass value as "Key=value";
	 *        @author ankitgupta
	 */
	public void printPageLoadBusinessHeader(String header,String...params){
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:100%;word-break:break-all'>");
		masterPrintHtml.println("<tbody>");
		//Setting the header for the table
		masterPrintHtml.println("<tr style='background-color:#92DAEA;text-align:center' >");
		//		masterPrintHtml.println("<td width='auto' valign='middle' align='center'>");
		//		masterPrintHtml.println("<b><font color='Black' face='Verdana' size='4'>Business Information</font></b>");
		masterPrintHtml.println("<th colspan='2'><b><font color='Black' face='Verdana' size='5'>"+header+"</b><br></th>");
		masterPrintHtml.println("</tr>");
		//		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:30%;'>");
		//		masterPrintHtml.println("<tbody>");
		for(String keyAndValue : params) {
			String key = keyAndValue.split("=")[0];
			String value = keyAndValue.split("=")[1];
			//		for (Map.Entry<String, String> entry : map.entrySet()){
			//			String key = entry.getKey();
			//			String value =  entry.getValue();
			masterPrintHtml.append("<tr>");
			masterPrintHtml.append("<td width='57%' bgcolor='' valign='top' align='left'>");
			masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>" + key + "</font>");
			masterPrintHtml.append("</td>");
			masterPrintHtml.append("<td width='43%' bgcolor='' valign='top' align='left'>");
			masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>" + value + "</font>");
			masterPrintHtml.append("</td>");
			masterPrintHtml.append("</tr>");
		}
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
	}
	
	
	
	/**
	 * 
	 * @param params pass as many as key and value which you want to add in report Header
	 *        pass value as "Key=value";
	 *        @author ankitgupta
	 */
	public void printPageLoadBusinessInfromation(String header, String column1,String column2,String...params){
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<BR>");
		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:100%;word-break:break-all'>");
		masterPrintHtml.println("<tbody>");
		//Setting the header for the table
		masterPrintHtml.println("<tr style='background-color:#92DAEA;text-align:center' >");
		//		masterPrintHtml.println("<td width='auto' valign='middle' align='center'>");
		//		masterPrintHtml.println("<b><font color='Black' face='Verdana' size='4'>Business Information</font></b>");
		masterPrintHtml.println("<th colspan='2'><b><font color='Black' face='Verdana' size='5'>"+header+"</b><br></th>");
		masterPrintHtml.println("</tr>");
		//		masterPrintHtml.println("<table class='tg' border='2' style='border-collapse:collapse;width:30%;'>");
		//		masterPrintHtml.println("<tbody>");
		for(String keyAndValue : params) {
			String key = keyAndValue.split("=")[0];
			String value = keyAndValue.split("=")[1];
			//		for (Map.Entry<String, String> entry : map.entrySet()){
			//			String key = entry.getKey();
			//			String value =  entry.getValue();
			masterPrintHtml.append("<tr>");
			masterPrintHtml.append("<td width='57%' bgcolor='' valign='top' align='left'>");
			masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>" + key + "</font>");
			masterPrintHtml.append("</td>");
			masterPrintHtml.append("<td width='43%' bgcolor='' valign='top' align='left'>");
			masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>" + value + "</font>");
			masterPrintHtml.append("</td>");
			masterPrintHtml.append("</tr>");
		}
		masterPrintHtml.append("<tr>");
		masterPrintHtml.append("<th width='auto' bgcolor='' valign='top' align='justify'>");
		masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>  </font>");
		masterPrintHtml.append("</th>");
		masterPrintHtml.append("<th width='auto'  bgcolor='' valign='top' align='justify'>");
		masterPrintHtml.append("<font color='#000000' face='Verdana' size='3'>  </font>");
		masterPrintHtml.append("</th>");
		masterPrintHtml.append("</tr>");
		masterPrintHtml.println("<tr style='background-color:#92DAEA;'>");
		masterPrintHtml.println("<th width='auto' valign='middle' align='center'>");
		masterPrintHtml.println("<b><font color='Black' face='Verdana' size='4'>"+column1+"</font></b>");
		masterPrintHtml.println("</th>");
		masterPrintHtml.println("<th width='auto' valign='middle' align='center'>");
		masterPrintHtml.println("<b><font color='Black' face='Verdana' size='4'>"+column2+"</font></b>");
		masterPrintHtml.println("</th>");
		masterPrintHtml.println("</tr>");
	}
	
	
	
	public void addPageLoadData(String key, String value){

		masterPrintHtml.println("<tr>");
		masterPrintHtml.println("<td width='auto' valign='top' align='justify'>");
		masterPrintHtml.println("<font color='#000000' face='Verdana' size='3'>" + key + "</font>");
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("<td width='auto' valign='top' align='justify'>");
		value = value.trim();
		if (!(value == null || value.isEmpty())){
		double time = Double.parseDouble(value);
		if (time > 4)
		masterPrintHtml.println("<font color='#ff0000' face='Verdana' size='3'>" + value + " sec</font>");
		else if(time > 2 && time <= 4){
			masterPrintHtml.println("<font color='#000000' face='Verdana' size='3'>" + value + " sec</font>");
		}
		else
			masterPrintHtml.println("<font color='#198c19' face='Verdana' size='3'>" + value + " sec</font>");
		}
		else{
			masterPrintHtml.println("<font color='#000000' face='Verdana' size='3'>" + value + "</font>");
		}
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("</tr>");
	}
	
	
	public void addPageLoadData(String key, String value, boolean string){

		masterPrintHtml.println("<tr>");
		masterPrintHtml.println("<td width='auto' valign='top' align='justify'>");
		masterPrintHtml.println("<font color='#000000' face='Verdana' size='3'>" + key + "</font>");
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("<td width='auto' valign='top' align='justify'>");
		value = value.trim();
        if (value.contains("OK") || value.equalsIgnoreCase("PASS")) {  // green
        	masterPrintHtml.println("<font color='#198c19' face='Verdana' size='3'>" + value + "</font>");
        }
		else {  // red
			masterPrintHtml.println("<font color='#ff0000' face='Verdana' size='3'>" + value + "</font>");
		}
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("</tr>");
	}
	
	
	public void addPageLoadData(TestSuites ts,String key, String value, boolean addSecond) throws Exception{

		masterPrintHtml.println("<tr>");
		masterPrintHtml.println("<td width='auto' valign='top' align='justify'>");
		masterPrintHtml.println("<font color='#000000' face='Verdana' size='3'>" + key + "</font>");
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("<td width='auto' valign='top' align='justify'>");
		value = value.trim();
		try {
			if (!(value == null || value.isEmpty())){
				double time = Double.parseDouble(value);
				//		if (time > 300)   // red
				//		masterPrintHtml.println("<font color='#ff0000' face='Verdana' size='3'>" + value + " sec</font>");
				//		else /*if (time <= 300){ */  // black
				if(!addSecond) { // this is for Free Trail
					masterPrintHtml.println("<font color='#198c19' face='Verdana' size='3'>" + value + " sec</font>");
				}
				else {
					// this is for Google page speed score
					if(time <80) { //red
						masterPrintHtml.println("<font color='#ff0000' face='Verdana' size='3'>" + value + "/100</font>");
					}else {
						//green
						masterPrintHtml.println("<font color='#198c19' face='Verdana' size='3'>" + value + "/100</font>");
					}
				}
				//		}
				//		else    // green
				//			masterPrintHtml.println("<font color='#198c19' face='Verdana' size='3'>" + value + " sec</font>");
			}
		}catch(Exception e) {
			String nameOfScreenShot = captureScreenShot(ts);
			String imgLink = "<a href=\"" + "../TestCases/ScreenShot/"+ nameOfScreenShot + "\"><font color='#FF7373'>"+value+"</a>";
			masterPrintHtml.println("<font color='#ff0000' face='Verdana' size='3'><b>" + imgLink + "</font></b>");
		}
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("</tr>");
	}
	
	public void addPageLoadData(TestSuites ts,String key, String value, String validationText) throws Exception{

		masterPrintHtml.println("<tr>");
		masterPrintHtml.println("<td width='auto' valign='top' align='justify'>");
		masterPrintHtml.println("<font color='#000000' face='Verdana' size='3'>" + key + "</font>");
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("<td width='auto' valign='top' align='justify'>");
		value = value.trim();
		try {
			if (value.equals(validationText)){
				masterPrintHtml.println("<font color='#198c19' face='Verdana' size='3'>" + value + "</font>");
			}
			else {
				masterPrintHtml.println("<font color='#ff0000' face='Verdana' size='3'>" + value + "</font>");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		masterPrintHtml.println("</td>");
		masterPrintHtml.println("</tr>");
	}

	
	
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
