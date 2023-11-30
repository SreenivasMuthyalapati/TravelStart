package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.SRP;

import java.util.concurrent.TimeUnit;

public class Booking {
    static WebDriver driver;
    static String Path = "C:\\Users\\Sreen\\eclipse-workspace\\travelStart\\src\\test\\resources\\configFiles\\config.properties";
    static testmethods.Method m = new testmethods.Method();

    @Test
    public void search() throws Exception {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sreen\\OneDrive\\Documents\\QA\\Selenium\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(m.ReadPropertyFile(Path, "LiveZAUrl"));

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Thread.sleep(2000);
        driver.findElement(HomePage.departureCity).sendKeys("JNB");
        Thread.sleep(2000);
        driver.findElement(HomePage.option).click();
        driver.findElement(HomePage.arrivalCity).sendKeys("LON");
        Thread.sleep(2000);
        driver.findElement(HomePage.option).click();
        driver.findElement(HomePage.departureDate).click();
        for (int i=0; i<4;i++){
            Thread.sleep(1000);
            driver.findElement(HomePage.nextMonth).click();
        }
        driver.findElement(HomePage.day).click();
        driver.findElement(HomePage.day2).click();

        driver.findElement(HomePage.search).click();
        Thread.sleep(30000);

    }
    @Test
    public void flightPage() {

        driver.findElement(SRP.bookNow).click();

    }

}
