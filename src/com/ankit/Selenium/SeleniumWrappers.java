package com.ankit.Selenium;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.ankit.DataMaps.CommonDataMaps;
import com.ankit.restAssured.api.ApiDefaultConfig;
import com.ankit.tools.ToolWrappers;



public class SeleniumWrappers extends ToolWrappers {
	
	/**
	 * Click on the given element
	 * @author Ankit
	 */
	public boolean click(TestSuites ts, String elementPath,String...params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		boolean flag = false;
//		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, params);
		WebElement we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath, params);
		try {
			for(int i =0; i<=2; i++){
			if (we !=null && verifyPresenceOfElement(ts,we)){
				try{
					we.click();
					flag = true;
					if(ts.getTestReporting() !=null)
					ts.getTestReporting().addTestSteps(ts,method, elementPath+ " "+getAllString(params), "PASS");
//					Thread.sleep(CommonDataMaps.waitConfigValues.get("second_4"));
					break;
					}catch(Exception e){
						try{
							Actions ac = getActionsDriver(ts);
							ac.moveToElement(we).click().build().perform();
							Thread.sleep(CommonDataMaps.waitConfigValues.get("second_5"));
							ts.getTestReporting().addTestSteps(ts,method, elementPath+ " "+getAllString(params), "PASS");
							break;
						}catch(Exception e1){
							if(ts.getTestReporting() !=null)
							ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+ " "+getAllString(params), "FAIL");
							System.out.println("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
							throw new Exception("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());	
						}
//						System.out.println("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
					}
				}
			else{
				Thread.sleep(CommonDataMaps.waitConfigValues.get("second_8"));
				we = ts.getFindLocator().getWebElement(ts,elementPath, 5, params);
				if(i ==2){
					if(ts.getTestReporting() !=null)
						ts.getTestReporting().addTestSteps(ts,method+" "+elementPath+ " "+getAllString(params),"Element is present but not dispaly", "FAIL");
					throw new Exception("Element: "+elementPath+" isn't present");
				}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			if(ts.getTestReporting() !=null)
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+ " "+getAllString(params), "FAIL");
			throw new Exception(e.getMessage());
		}
		return flag;
	}
	
	public String getCurentBrowserName(TestSuites ts) {
		Capabilities cap = ((RemoteWebDriver) ts.getDriver()).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		return browserName;
	}
	
	public boolean isCurrentBrowserIE(TestSuites ts) {
		String browserName = getCurentBrowserName(ts);
		if (browserName.equalsIgnoreCase("ie") | browserName.contains("internet")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isCurrentBrowserChrome(TestSuites ts) {
		String browserName = getCurentBrowserName(ts);
		if (browserName.equalsIgnoreCase("ch") | browserName.contains("chrome")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * Click on the given element
	 * @author Ankit
	 */
	public boolean clickWithOutWait(TestSuites ts,String elementPath,String...parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		boolean flag = false;
		try {
//			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
			WebElement we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath, parms);
			if (we !=null &&verifyPresenceOfElement(ts,we)){
				try{
					we.click(); 
					flag = true;
					ts.getTestReporting().addTestSteps(ts,method, elementPath+ " "+getAllString(parms), "PASS");
//					Thread.sleep(CommonDataMaps.waitConfigValues.get("second_2"));
					}catch(Exception e){
						try{
							Actions ac = getActionsDriver(ts);
							ac.moveToElement(we).click().build().perform();
						}catch(Exception e1){
							ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+ " "+getAllString(parms), "FAIL");
							System.out.println("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
							throw new Exception("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());	
						}
						System.out.println("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
					}
				}
				else{
					 ts.getTestReporting().addTestSteps(ts,method+" "+elementPath+ " "+getAllString(parms),"Element is present but not dispaly", "FAIL");
					throw new Exception("Element: "+elementPath+" isn't present");
				}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+ " "+getAllString(parms), "FAIL");
			throw new Exception(e.getMessage());
		}
		return flag;
	}
	
	public StopWatch clickWithOutWait(TestSuites ts,StopWatch pageLoad, String elementPath,String...parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		boolean flag = false;
		try {
//			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
			WebElement we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath, parms);
			if (we !=null &&verifyPresenceOfElement(ts,we)){
				try{
					we.click();
					Thread.sleep(200);
					pageLoad.start();
					flag = true;
					
					//un-commenting below logs for debugging report
					ts.getTestReporting().addTestSteps(ts,method, elementPath+ " "+getAllString(parms), "PASS",false);
//					Thread.sleep(CommonDataMaps.waitConfigValues.get("second_2"));
					}catch(Exception e){
						try{
							Actions ac = getActionsDriver(ts);
							ac.moveToElement(we).click().build().perform();
						}catch(Exception e1){
							ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+ " "+getAllString(parms), "WARNING");
							System.out.println("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
							throw new Exception("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());	
						}
						System.out.println("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
					}
				}
				else{
					 ts.getTestReporting().addTestSteps(ts,method+" "+elementPath+ " "+getAllString(parms),"Element is present but not dispaly", "WARNING");
//					throw new Exception("Element: "+elementPath+" isn't present");
				}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+ " "+getAllString(parms), "WARNING");
			throw new Exception(e.getMessage());
		}
		return pageLoad;
	}
	
	
	
	public StopWatch getTimeTillElementPresent(TestSuites ts,StopWatch pageLoad, long time, String elementPath,String...parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		boolean flag = false;
		try {
			pageLoad.start();
			//			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath,time, parms);
			if (we !=null &&verifyPresenceOfElement(ts,we)){
				pageLoad.stop();
				ts.getTestReporting().addTestSteps(ts,method, elementPath+ " "+getAllString(parms), "PASS",false);
			}
			else{
				ts.getTestReporting().addTestSteps(ts,method+" "+elementPath+ " "+getAllString(parms),"Element is present but not dispaly", "WARNING");
				//					throw new Exception("Element: "+elementPath+" isn't present");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+ " "+getAllString(parms), "WARNING");
			throw new Exception(e.getMessage());
		}
		return pageLoad;
	}
	
	
	
	
	/**
	 * Click on the given element
	 * @author Ankit
	 */
	public boolean moveAndclick(TestSuites ts,String elementPath,String...parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		boolean flag = false;
		Actions ac = new Actions(ts.getDriver());
		try {
			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
//			WebElement we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath, parms);
//			if (verifyPresenceOfElement(we)){
				try{
					JavascriptExecutor js = (JavascriptExecutor) ts.getDriver();
					js.executeScript("arguments[0].focus();", we);
					
					
//					ac.moveToElement(we).click(we).build().perform();
					ac.moveToElement(we).click(we).build().perform();
//					ac.moveToElement(we,0,0).click(we).build().perform();
					flag = true;
					ts.getTestReporting().addTestSteps(ts,method, elementPath+ " "+getAllString(parms), "PASS");
					Thread.sleep(CommonDataMaps.waitConfigValues.get("second_2"));
				    }catch(StaleElementReferenceException st) {
				    	if(ts.getLoopCount() == 1){
				    		ts.setLoopCount(0);
							 ts.getTestReporting().addTestSteps(ts,method+" "+elementPath+ " "+getAllString(parms)," Element is tried for two times but not dispaly", "FAIL");
							 throw new Exception("Element: "+elementPath+" isn't present"); 
						 }
						 ts.increaseCount();
						 moveAndclick(ts, elementPath, parms);
					}
					catch(Exception e){
						ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+ " "+getAllString(parms), "FAIL");
						System.out.println("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
						throw new Exception("Exception occurred while moving to : "+elementPath+" "+e.getMessage());
					}
//				}
//				else{
//					 // ts.getTestReporting().addTestSteps(ts,ts,"verifyPresenceOfElement before "+method,elementPath, "FAIL");
//					throw new Exception("Element: "+elementPath+" isn't present");
//				}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+ " "+getAllString(parms), "FAIL");
			throw new Exception(e.getMessage());
		}
		return flag;
	}
	
	
	
	
	
	
	/**
	 * Click on the given element
	 * @author Ankit
	 */
	public boolean moveAndclickWithoutWait(TestSuites ts,String elementPath,String...parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		boolean flag = false;
		Actions ac = new Actions(ts.getDriver());
		try {
//			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
			WebElement we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath, parms);
//			if (verifyPresenceOfElement(we)){
				try{
					
					JavascriptExecutor js = (JavascriptExecutor) ts.getDriver();
					js.executeScript("arguments[0].focus();", we);
					
					
//					ac.moveToElement(we).click(we).build().perform();
					ac.moveToElement(we).click(we).build().perform();
//					ac.moveToElement(we,0,0).click(we).build().perform();
					flag = true;
					ts.getTestReporting().addTestSteps(ts,method, elementPath+ " "+getAllString(parms), "PASS");
//					Thread.sleep(CommonDataMaps.waitConfigValues.get("second_2"));
					}catch(Exception e){
						ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+ " "+getAllString(parms), "FAIL");
						System.out.println("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
						throw new Exception("Exception occurred while moving to : "+elementPath+" "+e.getMessage());
					}
//				}
//				else{
//					 // ts.getTestReporting().addTestSteps(ts,ts,"verifyPresenceOfElement before "+method,elementPath, "FAIL");
//					throw new Exception("Element: "+elementPath+" isn't present");
//				}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+ " "+getAllString(parms), "FAIL");
			throw new Exception(e.getMessage());
		}
		return flag;
	}
	
	
	/**
	 * Click on the given element
	 * @author Ankit
	 */
	public boolean moveToElement(TestSuites ts,String elementPath,String...parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		boolean flag = false;
		Actions ac = new Actions(ts.getDriver());
		try {
//			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
			WebElement we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath, parms);
				try{
					
					JavascriptExecutor js = (JavascriptExecutor) ts.getDriver();
					js.executeScript("arguments[0].focus();", we);
					
					
					ac.moveToElement(we).build().perform();
//					ac.moveToElement(we).perform();
//					ac.moveToElement(we,0,0).build().perform();
					flag = true;
					ts.getTestReporting().addTestSteps(ts,method, elementPath+ " "+getAllString(parms), "PASS");
					Thread.sleep(CommonDataMaps.waitConfigValues.get("second_2"));
					}catch(Exception e){
						ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+ " "+getAllString(parms), "FAIL");
						System.out.println("Exception occurred while moving on: "+elementPath+" "+e.getMessage());
						throw new Exception("Exception occurred while moving to : "+elementPath+" "+e.getMessage());
					}
//				}
//				else{
//					 // ts.getTestReporting().addTestSteps(ts,ts,"verifyPresenceOfElement before "+method,elementPath, "FAIL");
//					throw new Exception("Element: "+elementPath+" isn't present");
//				}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+ " "+getAllString(parms), "FAIL");
			throw new Exception(e.getMessage());
		}
		return flag;
	}

	public Actions getActionsDriver(TestSuites ts){
		return new Actions(ts.getDriver());
	}
	
	
	public void clickAndValidateAnother(TestSuites ts,WebElement webElemnet, String elementPath,String...parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" to";
		WebElement we = null;
			webElemnet.click();
//			ac.moveToElement(webElemnet).clickAndHold().build().perform();
//			Thread.sleep(CommonDataMaps.waitConfigValues.get("second_1"));
			for(int i =0; i<=2; i++){
//				we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
				we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath, parms);
				if (we!= null){
					if(!verifyPresenceOfElement(ts,we)){
						webElemnet.click();
						Thread.sleep(CommonDataMaps.waitConfigValues.get("second_5"));
						we = ts.getFindLocator().getWebElement(ts,elementPath, 10,parms);
//						we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath, parms);
					}
					else{
						ts.getTestReporting().addTestSteps(ts,method, elementPath+ " is present "+getAllString(parms), "PASS");
						break;
					}
					if(i == 2){
						ts.getTestReporting().addTestSteps(ts,method, elementPath+ " is not present "+getAllString(parms), "FAIL");
						throw new Exception("is not present "+elementPath);
					}
				}
			}
	}
	
	
	public boolean moveToWebElementAndOpenAnother(TestSuites ts,WebElement webElemnet, String elementPath,String...parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" to";
		boolean flag = false;
		Actions ac = new Actions(ts.getDriver());
		WebElement we = null;
		try {
			// adding for Firefox to focus on the action class
			JavascriptExecutor js = (JavascriptExecutor) ts.getDriver();
			js.executeScript("arguments[0].focus();", webElemnet);
			
			ac.moveToElement(webElemnet).click().clickAndHold().click(webElemnet).build().perform();
			ts.getTestReporting().addTestSteps(ts,method+" "+elementPath+ " "+getAllString(parms)," 1: Opening dropDown opts", "PASS");
			we = ts.getFindLocator().getWebElement(ts,elementPath, 5,parms);
			if (we!= null && verifyPresenceOfElement(ts,we)){
						js.executeScript("arguments[0].focus();", webElemnet);
						ac.moveToElement(webElemnet).clickAndHold(webElemnet).build().perform();
						we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
						we.click();
						flag = true;
				}
				else {
//					int x  = webElemnet.getLocation().getX();
//					int y =  webElemnet.getLocation().getY();
					String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');"
							+ "evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} "
							+ "else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
//					js.executeScript("arguments[0].focus();", webElemnet);
					js.executeScript(mouseOverScript, webElemnet);
//					ac.moveToElement(webElemnet).click(webElemnet).moveToElement(webElemnet).click().contextClick(webElemnet).build().perform();
					ts.getTestReporting().addTestSteps(ts,method+" "+elementPath+ " "+getAllString(parms)," 2: Opening dropDown opts", "PASS");
					we = ts.getFindLocator().getWebElement(ts,elementPath,6, parms);
					ac.moveToElement(webElemnet).click(webElemnet).click(we).build().perform();
					flag = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				 if(ts.getLoopCount() == 2){
					 ts.setLoopCount(0);
					 ts.getTestReporting().addTestSteps(ts,method+" "+elementPath+ " "+getAllString(parms)," Element is tried for two times but not dispaly", "FAIL");
					 throw new Exception("Element: "+elementPath+" isn't present"); 
				 }
				 ts.increaseCount();
				 moveToWebElementAndOpenAnother(ts, webElemnet, elementPath, parms);
			}catch(Exception e1) {
				
			}
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+" "+getAllString(parms), "FAIL");
			throw new Exception(e.getMessage());
		}
		return flag;
	}

	
	
	
	
	
	public boolean moveToWebElementAndClickAnother(TestSuites ts,WebElement webElemnet, String elementPath,String...parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" to";
		boolean flag = false;
		Actions ac = new Actions(ts.getDriver());
		WebElement we = null;
		try {
			// adding for Firefox to focus on the action class
			JavascriptExecutor js = (JavascriptExecutor) ts.getDriver();
			js.executeScript("arguments[0].focus();", webElemnet);
			
			ac.moveToElement(webElemnet).clickAndHold(webElemnet).build().perform();
//			ac.moveToElement(webElemnet,0,0).clickAndHold().build().perform();
			Thread.sleep(CommonDataMaps.waitConfigValues.get("second_1"));
			for(int i =0; i<3; i++){
				we = ts.getFindLocator().getWebElement(ts,elementPath, 20,parms);
//				we = ts.getFindLocator().getWebElementPresence(ts,elementPath, 20,parms);
				if (we!= null){
					if(!verifyPresenceOfElement(ts,we)){
						
						js.executeScript("arguments[0].focus();", webElemnet);
						
						ac.moveToElement(webElemnet).clickAndHold(webElemnet).build().perform();
//						ac.moveToElement(webElemnet,0,0).clickAndHold().build().perform();
						Thread.sleep(CommonDataMaps.waitConfigValues.get("second_1"));
						we = ts.getFindLocator().getWebElement(ts,elementPath,6, parms);
//						we = ts.getFindLocator().getWebElementPresence(ts,elementPath, 6,parms);
					}
					else{
						break;
					}
				}
				else {
					js.executeScript("arguments[0].focus();", webElemnet);
					ac.moveToElement(webElemnet).clickAndHold(webElemnet).build().perform();
					we = ts.getFindLocator().getWebElement(ts,elementPath,6, parms);
//					we = ts.getFindLocator().getWebElementPresence(ts,elementPath, 6,parms);
					ac.moveToElement(webElemnet).click(webElemnet).click(we).build().perform();
				}
			}
			if (we !=null &&verifyPresenceOfElement(ts,we)){
				try{
//					ac.moveToElement(webElemnet).clickAndHold(we).click(we).build().perform();
//					ac.moveToElement(webElemnet).clickAndHold(we).build().perform();
					we.click();
//					ac.moveToElement(webElemnet).click(we).build().perform();
					flag = true;
					ts.getTestReporting().addTestSteps(ts,method, elementPath+ " "+getAllString(parms), "PASS");
					Thread.sleep(CommonDataMaps.waitConfigValues.get("second_1"));
					}catch(Exception e){
						ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+ " "+getAllString(parms), "FAIL");
						System.out.println("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
						throw new Exception("Exception occurred while moving to : "+elementPath+" "+e.getMessage());
					}
				}
				else{
					 if(ts.getLoopCount() == 1){
						 ts.setLoopCount(0);
						 ts.getTestReporting().addTestSteps(ts,method+" "+elementPath+ " "+getAllString(parms)," Element is tried for two times but not dispaly", "FAIL");
						 throw new Exception("Element: "+elementPath+" isn't present"); 
					 }
					 ts.increaseCount();
					 moveToWebElementAndClickAnother(ts, webElemnet, elementPath, parms);
				}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+" "+getAllString(parms), "FAIL");
			throw new Exception(e.getMessage());
		}
		return flag;
	}

	
	

