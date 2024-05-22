package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import pageObjects.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TsPlusSubscription {

    static WebDriver driver;
    static testmethods.Method m = new testmethods.Method();
    static String TsPlusdataPath = Paths.TSPlusDataPath;
    static String environment;
    static String baseURL = "";
    
    
    @AfterMethod @Ignore
    public void close(){

        // Closes all active windows in the automation session
        if (driver != null) {

            driver.quit();
        }
    }

    @DataProvider(name = "TSPlusData")
    public Object[][] getTSPlusData() throws IOException {

        List<Object[]> tsPlusData = new ArrayList<>();

        // Extracting test case count from data sheet
        int totalCount = m.getRowCount(TsPlusdataPath, "TSPlusSubscriptionData");

        // Iterates whole test data and stores in variables
        for (int i = 2; i < totalCount; i++) {

            String slNo = m.readDataFromExcel(TsPlusdataPath, "TSPlusSubscriptionData", i, 0);
            String environment = m.readDataFromExcel(TsPlusdataPath, "TSPlusSubscriptionData", i, 1);
            String username = m.readDataFromExcel(TsPlusdataPath, "TSPlusSubscriptionData", i, 2);
            String password = m.readDataFromExcel(TsPlusdataPath, "TSPlusSubscriptionData", i, 3);
            String cardNumber = m.readDataFromExcel(TsPlusdataPath, "TSPlusSubscriptionData", i, 4);
            String cardholderName = m.readDataFromExcel(TsPlusdataPath, "TSPlusSubscriptionData", i, 5);
            String cardExpiryMonth = m.readDataFromExcel(TsPlusdataPath, "TSPlusSubscriptionData", i, 6);
            String cardExpiryYear = m.readDataFromExcel(TsPlusdataPath, "TSPlusSubscriptionData", i, 7);
            String CVV = m.readDataFromExcel(TsPlusdataPath, "TSPlusSubscriptionData", i, 8);

            // Adding test data stored variables into test case list
            tsPlusData.add(new Object[]{slNo,
                    environment.toUpperCase(),
                    username,
                    password,
                    cardNumber,
                    cardholderName,
                    cardExpiryMonth,
                    cardExpiryYear,
                    CVV});
        }

        // Returns multiple data at every iteration
        return tsPlusData.toArray(new Object[0][]);
    }

    @Test(dataProvider = "TSPlusData")
    public void tsPlusSubscription(String slNo, String environment, String username, String password, String cardNumber, String cardholderName, String cardExpiryMonth, String cardExpiryYear, String CVV) throws InterruptedException, IOException {

        boolean subscriptionStatus = false;
        subscriptionStatus = m.checkSubscriptionStatus(username,password);
        if (subscriptionStatus){
            System.out.println("This account already has TS Plus subscription");
            throw new SkipException("Test is skipped as this test case " +slNo+ " account has already subscribed to TS Plus");
        }

        System.setProperty("webdriver.chrome.driver", Paths.chromeDriver);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println(environment);
        switch (environment) {

            // Live URL
            case "LIVE" -> baseURL = m.readDataFromExcel(Paths.dataPath, "URL's", 4, 1);
            // Beta URL
            case "BETA" -> baseURL = (m.readDataFromExcel(Paths.dataPath, "URL's", 6, 1));
            // Preprod URL
            case "PREPROD" -> baseURL = (m.readDataFromExcel(Paths.dataPath, "URL's", 8, 1));
            // Alpha URL
            case "ALPHA" -> baseURL = (m.readDataFromExcel(Paths.dataPath, "URL's", 10, 1));

            // Returns invalid environment if environment name doesn't match with environment names
            default -> System.out.println("Invalid environment name");

        }
        driver.get(baseURL+"ts-plus/ts-plus-benefits");

        Thread.sleep(3000);

        // Login
        driver.findElement(By.xpath("//button[@class='head_btn login_btn ng-star-inserted']")).click();

        driver.findElement(LoginPage.username).sendKeys(username);

        driver.findElement(LoginPage.password).sendKeys(password);

        driver.findElement(LoginPage.login).click();

        Thread.sleep(5000);

        // Subcription
        WebDriverWait wait = new WebDriverWait(driver, 45);

        Thread.sleep(3000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(BenefitsPage.upgradeNow));

        driver.findElement(BenefitsPage.upgradeNow).click();

        Thread.sleep(10000);

        // Payment
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@placeholder='Card Number']")));

        driver.findElement(UpgradePaymentPage.cardNumber).sendKeys(cardNumber);

        driver.switchTo().defaultContent();

        driver.findElement(UpgradePaymentPage.cardHolder).sendKeys(cardholderName);


        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@placeholder='CVV']")));

        driver.findElement(UpgradePaymentPage.CVV).sendKeys(CVV);

        driver.switchTo().defaultContent();

        driver.findElement(UpgradePaymentPage.expiry).sendKeys(cardExpiryMonth);
        driver.findElement(UpgradePaymentPage.expiry).sendKeys(cardExpiryYear);

        driver.findElement(UpgradePaymentPage.payNow).click();

        WebElement welcomeMsg = null;

        WebDriverWait waitSubs = new WebDriverWait(driver, 180);
        try {
            waitSubs.until(ExpectedConditions.visibilityOfElementLocated(UpgradePaymentPage.welcome));
        } catch (TimeoutException e) {
            e.printStackTrace();
            System.out.println("Welcome message wasn't appear");
        }
        try {
            welcomeMsg = driver.findElement(UpgradePaymentPage.welcome);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("Welcome message wasn't appear");
        }

        try {
            Assert.assertTrue(welcomeMsg.isDisplayed(), "Welcome message wasn't appear");
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Welcome message wasn't appear");
        }
        String requestBody = "{\n" +
                "    \"password\": \"" + password + "\",\n" +
                "    \"provider\": \"travelstart\",\n" +
                "    \"token\": null,\n" +
                "    \"userAgent\": {\n" +
                "        \"language\": \"en\",\n" +
                "        \"market\": \"za\"\n" +
                "    },\n" +
                "    \"username\": \"" + username + "\"\n" +
                "}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("https://preprod-tsacc.travelstart.com/api/v3/login");

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        String responseBody = response.getBody().asString();

        Assert.assertTrue(responseBody.contains("\"isTSPlusSubscriber\":true"), "Subscription not done");


    }
}