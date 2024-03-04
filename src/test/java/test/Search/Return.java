package test.Search;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

import java.io.IOException;

public class Return {
    static WebDriver driver;
    static testmethods.Method m = new testmethods.Method();
    static String dataPath = Paths.dataPath;
    static String environment;

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
        Thread.sleep(15000);
        WebDriverWait wait = new WebDriverWait(driver, 45);
        WebElement result = null;
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(SRP.results));
            result = driver.findElement(SRP.results);
        } catch (NoSuchElementException e){
            System.out.println("Booking Failed");
        }
        assrt.assertTrue(result.isDisplayed(),"Search result loaded");

        //Validate result

        String onwardRoute = driver.findElement(SRP.unbundledOnwardRouteDetails).getText();
        String expectedOnwardRoute = (departureCity+" "+arrivalCity);

        String returnRoute = driver.findElement(SRP.unbundledReturnRouteDetails).getText();
        String expectedReturnRoute = (arrivalCity+" "+departureCity);

        assrt.assertEquals(onwardRoute,expectedOnwardRoute, "Search result was not relevant to search made (Onward Flight)");
        assrt.assertEquals(returnRoute,expectedReturnRoute, "Search result was not relevant to search made (Return Flight)");

        assrt.assertAll("Test execution completed");
    }






}