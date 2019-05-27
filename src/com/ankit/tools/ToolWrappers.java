package com.ankit.tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.ankit.Selenium.TestSuites;


public class ToolWrappers{
	
//	public static FindLocators fl = new FindLocators();
	
	public boolean validateStrings(TestSuites ts,String actual, String expected) throws Exception{
		boolean flag = false;
		if (actual.equalsIgnoreCase(expected)){
			ts.getTestReporting().addTestSteps(ts,"Expected text: "+expected," :actual text is:  : "+actual, "PASS");
			System.out.println("Expected text: "+expected+" :actual text is:  : "+actual);
			flag = true;
		}
		else{
			ts.getTestReporting().addTestSteps(ts,"<b>Expected text:</b> "+expected," <b>:actual is:</b> "+actual, "FAIL");
			System.out.println("Expected text: "+expected+" :actual text is:  : "+actual);
			throw new Exception("Expected text: "+expected+" :actual text is:  : "+actual);
		}
		return flag;
	}
	
	
	public boolean verifyPDFContent(TestSuites ts,String strURL, String text) throws Exception {

		String output ="";
		boolean flag = false;
		try{
			URL url = new URL(strURL);
			BufferedInputStream file = new BufferedInputStream(url.openStream());
			PDDocument document = null;
			try {
				document = PDDocument.load(file);
				output = new PDFTextStripper().getText(document);
				System.out.println(output);
			} finally {
				if (document != null) {
					document.close();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(output.contains(text)){
			ts.getTestReporting().addTestSteps(ts,"<b>"+text+"</b>","is present on opened PDF", "PASS");
			flag =  true;
		}
		else{
			ts.getTestReporting().addTestSteps(ts,"<b>"+text+"</b>","is not present on opened PDF", "FAIL");
			throw new Exception(text+"is not present on opened PDF");
		}
		return flag;
	}
	
	
	
	public boolean verifyPDFFileContent(TestSuites ts,String strURL, String text, boolean present) throws Exception {

		String output ="";
		boolean flag = false;
		try{
			BufferedInputStream file = new BufferedInputStream(new FileInputStream(strURL));
			PDDocument document = null;
			try {
				document = PDDocument.load(file);
				output = new PDFTextStripper().getText(document);
				System.out.println(output);
			} finally {
				if (document != null) {
					document.close();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			ts.getTestReporting().addTestSteps(ts,"<b>"+text+"</b>","Unable to read pdf", "FAIL");
		}
		output = output.toLowerCase();
		if(present) {
			if(output.contains(text.toLowerCase())){
				ts.getTestReporting().addTestSteps(ts,"<b>"+text+"</b>","is present in PDF", "PASS");
				flag =  true;
			}
			else {
				ts.getTestReporting().addTestSteps(ts,"<b>"+text+"</b>","is not present in PDF", "FAIL");
			}
		}
		else{
			if(!output.contains(text.toLowerCase())){
				ts.getTestReporting().addTestSteps(ts,"<b>"+text+"</b>","is not present in PDF", "PASS");
				flag = true;
			}
			else {
				ts.getTestReporting().addTestSteps(ts,"<b>"+text+"</b>","is present in PDF", "FAIL");
			}
		}
		return flag;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
