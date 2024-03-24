package test.BookingFlow_RouteWise.BookingFlow_NG;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Booking_Oneway {

    static WebDriver driver;
    static testmethods.Method m = new testmethods.Method();
    static String dataPath = Paths.dataPath;
    static String environment;

    static {
        try {
            environment = m.readDataFromExcel(dataPath,"URL's",0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void setup() throws Exception {
        System.setProperty("webdriver.chrome.driver", Paths.chromeDriver);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        if (environment.equals("live")){
            driver.get(m.readDataFromExcel(dataPath,"URL's",4,1));
        } else if (environment.equals("beta")) {
            driver.get(m.readDataFromExcel(dataPath,"URL's",6,1));
        } else if (environment.equals("preprod")) {
            driver.get(m.readDataFromExcel(dataPath,"URL's",8,1));
        } else {
            System.out.println("Invalid envinorment name");
        }

        //accept all cookies
        driver.manage().deleteAllCookies();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @AfterMethod
    public void close(ITestResult result){
        if (result.getStatus() == result.FAILURE) {
            // If test fails, print the correlation ID
            System.out.println("Test Failed! Correlation ID: " + m.getCID(driver));
        }
        driver.quit();
    }
    @DataProvider(name = "cityData")
    public Object[][] getCityData() throws IOException {
        List<Object[]> cityData = new ArrayList<>();
        int totalRouteCount = m.getRowCount(dataPath, "Oneway Routes");
        for (int i = 1; i < totalRouteCount; i++) { // Start from 1 if data starts from row 2
            String departureCity = m.readDataFromExcel(dataPath, "Oneway Routes", i, 0);
            String returnCity = m.readDataFromExcel(dataPath, "Oneway Routes", i, 1);
            cityData.add(new Object[]{departureCity, returnCity});
        }
        return cityData.toArray(new Object[0][]);
    }

    @Test(dataProvider = "cityData", priority = 1)
    public void search(String departureCity, String arrivalCity) throws Exception {
        Thread.sleep(2000);
        driver.findElement(HomePage.oneWay).click();
        Thread.sleep(1000);
        driver.findElement(HomePage.departureCity).sendKeys(departureCity);
        Thread.sleep(2000);
        driver.findElement(HomePage.option).click();
        driver.findElement(HomePage.arrivalCity).sendKeys(arrivalCity);

        Thread.sleep(2000);
        driver.findElement(HomePage.option).click();
        driver.findElement(HomePage.departureDate).click();
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            driver.findElement(HomePage.nextMonth).click();
        }
        driver.findElement(HomePage.day).click();

        driver.findElement(HomePage.search).click();
        Thread.sleep(20);
        WebDriverWait wait = new WebDriverWait(driver, 45);
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
            m.takeScreenshot(driver, Paths.screenshotFolder);
            m.getConsole(driver);
        }

        Assert.assertTrue(result.isDisplayed(), "Search result not loaded");

        driver.findElement(SRP.book).click();
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
            m.takeScreenshot(driver, Paths.screenshotFolder);
            m.getConsole(driver);
        }

        Assert.assertTrue(travellerPage.isDisplayed(), "Traveller page  not loaded");

        boolean isFAFlight;

        String airline = driver.findElement(FlightPage.airline1).getText();
        if(airline.contains("FlySafair")){
            isFAFlight = true;
        }else{
            isFAFlight = false;
        }

        WebElement day = driver.findElement(FlightPage.dayDOB);
        WebElement month = driver.findElement(FlightPage.monthDOB);
        WebElement year = driver.findElement(FlightPage.yearDOB);

        //Sending contact details in booking
        driver.findElement(FlightPage.mobileNo).clear();
        driver.findElement(FlightPage.mobileNo).sendKeys(m.readDataFromExcel(dataPath,"PAX Details",3,14));
        driver.findElement(FlightPage.email).sendKeys(m.readDataFromExcel(dataPath,"PAX Details",3,15));
        driver.findElement(FlightPage.whatsApp).click();
        Thread.sleep(500);

        //Adding PAX details
        driver.findElement(FlightPage.mr).click();
        driver.findElement(FlightPage.firstName).sendKeys(m.readDataFromExcel(dataPath,"PAX Details",3,2));

        String lastname;
        if (isFAFlight) {
            lastname = "Test";
        } else {
            lastname = m.readDataFromExcel(dataPath,"PAX Details",3,4);
        }

        driver.findElement(FlightPage.lastName).sendKeys(lastname);

        //Date of birth
        Select daysc = new Select(day);
        String yearr = m.readDataFromExcel(dataPath, "PAX Details", 3, 7);
        String yearOfBirth = m.doubleToString(yearr);
        daysc.selectByIndex(m.stringToInt(m.readDataFromExcel(dataPath, "PAX Details", 3, 5)));
        Select monthsc = new Select(month);
        monthsc.selectByIndex(m.stringToInt(m.readDataFromExcel(dataPath, "PAX Details", 3, 6)));
        Select yearsc = new Select(year);
        yearsc.selectByValue(yearOfBirth);

        //Passport details
        WebElement ppInfo = null;
        try {
            ppInfo = driver.findElement(FlightPage.ppInfo);
            ppInfo = driver.findElement(FlightPage.ppInfo);
        } catch (NoSuchElementException ne) {
            ne.printStackTrace();
            System.out.println("PassPort details not required for this flight");
        }
        try {
            if (ppInfo.isDisplayed()) {
                WebElement ppNumber = driver.findElement(FlightPage.ppNumber);
                String ppnumb = m.readDataFromExcel(dataPath, "PAX Details", 3, 8);
                ppNumber.sendKeys(m.doubleToString(ppnumb));
                WebElement ppday = driver.findElement(FlightPage.ppExpiryDate);
                WebElement ppmonth = driver.findElement(FlightPage.ppExpiryMonth);
                WebElement ppyear = driver.findElement(FlightPage.ppExpiryYear);

                Select ppdaysc = new Select(ppday);
                ppdaysc.selectByIndex(1);
                Select ppmonthsc = new Select(ppmonth);
                ppmonthsc.selectByIndex(1);
                Select ppyearsc = new Select(ppyear);
                ppyearsc.selectByValue("2029");

                driver.findElement(FlightPage.ppNationality).click();
                driver.findElement(FlightPage.ppnationalityIndia).click();
                Thread.sleep(1000);
                driver.findElement(FlightPage.ppIssuingCountry).click();
                driver.findElement(FlightPage.ppInsuingCountryIndia).click();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Handling notification
        try {
            driver.switchTo().frame("webpush-onsite");
            driver.findElement(HomePage.denyNotification).click();

            driver.switchTo().defaultContent();
        } catch (NoSuchElementException e) {
            e.printStackTrace();

        } catch (NoSuchFrameException f){
            f.printStackTrace();
        }

        driver.findElement(FlightPage.contnue).click();
        System.out.println("Traveller details have been added");

        //From Add-Ons
        driver.findElement(AddOnsPage.checkBoxNG).click();
        driver.findElement(AddOnsPage.contnue).click();
        Thread.sleep(1000);


        //Payment using EFT
        wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.EFT));
        driver.findElement(PaymentPage.EFT).click();
       //driver.findElement(PaymentPage.reserve).click();
        Thread.sleep(10000);


        // Asserting Booking confirmation Page
        WebElement bookingRef = null;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(BookingConfirmationPage.refNumber));
            bookingRef = driver.findElement(BookingConfirmationPage.refNumber);
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
        }
        boolean isbookingRefAvailable = false;
        try{
            isbookingRefAvailable = bookingRef.isDisplayed();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if (isbookingRefAvailable){
            System.out.println("Booking completed. "+ bookingRef.getText());
        }else {
            m.takeScreenshot(driver, Paths.screenshotFolder);
            m.getConsole(driver);
        }

        Assert.assertTrue(isbookingRefAvailable, "Booking failed");

    }

}