package com.sn.dataproviders;
/**
 * @author mgp0966
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	public static final Logger logger  = LogManager.getLogger(ReadExcel.class.getName());
		
	/***
	 * 		
	 * @param excelPath
	 * @param sheetName
	 * @return String[][]
	 */
	public String[][] getExcelData(String excelPath, String sheetName) throws IOException {
		
		logger.info("Creating excel object:-" + excelPath);
		try (
				FileInputStream file = new FileInputStream(new File(excelPath));
				XSSFWorkbook workbook = new XSSFWorkbook(file);
				)
		{
				
			String[][] dataSets = null;

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheet(sheetName);
			// count number of active tows
			int totalRow = sheet.getLastRowNum() + 1;
			// count number of active columns in row
			int totalColumn = sheet.getRow(0).getLastCellNum();
			// Create array of rows and column
			int count = 0;
			for(int iRow = 1; iRow<=sheet.getLastRowNum();iRow++)	
			{	//XSSFRow row = 
				if(sheet.getRow(iRow).getCell(0).getStringCellValue().equalsIgnoreCase("yes"))	
				{	
					
					count++;
				}
			}
			dataSets = new String[count][totalColumn-1];
			// Iterate through each rows one by one
			int t = 0;
			
			for(int i = 1; i<=sheet.getLastRowNum();i++)	
			{	//XSSFRow row = 
				if(sheet.getRow(i).getCell(0).getStringCellValue().equalsIgnoreCase("yes"))	
				{	
				
					int k = t;
					t++;
					// For each row, iterate through all the columns
					for(int iCell = 1; iCell<sheet.getRow(i).getLastCellNum(); iCell++)
					{ 
						
						// Check the cell type and format accordingly
						dataSets[k][iCell-1] = sheet.getRow(i).getCell(iCell).getStringCellValue();
					}
					
							
						
					
				}
			}

			return dataSets;
		} catch (Exception e) {
			e.printStackTrace();
			return new String[0][0];
		}
		
	}	

	/***
	 * 
	 * @param excelPath
	 * @param sheetName
	 * @return
	 * @throws IOException
	 */
	/*
		public Map<String, Object> getExcelDataInMap(String excelPath, String sheetName) throws IOException {
			try {
				
				logger.info("Creating excel object:-"+excelPath);
				
				Map<String, Object > dataSets = new HashMap<String, Object>();
				//dataSets.
				FileInputStream file = new FileInputStream(new File(excelPath));

				// Create Workbook instance holding reference to .xlsx file
				XSSFWorkbook workbook = new XSSFWorkbook(file);

				// Get first/desired sheet from the workbook
				XSSFSheet sheet = workbook.getSheet(sheetName);
				// count number of active tows
				int totalRow = sheet.getLastRowNum() + 1;
				// count number of active columns in row
				int totalColumn = sheet.getRow(0).getLastCellNum();
				// Create array of rows and column
				//dataSets = new String[totalRow-1][totalColumn];
				// Iterate through each rows one by one
				Iterator<Row> rowIterator = sheet.iterator();

				int i = 0;
				int t = 0;
				
				Row ro = sheet.getRow(0);
				Iterator<Cell> cellItr = ro.cellIterator();
				while(cellItr.hasNext())
				{
					
				}
				
				
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if (i++ != 0) {
						int k = t;
						t++;
						// For each row, iterate through all the columns
						Iterator<Cell> cellIterator = row.cellIterator();
						int j = 0;
						while (cellIterator.hasNext()) {

							Cell cell = cellIterator.next();
							// Check the cell type and format accordingly
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								System.out.print(k+",");
								System.out.print(j+",");
								dataSets.put(sheet.getRow(0).getCell(j).getStringCellValue(), cell);
								dataSets[k][j++] = cell.getStringCellValue();
								System.out.println(cell.getNumericCellValue());
								break;
							case Cell.CELL_TYPE_STRING:
								System.out.print(k+",");
								System.out.print(j+",");
								dataSets[k][j++] = cell.getStringCellValue();
								System.out.println(cell.getStringCellValue());
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								System.out.print(k+",");
								System.out.print(j+",");
								dataSets[k][j++] = cell.getStringCellValue();
								System.out.println(cell.getStringCellValue());
								break;
							case Cell.CELL_TYPE_FORMULA:
								System.out.print(k+",");
								System.out.print(j+",");
								dataSets[k][j++] = cell.getStringCellValue();
								System.out.println(cell.getStringCellValue());
								break;
							}
						}
						System.out.println("");
					}
				}

				file.close();
				return dataSets;
			} catch (Exception e) {
				e.printStackTrace();
				return new String[0][0];
			}
		
		
		
	}
		
		***/
	
	
	
	
 
	

}
