package testmethods;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.io.FileUtils;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.SkipException;
import org.apache.http.HttpHeaders;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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


import static io.restassured.RestAssured.given;
import static pageObjects.Paths.dataPath;

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
			e.printStackTrace();
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

	public void invokeBrowser(String browserName, WebDriver driver){
		if (browserName.equalsIgnoreCase("Chrome")){
			WebDriverManager.chromedriver().browserVersion("123.0.6312.86").setup();
			driver = new ChromeDriver();
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

		int numberOfMonthsDifference = monthMap.get(departureMonth.toUpperCase()) - monthMap.get(currentMonthInString.toUpperCase());

		if (numberOfMonthsDifference > 0){
		for (int a = 0; a < numberOfMonthsDifference; a++) {
			Thread.sleep(200);

			driver.findElement(HomePage.nextMonth).click();
		}} else if (numberOfMonthsDifference < 0) {
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
					if (airline1.equalsIgnoreCase("FA") && airline2.equalsIgnoreCase("FA")) {
						lastName = "Test";
					}
					lastName = (String) pax.get(i)[j];
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
					e.printStackTrace();
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
					e.printStackTrace();
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

	public void sendNotification(String testName, String failureReason) {
		try {
			URL url = new URL(SLACK_WEBHOOK_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String message = "{\"text\":\"Test failed: " + testName + "\\nReason: " + failureReason + "\"}";

			OutputStream os = conn.getOutputStream();
			os.write(message.getBytes());
			os.flush();

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				System.out.println("Slack notification sent successfully.");
			} else {
				System.out.println("Failed to send Slack notification. Response code: " + conn.getResponseCode());
			}
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occurred while sending Slack notification: " + e.getMessage());
		}
	}

	public long timeCalculator(String time1, String time2){
		// Define two LocalTime objects representing your times
		LocalTime timeOne = LocalTime.parse(time1);
		LocalTime timeTwo = LocalTime.parse(time2);

		// Calculate the difference in seconds
		long secondsDifference = ChronoUnit.SECONDS.between(timeOne, timeTwo);
        return secondsDifference;
    }

}

