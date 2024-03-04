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
import org.testng.annotations.*;
import pageObjects.*;

import java.io.IOException;

public class TsPlusSubscription {

    static WebDriver driver;
    static testmethods.Method m = new testmethods.Method();
    static String dataPath = "C:\\Users\\Dell\\IdeaProjects\\travelStart\\TestData\\DataBook.xls";
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
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Documents\\chromedriver-win32\\chromedriver-win32\\chromedriver.exe");
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
        Thread.sleep(5000);
        driver.findElement(GoogleLogin.googleWorkSpace).click();
        WebDriverWait wait = new WebDriverWait(driver, 45);
        wait.until(ExpectedConditions.visibilityOfElementLocated(GoogleLogin.mail));
        driver.findElement(GoogleLogin.mail).sendKeys("sreenivasulu");
        driver.findElement(GoogleLogin.next).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(GoogleLogin.password));
        driver.findElement(GoogleLogin.password).sendKeys("Aliyavasu@143");
        driver.findElement(GoogleLogin.next).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(HomePage.myAccount));
    }
    @AfterMethod @Ignore
    public void close(){
        driver.quit();
    }

    @DataProvider(name = "loginCreds")
    public Object[][] getLoginCreds() throws IOException {

        return new Object[][] {
                {m.readDataFromExcel(dataPath,3,3,1), m.readDataFromExcel(dataPath,3,3,2)},
               // {m.readDataFromExcel(dataPath,3,4,1), m.readDataFromExcel(dataPath,3,4,2)},
               // {m.readDataFromExcel(dataPath,3,5,1), m.readDataFromExcel(dataPath,3,5,2)}
        };
    }

    @Test(dataProvider = "loginCreds")
    public void plusSubscription(String mailID, String password) throws InterruptedException, IOException {

        // Login
        driver.findElement(HomePage.myAccount).click();

        driver.findElement(LoginPage.username).sendKeys(mailID);

        driver.findElement(LoginPage.password).sendKeys(password);

        driver.findElement(LoginPage.login).click();

        Thread.sleep(5000);

        // Subcription
        WebDriverWait wait = new WebDriverWait(driver, 45);

        wait.until(ExpectedConditions.elementToBeClickable(HomePage.upgradeIcon));

        Thread.sleep(3000);

        driver.findElement(HomePage.upgradeIcon).click();

        Thread.sleep(3000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(BenefitsPage.upgradeNow));

        driver.findElement(BenefitsPage.upgradeNow).click();

        Thread.sleep(10000);

        // Payment
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@placeholder='Card Number']")));

        driver.findElement(UpgradePaymentPage.cardNumber).sendKeys(m.readDataFromExcel(dataPath, 4, 2, 1));

        driver.switchTo().defaultContent();

        driver.findElement(UpgradePaymentPage.cardHolder).sendKeys(m.readDataFromExcel(dataPath, 4, 2, 2));

        String CVV = m.readDataFromExcel(dataPath, 4, 2, 4);

        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@placeholder='CVV']")));

        driver.findElement(UpgradePaymentPage.CVV).sendKeys(CVV);
        //System.out.println(CVV);

        driver.switchTo().defaultContent();

        driver.findElement(UpgradePaymentPage.expiry).sendKeys(m.readDataFromExcel(dataPath, 4, 2, 3));

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
                "    \"username\": \"" + mailID + "\"\n" +
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
