package com.ankit.DataBaseUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelReadWrite {
	
	Connection connectionObj = null;

//	public static void main(String[] args) {
//
//	}
	
	
	public void createExcel(String fileName) {
		try {
			Workbook workbook = null;

			if (fileName.endsWith("xlsx")) {
				workbook = new XSSFWorkbook();
			} else if (fileName.endsWith("xls")) {
				workbook = new HSSFWorkbook();
			} else {
				System.err.println("The specified file is not Excel file");
			}
			FileOutputStream fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void addSheetAndColumns(String fileName, boolean formatingColumn, String sheetName, String...columns) {
		try {
			FileInputStream inputStream = new FileInputStream(new File(fileName));
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.createSheet(sheetName);
			CellStyle headerCellStyle = workbook.createCellStyle();
			if(formatingColumn) {
				Font headerFont = workbook.createFont();
				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 14);
				headerFont.setColor(IndexedColors.BLACK.getIndex());
				headerCellStyle.setFont(headerFont);
			}
			// Create a Row
			Row headerRow = sheet.createRow(0);

			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(headerCellStyle);
			}

			inputStream.close();
			FileOutputStream outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void writeInExistingExcel(String fileName, String sheetName, boolean bold, String...values) {
		try {
			FileInputStream inputStream = new FileInputStream(new File(fileName));
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheet(sheetName);
			int rowCount = sheet.getLastRowNum();
			Row row = sheet.createRow(++rowCount);
			int columnCount = 0;
			CellStyle style = workbook.createCellStyle();;
			if(bold) {
				Font cellFont = workbook.createFont();
				cellFont.setBold(true);
//				style=row.getRowStyle();
				style.setFont(cellFont);
			}
			Cell cell = row.createCell(columnCount);
			cell.setCellValue(rowCount);
			for (Object field : values) {
				cell = row.createCell(++columnCount);
				cell.setCellStyle(style);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				}
			}
			inputStream.close();
			FileOutputStream outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	public void writeInExistingExcelInRow(String fileName, String sheetName, boolean bold, List<String> values) {
		try {
			FileInputStream inputStream = new FileInputStream(new File(fileName));
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheet(sheetName);
			int rowCount = sheet.getLastRowNum();
			Row row = sheet.createRow(++rowCount);
			int columnCount = 0;
			CellStyle style = workbook.createCellStyle();;
			if(bold) {
				Font cellFont = workbook.createFont();
				cellFont.setBold(true);
//				style=row.getRowStyle();
				style.setFont(cellFont);
			}
			Cell cell = row.createCell(columnCount);
			cell.setCellValue(rowCount);
			for (Object field : values) {
				cell = row.createCell(++columnCount);
				cell.setCellStyle(style);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				}
			}
			inputStream.close();
			FileOutputStream outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void createFilloObject(String fileName) {
		try {
		connectionObj =new Fillo().getConnection(fileName);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
     
	
	
	public void executeQueryInExcel(String fileName, String query) {
		try {
			if(connectionObj == null) {
				connectionObj =new Fillo().getConnection(fileName);
			}
			System.out.println(query);
			connectionObj.executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public List<String> getDataFromExcel(String fileName, String query, String data) throws Exception{
		List<String> ls = new ArrayList<String>();
		if(connectionObj == null) {
			connectionObj =new Fillo().getConnection(fileName);
		}
		String[] reqHeaders = data.split(",");
		Recordset rs = null;
		try {
			rs  = connectionObj.executeQuery(query);
		} catch (FilloException e) {
			e.printStackTrace();
			System.out.println("Fillo exception occured "+e.getMessage());
			System.out.println("Exception occured for: "+query);
		}catch(Exception e){
			System.out.println("Another Exception ocured rather than Fillo Exception");
			System.out.println("Exception occured for: "+query);
			e.printStackTrace();
		}
		try {
			int row =0;
			while(rs.next()){
				for(int col =0; col <reqHeaders.length; col++){
					String head = reqHeaders[col];
					ls.add(rs.getField(head.trim()));
				}
				row++;
			}
			System.out.println("Total row for "+query+" is: "+row);
		} catch (FilloException e) {
			e.printStackTrace();
			throw new Exception("Fille exception while fetching data :"+e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("Another exception occured while fetching data :"+e.getMessage());
		}
		return ls;
	}	

	
	
	
}