    /**
     * Return the visible text of the given element
     * @author Ankit
     */
	public String getText(TestSuites ts,String elementPath, String... parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		String text = "";
		try{
//		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
			WebElement we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath, parms);
			try{
			text = we.getText();
			if(text.isEmpty()){
				text = we.getAttribute("text");
			}
			ts.getTestReporting().addTestSteps(ts,method, text+" from: "+elementPath+ " "+getAllString(parms), "PASS");
			System.out.println(text+" from: "+elementPath+ " "+getAllString(parms));
			}catch(Exception e){
				System.out.println("Exception occurred while geting text of: "+elementPath+" "+e.getMessage());
				ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+ " "+getAllString(parms), "FAIL");
				throw new Exception("Exception occurred while geting text of: "+elementPath+" "+e.getMessage());
			}
		}catch(Exception e) {
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+ " "+getAllString(parms), "FAIL");
			throw new Exception(method+" Unable to get the webElement :"+elementPath+e.getMessage());
		}
		return text;
	}
	
    /**
     * Return the visible text of the given element
     * @author Ankit
     */
	public String getTextWithOutCondition(TestSuites ts,String elementPath, String... parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		String text = "";
		try{
		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
//			WebElement we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath, parms);
			try{
			text = we.getText();
			if(text.isEmpty()){
				text = we.getAttribute("text");
			}
			ts.getTestReporting().addTestSteps(ts,method, text+" from: "+elementPath+ " "+getAllString(parms), "PASS");
			}catch(Exception e){
				System.out.println("Exception occurred while geting text of: "+elementPath+" "+e.getMessage());
				ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+ " "+getAllString(parms), "FAIL");
				throw new Exception("Exception occurred while geting text of: "+elementPath+" "+e.getMessage());
			}
		}catch(Exception e) {
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+ " "+getAllString(parms), "FAIL");
			throw new Exception(method+" Unable to get the webElement :"+elementPath+e.getMessage());
		}
		return text;
	}
	
	/**
	 * get the total size of any element
	 * @author Ankit
	 * @param ts
	 * @param elementPath
	 * @param time
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public int getSize(TestSuites ts,String elementPath, long time, String... parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		List<WebElement> wes = ts.getFindLocator().getWebElements(ts,elementPath,time, parms);
		int size = wes.size();
		ts.getTestReporting().addTestSteps(ts,method, size+" Total element present on: "+elementPath+ " "+getAllString(parms), "PASS");
		return size;
	}
	
	public void sizeValidation(TestSuites ts,String elementPath, int expectedSize, String more, String... parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		int size =getSize(ts, elementPath,15, parms);
		if(more.equalsIgnoreCase("more")) {
			if (size > expectedSize)
				ts.getTestReporting().addTestSteps(ts,method, size+" Total elements present on: "+elementPath+ " "+getAllString(parms), "PASS");
				else{
					ts.getTestReporting().addTestSteps(ts,method, size+" Total elements present on: "+elementPath+ " "+getAllString(parms)+" expeted is more : "+expectedSize, "FAIL");	
				}
		}
		else if(more.equalsIgnoreCase("equal")) {
		if (size == expectedSize)
		ts.getTestReporting().addTestSteps(ts,method, size+" Total elements present on: "+elementPath+ " "+getAllString(parms), "PASS");
		else{
			ts.getTestReporting().addTestSteps(ts,method, size+" Total elements present on: "+elementPath+ " "+getAllString(parms)+" expeted is: "+expectedSize, "FAIL");	
		}
		}
	}
	
	 /**
     * Return the visible text of the given element
     * @author Ankit
     */
	public List<String> getTextOfAllWebElement(TestSuites ts,String elementPath, String... parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		String text = "";
		List<String> allText = new ArrayList<String>();
		try{
		List<WebElement> wes = ts.getFindLocator().getWebElements(ts,elementPath, parms);
		try{
			for(WebElement we : wes ){
				text = we.getText();
				if(text.isEmpty()){
					text = we.getAttribute("text");
				}
				if (text == null){
					ts.getTestReporting().addTestSteps(ts,method,"Text is : "+text+" for "+elementPath+" "+getAllString(parms), "WARNIG");
				}
				else{
				ts.getTestReporting().addTestSteps(ts,method, "<b>"+text+"</b> from: "+elementPath+ " "+getAllString(parms), "PASS");
				System.out.println(text);
				allText.add(text);
				}
			}
		}catch(Exception e){
				System.out.println("Exception occurred while geting text of: "+elementPath+ ""+getAllString(parms)+" "+e.getMessage());
				ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+" "+getAllString(parms), "FAIL");
				throw new Exception("Exception occurred while geting text of: "+elementPath+" "+e.getMessage());
			}
			
		}catch(Exception e) {
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+" "+getAllString(parms), "FAIL");
			throw new Exception(method+" Unable to get the webElement :"+elementPath+e.getMessage());
		}
		return allText;
	}

	
	
	 /**
     * Return the visible text of the given element
     * @author Ankit
     */
	public List<String> getAllAttributeValue(TestSuites ts,String attribute, String elementPath, String... parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		String text = "";
		List<String> allText = new ArrayList<String>();
		try{
		List<WebElement> wes = ts.getFindLocator().getWebElements(ts,elementPath, parms);
		try{
			for(WebElement we : wes ){
				text = we.getAttribute(attribute);
				if (text == null){
					ts.getTestReporting().addTestSteps(ts,method,"Text is : "+text+" for "+elementPath+" "+getAllString(parms), "WARNIG");
				}
				else{
				ts.getTestReporting().addTestSteps(ts,method, "<b>"+text+"</b> from: "+elementPath+ " "+getAllString(parms), "PASS");
				System.out.println(text);
				allText.add(text);
				}
			}
		}catch(Exception e){
				System.out.println("Exception occurred while geting text of: "+elementPath+ ""+getAllString(parms)+" "+e.getMessage());
				ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+" "+getAllString(parms), "FAIL");
				throw new Exception("Exception occurred while geting text of: "+elementPath+" "+e.getMessage());
			}
			
		}catch(Exception e) {
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+" "+getAllString(parms), "FAIL");
			throw new Exception(method+" Unable to get the webElement :"+elementPath+e.getMessage());
		}
		return allText;
	}

	
	/**
	 * Return the passed attributes text of the given element
	 * @author Ankit
	 */
	public String getAttributteValue(TestSuites ts,String attribute, String elementPath, String... parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		String text = "";
		try{
		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
		if(we !=null && verifyPresenceOfElement(ts,we)){
			try{
				text = we.getAttribute(attribute);
				ts.getTestReporting().addTestSteps(ts,method, text+" from: "+elementPath+" "+getAllString(parms), "PASS");
			}catch(Exception e){
				System.out.println("Exception occurred while geting text of: "+elementPath+" "+e.getMessage());
				ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+" "+getAllString(parms), "FAIL");
				throw new Exception("Exception occurred while geting text of: "+elementPath+" "+e.getMessage());
			}
		}
		else{
			System.out.println("Element : "+elementPath+" is not present");
			 ts.getTestReporting().addTestSteps(ts,method+" "+elementPath,"Element is present but not dispaly", "FAIL");
			throw new Exception("Element : "+elementPath+" is not present");
		}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+" "+getAllString(parms), "FAIL");
			throw new Exception(e.getMessage());
		}
		return text;
	}

	
	/**
	 * return true if the given element is selected
	 * @author Ankit
	 */
	public boolean isSelected(TestSuites ts,String elementPath, String... parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		boolean flag = false;
		try {
			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
			if (we !=null && verifyPresenceOfElement(ts,we)){
				try{
					if(we.isSelected()){
						flag = true;
						ts.getTestReporting().addTestSteps(ts,method, flag+" for"+elementPath+" "+getAllString(parms), "PASS");
					}
				}catch(Exception e){
					System.out.println("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
					ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+" "+getAllString(parms), "FAIL");
					throw new Exception("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
				}
			}
			else{
	           ts.getTestReporting().addTestSteps(ts,method+" "+elementPath,"Element is present but not dispaly", "FAIL");
				throw new Exception("Element: "+elementPath+" isn't present");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath, "FAIL");
			throw new Exception(e.getMessage());
		}
		return flag;
	}

	
	/**
	 * Create the select object and return
	 * @author Ankit
	 */
	public Select getSelectOptionObj(TestSuites ts,String elementPath, String... parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		Select sl = null;
		try {
			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath);
//			if (verifyPresenceOfElement(we)){
				try{
					sl = new Select(we);
				}catch(Exception e){
					System.out.println("Exception occurred while creating select obj: "+elementPath+" "+e.getMessage());
					ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+" "+getAllString(parms), "FAIL");
					throw new Exception("Exception occurred while creating select obj: "+elementPath+" "+e.getMessage());
				}
//			}
//			else{
//		      // ts.getTestReporting().addTestSteps(ts,"verifyPresenceOfElement before "+method,elementPath, "FAIL");
//				throw new Exception("Selct obj isn't visible for: "+elementPath);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+" "+getAllString(parms), "FAIL");
			throw new Exception(e.getMessage());
		}
		return sl;
	}
	
	/**
	 * verify the visible text of the given element
	 * @author Ankit
	 * @param path
	 * @param expectText
	 * @throws Exception
	 */
	public void verifyVisibleText(TestSuites ts,String expectText,String elementPath, String...parms ) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		String visibleText = getText(ts,elementPath,parms);
		if (visibleText.equalsIgnoreCase(expectText)){
			System.out.println("Visible Text:"+visibleText+" is same as expected: "+expectText);
			ts.getTestReporting().addTestSteps(ts,"Expected text: "+expectText,"text on"+elementPath+" "+getAllString(parms)+" : "+visibleText, "PASS");
		}
		else{
			System.out.println("Visible Text:"+visibleText+" isn't same as expected: "+expectText);
			ts.getTestReporting().addTestSteps(ts,"Expected text: "+expectText,"text on"+elementPath+" "+getAllString(parms)+" : "+visibleText, "FAIL");
			throw new Exception("Visible Text:"+visibleText+" isn't same as expected: "+expectText);
		}
	}
	
	
	/**
	 * verify the visible text of the given element
	 * @author Ankit
	 * @param path
	 * @param expectText
	 * @throws Exception
	 */
	public void verifyContainsText(TestSuites ts,String expectText,String elementPath, String...parms ) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		String visibleText = getText(ts,elementPath,parms);
		if (visibleText.contains(expectText)){
			System.out.println("Visible Text: "+visibleText+" has expected: "+expectText);
			ts.getTestReporting().addTestSteps(ts,"Expected text: "+expectText,"text on"+elementPath+" "+getAllString(parms)+" : "+visibleText, "PASS");
		}
		else{
			System.out.println("Visible Text: "+visibleText+" does not contains expected: "+expectText);
			ts.getTestReporting().addTestSteps(ts,"Expected text: "+expectText,"text on"+elementPath+" "+getAllString(parms)+" : "+visibleText, "FAIL");
			throw new Exception("Visible Text: "+visibleText+" does not contains expected: "+expectText);
		}
	}
	
	
	/**
	 * verify the visible text of the given element
	 * @author Ankit
	 * @param path
	 * @param expectText
	 * @throws Exception
	 */
	public void verifyAttributeValue(TestSuites ts,String expectText,String attribute, String elementPath, String...parms ) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		String visibleText = getAttributteValue(ts, attribute, elementPath, parms);
		if (visibleText.equalsIgnoreCase(expectText)){
			System.out.println(attribute+" value: "+visibleText+" has expected: "+expectText);
			ts.getTestReporting().addTestSteps(ts,attribute+" value: "+expectText,"text on"+elementPath+" "+getAllString(parms)+" : "+visibleText, "PASS");
		}
		else{
			System.out.println(attribute+" value: "+visibleText+" does not contains expected: "+expectText);
			ts.getTestReporting().addTestSteps(ts,attribute+" value: "+expectText,"text on"+elementPath+" "+getAllString(parms)+" : "+visibleText, "FAIL");
			throw new Exception(attribute+" value: "+visibleText+" does not contains expected: "+expectText);
		}
	}

	
	
	/**
	 * Use when the Input text would present after the clicking on button.
	 * It will enter text when input text present 
	 * @author Ankit
	 */
	public boolean clickAndenterValue(TestSuites ts, String elementPath1, String elementPath, String text, String... params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		boolean flag = false;
		WebElement we =null;
		WebElement webElement = null;
		for(int i =0; i <2; i++){
//			webElement = ts.getFindLocator().getWebElement(ts,elementPath1);
			webElement = ts.getFindLocator().getWebElementVisiblity(ts,elementPath1);
		webElement.click();
//		we = ts.getFindLocator().getWebElement(ts,elementPath, params);
		we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath,params);
		if(we !=null && verifyPresenceOfElement(ts,we)){
			break;
		}
		}
		if(we !=null && verifyPresenceOfElement(ts,we)){
			try{
			we.click();
			we.clear();
			if(!we.getText().isEmpty()){
				we.click();
				we.clear();
			}
			we.click();
			we.sendKeys(text);
			flag = true;
				System.out.println("Entered value: "+text+" on: "+elementPath);
				if(ts.getTestReporting() !=null)
				ts.getTestReporting().addTestSteps(ts,method+" "+text," on"+elementPath+" "+getAllString(params), "PASS");
			}catch (StaleElementReferenceException st) {
				webElement = ts.getFindLocator().getWebElementVisiblity(ts,elementPath1);
				webElement.click();
				we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath,params);
				JavascriptExecutor executor = (JavascriptExecutor)ts.getDriver();
				executor.executeScript("arguments[0].click();", we);
				executor.executeScript("arguments[0].setAttribute('value', '');",we);
				we.sendKeys(text);
			   flag = true;
			   System.out.println("Entered value via Java Scripts: "+text+" on: "+elementPath);
			   ts.getTestReporting().addTestSteps(ts,method+" "+text," on"+elementPath+" via java script "+getAllString(params), "PASS");
			}
			catch(Exception e){
				try{
//                Need to write code with JavaScript
					JavascriptExecutor executor = (JavascriptExecutor)ts.getDriver();
					executor.executeScript("arguments[0].click();", we);
					executor.executeScript("arguments[0].setAttribute('value', '"+text+"');",we);
				flag = true;
				System.out.println("Entered value via Java Scripts: "+text+" on: "+elementPath);
				ts.getTestReporting().addTestSteps(ts,method+" "+text," on"+elementPath+" via java script "+getAllString(params), "PASS");
				}
				catch(Exception e1){
					 if(ts.getLoopCount() == 2){
						 ts.setLoopCount(0);
						 ts.getTestReporting().addTestSteps(ts,method+" "+elementPath+ " "+getAllString(params),"Element is tried for two times but not dispaly", "FAIL");
						 throw new Exception("Unable to enter Text on: "+elementPath);
					 }
					 ts.increaseCount();
					 clickAndenterValue(ts, elementPath1, elementPath, text, params);
				}
			}
		}
		else{
//			 ts.getTestReporting().addTestSteps(ts,"verifyPresenceOfElement before "+method,elementPath+" "+getAllString(params), "FAIL");
//			throw new Exception("Element is present but not dispaly: "+elementPath);
			 if(ts.getLoopCount() == 2){
				 ts.setLoopCount(0);
				 ts.getTestReporting().addTestSteps(ts,method+" "+elementPath+ " "+getAllString(params),"Element is tried for two times but not dispaly", "FAIL");
				 throw new Exception("Unable to enter Text on: "+elementPath);
			 }
			 ts.increaseCount();
			 clickAndenterValue(ts, elementPath1, elementPath, text, params);
		}
		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_5"));
		return flag;
	}
	
	
	/**
	 * Use when the Input text would present after the clicking on button.
	 * It will enter text when input text present 
	 * @author Ankit
	 */
	public boolean clickAndenterValue(TestSuites ts, WebElement webElement, String elementPath, String text, String... params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		boolean flag = false;
		WebElement we =null;
		//		WebElement webElement = ts.getFindLocator().getWebElement(ts,elementPath1);
		for(int i =0; i <2; i++){
			webElement.click();
			//		we = ts.getFindLocator().getWebElement(ts,elementPath, params);
			we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath,params);
			if(we !=null && verifyPresenceOfElement(ts,we)){
				break;
			}
		}
		if(we !=null && verifyPresenceOfElement(ts,we)){
			try{
				we.click();
				we.clear();
				if(!we.getText().isEmpty()){   // temp changes for Survey Release
					we.click();
					we.clear();
				}
				we.click();
				we.sendKeys(text);
				flag = true;
				System.out.println("Entered value: "+text+" on: "+elementPath);
				if(ts.getTestReporting() !=null)
					ts.getTestReporting().addTestSteps(ts,method+" "+text," on"+elementPath+" "+getAllString(params), "PASS");
			}catch(Exception e){
				try{
					e.printStackTrace();
					//                Need to write code with JavaScript
					JavascriptExecutor executor = (JavascriptExecutor)ts.getDriver();
					executor.executeScript("arguments[0].click();", webElement);
					executor.executeScript("arguments[0].click();", we);
					executor.executeScript("arguments[0].setAttribute('value', '');",we);
					executor.executeScript("arguments[0].setAttribute('value', '"+text+"');",we);
					flag = true;
					System.out.println("Entered value via Java Scripts: "+text+" on: "+elementPath);	
				}
				catch(Exception e1){
					e1.printStackTrace();
					if(ts.getTestReporting() !=null)
						ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+" "+getAllString(params), "WARNING");
					clickAndenterValue(ts, webElement, elementPath, text, params);
					throw new Exception("Unable to enter Text on: "+elementPath);
				}
			}
		}
		else{
			ts.getTestReporting().addTestSteps(ts,"verifyPresenceOfElement before "+method,elementPath+" "+getAllString(params), "FAIL");
			throw new Exception("Element is present but not dispaly: "+elementPath);
		}
		return flag;
	}
	
	

	/**
	 * Input text on the given Element
	 * @author Ankit
	 */
	public boolean enterValue(TestSuites ts, String elementPath, String text, String... params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		boolean flag = false;
		//		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, params);
		WebElement we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath,params);
		if(we !=null && verifyPresenceOfElement(ts,we)){
			try{
//				Capabilities cap = ((RemoteWebDriver) ts.getDriver()).getCapabilities();
//				String browserName = cap.getBrowserName().toLowerCase();
//				if (browserName.equalsIgnoreCase("edge") || browserName.equalsIgnoreCase("ie") || browserName.contains("internet")){
//					new Actions(ts.getDriver()).moveToElement(we).click(we).build().perform();
//					new Actions(ts.getDriver()).sendKeys(we, text).build().perform();
//				}
//				else {
					we.click();
					we.clear();
					if(!we.getText().isEmpty()){
						we.click();
						we.clear();
					}
					we.click();
					we.sendKeys(text);
					//			we.submit();
					flag = true;
					System.out.println("Entered value: "+text+" on: "+elementPath);
//				}
				if(ts.getTestReporting() !=null)
					ts.getTestReporting().addTestSteps(ts,method+" "+text," on"+elementPath+" "+getAllString(params), "PASS");
			}catch(Exception e){
				try{
					//                Need to write code with JavaScript
					JavascriptExecutor executor = (JavascriptExecutor)ts.getDriver();
					executor.executeScript("arguments[0].click();", we);
					executor.executeScript("arguments[0].setAttribute('value', '"+text+"');",we);
					flag = true;
					System.out.println("Entered value via Java Scripts: "+text+" on: "+elementPath);
					ts.getTestReporting().addTestSteps(ts,method+" "+text," on"+elementPath+" "+getAllString(params), "PASS");
				}
				catch(Exception e1){
					if(ts.getTestReporting() !=null)
						ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+" "+getAllString(params), "FAIL");
					throw new Exception("Unable to enter Text on: "+elementPath);
				}
			}
		}
		else{
			ts.getTestReporting().addTestSteps(ts,"verifyPresenceOfElement before "+method,elementPath+" "+getAllString(params), "FAIL");
			throw new Exception("Element is present but not dispaly: "+elementPath);
		}
		return flag;
	}
	
	
	/**
	 * Input text on the given Element
	 * @author Ankit
	 */
	public boolean enterValueAndEnterKey(TestSuites ts, String elementPath, String text, Keys keys, boolean secondKey,String... params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		boolean flag = false;
		//		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, params);
		WebElement we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath,params);
		if(we !=null && verifyPresenceOfElement(ts,we)){
			try{
//				Capabilities cap = ((RemoteWebDriver) ts.getDriver()).getCapabilities();
//				String browserName = cap.getBrowserName().toLowerCase();
//				if (browserName.equalsIgnoreCase("edge") || browserName.equalsIgnoreCase("ie") || browserName.contains("internet")){
//					new Actions(ts.getDriver()).moveToElement(we).click(we).build().perform();
//					new Actions(ts.getDriver()).sendKeys(we, text).build().perform();
//				}
//				else {
					we.click();
					we.clear();
					if(!we.getText().isEmpty()){
						we.click();
						we.clear();
					}
					we.click();
					we.sendKeys(text);
					//			we.submit();
					Thread.sleep(CommonDataMaps.waitConfigValues.get("second_1"));
					we.sendKeys(keys);
					if(secondKey) {
						Thread.sleep(CommonDataMaps.waitConfigValues.get("second_1"));
						we.sendKeys(keys);
					}
					flag = true;
					System.out.println("Entered value: "+text+" on: "+elementPath);
//				}
				if(ts.getTestReporting() !=null)
					ts.getTestReporting().addTestSteps(ts,method+" "+text," on"+elementPath+" "+getAllString(params), "PASS");
			}catch(Exception e){
				try{
					//                Need to write code with JavaScript
					JavascriptExecutor executor = (JavascriptExecutor)ts.getDriver();
					executor.executeScript("arguments[0].click();", we);
					executor.executeScript("arguments[0].setAttribute('value', '"+text+"');",we);
					flag = true;
					System.out.println("Entered value via Java Scripts: "+text+" on: "+elementPath);
					ts.getTestReporting().addTestSteps(ts,method+" "+text," on"+elementPath+" "+getAllString(params), "PASS");
				}
				catch(Exception e1){
					if(ts.getTestReporting() !=null)
						ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+" "+getAllString(params), "FAIL");
					throw new Exception("Unable to enter Text on: "+elementPath);
				}
			}
		}
		else{
			ts.getTestReporting().addTestSteps(ts,"verifyPresenceOfElement before "+method,elementPath+" "+getAllString(params), "FAIL");
			throw new Exception("Element is present but not dispaly: "+elementPath);
		}
		return flag;
	}
	
	public void openUrl(TestSuites ts, String url)  {
		ts.getDriver().get(url);
		try {
			ts.getFindLocator().waitForPageLoadData(ts);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
	}


	
	/**
	 * Clear text on the given Element
	 * @author Ankit
	 */
	public boolean clearEnteredValue(TestSuites ts, String elementPath, String... params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		boolean flag = false;
//		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, params);
		WebElement we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath,params);
		String text =  getAttributteValue(ts, "value", elementPath, params);
		if(we !=null && verifyPresenceOfElement(ts,we)){
			try{
			if (!text.isEmpty()){
			for (int i =0; i <= text.length(); i++)
				we.sendKeys(Keys.BACK_SPACE);
			}
			we.clear();
			we.sendKeys(Keys.CLEAR);
			flag = true;
				if(ts.getTestReporting() !=null)
				ts.getTestReporting().addTestSteps(ts,method+" "+text," on"+elementPath+" has been cleared"+getAllString(params), "PASS");
			}catch(Exception e){
//                Need to write code with JavaScript
					if(ts.getTestReporting() !=null)
					ts.getTestReporting().addTestSteps(ts,method,"Unable to clear text :"+elementPath+" "+ text+" "+getAllString(params), "FAIL");
					throw new Exception("Unable to clear Text on: "+elementPath);
//				}
			}
		}
		else{
			 ts.getTestReporting().addTestSteps(ts,"verifyPresenceOfElement before "+method,elementPath+" "+getAllString(params), "FAIL");
			throw new Exception("Element is present but not dispaly: "+elementPath);
		}
		return flag;
	}

	
	/**
	 * Select the given value from the dropdown
	 * @author Ankit
	 */
	public boolean selectByValue(TestSuites ts,String elementPath, String value, String... params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		Select sl = getSelectOptionObj(ts,elementPath, params);
		try{
		sl.selectByValue(value);
		ts.getTestReporting().addTestSteps(ts,method,"Selected: "+value+" from dropDown:"+elementPath+" "+getAllString(params), "PASS");
		return true;
		}catch(Exception e){
			System.out.println("Unable to select the value: "+value+" from dropDown:"+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to select the value: "+value+" from dropDown:"+elementPath+" "+getAllString(params), "FAIL");
			return false;
		}
        
	}

	/**
	 * This method will verify the presence of passed webElement 
	 * @author Ankit
	 * @param we
	 * @return
	 */
	public boolean verifyPresenceOfElement(TestSuites ts,WebElement we){
//		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		boolean flag = false;
		try{
			if (we.isDisplayed()){
				if(we.isEnabled()){
					flag = true;
				}
			}
		}catch(Exception e){
          e.printStackTrace();
		}
		return flag;
	}
	
	public String getAllString(String...params){
		String text ="";
		for(int i =0; i <params.length; i++){
			text = text+","+params[i];
		}
		return text;
	}
	
	/**
	 * This method will verify the presence of passed webElement 
	 * @author Ankit
	 * @param elementPath
	 * @return
	 * @throws Exception 
	 */
	public boolean verifyPresenceOfElement(TestSuites ts,String elementPath, String...params) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
//		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, params);
		boolean flag = false;
//		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, params);
		WebElement we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath,params);
		try{
			for(int i =0; i<=3; i++){
				if (we !=null && we.isDisplayed()){
					if(we.isEnabled()){
						flag = true;
						ts.getTestReporting().addTestSteps(ts,method,elementPath+" "+getAllString(params)+" is present", "PASS");
						break;
					}
				}
				else{
					Thread.sleep(CommonDataMaps.waitConfigValues.get("second_4"));
					we = ts.getFindLocator().getWebElement(ts,elementPath, 6, params);
					if(i ==3){
						ts.getTestReporting().addTestSteps(ts,method,elementPath+" "+getAllString(params)+" Element is present but not dispaly", "FAIL");
						throw new Exception(elementPath+" Element is present but not dispaly");
					}
				}
			}
		}catch(Exception e){
			ts.getTestReporting().addTestSteps(ts,method,elementPath+" "+getAllString(params)+" is not present", "FAIL");
			throw new Exception(elementPath+" is not present");
		}
		return flag;
	}
	
	
	
	
	/**
	 * This method will verify the presence of passed webElement and does not throw exception if not present
	 * @author Ankit
	 * @param elementPath
	 * @return
	 * @throws Exception 
	 */
	public boolean verifyPresenceOfElementOpt(TestSuites ts, String elementPath,long time, String...params) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath,time, params);
		boolean flag = false;
		try{
			if (we !=null && we.isDisplayed()){
				if(we.isEnabled()){
					flag = true;
					ts.getTestReporting().addTestSteps(ts,method,elementPath+" "+getAllString(params)+" is present", "PASS");
				}
				else {
					throw new Exception("Not Enablel");
				}
			}
			else {
				throw new Exception("Not Displayed");
			}
		}catch(Exception e){
//           e.printStackTrace();
			System.out.println("Element is not present");
			ts.getTestReporting().addTestSteps(ts,method,elementPath+" "+getAllString(params)+" is not present", "PASS");
           flag = false;
		}
		return flag;
	}
	
	
	/**
	 * This method will verify the presence of passed webElement and does not throw exception if not present
	 * @author Ankit
	 * @param elementPath
	 * @return
	 * @throws Exception 
	 */
	public boolean verifyPresenceOfElementOptWithOutCondi(TestSuites ts, String elementPath,long time, String...params) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		WebElement we = ts.getFindLocator().getWebElementWithOutConditon(ts,elementPath,time, params);
		boolean flag = false;
		try{
			if (we !=null && we.isDisplayed()){
				if(we.isEnabled()){
					flag = true;
					ts.getTestReporting().addTestSteps(ts,method,elementPath+" "+getAllString(params)+" is present", "PASS");
				}
			}
		}catch(Exception e){
//           e.printStackTrace();
			System.out.println("Element is not present");
           flag = false;
		}
		return flag;
	}
	
	public void refreshWindow(TestSuites ts, boolean wait) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		String url = getCurrentUrl(ts);
		ts.getTestReporting().addTestSteps(ts,method,"Page is going to refresh with the current url: "+url, "PASS");
