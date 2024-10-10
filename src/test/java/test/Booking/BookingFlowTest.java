package test.Booking;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.FlightPage;
import pageObjects.Paths;
import testmethods.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingFlowTest {

    static XSSFWorkbook workbook;
    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = Paths.B2CBookingE2ETestData;
    static String environment;
    static String browser;
    static String outputExcel = Paths.excelOutputPath;
    static String baseURL;
    static String runTime;
    static String screenShotPath ="";

    // Extracting environment from test data sheet
    static {
        try {
            environment = m.readDataFromExcel(dataPath, "URL's", 1, 1);
            browser = m.readDataFromExcel(dataPath, "URL's", 0, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public BookingFlowTest() throws IOException {
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
        int totalPaxCount = m.getRowCount(dataPath, "Booking Test Cases");

        for (int i = 2; i < totalPaxCount; i++) {

            String testCaseNumber = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 0);
            String shouldRun = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 1);
            String domain = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 2);
            String cpy_source = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 3);
            String tripType = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 4);
            String origin = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 5);
            String destination = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 6);
            String departureDate = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 7);
            String departureMonth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 8);
            String returnDate = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 9);
            String returnMonth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 10);
            String adultCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 11);
            String youngAdultCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 12);
            String childCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 13);
            String infantCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 14);
            String departureAirline = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 15);
            String returnAirline = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 16);
            String mailID = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 17);
            String mobileNumber = (m.readDataFromExcel(dataPath, "Booking Test Cases", i, 18));
            String title = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 19);
            String firstName = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 20);
            String middleName = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 21);
            String lastName = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 22);
            String dateOfBirth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 23);
            String monthOfBirth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 24);
            String yearOfBirth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 25);
            String passPortNumber = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 26);
            String dateOfPassportExpiry = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 27);
            String monthOfPassportExpiry = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 28);
            String yearOfPassportExpiry = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 29);
            String passPortNationality = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 30);
            String passPortIssuingCountry = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 31);
            String paymentMethod = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 32);
            String bankNameEFT = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 33);
            String isLoggedInUser = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 34);
            String isToBeCancelled = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 35);

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

        runTime = m.getCurrentTime();

        if (!shouldRun.equalsIgnoreCase("Yes")) {

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


        if (isLoggedInUser.equalsIgnoreCase("Yes")) {

            bookingFlowMethods.login("Sreenivasulu@travelstart.com", "Test@123");

        }


        homePageMethods.makeFlightSearch(driver,tripType, origin, destination, departureMonth, departureDate, returnMonth, returnDate, adultCount, youngAdultCount, childCount, infantCount);

        CID = m.getCID(driver);

        Assert.assertTrue(srpMethods.isSRPLoaded(driver), "Result not loaded");

        System.out.println("Is bundled? = "+ isBundled);

        srpMethods.openFilters(driver, isBundled);

        srpMethods.selectAirlineFilter(driver, tripType, isBundled, departureAirline, returnAirline);

        srpMethods.applyFilters(driver);

        srpMethods.selectUnbundledFlights(driver, isBundled);

        System.out.println("Segments: "+ srpMethods.getTotalSegmentCount(driver, tripType, isBundled));

        srpMethods.proceedToTravellerPage(driver, isBundled);


        TravellerDetailsPageMethods travellerDetailsPageMethods = new TravellerDetailsPageMethods();

        boolean isTravellerPageLoaded = travellerDetailsPageMethods.isTravellerPageLoaded(driver);

        boolean isFareIncreased = false;

        double increasedFare = 0;

        isFareIncreased = travellerDetailsPageMethods.isFareIncreased(driver, increasedFare);

        double flightCost = travellerDetailsPageMethods.getFlightCost(driver);

        System.out.println(flightCost);

        double whatsappPrice = travellerDetailsPageMethods.selectWhatsapp(driver, true);

        travellerDetailsPageMethods.enterContactDetails(driver, mobileNumber, mailID);

        travellerDetailsPageMethods.enterPaxDetails(driver, tripType, departureAirline, returnAirline, title, firstName, lastName, dateOfBirth, monthOfBirth, yearOfBirth, passPortNumber, dateOfPassportExpiry, monthOfPassportExpiry, yearOfPassportExpiry, adultCount, youngAdultCount, childCount, infantCount);

        boolean isBaggageOffered = travellerDetailsPageMethods.isBaggageSelectionOffered(driver);
        String baggageType = "";

        if (isBaggageOffered){

            baggageType = travellerDetailsPageMethods.getBaggageType(driver, isBaggageOffered);
            travellerDetailsPageMethods.addBaggage(driver, baggageType, adultCount, youngAdultCount, childCount);
            Thread.sleep(5000);
        }

        boolean isMealsOffered = travellerDetailsPageMethods.isMealsOffered(driver);

        if(isMealsOffered){

            System.out.println(travellerDetailsPageMethods.selectMeal(driver, adultCount, youngAdultCount, childCount, infantCount));

            }

        Thread.sleep(5000);

        }

    }



