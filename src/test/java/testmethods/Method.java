package testmethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.SkipException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//import jxl.read.biff.BiffException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Method {
	public static WebDriver driver;
	public static Properties prop = new Properties();
	public static FileReader fr;

	public static String code = "";

	public static void launch(String URL) {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(URL);
	}

	public static void close() {
		driver.quit();
	}
	
	public static void maximize() {
		driver.manage().window().maximize();
	}
	
	public static void wait(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	public static String ReadPropertyFile(String path, String key) throws Exception {

		FileReader f = new FileReader(path);
		Properties p = new Properties();
		p.load(f);
		return p.getProperty(key);

	}

	public String readDataFromExcel(String filePath, String sheetName, int row, int col) throws IOException {
		try (FileInputStream file = new FileInputStream(new File(filePath));
			 Workbook workbook = WorkbookFactory.create(file)) {

			Sheet sheet = workbook.getSheet(sheetName);
			Row excelRow = sheet.getRow(row);
			Cell cell = excelRow.getCell(col);

			return cell.toString();
		}
	}

	public String[] readExcelColumn(String filePath, String sheett, int col) {
		String[] columnValues = null;
		try (FileInputStream file = new FileInputStream(new File(filePath));
			 Workbook workbook = WorkbookFactory.create(file)) {

			Sheet sheet = workbook.getSheet(sheett);

			int rows = sheet.getPhysicalNumberOfRows();
			columnValues = new String[rows];

			for (int i = 0; i < rows; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					Cell cell = row.getCell(col);
					if (cell != null) {
						switch (cell.getCellType()) {
							case STRING:
								columnValues[i] = cell.getStringCellValue();
								break;
							case NUMERIC:
								columnValues[i] = String.valueOf(cell.getNumericCellValue());
								break;
							// Handle other cell types as needed
							default:
								// Handle other cell types or throw an exception
								break;
						}
					}
				}
			}
		} catch (IOException | EncryptedDocumentException e) {
			e.printStackTrace();
		}
		return columnValues;
	}


	public void takeScreenshot(WebDriver driver, String folderPath) {
		// Generate a random file name
		String fileName = generateRandomFileName() + ".png";

		// Take screenshot
		File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		// Set the destination file
		File destinationFile = new File(folderPath + File.separator + fileName);

		try {
			// Copy the screenshot to the destination file
			FileUtils.copyFile(screenshotFile, destinationFile);
			System.out.println("Screenshot saved as: " + destinationFile.getAbsolutePath());
		} catch (IOException e) {
			System.out.println("Failed to save screenshot: " + e.getMessage());
		}
	}

	private static String generateRandomFileName() {
		return UUID.randomUUID().toString();
	}

	public String doubleToString(String number) {

		// Convert String to double
		double doubleValue = Double.parseDouble(number);

		// Convert double to int
		int intValue = (int) doubleValue;

		// Convert int to String
		String stringValue = String.valueOf(intValue);

		return stringValue;
	}

	public String getCID(WebDriver driver){
		String correlationId = ((RemoteWebDriver) driver).getSessionId().toString();
        return correlationId;
    }

	public String getConsole(WebDriver driver){
		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
		String logs = null;

		// Check for errors in console logs
		for (LogEntry entry : logEntries) {
			if (entry.getLevel().toString().equalsIgnoreCase("SEVERE")) {
				logs = ("Error found in console: " + entry.getMessage());
			}
		}
        return logs;
    }

	public int stringToInt(String str){

		double doubleNumber = Double.parseDouble(str); // Convert string to double
		int intNumber = (int) doubleNumber; // Convert double to int
        return intNumber;
    }

	public int getRowCount(String filePath, String sheetName) {
		int rowCount = 0;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			Workbook workbook;
			if (filePath.toLowerCase().endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(fis); // For XLSX (Excel 2007+) format
			} else if (filePath.toLowerCase().endsWith(".xls")) {
				workbook = new HSSFWorkbook(fis); // For XLS (Excel 97-2003) format
			} else {
				throw new IllegalArgumentException("Unsupported file format");
			}
			Sheet sheet = workbook.getSheet(sheetName);
			rowCount = sheet.getPhysicalNumberOfRows();
			workbook.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	// Mapping test case IDs to test method names


}
