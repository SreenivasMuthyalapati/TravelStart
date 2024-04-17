package test.Booking;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.*;
import testmethods.Method;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingFlow {

    static XSSFWorkbook workbook;
    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = Paths.dataPath;
    static String environment;
    static String outputExcel = Paths.excelOutputPath;
    static String baseURL;
    static String runTime;
    static String screenShotPath ="";

    // Extracting environment from test data sheet
    static {
        try {
            environment = m.readDataFromExcel(dataPath, "URL's", 0, 1);
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
        int totalPaxCount = m.getRowCount(dataPath, "Booking Test Cases");

        for (int i = 2; i < totalPaxCount; i++) {

            String testCaseNumber = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 0);
            String shouldRun = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 1);
            String domain = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 2);
            String tripType = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 3);
            String origin = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 4);
            String destination = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 5);
            String departureDate = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 6);
            String departureMonth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 7);
            String returnDate = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 8);
            String returnMonth = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 9);
            String adultCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 10);
            String youngAdultCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 11);
            String childCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 12);
            String infantCount = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 13);
            String isBundled = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 14);
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
            String addBaggage = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 32);
            String addFlexi = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 33);
            String whatsapp = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 34);
            String paymentMethod = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 35);
            String bankNameEFT = m.readDataFromExcel(dataPath, "Booking Test Cases", i, 36);

            testCase.add(new Object[]{testCaseNumber,
                    shouldRun,
                    domain,
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
                    paymentMethod,
                    bankNameEFT});
        }
        return testCase.toArray(new Object[0][]);
    }

    @Test(dataProvider = "TestCase")
    public void bookingFlow(String testCaseNumber, String shouldRun, String domain, String tripType, String origin, String destination, String departureDate, String departureMonth, String returnDate, String returnMonth, String adultCount, String youngAdultCount, String childCount, String infantCount,String isBundled, String departureAirline, String returnAirline, String mailID, String mobileNumber, String title, String firstName, String middleName, String lastName, String dateOfBirth, String monthOfBirth, String yearOfBirth, String passPortNumber, String dateOfPassportExpiry, String monthOfPassportExpiry, String yearOfPassportExpiry, String passPortNationality, String passPortIssuingCountry, String addBaggage, String addFlexi, String whatsApp, String paymentMethod, String bankNameEFT) throws IOException, InterruptedException {

        runTime = m.getCurrentTime();
        String testStatus;


        if (!shouldRun.equalsIgnoreCase("Yes")) {
            m.writeToExcel(testCaseNumber, 0, outputExcel);
            m.writeToExcel("-", 1, outputExcel);
            testStatus = "Skipped";
            m.writeToExcel(testStatus, 2, outputExcel);
            m.writeToExcel("Skipped this test case as this test case is not approved to run", 3, outputExcel);
            m.writeToExcel("-", 4, outputExcel);
            m.writeToExcel(runTime, 5, outputExcel);
            throw new SkipException("Test is skipped as this test case " +testCaseNumber+ " is not approved to run");
        }



        //Printing the test case number
        System.out.println(STR."\{testCaseNumber} Executed");

        // Invoke Chrome browser
        System.setProperty("webdriver.chrome.driver", Paths.chromeDriver);
        driver = new ChromeDriver();

        // Maximize window
        driver.manage().window().maximize();


        // Setting up URL for ZA domain
        if (domain.equalsIgnoreCase("ZA"))

            switch (environment) {

                case "live" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 3, 1);
                case "beta" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 5, 1));
                case "preprod" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 7, 1));
                case "alpha" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 9, 1));

                default -> System.out.println("Invalid environment name");


            }

            // Setting up URL for NG domain
        else if (domain.equalsIgnoreCase("NG")) {

            switch (environment) {

                case "live" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 4, 1));
                case "beta" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 6, 1));
                case "preprod" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 8, 1));
                case "alpha" -> baseURL = (m.readDataFromExcel(dataPath, "URL's", 10, 1));

                default -> System.out.println("Invalid envinorment name");

            }
        }



        // Launch URL
        driver.get(baseURL);
        Thread.sleep(300);


            //accept all cookies
            driver.manage().deleteAllCookies();
            try {
                Alert alert = driver.switchTo().alert();
                alert.accept();
            } catch (Exception e) {
                e.printStackTrace();
            }

        Thread.sleep(2000);

            //Selecting trip type in search
            if (tripType.equalsIgnoreCase("Oneway")){

            driver.findElement(HomePage.oneWay).click();

            }

            // Giving search cities
        Thread.sleep(1000);
        driver.findElement(HomePage.departureCity).sendKeys(origin);
        Thread.sleep(2000);
        driver.findElement(HomePage.option).click();
        driver.findElement(HomePage.arrivalCity).sendKeys(destination);

        Thread.sleep(2000);
        driver.findElement(HomePage.option).click();

        //Creates instance to select date
        HomePage dateSelect = new HomePage(driver);

        //Selecting date if trip is oneway
        if (tripType.equalsIgnoreCase("Oneway")){
            driver.findElement(HomePage.departureDate).click();
            m.departureMonthSelector(driver, departureMonth);

            // Call the date selector method with a dynamic parameter

            String DepartureDate = m.doubleToString(departureDate);

            WebElement departureDateElement = dateSelect.dateSelector(DepartureDate);

            departureDateElement.click();

            //Selecting date if trip is return
        } else if (tripType.equalsIgnoreCase("Return")) {

            driver.findElement(HomePage.departureDate).click();
            //Selecting month from calendar
            m.departureMonthSelector(driver, departureMonth);
            // Call the date selector method with a dynamic parameter
            // Select departure date
            String DepartureDate = m.doubleToString(departureDate);

            WebElement departureDateElement = dateSelect.dateSelector(DepartureDate);

            departureDateElement.click();


            m.returnMonthSelector(driver,departureMonth, returnMonth);

            // Call the date selector method with a dynamic parameter
            // Selecting return date

            String ReturnDate = m.doubleToString(returnDate);

            WebElement returnDateElement = dateSelect.dateSelector(ReturnDate);

            returnDateElement.click();
        }


        // Selecting Paxtype and count

        driver.findElement(HomePage.passengerSelector).click();

        //Instance of homepage for pax selection
        HomePage paxSelect = new HomePage(driver);

        paxSelect.paxSelector(adultCount, youngAdultCount, childCount, infantCount);

        driver.findElement(HomePage.applyPax).click();

        //Clicking search
        driver.findElement(HomePage.search).click();
        Thread.sleep(20);

        //Initializing wait explicitly
        WebDriverWait wait = new WebDriverWait(driver, 65);

        //Handling notification
        try {
            driver.switchTo().frame("webpush-onsite");
            driver.findElement(HomePage.denyNotification).click();

            driver.switchTo().defaultContent();
        } catch (NoSuchElementException | NoSuchFrameException e) {
            e.printStackTrace();

        }


        // Asserting result
        WebElement result = null;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(SRP.results));
            result = driver.findElement(SRP.results);
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
        }
        boolean isResultAvailable = false;
        try{
            isResultAvailable = result.isDisplayed();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if (isResultAvailable){
            System.out.println("Result loaded");
        }else {
            m.takeScreenshot(driver, Paths.screenshotFolder, screenShotPath);
            m.getConsole(driver);
            File screenShotFile = new File(screenShotPath);
            m.sendNotification(testCaseNumber, "Result not loaded or result not loaded within time limit");
            m.writeToExcel(testCaseNumber, 0, outputExcel);
            m.writeToExcel("-", 1, outputExcel);
            testStatus = "Failed";
            m.writeToExcel(testStatus, 2, outputExcel);
            m.writeToExcel("Result not loaded or result not loaded within time limit", 3, outputExcel);
            m.writeToExcel(m.getCID(driver), 4, outputExcel);
            m.writeToExcel(runTime, 5, outputExcel);
        }

            Assert.assertTrue(isResultAvailable, "Search result not loaded");


        //Selecting airline from filter
        if (isBundled.equalsIgnoreCase("Yes")) {

                driver.findElement(SRP.filters).click();

            Filters airlineInstance = new Filters(driver);

            // Call the airlineFilter method with a dynamic parameter

            try {

                WebElement airlineFilterElement = airlineInstance.airlineFilter(departureAirline);

                airlineFilterElement.click();
            }
            catch (NoSuchElementException e){
                m.writeToExcel(testCaseNumber, 0, outputExcel);
                m.writeToExcel("-", 1, outputExcel);
                testStatus = "Skipped";
                m.writeToExcel(testStatus, 2, outputExcel);
                m.writeToExcel(("Skipped this test because desired airline: "+departureAirline+" was not avaible in result"), 3, outputExcel);
                m.writeToExcel(m.getCID(driver), 4, outputExcel);
                m.writeToExcel(runTime, 5, outputExcel);

                throw new SkipException("Test is skipped as the given airline " +departureAirline+ " was not available in search result");
            }

            driver.findElement(Filters.apply).click();

            driver.findElement(SRP.book).click();
            Thread.sleep(1000);

        }

        else if (isBundled.equalsIgnoreCase("No")) {

            driver.findElement(SRP.unBundledFilter).click();

            Filters airlineInstance = new Filters(driver);

            // Call the airlineFilter method with a dynamic parameter

            try {

                WebElement airlineFilterElement = airlineInstance.airlineFilter(departureAirline);

                airlineFilterElement.click();
            }
            catch (NoSuchElementException e){
                m.writeToExcel(testCaseNumber, 0, outputExcel);
                m.writeToExcel("-", 1, outputExcel);
                testStatus = "Skipped";
                m.writeToExcel(testStatus, 2, outputExcel);
                m.writeToExcel(("Skipped this test because desired airline: "+departureAirline+" was not avaible in result"), 3, outputExcel);
                m.writeToExcel(m.getCID(driver), 4, outputExcel);
                m.writeToExcel(runTime, 5, outputExcel);

                throw new SkipException("Test is skipped as the given airline " +departureAirline+ " was not available in search result");
            }
            driver.findElement(Filters.returnAirline).click();

            // Call the airlineFilter method with a dynamic parameter

            try {


                WebElement airlineFilterElement = airlineInstance.airlineFilter(returnAirline);

                airlineFilterElement.click();
            }
            catch (NoSuchElementException e){

                m.writeToExcel(testCaseNumber, 0, outputExcel);
                m.writeToExcel("-", 1, outputExcel);
                testStatus = "Skipped";
                m.writeToExcel(testStatus, 2, outputExcel);
                m.writeToExcel(("Skipped this test because desired airline: "+returnAirline+" was not avaible in result"), 3, outputExcel);
                m.writeToExcel(m.getCID(driver), 4, outputExcel);
                m.writeToExcel(runTime, 5, outputExcel);

                throw new SkipException("Test is skipped as the given airline " +returnAirline+ " was not available in search result");
            }

            driver.findElement(Filters.apply).click();

            driver.findElement(SRP.outboundFlightUnbundled).click();
            driver.findElement(SRP.inboundFlightUnbundled).click();


            driver.findElement(SRP.domBook).click();


        }

        if (tripType.equalsIgnoreCase("Return")){
            try {
                driver.findElement(SRP.airPortChange).click();
            }catch (NoSuchElementException ne){
                ne.printStackTrace();
            }
        }

        Thread.sleep(1000);

        // Asserting Traveller Page
        WebElement travellerPage = null;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(FlightPage.flightReviewPage));
            travellerPage = driver.findElement(FlightPage.flightReviewPage);
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
        }
        boolean istravellerPageAvailable = false;
        try{
            istravellerPageAvailable = travellerPage.isDisplayed();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if (istravellerPageAvailable){
            System.out.println("Traveller page loaded");
        }else {
            m.takeScreenshot(driver, Paths.screenshotFolder, screenShotPath);
            m.getConsole(driver);
            File screenShotFile = new File(screenShotPath);
            m.sendNotification(testCaseNumber, "Not redirected to flight details screen or not redirected within 60 seconds");

            m.writeToExcel(testCaseNumber, 0, outputExcel);
            m.writeToExcel("-", 1, outputExcel);
            testStatus = "Failed";
            m.writeToExcel(testStatus, 2, outputExcel);
            m.writeToExcel(("Not redirected to flight details screen or not redirected within 60 seconds"), 3, outputExcel);
            m.writeToExcel(m.getCID(driver), 4, outputExcel);
            m.writeToExcel(runTime, 5, outputExcel);
        }

        boolean isTravellerPageAvailable = false;
        try {
            isTravellerPageAvailable = travellerPage.isDisplayed();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
            Assert.assertTrue(isTravellerPageAvailable, "Traveller page  not loaded");

        boolean isFAFlight;


        if (departureAirline.equalsIgnoreCase("FA") && returnAirline.equalsIgnoreCase("FA")) {
            isFAFlight = true;
        } else {
            isFAFlight = false;
        }


        String lastname;
        if (isFAFlight) {
            lastname = "Test";
        } else {
            lastname = lastName;
        }


        //Sending contact details in booking
        driver.findElement(FlightPage.mobileNo).sendKeys(mobileNumber);
        driver.findElement(FlightPage.email).sendKeys(mailID);

        if (whatsApp.equalsIgnoreCase("No")){

        driver.findElement(FlightPage.whatsApp).click();

        }
        Thread.sleep(500);

        //Adding PAX details

        WebElement paxTitle = null;

        if (title.equalsIgnoreCase("mr")){
            paxTitle =  driver.findElement(FlightPage.mr);
        }
        else if (title.equalsIgnoreCase("ms")){
            paxTitle = driver.findElement(FlightPage.ms);
        }
        else if (title.equalsIgnoreCase("mrs")) {
            paxTitle = driver.findElement(FlightPage.mrs);
        }
        paxTitle.click();


        //First Name
        driver.findElement(FlightPage.firstName).sendKeys(firstName);

        //Last Name
        driver.findElement(FlightPage.lastName).sendKeys(lastname);

        //Date of birth

        WebElement day = driver.findElement(FlightPage.dayDOB);
        WebElement month = driver.findElement(FlightPage.monthDOB);
        WebElement year = driver.findElement(FlightPage.yearDOB);

        //Selecting date of birth from dropdown
        Select daySelector = new Select(day);
        daySelector.selectByIndex(m.stringToInt(dateOfBirth));

        //Selecting date of birth from dropdown

        Select monthSelector = new Select(month);
        monthSelector.selectByIndex(m.stringToInt(monthOfBirth));

        //Selecting year of birth
        String yearOfBirthSelect = m.doubleToString(yearOfBirth);
        Select yearSelector = new Select(year);
        yearSelector.selectByValue(yearOfBirthSelect);

        //Add checked baggage baggage
        if (addBaggage.equalsIgnoreCase("Yes")){
            try {
                List<WebElement> addBaggageChecks = driver.findElements(FlightPage.addCheckedBaggage);
                for (WebElement addBaggageCheck : addBaggageChecks) {
                    addBaggageCheck.click();
                }
            }catch (NoSuchElementException e){
                System.out.println("Baggage addition is not available for this flight");
            }
        }

        //Passport details
        WebElement passPortInfo = null;
        try {
            passPortInfo = driver.findElement(FlightPage.ppInfo);
            passPortInfo = driver.findElement(FlightPage.ppInfo);
        } catch (NoSuchElementException ne) {
            ne.printStackTrace();
            System.out.println("PassPort details not required for this flight");
        }
        try {
            if (passPortInfo.isDisplayed()) {
                WebElement passportNumber = driver.findElement(FlightPage.ppNumber);
                passportNumber.sendKeys(passPortNumber);

                WebElement passportExpiryday = driver.findElement(FlightPage.ppExpiryDate);
                WebElement passportExpirymonth = driver.findElement(FlightPage.ppExpiryMonth);
                WebElement passportExpiryyear = driver.findElement(FlightPage.ppExpiryYear);

                Select passportExpirydaySelector = new Select(passportExpiryday);
                passportExpirydaySelector.selectByIndex(m.stringToInt(dateOfPassportExpiry));
                Select passportExpirymonthSelector = new Select(passportExpirymonth);
                passportExpirymonthSelector.selectByIndex(m.stringToInt(monthOfPassportExpiry));
                Select passportExpiryyearSelector = new Select(passportExpiryyear);
                passportExpiryyearSelector.selectByValue(m.doubleToString(yearOfPassportExpiry));

                driver.findElement(FlightPage.ppNationality).click();
                driver.findElement(FlightPage.ppnationalityIndia).click();

                Thread.sleep(1000);
                driver.findElement(FlightPage.ppIssuingCountry).click();
                driver.findElement(FlightPage.ppInsuingCountryIndia).click();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int adultCountTiInt = Integer.parseInt(adultCount);
        int youngAdultCountTiInt = Integer.parseInt(youngAdultCount);
        int childCountTiInt = Integer.parseInt(childCount);
        int infantCountTiInt = Integer.parseInt(infantCount);

        if (adultCountTiInt > 1 || youngAdultCountTiInt > 0 || childCountTiInt > 0 || infantCountTiInt > 0){
        m.paxSender(driver, adultCount, youngAdultCount, childCount, infantCount, departureAirline, returnAirline);
        }

        //Handling notification
        try {
            driver.switchTo().frame("webpush-onsite");
            driver.findElement(HomePage.denyNotification).click();

            driver.switchTo().defaultContent();
        } catch (NoSuchElementException | NoSuchFrameException e) {
            e.printStackTrace();

        }

        driver.findElement(FlightPage.contnue).click();
        System.out.println("Traveller details have been added");
        Thread.sleep(200);

        // To deselect addOns
        try {
            List<WebElement> selectedAddons = driver.findElements(AddOnsPage.selectedAddons);
            int numberOfSelectedAddOns = selectedAddons.size();

            for (int i = 0; i < numberOfSelectedAddOns;) {
                WebElement selectedAddon = selectedAddons.get(i);
                if (selectedAddon.isDisplayed()) {
                    selectedAddon.click();
                }
                // Re-find the list of selected addons after each interaction
                selectedAddons = driver.findElements(AddOnsPage.selectedAddons);
                int newNumberOfSelectedAddOns = selectedAddons.size();

                // Check if the number of selected addons has decreased
                if (newNumberOfSelectedAddOns < numberOfSelectedAddOns) {
                    numberOfSelectedAddOns = newNumberOfSelectedAddOns;
                    // Restart the loop as the indices may have shifted
                    continue;
                }

                numberOfSelectedAddOns = newNumberOfSelectedAddOns;
                i++; // Proceed to the next addon
            }
        } catch (Exception e) {
            System.out.println("No addons selected by default");
        }

        // To add flexi
        try {
            List<WebElement> availableAddons = driver.findElements(AddOnsPage.addOnName);
            List<WebElement> SelectAddons = driver.findElements(AddOnsPage.selectAddon);
            int numberOfAddOns = availableAddons.size();
            if (addFlexi.equalsIgnoreCase("Yes")){
                for (int i = 0; i < numberOfAddOns; ) {
                    WebElement availableAddon = availableAddons.get(i);
                    WebElement SelectaddOn = SelectAddons.get(i);
                    if (availableAddon.getText().contains("Flexible Travel Dates") && SelectaddOn.isDisplayed()) {
                        SelectaddOn.click();
                    }
                    // Re-find the list of selected addons after each interaction
                    availableAddons = driver.findElements(AddOnsPage.addOnName);
                    int newNumberOfAddOns = availableAddons.size();

                    // Check if the number of selected addons has decreased
                    if (newNumberOfAddOns < numberOfAddOns) {
                        numberOfAddOns = newNumberOfAddOns;
                        // Restart the loop as the indices may have shifted
                        continue;
                    }

                    numberOfAddOns = newNumberOfAddOns;
                    i++; // Proceed to the next addon
                }}
        } catch (NoSuchElementException e) {
            System.out.println("Add ons not available");
        }


        if (domain.equalsIgnoreCase("NG")){
            driver.findElement(AddOnsPage.checkBoxNG).click();
        }

        driver.findElement(AddOnsPage.contnue).click();
        Thread.sleep(200);
        try {
            driver.findElement(AddOnsPage.noIWillRiskIt).click();
        } catch (Exception e){

        }
        //Payment page

        String bookingReference = "";

        // Asserting Payment Page
        WebElement paymentPage = null;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.bookingReference));
            paymentPage = driver.findElement(PaymentPage.bookingReference);
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
        }
        boolean ispaymentPageAvailable = false;
        try{
            ispaymentPageAvailable = paymentPage.isDisplayed();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if (ispaymentPageAvailable){
            System.out.println("Payment page loaded");
            bookingReference = driver.findElement(PaymentPage.bookingReference).getText();
        }else {
            m.takeScreenshot(driver, Paths.screenshotFolder, screenShotPath);
            m.getConsole(driver);
            File screenShotFile = new File(screenShotPath);
            m.sendNotification(testCaseNumber, "Not redirected to payment screen or not redirected within 60 seconds");

            m.writeToExcel(testCaseNumber, 0, outputExcel);
            m.writeToExcel("-", 1, outputExcel);
            testStatus = "Failed";
            m.writeToExcel(testStatus, 2, outputExcel);
            m.writeToExcel(("Not redirected to payment page or not redirected within 60 seconds"), 3, outputExcel);
            m.writeToExcel(m.getCID(driver), 4, outputExcel);
            m.writeToExcel(runTime, 5, outputExcel);
        }

        Assert.assertTrue(ispaymentPageAvailable, "Payment page not loaded");

        String timeOne = "";
        String timeTwo = "";

        //Payment in ZA - EFT
        if (domain.equalsIgnoreCase("ZA")&& paymentMethod.equalsIgnoreCase("EFT")){
            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.EFT));
            driver.findElement(PaymentPage.EFT).click();

            switch (bankNameEFT) {

                //Banks
                case "Nedbank" -> driver.findElement(PaymentPage.nedBank).click();
                case "FNB" -> driver.findElement(PaymentPage.fnb).click();
                case "Standard Bank" -> driver.findElement(PaymentPage.standardBank).click();
                case "ABSA" -> driver.findElement(PaymentPage.absa).click();


                default -> System.out.println("Invalid bank name");

            }

            driver.findElement(PaymentPage.payNow).click();

        }

        //Paying with card
        else if (paymentMethod.equalsIgnoreCase("cc/dc")){
            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.credicCardOrDebitCard));
            driver.findElement(PaymentPage.credicCardOrDebitCard).click();
            String cardNumber = m.readDataFromExcel(dataPath, "Card detals", 2,1);
            String cardHolderName = m.readDataFromExcel(dataPath, "Card detals", 2,2);
            int cardExpiryMonth = m.stringToInt(m.readDataFromExcel(dataPath, "Card detals", 2,3));
            String cardExpiryYear = m.readDataFromExcel(dataPath, "Card detals", 2,4);
            String CVV = m.doubleToString(m.readDataFromExcel(dataPath, "Card detals", 2,5));
            String AddressLine1 = m.readDataFromExcel(dataPath, "Card detals", 2,6);
            String AddressLine2 = m.readDataFromExcel(dataPath, "Card detals", 2,7);
            String PostalCode = m.doubleToString(m.readDataFromExcel(dataPath, "Card detals", 2,8));
            String city = m.readDataFromExcel(dataPath, "Card detals", 2,9);
            String country = m.readDataFromExcel(dataPath, "Card detals", 2,10);
            String contactNumber = m.readDataFromExcel(dataPath, "Card detals", 2,11);



            driver.findElement(PaymentPage.cardNumber).sendKeys(cardNumber);
            driver.findElement(PaymentPage.cardHolderName).sendKeys(cardHolderName);

            WebElement CardExpiryMonthElement = driver.findElement(PaymentPage.cardExpiryMonth);
            WebElement CardExpiryYearElement = driver.findElement(PaymentPage.cardExpiryYear);



            Select expiryMonthSelector = new Select(CardExpiryMonthElement);
            expiryMonthSelector.selectByIndex(cardExpiryMonth);

            Select expiryYearSelector = new Select(CardExpiryYearElement);
            expiryYearSelector.selectByValue(cardExpiryYear);

            driver.findElement(PaymentPage.CVV).sendKeys(CVV);


