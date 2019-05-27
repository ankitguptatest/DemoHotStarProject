package com.ankit.Selenium;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ankit.DataMaps.CommonDataMaps;

import io.appium.java_client.MobileBy;


public class FindLocators extends SeleniumDriver{

	
	/**
	 * Store all locators methods of selenium: {@link By} and Appium {@link MobileBy}
	 * in map using key as method of By and value as a full method name
	 * @author Ankit
	 */
	private  Map<String, Method> methods = new HashMap<String, Method>();
	
	public FindLocators(){
		storeAllAvailableLocatorMethods();
	}

	private void storeAllAvailableLocatorMethods(){
		try{
			//Field containing the Array of Method Objects for {@link By} class
			Method[] byMeths = (By.class).getMethods();
			//Field containing the Array of Method Objects for {@link MobileBy} class
			Method[] mobByMeths = (MobileBy.class).getMethods();

			List<Method> methList = new ArrayList<Method>();
			for(Method meth : byMeths)
				methList.add(meth);
			for(Method meth : mobByMeths)
				methList.add(meth);
			for (Method byMeth : methList){
				String methName = byMeth.toString();
				methName = methName.split("[(]")[0];
				int len = methName.split("[.]").length;
				methName = methName.split("[.]")[len - 1];
				methods.put(methName, byMeth);
			}
		}catch(Exception e){
			e.printStackTrace();
			e.getMessage();
		}
	}
	
