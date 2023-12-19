package test;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.*;


import java.awt.print.Book;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Booking_Int_Return {
    static WebDriver driver;
    static testmethods.Method m = new testmethods.Method();
    static String dataPath = "C:\\Users\\Dell\\IdeaProjects\\travelStart\\TestData\\DataBook.xls";
    static String environment;

    static {
        try {
            environment = m.readDataFromExcel(dataPath,0,0,1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Booking_Int_Return() throws IOException {
    }

    @BeforeClass
    public void setup() throws Exception {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Documents\\chromedriver-win32\\chromedriver-win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        if (environment == "Live") {
            driver.get(m.readDataFromExcel(dataPath, 0, 3, 1));
        } else if (environment == "Beta") {
            driver.get(m.readDataFromExcel(dataPath, 0, 5, 1));
        } else if (environment == "Preprod") {
            driver.get(m.readDataFromExcel(dataPath, 0, 7, 1));
        }
    }
    @AfterClass
    public void close(){
     driver.quit();
    }
    @Test(priority = 1)
    public void search() throws Exception {

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Thread.sleep(1000);
        driver.findElement(HomePage.departureCity).sendKeys(m.readDataFromExcel(dataPath,1,2,0));
        Thread.sleep(2000);
        driver.findElement(HomePage.option).click();
        driver.findElement(HomePage.arrivalCity).sendKeys(m.readDataFromExcel(dataPath,1,2,1));

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

        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement result = null;
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(SRP.results));
            result = driver.findElement(SRP.results);
        } catch (NoSuchElementException e){
            System.out.println("Search result loaded");
        }
        Assert.assertTrue(result.isDisplayed(),"Search result loaded");

        driver.findElement(SRP.book).click();
        Thread.sleep(1000);
        try {
            driver.findElement(SRP.airPortChange).click();
        }catch (NoSuchElementException ne){
            ne.printStackTrace();
        }
        Thread.sleep(5000);

    }


    @Test(priority = 2)
    public void flightReviewPage() {

        Duration timeout = Duration.ofSeconds(30);

        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement travellerPage = null;
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(FlightPage.flightReviewPage));
            travellerPage = driver.findElement(FlightPage.flightReviewPage);
        } catch (NoSuchElementException e){
            System.out.println("Traveller page not loaded");
        }
        Assert.assertTrue(travellerPage.isDisplayed(),"Traveller page not loaded");

    }

    @Test(priority = 3)
    public void travellerPage() throws Exception {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);


            //Waits for DOB dropdowns to be located

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
            driver.findElement(FlightPage.lastName).sendKeys(m.readDataFromExcel(dataPath, 2, 11, 4));
            Select daysc = new Select(day);
            String yearr = m.readDataFromExcel(dataPath, 2, 11, 7);
            daysc.selectByIndex(4);
            Select monthsc = new Select(month);
            monthsc.selectByIndex(6);
            Select yearsc = new Select(year);
            yearsc.selectByValue("1999");

            //Handling notification
            try {
                driver.switchTo().frame("webpush-onsite");
                driver.findElement(HomePage.denyNotification).click();

                driver.switchTo().defaultContent();
            }catch (NoSuchElementException | NoSuchFrameException e){
                e.printStackTrace();

            }

        //Passport details
        WebElement ppInfo = null;
        try{
            ppInfo = driver.findElement(FlightPage.ppInfo);
        }catch (NoSuchElementException ne){
            ne.printStackTrace();
            System.out.println("PassPort details not required for this flight");
        }

        if(ppInfo.isDisplayed()) {
            WebElement ppNumber = driver.findElement(FlightPage.ppNumber);
            ppNumber.sendKeys(m.readDataFromExcel(dataPath, 2, 11, 8));

            WebElement ppday = driver.findElement(FlightPage.ppExpiryDate);
            WebElement ppmonth = driver.findElement(FlightPage.ppExpiryMonth);
            WebElement ppyear = driver.findElement(FlightPage.ppExpiryYear);

            Select ppdaysc = new Select(ppday);
            Select ppmonthsc = new Select(ppmonth);
            Select ppyearsc = new Select(ppyear);

            ppdaysc.selectByIndex(1);
            ppmonthsc.selectByIndex(1);
            ppyearsc.selectByValue("2029");

            driver.findElement(FlightPage.ppNationality).click();
            driver.findElement(By.xpath("//*[text()='India']")).click();
            Thread.sleep(1000);
            driver.findElement(FlightPage.ppIssuingCountry).click();
            driver.findElement(By.xpath("(//*[text()='India'])[2]")).click();

        }

        }

    @Test(priority = 4) @Ignore
    public void booking() throws InterruptedException {

        Duration timeout = Duration.ofSeconds(45);

        WebDriverWait wait = new WebDriverWait(driver, timeout);

        //Payment using EFT
        wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.EFT));
        driver.findElement(PaymentPage.EFT).click();
        driver.findElement(PaymentPage.nedBank).click();
        driver.findElement(PaymentPage.payNow).click();
        Thread.sleep(10000);

        WebElement refNmbr = null;
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(BookingConfirmationPage.refNumber));
            refNmbr = driver.findElement(BookingConfirmationPage.refNumber);
            String refNumber = refNmbr.getText();
            System.out.println(refNumber);
        } catch (NoSuchElementException e){
            System.out.println("Booking Failed");
        }
        Assert.assertTrue(refNmbr.isDisplayed(),"Booking not completed");
    }



}
