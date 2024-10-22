package testClasses.Booking;

import configs.dataPaths;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import pageObjects.*;
import testmethods.ExcelUtils;
import testmethods.Method;
import testmethods.PaymentPageMethods;
import testmethods.TSMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingFlow {

    static ExcelUtils excelUtils = new ExcelUtils();
    static XSSFWorkbook workbook;
    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = configs.dataPaths.dataPath;
    static String environment;
    static String browser;
    static String outputExcel = configs.dataPaths.excelOutputPath;
    static String baseURL;
    static String runTime;
    static String screenShotPath ="";

    // Extracting environment from test data sheet
    static {
        try {
            environment = excelUtils.readDataFromExcel(dataPaths.URLs, "URL's", 1, 1);
            browser = excelUtils.readDataFromExcel(dataPaths.URLs, "URL's", 0, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public BookingFlow() throws IOException {
    }


    @AfterMethod
    public void close(ITestResult result) {
        if (driver != null && result.getStatus() == ITestResult.FAILURE) {
            // If test fails and driver is not null, print the correlation ID
            System.out.println("Test Failed! Correlation ID: " + m.getCID(driver));
        }
        if (driver != null) {

            driver.quit();

        }
    }




    @DataProvider(name = "TestCase")
    public Object[][] getTestCase() throws IOException {
        List<Object[]> testCase = new ArrayList<>();

        // Extracting all test data from test cases in test data sheet
        int totalPaxCount = excelUtils.getRowCount(dataPath, "Booking Test Cases");

        for (int i = 2; i < totalPaxCount; i++) {

            String testCaseNumber = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 0);
            String shouldRun = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 1);
            String domain = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 2);
            String cpy_source = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 3);
            String tripType = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 4);
            String origin = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 5);
            String destination = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 6);
            String departureDate = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 7);
            String departureMonth = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 8);
            String returnDate = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 9);
            String returnMonth = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 10);
            String adultCount = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 11);
            String youngAdultCount = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 12);
            String childCount = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 13);
            String infantCount = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 14);
            String isBundled = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 15);
            String departureAirline = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 16);
            String returnAirline = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 17);
            String mailID = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 18);
            String mobileNumber = (excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 19));
            String title = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 20);
            String firstName = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 21);
            String middleName = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 22);
            String lastName = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 23);
            String dateOfBirth = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 24);
            String monthOfBirth = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 25);
            String yearOfBirth = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 26);
            String passPortNumber = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 27);
            String dateOfPassportExpiry = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 28);
            String monthOfPassportExpiry = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 29);
            String yearOfPassportExpiry = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 30);
            String passPortNationality = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 31);
            String passPortIssuingCountry = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 32);
            String addBaggage = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 33);
            String addFlexi = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 34);
            String whatsapp = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 35);
            String addSeats = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 36);
            String paymentMethod = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 37);
            String bankNameEFT = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 38);
            String isLoggedInUser = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 39);
            String isToBeCancelled = excelUtils.readDataFromExcel(dataPath, "Booking Test Cases", i, 40);

            testCase.add(new Object[]{testCaseNumber,
                    shouldRun,
                    domain,
                    cpy_source,
                    tripType,
                    origin,
                    destination,
                    departureDate,
                    departureMonth,
                    returnDate,
                    returnMonth,
                    adultCount,
                    youngAdultCount,
                    childCount,
                    infantCount,
                    isBundled,
                    departureAirline,
                    returnAirline,
                    mailID,
                    mobileNumber,
                    title,
                    firstName,
                    middleName,
                    lastName,
                    dateOfBirth,
                    monthOfBirth,
                    yearOfBirth,
                    passPortNumber,
                    dateOfPassportExpiry,
                    monthOfPassportExpiry,
                    yearOfPassportExpiry,
                    passPortNationality,
                    passPortIssuingCountry,
                    addBaggage,
                    addFlexi,
                    whatsapp,
                    addSeats,
                    paymentMethod,
                    bankNameEFT,
                    isLoggedInUser,
                    isToBeCancelled});
        }
        return testCase.toArray(new Object[0][]);
    }



    @Test(dataProvider = "TestCase")
    public void bookingFlow(String testCaseNumber, String shouldRun, String domain, String cpy_source, String tripType, String origin, String destination, String departureDate, String departureMonth, String returnDate, String returnMonth, String adultCount, String youngAdultCount, String childCount, String infantCount,String isBundled, String departureAirline, String returnAirline, String mailID, String mobileNumber, String title, String firstName, String middleName, String lastName, String dateOfBirth, String monthOfBirth, String yearOfBirth, String passPortNumber, String dateOfPassportExpiry, String monthOfPassportExpiry, String yearOfPassportExpiry, String passPortNationality, String passPortIssuingCountry, String addBaggage, String addFlexi, String whatsApp, String addSeats, String paymentMethod, String bankNameEFT, String isLoggedInUser, String isToBeCancelled) throws IOException, InterruptedException {

        runTime = m.getCurrentTime();
        String testStatus;


        if (!shouldRun.equalsIgnoreCase("Yes")) {
            throw new SkipException("Test is skipped as this test case " + testCaseNumber + " is not approved to run");
        }

        if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("Edge")) {
            driver = new EdgeDriver();
        }

        // Maximize window
        driver.manage().window().maximize();

        //Printing the test case number
        System.out.println(testCaseNumber + " Executed");

        System.out.println(environment);


        TSMethods bookingFlowMethods = new TSMethods(driver);

        baseURL = m.getBaseURL(environment, domain, cpy_source);


        String CID = Method.generateCID();

        System.out.println("URL: "+baseURL);

        // Launch URL
        driver.get(baseURL);
        Thread.sleep(300);


        //accept all cookies
        driver.manage().deleteAllCookies();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {

        }

        Thread.sleep(2000);




        if (isLoggedInUser.equalsIgnoreCase("Yes")) {

            bookingFlowMethods.login("Sreenivasulu@travelstart.com", "Test@123");

        }

        bookingFlowMethods.searchFlight(testCaseNumber, tripType, origin, destination, departureDate, departureMonth, returnDate, returnMonth,  adultCount, youngAdultCount, childCount, infantCount);

        bookingFlowMethods.SelectAirline(testCaseNumber, tripType, isBundled, departureAirline, returnAirline);
        bookingFlowMethods.clickBook(testCaseNumber, tripType, isBundled);
        bookingFlowMethods.enterPaxDetails(isLoggedInUser, testCaseNumber, tripType, adultCount, youngAdultCount, childCount, infantCount, departureAirline, returnAirline, mailID, mobileNumber, title, firstName, middleName, lastName, dateOfBirth, monthOfBirth, yearOfBirth, passPortNumber, dateOfPassportExpiry, monthOfPassportExpiry, yearOfPassportExpiry, passPortNationality, passPortIssuingCountry, addBaggage, whatsApp);
        bookingFlowMethods.add_seats(addSeats);
        System.out.println(bookingFlowMethods.getPriceBreakdown((FlightPage.fareBreakdownTables)));
        bookingFlowMethods.add_Addons(domain, addFlexi);
        Thread.sleep(2000);
        //System.out.println(bookingFlowMethods.getPriceBreakdown((FlightPage.fareBreakdownTables)));
        PaymentPageMethods paymentPageMethods = new PaymentPageMethods();
        String bookingRef = paymentPageMethods.getBookingReference(driver);
        bookingFlowMethods.paymentAndBooking(environment, testCaseNumber, domain, paymentMethod, bankNameEFT, isToBeCancelled);
        try {
            m.cancelBooking(environment, bookingRef);
        } catch (Exception e){

        }

    }


}