//		ts.getDriver().navigate().to(url);
		ts.getDriver().navigate().refresh();
		ts.getTestReporting().addTestSteps(ts,method,"Page refreshed with url: "+url, "PASS");
		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_1"));
		if(wait)
			Thread.sleep(CommonDataMaps.waitConfigValues.get("second_10"));
	}
	
	public void refreshWindow(TestSuites ts) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		String url = getCurrentUrl(ts);
		ts.getTestReporting().addTestSteps(ts,method,"Page is going to refresh with the current url: "+url, "PASS");
//		ts.getDriver().navigate().to(url);
		ts.getDriver().navigate().refresh();
		ts.getTestReporting().addTestSteps(ts,method,"Page refreshed with url: "+url, "PASS");
		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_10"));
	}
	
	
	public void refreshWindowWithNewUrl(TestSuites ts, String url) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
//		String url = getCurrentUrl(ts);
		ts.getTestReporting().addTestSteps(ts,method,"Page is going to refresh with the current url: "+url, "PASS");
//		ts.getDriver().navigate().to(url);
		ts.getDriver().navigate().to(url);
		ts.getTestReporting().addTestSteps(ts,method,"Page refreshed with url: "+url, "PASS");
		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_10"));
	}
	
	/**
	 * This method will verify the presence of passed webElement 
	 * @author Ankit
	 * @param elementPath
	 * @return
	 * @throws Exception 
	 */
	public boolean verifyNonPresenceOfElement(TestSuites ts,String elementPath, long time, String...params) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, time,params);
		boolean flag = false;
		try{
			if (we !=null && we.isDisplayed()){
				if(we.isEnabled()){
					flag = true;
				}
			}else{
				ts.getTestReporting().addTestSteps(ts,method,elementPath+" "+getAllString(params)+" is not present", "PASS");
			}
		}catch(Exception e){
			ts.getTestReporting().addTestSteps(ts,method,elementPath+" "+getAllString(params)+" is not present", "PASS");
//           e.printStackTrace();
           flag = false;
		}
		if (flag){
			ts.getTestReporting().addTestSteps(ts,method,elementPath+" "+getAllString(params)+" is present", "FAIL");
		}
		return flag;
	}
	
	/**
	 * Return the current open url tilte
	 * @return
	 */
	public String getTitle(TestSuites ts){
		return ts.getDriver().getTitle();
	}
	
	public String getCurrentUrl(TestSuites ts){
		return ts.getDriver().getCurrentUrl();
	}
	
	
	public String getParentWindowHandle(TestSuites ts){
		return ts.getDriver().getWindowHandle();
	}
	
	public void switchToWindow(TestSuites ts,String handle) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		ts.getDriver().switchTo().window(handle);
		refreshWindow(ts);
		ts.getTestReporting().addTestSteps(ts,method,"Current window ", "PASS", true);
	}
	
	
