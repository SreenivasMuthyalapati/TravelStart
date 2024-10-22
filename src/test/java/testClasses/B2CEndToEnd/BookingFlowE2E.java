package testClasses.B2CEndToEnd;

import configs.dataPaths;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import testmethods.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingFlowE2E {


    /*Booking Validations
    :

    1: Web app launch assertion
    2: Search result assertion
    4: Currency validation
    8: Traveller page assertion
    9: (Login user) Mobile number and mail ID prepopulate
    10: Saved travellers on traveller details page
    11: Price match between SRP and traveller page
    12: Flight numbers match between SRP and traveller page
    13: Departure & return dates match between SRP and traveller page
    14: Flight timings match between SRP and traveller page
    15: Traveller details fields validation
    16: Checked baggage reprice
    17: Checked baggage addition validation
    18: Checked baggage price addition
    19: Checked baggage
    20: Check in baggage addition
    21: Check in baggage price into breakdown
    22: Whatsapp price in breakdown
    23: Meals selection (If offered)
    24: Meals price into breakdown
    25: Frequent Flyer Number
    26: Seat map assertion
    27: Seats selection
    28: Seats price
    29: Seats price in breakdown validation
    30: Addons assertion
    31: Addons selections
    32: Addons price into breakdown validation
    33: Payment page assertion
    34: Payment methods validation
    35: Payment fields validation
    36: Booking reference validation
    38: Voucher application
    39: Voucher amount into breakdown
    40: price calculation validation on payment page
    41: Booking fee
    42: VAT application
    43: Payment split validation
    44: 3ds redirection(cc/dc)
    45: (Login user)Saved cards prepopulate
    46: (Login user)Saved card payment redirection
    47: Paystack / IPAY redirection
    48: Paystack / IPAY failure booking confirmation
    49: EFT booking
    50: Booking confirmation assertion
    51: Price calculation on booking confirmation
    52: Flight numbers validation on booking confirmation
    53: Invoice validation
    54: Passengers details validation on booking confirmation
    55: Selected seats display on booking confirmation
    56: Payment method details on booking confirmation
    57: Booking details in "My Bookings"

    */

    // Class variables and instances
    public static ExcelUtils excelUtils = new ExcelUtils();
    public static SendEmail sendEmail = new SendEmail();
    public static Method method = new Method();
    public static HomePageMethods homePageMethods = new HomePageMethods();
    public static SRPMethods srpMethods = new SRPMethods();
    public static TravellerDetailsPageMethods travellerDetailsPageMethods = new TravellerDetailsPageMethods();
    public static String dataPath = configs.dataPaths.B2CBookingE2ETestData;
    public static String environment;
    public static String browser;
    public static WebDriver driver;
    public static boolean isBundled = false;
    // Validation and Assertion variables

    private String testCaseSummary = "";

    // Extracting environment from test data sheet
    static {
        try {
            environment = excelUtils.readDataFromExcel(dataPaths.URLs, "URL's", 1, 1);
            browser = excelUtils.readDataFromExcel(dataPaths.URLs, "URL's", 0, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public BookingFlowE2E() throws IOException {
    }

    @BeforeTest
    public void setupTest() throws IOException {
        // Create a new Excel report for each test run
        ExcelUtils.createExcelReport();

    }


    @AfterMethod
    public void close(ITestResult result) {
        if (driver != null && result.getStatus() == ITestResult.FAILURE) {
            // If test fails and driver is not null, print the correlation ID
            System.out.println("Test Failed! Correlation ID: " + method.getCID(driver));
        }
        if (driver != null) {

            driver.quit();

        }
    }

    static String testReportPath = "";

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        // Save the Excel report after the test run
        testReportPath = ExcelUtils.saveExcelReport();

        Thread.sleep(1000);

        // Sends email with test report
        //sendEmail.sendEmailWithTemplateAndJson(testReportPath, "B2C Booking Flow End to End Automation Test", 2, 6, 6, 0, 0);

    }



    @DataProvider(name = "TestCase")
    public Object[][] getTestCase() throws IOException {
        List<Object[]> testCase = new ArrayList<>();

        // Extracting all test data from test cases in test data sheet
        int totalPaxCount = excelUtils.getRowCount(dataPath, "Booking Scenarios");

        for (int i = 2; i < totalPaxCount; i++) {

            System.out.println("Yes");

            String testCaseNumber = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 0);
            String shouldRun = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 1);
            String domain = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 2);
            String cpy_source = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 3);
            String tripType = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 4);
            String origin = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 5);
            String destination = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 6);
            String departureDate = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 7);
            String departureMonth = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 8);
            String returnDate = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 9);
            String returnMonth = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 10);
            String adultCount = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 11);
            String youngAdultCount = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 12);
            String childCount = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 13);
            String infantCount = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 14);
            String departureAirline = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 15);
            String returnAirline = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 16);
            String mailID = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 17);
            String mobileNumber = (excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 18));
            String title = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 19);
            String firstName = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 20);
            String middleName = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 21);
            String lastName = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 22);
            String dateOfBirth = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 23);
            String monthOfBirth = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 24);
            String yearOfBirth = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 25);
            String passPortNumber = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 26);
            String dateOfPassportExpiry = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 27);
            String monthOfPassportExpiry = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 28);
            String yearOfPassportExpiry = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 29);
            String passPortNationality = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 30);
            String passPortIssuingCountry = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 31);
            String paymentMethod = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 32);
            String bankNameEFT = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 33);
            String isLoggedInUser = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 34);
            String isToBeCancelled = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 35);

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
                    paymentMethod,
                    bankNameEFT,
                    isLoggedInUser,
                    isToBeCancelled});
        }
        return testCase.toArray(new Object[0][]);
    }



    @Test(dataProvider = "TestCase")
    public void bookingFlow(String testCaseNumber, String shouldRun, String domain, String cpy_source, String tripType, String origin, String destination, String departureDate, String departureMonth, String returnDate, String returnMonth, String adultCount, String youngAdultCount, String childCount, String infantCount, String departureAirline, String returnAirline, String mailID, String mobileNumber, String title, String firstName, String middleName, String lastName, String dateOfBirth, String monthOfBirth, String yearOfBirth, String passPortNumber, String dateOfPassportExpiry, String monthOfPassportExpiry, String yearOfPassportExpiry, String passPortNationality, String passPortIssuingCountry, String paymentMethod, String bankNameEFT, String isLoggedInUser, String isToBeCancelled) throws IOException, InterruptedException {

        System.out.println(testCaseNumber+" executed");

        if (!shouldRun.equalsIgnoreCase("Yes")) {
            System.out.println("Skipped");
            throw new SkipException("Test is skipped as this test case " + testCaseNumber + " is not approved to run");

        }



        driver = method.launchBrowser(driver, browser);
        // Maximize window
        driver.manage().window().maximize();

        //Printing the test case number
        System.out.println(testCaseNumber + " Executed");
        System.out.println(" ");
        System.out.println("===========================================================================");

        isBundled = method.isBundled(domain, tripType, origin, destination);
        String CID = "";


        String baseURL = method.getBaseURL(environment, domain, cpy_source);
        // Launch URL
        driver.get(baseURL);
        Thread.sleep(300);

        //-------------------------------------------------------------------------------------------------------------//
        // Test case 1:
        testCaseSummary = "Verify the web applicaion launched or not";

        boolean isAppLaunched = false;

        isAppLaunched = homePageMethods.assertHomePageRedirection(driver);

        ExcelUtils.writeTestReport(testCaseNumber, testCaseSummary, isAppLaunched ? "Pass" : "Fail");

        Assert.assertTrue(isAppLaunched, "Test case failed: "+testCaseSummary);
        if (isAppLaunched){

            System.out.println("Test Case Summary: "+testCaseSummary);
            System.out.println("Test Status: Pass");
            System.out.println("===========================================================================");
        }

        //-------------------------------------------------------------------------------------------------------------//
        // Test case 2:
        testCaseSummary = "Verify search result is loading after search attempt";

        homePageMethods.makeFlightSearch(driver, tripType, origin, destination, departureMonth, departureDate, returnMonth, returnDate, adultCount, youngAdultCount, childCount, infantCount);

        boolean isSearchLoaded = srpMethods.isSRPLoaded(driver);
        CID = method.getCID(driver);

        ExcelUtils.writeTestReport(testCaseNumber, testCaseSummary, isSearchLoaded ? "Pass" : "Fail");

        Assert.assertTrue(isSearchLoaded, "Test case failed: "+testCaseSummary+"," +" correlation ID: "+CID);


        if (isSearchLoaded){

            System.out.println("Test Case Summary: "+testCaseSummary);
            System.out.println("Test Status: Pass");
            System.out.println("===========================================================================");

        }

        //-------------------------------------------------------------------------------------------------------------//
        //Test case 3:
        testCaseSummary = "Verify currency is valid or not";

        boolean isCurrencyValid = srpMethods.isCurrencyValid(driver, domain, isBundled);

        ExcelUtils.writeTestReport(testCaseNumber, testCaseSummary, isCurrencyValid ? "Pass" : "Fail");

        Assert.assertTrue(isCurrencyValid, "Test case failed: "+testCaseSummary+"," +" correlation ID: "+CID);


        if (isCurrencyValid){

            System.out.println("Test Case Summary: "+testCaseSummary);
            System.out.println("Test Status: Pass");
            System.out.println("===========================================================================");

        }

        //Test case 4
        testCaseNumber = "Verify functionality of stops filter";

        

}


}
