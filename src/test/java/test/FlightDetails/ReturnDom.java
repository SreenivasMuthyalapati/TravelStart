package test.FlightDetails;

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
import pageObjects.FlightPage;
import pageObjects.HomePage;
import pageObjects.Paths;
import pageObjects.SRP;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ReturnDom {


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
            switch (environment) {
                case "live" -> driver.get(m.readDataFromExcel(dataPath, 0, 3, 1));
                case "beta" -> driver.get(m.readDataFromExcel(dataPath, 0, 5, 1));
                case "preprod" -> driver.get(m.readDataFromExcel(dataPath, 0, 7, 1));
                default -> System.out.println("Invalid envinorment name");
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
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(SRP.results));
                result = driver.findElement(SRP.results);
            } catch (NoSuchElementException e){
                System.out.println("Result not loaded!");
            }

            Assert.assertTrue(result.isDisplayed(),"No result loaded");

            // Taking trip details from SRP for validation

            String flightNumber = driver.findElement(SRP.flightNumberOneway).getText();
            String SRPPrice = driver.findElement(SRP.flightPrice).getText();
            String depTimeSRP = driver.findElement(SRP.depTimeOnward).getText();
            String arrTimeSRP = driver.findElement(SRP.arrTimeOnward).getText();


            driver.findElement(SRP.book).click();
            Thread.sleep(1000);

            WebElement travellerPage = null;
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(FlightPage.flightReviewPage));
                travellerPage = driver.findElement(FlightPage.flightReviewPage);
            } catch (NoSuchElementException e){
                System.out.println("Traveller page not loaded");
            }
            Assert.assertTrue(travellerPage.isDisplayed(),"Traveller page not loaded");

            // Verify Flight Number selected

            String flightNumber1 = driver.findElement(FlightPage.flightNumberOneway).getText();

            assrt.assertEquals(flightNumber,flightNumber1, "Choosen flight not selected. Choosen flight: "+flightNumber+" but flight selected: "+ flightNumber1);

            //Verify cities searched for

            String depCity = driver.findElement(FlightPage.depCityOneway).getText();
            String arrCity = driver.findElement(FlightPage.arrCityOneway).getText();

            boolean depCityVerify = depCity.contains(departureCity);
            boolean arrCityVerify = arrCity.contains(arrivalCity);

            assrt.assertTrue(depCityVerify, "Departure city mismatch. Expected: "+ departureCity+" but actual: "+ depCity);

            assrt.assertTrue(arrCityVerify, "Arrival city mismatch. Expected: "+ arrivalCity+" but actual: "+ arrCity);

            //Verify price matching between SRP and Flight details page

            String baseFare = driver.findElement(FlightPage.fare).getText();

            boolean isPriceMatching;

            if(SRPPrice.equals(baseFare)){
                isPriceMatching = true;
            }else{
                isPriceMatching = false;
            }

            assrt.assertTrue(isPriceMatching, "Price mismatch between SRP and flight details page. SRP price is: "+ SRPPrice+" but baseFare in flight details page is: "+ baseFare );


            //Verify Departure and arrival timings

            String depTime = driver.findElement(FlightPage.depTimeOnward).getText();
            String arrTime = driver.findElement(FlightPage.arrTimeOnward).getText();

            boolean isDepTimeMatching;
            boolean isArrTimeMatching;


            if(depTimeSRP.contains(depTime)){
                isDepTimeMatching = true;
            }else{
                isDepTimeMatching = false;
            }

            if(arrTimeSRP.contains(arrTime)){
                isArrTimeMatching = true;
            }else{
                isArrTimeMatching = false;
            }

            assrt.assertTrue(isDepTimeMatching, "Departure time mismatch between SRP and flight details page. SRP departure time is: "+ depTimeSRP+" but departure time in flight details page is: "+ depTime );
            assrt.assertTrue(isArrTimeMatching, "Arrival time mismatch between SRP and flight details page. SRP departure time is: "+ arrTimeSRP+" but departure time in flight details page is: "+ arrTime );


            //Complete Test
            assrt.assertAll("Test Completed");

        }

    }