//	public void switchToIFrame(TestSuites ts,String elementPath, String...params){
//		String method = new Object(){}.getClass().getEnclosingMethod().getName();
//		WebElement we  =   ts.get
//		ts.getDriver().switchTo().frame(arg0)
//		ts.getTestReporting().addTestSteps(ts,method,"Current window ", "PASS", true);
//	}
	
	public boolean switchToIFrame(TestSuites ts, String elementPath,String...params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		boolean flag = false;
		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, params);
		try {for(int i =0; i <3; i++){
			if (we !=null && verifyPresenceOfElement(ts,we)){
				try{
					ts.getDriver().switchTo().frame(we);
					flag = true;
					if(ts.getTestReporting() !=null)
					ts.getTestReporting().addTestSteps(ts,method, elementPath+ " "+getAllString(params), "PASS");
					break;
					}catch(Exception e){
							if(ts.getTestReporting() !=null)
							ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+ " "+getAllString(params), "FAIL");
							System.out.println("Exception occurred while switching into frame: "+elementPath+" "+e.getMessage());
							throw new Exception("Exception occurred while switching into: "+elementPath+" "+e.getMessage());	
						}
//						System.out.println("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
					}
			else{
				Thread.sleep(CommonDataMaps.waitConfigValues.get("second_2"));
				we = ts.getFindLocator().getWebElement(ts,elementPath, 10, params);
				if(i ==2){
					if(ts.getTestReporting() !=null)
						ts.getTestReporting().addTestSteps(ts,method+" "+elementPath+ " "+getAllString(params),"Element is present but not dispaly", "FAIL");
					throw new Exception("Element: "+elementPath+" isn't present");
				}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			if(ts.getTestReporting() !=null)
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+ " "+getAllString(params), "FAIL");
			throw new Exception(e.getMessage());
		}
		return flag;
	}
	
	
	
	public boolean switchToIFrameByIdOrName(TestSuites ts, String idOrName, String elementPath, String...params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		boolean flag = false;
		ts.getDriver().switchTo().frame(idOrName);
		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, params);
		try {for(int i =0; i <3; i++){
			if (we !=null && verifyPresenceOfElement(ts,we)){
				try{
					flag = true;
					if(ts.getTestReporting() !=null)
						ts.getTestReporting().addTestSteps(ts,method, elementPath+ " "+getAllString(params), "PASS");
					break;
				}catch(Exception e){
					if(ts.getTestReporting() !=null)
						ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+ " "+getAllString(params), "FAIL");
					System.out.println("Exception occurred while switching into frame: "+elementPath+" "+e.getMessage());
					throw new Exception("Exception occurred while switching into: "+elementPath+" "+e.getMessage());	
				}
			}
			else{
				Thread.sleep(CommonDataMaps.waitConfigValues.get("second_2"));
				we = ts.getFindLocator().getWebElement(ts,elementPath, 10, params);
				if(i ==2){
					if(ts.getTestReporting() !=null)
						ts.getTestReporting().addTestSteps(ts,method+" "+elementPath+ " "+getAllString(params),"Element is present but not dispaly", "FAIL");
					throw new Exception("Element: "+elementPath+" isn't present");
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			if(ts.getTestReporting() !=null)
				ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+ " "+getAllString(params), "FAIL");
			throw new Exception(e.getMessage());
		}
		return flag;	
	}

	
	
	/**
	 * Switch to the next window except opened window
	 * @author Ankit
	 * @throws Exception 
	 */