	/**
	 * This method will give you the object of your locator {@link Method} which is available in Selenium and Appium
	 * @author ankit.gupta04
	 * @param method Name of the {@link By} Method
	 * @return
	 */
	private  Method getByMethod(String locator) {
		Method retMethod = null;
		try {
			if (methods.containsKey(locator)){
				retMethod = methods.get(locator);
			}
			else if(methods.containsKey(locator.toLowerCase())){
				retMethod = methods.get(locator);
			}
			else{
				throw new Exception("Given locator: "+locator+" is not available in Selenium or Appium methods");
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		return retMethod;
	}
	
	/**
	 * Method will return the {@link WebElement}} of given path if path is valid 
	 * and present in the object repository 
	 * @author Ankit
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public  WebElement getWebElement(TestSuites ts,String path,String...params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		WebElement we = null;
		try{
			String locatorBy ="";
			String value ="";
			String page = path.split("/")[0];
			String elementPath = path.split("/")[1];
			try{
				HashMap<String, String> map = CommonDataMaps.objectRepoMapValues.get(page).get(elementPath);
				for(Entry<String, String> entry: map.entrySet()) {
					locatorBy = entry.getKey();
					//	        System.out.println("Ket: "+locatorBy);
					value = entry.getValue();
					//	        System.out.println("value: "+value);
					//	        System.out.println(entry.getValue());
					try{
						if(!locatorBy.isEmpty() && !value.isEmpty()){
							//				By by = getByMethod(locatorBy);
							value = MessageFormat.format(value, (Object[]) params);
							value = value.trim();
							By by = (By) getByMethod(locatorBy).invoke(null, value);
							//				ts.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
							System.out.println("locator for : "+path+" is :"+value);
							int expWait = CommonDataMaps.waitConfigValues.get("ExplixitWait");
							JavascriptExecutor js =  (JavascriptExecutor) ts.getDriver();
							WebDriverWait wait = new WebDriverWait(ts.getDriver(), expWait);
							wait.until((ExpectedCondition<Boolean>) wd ->
					        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
//							wait.until((ExpectedCondition<Boolean>) wd ->
//					        ((JavascriptExecutor) wd).executeScript("return jQuery.active").toString().equals("0"));
//							waitForPageLoad(ts);
							waitForPageLoad4JavaScriptAndReact4Script(ts);
							we = wait.until(ExpectedConditions.presenceOfElementLocated(by));

//							we = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
							//				ts.getDriver();.manage().timeouts().implicitlyWait(DataHashMap.waitConfigValues.get("ImplicitWait"), TimeUnit.SECONDS);
							if(we != null){
								highLighter(ts,we);
								break;
							}
						}
						else{
							ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
							throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
						}
					}catch(Exception e){
						ts.getTestReporting().addTestSteps(ts,method,"Selenium Exception: "+e.getMessage(), "WARNING");
						System.out.println("Selenium Exception: "+e.getMessage());
					}
				}
			}catch(Exception e1){
//				e1.printStackTrace();
//				ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
				System.out.println("Given element path :"+path+" isn't present in object repository");
				throw new Exception(e1.getMessage());
			}
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
			ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository"+e.getMessage(), "FAIL");
			throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
		}
		return we;
	}

	
	
	
	
	public void waitForPageLoad(TestSuites ts) throws InterruptedException{
	    JavascriptExecutor executor = (JavascriptExecutor)ts.getDriver();
	    int count =0;
	    if((Boolean) executor.executeScript("return window.jQuery != undefined")){
	        while(!(Boolean) executor.executeScript("return jQuery.active == 0")){
	            Thread.sleep(200);
	            if(count>4)
	            	break;
	            count++;
	        }
	    }
	    return;
	}
	
	
	public void waitForPageLoadData(TestSuites ts) throws InterruptedException{
	    JavascriptExecutor executor = (JavascriptExecutor)ts.getDriver();
	    int count =0;
	    if((Boolean) executor.executeScript("return window.jQuery != undefined")){
	        while(!(Boolean) executor.executeScript("return jQuery.active == 0")){
	            Thread.sleep(100);
	            if(count>40)
	            	break;
	            count++;
	        }
	    }
	    return;
	}
	
	
	public boolean waitForPageLoad4JavaScriptAndReact4Script(TestSuites ts) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		JavascriptExecutor executor = (JavascriptExecutor)ts.getDriver();
		boolean flag =false;
		WebDriverWait wait = new WebDriverWait(ts.getDriver(), 20);
		WebElement we = null;
		String xpath ="";
		try{
			if(ts.getDriver().getCurrentUrl().contains("birdeye.com/")) {
			
			if((Boolean) executor.executeScript("return window.jQuery != undefined")){
//				xpath = "//body[contains(@class,'pace-running')]";
//				we = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
				xpath = "//body[contains(@class,'pace-done')] | //body[contains(@class,'signInBg') or contains(@class,'signin-bg')] | //body[contains(@class,'PublicProfile') or contains(@class,'CheckIn') or contains(@class,'Mobile')] | //div[@id='app']";
				we = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
				we = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				if (we == null){
//					throw new Exception("Page load fail");
				}
				else{
					flag = true;
				}
			}
			//	    else if((Boolean) executor.executeScript("return window.jQuery == undefined")){
			else{
				
				xpath = "//div[@id='nprogress']";
				String cssSelector = "div.nprogress";
				xpath = "//html[contains(@class,'nprogress-done')] | //div[@id='ads'] | //div[@class='loading hide']";
//				boolean loaded = false;
//				wait = new WebDriverWait(ts.getDriver(), 0);
//				wait = new WebDriverWait(ts.getDriver(), 2);
//				if ((wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))) != null)){
//					loaded = true;
//				}
//				else{
//					flag = true;
//				}
////				we = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
//				if (loaded){
//					wait = new WebDriverWait(ts.getDriver(), 50);
//				ts.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
//				boolean size = (ts.getDriver().findElements(By.xpath(xpath)).size() >0);
//				System.out.println(size);
//				boolean know = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
//                System.out.println(know);
////                know = wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))));
//                System.out.println(know);
				long millis = System.currentTimeMillis();
//				if(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)))){
				if(wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))) != null){
//				if(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(cssSelector)))){
					long millis1 = System.currentTimeMillis();
					System.out.println("Data has been uploaded in react "+((millis1-millis)%1000) );
					flag = true;
				}
//				}
//				if (we == null){
////					throw new Exception("Page load fail");
//				}
//				else{
//					flag = true;
//				}

			}
			}
		}
		catch(Exception e){
//			ts.getTestReporting().addTestSteps(ts,method," Page laod has been failed after 200 sec wait xpath "+xpath, "WARNING");
		}
		return flag;
	}
	
	
	
	
	public boolean waitForPageLoad4JavaScriptAndReact(TestSuites ts) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName()+" on";
		JavascriptExecutor executor = (JavascriptExecutor)ts.getDriver();
		boolean flag =false;
		WebDriverWait wait = new WebDriverWait(ts.getDriver(), 60);
		WebElement we = null;
		String xpath ="";
		try{
			String elementPath = "IndividualAccountPage/VisiblePageOption";
			we = ts.getFindLocator().getWebElementclickAble(ts, elementPath,"Reviews");
			if((Boolean) executor.executeScript("return window.jQuery != undefined") && 
					((Boolean) executor.executeScript("return window.webpackJsonp == undefined"))){
				System.out.println("Current page is JavaScript page");
//				xpath = "//body[contains(@class,'pace-running')]";
				xpath = "//body[contains(@class,'pace-running')] | //body[contains(@class,'pace-done')]";
				we = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
				xpath = "//body[contains(@class,'pace-done')]";
				we = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
//				we = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				if (we == null){
//					throw new Exception("Page load fail");
				}
				else{
					flag = true;
				}
			}
			
//			Commented For Old changes, Now done parameter in the React
/*			else {
			//	    else if((Boolean) executor.executeScript("return window.jQuery == undefined")){
			xpath = "//div[@id='nprogress']";
			System.out.println("Current page is React page");
//			xpath = "//html[@class='gr__birdeye_com' or @class=' ' or @class and not(contains(@class,'js'))]";
			we = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
//			xpath = "//html[@class='gr__birdeye_com']";
			if(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)))){
				System.out.println("Data has been uploaded");
			}
			if (we == null){
//				throw new Exception("Page load fail");
			}
			else{
				flag = true;
			}

		} */
			else{
//				xpath = "//div[@id='nprogress']";
				System.out.println("Current page is React page");
				xpath = "//html[contains(@class,'nprogress-busy')] | //html[contains(@class,'nprogress-done')]";
				we = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
				xpath = "//html[contains(@class,'nprogress-done')]";
				we = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
					System.out.println("Data has been uploaded");
				if (we == null){
//					throw new Exception("Page load fail");
				}
				else{
					flag = true;
					System.out.println("Data has been uploaded for react");
				}
			}
		}
		catch(Exception e){
			ts.getTestReporting().addTestSteps(ts,method," Page laod has been failed after 100 sec wait xpath "+xpath, "WARNING");
		}
		return flag;
	}
	
	
//	public static boolean waitForJQueryProcessing(int timeOutInSeconds) {
//	    boolean jQcondition = false;
//	    try {
//	        new WebDriverWait(driver, timeOutInSeconds) {
//	        }.until(new ExpectedCondition<Boolean>() {
//
//	            @Override
//	            public Boolean apply(WebDriver driverObject) {
//	                return (Boolean) ((JavascriptExecutor) driverObject)
//	                        .executeScript("return jQuery.active == 0");
//	            }
//
//				@Override
//				public Boolean apply(WebDriver arg0) {
//					return null;
//				}
//	        });
//	        jQcondition = (Boolean) ((JavascriptExecutor) driver)
//	                .executeScript("return window.jQuery != undefined && jQuery.active === 0");
//	        return jQcondition;
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	    return jQcondition;
//	}
	
	
	/**
	 * Method will return the {@link WebElement}} of given path if path is valid 
	 * and present in the object repository 
	 * @author Ankit
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public  WebElement getWebElementWithOutConditon(TestSuites ts,String path,String...params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		WebElement we = null;
		try{
			String locatorBy ="";
			String value ="";
			String page = path.split("/")[0];
			String elementPath = path.split("/")[1];
			try{
				HashMap<String, String> map = CommonDataMaps.objectRepoMapValues.get(page).get(elementPath);
				for(Entry<String, String> entry: map.entrySet()) {
					locatorBy = entry.getKey();
					//	        System.out.println("Ket: "+locatorBy);
					value = entry.getValue();
					//	        System.out.println("value: "+value);
					//	        System.out.println(entry.getValue());
					try{
						if(!locatorBy.isEmpty() && !value.isEmpty()){
							//				By by = getByMethod(locatorBy);
							value = MessageFormat.format(value, (Object[]) params);
							value = value.trim();
							System.out.println("locator for : "+path+" is :"+value);
							By by = (By) getByMethod(locatorBy).invoke(null, value);
							//				ts.getDriver();.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
//							int expWait = CommonDataMaps.waitConfigValues.get("ExplixitWait");
//							WebDriverWait wait = new WebDriverWait(ts.getDriver();, expWait);
//							we = wait.until(ExpectedConditions.);
							ts.getDriver().manage().timeouts().implicitlyWait(CommonDataMaps.waitConfigValues.get("ExplixitWait"), TimeUnit.SECONDS);
							we  = ts.getDriver().findElement(by);
							ts.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
							//				ts.getDriver()manage().timeouts().implicitlyWait(DataHashMap.waitConfigValues.get("ImplicitWait"), TimeUnit.SECONDS);
							if(we != null){
								highLighter(ts,we);
								break;
							}
						}
						else{
							ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
							throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
						}
					}catch(Exception e){
						ts.getTestReporting().addTestSteps(ts,method,"Selenium Exception: "+e.getMessage(), "FAIL");
						System.out.println("Selenium Exception: "+e.getMessage());
						throw new Exception("Selenium Exception: "+e.getMessage());
					}
				}
			}catch(Exception e1){
//				e1.printStackTrace();
//				ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
				System.out.println("Given element path :"+path+" isn't present in object repository");
				throw new Exception(e1.getMessage());
			}
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
			ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository"+e.getMessage(), "FAIL");
			throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
		}
		return we;
	}

	
	
	/**
	 * Method will return the {@link WebElement}} of given path if path is valid 
	 * and present in the object repository 
	 * @author Ankit
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public  WebElement getWebElementWithOutConditon(TestSuites ts,String path,long time, String...params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		WebElement we = null;
		try{
			String locatorBy ="";
			String value ="";
			String page = path.split("/")[0];
			String elementPath = path.split("/")[1];
			try{
				HashMap<String, String> map = CommonDataMaps.objectRepoMapValues.get(page).get(elementPath);
				for(Entry<String, String> entry: map.entrySet()) {
					locatorBy = entry.getKey();
					//	        System.out.println("Ket: "+locatorBy);
					value = entry.getValue();
					//	        System.out.println("value: "+value);
					//	        System.out.println(entry.getValue());
					try{
						if(!locatorBy.isEmpty() && !value.isEmpty()){
							//				By by = getByMethod(locatorBy);
							value = MessageFormat.format(value, (Object[]) params);
							value = value.trim();
							System.out.println("locator for : "+path+" is :"+value);
							By by = (By) getByMethod(locatorBy).invoke(null, value);
							//				ts.getDriver()manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
//							int expWait = CommonDataMaps.waitConfigValues.get("ExplixitWait");
							WebDriverWait wait = new WebDriverWait(ts.getDriver(), time);
//							JavascriptExecutor js = (JavascriptExecutor) ts.getDriver();
//							wait.until((ExpectedCondition<Boolean>) wd ->
//					        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
//							wait.until((ExpectedCondition<Boolean>) wd ->
//					        ((JavascriptExecutor) wd).executeScript("return jQuery.active").toString().equals("0"));
//							waitForPageLoad(ts);
							we = wait.until(ExpectedConditions.presenceOfElementLocated(by));
//							we = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
							//				ts.getDriver()manage().timeouts().implicitlyWait(DataHashMap.waitConfigValues.get("ImplicitWait"), TimeUnit.SECONDS);
							if(we != null){
								highLighter(ts,we);
								break;
							}
						}
						else{
							ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
							throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
						}
					}catch(Exception e){
//						ts.getTestReporting().addTestSteps(ts,method,"Selenium Exception: "+e.getMessage(), "FAIL");
						System.out.println("Selenium Exception: "+e.getMessage());
//						throw new Exception("Selenium Exception: "+e.getMessage());
					}
				}
			}catch(Exception e1){
				e1.printStackTrace();
				ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
				System.out.println("Given element path :"+path+" isn't present in object repository");
				throw new Exception(e1.getMessage());
			}
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
			ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository"+e.getMessage(), "FAIL");
			throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
		}
		return we;
	}

	
	
	/**
	 * Method will return the {@link WebElement}} of given path if path is valid 
	 * and present in the object repository 
	 * @author Ankit
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public  WebElement getWebElement(TestSuites ts,String path, long time, String...params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		WebElement we = null;
		try{
			String locatorBy ="";
			String value ="";
			String page = path.split("/")[0];
			String elementPath = path.split("/")[1];
			try{
				HashMap<String, String> map = CommonDataMaps.objectRepoMapValues.get(page).get(elementPath);
				for(Entry<String, String> entry: map.entrySet()) {
					locatorBy = entry.getKey();
					//	        System.out.println("Ket: "+locatorBy);
					value = entry.getValue();
					//	        System.out.println("value: "+value);
					//	        System.out.println(entry.getValue());
					try{
						if(!locatorBy.isEmpty() && !value.isEmpty()){
							//				By by = getByMethod(locatorBy);
							value = MessageFormat.format(value, (Object[]) params);
							value = value.trim();
							System.out.println("locator for : "+path+" is :"+value);
							By by = (By) getByMethod(locatorBy).invoke(null, value);
							//				ts.getDriver()manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
//							int expWait = CommonDataMaps.waitConfigValues.get("ExplixitWait");
							WebDriverWait wait = new WebDriverWait(ts.getDriver(), time);
//							JavascriptExecutor js = (JavascriptExecutor) ts.getDriver();
							wait.until((ExpectedCondition<Boolean>) wd ->
					        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
//							wait.until((ExpectedCondition<Boolean>) wd ->
//					        ((JavascriptExecutor) wd).executeScript("return jQuery.active").toString().equals("0"));
//							waitForPageLoad(ts);
							waitForPageLoad4JavaScriptAndReact4Script(ts);
							we = wait.until(ExpectedConditions.presenceOfElementLocated(by));
							we = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
							//				ts.getDriver()manage().timeouts().implicitlyWait(DataHashMap.waitConfigValues.get("ImplicitWait"), TimeUnit.SECONDS);
							if(we != null){
								highLighter(ts,we);
								break;
							}
						}
						else{
							ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
							throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
						}
					}catch(Exception e){
//						ts.getTestReporting().addTestSteps(ts,method,"Selenium Exception: "+e.getMessage(), "FAIL");
						System.out.println("Selenium Exception: "+e.getMessage());
//						throw new Exception("Selenium Exception: "+e.getMessage());
					}
				}
			}catch(Exception e1){
				e1.printStackTrace();
				ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
				System.out.println("Given element path :"+path+" isn't present in object repository");
				throw new Exception(e1.getMessage());
			}
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
			ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository"+e.getMessage(), "FAIL");
			throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
		}
		return we;
	}
	
	
	
	public  WebElement getWebElementPresence(TestSuites ts,String path, long time, String...params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		WebElement we = null;
		try{
			String locatorBy ="";
			String value ="";
			String page = path.split("/")[0];
			String elementPath = path.split("/")[1];
			try{
				HashMap<String, String> map = CommonDataMaps.objectRepoMapValues.get(page).get(elementPath);
				for(Entry<String, String> entry: map.entrySet()) {
					locatorBy = entry.getKey();
					//	        System.out.println("Ket: "+locatorBy);
					value = entry.getValue();
					//	        System.out.println("value: "+value);
					//	        System.out.println(entry.getValue());
					try{
						if(!locatorBy.isEmpty() && !value.isEmpty()){
							//				By by = getByMethod(locatorBy);
							value = MessageFormat.format(value, (Object[]) params);
							value = value.trim();
							System.out.println("locator for : "+path+" is :"+value);
							By by = (By) getByMethod(locatorBy).invoke(null, value);
							//				ts.getDriver()manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
//							int expWait = CommonDataMaps.waitConfigValues.get("ExplixitWait");
							WebDriverWait wait = new WebDriverWait(ts.getDriver(), time);
//							JavascriptExecutor js = (JavascriptExecutor) ts.getDriver();
							wait.until((ExpectedCondition<Boolean>) wd ->
					        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
//							wait.until((ExpectedCondition<Boolean>) wd ->
//					        ((JavascriptExecutor) wd).executeScript("return jQuery.active").toString().equals("0"));
//							waitForPageLoad(ts);
							waitForPageLoad4JavaScriptAndReact4Script(ts);
							we = wait.until(ExpectedConditions.presenceOfElementLocated(by));
//							we = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
							//				ts.getDriver()manage().timeouts().implicitlyWait(DataHashMap.waitConfigValues.get("ImplicitWait"), TimeUnit.SECONDS);
							if(we != null){
								highLighter(ts,we);
								break;
							}
						}
						else{
							ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
							throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
						}
					}catch(Exception e){
//						ts.getTestReporting().addTestSteps(ts,method,"Selenium Exception: "+e.getMessage(), "FAIL");
						System.out.println("Selenium Exception: "+e.getMessage());
//						throw new Exception("Selenium Exception: "+e.getMessage());
					}
				}
			}catch(Exception e1){
				e1.printStackTrace();
				ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
				System.out.println("Given element path :"+path+" isn't present in object repository");
				throw new Exception(e1.getMessage());
			}
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
			ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository"+e.getMessage(), "FAIL");
			throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
		}
		return we;
	}

	
	/**
	 * Method will return the {@link WebElement}} of given path if path is valid 
	 * and present in the object repository 
	 * @author Ankit
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public  WebElement getWebElementVisiblity(TestSuites ts,String path,String...params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		WebElement we = null;
		try{
			String locatorBy ="";
			String value ="";
			String page = path.split("/")[0];
			String elementPath = path.split("/")[1];
			try{
				HashMap<String, String> map = CommonDataMaps.objectRepoMapValues.get(page).get(elementPath);
				for(Entry<String, String> entry: map.entrySet()) {
					locatorBy = entry.getKey();
					//	        System.out.println("Ket: "+locatorBy);
					value = entry.getValue();
					//	        System.out.println("value: "+value);
					//	        System.out.println(entry.getValue());
					try{
						if(!locatorBy.isEmpty() && !value.isEmpty()){
							//				By by = getByMethod(locatorBy);
							value = MessageFormat.format(value, (Object[]) params);
							value = value.trim();
							By by = (By) getByMethod(locatorBy).invoke(null, value);
							//				ts.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
							System.out.println("locator for : "+path+" is :"+value);
							int expWait = CommonDataMaps.waitConfigValues.get("ExplixitWait");
							WebDriverWait wait = new WebDriverWait(ts.getDriver(), expWait);
//							wait.until((ExpectedCondition<Boolean>) wd ->
//					        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
//							wait.until((ExpectedCondition<Boolean>) wd ->
//					        ((JavascriptExecutor) wd).executeScript("return jQuery.active").toString().equals("0"));
//							waitForPageLoad(ts);
							waitForPageLoad4JavaScriptAndReact4Script(ts);
//							we = wait.until(ExpectedConditions.presenceOfElementLocated(by));
							we = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
							//				ts.getDriver();.manage().timeouts().implicitlyWait(DataHashMap.waitConfigValues.get("ImplicitWait"), TimeUnit.SECONDS);
							if(we != null){
								highLighter(ts,we);
								break;
							}
						}
						else{
							ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
							throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
						}
					}catch(Exception e){
						ts.getTestReporting().addTestSteps(ts,method,"Selenium Exception: "+e.getMessage(), "WARNING");
						System.out.println("Selenium Exception: "+e.getMessage());
						throw new Exception("Selenium Exception:  "+e.getMessage());
					}
				}
			}catch(Exception e1){
//				e1.printStackTrace();
//				ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
				System.out.println("Given element path :"+path+" isn't present in object repository");
				throw new Exception(e1.getMessage());
			}
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
			ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository"+e.getMessage(), "FAIL");
			throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
		}
		return we;
	}

	
	
	/**
	 * Method will return the {@link WebElement}} of given path if path is valid 
	 * and present in the object repository 
	 * @author Ankit
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public  WebElement getWebElementclickAble(TestSuites ts,String path,String...params) throws Exception {
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		WebElement we = null;
		try{
			String locatorBy ="";
			String value ="";
			String page = path.split("/")[0];
			String elementPath = path.split("/")[1];
			try{
				HashMap<String, String> map = CommonDataMaps.objectRepoMapValues.get(page).get(elementPath);
				for(Entry<String, String> entry: map.entrySet()) {
					locatorBy = entry.getKey();
					//	        System.out.println("Ket: "+locatorBy);
					value = entry.getValue();
					//	        System.out.println("value: "+value);
					//	        System.out.println(entry.getValue());
					try{
						if(!locatorBy.isEmpty() && !value.isEmpty()){
							//				By by = getByMethod(locatorBy);
							value = MessageFormat.format(value, (Object[]) params);
							value = value.trim();
							By by = (By) getByMethod(locatorBy).invoke(null, value);
							//				ts.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
							System.out.println("locator for : "+path+" is :"+value);
							int expWait = CommonDataMaps.waitConfigValues.get("ExplixitWait");
							WebDriverWait wait = new WebDriverWait(ts.getDriver(), expWait);
							wait.until((ExpectedCondition<Boolean>) wd ->
					        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
//							wait.until((ExpectedCondition<Boolean>) wd ->
//					        ((JavascriptExecutor) wd).executeScript("return jQuery.active").equals("0"));
							waitForPageLoad(ts);
							waitForPageLoad4JavaScriptAndReact4Script(ts);
//							we = wait.until(ExpectedConditions.presenceOfElementLocated(by));
							we = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
							we = wait.until(ExpectedConditions.elementToBeClickable(by));
							List <WebElement> web =  wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, 0));
							//				ts.getDriver();.manage().timeouts().implicitlyWait(DataHashMap.waitConfigValues.get("ImplicitWait"), TimeUnit.SECONDS);
							if(we != null){
								highLighter(ts,we);
								break;
							}
						}
						else{
							ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
							throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
						}
					}catch(Exception e){
						ts.getTestReporting().addTestSteps(ts,method,"Selenium Exception: "+e.getMessage(), "WARNING");
						System.out.println("Selenium Exception: "+e.getMessage());
					}
				}
			}catch(Exception e1){
//				e1.printStackTrace();
//				ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
				System.out.println("Given element path :"+path+" isn't present in object repository");
				throw new Exception(e1.getMessage());
			}
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
			ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository"+e.getMessage(), "FAIL");
			throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
		}
		return we;
	}

	/**
	 * Method will return the {@link List} of {@link WebElement} of given path if path is valid 
	 * and present in the object repository 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public  List<WebElement> getWebElements(TestSuites ts,String path, String...parms) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		List<WebElement> we = new ArrayList<WebElement>();
		try{
			String locatorBy ="";
			String value ="";
			String page = path.split("/")[0];
			String elementPath = path.split("/")[1];
			try{
				HashMap<String, String> map = CommonDataMaps.objectRepoMapValues.get(page).get(elementPath);
				for(Entry<String, String> entry: map.entrySet()) {
					locatorBy = entry.getKey();
					//	        System.out.println("Ket: "+locatorBy);
					value = entry.getValue();
					//	        System.out.println("value: "+value);
					//	        System.out.println(entry.getValue());
					try{
						if(!locatorBy.isEmpty() && !value.isEmpty()){
							//			By by = getByMethod(locatorBy);
							value = MessageFormat.format(value, (Object[]) parms);
							value = value.trim();
							System.out.println("Data for : "+path+" is :"+value);
							By by = (By) getByMethod(locatorBy).invoke(null, value);
							WebDriverWait wait = new WebDriverWait(ts.getDriver(), CommonDataMaps.waitConfigValues.get("ExplixitWait"));
							we = (List<WebElement>) wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
							if (!we.isEmpty()){
								highLighter(ts,we);
								break;
							}
						}
						else{
							ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
							throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
						}
					}catch(Exception e){
						System.out.println("Selenium Exception: "+e.getMessage());
						System.out.println("Get selenium exception: "+e.getMessage());
						ts.getTestReporting().addTestSteps(ts,"Selenium Exception: ",e.getMessage(), "FAIL");
						throw new Exception("Selenium Exception: "+e.getMessage());
					}
				}
			}catch(Exception e1){
				e1.printStackTrace();
				ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
				System.out.println("Given element path :"+path+" isn't present in object repository");
				throw new Exception(e1.getMessage());
			}
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
			ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
			throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
		}
		return we;
	}
	
	
	
	/**
	 * Method will return the {@link List} of {@link WebElement} of given path if path is valid 
	 * and present in the object repository 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public  List<WebElement> getWebElementsWithoutConditions(TestSuites ts,String path, String...parms) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		List<WebElement> we = new ArrayList<WebElement>();
		try{
			String locatorBy ="";
			String value ="";
			String page = path.split("/")[0];
			String elementPath = path.split("/")[1];
			try{
				HashMap<String, String> map = CommonDataMaps.objectRepoMapValues.get(page).get(elementPath);
				for(Entry<String, String> entry: map.entrySet()) {
					locatorBy = entry.getKey();
					//	        System.out.println("Ket: "+locatorBy);
					value = entry.getValue();
					//	        System.out.println("value: "+value);
					//	        System.out.println(entry.getValue());
					try{
						if(!locatorBy.isEmpty() && !value.isEmpty()){
							//			By by = getByMethod(locatorBy);
							value = MessageFormat.format(value, (Object[]) parms);
							value = value.trim();
							System.out.println("Data for : "+path+" is :"+value);
							By by = (By) getByMethod(locatorBy).invoke(null, value);
							WebDriverWait wait = new WebDriverWait(ts.getDriver(), CommonDataMaps.waitConfigValues.get("ExplixitWait"));
//							we = (List<WebElement>) wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
							we =  ts.getDriver().findElements(by);
							if (!we.isEmpty()){
								highLighter(ts,we);
								break;
							}
						}
						else{
							ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
							throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
						}
					}catch(Exception e){
						System.out.println("Selenium Exception: "+e.getMessage());
						System.out.println("Get selenium exception: "+e.getMessage());
						ts.getTestReporting().addTestSteps(ts,"Selenium Exception: ",e.getMessage(), "FAIL");
						throw new Exception("Selenium Exception: "+e.getMessage());
					}
				}
			}catch(Exception e1){
				e1.printStackTrace();
				ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
				System.out.println("Given element path :"+path+" isn't present in object repository");
				throw new Exception(e1.getMessage());
			}
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
			ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+" is not a valid path or isn't in object repository", "FAIL");
			throw new Exception("Given path: "+path+" is not a valid path or isn't in object repository");
		}
		return we;
	}

	
	
	public  List<WebElement> getWebElements(TestSuites ts,String path,  long time,String...parms) throws Exception{
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		List<WebElement> we = new ArrayList<WebElement>();
		try{
			String locatorBy ="";
			String value ="";
			String page = path.split("/")[0];
			String elementPath = path.split("/")[1];
			try{
				HashMap<String, String> map = CommonDataMaps.objectRepoMapValues.get(page).get(elementPath);
				for(Entry<String, String> entry: map.entrySet()) {
					locatorBy = entry.getKey();
					//	        System.out.println("Ket: "+locatorBy);
					value = entry.getValue();
					//	        System.out.println("value: "+value);
					//	        System.out.println(entry.getValue());
					try{
						if(!locatorBy.isEmpty() && !value.isEmpty()){
							//			By by = getByMethod(locatorBy);
							value = MessageFormat.format(value, (Object[]) parms);
							value = value.trim();
							System.out.println("locator for : "+path+" is :"+value);
							By by = (By) getByMethod(locatorBy).invoke(null, value);
//							WebDriverWait wait = new WebDriverWait(getDriver(), time);
							ts.getDriver().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
							we = ts.getDriver().findElements(by);
//							we = (List<WebElement>) wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
					        ts.getDriver().manage().timeouts().implicitlyWait(CommonDataMaps.waitConfigValues.get("ImplicitWait"), TimeUnit.SECONDS);
							if (!we.isEmpty()){
								highLighter(ts,we);
								break;
							}
						}
						else{
							ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+"is not a valid path or isn't in object repository", "FAIL");
							throw new Exception("Given path: "+path+"is not a valid path or isn't in object repository");
						}
					}catch(Exception e){
						System.out.println("Selenium Exception: "+e.getMessage());
						System.out.println("Get selenium exception: "+e.getMessage());
						ts.getTestReporting().addTestSteps(ts,"Selenium Exception: ",e.getMessage(), "FAIL");
						throw new Exception("Selenium Exception: "+e.getMessage());
					}
				}
			}catch(Exception e1){
				e1.printStackTrace();
				ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+"is not a valid path or isn't in object repository", "FAIL");
				System.out.println("Given element path :"+path+" isn't present in object repository");
				throw new Exception(e1.getMessage());
			}
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
			ts.getTestReporting().addTestSteps(ts,method,"Given path: "+path+"is not a valid path or isn't in object repository", "FAIL");
			throw new Exception("Given path: "+path+"is not a valid path or isn't in object repository");
		}
		ts.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		return we;
	}
	
	
	public void highLighter(TestSuites ts,WebElement we) {
		try{
          JavascriptExecutor js = (JavascriptExecutor)ts.getDriver();
          String var =  (String) js.executeScript("return arguments[0].getAttribute('style', arguments[1]);", we);
          js.executeScript("return arguments[0].setAttribute('style', arguments[1]);", we,"border:4px solid red;");
          Thread.sleep(200);
          js.executeScript("return arguments[0].setAttribute('style', arguments[1]);", we,var);
		}catch(Exception e){
          System.out.println("unable to HighLight");
		}
	}
		
	public void highLighter(TestSuites ts,List<WebElement> we) {
		JavascriptExecutor js = (JavascriptExecutor)ts.getDriver();
		for(WebElement ele : we){
			try{
				String var =  (String) js.executeScript("return argument[0].getAttribute('style', arguments[1]);", ele);
				js.executeScript("return arguments[0].setAttributes('style' arguments[1]);", ele,"border:4px solid red;");
				Thread.sleep(200);
				js.executeScript("return arguments[0].setAttributes('style' arguments[1]);", ele,var);
			}catch(Exception e){
//				e.printStackTrace();
//				System.out.println("unable to HighLight");
			}
		}
	}


}
