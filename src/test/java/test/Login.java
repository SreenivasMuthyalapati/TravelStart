package test;


import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Login {
    static WebDriver driver;
    static testmethods.Method m = new testmethods.Method();
    static String dataPath = "C:\\Users\\Sreen\\IdeaProjects\\travelStart\\TestData\\DataBook.xls";

    @BeforeTest
    public void setup() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sreen\\OneDrive\\Documents\\QA\\Selenium\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(m.readDataFromExcel(dataPath,0,7,1));

        // To accept all cookies
        driver.manage().deleteAllCookies();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(2000);
    }

    @AfterTest
    public void close(){
        driver.quit();
    }

    @Test
    public void login() throws IOException, InterruptedException {
        String username = m.readDataFromExcel(dataPath,0,7,4);
        String password = m.readDataFromExcel(dataPath,0,7,5);
        String expectedURL = m.readDataFromExcel(dataPath,0,7,3);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(HomePage.myAccount).click();
        driver.findElement(LoginPage.username).sendKeys(username);
        driver.findElement(LoginPage.password).sendKeys(password);
        driver.findElement(LoginPage.login).click();

        WebElement myAC = null;
        try{
            myAC = driver.findElement(HomePage.myAccount);
        }catch (NoSuchElementException e){
            System.out.println("Logged In Successfully");
        }
        driver.findElement(HomePage.profile).click();
        driver.findElement(HomePage.myProfile).click();

        String cURL = driver.getCurrentUrl();
        Assert.assertEquals(expectedURL,cURL,"Login Failed");
    }

}
