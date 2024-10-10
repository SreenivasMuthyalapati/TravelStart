package test.Booking;

import com.sendgrid.helpers.mail.objects.Email;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.FlightPage;
import pageObjects.Paths;
import pageObjects.SRP;
import testmethods.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MetaLinks {

    static XSSFWorkbook workbook;
    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = "C:\\Users\\Sreen\\IdeaProjects\\travelStart\\TestData\\metaDeeplinks.xls";
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


    public MetaLinks() throws IOException {
    }

    @AfterTest
    public void sendReport(){

        SendEmail sendEmail = new SendEmail();

        sendEmail.sendEmail();

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
        int totalPaxCount = m.getRowCount(dataPath, "Booking Scenarios");

        for (int i = 2; i < totalPaxCount; i++) {

            String testCaseNumber = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 0);
            String shouldRun = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 1);
            String domain = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 2);
            String metaName = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 3);
            String tripType = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 4);
            String origin = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 5);
            String destination = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 6);
            String departureDate = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 7);
            String departureMonth = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 8);
            String departureYear = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 9);
            String returnDate = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 10);
            String returnMonth = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 11);
            String returnYear = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 12);
            String adultCount = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 13);
            String childCount = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 14);
            String infantCount = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 15);
            String departureAirline = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 16);
            String returnAirline = m.readDataFromExcel(dataPath, "Booking Scenarios", i, 17);


            testCase.add(new Object[]{testCaseNumber,
                    shouldRun,
                    domain,
                    metaName,
                    tripType,
                    origin,
                    destination,
                    departureDate,
                    departureMonth,
                    departureYear,
                    returnDate,
                    returnMonth,
                    returnYear,
                    adultCount,
                    childCount,
                    infantCount,
                    departureAirline,
                    returnAirline,
//                    mailID,
//                    mobileNumber,
//                    title,
//                    firstName,
//                    middleName,
//                    lastName,
//                    dateOfBirth,
//                    monthOfBirth,
//                    yearOfBirth,
//                    passPortNumber,
//                    dateOfPassportExpiry,
//                    monthOfPassportExpiry,
//                    yearOfPassportExpiry,
//                    passPortNationality,
//                    passPortIssuingCountry,
//                    paymentMethod,
//                    bankNameEFT,
//                    isLoggedInUser,
//                    isToBeCancelled
                  });
        }
        return testCase.toArray(new Object[0][]);
    }



    @Test(dataProvider = "TestCase")
    public void bookingFlow(String testCaseNumber, String shouldRun, String domain, String metaName, String tripType, String origin, String destination, String departureDate, String departureMonth, String departureYear, String returnDate, String returnMonth, String returnYear, String adultCount, String childCount, String infantCount, String departureAirline, String returnAirline) throws IOException, InterruptedException {

//        runTime = m.getCurrentTime();
//
//        if (!shouldRun.equalsIgnoreCase("Yes")) {
//
//            throw new SkipException("Test is skipped as this test case " + testCaseNumber + " is not approved to run");
//        }
//
//        driver = m.launchBrowser(driver, browser);
//
//        // Maximize window
//        driver.manage().window().maximize();
//
//        //Printing the test case number
//        System.out.println(testCaseNumber + " Executed");
//
//
//        TSMethods bookingFlowMethods = new TSMethods(driver);
        boolean isBundled = m.isBundled(domain, tripType, origin, destination);

        DeeplinksMethods deeplinksMethods = new DeeplinksMethods();

        String deeplink = deeplinksMethods.generateMetaDeeplink(environment, domain, metaName,tripType, origin, destination, departureDate, departureMonth, departureYear, returnDate, returnMonth, returnYear, adultCount, "0", childCount, infantCount, isBundled, departureAirline, returnAirline);

//        driver.get(deeplink);
//
//        boolean isPass = m.verifyRedirection(driver, FlightPage.flightReviewPage, "Flight page");

        System.out.println(deeplink);
//
//        Thread.sleep(5000);
//
//        Assert.assertTrue(isPass);

        }

    }



