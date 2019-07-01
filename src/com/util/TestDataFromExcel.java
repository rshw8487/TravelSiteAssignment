package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class TestDataFromExcel {

	private static XSSFSheet excelWSheet;
	private static XSSFWorkbook excelWBook;
	private static XSSFCell cell;
	private static XSSFRow row;
	private static String excelPath;
	private static String excelSheet;
	static EventFiringWebDriver driver1;

	public TestDataFromExcel(String excelFileName, String sheetName) {
		HelperMethods helper = new HelperMethods();
		
		String excelPath = helper.getPropValue("excelFilePath")
				+ File.separator + excelFileName;
		this.excelPath = excelPath;
		this.excelSheet = sheetName;
		createExcelFileObjectAndSheetObject(sheetName);
	}

	// This method is to set the File path and to open the Excel file, Pass
	// Excel Path and Sheet name as Arguments to this method
	public void createExcelFileObjectAndSheetObject(String sheetName) {
		try {
			// Open the Excel file
			FileInputStream excelFile = new FileInputStream(excelPath);
			// Access the required test data sheet
			excelWBook = new XSSFWorkbook(excelFile);
			excelWSheet = excelWBook.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// This method is to read the test data from the Excel cell, in this we are
	// passing parameters as Row num and Col num
	public String getCellData(int rowNum, int colNum) {
		try {
			cell = excelWSheet.getRow(rowNum).getCell(colNum);
			String cellData = cell.getStringCellValue();
			return cellData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// This method is to write in the Excel cell, Row num and Col num are the
	// parameters
	public void writeDataToExcel(String result, int rowNum, int colNum) {
		try {
			row = excelWSheet.getRow(rowNum);
			cell = row.getCell(colNum);
			if (cell == null) {
				cell = row.createCell(colNum);
				cell.setCellValue(result);
			} else {
				cell.setCellValue(result);
			}

			// Constant variables Test Data path and Test Data file name
			FileOutputStream fileOut = new FileOutputStream(excelPath);

			excelWBook.write(fileOut);
			System.out.println("fileOut is " + fileOut.toString());

			fileOut.flush();
			fileOut.close();

			System.out.println("value is wrtten to excel file");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateDataToExcel(Object result) {
		try {
    		// Iterator<Row> rowIterator = excelWSheet.rowIterator();
			int rownum = excelWSheet.getLastRowNum();
			Row row = excelWSheet.createRow(++rownum);

			int cellnum = 0;
			Cell cell = row.createCell(cellnum);

			if (result instanceof String) {
				cell.setCellValue((String) result);
			} else if (result instanceof Boolean) {
				cell.setCellValue((Boolean) result);
			} else if (result instanceof Date) {
				cell.setCellValue((Date) result);
			} else if (result instanceof Double) {
				cell.setCellValue((Double) result);
			}

			// Constant variables Test Data path and Test Data file name
			FileOutputStream fileOut = new FileOutputStream(excelPath);
			System.out.println("fileOut is " + fileOut);

			System.out.println("excelPath is " + excelPath.toString());

			excelWBook.write(fileOut);
			System.out.println("fileOut is " + fileOut.toString());

			fileOut.flush();
			fileOut.close();

			System.out.println("value is wrtten to excel file");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateDateToSpecificCell(String desiredHeader, String result){
		try{
			int rownum = excelWSheet.getLastRowNum();
			System.out.println(rownum);
			// finding the header
			for( int i=1; i<excelWSheet.getRow(0).getLastCellNum(); i++){
				String s= excelWSheet.getRow(0).getCell(i).getStringCellValue();
				if(s.equals(desiredHeader))	{
					excelWSheet.getRow(rownum).createCell(i).setCellValue(result);
					// updating the cell					
				}
			}
			// updating changes to the excel sheet
			FileOutputStream outputStream = new FileOutputStream(excelPath);
			excelWBook.write(outputStream);
			excelWBook.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<Integer, ArrayList> readAllRows() {
		List sheetData = new ArrayList();
		int i = 0;
		Map<Integer, ArrayList> data = new HashMap<Integer, ArrayList>();
		try {
			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = excelWSheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				int rowNum = row.getRowNum();
				ArrayList temp = new ArrayList();
				if (rowNum != 0) {
					// For each row, iterate through each columns
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							// System.out.print(cell.getStringCellValue() +
							// "\t");
							temp.add(cell.getStringCellValue());
							break;

						case Cell.CELL_TYPE_NUMERIC:
							// System.out.print(cell.getNumericCellValue() +
							// "\t");
							temp.add(cell.getNumericCellValue());
							break;

						case Cell.CELL_TYPE_BOOLEAN:
							// System.out.print(cell.getBooleanCellValue() +
							// "\t");
							temp.add(cell.getBooleanCellValue());
							break;

						default:
						}
					}
					if (temp.isEmpty() != true)
						data.put(rowNum, temp);
				}
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
