package testmethods;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.io.FileUtils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.record.PageBreakRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.apache.http.HttpHeaders;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpHeaders;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import pageObjects.CloudFlare;
import pageObjects.FlightPage;
import pageObjects.HomePage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.text.BreakIterator;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

//import jxl.read.biff.BiffException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import pageObjects.Paths;


import static io.restassured.RestAssured.given;
import static pageObjects.Paths.dataPath;
import static testmethods.TSMethods.baseURL;
import static testmethods.TSMethods.m;

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

			// Use DataFormatter to get formatted string representation of cell value
			DataFormatter dataFormatter = new DataFormatter();
			return dataFormatter.formatCellValue(cell);
		}
	}

	public static String readDataFromExcelFile(String filePath, String sheetName, int row, int col) throws IOException {
		try (FileInputStream file = new FileInputStream(new File(filePath));
			 Workbook workbook = WorkbookFactory.create(file)) {

			Sheet sheet = workbook.getSheet(sheetName);
			Row excelRow = sheet.getRow(row);
			Cell cell = excelRow.getCell(col);

			// Use DataFormatter to get formatted string representation of cell value
			DataFormatter dataFormatter = new DataFormatter();
			return dataFormatter.formatCellValue(cell);
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
			
		}
		return columnValues;
	}


	public void takeScreenshot(WebDriver driver, String folderPath, String screenShotPath) {
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
			screenShotPath = destinationFile.getAbsolutePath();
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
		String correlationId = "";

		String URL = driver.getCurrentUrl();
		String [] brokenURL = URL.split("correlation_id=");
		try {
			correlationId = brokenURL[1];
		} catch (ArrayIndexOutOfBoundsException e){
			correlationId = "NA";
		}

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
			
		}
		return rowCount;
	}

	public void invokeBrowser(String browser, WebDriver driver){
		if (browser.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("Edge")) {
			driver = new EdgeDriver();
		}
	}

	public String scientificNotationToString(String value){
		String correctedValue = String.format("%.0f", value);
        return correctedValue;
    }

	public void writeToExcel(String value, int cellNumber, String outputExcelPath) throws IOException {
		// Load Excel file
		FileInputStream inputStream = new FileInputStream(new File(outputExcelPath));
		Workbook workbook = new XSSFWorkbook(inputStream);

		// Get the first sheet of the workbook
		Sheet sheet = workbook.getSheetAt(0);

		// Iterate through the rows to find an empty cell in the first column
		int rowNum = 0;
		for (Row row : sheet) {
			Cell cell = row.getCell(cellNumber);
			if (cell == null || cell.getCellType() == CellType.BLANK) {
				break; // Found an empty cell, stop iterating
			}
			rowNum++; // Move to the next row
		}

		// Create a new row if necessary
		Row row = sheet.getRow(rowNum);
		if (row == null) {
			row = sheet.createRow(rowNum);
		}

		// Create or fetch the cell and write the value
		Cell cell = row.createCell(cellNumber);
		cell.setCellValue(value);

		// Write data back to the Excel file using a new FileOutputStream
		FileOutputStream outputStream = new FileOutputStream(outputExcelPath);
		workbook.write(outputStream);

		// Close streams
		outputStream.close();
		workbook.close();
		inputStream.close();
	}






	public void cloudFlareAuthenticate(String mailID) throws InterruptedException {
		Scanner getOTP = new Scanner(System.in);
		WebElement cloudflareicon = null;

		try{
			cloudflareicon = driver.findElement(CloudFlare.cloudFlareLogo);
		}catch (NoSuchElementException e){
			System.out.println("Cloud flare authentication not required");
		}

		if (cloudflareicon.isDisplayed()){
			driver.findElement(CloudFlare.email).sendKeys(mailID);
			driver.findElement(CloudFlare.sendCode).click();
			Thread.sleep(2000);
			String OTPCode = getOTP.next();
			driver.findElement(CloudFlare.codeInput).sendKeys(OTPCode);
			driver.findElement(CloudFlare.submitCode).click();
		}

	}

	public void departureMonthSelector (WebDriver driver, String departureMonth) throws InterruptedException {

		// Define a map to map month numbers to month names
		Map<String, Integer> monthMap = new HashMap<>();
		monthMap.put("JANUARY", 1);
		monthMap.put("FEBRUARY", 2);
		monthMap.put("MARCH", 3);
		monthMap.put("APRIL", 4);
		monthMap.put("MAY", 5);
		monthMap.put("JUNE", 6);
		monthMap.put("JULY", 7);
		monthMap.put("AUGUST", 8);
		monthMap.put("SEPTEMBER", 9);
		monthMap.put("OCTOBER", 10);
		monthMap.put("NOVEMBER", 11);
		monthMap.put("DECEMBER", 12);

		// Get the current YearMonth
		YearMonth currentYearMonth = YearMonth.now();

		// Get the current month
		Month currentMonth = currentYearMonth.getMonth();

		String currentMonthInString = String.valueOf(currentMonth);

		String selectedMonth = driver.findElement(By.xpath("(//div[@class='ngb-dp-month-name ng-star-inserted'])[1]")).getText();
		String selectedMonthArr[] = selectedMonth.split(" ");

		driver.findElement(HomePage.departureDate).click();

		Thread.sleep(500);

		selectedMonth  =selectedMonthArr[0];

		boolean isCalenderPrefilled = false;

		if (!selectedMonth.equalsIgnoreCase(currentMonthInString)){

			isCalenderPrefilled = true;

		}

		currentMonthInString = selectedMonth;

		Thread.sleep(2000);


		int numberOfMonthsDifference = monthMap.get(departureMonth.toUpperCase()) - monthMap.get(currentMonthInString.toUpperCase());

		if (numberOfMonthsDifference > 0){
		for (int a = 0; a < numberOfMonthsDifference; a++) {
			Thread.sleep(200);

			driver.findElement(HomePage.nextMonth).click();
		}} else if (numberOfMonthsDifference < 0) {

			if (isCalenderPrefilled){

				for (int a = 0; a < numberOfMonthsDifference; a++) {
					Thread.sleep(200);

					driver.findElement(By.xpath("(//button[@title='Previous month'])[1]")).click();
				}

			}

			numberOfMonthsDifference = (12 - monthMap.get(currentMonthInString.toUpperCase()));
			numberOfMonthsDifference = (monthMap.get(departureMonth.toUpperCase())) + numberOfMonthsDifference;

			for (int a = 0; a < numberOfMonthsDifference; a++) {
				Thread.sleep(200);

				driver.findElement(HomePage.nextMonth).click();

			}
		}
	}

	public void returnMonthSelector (WebDriver driver, String departureMonth, String returnMonth) throws InterruptedException {

		// Define a map to map month numbers to month names
		Map<String, Integer> monthMap = new HashMap<>();
		monthMap.put("JANUARY", 1);
		monthMap.put("FEBRUARY", 2);
		monthMap.put("MARCH", 3);
		monthMap.put("APRIL", 4);
		monthMap.put("MAY", 5);
		monthMap.put("JUNE", 6);
		monthMap.put("JULY", 7);
		monthMap.put("AUGUST", 8);
		monthMap.put("SEPTEMBER", 9);
		monthMap.put("OCTOBER", 10);
		monthMap.put("NOVEMBER", 11);
		monthMap.put("DECEMBER", 12);

		int numberOfMonthsDifference = monthMap.get(returnMonth.toUpperCase()) - monthMap.get(departureMonth.toUpperCase());

		driver.findElement(By.xpath("//input[@id='arr_date0']")).click();

		if (numberOfMonthsDifference > 0){
			for (int a = 0; a < numberOfMonthsDifference; a++) {
				Thread.sleep(200);

				driver.findElement(HomePage.nextMonth).click();
			}} else if (numberOfMonthsDifference < 0) {
			numberOfMonthsDifference = (12 - monthMap.get(departureMonth.toUpperCase()));
			numberOfMonthsDifference = (monthMap.get(returnMonth.toUpperCase())) + numberOfMonthsDifference;

			for (int a = 0; a < numberOfMonthsDifference; a++) {
				Thread.sleep(200);

				driver.findElement(HomePage.nextMonth).click();

			}
		}
	}

	public void paxSender(WebDriver driver, String adultCount, String youngAdultCount, String childCount, String infantCount, String airline1, String airline2) throws IOException, InterruptedException {

		List<Object[]> pax = new ArrayList<>();

		// Extracting all test data from test cases in test data sheet
		int adultPaxCount = Integer.parseInt(adultCount)-1;
		int youngAdultPaxCount = Integer.parseInt(youngAdultCount);
		int childPaxCount = Integer.parseInt(childCount);
		int infantPaxCount = Integer.parseInt(infantCount);


		//Adding adults to list
		for (int i = 3; i < adultPaxCount + 3; i++) {
			String paxType = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 0);
			String title = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 1);
			String firstName = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 2);
			String middleName = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 3);
			String lastName = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 4);
			String dateOfBirth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 5);
			String monthOfBirth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 6);
			String yearOfBirth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 7);
			String passportNumber = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 8);
			String passportExpiryDate = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 9);
			String passportExpiryMonth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 10);
			String passportExpiryYear = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 11);
			String passportNationality = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 12);
			String passportIssuingCountry = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 13);
			String addBaggage = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 14);

			pax.add(new Object[]{
					paxType,
					title,
					firstName,
					middleName,
					lastName,
					dateOfBirth,
					monthOfBirth,
					yearOfBirth,
					passportNumber,
					passportExpiryDate,
					passportExpiryMonth,
					passportExpiryYear,
					passportNationality,
					passportIssuingCountry,
					addBaggage});
		}


		//Adding young adults to list
		for (int i = 12; i < youngAdultPaxCount + 12; i++) {
			String paxType = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 0);
			String title = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 1);
			String firstName = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 2);
			String middleName = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 3);
			String lastName = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 4);
			String dateOfBirth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 5);
			String monthOfBirth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 6);
			String yearOfBirth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 7);
			String passportNumber = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 8);
			String passportExpiryDate = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 9);
			String passportExpiryMonth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 10);
			String passportExpiryYear = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 11);
			String passportNationality = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 12);
			String passportIssuingCountry = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 13);
			String addBaggage = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 14);

			pax.add(new Object[]{
					paxType,
					title,
					firstName,
					middleName,
					lastName,
					dateOfBirth,
					monthOfBirth,
					yearOfBirth,
					passportNumber,
					passportExpiryDate,
					passportExpiryMonth,
					passportExpiryYear,
					passportNationality,
					passportIssuingCountry,
					addBaggage});
		}


		// Adding child to list
		for (int i = 21; i < childPaxCount + 21; i++) {
			String paxType = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 0);
			String title = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 1);
			String firstName = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 2);
			String middleName = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 3);
			String lastName = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 4);
			String dateOfBirth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 5);
			String monthOfBirth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 6);
			String yearOfBirth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 7);
			String passportNumber = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 8);
			String passportExpiryDate = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 9);
			String passportExpiryMonth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 10);
			String passportExpiryYear = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 11);
			String passportNationality = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 12);
			String passportIssuingCountry = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 13);
			String addBaggage = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 14);

			pax.add(new Object[]{
					paxType,
					title,
					firstName,
					middleName,
					lastName,
					dateOfBirth,
					monthOfBirth,
					yearOfBirth,
					passportNumber,
					passportExpiryDate,
					passportExpiryMonth,
					passportExpiryYear,
					passportNationality,
					passportIssuingCountry,
					addBaggage});
		}

		//Adding infants to pax
		// Adding child to list
		for (int i = 30; i < infantPaxCount + 30; i++) {
			String paxType = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 0);
			String title = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 1);
			String firstName = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 2);
			String middleName = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 3);
			String lastName = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 4);
			String dateOfBirth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 5);
			String monthOfBirth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 6);
			String yearOfBirth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 7);
			String passportNumber = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 8);
			String passportExpiryDate = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 9);
			String passportExpiryMonth = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 10);
			String passportExpiryYear = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 11);
			String passportNationality = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 12);
			String passportIssuingCountry = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 13);
			String addBaggage = Method.readDataFromExcelFile(dataPath, "Passenger details", i, 14);

			pax.add(new Object[]{
					paxType,
					title,
					firstName,
					middleName,
					lastName,
					dateOfBirth,
					monthOfBirth,
					yearOfBirth,
					passportNumber,
					passportExpiryDate,
					passportExpiryMonth,
					passportExpiryYear,
					passportNationality,
					passportIssuingCountry,
					addBaggage});
		}

		FlightPage enterPaxDetails = new FlightPage(driver);

			for (int i = 0; i < pax.size(); i++) {

				// Select title
				for (int j = 0; j <= 1; j++){
					// Select title
					String title = (String) pax.get(i)[j];
					enterPaxDetails.titleSelector(i,title);
				}

				// Enter First name
				for (int j = 2; j <= 2; j++){
					String firstName = (String) pax.get(i)[j];
					enterPaxDetails.inputFirstName(i,firstName);
				}

				// Enter Last name
				for (int j = 4; j <= 4; j++){
					String lastName = "";
					if (airline1.equalsIgnoreCase("FA") && (airline2.isEmpty()||airline2.isBlank())){
						lastName = "Test";
					}
					else if (airline1.equalsIgnoreCase("FA") && airline2.equalsIgnoreCase("FA")) {
						lastName = "Test";
					}else {
						lastName = (String) pax.get(i)[j];
					}
					enterPaxDetails.inputLastName(i,lastName);
				}

				// Select date of birth
				for (int j = 5; j <= 5; j++){
					String dateOfBirth = (String) pax.get(i)[j];
					enterPaxDetails.selectDateOfBirth(i+1, dateOfBirth);

				}

				// Select month of birth
				for (int j = 6; j <= 6; j++){
					String monthOfBirth = (String) pax.get(i)[j];
					enterPaxDetails.selectMonthOfBirth(i+1, monthOfBirth);
				}

				// Select year of birth
				for (int j = 7; j <= 7; j++){
					String yearOfBirth = (String) pax.get(i)[j];
					enterPaxDetails.selectYearOfBirth(i+1, yearOfBirth);
				}

				// Input passport details
				WebElement passPortInfo = null;
				try{
					passPortInfo = driver.findElement(FlightPage.ppInfo);
				} catch (NoSuchElementException e){
					
				}
                try{
                if (passPortInfo.isDisplayed()){

					// Input passport number
					for (int j = 8; j <= 8; j++){
						String passPortNumber = (String) pax.get(i)[j];
						enterPaxDetails.enterPassportNumber(i, passPortNumber);
					}

					// Select date of passport expiry
					for (int j = 9; j <= 9; j++){
						String ppExpiryDate = (String) pax.get(i)[j];
						enterPaxDetails.selectDateOfppExpiry(i+1, ppExpiryDate);
					}

					// Select month of passport expiry
					for (int j = 10; j <= 10; j++){
						String monthOfppExpiry = (String) pax.get(i)[j];
						enterPaxDetails.selectMonthOfppExpiry(i+1, monthOfppExpiry);
					}

					// Select year of passport expiry
					for (int j = 11; j <= 11; j++){
						String yearOfppExpiry = (String) pax.get(i)[j];
						enterPaxDetails.selectYearppExpiry(i+1, yearOfppExpiry);
					}

					//Selecting passport Nationality
					for (int j = 12; j <= 12; j++){
						String ppNationality = (String) pax.get(i)[j];
						enterPaxDetails.selectppNationality(i, ppNationality);
					}

					//Selecting passport Nationality
					for (int j = 13; j <= 13; j++){
						String ppCountry = (String) pax.get(i)[j];
						enterPaxDetails.selectppIssuingCountry(i, ppCountry);
					}

				}}catch (NullPointerException e){
					
				}

			}



	}

	public String getCurrentTime(){
		// Get the current time
		LocalTime currentTime = LocalTime.now();

		// Define a format for the time
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

		// Format the current time as a string
		String currentTimeString = currentTime.format(formatter);
        return currentTimeString;
    }

	private static final String SLACK_WEBHOOK_URL = "https://hooks.slack.com/services/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";

	public long timeCalculator(String time1, String time2){
		// Define two LocalTime objects representing your times
		LocalTime timeOne = LocalTime.parse(time1);
		LocalTime timeTwo = LocalTime.parse(time2);

		// Calculate the difference in seconds
		long secondsDifference = ChronoUnit.SECONDS.between(timeOne, timeTwo);
        return secondsDifference;
    }

	public void selectFromDropDown(WebDriver driver, WebElement dropdownElement, String value) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 5);

		try{


			dropdownElement.click();

			Thread.sleep(1000);


		}catch (NoSuchElementException e){

		}

		try {



			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//span[@class='mat-option-text'][text()='" + value + "']"))));

			driver.findElement(By.xpath("//span[@class='mat-option-text'][text()='" + value + "']")).click();
		}
		catch (TimeoutException | NoSuchElementException e){

			System.out.println("Desired value not available in dropdown");

		}

		}

		public String[] getPNR(String environment, String bookingReference) throws IOException {
			// Construct the API endpoint with the booking reference

			
			List<String> pnrs = null;
			pnrs = new ArrayList<>();

			String apiUrl = "";
			if (environment.equalsIgnoreCase("Preprod")) {
				apiUrl = "https://preprod-wapi.travelstart.com/website-services/api/itinerary/summary/" + bookingReference;
			} else if (environment.equalsIgnoreCase("live")) {
				apiUrl = "https://wapi.travelstart.com/website-services/api/itinerary/summary/" + bookingReference;
			} else if (environment.equalsIgnoreCase("beta")) {
				apiUrl = "https://beta-wapi.travelstart.com/website-services/api/itinerary/summary/" + bookingReference;
			}
			String pnrReference = "";
			// Bearer token for authorization
			String bearerToken = "f7904a2f-cb89-46a6-bbc9-158ad96160b2";

			if(environment.equalsIgnoreCase("Preprod")){
				bearerToken = "f7904a2f-cb89-46a6-bbc9-158ad96160b2";
			} else if (environment.equalsIgnoreCase("Live")) {
				bearerToken = "7119e7c4-e507-449c-8cac-d31ca3435f34a8c85694-f8e3-4941-b3ba-f1da94d38ce5";
			} else if (environment.equalsIgnoreCase("Beta") || environment.equalsIgnoreCase("Alpha")){
				bearerToken = "f416567b-8837-4704-b597-7937f58ab20c";
			}

			// Create an HttpClient instance
			CloseableHttpClient httpClient = HttpClients.createDefault();

			HttpGet request = new HttpGet(apiUrl);

			// Add the Authorization header with the bearer token
			request.addHeader("Authorization", "Bearer " + bearerToken);

			// Send the GET request and get the response
			HttpResponse response = httpClient.execute(request);


			// Check the response status code to determine if the cancellation was successful
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				System.out.println("Booking information retrieved for the booking reference : "+ bookingReference);

				// Parse the response body
				HttpEntity entity = response.getEntity();
				String responseBody = EntityUtils.toString(entity);
				JSONObject jsonResponse = new JSONObject(responseBody);

				// Extract the booking status
				JSONObject statusObject = jsonResponse.getJSONObject("status");
				String bookingStatus = statusObject.getString("booking");

				// If status is "CANCELLED", ignore cancellation
				if (bookingStatus.equalsIgnoreCase("CANCELLED")) {

					System.out.println("Booking is already cancelled.");

				} else {
					// If status is not "CANCELLED"m get booking ID
					try {
						JSONObject jsonObject = new JSONObject(jsonResponse.toString());
						JSONArray reservationArray = jsonObject.getJSONArray("reservations");

						for (int i = 0; i < reservationArray.length(); i++) {
							JSONObject reservation = reservationArray.getJSONObject(i);
							pnrReference = reservation.getString("pnrReference");

							System.out.println("Reservation " + (i + 1) + ":");
							System.out.println("PNR Reference: " + pnrReference);
							System.out.println();
							pnrs.add(pnrReference);

						}
					} catch (JSONException e) {
						
					}
				}
			} else {
				System.out.println("Booking information retrival failed. Status code: " + statusCode);
				// Print response body for debugging
				HttpEntity entity = response.getEntity();
				String responseBody = EntityUtils.toString(entity);
				System.out.println("Response body: " + responseBody);
				// Handle errors or exceptions
			}
			return pnrs.toArray(new String[0]);
        }

	public void cancelBooking(String environment, String bookingReference) throws IOException {
		String[] pnrs = getPNR(environment, bookingReference);

		// If PNRs are retrieved, attempt to cancel each booking
		for (String pnr : pnrs) {
			cancelBookingRequest(environment, bookingReference, pnr);
		}
	}


	private void cancelBookingRequest(String environment, String bookingReference, String pnr) throws IOException {

		String BASE_URL_PREPROD = "https://preprod-wapi.travelstart.com/website-services/api/itinerary/cancel";
		String BASE_URL_BETA = "https://beta-wapi.travelstart.com/website-services/api/itinerary/cancel";
		String BASE_URL_LIVE = "https://wapi.travelstart.com/website-services/api/itinerary/cancel";

		String BEARER_TOKEN = "f7904a2f-cb89-46a6-bbc9-158ad96160b2";

		if(environment.equalsIgnoreCase("Preprod")){
			BEARER_TOKEN = "f7904a2f-cb89-46a6-bbc9-158ad96160b2";
		} else if (environment.equalsIgnoreCase("Live")) {
			BEARER_TOKEN = "7119e7c4-e507-449c-8cac-d31ca3435f34a8c85694-f8e3-4941-b3ba-f1da94d38ce5";
		} else if (environment.equalsIgnoreCase("Beta") || environment.equalsIgnoreCase("Alpha")){
			BEARER_TOKEN = "f416567b-8837-4704-b597-7937f58ab20c";
		}


		// Construct the URL for cancellation based on environment
		String apiUrl = "";
		String cancelUrl = "";
		if (environment.equalsIgnoreCase("Beta")) {
			cancelUrl = BASE_URL_BETA + "/" + bookingReference + "/" + pnr;
		} else if (environment.equalsIgnoreCase("Preprod")) {
			cancelUrl = BASE_URL_PREPROD + "/" + bookingReference + "/" + pnr;
		} else if (environment.equalsIgnoreCase("Live")) {
			cancelUrl = BASE_URL_LIVE + "/" + bookingReference + "/" + pnr;
		}

		// Create an HttpClient instance
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// Create a PUT request to cancel the booking
		HttpPut request = new HttpPut(cancelUrl);

		// Add the Authorization header with the bearer token
		request.addHeader("Authorization", "Bearer " + BEARER_TOKEN);

		// Send the PUT request and get the response
		HttpResponse response = httpClient.execute(request);

		// Check the response status code to determine if the cancellation was successful
		int statusCode = response.getStatusLine().getStatusCode();

		// Parse the response body
		HttpEntity entity = response.getEntity();
		String responseBody = EntityUtils.toString(entity);
		JSONObject jsonResponse = new JSONObject(responseBody.toString());


		// Extract the booking status
		JSONObject statusObject = jsonResponse.getJSONObject("result");
		boolean bookingCancelStatus = statusObject.getBoolean("successful");

		if (statusCode == 200 && bookingCancelStatus) {
			System.out.println("Booking with Booking Reference: " + bookingReference + " and PNR: " + pnr + " cancelled successfully.");
		} else if (statusCode == 200 && !bookingCancelStatus) {
			response = httpClient.execute(request);
			// Parse the response body
			entity = response.getEntity();
			responseBody = EntityUtils.toString(entity);
			jsonResponse = new JSONObject(responseBody.toString());

			// Extract the booking status
			statusObject = jsonResponse.getJSONObject("result");
			bookingCancelStatus = statusObject.getBoolean("successful");
		}

		if (!bookingCancelStatus){
			System.out.println("Cancellation failed for "+ bookingReference +" with PNR "+ pnr);
		}

		// Close the HttpClient
		httpClient.close();
	}

	public boolean checkSubscriptionStatus(String username, String password){
		boolean subscriptionStatus = false;
		String requestBody = "{\n" +
				"    \"password\": \"" + password + "\",\n" +
				"    \"provider\": \"travelstart\",\n" +
				"    \"token\": null,\n" +
				"    \"userAgent\": {\n" +
				"        \"language\": \"en\",\n" +
				"        \"market\": \"za\"\n" +
				"    },\n" +
				"    \"username\": \"" + username + "\"\n" +
				"}";

		Response response = given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.post("https://preprod-tsacc.travelstart.com/api/v3/login");

		Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

		String responseBody = "";

		if (response.getStatusCode() == 200){

			responseBody = response.getBody().asString();

		}


		if (responseBody.contains("\"isTSPlusSubscriber\":true")){

			subscriptionStatus = true;

		}

        return subscriptionStatus;
    }

	public boolean checkAccountStatus(String username, String password){
		boolean accountActive = false;
		String requestBody = "{\n" +
				"    \"password\": \"" + password + "\",\n" +
				"    \"provider\": \"travelstart\",\n" +
				"    \"token\": null,\n" +
				"    \"userAgent\": {\n" +
				"        \"language\": \"en\",\n" +
				"        \"market\": \"za\"\n" +
				"    },\n" +
				"    \"username\": \"" + username + "\"\n" +
				"}";

		Response response = given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.post("https://preprod-tsacc.travelstart.com/api/v3/login");

		Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

		String responseBody = "";

		if (response.getStatusCode() == 200){

			responseBody = response.getBody().asString();


		}

		if (responseBody.contains("ACTIVE")){

			accountActive = true;

		}

		return accountActive;

	}



	public String deeplinkGeneratorSRP (String environment, String domain, String tripType, String from, String to, String depDay, String depMonth, String depYear, String retDay, String retMonth, String retYear, String adultCount, String teenCount, String childCount, String infantCount){

		String deepLink = "";

		if (environment.equalsIgnoreCase("LIVE")){

			if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Oneway")){

				deepLink = "https://www.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}
			else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Oneway")){

				deepLink = "https://www.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}
			else if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Return")){

				deepLink = "https://www.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}
			else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Return")){

				deepLink = "https://www.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}

		}

		else if (environment.equalsIgnoreCase("BETA")){

			if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Oneway")){

				deepLink = "https://beta.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}
			else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Oneway")){

				deepLink = "https://beta.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}
			else if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Return")){

				deepLink = "https://beta.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}
			else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Return")){

				deepLink = "https://beta.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}

		}

		else if (environment.equalsIgnoreCase("preprod")){

			if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Oneway")){

				deepLink = "https://preprod.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}
			else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Oneway")){

				deepLink = "https://preprod.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}
			else if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Return")){

				deepLink = "https://preprod.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}
			else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Return")){

				deepLink = "https://preprod.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}

		}

		else if (environment.equalsIgnoreCase("alpha")){

			if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Oneway")){

				deepLink = "https://alpha.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}
			else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Oneway")){

				deepLink = "https://alpha.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}
			else if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Return")){

				deepLink = "https://alpha.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}
			else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Return")){

				deepLink = "https://alpha.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

			}


		}

        return deepLink;
    }



	public static String generateCID(){

		String CID = Method.generateRandomFileName();

		int length = CID.length();
		CID = CID.substring(length / 2);
		CID = "automation"+CID;

        return CID;



    }



	public WebDriver launchBrowser( WebDriver driver, String browser){

		if (browser.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("Edge")) {
			driver = new EdgeDriver();
		}

        return driver;
    }

	public boolean isBundled(String domain, String tripType, String origin, String destination){

		boolean isBundled = true;
		boolean isOriginDomestic = false;
		boolean isDestinationDomestic = false;

		String ZAAirports[] = {"JNB","CPT","DUR","GRG","PLZ","UTN","ELS","AGZ","ALJ","ADY","BFN","AAM"};
		String NGAirports[] = {"ABV","LOS","AKR","ABB","BCU","BNI","CBQ","ENU","GMO","IBA","ILR","JOS"};
        //param =  origin

		if (domain.equalsIgnoreCase("ZA")){
			
			for (int i = 0; i < ZAAirports.length; i++){
				
				String airport = ZAAirports[i];
				
				if (airport.equalsIgnoreCase(origin)){

					isOriginDomestic = true;
					break;

				} else {
					isOriginDomestic = false;
				}
				
			}

			for (int i = 0; i < ZAAirports.length; i++){

				String airport = ZAAirports[i];

				if (airport.equalsIgnoreCase(destination)){

					isDestinationDomestic = true;
					break;

				} else {

					isDestinationDomestic = false;

				}

			}

		} else if (domain.equalsIgnoreCase("NG")) {

			for (int i = 0; i < NGAirports.length; i++){

				String airport = NGAirports[i];

				if (airport.equalsIgnoreCase(origin)){
					isOriginDomestic = true;
				} else {
					isOriginDomestic = false;
				}

				if (isOriginDomestic) break;

			}

			for (int i = 0; i < NGAirports.length; i++){

				String airport = NGAirports[i];

				if (airport.equalsIgnoreCase(origin)){
					isDestinationDomestic = true;
				} else {
					isDestinationDomestic = false;
				}

				if (isDestinationDomestic) break;

			}
		}


		if (tripType.equalsIgnoreCase("Oneway")) {

			isBundled = true;

		}

		else if (tripType.equalsIgnoreCase("Return")){

			if (isOriginDomestic && isDestinationDomestic){

				isBundled = false;

			}else{

				isBundled = true;
			}


		}


        return isBundled;
    }


	public String getBaseURL (String environment, String domain) throws IOException {

		String baseURL = "";

		// Setting up URL for ZA domain
		if (domain.equalsIgnoreCase("ZA")){

			environment = environment.toUpperCase();

			switch (environment) {

				case "LIVE" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 4, 1);
				case "BETA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 6, 1));
				case "PREPROD" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 8, 1));
				case "ALPHA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 10, 1));

				default -> System.out.println("Invalid environment name");


			}
		}
		// Setting up URL for NG domain
		else if (domain.equalsIgnoreCase("NG")) {

			switch (environment) {

				case "LIVE" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 5, 1));
				case "BETA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 7, 1));
				case "PREPROD" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 9, 1));
				case "ALPHA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 11, 1));

				default -> System.out.println("Invalid envinorment name");

			}
		}
		// Setting FS META
		else if (domain.equalsIgnoreCase("FS")) {

			switch (environment) {

				case "LIVE" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 12, 1));
				case "BETA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 13, 1));
				case "PREPROD" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 14, 1));
				case "ALPHA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 15, 1));

				default -> System.out.println("Invalid envinorment name");

			}
		}



        return baseURL;

    }


	public String getBaseURL (String environment, String domain, String cpy_source) throws IOException {

		String baseURL = "";

		environment = environment.toUpperCase();

		// Setting up URL for ZA domain
		if (domain.equalsIgnoreCase("ZA")){



			switch (environment) {

				case "LIVE" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 4, 1);
				case "BETA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 6, 1));
				case "PREPROD" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 8, 1));
				case "ALPHA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 10, 1));

				default -> System.out.println("Invalid environment name");


			}
		}
		// Setting up URL for NG domain
		else if (domain.equalsIgnoreCase("NG")) {

			switch (environment) {

				case "LIVE" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 5, 1));
				case "BETA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 7, 1));
				case "PREPROD" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 9, 1));
				case "ALPHA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 11, 1));

				default -> System.out.println("Invalid envinorment name");

			}
		}
		// Setting FS META
		else if (domain.equalsIgnoreCase("FS")) {

			switch (environment) {

				case "LIVE" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 12, 1));
				case "BETA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 13, 1));
				case "PREPROD" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 14, 1));
				case "ALPHA" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 15, 1));

				default -> System.out.println("Invalid envinorment name");

			}
		}

		if (cpy_source.equalsIgnoreCase("tszaweb")|| cpy_source.equalsIgnoreCase("tsngweb")|| cpy_source.isEmpty() || cpy_source.isBlank() || cpy_source.equals("-")){

			baseURL = baseURL;

		}
		else {
			baseURL = baseURL+"?cpysource="+cpy_source;
		}


		return baseURL;


	}

	public boolean verifyElementAvailability(By targetElementLocator, String targetElementName) {

		// Asserting Traveller Page
		WebElement targetElement = null;

		long duration = 5;

		WebDriverWait wait = new WebDriverWait(driver, duration);

		try {

			// Waits until flight details page is loaded for maximum 60 seconds
			wait.until(ExpectedConditions.visibilityOfElementLocated(targetElementLocator));
			targetElement = driver.findElement(targetElementLocator);

		}
		catch (NoSuchElementException | TimeoutException e) {

		}

		// Initializing boolean variable to asser flight details page
		boolean istargetElementLoaded = false;

		try{

			// Assigning boolean value to assertion variable if flight details page is available
			istargetElementLoaded = targetElement.isDisplayed();

		}catch (NullPointerException e){


		}
		if (istargetElementLoaded){

			System.out.println( targetElementName +" loaded on frontend");

		}

		// To return test failure information into test result document if flight details page is not loaded
		else {

			// Takes screenshot if flight details page is not loaded
			System.out.println( targetElementName +" not loaded on frontend, screenshot saved in belows path");
			m.takeScreenshot(driver, Paths.screenshotFolder, Paths.screenshotFolder);


		}


		return istargetElementLoaded;
	}


	public boolean verifyRedirection(WebDriver driver, By targetElementLocator, String targetElementName){

		// Asserting Traveller Page
		WebElement targetElement = null;

		long duration = 60;

		WebDriverWait wait = new WebDriverWait(driver, duration);

		try {

			// Waits until flight details page is loaded for maximum 60 seconds
			wait.until(ExpectedConditions.visibilityOfElementLocated(targetElementLocator));
			targetElement = driver.findElement(targetElementLocator);

		}
		catch (NoSuchElementException | TimeoutException e) {

		}

		// Initializing boolean variable to asser flight details page
		boolean istargetElementLoaded = false;

		try{

			// Assigning boolean value to assertion variable if flight details page is available
			istargetElementLoaded = targetElement.isDisplayed();

		}catch (NullPointerException e){


		}
		if (istargetElementLoaded){

			System.out.println( targetElementName +" loaded on frontend");

		}

		// To return test failure information into test result document if flight details page is not loaded
		else {

			// Takes screenshot if flight details page is not loaded
			System.out.println( targetElementName +" not loaded on frontend, screenshot saved in belows path");
			m.takeScreenshot(driver, Paths.screenshotFolder, Paths.screenshotFolder);


		}


		return istargetElementLoaded;



	}

	public boolean verifyRedirection(WebDriver driver, By desiredElementLocator, By errorElementLocator) throws InterruptedException {

		boolean isFlowWorking = false;
		boolean errorOccured = false;

		WebElement desiredElement;

		WebElement errorElement;

		int wait = 3000;

		for (int i = 0; i < 20; i++){

			Thread.sleep(wait);

			try{

				desiredElement = driver.findElement(desiredElementLocator);

				if (desiredElement.isDisplayed() && desiredElement.isEnabled()){

					isFlowWorking = true;

				}

			}
			catch (NoSuchElementException | NullPointerException e){

				try{

					errorElement = driver.findElement(errorElementLocator);

					if (errorElement.isDisplayed()){

						isFlowWorking = false;
						errorOccured = true;

					}

				}catch (NoSuchElementException | NullPointerException e1){


				}

			}

			if (isFlowWorking) break;

			if (errorOccured) break;

		}


        return isFlowWorking;
    }


	public boolean assertAppLaunch(WebDriver driver, String domain, String environment){

		boolean isAppLaunched = false;

		String URL =driver.getCurrentUrl();

		String expected = "";

		if (domain.equalsIgnoreCase("ZA") && environment.equalsIgnoreCase("Live")){

			expected = "https://www.travelstart.co.za";

		}

		if (URL.equalsIgnoreCase(expected)){
			isAppLaunched = true;
		}

        return false;
    }


	public String getUUID(String environment, String bookingReference) throws IOException {

		String UUID = "";

		String apiUrl = "";

		if (environment.equalsIgnoreCase("Preprod")) {
			apiUrl = "https://preprod-wapi.travelstart.com/website-services/api/itinerary/summary/" + bookingReference;
		} else if (environment.equalsIgnoreCase("live")) {
			apiUrl = "https://wapi.travelstart.com/website-services/api/itinerary/summary/" + bookingReference;
		} else if (environment.equalsIgnoreCase("beta")) {
			apiUrl = "https://beta-wapi.travelstart.com/website-services/api/itinerary/summary/" + bookingReference;
		}

		// Bearer token for authorization
		String bearerToken = "f7904a2f-cb89-46a6-bbc9-158ad96160b2";

		if(environment.equalsIgnoreCase("Preprod")){
			bearerToken = "f7904a2f-cb89-46a6-bbc9-158ad96160b2";
		} else if (environment.equalsIgnoreCase("Live")) {
			bearerToken = "7119e7c4-e507-449c-8cac-d31ca3435f34a8c85694-f8e3-4941-b3ba-f1da94d38ce5";
		} else if (environment.equalsIgnoreCase("Beta") || environment.equalsIgnoreCase("Alpha")){
			bearerToken = "f416567b-8837-4704-b597-7937f58ab20c";
		}

		// Create an HttpClient instance
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpGet request = new HttpGet(apiUrl);

		// Add the Authorization header with the bearer token
		request.addHeader("Authorization", "Bearer " + bearerToken);

		// Send the GET request and get the response
		HttpResponse response = httpClient.execute(request);


		// Check the response status code to determine if the cancellation was successful
		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {

			// Parse the response body

			HttpEntity entity = response.getEntity();

			String responseBody = EntityUtils.toString(entity);

			JSONObject jsonResponse = new JSONObject(responseBody);

			UUID = jsonResponse.getString("uuid");


		}else {

			System.out.println("UUID Retrieval failed. Itinerary API returned status code: "+ statusCode);

		}


			return UUID;
    }

	public String getCpySource(String environment, String bookingReference) throws IOException {
		String cpySource = "";

		JSONObject bookingSummaryResponse = this.getBookingSummaryResponse(environment, bookingReference);
		cpySource = bookingSummaryResponse.getString("companyCode");




        return cpySource;
    }

	public JSONObject getBookingSummaryResponse(String environment, String bookingReference) throws IOException {

		JSONObject bookingSummaryResponse = null;

			String apiUrl = "";

			if (environment.equalsIgnoreCase("Preprod")) {
				apiUrl = "https://preprod-wapi.travelstart.com/website-services/api/itinerary/summary/" + bookingReference;
			} else if (environment.equalsIgnoreCase("live")) {
				apiUrl = "https://wapi.travelstart.com/website-services/api/itinerary/summary/" + bookingReference;
			} else if (environment.equalsIgnoreCase("beta")) {
				apiUrl = "https://beta-wapi.travelstart.com/website-services/api/itinerary/summary/" + bookingReference;
			}

			// Bearer token for authorization
			String bearerToken = "f7904a2f-cb89-46a6-bbc9-158ad96160b2";

			if(environment.equalsIgnoreCase("Preprod")){
				bearerToken = "f7904a2f-cb89-46a6-bbc9-158ad96160b2";
			} else if (environment.equalsIgnoreCase("Live")) {
				bearerToken = "7119e7c4-e507-449c-8cac-d31ca3435f34a8c85694-f8e3-4941-b3ba-f1da94d38ce5";
			} else if (environment.equalsIgnoreCase("Beta") || environment.equalsIgnoreCase("Alpha")){
				bearerToken = "f416567b-8837-4704-b597-7937f58ab20c";
			}

			// Create an HttpClient instance
			CloseableHttpClient httpClient = HttpClients.createDefault();

			HttpGet request = new HttpGet(apiUrl);

			// Add the Authorization header with the bearer token
			request.addHeader("Authorization", "Bearer " + bearerToken);

			// Send the GET request and get the response
			HttpResponse response = httpClient.execute(request);


			// Check the response status code to determine if the cancellation was successful
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {

				// Parse the response body

				HttpEntity entity = response.getEntity();

				String responseBody = EntityUtils.toString(entity);

				bookingSummaryResponse = new JSONObject(responseBody);


			}else {

				System.out.println("Booking response Retrieval failed. Itinerary API returned status code: "+ statusCode);

			}

			return bookingSummaryResponse;


	}

	public String getInvID(String environment, String bookingReference) throws IOException {

		String invID = "";

		String apiUrl = "";

		if (environment.equalsIgnoreCase("Preprod")) {
			apiUrl = "https://preprod-wapi.travelstart.com/website-services/api/itinerary/summary/" + bookingReference;
		} else if (environment.equalsIgnoreCase("live")) {
			apiUrl = "https://wapi.travelstart.com/website-services/api/itinerary/summary/" + bookingReference;
		} else if (environment.equalsIgnoreCase("beta")) {
			apiUrl = "https://beta-wapi.travelstart.com/website-services/api/itinerary/summary/" + bookingReference;
		}

		// Bearer token for authorization
		String bearerToken = "f7904a2f-cb89-46a6-bbc9-158ad96160b2";

		if(environment.equalsIgnoreCase("Preprod")){
			bearerToken = "f7904a2f-cb89-46a6-bbc9-158ad96160b2";
		} else if (environment.equalsIgnoreCase("Live")) {
			bearerToken = "7119e7c4-e507-449c-8cac-d31ca3435f34a8c85694-f8e3-4941-b3ba-f1da94d38ce5";
		} else if (environment.equalsIgnoreCase("Beta") || environment.equalsIgnoreCase("Alpha")){
			bearerToken = "f416567b-8837-4704-b597-7937f58ab20c";
		}

		// Create an HttpClient instance
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpGet request = new HttpGet(apiUrl);

		// Add the Authorization header with the bearer token
		request.addHeader("Authorization", "Bearer " + bearerToken);

		// Send the GET request and get the response
		HttpResponse response = httpClient.execute(request);


		// Check the response status code to determine if the cancellation was successful
		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {

			// Parse the response body

			HttpEntity entity = response.getEntity();

			String responseBody = EntityUtils.toString(entity);

			JSONObject jsonResponse = new JSONObject(responseBody);

			JSONArray invoiceListArray = jsonResponse.getJSONArray("invoiceList");

			JSONObject invoiceListBody = invoiceListArray.getJSONObject(0);

			invID = String.valueOf(invoiceListBody.getBigInteger("invoiceId"));



		}else {

			System.out.println("UUID Retrieval failed. Itinerary API returned status code: "+ statusCode);

		}

        return invID;
    }

	public String generatePaymentLink(String environment, String domain, String bookingReference) throws IOException {

		String paymentLink = "";

		String UUID = m.getUUID(environment, bookingReference);
		String invID = m.getInvID(environment, bookingReference);
		String cpySource = m.getCpySource(environment, bookingReference);
		String CID = Method.generateCID();

		String baseURL = "https://www.travelstart.co.za/find-itinerary?";


		if (domain.equalsIgnoreCase("ZA")) {

			if (environment.equalsIgnoreCase("Live")) {
				baseURL = "https://www.travelstart.co.za/find-itinerary?";
			} else if (environment.equalsIgnoreCase("Preprod")) {
				baseURL = "https://preprod.travelstart.co.za/find-itinerary?";
			} else if (environment.equalsIgnoreCase("Beta")) {
				baseURL = "https://beta.travelstart.co.za/find-itinerary?";
			} else if (environment.equalsIgnoreCase("Alpha")) {
				baseURL = "https://alpha.travelstart.co.za/find-itinerary?";
			}

		} else if (domain.equalsIgnoreCase("NG")) {

			if (environment.equalsIgnoreCase("Live")) {
				baseURL = "https://www.travelstart.com.ng/find-itinerary?";
			} else if (environment.equalsIgnoreCase("Preprod")) {
				baseURL = "https://preprod.travelstart.com.ng/find-itinerary?";
			} else if (environment.equalsIgnoreCase("Beta")) {
				baseURL = "https://beta.travelstart.com.ng/find-itinerary?";
			} else if (environment.equalsIgnoreCase("Alpha")) {
				baseURL = "https://alpha.travelstart.com.ng/find-itinerary?";
			}

		}

		paymentLink = baseURL+"uuid="+UUID+"&invid="+invID+"&cpysource="+cpySource+"&utm_source=transactional&utm_medium=email&utm_campaign=booking-confirmation&utm_content=payment-link"+"&correlation_id="+CID;


		return paymentLink;
    }


	public double amountToDouble(String value){

		String currency = "";

		boolean hasCurrency = false;
		boolean hasComma = false;

		if (value.contains("₦")){
			currency = "₦";
			hasCurrency = true;
		} else if (value.contains("R")) {
			currency = "R";
			hasCurrency = true;
		}

		if (hasCurrency){

			value = value.replace(currency, "");

		}

		if (value.contains(",")) {

			hasComma = true;

		}

		if (hasComma){

			value = value.replace(",", "");

		}

		if (value.contains("+")){

			value = value.replace("+", "");

		}

		if (value.contains("-")){

			value = value.replace("-", "");

		}


		// Convert to int

		double amount = Double.parseDouble(value);

        return amount;

    }

}