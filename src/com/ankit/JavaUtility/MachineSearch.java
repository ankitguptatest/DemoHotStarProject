package com.ankit.JavaUtility;

import java.io.File;
import java.io.IOException;

public class MachineSearch {
	
	
	/**
	 * Method to search for the given filename in a specific directory
	 * @author Ankit
	 * @param dir
	 *          given dir to find the fileName
	 * @param appium
	 *          the name of fileName which is in the given dir
	 * @return
	 *          location of the fileName
	 */
	public String serachMachineForFile(String dir, String fileName) {
		String filePath = "";
		File fileDir = new File(dir);
		File[] roots =  fileDir.listFiles();
		//		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			try {
				if (roots[i].listFiles() == null){
					if (roots[i].isDirectory()) {
						String directory = roots[i].getAbsolutePath();
						filePath = searchDirectory(directory, fileName);
						if (!filePath.equals(""))
							break;
					}
					if (roots[i].getName().equals(fileName)) {
						try {
							filePath = roots[i].getCanonicalPath();
							break;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				else{
					for (File f : roots[i].listFiles()) {
						if (f.isDirectory()) {
							String directory = f.getAbsolutePath();
							filePath = searchDirectory(directory, fileName);
							if (!filePath.equals(""))
								break;
						}
						if (f.getName().equals(fileName)) {
							try {
								filePath = f.getCanonicalPath();
								break;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		return filePath;
	}

	/**
	 * Method to search for the given filename in a specific directory
	 * @author Ankit
	 * @param dir
	 *          given dir to find the fileName
	 * @param appium
	 *          the name of fileName which is in the given dir
	 * @return
	 *          location of the fileName
	 */
	public String serachMachineForFileWhichStartWith(String dir, String name) {
		String filePath = "";
		File fileDir = new File(dir);
		File[] roots =  fileDir.listFiles();
//		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			try {
				for (File f : roots[i].listFiles()) {
					if (f.isDirectory()) {
						String directory = f.getAbsolutePath();
						filePath = searchDirNameWhichStartWith(directory, name);
						if (!filePath.equals(""))
							break;
					}
					if (f.getName().equals(name)) {
						try {
							filePath = f.getCanonicalPath();
							break;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		return filePath;
	}

	
//	/**
//	 * Will get the fileName absolute path from the given directory
//	 * @author Ankit
//	 * @return the absolute path of the given fileName
//	 */
//	public String getFilePath(String filePath, String fileName) throws Exception{
//		File f = new File("C:\\Users\\Ankit\\Downloads");
//		return searchDirectory(f, filePath);
//	}
	
	
	
	
	/**
	 * Method to search for the given filename in a specific directory
	 * @author Ankit
	 * @param f
	 *            Object of the directory to be searched into
	 * @param filename
	 *            Name of the file to be searched
	 * @return Path of the file to be searched or an empty string if the file
	 *         was not found
	 * @throws Exception 
	 */
	private String searchDirectory(String dir, String filename) throws Exception {
		File f = new File(dir);
		File[] subFiles = f.listFiles();
		if (subFiles == null) {
			return "";
		}
		String path = "";
		for (File fi : subFiles) {
			if (fi.isDirectory()) {
				String directory = fi.getAbsolutePath();
				path = searchDirectory(directory, filename);
				if (!path.equals(""))
					break;
			} else if (fi.getName().equals(filename)) {
				try {
					path = fi.getCanonicalPath();
					System.out.println("Match found : " + path);
					break;
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(filename+" is not found in the given : "+f.getAbsolutePath());
				}
			}
		}
		return path;
	}

	
//	/**
//	 * Will get the fileName absolute path from the given directory
//	 * @author Ankit
//	 * @return the absolute path of the given fileName
//	 */
//	public String getFilePathWhichStartWith(String filePath, String fileName) throws Exception{
//		File f = new File("C:\\Users\\Ankit\\Downloads");
//		return searchFileNameWhichStartWith(f, filePath);
//	}
	
	
	/**
	 * Method to search for the given filename which has partial name 
	 * in a specific directory
	 * @author Ankit
	 * @param f
	 *            Object of the directory to be searched into
	 * @param filename
	 *            Name of the file to be searched
	 * @return Path of the file to be searched or an empty string if the file
	 *         was not found
	 * @throws Exception 
	 */
	private String searchDirNameWhichStartWith(String dir, String filename) throws Exception {
		File f = new File(dir);
		File[] subFiles = f.listFiles();
		if (subFiles == null) {
			return "";
		}
		String path = "";
		for (File fi : subFiles) {
			if (fi.isDirectory()) {
				String directory = fi.getAbsolutePath();
				path = searchDirNameWhichStartWith(directory, filename);
				if (!path.equals(""))
					break;
			} else if (fi.getName().startsWith(filename)) {
				try {
					path = fi.getCanonicalPath();
					System.out.println("Match found : " + path);
					break;
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(filename+" is not found in the given : "+f.getAbsolutePath());
				}
			}
		}
		return path;
	}
}
