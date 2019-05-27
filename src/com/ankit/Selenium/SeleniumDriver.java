package com.ankit.Selenium;

import java.awt.Toolkit;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.session.EdgeFilter;

import com.ankit.DataMaps.CommonDataMaps;
import com.ankit.JavaUtility.MachineSearch;
import com.ankit.JavaUtility.OSValidator;






public class SeleniumDriver{



	public static void main(String[] args) throws InterruptedException {
		new SeleniumDriver().startDriver("chrome", "https://birdeye.com/sign-in/");
	}

	//	public SeleniumDriver(){
	//		CommonDataMaps.waitConfigValues = loadWaitConfig();
	//	}

	public WebDriver driver =  null;
	//	public static ThreadLocal<WebDriver> threadDriver = null;
	//	public TestReporting tr = null;
	//    private BrowserMobProxy proxy = null;

	//	public void setProxyObject(BrowserMobProxy pr) {
	//		proxy = pr;
	//	}

	//	public BrowserMobProxy getProxyObject(SeleniumDriver sd) {
	//		return sd.proxy;
	//	}
	
	
	public void waitBrowser() throws InterruptedException {
     
		String userName = System.getProperty("user.name");
		if(userName.equalsIgnoreCase("Ankit") || userName.contains("ankit")) {
			return;
		}
		else {
			Thread.sleep(CommonDataMaps.waitConfigValues.get("second_20"));	
		}
	}

