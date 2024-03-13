package test.ScheduleTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.*;
import testmethods.Method;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;


public class test {

    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = Paths.dataPath;
    static String environment;

    ChromeOptions options = new ChromeOptions();

    String outputPath = "screen_capture.mov"; // Adjust the file extension as per your requirements

    // Create an instance of ScreenRecorder


    private Filters page;

    static {
        try {
            environment = m.readDataFromExcel(dataPath,0,0,1);
            environment = "live";
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
            driver.get(m.readDataFromExcel(dataPath,0,3,1));
        } else if (environment.equals("beta")) {
            driver.get(m.readDataFromExcel(dataPath,0,5,1));
        } else if (environment.equals("preprod")) {
            driver.get(m.readDataFromExcel(dataPath,0,7,1));
        } else {
            System.out.println("Invalid envinorment name");
        }

        environment = "live";

        page = new Filters(driver);
        //accept all cookies
        driver.manage().deleteAllCookies();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @DataProvider(name = "cityData")
    public Object[][] getCityData() throws IOException {
        return new Object[][] {

                {m.readDataFromExcel(dataPath,1,4,0), m.readDataFromExcel(dataPath,1,4,1)},
                {m.readDataFromExcel(dataPath,1,4,0), m.readDataFromExcel(dataPath,1,4,1)},
                {m.readDataFromExcel(dataPath,1,4,0), m.readDataFromExcel(dataPath,1,4,1)},
                {m.readDataFromExcel(dataPath,1,4,0), m.readDataFromExcel(dataPath,1,4,1)},
                {m.readDataFromExcel(dataPath,1,4,0), m.readDataFromExcel(dataPath,1,4,1)},
                {m.readDataFromExcel(dataPath,1,4,0), m.readDataFromExcel(dataPath,1,4,1)},
                {m.readDataFromExcel(dataPath,1,4,0), m.readDataFromExcel(dataPath,1,4,1)},
                {m.readDataFromExcel(dataPath,1,4,0), m.readDataFromExcel(dataPath,1,4,1)},
                {m.readDataFromExcel(dataPath,1,4,0), m.readDataFromExcel(dataPath,1,4,1)},
                {m.readDataFromExcel(dataPath,1,4,0), m.readDataFromExcel(dataPath,1,4,1)}

        };
    }

    @Test(dataProvider = "cityData", priority = 1, description = "To verify that the result is getting loaded or not")
    public void search(String departureCity, String arrivalCity) throws Exception {
        SoftAssert assrt = new SoftAssert();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
        WebElement result = null;
        try {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(SRP.results));
                result = driver.findElement(SRP.results);
            } catch (TimeoutException t) {
                System.out.println("Result not loaded!");
                m.takeScreenshot(driver, Paths.screenshotFolder);
            }

        } catch (NoSuchElementException e) {

            e.printStackTrace();

        }

        //Click on Book

        driver.findElement(SRP.book).click();
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        WebElement flightReviewPage = null;
        boolean isFlightPagePass = false;
        try {
            wait1.until(ExpectedConditions.visibilityOfElementLocated(FlightPage.flightReviewPage));
            flightReviewPage = driver.findElement(FlightPage.flightReviewPage);
            isFlightPagePass = flightReviewPage.isDisplayed();

            assrt.assertTrue(isFlightPagePass, "Not redirected to flight page");
        }catch (NoSuchElementException e){
            e.printStackTrace();
            // Handle NoSuchElementException
        }

        try{
            assert flightReviewPage != null;
            assrt.assertTrue(flightReviewPage.isDisplayed(), "Not redirected to flight page");
            if(!isFlightPagePass){
                // Get the browser's console logs
                LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);

                // Check for errors in console logs
                for (LogEntry entry : logEntries) {
                    if (entry.getLevel().toString().equalsIgnoreCase("SEVERE")) {
                        System.out.println("Error found in console: " + entry.getMessage());
                    }
                }
            }

        } catch (NullPointerException e){
            // Get the browser's console logs
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);

            // Check for errors in console logs
            for (LogEntry entry : logEntries) {
                if (entry.getLevel().toString().equalsIgnoreCase("SEVERE")) {
                    System.out.println("Error found in console: " + entry.getMessage());
                }
            }
        }
        Thread.sleep(10000);

        assrt.assertAll("Test Completed");

    }

    @AfterMethod
    public void close(){
        driver.quit();

    }



}