//	public String switchToNextWindow(TestSuites ts,String parentHandle, boolean fail) throws Exception{
////		String parentHandle = getParentWindowHandle();
//		String currentHandle ="";
//		Set<String> win  = ts.getDriver().getWindowHandles();
//		String method = new Object(){}.getClass().getEnclosingMethod().getName();
//		if(ts.getTestReporting() !=null)
//		ts.getTestReporting().addTestSteps(ts,method,"Total number of wondow tabs are: "+win.size(), "PASS");
//		Iterator<String> it =  win.iterator();
//		if(win.size() > 1){
//		while(it.hasNext()){
//			String handle = it.next();
//			if (!handle.equalsIgnoreCase(parentHandle)){
//				ts.getDriver().switchTo().window(handle);
//				currentHandle = handle;
//			}
//		}
//		}
//		else{
//			if(fail)
//				ts.getTestReporting().addTestSteps(ts,method,"Total number of wondow tabs are: "+win.size()+" So can't switch", "FAIL");
//		}
//		ts.getSeleniumDriver().maximizeScreen(ts.getDriver());
//		refreshWindow(ts);
//		ts.getTestReporting().addTestSteps(ts,method,"Current window ", "PASS", true);
//		return currentHandle;
//	}
//	
	
	

	/**
	 * Switch to the next window except opened window
	 * @author Ankit
	 * @throws Exception 
	 */
	public String switchToNextWindow(TestSuites ts,boolean fail, String...parentHandle) throws Exception{
//		String parentHandle = getParentWindowHandle();
		String currentHandle ="";
		List<String> windowHandles = Arrays.asList(parentHandle);
		Set<String> win  = ts.getDriver().getWindowHandles();
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		if(ts.getTestReporting() !=null)
		ts.getTestReporting().addTestSteps(ts,method,"Total number of wondow tabs are: "+win.size(), "PASS");
		Iterator<String> it =  win.iterator();
		if(win.size() > 1){
		while(it.hasNext()){
			String handle = it.next();
			if (!windowHandles.contains(handle)){
				ts.getDriver().switchTo().window(handle);
				currentHandle = handle;
			}
		}
		}
		else{
			if(fail)
				ts.getTestReporting().addTestSteps(ts,method,"Total number of wondow tabs are: "+win.size()+" So can't switch", "FAIL");
		}
		ts.getSeleniumDriver().maximizeScreen(ts.getDriver());
		refreshWindow(ts);
		ts.getTestReporting().addTestSteps(ts,method,"Current window ", "PASS", true);
		return currentHandle;
	}
	
	
	/**
	 * Switch to the next window except opened window
	 * @author Ankit
	 * @throws Exception 
	 */
	public void closeNextWindow(TestSuites ts,String parentHandle) throws Exception{
		//		String parentHandle = getParentWindowHandle();
		String currentHandle ="";
		Set<String> win  = ts.getDriver().getWindowHandles();
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		if(ts.getTestReporting() !=null)
			ts.getTestReporting().addTestSteps(ts,method,"Total number of wondow tabs are: "+win.size(), "PASS");
		Iterator<String> it =  win.iterator();
		if(win.size() > 1){
			while(it.hasNext()){
				String handle = it.next();
				if (!handle.equalsIgnoreCase(parentHandle)){
					ts.getDriver().switchTo().window(handle);
					currentHandle = handle;
					ts.getTestReporting().addTestSteps(ts,method,"Closing current window ", "PASS", true);
					ts.getDriver().close();
				}
			}
			ts.getDriver().switchTo().window(parentHandle);
			ts.getTestReporting().addTestSteps(ts,method,"Closed next window now it is parent window ", "PASS", true);
		}
		else{
			ts.getTestReporting().addTestSteps(ts,method,"Total number of wondow tabs are: "+win.size()+" So can't switch", "WARNING");
		}
	}
	
	
	public void closeGivenWindow(TestSuites ts,String closeHandle, String parentHandle,boolean close) throws Exception{
//		String parentHandle = getParentWindowHandle();
		String currentHandle ="";
		Set<String> win  = ts.getDriver().getWindowHandles();
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		if(ts.getTestReporting() !=null)
		ts.getTestReporting().addTestSteps(ts,method,"Total number of wondow tabs are: "+win.size(), "PASS");
		Iterator<String> it =  win.iterator();
		if(win.size() > 1){
		while(it.hasNext()){
			String handle = it.next();
			if (handle.equalsIgnoreCase(closeHandle)){
				ts.getDriver().switchTo().window(handle);
				currentHandle = handle;
				ts.getDriver().close();
			}
		}
		}
		else{
			ts.getDriver().switchTo().window(parentHandle);
			if(close)
				ts.getTestReporting().addTestSteps(ts,method,"Total number of wondow tabs are: "+win.size()+" So can't switch", "FAIL");
		}
		ts.getDriver().switchTo().window(parentHandle);
		ts.getTestReporting().addTestSteps(ts,method,"Closed next window except parent window ", "PASS", true);
	}
	
	
	public void OpenNewUrl(TestSuites ts,String url){
		ts.getDriver().navigate().to(url);
	}
	
	public boolean OpenNewUrlValidation(TestSuites ts,String url) {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		boolean failed = false;
		try {
		ts.getTestReporting().addTestSteps(ts,"Opening ",url, "PASS", false);
		openUrl(ts, url);
		String title = getTitle(ts);
//		ts.getDriver().navigate().to(url);
//		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_1"));
		String currentURl = getCurrentUrl(ts);
		ts.getTestReporting().addTestSteps(ts,"Original "+url, "After url: "+currentURl, "PASS", false);
//		String title = ts.getDriver().getTitle();
//		if ((currentURl.contains("500") | currentURl.contains("501") | currentURl.contains("502") 
//				| currentURl.contains("503")) |	currentURl.contains("504") | currentURl.contains("505")){
//			ts.getTestReporting().addTestSteps(ts,"Original "+url, "<b>After url: "+currentURl+"</b>", "WARNING", true);
//			System.out.println("5XX error: "+url);
//			failed= true;
//		}
		if (!currentURl.equalsIgnoreCase(url)){
			ts.getTestReporting().addTestSteps(ts,"Original "+url, "<b>Redirected To: "+currentURl+"</b>", "WARNING", true);
			System.out.println("Original Url:  "+url+ " redirected url: "+currentURl);
			failed= true;
		}
//		if(title.contains("Page Not Found")) {
//			ts.getTestReporting().addTestSteps(ts,"URL "+url, "<b>Page not found</b>", "WARNING", true);
//			failed= true;
//		}
		ApiDefaultConfig api = new ApiDefaultConfig();
		int statusCode = api.checkgetRequest(url);
		if(statusCode != 200) {
			ts.getTestReporting().addTestSteps(ts,"Url: "+url, "<b>Status code: "+statusCode+"</b>", "WARNING", true);
			System.out.println("Status code: "+statusCode+" : of url : "+url);
			failed= true;
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return failed;
	}
	
	
	
	
	public String finalOpenNewUrlValidation(TestSuites ts,String url) throws InterruptedException{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		String failedReason = "";
		ts.getTestReporting().addTestSteps(ts,"Opening ",url, "PASS", false);
		openUrl(ts, url);
		String title = getTitle(ts);
//		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_1"));
		String currentURl = getCurrentUrl(ts);
		ts.getTestReporting().addTestSteps(ts,"Before "+url, "After url: "+currentURl, "PASS", false);
//		String title = ts.getDriver().getTitle();
//		if ((currentURl.contains("500") | currentURl.contains("501") | currentURl.contains("502") 
//				| currentURl.contains("503")) |	currentURl.contains("504") | currentURl.contains("505")){
//			ts.getTestReporting().addTestSteps(ts,"Before "+url, "<b>After url: "+currentURl+"</b>", "WARNING", true);
//			System.out.println("5XX error: "+url);
//			failedReason= "Web Tilte is: "+title;
//		}
		if (!currentURl.equalsIgnoreCase(url) && (!(currentURl.equals("https://birdeye.com/") && url.equals("https://birdeye.com")))){
			ts.getTestReporting().addTestSteps(ts,"Before "+url, "<b>After url: "+currentURl+"</b>", "WARNING", true);
			System.out.println("Original Url:  "+url+ " redirected url: "+currentURl);
			failedReason= "Redirected To: "+currentURl;
			if(url.contains("bit.ly/")) {
				failedReason = finalOpenNewUrlValidation(ts, currentURl);
			}
			if(url.contains("goo.gl/")) {
				failedReason = finalOpenNewUrlValidation(ts, currentURl);
			}
			return failedReason;
		}
//		if(title.contains("Page Not Found")) {
//			failedReason = "StatusCode = "+404;
//			ts.getTestReporting().addTestSteps(ts,"URL "+url, "<b>Page not found</b>", "WARNING", true);
//		}
		ApiDefaultConfig api = new ApiDefaultConfig();
		int statusCode = api.checkgetRequest(url);
		if(statusCode != 200 && statusCode != 429 && statusCode != 307) {
//		if(statusCode != 200) {
			ts.getTestReporting().addTestSteps(ts,"Url "+url, "<b>Status code: "+statusCode+"</b>", "WARNING", true);
			failedReason = "StatusCode = "+statusCode;
			System.out.println("Status code: "+statusCode+" : for url : "+url);
		}
		return failedReason;
	}
	
	
	/**
	 * This method will open the passed url into new tab on the same browser window
	 * @author Ankit
	 * @param ts
	 * @param parentWindowHandle
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String openUrlInNewTab(TestSuites ts,String parentWindowHandle, String url) throws Exception{
		
		((JavascriptExecutor)ts.getDriver()).executeScript("window.open()");
		String currentHandle = switchToNextWindow(ts,true,parentWindowHandle);
		OpenNewUrl(ts,url);
		return currentHandle;
	}
	
	
	/**
	 * This method will click on the passed location and open that location link in new tab
	 * @author Ankit
	 * @param ts
	 * @param parentWindowHandle
	 * @param elementPath
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String clickAndOpenInNewTab(TestSuites ts,String parentWindowHandle,String elementPath, String...params) throws Exception{
		Actions ac = getActionsDriver(ts);
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
//		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, params);
		WebElement we = ts.getFindLocator().getWebElementVisiblity(ts,elementPath,params);
		ts.getTestReporting().addTestSteps(ts,method, "clicking on: "+elementPath+" "+getAllString(params), "PASS");
		ac.keyDown(Keys.CONTROL).click(we).keyUp(Keys.CONTROL).build().perform();
		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_5"));
//		ac.sendKeys(we, Keys.CONTROL).click().build().perform();
		String currentHandle = switchToNextWindow(ts,true,parentWindowHandle);
		ts.getTestReporting().addTestSteps(ts,method, "New Window : "+elementPath+" "+getAllString(params), "PASS");
		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_5"));
		return currentHandle;
	}
	
	public void pressAnyKey(TestSuites ts, CharSequence keys) {
		Actions ac = getActionsDriver(ts);
		ac.sendKeys(keys).build().perform();
	}
	
	public void clickEnterKey(TestSuites ts, String elementPath, String...params) throws Exception{
		Actions ac = getActionsDriver(ts);
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, params);
		we.sendKeys(Keys.ENTER);
		ts.getTestReporting().addTestSteps(ts,method, "Entering the Key : "+elementPath+" "+getAllString(params), "PASS");
	}
	
	

	
	public String captureScreenShot(TestSuites ts) throws Exception{
		TakesScreenshot tss = (TakesScreenshot) ts.getDriver();
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
//		try{
//		File sourcePath = tss.getScreenshotAs(OutputType.FILE);
//		}catch(org.openqa.selenium.TimeoutException e){
//			System.out.println("Page timeout Exception occured");
//			String url = ts.getDriver().getCurrentUrl();
//			ts.getDriver().navigate().to(url);
//		}
		StopWatch pageLoad = new StopWatch();
        pageLoad.start();
//		String dest = path+File.separator+"Image_"+JavaWrappers.getCurrentTime("HH_MM_ss");
//		File destPath =  new File(dest); 
		String path = ts.getIndividulaSuiteReportFolder()+File.separator+"TestCases"+File.separator+"ScreenShot";
		File newImageFile = null;
		try {
			File sourcePath = tss.getScreenshotAs(OutputType.FILE);
			newImageFile = new File(path+File.separator); 
//			String fileName = "Image_"+JavaWrappers.getCurrentTime("HH_MM_ss");
			String fileName = "Image_"+System.currentTimeMillis();
//			newImageFile = File.createTempFile(fileName, ".png",newImageFile);
			newImageFile = new File(path+File.separator+fileName+".png");
//			FileUtils.copyFile(sourcePath, newImageFile);
//			FileUtils.moveDirectory(sourcePath, destPath);
//			FileUtils.moveFile(sourcePath, newImageFile);
//			FileUtils.moveFile(sourcePath, newImageFile);
			FileUtils.copyFile(sourcePath, newImageFile);
//			FileUtils.deleteQuietly(sourcePath);
			pageLoad.stop();
			long pageLoadTime_ms = pageLoad.getTime();
	        long pageLoadTime_Seconds = pageLoadTime_ms / 1000;
//	        System.out.println("Total ScreenShot Time: " + pageLoadTime_ms + " milliseconds");
		}catch(org.openqa.selenium.TimeoutException e){
			System.out.println("Page timeout Exception occured"+e.getMessage());
			e.printStackTrace();
			return "UnableToCapture";
//			ts.getTestReporting().addTestSteps(ts,method,"Error occured while taking screen shot "+e.getMessage(), "FAIL");
//			throw new Exception();
//			String url = ts.getDriver().getCurrentUrl();
//			ts.getDriver().navigate().to(url);
//			ts.getTestReporting().addTestSteps(ts,method,"Error occured while taking screen shot "+e.getMessage(), "FAIL");
		}catch (IOException e) {
			System.out.println("Error occured while capturing screen shot "+e.getMessage());
			e.printStackTrace();
			return "UnableToCapture";
//			throw new Exception();
//			ts.getTestReporting().addTestSteps(ts,method,"Error occured while taking screen shot "+e.getMessage(), "FAIL");
		}
		catch(Exception e) {
			System.out.println("Exception occured while capturing the screenshot"+e.getMessage());
			e.printStackTrace();
			return "UnableToCapture";
		}
		
		return newImageFile.getName();
	}


	/**
	 * return true of check box is selected, if not then select and return true
	 * @author Ankit
	 */
	public boolean isSelectedCheckBox(TestSuites ts,String elementPath, String... parms) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		boolean flag = false;
		try {
			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
//			if (verifyPresenceOfElement(we)){
				try{
					if (we !=null && we.isSelected()){
					flag = true;
					ts.getTestReporting().addTestSteps(ts,method, elementPath+" "+getAllString(parms), "PASS");
					}
					else
	 					flag = false;
					}catch(Exception e){
						ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+" "+getAllString(parms), "FAIL");
						System.out.println("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
						throw new Exception("Exception occurred while clicking on: "+elementPath+" "+e.getMessage());
					}
//				}
//				else{
//					 // ts.getTestReporting().addTestSteps(ts,"verifyPresenceOfElement before "+method,elementPath, "FAIL");
//					throw new Exception("Element: "+elementPath+" isn't present");
//				}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+" "+getAllString(parms), "FAIL");
			throw new Exception(e.getMessage());
		}
		return flag;
	}
	
	
	public String getConsoleData(TestSuites ts, String command, boolean flag) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		Object data = "";
		JavascriptExecutor jse = (JavascriptExecutor) ts.getDriver();
		try {
		data = (Object) jse.executeScript("return "+command);
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Command: "+command+" and data is: "+data.toString());
//		jse.executeScript("window.scrollTo(0,document.querySelector(.scrollingContainer).scrollHeight)", "");
		if(flag)
		ts.getTestReporting().addTestSteps(ts,method, "Command is: "+command+ "and data is: <b>"+data.toString()+"</b>", "PASS");
		return data.toString();
	}
	
	
//	public void scrollDown(TestSuites ts) throws Exception{
//		String method = new Object(){}.getClass().getEnclosingMethod().getName();
//		JavascriptExecutor jse = (JavascriptExecutor) ts.getDriver();
//		jse.executeScript("window.scrollBy(0,document.body.scrollHeight || document.documentElement.scrollHeight)", "");
////		jse.executeScript("window.scrollTo(0,document.querySelector(.scrollingContainer).scrollHeight)", "");
//		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_5")); 
//		ts.getTestReporting().addTestSteps(ts,method, "", "PASS");
//	}
	
	public void scrollDown(TestSuites ts, boolean flag) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		JavascriptExecutor jse = (JavascriptExecutor) ts.getDriver();
		jse.executeScript("window.scrollBy(0,document.body.scrollHeight || document.documentElement.scrollHeight)", "");
//		jse.executeScript("window.scrollTo(0,document.querySelector(.scrollingContainer).scrollHeight)", "");
		if(flag)
		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_5")); 
		ts.getTestReporting().addTestSteps(ts,method, "", "PASS");
	}
	
	
	public void scrollDown(TestSuites ts, boolean flag, boolean notfail){
		try {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		JavascriptExecutor jse = (JavascriptExecutor) ts.getDriver();
		jse.executeScript("window.scrollBy(0,document.body.scrollHeight || document.documentElement.scrollHeight)", "");
//		jse.executeScript("window.scrollTo(0,document.querySelector(.scrollingContainer).scrollHeight)", "");
		if(flag)
		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_5")); 
		ts.getTestReporting().addTestSteps(ts,method, "", "PASS");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	public void scrollUp(TestSuites ts) throws Exception{
//		String method = new Object(){}.getClass().getEnclosingMethod().getName();
//		JavascriptExecutor jse = (JavascriptExecutor) ts.getDriver();
//		jse.executeScript("window.scrollBy(0,-document.body.scrollHeight || -document.documentElement.scrollHeight)", "");
//		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_2"));
//		ts.getTestReporting().addTestSteps(ts,method, "", "PASS");
//	}
	
	public void scrollUp(TestSuites ts, boolean flag) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		JavascriptExecutor jse = (JavascriptExecutor) ts.getDriver();
		jse.executeScript("window.scrollBy(0,-document.body.scrollHeight || -document.documentElement.scrollHeight)", "");
		if(flag)
		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_2"));
		ts.getTestReporting().addTestSteps(ts,method, "", "PASS");
	}
	
	public void scrollJustUp(TestSuites ts) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		JavascriptExecutor jse = (JavascriptExecutor) ts.getDriver();
		jse.executeScript("window.scrollBy(0,(-document.body.scrollHeight)/20 || (-document.documentElement.scrollHeight)/20)", "");
		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_2"));
		ts.getTestReporting().addTestSteps(ts,method, "", "PASS");
	}
	
	
	public void scrollTillElement(TestSuites ts,boolean wait, String elementPath, String... parms) throws Exception{

		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		boolean flag = false;
		try {
			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
			try{
				((JavascriptExecutor)ts.getDriver()).executeScript("arguments[0].scrollIntoView(true);", we);
				Thread.sleep(500);
				ts.getTestReporting().addTestSteps(ts,method, elementPath+" "+getAllString(parms), "PASS");
				if(wait)
					Thread.sleep(CommonDataMaps.waitConfigValues.get("second_5"));
			}catch(Exception e){
				ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+" "+getAllString(parms), "FAIL");
				System.out.println("Exception occurred while scrolling: "+elementPath+" "+e.getMessage());
				throw new Exception("Exception occurred while scrolling: "+elementPath+" "+e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+" "+getAllString(parms), "FAIL");
			throw new Exception(e.getMessage());
		}
	}

	
	
	public void scrollTillElement(TestSuites ts,String elementPath, String... parms) throws Exception{

		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		boolean flag = false;
		try {
			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
			try{
				((JavascriptExecutor)ts.getDriver()).executeScript("arguments[0].scrollIntoView(true);", we);
				Thread.sleep(500);
				ts.getTestReporting().addTestSteps(ts,method, elementPath+" "+getAllString(parms), "PASS");
				Thread.sleep(CommonDataMaps.waitConfigValues.get("second_5"));
			}catch(Exception e){
				ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath+" "+getAllString(parms), "FAIL");
				System.out.println("Exception occurred while scrolling: "+elementPath+" "+e.getMessage());
				throw new Exception("Exception occurred while scrolling: "+elementPath+" "+e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath+" "+getAllString(parms), "FAIL");
			throw new Exception(e.getMessage());
		}
	}
	
	
	public boolean scrollTillElementOpt(TestSuites ts,String elementPath, String... parms) throws Exception{

		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		boolean flag = false;
		try {
//			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, parms);
			WebElement we = ts.getFindLocator().getWebElement(ts,elementPath, 10, parms);
			try{
				((JavascriptExecutor)ts.getDriver()).executeScript("arguments[0].scrollIntoView(true);", we);
				Thread.sleep(500);
				flag = true;
//				ts.getTestReporting().addTestSteps(ts,method, elementPath, "PASS");
			}catch(Exception e){
//				ts.getTestReporting().addTestSteps(ts,method,"Exception: "+e.getMessage()+" for "+elementPath, "FAIL");
				System.out.println("Exception occurred while scrolling: "+elementPath+" "+e.getMessage());
//				throw new Exception("Exception occurred while scrolling: "+elementPath+" "+e.getMessage());
//				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to get the webElement of: "+elementPath);
//			ts.getTestReporting().addTestSteps(ts,method,"Unable to get the webElement :"+elementPath, "FAIL");
//			throw new Exception(e.getMessage());
//			e.printStackTrace();
		}
		return flag;
	}
	
	public void clearHistory(TestSuites ts){
        JavascriptExecutor js = (JavascriptExecutor) ts.getDriver();
        js.executeScript("history.go(0)");
	}
	
	public void navigateBack(TestSuites ts) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		ts.getTestReporting().addTestSteps(ts,method, "Current Window before back", "PASS");
		ts.getDriver().navigate().back();
		Thread.sleep(CommonDataMaps.waitConfigValues.get("second_5")); 
		ts.getTestReporting().addTestSteps(ts,method, "After back", "PASS");
	}
	
}