	public synchronized  WebDriver startDriver(String name, String url) throws InterruptedException{
		waitBrowser();
		//		threadDriver = new ThreadLocal<WebDriver>();
		System.out.println("Opening browser: "+name);
		String platform = System.getProperty("os.name");
		File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "Drivers"+ System.getProperty("file.separator") + "SeleniumDriver");
		//		String browser_driver_path = "C:\\Eclipse_WorkSpace\\Parallel_BirdEye_WebAutomation\\automation\\Parallel_WebAutomation\\BirdEye_TestFrameWork\\BirdEye_TestFrameWork\\Drivers\\SeleniumDriver\\";
		String browser_driver_path = "";
		if (file.exists()){
			browser_driver_path = System.getProperty("user.dir") + System.getProperty("file.separator") + "Drivers"+System.getProperty("file.separator") + "SeleniumDriver";
		}
		else{
			System.out.println("Driver path does not exist");
			System.exit(0);
		}
		try{
			if (name.equalsIgnoreCase("firefox") || name.contains("mozila") || name.contains("ff")){
				//		driver = new FirefoxDriver();
				if (platform.startsWith("Windows"))
					browser_driver_path = new MachineSearch().serachMachineForFile(browser_driver_path,"geckodriver.exe");
				if (platform.startsWith("Mac"))
					browser_driver_path = new MachineSearch().serachMachineForFile(browser_driver_path,"geckodriver");
				System.setProperty("webdriver.gecko.driver", browser_driver_path);
				System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
				//				System.setProperty("webdriver.firefox.marionette", browser_driver_path);
				//				cap = new DesiredCapabilities().firefox();
				//				cap.setCapability("marionette", true);
				FirefoxOptions option = new FirefoxOptions();
				option.addArguments("--enable-automation");
				option.addArguments("--disable-infobars");
				FirefoxProfile pf = new FirefoxProfile();
				pf.setPreference("app.update.enabled", false);
				
				// to download file into the recent folder without popup
				pf.setPreference("browser.download.folderList", 1);
				pf.setPreference("browser.helperApps.alwaysAsk.force", false);
//				pf.setPreference("browser.download.dir",System.getProperty("user.home"));
				pf.setPreference("browser.helperApps.neverAsk.saveToDisk","application/excel,application/x-msexcel,application/vnd.ms-excel,application/csv,application/x-csv");
				//				cap.setCapability("marionette", false);
				option.setProfile(pf);
//				ProfilesIni prof = new ProfilesIni();			
//				FirefoxProfile ffProfile= prof.getProfile("CertificateIssue");
				//				ffProfile.setAcceptUntrustedCertificates(true);
				//				ffProfile.setAssumeUntrustedCertificateIssuer(false);
//				option.setCapability(FirefoxDriver.PROFILE, ffProfile);
				driver = new FirefoxDriver(option);
			}
			else if (name.equalsIgnoreCase("chrome") || name.equalsIgnoreCase("ch")){
				ChromeOptions option = new ChromeOptions();
				if (platform.startsWith("Windows"))
					browser_driver_path = new MachineSearch().serachMachineForFile(browser_driver_path,"chromedriver.exe");
				if (platform.startsWith("Mac"))
					browser_driver_path = new MachineSearch().serachMachineForFile(browser_driver_path,"chromedriver");
				System.setProperty("webdriver.chrome.driver",browser_driver_path);
				//				cap = new DesiredCapabilities().chrome();
				//				cap.setCapability("applicationCacheEnabled", "false");

				// below code is to capture the logs
				LoggingPreferences preferences = new LoggingPreferences();
				preferences.enable(LogType.PERFORMANCE, Level.ALL);
				

				//				BrowserMobProxy proxy = new BrowserMobProxyServer();
				//				proxy.setTrustAllServers(true);
				//				proxy.start(0);
				//				 int port = proxy.getPort(); // get the JVM-assigned port
				// get the selenium proxy object
				//				Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
				//				seleniumProxy.setSslProxy("trustAllSSLCertificates");
				//				seleniumProxy.setHttpProxy("localhost:8080");

				//				capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				//				capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));				

				//				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				//				ChromeOptions options = new ChromeOptions();
				//				options.addArguments("test-type", "start-maximized","no-default-browser-check");
				option.addArguments("--start-maximized");
				option.addArguments("disable-infobars");
				option.setPageLoadStrategy(PageLoadStrategy.NONE);
				
				/*
				 * option.add_argument('--headless')
				 * option.add_argument('--no-sandbox')
				 * option.add_argument('--disable-dev-shm-usage')
				 * 
				 *   /
				
//				
 */
//								option.addArguments("--window-size=1500,1000");
//								option.addArguments("--disable-gpu");
				//				option.addArguments("--screenshot");
				option.addArguments("--enable-automation");
//				option.addArguments("--incognito");      To open browser in incognito mode
//								cap.setCapability("takesScreenshot", true);
				//				option.addArguments("--test-type");

				//				option.addArguments("--disable-application-cache");
				//				option.addArguments("--disk-cache-size=1");
				//				option.addArguments("--media-cache-size=1"); 
				//				option.addArguments("--audio-buffer-size=1");
				//				options.addArguments("--disable-extensions");
				//				options.addArguments("--disable-popup-blocking");
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				
				//				prefs.put("browser.cache.disk.enable", false);
				//				prefs.put("browser.cache.memory.enable", false);
				//				prefs.put("browser.cache.offline.enable", false);
				//				prefs.put("network.http.use-cache", false);
				//				prefs.put("app.update.auto", false);
				option.setExperimentalOption("prefs", prefs);
				
				option.setCapability(CapabilityType.LOGGING_PREFS, preferences);
				option.setCapability(ChromeOptions.CAPABILITY, option);
				option.addArguments("--always-authorize-plugins");
				//				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, seleniumProxy);
				//				cap.setCapability(CapabilityType.PROXY, seleniumProxy);
				// enable more detailed HAR capture, if desired (see CaptureType for the complete list)
				//			    proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
				//			    setProxyObject(proxy);
				driver = new ChromeDriver(option);
//				int h = driver.manage().window().getSize().getHeight();
//				int w =  driver.manage().window().getSize().getWidth();
//				System.out.println("h ="+h+" w= "+w);
			}
			else if (name.equalsIgnoreCase("headless") || name.equalsIgnoreCase("chromeheadless")){
				ChromeOptions option = new ChromeOptions();
				if (platform.startsWith("Windows"))
					browser_driver_path = new MachineSearch().serachMachineForFile(browser_driver_path,"chromedriver.exe");
				if (platform.startsWith("Mac"))
					browser_driver_path = new MachineSearch().serachMachineForFile(browser_driver_path,"chromedriver");
				System.setProperty("webdriver.chrome.driver",browser_driver_path);
				// below code is to capture the logs
				
				LoggingPreferences preferences = new LoggingPreferences();
				preferences.enable(LogType.PERFORMANCE, Level.ALL);
				//				option.setBinary("/usr/bin/chromium-browser");
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				option.addArguments("--start-maximized");
				option.addArguments("disable-infobars");
				option.addArguments("--headless");
				option.addArguments("--window-size=1500,800");
				option.addArguments("--disable-gpu");
                option.addArguments("--enable-automation");
				option.setExperimentalOption("prefs", prefs);
				option.setCapability(CapabilityType.LOGGING_PREFS, preferences);
				option.setCapability(ChromeOptions.CAPABILITY, option);
				option.addArguments("--always-authorize-plugins");
				driver = new ChromeDriver(option);
				int h = driver.manage().window().getSize().getHeight();
				int w =  driver.manage().window().getSize().getWidth();
				System.out.println("h ="+h+" w= "+w);
			}
			else if (name.equalsIgnoreCase("chromeCookie")){
				ChromeOptions option = new ChromeOptions();
				if (platform.startsWith("Windows")){
					browser_driver_path = new MachineSearch().serachMachineForFile(browser_driver_path,"chromedriver.exe");
					System.setProperty("webdriver.chrome.driver", browser_driver_path);
					option.addArguments(System.getProperty("user.name")+"/AppData/Local/Google/Chrome/User Data/Default");
				}
				if (platform.startsWith("Mac")){
					browser_driver_path = new MachineSearch().serachMachineForFile(browser_driver_path,"chromedriver");
					System.setProperty("webdriver.chrome.driver", browser_driver_path);
					option.addArguments(System.getProperty("user.name")+"/Library/Application Support/Google/Chrome/Default");
				}
				if (OSValidator.isUnix()){
					System.setProperty("webdriver.chrome.driver", browser_driver_path + "chromedriver");
					option.addArguments(System.getProperty("user.name")+"/Library/Application Support/Google/Chrome/Default");
				}
				//				Map<String, Object> prefs = new HashMap<String, Object>();
				//				prefs.put("credentials_enable_service", false);
				//				prefs.put("profile.password_manager_enabled", false);
				//				option.setExperimentalOption("prefs", prefs);
				//								option.addArguments("--start-maximized");
				//				threadDriver.set(new ChromeDriver(option));

				//				cap = new DesiredCapabilities().chrome();
				//				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				//ChromeOptions options = new ChromeOptions();
				//								option.addArguments("test-type", "start-maximized","no-default-browser-check");    
				//				cap.setCapability(ChromeOptions.CAPABILITY, option);
				option.addArguments("--test-type");
				option.addArguments("--enable-automation"); 
				option.addArguments("--start-maximized");
				//options.addArguments("--disable-application-cache");
				//				option.addArguments("--disable-extensions");
				//options.addArguments("--disable-popup-blocking");
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				option.setExperimentalOption("prefs", prefs);
				//				driver = new ChromeDriver(cap);
			}
			else if (name.equalsIgnoreCase("edge")){
				browser_driver_path = new MachineSearch().serachMachineForFile(browser_driver_path,"MicrosoftWebDriver.exe");
				System.setProperty("webdriver.edge.driver", browser_driver_path);
				//		driver =  new InternetExplorerDriver();
				//				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				//				cap.setCapability("ie.enableFullPageScreenshot", false);
				//								cap.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
				//								cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				//								cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				//								cap.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, UnexpectedAlertBehaviour.ACCEPT);
				EdgeOptions option = new EdgeOptions();
				option.setCapability("ie.enableFullPageScreenshot", false);
				option.setCapability("disable-save-password-bubble", true);
				driver = new EdgeDriver(option);
			}
			else if (name.equalsIgnoreCase("internet") || name.contains("explorer") || name.contains("ie") || name.equalsIgnoreCase("IE")){
				browser_driver_path = new MachineSearch().serachMachineForFile(browser_driver_path,"IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", browser_driver_path);
				//		driver =  new InternetExplorerDriver();
				//				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				//				cap.setCapability("ie.enableFullPageScreenshot", false);
				//								cap.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
				//								cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				//								cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				//								cap.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, UnexpectedAlertBehaviour.ACCEPT);
                InternetExplorerOptions option = new InternetExplorerOptions();
              option.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                option.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                option.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
              option.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, true);
                option.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
                option.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
                option.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, "accept");
                option.setCapability("disable-popup-blocking", true);
				option.setCapability("ie.enableFullPageScreenshot", true);
				option.setCapability("enablePersistentHover", true);
				option.setCapability("ignoreZoomSetting", true);
				option.setCapability("ignoreProtectedModeSettings", false);
				option.setCapability("forceCreateProcessAp", true);
//				option.setCapability("disable-save-password-bubble", true);
				driver = new InternetExplorerDriver(option);
			}
			//			else if (name.equalsIgnoreCase("HTML")){
			//				cap = new DesiredCapabilities().htmlUnit();
			//				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			//				cap.setJavascriptEnabled(true);
			//				cap.setCapability("takesScreenshot", false);
			//				driver = new HtmlUnitDriver();
			//			}
			else if (name.equalsIgnoreCase("safari")) {
				driver = new org.openqa.selenium.safari.SafariDriver();
			}
			else if (name.equalsIgnoreCase("Phantom")){
				if (platform.startsWith("Windows"))
					browser_driver_path = new MachineSearch().serachMachineForFile(browser_driver_path,"phantomjs.exe");
				System.setProperty("phantomjs.binary.path",browser_driver_path);
				//				cap = new DesiredCapabilities().phantomjs();
				//				cap.setJavascriptEnabled(true); // not really needed: JS enabled by default
				//				cap.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, browser_driver_path);
				//				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				//				cap.setCapability("takesScreenshot", true);
				//				driver = new PhantomJSDriver(cap);
			}
			//			else if (name.equalsIgnoreCase("PH")){
			//				System.setProperty("phantomjs.binary.path", "D:\\Eclipse Code\\Drivers\\phantomjs-2.0.0-windows\\bin\\phantomjs.exe");
			//				threadDriver.set(new PhantomJSDriver());
			//			}
			else{
				System.out.println("NO browser matched from the above condition: "+name);	
			}

		} catch (Exception e){

			System.err.println("Unable to open "+name+": browser");
			System.err.println("Error while opening "+name+" because: "+e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		maximizeScreen(driver);
		closeIfOpenMoreThnOne(driver);

		try{
			//		driver.navigate().to(url);
			driver.get(url);
			Thread.sleep(CommonDataMaps.waitConfigValues.get("second_2"));
		}catch(Exception e){
			e.printStackTrace();
			driver.navigate().to(url);
		}
		//	System.out.println("usr has been opened");

		//		driver.manage().deleteAllCookies();
		//		driver.manage().timeouts().pageLoadTimeout(CommonDataMaps.waitConfigValues.get("PageTimeOut"), TimeUnit.SECONDS);
		//		driver.manage().timeouts().setScriptTimeout(150, TimeUnit.SECONDS);
		//		driver.manage().timeouts().implicitlyWait(DataHashMap.waitConfigValues.get("ImplicitWait"), TimeUnit.SECONDS);	
		//		System.out.println("done");
		return driver;
	}
	

	//	/**
	//	 * get the webDriver
	//	 * @return
	//	 */
	//	public WebDriver getDriver() {
	//		WebDriver driver = threadDriver.get();
	//		return driver;
	//	}


	//	public void setTestReporting(TestReporting t){
	//		tr = t;
	//	}
	//
	//	public TestReporting getTestReporting(){
	//		return tr;
	//	}

	/**
	 * This method will maximise the browser if not maximise 
	 * @author Ankit
	 * @param driver
	 */
	public void maximizeScreen(WebDriver driver) {
		try{
			driver.manage().window().maximize(); 
//			driver.manage().window().fullscreen();
		}catch(Exception e){
			try{
				java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Point position = new Point(0, 0);
				driver.manage().window().setPosition(position);
				Dimension maximizedScreenSize =
						new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
				driver.manage().window().setSize(maximizedScreenSize);
//				Dimension maximizedScreenSize = new Dimension(1500, 900);
//				driver.manage().window().setSize(maximizedScreenSize);
			}catch(Exception e1){

			}
		}
	}


	public void closeIfOpenMoreThnOne(WebDriver driver){
		String parentHandle = driver.getWindowHandle();
		Set<String> win  = driver.getWindowHandles();
		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		Iterator<String> it =  win.iterator();
		if(win.size() > 1){
			while(it.hasNext()){
				String handle = it.next();
				if (!handle.equalsIgnoreCase(parentHandle)){
					driver.switchTo().window(handle);
					driver.close();
				}
			}
			driver.switchTo().window(parentHandle);
		}

	}
	
 
	public  WebDriver startChromeDriver() throws InterruptedException{
//		waitBrowser();
		//		threadDriver = new ThreadLocal<WebDriver>();
//		System.out.println("Opening browser: "+name);
		String platform = System.getProperty("os.name");
		File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "Drivers"+ System.getProperty("file.separator") + "SeleniumDriver");
		//		String browser_driver_path = "C:\\Eclipse_WorkSpace\\Parallel_BirdEye_WebAutomation\\automation\\Parallel_WebAutomation\\BirdEye_TestFrameWork\\BirdEye_TestFrameWork\\Drivers\\SeleniumDriver\\";
		String browser_driver_path = "";
		if (file.exists()){
			browser_driver_path = System.getProperty("user.dir") + System.getProperty("file.separator") + "Drivers"+System.getProperty("file.separator") + "SeleniumDriver";
		}
		else{
			System.out.println("Driver path does not exist");
			System.exit(0);
		}
		try{
				ChromeOptions option = new ChromeOptions();
				if (platform.startsWith("Windows"))
					browser_driver_path = new MachineSearch().serachMachineForFile(browser_driver_path,"chromedriver.exe");
				if (platform.startsWith("Mac"))
					browser_driver_path = new MachineSearch().serachMachineForFile(browser_driver_path,"chromedriver");
				System.setProperty("webdriver.chrome.driver",browser_driver_path);
				//				cap = new DesiredCapabilities().chrome();
				//				cap.setCapability("applicationCacheEnabled", "false");

				// below code is to capture the logs
				LoggingPreferences preferences = new LoggingPreferences();
				preferences.enable(LogType.PERFORMANCE, Level.ALL);

				//				BrowserMobProxy proxy = new BrowserMobProxyServer();
				//				proxy.setTrustAllServers(true);
				//				proxy.start(0);
				//				 int port = proxy.getPort(); // get the JVM-assigned port
				// get the selenium proxy object
				//				Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
				//				seleniumProxy.setSslProxy("trustAllSSLCertificates");
				//				seleniumProxy.setHttpProxy("localhost:8080");

				//				capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				//				capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));				

				//				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				//				ChromeOptions options = new ChromeOptions();
				//				options.addArguments("test-type", "start-maximized","no-default-browser-check");
				option.addArguments("--start-maximized");
				option.addArguments("disable-infobars");
//								option.addArguments("--headless");
//								option.addArguments("--window-size=1500,1000");
								option.addArguments("--disable-gpu");  // disable process if still run
				//				option.addArguments("--screenshot");
//				option.addArguments("--enable-automation");
//								cap.setCapability("takesScreenshot", true);
				//				option.addArguments("--test-type");

				//				option.addArguments("--disable-application-cache");
				//				option.addArguments("--disk-cache-size=1");
				//				option.addArguments("--media-cache-size=1"); 
				//				option.addArguments("--audio-buffer-size=1");
				//				options.addArguments("--disable-extensions");
				//				options.addArguments("--disable-popup-blocking");
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				
				//				prefs.put("browser.cache.disk.enable", false);
				//				prefs.put("browser.cache.memory.enable", false);
				//				prefs.put("browser.cache.offline.enable", false);
				//				prefs.put("network.http.use-cache", false);
				//				prefs.put("app.update.auto", false);
				option.setExperimentalOption("prefs", prefs);
				option.setCapability(CapabilityType.LOGGING_PREFS, preferences);
				option.setCapability(ChromeOptions.CAPABILITY, option);
				option.addArguments("--always-authorize-plugins");
				//				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, seleniumProxy);
				//				cap.setCapability(CapabilityType.PROXY, seleniumProxy);
				// enable more detailed HAR capture, if desired (see CaptureType for the complete list)
				//			    proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
				//			    setProxyObject(proxy);
				driver = new ChromeDriver(option);
//				int h = driver.manage().window().getSize().getHeight();
//				int w =  driver.manage().window().getSize().getWidth();
//				System.out.println("h ="+h+" w= "+w);
		} catch (Exception e){

			System.err.println("Unable to open : browser");
			System.err.println("Error while opening browser because: "+e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		maximizeScreen(driver);
//		closeIfOpenMoreThnOne(driver);

//		try{
//			//		driver.navigate().to(url);
////			driver.get(url);
////			Thread.sleep(CommonDataMaps.waitConfigValues.get("second_2"));
//		}catch(Exception e){
//			e.printStackTrace();
////			driver.navigate().to(url);
//		}
		//	System.out.println("usr has been opened");

		//		driver.manage().deleteAllCookies();
		//		driver.manage().timeouts().pageLoadTimeout(CommonDataMaps.waitConfigValues.get("PageTimeOut"), TimeUnit.SECONDS);
		//		driver.manage().timeouts().setScriptTimeout(150, TimeUnit.SECONDS);
		//		driver.manage().timeouts().implicitlyWait(DataHashMap.waitConfigValues.get("ImplicitWait"), TimeUnit.SECONDS);	
		//		System.out.println("done");
		return driver;
	}
}
