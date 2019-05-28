package com.ankit.JavaUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import org.apache.commons.digester.plugins.strategies.FinderFromFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import com.ankit.DataMaps.CommonDataMaps;
import com.ankit.Selenium.TestSuites;
import com.ankit.tools.ToolWrappers;

public class JavaWrappers {


	/**
	 * Return the date in format of (MMM-D-YYYY)
	 * @author Ankit
	 * @return Current date (MMM-D-YYYY) in String
	 * @throws Exception 
	 */
	
	
//	public void getDay(String date) {
//		Calendar c = Calendar.getInstance();
//		c.setTime(yourDate);
//		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//	}
	
	public static void main(String[] args) throws Exception {
		
//		ToolWrappers tool = new ToolWrappers();
//		String path = findFile("dashboard_david");
//		tool.verifyPDFContent(new TestSuites(), path, "Report provided by:Feedback2Reviews");
		final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
//		ZonedDateTime todayWithDefaultTimeZone=ZonedDateTime.now();
		ZoneId timeZone=ZoneId.of("America/New_York");
		System.out.println("TimeZone : " + timeZone);
//		ZonedDateTime asiaZonedDateTime = ldt.atZone(singaporeZoneId);
//		System.out.println("Date (New York) : " + formatter.format(timeZone));
//        String currentTime= "2017-10-19 22:00:00";
		LocalDateTime datetime = LocalDateTime.now(timeZone);
        String currentTime = datetime.format(formatter);
        System.out.println("Before subtraction of hours from date: "+currentTime);
 
//        LocalDateTime datetime = LocalDateTime.parse(currentTime,formatter);
 
        datetime=datetime.minusHours(4);
 
        String aftersubtraction=datetime.format(formatter);
        System.out.println("After 4 hours subtraction from date: "+aftersubtraction);
	}
	
	
	public  static String getMinus_N_HourInCurrentTimePST(String DATE_FORMAT, int minusHour) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		ZoneId timeZone=ZoneId.of("America/New_York");
		LocalDateTime datetime = LocalDateTime.now(timeZone);
		String currentTime = datetime.format(formatter);
		datetime=datetime.minusHours(minusHour);
		String aftersubtraction=datetime.format(formatter);
		return currentTime+"~"+aftersubtraction;
	}


	public static String getCurrentDate(){

		DateFormat dateFormat = new SimpleDateFormat("MMM-D-YYYY");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getTimeOfAnyZone(String format, String zone){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(format);

		// Use Madrid's time zone to format the date in
		df.setTimeZone(TimeZone.getTimeZone(zone));

		System.out.println("Date and time in "+zone+"  "  + df.format(date));
		return df.format(date)+" "+zone;
	}

	/**
	 * Return time in the format of (DD-MM-YYYY-HH-MM-ss)
	 * @author Ankit
	 * @return Return date_time in String
	 */
	public static String getCurrentDateAndTime(){

		DateFormat df = new SimpleDateFormat("dd-MMM-YYYY-HH-mm-ss");
		Date dateobj = new Date();
		//	       System.out.println("Current Date and time is: "+df.format(dateobj));
		return df.format(dateobj);
	}


	/**
	 * Return the date in the given format
	 * @author Ankit
	 * @return Current date in the given format
	 */
	public static String getCurrentDate(String format){

		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}


	public static String getCurrentDateInPST(String format){
		TimeZone zone = TimeZone.getTimeZone("America/Los_Angeles");
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(zone);
		Date date = new Date();
		return dateFormat.format(date);
	}


	/**
	 * Get yesterday date as in given format
	 * @author Ankit
	 * @param format
	 * @return
	 */
	public static String getYesterdayDateString(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(yesterday());
	}

	private static Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}


	public static String getTomorrowDateString(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +1);
		return dateFormat.format(cal.getTime());
	}

	public static String getTomorrowDateInPST(String format) {
		TimeZone zone = TimeZone.getTimeZone("America/Los_Angeles");
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(zone);
		final Calendar cal = Calendar.getInstance(zone);
		cal.add(Calendar.DATE, +1);
		return dateFormat.format(cal.getTime());
	}

	/**
	 * Return the date in the given format like(HH:mmm:ss)
	 * @author Ankit
	 * @return Current date in the given format
	 */
	public static String getCurrentTime(String format){

		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}


	/**
	 * Return time in (HH:MM:ss) where
	 * @author Ankit
	 * @param starttime
	 *           in mili second
	 * @param endtime
	 *           in mili second
	 * @return
	 */
	public static String getTime(long starttime, long endtime) {
		long diff = endtime - starttime;
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		// long diffDays = diff / (24 * 60 * 60 * 1000);
		return (diffHours + ":" + diffMinutes + ":" + diffSeconds);
	}




	/**
	 * Subtract all the values from given string except number and return
	 * @author Ankit
	 * @param value
	 * @return number from the given string
	 */
	public static String getNumericValueFromString(String value){

		if (value.length() != 0)
			return value.replaceAll("[^\\d]", "");
		else
			return "";
	}

	public static String getAlphabetOnlyFmString(String value){

		if (value.length() != 0)
			return value.replaceAll("[^A-Za-z]", "");
		else
			return "";
	}

	/**
	 * Return value in Indian Currency format, like (10,123 or 1,000)
	 * @author Ankit
	 * <p>Suppose you entered A string which have number: 1456089
	 * then method will return 14,56,089 as in Indian Currency format
	 * @param number
	 * @return
	 */
	public static String convertNumberIntoFormat(long number) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		formatter.setMaximumFractionDigits(0);
		DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
		decimalFormatSymbols.setCurrencySymbol("");
		((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
		String moneyString = formatter.format(number).trim();
		moneyString = moneyString.replaceAll("\\u00A0", "");
		return moneyString;
	}



	/**
	 * Will create directory in given path with folder name
	 * @author Ankit
	 * @param pathTofolder where to create folder 
	 * @param folderName Name of the folder
	 * @return file of the folder
	 * @throws IOException
	 */
	public synchronized static File createDir(String pathTofolder, String folderName)
			throws IOException {
		final File sysDir = new File(pathTofolder);
		File newTempDir;
		final int maxAttempts = 9;
		int attemptCount = 0;
		do {
			attemptCount++;
			if (attemptCount > maxAttempts) {
				throw new IOException("The highly improbable has occurred! Failed to "
						+ "create a unique temporary directory after "
						+ maxAttempts + " attempts.");
			}
			String dirName = folderName+"_"+getCurrentDateAndTime();
			newTempDir = new File(sysDir, dirName);
		} while (newTempDir.exists());

		if (newTempDir.mkdirs()) {
			return newTempDir;
		} else {
			throw new IOException("Failed to create temp dir named "+ newTempDir.getAbsolutePath());
		}
	}


	/**
	 * Will create directory in given path with folder name
	 * @author Ankit
	 * @param pathTofolder where to create folder 
	 * @param folderName Name of the folder
	 * @return file of the folder
	 * @throws IOException
	 */
	public synchronized static File createDirIfNotExist(String pathTofolder, String folderName)
			throws IOException {
		final File sysDir = new File(pathTofolder);
		File newTempDir = new File(sysDir,folderName);
		if(!newTempDir.exists()){
			final int maxAttempts = 9;
			int attemptCount = 0;
			do {
				attemptCount++;
				if (attemptCount > maxAttempts) {
					throw new IOException("The highly improbable has occurred! Failed to "
							+ "create a unique temporary directory after "
							+ maxAttempts + " attempts.");
				}
				String dirName = folderName+"_"+getCurrentDateAndTime();
				newTempDir = new File(sysDir, dirName);
			} while (newTempDir.exists());

			if (newTempDir.mkdirs()) {
				return newTempDir;
			} else {
				throw new IOException("Failed to create temp dir named "+ newTempDir.getAbsolutePath());
			}
		}
		else{
			return newTempDir;
		}
	}


	public static String convertIntoUS(String date) throws ParseException{


		//		DateFormat dtfmt = new SimpleDateFormat( "yyyy-MM-dd");
		//		TimeZone obj = TimeZone.getTimeZone("CST");
		//		dtfmt.setTimeZone(obj);
		//		Date d = new Date();
		//		System.out.println(dtfmt.format(d));

		Calendar currentdate = Calendar.getInstance();
		String strdate = null;
		DateFormat formatter = new SimpleDateFormat(date);
		strdate = formatter.format(currentdate.getTime());
		TimeZone obj = TimeZone.getTimeZone("CST");

		formatter.setTimeZone(obj);
		//System.out.println(strdate);
		//System.out.println(formatter.parse(strdate));
		Date theResult = formatter.parse(strdate);

		//        System.out.println("The current time in India is  :: " +currentdate.getTime());
		System.out.println("The date and time in :: "+ obj.getDisplayName() + "is : : " + theResult);
		return obj.getDisplayName();
	}



	/**
	 * Will delete the folder if Exist
	 * @author Ankit
	 * @param pathFolder
	 */
	public static void deleteExistingFolder(File pathFolder)
	{
		if (pathFolder.exists()) {
			try {
				FileUtils.deleteDirectory(pathFolder);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method will return the number in between low and high, the low or high 
	 * Example: if you pass (5, 0), it may return 0,1,2,3,4,5
	 * @param high
	 * @param low
	 * @return
	 */
	public static int getRandoNumber(int high, int low){
		Random rn = new Random();
		int num = rn.nextInt((high-low)+low)+low;
		return num;
	}


	public static String getRandomString(int length){
		return RandomStringUtils.randomAlphabetic(length);
	}


	public static void runABatFile(String command) {
		try {
			File file = new File("run.bat");
			file.createNewFile();
			PrintWriter batWriter = new PrintWriter(file, "UTF-8");
			System.out.println("Command is: "+command);
			batWriter.print(command);
			batWriter.close();
			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "run.bat");
			Process process = pb.start();
			InputStream is = process.getInputStream();  
			InputStreamReader isr = new InputStreamReader(is);  
			BufferedReader br = new BufferedReader(isr);  
			String line;  
			while ((line = br.readLine()) != null) {  
				System.out.println(line);  
			}  
			process.waitFor();
			file.delete();
			process.exitValue();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void closeAllBrowsers() {

		String platform = System.getProperty("os.name");
		if (platform.startsWith("Windows")) {
			String command = "taskkill /F /IM chrome* /T";
			runABatFile(command);
			command = "taskkill /F /IM ie* /T";
			runABatFile(command);
			command = "taskkill /F /IM firefox* /T";
			runABatFile(command);
		}
	}
	
	public static void closeIEBrowsers() {

		String platform = System.getProperty("os.name");
		if (platform.startsWith("Windows")) {
			String command = "taskkill /F /IM ie* /T";
			runABatFile(command);
		}
	}	
	
	
	public synchronized static boolean isFileDownloaded(TestSuites ts, String filetype) throws Exception {
		boolean flag = false;
		if(isRemoteMachine()) {
			String home = System.getProperty("user.home");
			String downloadPath = home+File.separator+"Downloads";
			File dir = new File(downloadPath);
			File[] dirContents = dir.listFiles();

			for (int i = 0; i <5; i++) {
				if (getDetetedFile(ts,dirContents, filetype)) {
					flag= true;
					break;
				}
				else {
					Thread.sleep(3000);
					dir = new File(downloadPath);
					dirContents = dir.listFiles();
					if(getDetetedFile(ts,dirContents, filetype)) {
						flag = true;
						break;
					}
				}
			}
		}
		else {
			flag = true;
		}
		return flag;
//		return false;
	}
	
	
	public static String findFile(String name) {
//		String name= "dashboard_david";
		String path ="";
		String home = System.getProperty("user.home");
		String downloadPath = home+File.separator+"Downloads";
		File file = new File(downloadPath);
		File[] list = file.listFiles();
		if(list!=null)
			for (File fil : list)
			{
				if (fil.getName().contains(name))
				{
					path = fil.getAbsolutePath();
//					System.out.println(fil.getAbsolutePath());
				}
			}
		return path;
	}

	
	
	
	
	public static boolean getDetetedFile(TestSuites ts,File[] dirContents, String filetype) throws Exception {
		for (int i = 0; i < dirContents.length; i++) {
			if (dirContents[i].getName().contains(filetype)) {
				try {
				// File has been found, it can now be deleted:
				System.out.println("Deleting Downloaded file: "+dirContents[i].getName());
				dirContents[i].delete();
				ts.getTestReporting().addTestSteps(ts,"<b>Deleted File </b>", dirContents[i].getName(), "PASS", false);
				}catch(Exception e) {
					e.printStackTrace();
					ts.getTestReporting().addTestSteps(ts,"<b>Deleted File </b>", dirContents[i].getName(), "FAIL", false);	
				}
				return true;
			}
		}
		return false;
	}
	
	
	public synchronized static void deleteDownloadedFiles(String filetype) throws UnknownHostException {
		if(isRemoteMachine()) {
			String home = System.getProperty("user.home");
			String downloadPath = home+File.separator+"Downloads";
			File dir = new File(downloadPath);
			File[] dirContents = dir.listFiles();

			for (int i = 0; i < dirContents.length; i++) {
				if (dirContents[i].getName().contains(filetype)) {
					// File has been found, it can now be deleted:
					System.out.println("Deleting file: "+dirContents[i].getName());
					dirContents[i].delete();
				}
			}
		}
	}

	
	public static boolean isRemoteMachine(){
		try {
//			HashMap<String, String> mailMap = (HashMap<String, String>) new PropertiseFileUtility().getConfig(System.getProperty("user.dir")+File.separator+"src", "MailConfig.properties");
			String machineIP = InetAddress.getLocalHost().getHostAddress();
			System.out.println(machineIP);
			if (!getprivateIP(CommonDataMaps.mailConfig).contains(machineIP)){
				System.out.println("It's not a Jenkin Machine");
				return false;
			}else{
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	public static void setMailConfig() {
//		try {
//			CommonDataMaps.mailConfig = (HashMap<String, String>) new PropertiseFileUtility().getConfig(System.getProperty("user.dir")+File.separator+"src", "MailConfig.properties");
//			String machineIP = InetAddress.getLocalHost().getHostAddress();
//			System.out.println(machineIP);
//			if (!getprivateIP(CommonDataMaps.mailConfig).contains(machineIP)){
//				System.out.println("It's not a Jenkin Machine");
//			}else{
//				CommonDataMaps.mailConfig.put("JenkinPrivateMachineIP", machineIP);
//			}
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//			System.exit(0);
//		}
//	}
	
	
	public static void deleteDirectory(File dir) {
		try {
		 recursiveDelete(dir);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void recursiveDelete(File file) {
        //to end the recursive loop
        if (!file.exists())
            return;
        
        //if directory, go inside and call recursively
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                //call recursively
                recursiveDelete(f);
            }
        }
        //call delete to delete files and empty directory
        file.delete();
        System.out.println("Deleted file/folder: "+file.getAbsolutePath());
    }

		

	
	public static String getprivateIP(HashMap<String, String> mailMap) throws UnknownHostException {
		String jenkingIP = "";
		String platform = System.getProperty("os.name");
		System.out.println("PlateForm: "+platform);
		if (platform.contains("Linux") || platform.contains("linux")) {
			jenkingIP = mailMap.get("JenkinPrivateMachineIP_Linux");
		}
		else {
			jenkingIP = mailMap.get("JenkinPrivateMachineIP");
		}
		return jenkingIP;
	}

	
	

}
