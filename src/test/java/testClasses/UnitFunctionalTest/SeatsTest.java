package testClasses.UnitFunctionalTest;

import configs.dataPaths;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pageObjects.AddOnsPage;
import pageObjects.PaymentPage;
import testMethods.*;
import utils.ExcelTestReport;
import utils.HtmlTestReport;
import utils.TestReport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatsTest {

    static XSSFWorkbook workbook;
    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = dataPaths.seatsTest;
    static String environment;
    static String browser;
    static String baseURL;
    static String runTime;
    static String screenShotPath ="";
    static ExcelUtils excelUtils = new ExcelUtils();
    static SeatsPageMethods seatsPageMethods = new SeatsPageMethods();
    static AddOnsPageMethods addOnsPageMethods = new AddOnsPageMethods();

    static SoftAssert softAssert = new SoftAssert();

    static TestReport testReport = new TestReport();
    static HtmlTestReport htmlTestReport = new HtmlTestReport();
    static ExcelTestReport excelTestReport = new ExcelTestReport();

    static List<String[]> testResultData = new ArrayList<>();
    static List<String> attachments = new ArrayList<>();

    static List<String[]> testCases = new ArrayList<>();


    // Extracting environment from test data sheet
    static {
        try {
            environment = excelUtils.readDataFromExcel(dataPaths.URLs, "URL's", 1, 1);
            browser = excelUtils.readDataFromExcel(dataPaths.URLs, "URL's", 0, 1);
        } catch (IOException e) {

            System.out.println("Failed to get URL or Browser data");
            throw new RuntimeException(e);
        }
    }


    public SeatsTest() throws IOException {
    }

    @BeforeTest
    public void createTestReports() throws IOException {

        // Get test cases
        testCases = m.getTestCasesFromTestCasesDocument(dataPaths.seatsTest, "Seats Test Cases");
        excelTestReport.createExcelReport();
        htmlTestReport.createHTMLReport();


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

    @AfterTest
    public void reportTestResult() throws IOException, InterruptedException {

        testReport.saveReportsAfterTest(attachments);

    }



    @DataProvider(name = "TestCase")
    public Object[][] getTestCase() throws IOException {
        List<Object[]> testCase = new ArrayList<>();

        // Extracting all test data from test cases in test data sheet
        int totalPaxCount = excelUtils.getRowCount(dataPath, "Booking Scenarios");

        for (int i = 2; i < totalPaxCount; i++) {

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
            String cabinClass = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 15);
            String departureAirline = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 16);
            String returnAirline = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 17);
            String mailID = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 18);
            String mobileNumber = (excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 19));
            String title = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 20);
            String firstName = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 21);
            String middleName = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 22);
            String lastName = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 23);
            String dateOfBirth = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 24);
            String monthOfBirth = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 25);
            String yearOfBirth = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 26);
            String passPortNumber = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 27);
            String dateOfPassportExpiry = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 28);
            String monthOfPassportExpiry = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 29);
            String yearOfPassportExpiry = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 30);
            String passPortNationality = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 31);
            String passPortIssuingCountry = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 32);
            String addBaggage = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 33);
            String addSeats = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 34);
            String addSeatsForAllSegments = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 35);
            String seatsSegment1 = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 36);
            String seatsSegment2 = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 37);
            String seatsSegment3 = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 38);
            String seatsSegment4 = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 39);
            String seatsSegment5 = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 40);
            String seatsSegment6 = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 41);
            String seatsSegment7 = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 42);
            String paymentMethod = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 43);
            String bankNameEFT = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 44);
            String isLoggedInUser = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 45);
            String isToBeCancelled = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 46);

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
                    cabinClass,
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
                    addSeats,
                    addSeatsForAllSegments,
                    seatsSegment1,
                    seatsSegment2,
                    seatsSegment3,
                    seatsSegment4,
                    seatsSegment5,
                    seatsSegment6,
                    seatsSegment7,
                    paymentMethod,
                    bankNameEFT,
                    isLoggedInUser,
                    isToBeCancelled});
        }
        return testCase.toArray(new Object[0][]);
    }


    @Test(dataProvider = "TestCase")
    public void seatsTest(String testCaseNumber, String shouldRun, String domain, String cpy_source, String tripType, String origin, String destination, String departureDate, String departureMonth, String returnDate, String returnMonth, String adultCount, String youngAdultCount, String childCount, String infantCount, String cabinClass, String departureAirline, String returnAirline, String mailID, String mobileNumber, String title, String firstName, String middleName, String lastName, String dateOfBirth, String monthOfBirth, String yearOfBirth, String passPortNumber, String dateOfPassportExpiry, String monthOfPassportExpiry, String yearOfPassportExpiry, String passPortNationality, String passPortIssuingCountry, String addBaggage, String addSeats, String addSeatsForAllSegments, String seatsForSegment1, String seatsForSegment2, String seatsForSegment3, String seatsForSegment4, String seatsForSegment5, String seatsForSegment6, String seatsForSegment7, String paymentMethod, String bankNameEFT, String isLoggedInUser, String isToBeCancelled) throws IOException, InterruptedException {


        runTime = m.getCurrentTime();

        if (!shouldRun.equalsIgnoreCase("TRUE")) {

            System.out.println("Skipped this test");
            throw new SkipException("Test is skipped as this test case " + testCaseNumber + " is not approved to run");

        }

        driver = m.launchBrowser(driver, browser);

        // Maximize window
        driver.manage().window().maximize();

        //Printing the test case number
        System.out.println(testCaseNumber + " Executed");


        TSMethods bookingFlowMethods = new TSMethods(driver);
        boolean isBundled = m.isBundled(domain, tripType, origin, destination);


        baseURL = m.getBaseURL(environment, domain, cpy_source);


        String CID = "";

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


        HomePageMethods homePageMethods = new HomePageMethods();
        SRPMethods srpMethods = new SRPMethods();


        if (isLoggedInUser.equalsIgnoreCase("TRUE")) {

            bookingFlowMethods.login("Sreenivasulu@travelstart.com", "Test@123");

        }


        homePageMethods.makeFlightSearch(driver,tripType, origin, destination, departureMonth, departureDate, returnMonth, returnDate, adultCount, youngAdultCount, childCount, infantCount, cabinClass);

        CID = m.getCID(driver);

        Assert.assertTrue(srpMethods.isSRPLoaded(driver), "Result not loaded");

        srpMethods.openFilters(driver, isBundled);

        srpMethods.selectAirlineFilter(driver, tripType, isBundled, departureAirline, returnAirline);

        srpMethods.applyFilters(driver);

        srpMethods.selectUnbundledFlights(driver, isBundled);

        srpMethods.proceedToTravellerPage(driver, isBundled);

        TravellerDetailsPageMethods travellerDetailsPageMethods = new TravellerDetailsPageMethods();

        boolean isTravellerPageLoaded = travellerDetailsPageMethods.isTravellerPageLoaded(driver);

        boolean isFareIncreased = travellerDetailsPageMethods.isFareIncreased(driver);

        Assert.assertTrue(isTravellerPageLoaded, "Traveller details page wasn't loaded");

        double flightCost = travellerDetailsPageMethods.getFlightCost(driver);

        travellerDetailsPageMethods.enterContactDetails(driver, mobileNumber, mailID);

        travellerDetailsPageMethods.enterPaxDetails(driver, tripType, departureAirline, returnAirline, title, firstName, lastName, dateOfBirth, monthOfBirth, yearOfBirth, passPortNumber, dateOfPassportExpiry, monthOfPassportExpiry, yearOfPassportExpiry, adultCount, youngAdultCount, childCount, infantCount);

        boolean isBaggageOffered = travellerDetailsPageMethods.isBaggageSelectionOffered(driver);
        String baggageType = "";

        boolean addBaggage1 = Boolean.parseBoolean(addBaggage);

        if (isBaggageOffered && addBaggage1){

            baggageType = travellerDetailsPageMethods.getBaggageType(driver, isBaggageOffered);

            travellerDetailsPageMethods.addBaggage(driver, baggageType, adultCount, youngAdultCount, childCount);
            Thread.sleep(5000);

        }

        boolean isMealsOffered = travellerDetailsPageMethods.isMealsOffered(driver);

        boolean testSeats = false;
        boolean selectSeats = Boolean.parseBoolean(addSeats);

        Thread.sleep(500);

        boolean isSeatsOffered = travellerDetailsPageMethods.isSeatsOffered(driver);
        boolean isAddOnsOffered = travellerDetailsPageMethods.isAddOnsOffered(driver);

        if(selectSeats && isSeatsOffered){
            testSeats = true;
        }

        travellerDetailsPageMethods.contiueToNextStep(driver);

        if ((!isSeatsOffered) && selectSeats){

            System.out.println("Seats not offered for this flight, hence skipping seat related test cases");
            throw new SkipException("Seats not offered for this flight, hence skipping seat related test cases");

        }

        Thread.sleep(1000);

        boolean isSeatsMapLoaded = false;

        if (testSeats){

            isSeatsMapLoaded = seatsPageMethods.verifySeatsDisplayed(driver);
            Assert.assertTrue(isSeatsMapLoaded, "Seats map was failed to load");

        } else if (isSeatsOffered && (!selectSeats)) {

            seatsPageMethods.skipSeats(driver, isSeatsOffered);

        }

        // Store selected seats to test
        List<String> selectedSeats = new ArrayList<>();
        double totalCostOfSeats = 0;

        if (testSeats && isSeatsMapLoaded){

                seatsPageMethods.selectSeats(driver, selectSeats);

                Thread.sleep(2000);

                // Get all selected seats
                selectedSeats = seatsPageMethods.getSelectedSeatNumbers(driver);
                totalCostOfSeats = seatsPageMethods.getTotalSeatsCost(driver);

                seatsPageMethods.continueToNextStep(driver);


        }

        boolean isAddOnsAvailable = false;

        isAddOnsAvailable = addOnsPageMethods.isAddOnsLoaded(driver, isAddOnsOffered);

        if (isAddOnsAvailable){

            addOnsPageMethods.unselectAllAddOns(driver);

            addOnsPageMethods.selectFlexiProduct(driver, true);

        }

        Map<String, String> breakDownOnAddOnsPage = addOnsPageMethods.getPriceBreakdown(driver);

        System.out.println(breakDownOnAddOnsPage);

        addOnsPageMethods.continueToNextStep(driver);

        boolean isPaymentPageLoaded = false;

        PaymentPageMethods paymentPageMethods = new PaymentPageMethods();

        isPaymentPageLoaded = paymentPageMethods.assertPaymentPage(driver);

        Assert.assertTrue(isPaymentPageLoaded, "Payment page failed to load");

        String bookingReference = paymentPageMethods.getBookingReference(driver);

        Map<String, String> breakDownInPaymentPage = new HashMap<>();

        breakDownInPaymentPage = paymentPageMethods.getPriceBreakdown(driver, PaymentPage.fareBreakdownTables);

        String totalPriceDisplayedOnPaymentPage = paymentPageMethods.getTotalPrice(driver);

        paymentPageMethods.selectPaymentMethod(driver, domain, paymentMethod);

        if (paymentMethod.equalsIgnoreCase("EFT")){

            paymentPageMethods.selectEFTBank(driver, domain, bankNameEFT);

            paymentPageMethods.clickPay(driver, domain, paymentMethod);

        }

        boolean isBookingSuccess = false;

        BookingConfirmationPageMethods bookingConfirmationPageMethods = new BookingConfirmationPageMethods();

        isBookingSuccess = bookingConfirmationPageMethods.verifyBookingConfirmationRedirect(driver);

        Assert.assertTrue(isBookingSuccess, "Booking got failed. Correlation ID: "+CID);

        List<String> seatsNumbersOnBookingConfirmationPage = new ArrayList<>();

        seatsNumbersOnBookingConfirmationPage = bookingConfirmationPageMethods.getAllSelectedSeats(driver);

        boolean selectedSeatNumbersMatchingWithBookingConfirmation = bookingConfirmationPageMethods.validateSelectedSeatsInBookingConfirmationPage(selectedSeats, seatsNumbersOnBookingConfirmationPage);

        softAssert.assertTrue(selectedSeatNumbersMatchingWithBookingConfirmation, "Seats selected are not matching with seats displayed on booking confirmation");


        // Check all assertions
        softAssert.assertAll();

        //Cancel flight
        if(isToBeCancelled.equalsIgnoreCase("Yes")) {
            m.cancelBooking(environment, bookingReference);
        }

    }




    // TestCaseMethods





}




