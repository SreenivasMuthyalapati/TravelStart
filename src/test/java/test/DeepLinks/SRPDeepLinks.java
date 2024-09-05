package test.DeepLinks;

import ch.qos.logback.core.net.SyslogOutputStream;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.FlightPage;
import pageObjects.Paths;
import pageObjects.SRP;
import testmethods.Method;
import testmethods.TSMethods;

import java.io.File;
import java.io.IOException;

public class SRPDeepLinks {

    static XSSFWorkbook workbook;
    static WebDriver driver;
    static Method m = new Method();
    TSMethods bookingMethod;
    static String dataPath = Paths.deepLinks;
    static String environment;
    static String browser;
    static String outputExcel = Paths.testOutput;
    static String deepLinkURL;
    static String runTime;
    static String screenShotPath ="";
    static String domain;
    static String testCaseID;
    static String testCaseSummary;
    static String tripType;
    static String from;
    static String to;
    static String depDay;
    static String depMonth;
    static String depYear;
    static String retDay;
    static String retMonth;
    static String retYear;
    static String adultCount;
    static String teenCount;
    static String childCount;
    static String infantCount;
    static String shouldRun;
    WebDriverWait wait;
    static String testStatus;
    static String isBundled;
    static String departureAirline;
    static String returnAirline;
    static String mailID;
    static String mobileNumber;
    static String title;
    static String firstName;
    static String middleName;
    static String lastName;
    static String dateOfBirth;
    static String monthOfBirth;
    static String yearOfBirth;
    static String passPortNumber;
    static String dateOfPassportExpiry;
    static String monthOfPassportExpiry;
    static String yearOfPassportExpiry;
    static String passPortNationality;
    static String passPortIssuingCountry;
    static String addBaggage;
    static String whatsApp;

