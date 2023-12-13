package test;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pageObjects.*;


import javax.xml.xpath.XPath;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Booking {
    static WebDriver driver;
    static String path = "C:\\Users\\Sreen\\eclipse-workspace\\travelStart\\src\\test\\resources\\configFiles\\config.properties";
    static testmethods.Method m = new testmethods.Method();
    static String dataPath = "C:\\Users\\Sreen\\IdeaProjects\\travelStart\\TestData\\DataBook.xls";

    @BeforeTest
    public void setup() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sreen\\OneDrive\\Documents\\QA\\Selenium\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(m.readDataFromExcel(dataPath,0,3,1));


    }
    @AfterTest
    public void close(){
       // driver.quit();
    }
    @Test(priority = 1)
    public void search() throws Exception {


        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        Thread.sleep(1000);
        driver.findElement(HomePage.departureCity).sendKeys("JNB");
        Thread.sleep(2000);
        driver.findElement(HomePage.option).click();
        driver.findElement(HomePage.arrivalCity).sendKeys("LON");
        Thread.sleep(2000);
        driver.findElement(HomePage.option).click();
        driver.findElement(HomePage.departureDate).click();
        for (int i = 0; i < 4; i++) {
            Thread.sleep(1000);
            driver.findElement(HomePage.nextMonth).click();
        }
        driver.findElement(HomePage.day).click();
        driver.findElement(HomePage.day2).click();

        driver.findElement(HomePage.search).click();
        Thread.sleep(27000);

        WebElement result = driver.findElement(SRP.results);
        Assert.assertTrue(result.isDisplayed(), "No search result loaded");


        By bookNowButtonLocator = SRP.book;

        // Wait for the button to be clickable
        WebElement bookNowButton = waitForElementToBeClickable(driver, bookNowButtonLocator);

        // Click the "Book Now" button
        bookNowButton.click();
        Thread.sleep(5000);

    }

    private static WebElement waitForElementToBeClickable(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    @Test(priority = 2)
    public void flightReviewPage() throws Exception {
        String currentUrl = driver.getCurrentUrl();

        // Verify HTTP status code using Apache HttpClient
        int statusCode = getStatusCode(currentUrl);
        Assert.assertEquals(statusCode, 200, "HTTP status code is not 200.");

    }
    private int getStatusCode(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseString = EntityUtils.toString(entity);
                    System.out.println("Response: " + responseString);
                }
                return response.getCode();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return -1; // Return a special value to indicate an error
        }
    }
    @Test(priority = 3)
    public void travellerPage() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement day = wait.until(ExpectedConditions.presenceOfElementLocated(FlightPage.dayDOB));
        WebElement month = wait.until(ExpectedConditions.presenceOfElementLocated(FlightPage.monthDOB));
        WebElement year = wait.until(ExpectedConditions.presenceOfElementLocated(FlightPage.yearDOB));

        driver.findElement(FlightPage.mobileNo).sendKeys(m.readDataFromExcel(dataPath,0,12,1));
        driver.findElement(FlightPage.email).sendKeys(m.readDataFromExcel(dataPath,0,13,1));
        driver.findElement(FlightPage.whatsApp).click();
        Thread.sleep(1000);
        driver.findElement(FlightPage.whatsApp).click();
        driver.findElement(FlightPage.mr).click();
        driver.findElement(FlightPage.firstName).sendKeys(m.readDataFromExcel(dataPath,0,14,1));
        driver.findElement(FlightPage.lastName).sendKeys(m.readDataFromExcel(dataPath,0,15,1));
        Select daysc = new Select(day);
        daysc.selectByIndex(4);
        Select monthsc = new Select(month);
        monthsc.selectByIndex(6);
        Select yearsc = new Select(year);
        yearsc.selectByValue("1999");
        driver.findElement(FlightPage.meal).click();
        driver.findElement(FlightPage.vegetarianMeal).click();
        driver.findElement(FlightPage.contnue).click();
        System.out.println("Traveller details have been added");
        /*driver.findElement(AddOnsPage.editSeats).click();
        driver.findElement(By.xpath("(//*[text()='18B'])[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//*[text()='24B'])[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//*[text()='43B'])[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//*[text()='20B'])[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@class='btn primary_btn ml-auto st_btn addons_continueBtn float-right']")).click();
        */
        driver.findElement(AddOnsPage.contnue).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[text()='Yes, Continue']")).click();
        driver.findElement(By.xpath("//a[@id='eft-tab']")).click();
        driver.findElement(By.xpath("//img[@alt='nedbank']")).click();
        driver.findElement(By.xpath("//span[@class='ng-star-inserted'][normalize-space()='Pay Now']")).click();
        String refNo = driver.findElement(By.xpath("//label[@class='ref_details']")).getText();
        System.out.println(refNo);
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL,"https://www.travelstart.co.za/payments/bookingConfirm","Booking failed");

    }


}
