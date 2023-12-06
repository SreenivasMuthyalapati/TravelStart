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
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Booking {
    static WebDriver driver;
    static String path = "C:\\Users\\Sreen\\eclipse-workspace\\travelStart\\src\\test\\resources\\configFiles\\config.properties";
    static testmethods.Method m = new testmethods.Method();

    @BeforeTest
    public void setup() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sreen\\OneDrive\\Documents\\QA\\Selenium\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(m.ReadPropertyFile(path, "LiveZAUrl"));
    }
    @AfterTest
    public void close(){
       // driver.quit();
    }
    @Test(priority = 1)
    public void search() throws Exception {


        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        Thread.sleep(2000);
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
        Thread.sleep(30000);

        WebElement result = driver.findElement(SRP.results);
        Assert.assertTrue(result.isDisplayed(), "No search result loaded");


        By bookNowButtonLocator = By.cssSelector("button.btn.primary_btn.book_btn.onHover");

        // Wait for the button to be clickable
        WebElement bookNowButton = waitForElementToBeClickable(driver, bookNowButtonLocator);

        // Click the "Book Now" button
        bookNowButton.click();
        Thread.sleep(10000);


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
        WebElement month = wait.until(ExpectedConditions.presenceOfElementLocated(FlightPage.dayDOB));
        WebElement year = wait.until(ExpectedConditions.presenceOfElementLocated(FlightPage.dayDOB));

        driver.findElement(FlightPage.mobileNo).sendKeys("9492330035");
        driver.findElement(FlightPage.email).sendKeys("sreenivasulu@travelstart.com");
        driver.findElement(FlightPage.whatsApp).click();
        Thread.sleep(1000);
        driver.findElement(FlightPage.whatsApp).click();
        driver.findElement(FlightPage.mr).click();
        driver.findElement(FlightPage.firstName).sendKeys("Sreenivasulu");
        driver.findElement(FlightPage.lastName).sendKeys("Muthyalapati");
        Select daysc = new Select(day);
        driver.findElement(By.xpath("//option[normalize-space()='6']")).click();
        Select monthsc = new Select(month);
        driver.findElement(By.xpath("//*[text()='Jun']")).click();
        Select yearsc = new Select(year);
        driver.findElement(By.xpath("//*[@value='1999']")).click();
        driver.findElement(FlightPage.meal).click();
        driver.findElement(FlightPage.vegetarianMeal).click();
        driver.findElement(FlightPage.contnue).click();
        System.out.println("Traveller details have been added");

    }


    @Test(priority = 4)
    public void mealsPrice() {
        WebElement mealsPrice = null;
        try {
            mealsPrice = driver.findElement(By.id("MEALS"));
        } catch (NoSuchElementException ne) {
            ne.printStackTrace();
            System.out.println("Meals price is not included in price breakdown");
        }
        Assert.assertTrue(mealsPrice.isDisplayed(), "Meals price is not included in price breakdown");

    }

    @Test(priority = 5)
    public void whatsappPrice() throws InterruptedException {
        WebElement mealsPrice = null;
        try {
            mealsPrice = driver.findElement(By.id("WHATSAPP"));
        } catch (NoSuchElementException ne) {
            ne.printStackTrace();
            System.out.println("WhatsApp price is not included in price breakdown");
        }
        Assert.assertTrue(mealsPrice.isDisplayed(), "WhatsApp price is not included in price breakdown");
        driver.findElement(AddOnsPage.editSeats).click();
        Thread.sleep(15000);
    }

    @Test
    public void Seats() throws InterruptedException {

        WebElement seatMap = null;
        try{
            seatMap = driver.findElement(SeatsPage.seatMap);
        } catch (NoSuchElementException ne){
            ne.printStackTrace();
            System.out.println("No seat map loaded");
        }
        Assert.assertTrue(seatMap.isDisplayed(),"Test failed, no seat map loaded");
    }



}
