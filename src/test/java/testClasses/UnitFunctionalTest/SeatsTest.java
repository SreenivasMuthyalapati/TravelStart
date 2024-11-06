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
import pageObjects.SeatsPage;
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

        for (int i =0; i< testResultData.size(); i++){

            try{
                String[] arr = testResultData.get(i);
                attachments.add(arr[5]);
            }catch (Exception e){

            }

        }
        testReport.updateToReport(testResultData);

        if (driver != null) {

           driver.quit();

        }
    }

    @AfterTest
    public void reportTestResult() throws IOException, InterruptedException {

        testReport.saveReportsAfterTest(attachments);
        SendEmail sendEmail = new SendEmail();
        sendEmail.sendEmail("B2C Seats Test Automation Result Report"+ runTime, attachments);

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
            String paymentMethod = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 42);
            String bankNameEFT = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 43);
            String isLoggedInUser = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 44);
            String isToBeCancelled = excelUtils.readDataFromExcel(dataPath, "Booking Scenarios", i, 45);

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
                    paymentMethod,
                    bankNameEFT,
                    isLoggedInUser,
                    isToBeCancelled});
        }
        return testCase.toArray(new Object[0][]);
    }


    @Test(dataProvider = "TestCase")
    public void seatsTest(String testCaseNumber, String shouldRun, String domain, String cpy_source, String tripType, String origin, String destination, String departureDate, String departureMonth, String returnDate, String returnMonth, String adultCount, String youngAdultCount, String childCount, String infantCount, String cabinClass, String departureAirline, String returnAirline, String mailID, String mobileNumber, String title, String firstName, String middleName, String lastName, String dateOfBirth, String monthOfBirth, String yearOfBirth, String passPortNumber, String dateOfPassportExpiry, String monthOfPassportExpiry, String yearOfPassportExpiry, String passPortNationality, String passPortIssuingCountry, String addBaggage, String addSeats, String addSeatsForAllSegments, String seatsForSegment1, String seatsForSegment2, String seatsForSegment3, String seatsForSegment4, String seatsForSegment5, String seatsForSegment6, String paymentMethod, String bankNameEFT, String isLoggedInUser, String isToBeCancelled) throws IOException, InterruptedException {


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

        int flightCost = travellerDetailsPageMethods.getFlightCost(driver);

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

        // Seats_TC_01

        if(Boolean.parseBoolean(testCases.get(0)[2])) {
            if (testSeats) {

                isSeatsMapLoaded = seatsPageMethods.verifySeatsDisplayed(driver);
                testReport.updateTestResult(testResultData, driver, testCaseNumber, testCases.get(0)[0], testCases.get(0)[1], CID, isSeatsMapLoaded);
                String failMessage = "Seatmap wasn't loaded for test scenario ID:"+ testCaseNumber;
                Assert.assertTrue(isSeatsMapLoaded, failMessage);

            } else if (isSeatsOffered && (!selectSeats)) {

                seatsPageMethods.skipSeats(driver, isSeatsOffered);

            }
        }


        // Seats_TC_02

        boolean isSegmentSwitchable = false;

        if(Boolean.parseBoolean(testCases.get(1)[2])) {
            if (testSeats) {

                boolean isSeatsAvailaleForAllSegments = false;

                int i;

                String currentSegment = "";

                for (i = 0; i < seatsPageMethods.getAvailableSegmentsCount(driver); i++)
                {

                    seatsPageMethods.switchToSegment(driver, String.valueOf(i + 1));

                    isSeatsAvailaleForAllSegments = seatsPageMethods.verifySeatsDisplayed(driver);

                    if (!isSeatsAvailaleForAllSegments) {
                        break;
                    }


                    testReport.updateTestResult(testResultData, driver, testCaseNumber, testCases.get(1)[0], testCases.get(1)[1], CID, isSeatsAvailaleForAllSegments);
                    String failMessage = "Seatmap wasn't loaded for test scenario ID:" + testCaseNumber +" and segment number: "+ i+1;
                    softAssert.assertTrue(isSeatsAvailaleForAllSegments, failMessage);

                }

                currentSegment = driver.findElement(SeatsPage.currentSegment).getText();

                seatsPageMethods.switchToSegment(driver, String.valueOf(1));


                if (!currentSegment.equalsIgnoreCase(driver.findElement(SeatsPage.currentSegment).getText())){

                    isSegmentSwitchable = true;

                }

                }

        }

        // Seats_TC_03

        if(Boolean.parseBoolean(testCases.get(2)[2])) {

            if (testSeats) {

                    testReport.updateTestResult(testResultData, driver, testCaseNumber, testCases.get(2)[0], testCases.get(2)[1], CID, isSegmentSwitchable);
                    String failMessage = "Seat segment switch wasn't working for test scenario ID:" + testCaseNumber ;
                    softAssert.assertTrue(isSegmentSwitchable, failMessage);

                }

        }

        // Store selected seats to test
        List<String> selectedSeats = new ArrayList<>();
        int totalCostOfSeats = 0;



        if (testSeats && isSeatsMapLoaded){
            
            boolean selectSeatsForAllSegmentsandAllPax = Boolean.parseBoolean(addSeatsForAllSegments);
            if (selectSeatsForAllSegmentsandAllPax) {
                seatsPageMethods.selectSeats(driver, selectSeatsForAllSegmentsandAllPax);
            } else if (!selectSeatsForAllSegmentsandAllPax) {
                seatsPageMethods.selectSeats(driver, selectSeatsForAllSegmentsandAllPax, seatsForSegment1, seatsForSegment2, seatsForSegment3, seatsForSegment4, seatsForSegment5, seatsForSegment6);
            }
            Thread.sleep(1000);

                // Get all selected seats
                selectedSeats = seatsPageMethods.getSelectedSeatNumbers(driver);
                totalCostOfSeats = seatsPageMethods.getTotalSeatsCost(driver);

                seatsPageMethods.continueToNextStep(driver);


        }

        // Seats_TC_04

        if(Boolean.parseBoolean(testCases.get(3)[2])) {

            if (testSeats) {

                boolean isSelectedSeatsDisplayed = false;

                if (!selectedSeats.isEmpty()){

                    isSelectedSeatsDisplayed = true;

                }

                testReport.updateTestResult(testResultData, driver, testCaseNumber, testCases.get(3)[0], testCases.get(3)[1], CID, isSelectedSeatsDisplayed);
                String failMessage = "Seats selected were not displayed in seat map for test scenario ID:" + testCaseNumber ;
                softAssert.assertTrue(isSelectedSeatsDisplayed, failMessage);

            }

        }

        // Seats_TC_05

        if(Boolean.parseBoolean(testCases.get(4)[2])) {

            if (testSeats) {

                boolean isSelectedSeatsPriceDisplayed = false;

                if (!(totalCostOfSeats == 0)){

                    isSelectedSeatsPriceDisplayed = true;

                }

                testReport.updateTestResult(testResultData, driver, testCaseNumber, testCases.get(4)[0], testCases.get(4)[1], CID, isSelectedSeatsPriceDisplayed);
                String failMessage = "Seats cost was not displayed in seat map for test scenario ID:" + testCaseNumber ;
                softAssert.assertTrue(isSelectedSeatsPriceDisplayed, failMessage);

            }

        }

        boolean isAddOnsAvailable = false;


        PaymentPageMethods paymentPageMethods = new PaymentPageMethods();


        isAddOnsAvailable = addOnsPageMethods.isAddOnsLoaded(driver, isAddOnsOffered);

        Map<String, String> breakDownOnAddOnsPage = new HashMap<>();
        if (isAddOnsAvailable) {
            breakDownOnAddOnsPage = addOnsPageMethods.getPriceBreakdown(driver);
        }

        // Seats_TC_06

        if(Boolean.parseBoolean(testCases.get(5)[2])) {

            boolean isNextStepLoaded = false;

            if (isAddOnsOffered) {

                isNextStepLoaded = m.verifyRedirection(driver, AddOnsPage.AddOns, "Add-Ons");


            } else if (!isAddOnsOffered) {

                isNextStepLoaded = paymentPageMethods.assertPaymentPage(driver);

            }


            testReport.updateTestResult(testResultData, driver, testCaseNumber, testCases.get(5)[0], testCases.get(5)[1], CID, isNextStepLoaded);
            String failMessage = "Unable to move to next step after seats map for test scenario ID:" + testCaseNumber ;
            Assert.assertTrue(isNextStepLoaded, failMessage);

        }


        // Seats_TC_07

        if(Boolean.parseBoolean(testCases.get(6)[2])) {

            boolean isPriceDisplayedInBreakDown = false;

            if (isAddOnsOffered && testSeats) {

                String seatsAmountInAddOnsBreakDown = m.retriveValueFromMap(breakDownOnAddOnsPage, "Seat");

                isPriceDisplayedInBreakDown = (totalCostOfSeats == m.stringToInt(seatsAmountInAddOnsBreakDown) );

                testReport.updateTestResult(testResultData, driver, testCaseNumber, testCases.get(6)[0], testCases.get(6)[1], CID, isPriceDisplayedInBreakDown);
                String failMessage = "Seats selection price was not displayed in breakdown on addOns page for test scenario ID:" + testCaseNumber ;
                softAssert.assertTrue(isPriceDisplayedInBreakDown, failMessage);

            }

        }

        // Seats_TC_08

        if(Boolean.parseBoolean(testCases.get(7)[2])) {

            boolean isSeatDisplayedOnAddOns = false;

            if (isAddOnsOffered && testSeats) {

                isSeatDisplayedOnAddOns = true;

                testReport.updateTestResult(testResultData, driver, testCaseNumber, testCases.get(7)[0], testCases.get(7)[1], CID, isSeatDisplayedOnAddOns);
                String failMessage = "Selected seat numbers were not displayed on addOns page for test scenario ID:" + testCaseNumber ;
                softAssert.assertTrue(isSeatDisplayedOnAddOns, failMessage);

            }

        }


        if (isAddOnsAvailable){

            addOnsPageMethods.unselectAllAddOns(driver);

        }


        if (isAddOnsAvailable) {
            addOnsPageMethods.continueToNextStep(driver);
        }

        boolean isPaymentPageLoaded = false;

        // Seats_TC_15

        if(Boolean.parseBoolean(testCases.get(14)[2])) {

            isPaymentPageLoaded = paymentPageMethods.assertPaymentPage(driver);

                testReport.updateTestResult(testResultData, driver, testCaseNumber, testCases.get(14)[0], testCases.get(14)[1], CID, isPaymentPageLoaded);
                String failMessage = "Payment page not loaded for test scenario ID:" + testCaseNumber ;
                Assert.assertTrue(isPaymentPageLoaded, failMessage);


        }


        String bookingReference = paymentPageMethods.getBookingReference(driver);

        Map<String, String> breakDownInPaymentPage = new HashMap<>();

        breakDownInPaymentPage = paymentPageMethods.getPriceBreakdown(driver, PaymentPage.fareBreakdownTables);

        String totalPriceDisplayedOnPaymentPage = paymentPageMethods.getTotalPrice(driver);

        // Seats_TC_16

        if(Boolean.parseBoolean(testCases.get(15)[2])) {

            boolean isPriceMatching = false;

            if (testSeats) {

                String seatsAmountInBreakDown = m.retriveValueFromMap(breakDownInPaymentPage, "Seat");

                isPriceMatching = (totalCostOfSeats == m.stringToInt(seatsAmountInBreakDown) );

                testReport.updateTestResult(testResultData, driver, testCaseNumber, testCases.get(15)[0], testCases.get(15)[1], CID, isPriceMatching);
                String failMessage = "Seats selection price was not matching with seats price displayed on payment page for test scenario ID:" + testCaseNumber + "Actual seat selected amount: "+ totalCostOfSeats +" but the seats amount displayed on payment page is: "+ m.stringToInt(seatsAmountInBreakDown) ;
                softAssert.assertTrue(isPriceMatching, failMessage);

            }

        }

        paymentPageMethods.selectPaymentMethod(driver, domain, paymentMethod);
        BookingConfirmationPageMethods bookingConfirmationPageMethods = new BookingConfirmationPageMethods();

        // Seats_TC_17

        if(Boolean.parseBoolean(testCases.get(16)[2])) {

            if (paymentMethod.equalsIgnoreCase("EFT")){

                paymentPageMethods.selectEFTBank(driver, domain, bankNameEFT);

                paymentPageMethods.clickPay(driver, domain, paymentMethod);

            }

            boolean isBookingSuccess = false;

            isBookingSuccess = bookingConfirmationPageMethods.verifyBookingConfirmationRedirect(driver);

            testReport.updateTestResult(testResultData, driver, testCaseNumber, testCases.get(16)[0], testCases.get(16)[1], CID, isBookingSuccess);
            String failMessage = "Booking failed for test scenario ID:" + testCaseNumber +" and CID is:"+ CID;
            Assert.assertTrue(isBookingSuccess, failMessage);


        }else {
            throw new SkipException("Skipping tests after booking step");
        }


        List<String> seatsNumbersOnBookingConfirmationPage = new ArrayList<>();

        // Seats_TC_18

        if(Boolean.parseBoolean(testCases.get(17)[2])) {


            if (testSeats) {

                seatsNumbersOnBookingConfirmationPage = bookingConfirmationPageMethods.getAllSelectedSeats(driver);

                boolean selectedSeatNumbersMatchingWithBookingConfirmation = bookingConfirmationPageMethods.validateSelectedSeatsInBookingConfirmationPage(selectedSeats, seatsNumbersOnBookingConfirmationPage);

                testReport.updateTestResult(testResultData, driver, testCaseNumber, testCases.get(17)[0], testCases.get(17)[1], CID, selectedSeatNumbersMatchingWithBookingConfirmation);
                String failMessage = "Selected seats numbers was not matching with seats displayed on booking confirmation page for test scenario ID:" + testCaseNumber + ". The selected seats were: "+ selectedSeats +", but seats displayed on booking confirmation were: "+ seatsNumbersOnBookingConfirmationPage;
                softAssert.assertTrue(selectedSeatNumbersMatchingWithBookingConfirmation, failMessage);

            }

        }

        // Seats_TC_19

        Map<String, String> invoiceBreakDown = bookingConfirmationPageMethods.getPricebreakDown(driver);

        if(Boolean.parseBoolean(testCases.get(18)[2])) {

            if (testSeats) {
                int seatsPriceInBreakDown = m.stringToInt(m.retriveValueFromMap(invoiceBreakDown, "Seat"));
                boolean isPriceMatching = false;

                isPriceMatching = totalCostOfSeats == seatsPriceInBreakDown;
                testReport.updateTestResult(testResultData, driver, testCaseNumber, testCases.get(18)[0], testCases.get(18)[1], CID, isPriceMatching);
                String failMessage = "Seats selection price was not matching with seats price displayed on confirmation page for test scenario ID:" + testCaseNumber +". Cost of seats: "+totalCostOfSeats+" but price displayed on booking confirmarion was: "+ seatsPriceInBreakDown;
                softAssert.assertTrue(isPriceMatching, failMessage);

            }

        }

        // Check all assertions
        softAssert.assertAll();
        softAssert = new SoftAssert();


        //Cancel flight
        boolean cancelBooking = Boolean.parseBoolean(isToBeCancelled);
        if(cancelBooking) {
            m.cancelBooking(environment, bookingReference);
        }

    }


}