    // Extracting environment from test data sheet
    static {
        try {
            environment = m.readDataFromExcel(dataPath, "URL's", 1, 1);
            browser = m.readDataFromExcel(dataPath, "URL's", 0, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Validation variables
    static String flightNumberSRP = "";
    static String flightNumberSRP2 = "";

    static String flightNumberFlightDetailsPage = "";
    static String flightNumberFlightDetailsPage2 = "";

    static String flightNumberConfirmationPage = "";
    static String flightNumberConfirmationPage2 = "";

    static String airportSRP = "";
    static String airportSRP2 = "";

    static String airportFlightDetailsPage = "";
    static String airportFlightDetailsPage2 = "";

    static String airportConfirmationPage = "";
    static String airportConfirmationPage2 = "";


    static String departureDateFlightDetailsPage;
    static String departureDateFlightDetailsPage2;

    static String departureDateConfirmationPage;
    static String departureDateConfirmationPage2;

    static String priceSRP = "";

    static String priceFlightDetailsPage;

    static String pricePaymentPage;

    static String priceConfirmationPage;





    public SRPDeepLinks() throws IOException {

    }


    @BeforeClass
    public void launchBrowser(){

        if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("Edge")) {
            driver = new EdgeDriver();
        }

        bookingMethod = new TSMethods(driver);

        wait = new WebDriverWait(driver, 60);

    }



    @AfterClass
    public void close(){
       driver.quit();
    }

    int testDataGroup = 5;

    @Test (priority = 1, description = "Search result deeplink redirection")
    public void SRP_Deeplink_Case01() throws IOException, InterruptedException {


        testCaseID = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 0);
        shouldRun = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 1);
        testCaseSummary = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 3);
        domain = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 4);
        tripType = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 5);
        from = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 6);
        to = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 7);
        depDay = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 8);
        depMonth = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 9);
        depYear = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 10);
        retDay = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 11);
        retMonth = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 12);
        retYear = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 13);
        adultCount = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 14);
        teenCount = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 15);
        childCount = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 16);
        infantCount = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 17);

        if (!shouldRun.equalsIgnoreCase("Yes")) {

            throw new SkipException("Test is skipped as this test case " + testCaseID + " is not approved to run");

        }


        deepLinkURL = m.deeplinkGeneratorSRP(environment, domain, tripType, from, to, depDay, depMonth, depYear, retDay, retMonth, retYear, adultCount, teenCount, childCount, infantCount);

        driver.get(deepLinkURL);
        driver.manage().window().maximize();

        Thread.sleep(2000);

        WebElement result = null;

        try {
            // To wait until result is loaded (Waits for 60 seconds maximum)
            wait.until(ExpectedConditions.visibilityOfElementLocated(SRP.results));
            result = driver.findElement(SRP.results);

        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
        }

        // Initializing a boolean variable for result assertion
        boolean isResultAvailable = false;

        try{
            // Stores true if result is available
            isResultAvailable = result.isDisplayed();

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        // Initializing a boolean variable for result assertion
        isResultAvailable = false;

        try{
            // Stores true if result is avaiable
            isResultAvailable = result.isDisplayed();

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        if (isResultAvailable){
            System.out.println("Result loaded");
        }
        else {

            m.takeScreenshot(driver, Paths.screenshotFolder, screenShotPath);

            File screenShotFile = new File(screenShotPath);

            //m.sendNotification(testCaseID, "Result not loaded or result not loaded within time limit");

            m.writeToExcel(testCaseID, 0, outputExcel);

            m.writeToExcel("-", 1, outputExcel);

            testStatus = "Failed";

            m.writeToExcel(testStatus, 2, outputExcel);

            m.writeToExcel(testCaseSummary, 3, outputExcel);

            m.writeToExcel(m.getCID(driver), 4, outputExcel);

            m.writeToExcel(runTime, 5, outputExcel);

        }

        Assert.assertTrue(isResultAvailable, "Result is not loaded");

    }

    @Test (priority = 2, description = "Verify that user is able to make booking through deeplink")
    public void SRP_Deeplink_Case02() throws IOException, InterruptedException {

        if (!shouldRun.equalsIgnoreCase("Yes")) {

            throw new SkipException("Test is skipped as this test case " + testCaseID + " is not approved to run");

        }


        testCaseID = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 0);
        shouldRun = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 1);
        testCaseSummary = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 3);
        domain = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 4);
        tripType = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 5);
        from = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 6);
        to = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 7);
        depDay = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 8);
        depMonth = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 9);
        depYear = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 10);
        retDay = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 11);
        retMonth = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 12);
        retYear = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 13);
        adultCount = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 14);
        teenCount = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 15);
        childCount = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 16);
        infantCount = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 17);
        isBundled = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 18);
        departureAirline = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 19);
        returnAirline = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 20);
        mailID = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 21);
        mobileNumber = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 22);
        title = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 23);
        firstName = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 24);
        middleName = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 25);
        lastName = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 26);
        dateOfBirth = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 27);
        monthOfBirth = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 28);
        yearOfBirth = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 29);
        passPortNumber = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 30);
        dateOfPassportExpiry = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 31);
        monthOfPassportExpiry = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 32);
        yearOfPassportExpiry = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 33);
        passPortNationality = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 34);
        passPortIssuingCountry = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 35);
        addBaggage = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 36);
        whatsApp = m.readDataFromExcel(dataPath, "Search Result Deeplinks", testDataGroup, 38);





        bookingMethod.SelectAirline(testCaseID, tripType,isBundled, departureAirline, returnAirline );

        if (isBundled.equalsIgnoreCase("Yes")) {

            priceSRP = driver.findElement(SRP.flightPrice).getText();
        }
        else if (tripType.equalsIgnoreCase("Return") && isBundled.equalsIgnoreCase("No")){

            priceSRP = driver.findElement(SRP.flightPriceUnbundled).getText();
        }

        bookingMethod.clickBook(testCaseID, tripType, isBundled);

        // Flight Details Page

        //bookingMethod.enterPaxDetails(testCaseID, tripType, adultCount, teenCount, childCount, infantCount, departureAirline, returnAirline,mailID, mobileNumber, title, firstName, middleName, lastName, dateOfBirth, monthOfBirth, yearOfBirth, passPortNumber, dateOfPassportExpiry, monthOfPassportExpiry, yearOfPassportExpiry, passPortNationality, passPortIssuingCountry, addBaggage, whatsApp);

        flightNumberFlightDetailsPage = driver.findElement(FlightPage.flightNumber1).getText();
        departureDateFlightDetailsPage = driver.findElement(FlightPage.departureDate).getText();
        airportFlightDetailsPage = driver.findElement(FlightPage.departureCity).getText();
        airportFlightDetailsPage2 = driver.findElement(FlightPage.arrivalCity).getText();

        if (tripType.equalsIgnoreCase("Return")){

            flightNumberFlightDetailsPage2 = driver.findElement(FlightPage.flightNumber2).getText();
            departureDateFlightDetailsPage2 = driver.findElement(FlightPage.returnDate).getText();

        }

    }

}
