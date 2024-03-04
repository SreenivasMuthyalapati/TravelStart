package testmethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//import jxl.read.biff.BiffException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

	public String readDataFromExcel(String filePath, int sheetIndex, int row, int col) throws IOException {
		try (FileInputStream file = new FileInputStream(new File(filePath));
			 Workbook workbook = WorkbookFactory.create(file)) {

			Sheet sheet = workbook.getSheetAt(sheetIndex);
			Row excelRow = sheet.getRow(row);
			Cell cell = excelRow.getCell(col);

			return cell.toString();
		}
	}

	public String[] readExcelColumn(String filePath, int sheetIndex, int col) {
		String[] columnValues = null;
		try (FileInputStream file = new FileInputStream(new File(filePath));
			 Workbook workbook = WorkbookFactory.create(file)) {

			Sheet sheet = workbook.getSheetAt(sheetIndex);

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




}
