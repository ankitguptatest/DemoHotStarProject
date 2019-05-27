package com.ankit.Suites;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ankit.DataBaseUtility.DataProviderClass;
import com.ankit.DataBaseUtility.DataProviderClass.TestData;
import com.ankit.DataMaps.CommonDataMaps;
import com.ankit.JavaUtility.JavaWrappers;
import com.ankit.Selenium.SeleniumDriver;
import com.ankit.Selenium.TestSuites;
import com.ankit.WebPages.HotStarHomePage;
import com.ankit.reporting.SuiteReporting;
import com.ankit.reporting.TestReporting;

public class HotStarSuite extends TestSuites{
	
	
	
	HotStarHomePage hs = null;
	List<String> tray_beforeLogin = new ArrayList<>();
	List<String> tray_afterLogin = new ArrayList<>();
	
	 public HotStarSuite() {
		hs = new HotStarHomePage();
	}
	 
	 HotStarSuite ts = null;
		
	@BeforeClass(alwaysRun=true)
	@Parameters({"BrowserName"})
	public  void initilizePages(String browserName) throws IOException, InterruptedException{
//		 Thread.sleep(CommonDataMaps.waitConfigValues.get("second_30"));
			if (InitilizeClass.jenkinBrowserName != null && !InitilizeClass.jenkinBrowserName.isEmpty())
				browserName = InitilizeClass.jenkinBrowserName;
		ts = new HotStarSuite();
		this.ts.setBrowserConsoleLogs(InitilizeClass.browserConsoleLogs);
		String className = this.getClass().getName();
		System.out.println("className in BeofreClass: "+className);
		String[] name = className.split("\\.");
		className = name[name.length-1];
		SuiteReporting suitReport = new SuiteReporting();
		this.ts.setSuiteReportingObject(suitReport);
		this.ts.getSuiteReportingObject().createSuiteFileHeader(this.ts,className,browserName);
        SeleniumDriver sd = new SeleniumDriver();
        this.ts.setSeleniumDriver(sd);
        System.out.println("Opening the new Browser");
        WebDriver driver = this.ts.getSeleniumDriver().startDriver(browserName, CommonDataMaps.masterConfigValues.get("url"));
        this.ts.setDriver(driver);
        String startTime = JavaWrappers.getCurrentTime("HH:mm:ss");
        this.ts.setSuiteStartTime(startTime);
        long mStartTime = System.currentTimeMillis();
        this.ts.setSuiteTimeInMiliSecond(mStartTime);
	}
	
	@BeforeMethod(alwaysRun=true)
	@Parameters({"BrowserName"})
	public  void StartTest(String browserName, Method m) throws Exception{
		String testName = m.getName();
		System.out.println("--------"+testName+"-------------");
		TestReporting testReport = new TestReporting();
		this.ts.setTestReporting(testReport);
		String testFile  =  this.ts.getTestReporting().initilizeTestReport(this.ts,testName);
		this.ts.setTestCaseFileName(testFile);
		this.ts.mtestStartTime = System.currentTimeMillis();
	}
	
	
	

	@Test(dataProvider="getTestData", dataProviderClass=DataProviderClass.class, groups = { "sanity", "regression" })
	@TestData("HotStarTest:hotStarHomePage=TotalTray")
	public void hotStarHomePage(String totalTray) throws Exception{
		tray_beforeLogin = hs.getTray(ts, Integer.parseInt(totalTray));
	}
	
	@Test(dataProvider="getTestData", dataProviderClass=DataProviderClass.class, groups = { "sanity", "regression" })
	@TestData("HotStarTest:hotStar_Login=MobileNumber,TotalTray")
	public void hotStar_Login(String mobileNumber, String totalTray) throws Exception{
		hs.login(ts, mobileNumber);
		tray_afterLogin = hs.getTray(ts, Integer.parseInt(totalTray));
		hs.getDiffOfTrays(ts, tray_beforeLogin, tray_afterLogin);
	}
	
	
	
	@AfterMethod(alwaysRun=true)
	public void TearDownTest(ITestResult result) throws Exception{
		 this.ts.mtestCompleteTime =0;
		 this.ts.mtestCompleteTime = System.currentTimeMillis();
		String time = JavaWrappers.getTime( this.ts.mtestStartTime,  this.ts.mtestCompleteTime);
		String status = "";
		if(result.getStatus() == ITestResult.FAILURE){
			status = "FAIL";
				System.out.println("test:result.getMethod().getMethodName() "+result.getMethod().getMethodName()+" case has been failed");
				System.out.println("test:result.getInstanceName() "+result.getInstanceName()+" case has been failed");
				this.ts.getTestReporting().addTestSteps(this.ts,"Completed Test", "<b>Total time taken: "+time+"</b>",status, true );
				this.ts.getSuiteReportingObject().addTestToSuiteFile("FAIL", result.getMethod().getMethodName(), this.ts.getTestCaseFileName(), time);
		}
		else if(result.getStatus() == ITestResult.SUCCESS){
			status = "PASS";
			System.out.println("test:result.getName() "+result.getName()+" case has been competed successfully");
			System.out.println("test:result.getMethod().getMethodName() "+result.getMethod().getMethodName()+" case has been competed successfully");
			System.out.println("test:result.getInstanceName() "+result.getInstanceName()+" case has been competed successfully");
			this.ts.getTestReporting().addTestSteps(this.ts,"Completed Test", "<b>Total time taken: "+time+"</b>",status, true );
			if (!result.getMethod().getMethodName().equalsIgnoreCase("valid_Login"))
			this.ts.getSuiteReportingObject().addTestToSuiteFile("PASS", result.getMethod().getMethodName(), this.ts.getTestCaseFileName(), time);
		}
		else if(result.getStatus() == ITestResult.SKIP){
			status = "FAIL";
				System.out.println("test: "+result.getMethod().getMethodName()+" case skipped");
				this.ts.getTestReporting().addTestSteps(this.ts,"Completed Test", "<b>Total time taken: "+time+"</b>",status, false );
				this.ts.getSuiteReportingObject().addTestToSuiteFile("FAIL", result.getMethod().getMethodName(), this.ts.getTestCaseFileName(), time);
				
		}
		this.ts.getTestReporting().addConsoleLogsToFile(this.ts);
		this.ts.mtestStartTime =0;
	}

	
	@AfterClass(alwaysRun=true)
	public  void finshTestSuites1(){
		this.ts.getSuiteReportingObject().completeSuiteFileFooter(this.ts, InitilizeClass.concludeReport);
		this.ts.getDriver().quit();
		System.out.println("Closed Browser");
	}
	
	

}
