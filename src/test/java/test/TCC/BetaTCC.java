package test.TCC;

import io.qameta.allure.Description;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.Paths;
import pageObjects.TCCHome;
import testmethods.Method;

import java.io.IOException;

public class BetaTCC {

    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = Paths.dataPath;


    @BeforeMethod
    public void setup() throws Exception {
        System.setProperty("webdriver.chrome.driver", Paths.chromeDriver);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(m.readDataFromExcel(dataPath, "URL's", 14, 1));
    }

    @AfterMethod
    public void close(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // If test fails, print the correlation ID
            System.out.println("Test Failed! Correlation ID: " + m.getCID(driver));
        }
        driver.quit();
    }

    @Test
    public void TCCSmokeTest() throws InterruptedException {
        Thread.sleep(1000);

        //Get login element stored
        WebElement TCCLoginSection = null;
        try{
            TCCLoginSection = driver.findElement(TCCHome.loginSection);
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }

        //Validate availability of login
        boolean isLoginAvailable = false;
        try {
            isLoginAvailable = TCCLoginSection.isDisplayed();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if (isLoginAvailable){
            System.out.println("Beta TCC Launched");
        } else {
            m.takeScreenshot(driver,Paths.screenshotFolder);
            m.getConsole(driver);
        }
        Assert.assertTrue(isLoginAvailable, "Beta TCC not launched");

    }
}