//            driver.findElement(PaymentPage.addressLine1).sendKeys(AddressLine1);
//            driver.findElement(PaymentPage.addressLine2).sendKeys(AddressLine2);
//
//            driver.findElement(PaymentPage.postalCode).sendKeys(PostalCode);
//            driver.findElement(PaymentPage.city).sendKeys(city);
//
//            driver.findElement(PaymentPage.country).click();
//            Thread.sleep(500);
//            driver.findElement(PaymentPage.countryIndia).click();
//            driver.findElement(PaymentPage.contactNo).sendKeys(m.doubleToString(contactNumber));

            driver.findElement(PaymentPage.payNow).click();


        }

        // Pay using iPay
        else if (domain.equalsIgnoreCase("ZA") && (paymentMethod.equalsIgnoreCase("Instant EFT") || paymentMethod.equalsIgnoreCase("IPAY"))) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.iPay));
            driver.findElement(PaymentPage.iPay).click();
            //driver.findElement(PaymentPage.payNow).click();

        }

        // Pay using NG EFT
        else if (domain.equalsIgnoreCase("NG") && paymentMethod.equalsIgnoreCase("EFT")){

            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.EFT));
            driver.findElement(PaymentPage.EFT).click();

            switch (bankNameEFT) {

                //Banks
                case "travelstart" -> driver.findElement(PaymentPage.travelStart).click();
                case "Access" -> driver.findElement(PaymentPage.access).click();
                case "UBA" -> driver.findElement(PaymentPage.UBA).click();
                case "Zenith" -> driver.findElement(PaymentPage.zenithBank).click();


                default -> System.out.println("Invalid bank name");

            }
            driver.findElement(PaymentPage.reserve).click();

        }

        //Pay using Paystack
        else if (domain.equalsIgnoreCase("NG") && (paymentMethod.equalsIgnoreCase("Instant EFT")||paymentMethod.equalsIgnoreCase("Paystack"))) {

            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.payStack));
            driver.findElement(PaymentPage.payStack).click();

            driver.findElement(PaymentPage.payNow).click();

        }
        timeOne = m.getCurrentTime();


        Thread.sleep(10000);

        // Asserting Booking confirmation Page
        WebElement bookingConfirm = null;

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(BookingConfirmationPage.isBookingConfirmed));
            timeTwo = m.getCurrentTime();
            bookingConfirm = driver.findElement(BookingConfirmationPage.isBookingConfirmed);
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
        }
        boolean isbookingRefAvailable = false;
        try{
            isbookingRefAvailable = bookingConfirm.isDisplayed();
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (TimeoutException te){
            System.out.println("Booking failed due to this booking took more than 1 minute");
        }
        if (isbookingRefAvailable){
            bookingReference = driver.findElement(BookingConfirmationPage.refNumber).getText();
            bookingReference = bookingReference.trim();
            System.out.println("Booking completed. "+ bookingReference);

            m.writeToExcel(testCaseNumber, 0, outputExcel);
            m.writeToExcel(bookingReference, 1, outputExcel);
            testStatus = "Passed";
            m.writeToExcel(testStatus, 2, outputExcel);
            m.writeToExcel(("Booking completed"), 3, outputExcel);
            m.writeToExcel(m.getCID(driver), 4, outputExcel);
            m.writeToExcel(runTime, 5, outputExcel);
        }else {
            m.takeScreenshot(driver, Paths.screenshotFolder, screenShotPath);
            m.getConsole(driver);
            File screenShotFile = new File(screenShotPath);
            m.sendNotification(testCaseNumber, "Booking not succeeded");
            m.writeToExcel(testCaseNumber, 0, outputExcel);
            m.writeToExcel(bookingReference, 1, outputExcel);
            testStatus = "Failed";
            m.writeToExcel(testStatus, 2, outputExcel);
            m.writeToExcel(("Booking not succeeded"), 3, outputExcel);
            m.writeToExcel(m.getCID(driver), 4, outputExcel);
            m.writeToExcel(runTime, 5, outputExcel);
        }

            Assert.assertTrue(isbookingRefAvailable, "Booking failed");

        Long timeTookForBooking = null;

        if (isbookingRefAvailable){
            timeTookForBooking = m.timeCalculator(timeOne, timeTwo);
        }

        boolean isBookingDoneWithinTime = false;
        isBookingDoneWithinTime = timeTookForBooking <= 45;

        if (!isBookingDoneWithinTime){
            m.sendNotification(testCaseNumber, "Booking not completed within 45 seconds, time took for booking is "+ timeTookForBooking + " seconds");
            m.writeToExcel(testCaseNumber, 0, outputExcel);
            m.writeToExcel(bookingReference, 1, outputExcel);
            testStatus = "Failed";
            m.writeToExcel(testStatus, 2, outputExcel);
            m.writeToExcel(("Booking not completed within 45 seconds, time took for booking is "+ timeTookForBooking +" seconds"), 3, outputExcel);
            m.writeToExcel(m.getCID(driver), 4, outputExcel);
            m.writeToExcel(runTime, 5, outputExcel);
        }

        Assert.assertTrue(isBookingDoneWithinTime, "Booking not completed within 45 seconds");

    }

}
