package test.RetrieveSubscriptions;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.Paths;
import pageObjects.TCCHome;
import pageObjects.TCCTSAccountUsers;
import testmethods.Method;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RetrieveSubscriptions {

    static XSSFWorkbook workbook;
    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = Paths.dataPath;
    static String outputExcel = Paths.excelOutputPath;
    static String TCCURL;
    static String peachDashBoardURL;



    @BeforeClass
    public void invokeBrowser(){

    }

    @Test
    public <UserData> void getSubscribedMailsFromTcc() throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", Paths.chromeDriver);
        driver = new ChromeDriver();
        driver.manage().window().maximize();


        String fromDate = m.readDataFromExcel(dataPath, "Subscription Dates", 0, 1);
        String toDate = m.readDataFromExcel(dataPath, "Subscription Dates", 1, 1);

        TCCURL = m.readDataFromExcel(dataPath, "Subscription Dates", 3, 1);

        driver.get(TCCURL);

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
            System.out.println("TCC Launched");
        } else {
            m.takeScreenshot(driver,Paths.screenshotFolder,"");
            m.getConsole(driver);
        }
        Assert.assertTrue(isLoginAvailable, "TCC not launched");

        driver.findElement(TCCHome.userName).sendKeys("sreenivasulu");
        driver.findElement(TCCHome.password).sendKeys("Aliyavasu@143");
        driver.findElement(TCCHome.loginButton).click();

        driver.findElement(TCCHome.tsAccountUsers).click();
        driver.findElement(TCCTSAccountUsers.fromDate).sendKeys(fromDate);
        driver.findElement(TCCTSAccountUsers.toDate).sendKeys(toDate);
        driver.findElement(TCCTSAccountUsers.search).click();

        List<Object[]> subscriptionsData = new ArrayList<>();
        List<WebElement> subscriptions = driver.findElements(TCCTSAccountUsers.allSubscriptions);

        // Find the table element
        WebElement table = driver.findElement(By.xpath("//table[@class='bookingList users-table']"));
        




    }


    
}
