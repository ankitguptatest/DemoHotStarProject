 package com.ankit.WebPages;

import static org.testng.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ankit.DataBaseUtility.ExcelReadWrite;
import com.ankit.DataMaps.CommonDataMaps;
import com.ankit.JavaUtility.JavaWrappers;
import com.ankit.Selenium.SeleniumDriver;
import com.ankit.Selenium.SeleniumWrappers;
import com.ankit.Selenium.TestSuites;
import com.ankit.restAssured.api.ApiDefaultConfig;

public class Page extends SeleniumWrappers{
	
	String elementPath = "";
	WebElement we = null;
	List<WebElement> wes = new ArrayList<WebElement>();
	
	


}




