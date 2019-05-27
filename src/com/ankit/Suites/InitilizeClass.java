package com.ankit.Suites;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ankit.DataMaps.CommonDataMaps;
import com.ankit.JavaUtility.JavaWrappers;
import com.ankit.JavaUtility.MachineSearch;
import com.ankit.propertiesFileUtility.PropertiseFileUtility;
import com.ankit.reporting.ConcludeReport;
import com.my.objectRepository.ReadObjectRepository;


public class InitilizeClass {

	
//	SuiteReporting suitReport = new SuiteReporting();
//	SeleniumDriver sd = new SeleniumDriver();
	ReadObjectRepository  xmlRepoObj =  new ReadObjectRepository();
	PropertiseFileUtility prop = new PropertiseFileUtility();
	public static ConcludeReport concludeReport = new ConcludeReport();
    String concludeFile =""; 
	String projDir = "";
	static String  url =System.getProperty("url");
	static String browserConsoleLogs = System.getProperty("BrowserConsoleLogs");
	static String databaseName = System.getProperty("DataBaseFileName");
	static String jenkinBrowserName = System.getProperty("BrowserName");
	static String ScreenShot = System.getProperty("ScreenShot");
	
	
	
	
	
	@BeforeSuite
	public void loadObjectRepo(ITestContext itx) throws IOException{
		String suiteName = itx.getCurrentXmlTest().getSuite().getName();
		String to=System.getProperty("to");
		if (to != null && !to.isEmpty()) {
			JavaWrappers.closeAllBrowsers();
		}
		projDir = System.getProperty("user.dir")+File.separator+"src";
		xmlRepoObj.getObjectRepository(projDir, "LocatorsRepository.xml");
//		projDir = System.getProperty("user.dir")+File.separator+"src";
		CommonDataMaps.masterConfigValues = (HashMap<String, String>) prop.getConfig(projDir,"TestSuiteProperties.properties");
        if(url != null){
        	CommonDataMaps.masterConfigValues.put("url", url);
        }
        System.out.println("Parametrize URL: "+url+" and config url is:"+CommonDataMaps.masterConfigValues.get("url") );
		concludeFile = concludeReport.initilizeConcludeReport(suiteName,"SeleniumReport");
		CommonDataMaps.waitConfigValues = prop.loadWaitConfig();
//		System.out.println("Parametrize database: "+databaseName+" and config database name is:"+CommonDataMaps.masterConfigValues.get("DataBaseFileName") );
		String dataBaseUrl ="";
        if(databaseName != null){
        	CommonDataMaps.masterConfigValues.put("DataBaseFileName", databaseName);
        }
        if(ScreenShot != null){
        	CommonDataMaps.masterConfigValues.put("ScreenShot", ScreenShot);
        }
        System.out.println("Parametrize database: "+dataBaseUrl+" and config database name is:"+CommonDataMaps.masterConfigValues.get("DataBaseFileName") );
//		if (CommonDataMaps.masterConfigValues.get("url").contains("demo")){
//			dataBaseUrl = new MachineSearch().serachMachineForFile(System.getProperty("user.dir")+File.separator+"TestData", CommonDataMaps.masterConfigValues.get("DataBaseFileNameDemo"));	
//		}
//		else{
			dataBaseUrl = new MachineSearch().serachMachineForFile(System.getProperty("user.dir")+File.separator+"TestData", CommonDataMaps.masterConfigValues.get("DataBaseFileName"));
//		}
		
		CommonDataMaps.masterConfigValues.put("DataBaseUrl", dataBaseUrl);
		System.out.println("Data base path: "+CommonDataMaps.masterConfigValues.get("DataBaseUrl"));
//		JavaWrappers.setMailConfig();
//		JavaWrappers.deleteDownloadedFiles(".xls");
//		JavaWrappers.deleteDownloadedFiles(".pdf");
//		this.suitReport.initilizeSuiteReport();
	}
	
//	@BeforeTest
//	public void loadTestSuites() throws IOException{
//		String dataBaseUrl ="";
//		if (CommonDataMaps.masterConfigValues.get("url").contains("demo")){
//			dataBaseUrl = new MachineSearch().serachMachineForFile(System.getProperty("user.dir")+File.separator+"TestData", CommonDataMaps.masterConfigValues.get("DataBaseFileNameDemo"));	
//		}
//		else{
//			dataBaseUrl = new MachineSearch().serachMachineForFile(System.getProperty("user.dir")+File.separator+"TestData", CommonDataMaps.masterConfigValues.get("DataBaseFileNameProd"));
//		}
//		
//		CommonDataMaps.masterConfigValues.put("DataBaseUrl", dataBaseUrl);
//		System.out.println("Data base path: "+CommonDataMaps.masterConfigValues.get("DataBaseUrl"));
////		this.suitReport.initilizeSuiteReport();
//		
//	}
	
	
	
	
	
	


	
	
	// need to be checked,  
//    @AfterClass(alwaysRun=true)
//    public void finshTestSuites() {
//        List<JavaScriptError> jsErrors = JavaScriptError.readErrors(sd.getDriver());
//        System.out.println("###start displaying errors");
//        for(int i = 0; i < jsErrors.size(); i++)
//        {
//            System.out.println(jsErrors.get(i).getErrorMessage());
//            System.out.println(jsErrors.get(i).getLineNumber());
//            System.out.println(jsErrors.get(i).getSourceName());
//        }
//        System.out.println("###start displaying errors");
////        sd.getDriver().close(); 
////        sd.getDriver().quit();
//    }
	
	
	
//	@AfterTest
//	public void finishTests(){
//		concludeReport.completeConcludeFileFooter();
//	}
	
	
	@AfterSuite(alwaysRun=true)
	public void FinishTestSuite(ITestContext itx) throws IOException{
		concludeReport.completeConcludeFileFooter();
//		String to=System.getProperty("to");
//		String cc=System.getProperty("cc");
//		System.out.println("to: "+to+" cc:"+cc);
//		if (to != null && !to.isEmpty()){
//			WebMailReport ml = null;
//			try {
//			ml = new WebMailReport();
//			}
//			catch(Exception e) {
//				e.printStackTrace();
//				ml = new WebMailReport();	
//			}
//			ml.sendMail(to, cc, concludeFile, itx.getCurrentXmlTest().getSuite().getName()+" Report of Web Automation ");
//		}
		System.exit(0);
	}
}
