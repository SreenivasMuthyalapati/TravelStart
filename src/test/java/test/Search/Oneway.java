package test.Search;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.HomePage;
import pageObjects.Paths;
import pageObjects.SRP;
import pageObjects.Filters;
import testmethods.Method;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Oneway {

    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = Paths.dataPath;
    static String environment;

    private Filters page;

    static {
        try {
            environment = m.readDataFromExcel(dataPath,0,0,1);
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
    @AfterMethod
    public void close(){
        driver.quit();
    }

    @DataProvider(name = "cityData")
    public Object[][] getCityData() throws IOException {
        return new Object[][] {
                //Domestic Routes
                {m.readDataFromExcel(dataPath,1,7,0), m.readDataFromExcel(dataPath,1,7,1)},
                {m.readDataFromExcel(dataPath,1,8,0), m.readDataFromExcel(dataPath,1,8,1)},
                {m.readDataFromExcel(dataPath,1,9,0), m.readDataFromExcel(dataPath,1,9,1)},

                // International Routes :
                {m.readDataFromExcel(dataPath,1,2,0), m.readDataFromExcel(dataPath,1,2,1)},
                {m.readDataFromExcel(dataPath,1,3,0), m.readDataFromExcel(dataPath,1,3,1)},
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
            wait.until(ExpectedConditions.visibilityOfElementLocated(SRP.results));
            result = driver.findElement(SRP.results);
        } catch (NoSuchElementException e) {
            System.out.println("Booking Failed");
        }
        assrt.assertTrue(result.isDisplayed(), "Search result not loaded");


        assrt.assertAll("Test Completed");

    }





}
