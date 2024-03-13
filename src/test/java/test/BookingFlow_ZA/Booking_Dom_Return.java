package test.BookingFlow_ZA;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObjects.*;

import java.io.IOException;
import java.time.Duration;

public class Booking_Dom_Return {

    static WebDriver driver;
    static testmethods.Method m = new testmethods.Method();
    static String dataPath = Paths.dataPath;
    static String environment;

    static {
        try {
            environment = m.readDataFromExcel(dataPath,0,0,1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Booking_Dom_Return() throws IOException {
    }

    @BeforeMethod
    public void setup() throws Exception {
        System.setProperty("webdriver.chrome.driver", Paths.chromeDriver);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        if (environment.equals("live")){
            driver.get(m.readDataFromExcel(dataPath,0,3,1));
        } else if (environment.equals("beta")) {
            driver.get(m.readDataFromExcel(dataPath,0,5,1));
        } else if (environment.equals("preprod")) {
            driver.get(m.readDataFromExcel(dataPath,0,7,1));
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
        if (result.getStatus() == ITestResult.FAILURE) {
            // If test fails, print the correlation ID
            System.out.println("Test Failed! Correlation ID: " + m.getCID(driver));
        }
        driver.quit();
    }

    @DataProvider(name = "cityData")
    public Object[][] getCityData() throws IOException {
        return new Object[][] {
                {m.readDataFromExcel(dataPath,1,7,0), m.readDataFromExcel(dataPath,1,7,1)},
                {m.readDataFromExcel(dataPath,1,8,0), m.readDataFromExcel(dataPath,1,8,1)},
                {m.readDataFromExcel(dataPath,1,9,0), m.readDataFromExcel(dataPath,1,9,1)}
        };
    }

    @Test(dataProvider = "cityData", priority = 1)
    public void search(String departureCity, String arrivalCity) throws Exception {
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
        driver.findElement(HomePage.day2).click();

        driver.findElement(HomePage.search).click();
        Thread.sleep(20);

        Duration timeout = Duration.ofSeconds(45);

        WebDriverWait wait = new WebDriverWait(driver, 45);
        WebElement result = null;
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(SRP.results));
            result = driver.findElement(SRP.results);
        } catch (NoSuchElementException e){
            System.out.println("Booking Failed");
            if (result.isDisplayed()==false){
                m.takeScreenshot(driver,Paths.screenshotFolder);
                m.getConsole(driver);
            }
        }catch (TimeoutException e){
            if (result.isDisplayed()==false){
                m.takeScreenshot(driver,Paths.screenshotFolder);
                m.getConsole(driver);
            }
        }
        Assert.assertTrue(result.isDisplayed(),"b_Search result loaded");

        driver.findElement(SRP.domBook).click();
        Thread.sleep(1000);
        try {
            driver.findElement(SRP.airPortChange).click();
        }catch (NoSuchElementException ne){
            ne.printStackTrace();
        }
        Thread.sleep(5000);

        WebElement travellerPage = null;
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(FlightPage.flightReviewPage));
            travellerPage = driver.findElement(FlightPage.flightReviewPage);
        } catch (NoSuchElementException e){
            System.out.println("Traveller page not loaded");
            if (travellerPage.isDisplayed()==false){
                m.takeScreenshot(driver,Paths.screenshotFolder);
                m.getConsole(driver);
            }
        }catch (TimeoutException e){
            if (travellerPage.isDisplayed()==false){
                m.takeScreenshot(driver,Paths.screenshotFolder);
                m.getConsole(driver);
            }
        }
        Assert.assertTrue(travellerPage.isDisplayed(),"Traveller page not loaded");

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
        driver.findElement(FlightPage.mobileNo).sendKeys(m.readDataFromExcel(dataPath, 2, 3, 1));
        driver.findElement(FlightPage.email).sendKeys(m.readDataFromExcel(dataPath, 2, 4, 1));
        driver.findElement(FlightPage.whatsApp).click();
        Thread.sleep(1000);
        driver.findElement(FlightPage.whatsApp).click();

        //Adding PAX details
        driver.findElement(FlightPage.mr).click();
        driver.findElement(FlightPage.firstName).sendKeys(m.readDataFromExcel(dataPath, 2, 11, 2));

        String lastname;
        if (isFAFlight){
            lastname = "Test";
        } else{
            lastname = m.readDataFromExcel(dataPath, 2, 11, 4);
        }

        driver.findElement(FlightPage.lastName).sendKeys(lastname);


        //Date of birth
        Select daysc = new Select(day);
        String yearr = m.readDataFromExcel(dataPath, 2, 11, 7);
        String yearOfBirth = m.doubleToString(yearr);
        daysc.selectByIndex(m.stringToInt(m.readDataFromExcel(dataPath, 2, 11, 5)));
        Select monthsc = new Select(month);
        monthsc.selectByIndex(m.stringToInt(m.readDataFromExcel(dataPath, 2, 11, 6)));
        Select yearsc = new Select(year);
        yearsc.selectByValue(yearOfBirth);

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

            //From Add-Ons
            driver.findElement(AddOnsPage.contnue).click();
            Thread.sleep(1000);

        //Payment using EFT
        wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.EFT));
        driver.findElement(PaymentPage.EFT).click();
        driver.findElement(PaymentPage.nedBank).click();
       // driver.findElement(PaymentPage.payNow).click();
        Thread.sleep(10000);


        WebElement refNmbr = null;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(BookingConfirmationPage.refNumber));
            refNmbr = driver.findElement(BookingConfirmationPage.refNumber);
            String refNumber = refNmbr.getText();
            System.out.println(refNumber);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } catch (TimeoutException te) {
            te.printStackTrace();

        }

        if (refNmbr != null) {
            System.out.println("Booking success");
        } else {
            m.takeScreenshot(driver, Paths.screenshotFolder);
            m.getConsole(driver);
        }

        Assert.assertTrue(refNmbr.isDisplayed(), "Booking not completed");

    }

}