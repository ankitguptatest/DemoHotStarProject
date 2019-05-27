package com.ankit.reporting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import com.ankit.propertiesFileUtility.PropertiseFileUtility;

public class SaveIntoTomcat extends ReportingSession{
	
	
	
	public void uploadToWebApps(HashMap<String, String> mp) throws IOException{
		String autoDeployPath ="";
		OutputStream zipShellFileStream = null;
		OutputStream warShellFileStream = null;
		OutputStream deployShellFileStream = null;
		File resultFolder = new File(System.getProperty("user.home")+File.separator+createdFolderName);
		
		try{
		File zipShellFile = new File(System.getProperty("user.home")+"/zipFiles.bat");
		if(zipShellFile.exists())
			zipShellFile.delete();
		zipShellFile.createNewFile();
		zipShellFileStream = new FileOutputStream(new File(System.getProperty("user.home")+"/zipFiles.bat"), false);
		PrintStream zipShellFilePrintStream = new PrintStream(zipShellFileStream);
//		zipShellFilePrintStream.println("cd " + resultFolder.getAbsolutePath().replaceAll("\\\\", "/") + " && jar cvf " + createdFolderName + ".zip *");
		zipShellFilePrintStream.println("cd " + resultFolder.getAbsolutePath()+ " && "+System.getenv("JAVA_HOME")+File.separator+"bin"+File.separator+"jar cvf " + createdFolderName + ".zip *");
//		zipShellFilePrintStream.println("cd " + System.getProperty("user.home")+ " && jar cvf " + createdFolderName + ".zip *");
		zipShellFilePrintStream.flush();
		zipShellFilePrintStream.close();
		zipShellFileStream.close();
		
		File warShellFile = new File(System.getProperty("user.home")+"/warFiles.bat");
		if(warShellFile.exists())
			warShellFile.delete();
		warShellFile.createNewFile();
		warShellFileStream = new FileOutputStream(new File(System.getProperty("user.home")+"/warFiles.bat"), false);
		PrintStream warShellFilePrintStream = new PrintStream(warShellFileStream);
//		warShellFilePrintStream.println("cd " + resultFolder.getAbsolutePath().replaceAll("\\\\", "/") + " && jar cvf " + createdFolderName + ".war *");
		warShellFilePrintStream.println("cd " + resultFolder.getAbsolutePath()+ " && "+System.getenv("JAVA_HOME")+File.separator+"bin"+File.separator+"jar cvf " + createdFolderName + ".war *");
//		warShellFilePrintStream.println("cd " + System.getProperty("user.home")+ " && jar cvf " + createdFolderName + ".war *");
		warShellFilePrintStream.flush();
		warShellFilePrintStream.close();
		warShellFileStream.close();
		
//        autoDeployPath =computeBasePath(createdFolderName, mp.get("TomcatPort"), mp.get("TomcatInstallationPath"));
        autoDeployPath =mp.get("TomcatInstallationPath")+File.separator;
		if(autoDeployPath!=null && !autoDeployPath.equals("")) {
			File deployShellFile = new File(System.getProperty("user.home")+"/autoDeploy.bat");
			if(deployShellFile.exists())
				deployShellFile.delete();
			deployShellFile.createNewFile();
			deployShellFileStream = new FileOutputStream(new File(System.getProperty("user.home")+"/autoDeploy.bat"), false);
			PrintStream deployShellFilePrintStream = new PrintStream(deployShellFileStream);
//			deployShellFilePrintStream.println("cp " + resultFolder.getAbsolutePath().replaceAll("\\\\", "/") + "/" + createdFolderName + ".war " + autoDeployPath);
			deployShellFilePrintStream.println("copy " + resultFolder.getAbsolutePath()+"\\" + createdFolderName + ".war " + autoDeployPath);
			deployShellFilePrintStream.flush();
			deployShellFilePrintStream.close();
			deployShellFileStream.close();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
				
				Process process = Runtime.getRuntime().exec("cmd " +System.getProperty("user.home") + "/zipFiles.bat");
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((reader.readLine()) != null) {}
				process.waitFor();

				process = Runtime.getRuntime().exec("cmd "+System.getProperty("user.home") + "/warFiles.bat");
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((reader.readLine()) != null) {}
				process.waitFor();
				
				if(autoDeployPath!=null && !autoDeployPath.equals(""))
					process = Runtime.getRuntime().exec("cmd "+System.getProperty("user.home") + "/autoDeploy.bat");
					reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					while ((reader.readLine()) != null) {}
					process.waitFor();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
		private String computeBasePath(HashMap<String, String> mp) throws UnknownHostException {
			String warName = "";
			int attempt = 0;
			String basePath="";
			String port = mp.get("TomcatPort"); 
//			String tomcatPath = mp.get("TomcatInstallationPath");
//			do {
//				File warFile = new File(tomcatPath+File.separator+createdFolderName);
//				System.out.println(tomcatPath+File.separator+createdFolderName);
//				if(!warFile.exists()) {
					basePath = "http://"+InetAddress.getLocalHost().getHostAddress()+":"+port+File.separator+"Report"+File.separator+"ConcludeReport.html";
//					warName = warFile.getName();
//				}
//			}
//			while(++attempt<10 && !warName.endsWith(""));
			return basePath;
		}

}
