package test.FunctionalTesting.FlightDetails;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Oneway {

    static WebDriver driver;
    static testmethods.Method m = new testmethods.Method();
    static String dataPath = Paths.dataPath;
    static String environment;


    static {
        try {
            environment = m.readDataFromExcel(dataPath,"URL's",0,1);
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
            case "live" -> driver.get(m.readDataFromExcel(dataPath, "URL's", 3, 1));
            case "beta" -> driver.get(m.readDataFromExcel(dataPath, "URL's", 5, 1));
            case "preprod" -> driver.get(m.readDataFromExcel(dataPath, "URL's", 7, 1));
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
               // Domestic Routes
                {m.readDataFromExcel(dataPath,"Oneway Routes",7,0), m.readDataFromExcel(dataPath,"Oneway Routes",7,1)},
                {m.readDataFromExcel(dataPath,"Oneway Routes",8,0), m.readDataFromExcel(dataPath,"Oneway Routes",8,1)},
                {m.readDataFromExcel(dataPath,"Oneway Routes",9,0), m.readDataFromExcel(dataPath,"Oneway Routes",9,1)},

                // International Routes :
                {m.readDataFromExcel(dataPath,"Oneway Routes",2,0), m.readDataFromExcel(dataPath,"Oneway Routes",2,1)},
                {m.readDataFromExcel(dataPath,"Oneway Routes",3,0), m.readDataFromExcel(dataPath,"Oneway Routes",3,1)},
                {m.readDataFromExcel(dataPath,"Oneway Routes",4,0), m.readDataFromExcel(dataPath,"Oneway Routes",4,1)}
        };
    }

    @Test(dataProvider = "cityData", priority = 1, description = "To verify that the result is getting loaded or not")
    public void search(String departureCity, String arrivalCity) throws Exception {

        SoftAssert assrt = new SoftAssert();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
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
        WebDriverWait wait = new WebDriverWait(driver, 60);
        WebElement result = null;
        try{
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(SRP.results));
                result = driver.findElement(SRP.results);
            }
            catch (TimeoutException t){
                System.out.println("Result not loaded!");
                m.takeScreenshot(driver, Paths.screenshotFolder);}

        } catch (NoSuchElementException e){


        }

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

        // Screen Shot
        if(depCityVerify== false || arrCityVerify== false) {
            m.takeScreenshot(driver, Paths.screenshotFolder);
        }
        //Verify price matching between SRP and Flight details page

        String baseFare = driver.findElement(FlightPage.fare).getText();

       boolean isPriceMatching;

       if(SRPPrice.equals(baseFare)){
           isPriceMatching = true;
       }else{
           isPriceMatching = false;
       }

       assrt.assertTrue(isPriceMatching, "Price mismatch between SRP and flight details page. SRP price is: "+ SRPPrice+" but baseFare in flight details page is: "+ baseFare );

        if(!isPriceMatching) {
            m.takeScreenshot(driver, Paths.screenshotFolder);
        }

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
        assrt.assertTrue(isArrTimeMatching, "Arrival time mismatch between SRP and flight details page. SRP arrival time is: "+ arrTimeSRP+" but arrival time in flight details page is: "+ arrTime );

        if(isDepTimeMatching== false|| isArrTimeMatching==false) {
            m.takeScreenshot(driver, Paths.screenshotFolder);
        }

        boolean isFAFlight;

        String airline = driver.findElement(FlightPage.airline1).getText();
        if(airline.contains("FlySafair")){
            isFAFlight = true;
        }else{
            isFAFlight = false;
        }

        String lastname;
        if (isFAFlight){
            lastname = "Test";
        } else{
            lastname = m.readDataFromExcel(dataPath, "PAX Details", 11, 4);
        }

        WebElement day = driver.findElement(FlightPage.dayDOB);
        WebElement month = driver.findElement(FlightPage.monthDOB);
        WebElement year = driver.findElement(FlightPage.yearDOB);

        //Sending contact details in booking
        driver.findElement(FlightPage.mobileNo).clear();
        driver.findElement(FlightPage.mobileNo).sendKeys(m.readDataFromExcel(dataPath, "PAX Details", 3, 1));
        driver.findElement(FlightPage.email).sendKeys(m.readDataFromExcel(dataPath, "PAX Details", 4, 1));
        driver.findElement(FlightPage.whatsApp).click();
        Thread.sleep(1000);
        driver.findElement(FlightPage.whatsApp).click();

        //Adding PAX details
        driver.findElement(FlightPage.mr).click();
        driver.findElement(FlightPage.firstName).sendKeys(m.readDataFromExcel(dataPath, "PAX Details", 11, 2));
        driver.findElement(FlightPage.lastName).sendKeys(lastname);
        Select daysc = new Select(day);
        String yearr = m.readDataFromExcel(dataPath, "PAX Details", 11, 7);
        daysc.selectByIndex(4);
        Select monthsc = new Select(month);
        monthsc.selectByIndex(6);
        Select yearsc = new Select(year);
        yearsc.selectByValue("1999");

        //Handling notification
        try {
            driver.switchTo().frame("webpush-onsite");
            driver.findElement(HomePage.denyNotification).click();

            driver.switchTo().defaultContent();
        }catch (NoSuchElementException | NoSuchFrameException e){
            e.printStackTrace();

        }

        //Passport details
        WebElement ppInfo = null;
        try{
            ppInfo = driver.findElement(FlightPage.ppInfo);
        }catch (NoSuchElementException ne){
            ne.printStackTrace();
            System.out.println("PassPort details not required for this flight");
        }
        try{
            if(ppInfo.isDisplayed()){
                WebElement  ppNumber = driver.findElement(FlightPage.ppNumber);
                double ppnumberdouble = Double.parseDouble(m.readDataFromExcel(dataPath,"PAX Details",11,8));
                // Convert double to int
                int ppnumberInt = (int) ppnumberdouble;
                String ppnumber = Integer.toString(ppnumberInt);
                ppNumber.sendKeys(ppnumber);
                WebElement ppday = driver.findElement(FlightPage.ppExpiryDate);
                WebElement ppmonth = driver.findElement(FlightPage.ppExpiryMonth);
                WebElement ppyear = driver.findElement(FlightPage.ppExpiryYear);

                Select ppdaysc = new Select(ppday);
                ppdaysc.selectByIndex(1);
                Select ppmonthsc = new Select(ppmonth);
                ppmonthsc.selectByIndex(1);
                Select ppyearsc = new Select(ppyear);
                ppyearsc.selectByValue("2029");

                driver.findElement(FlightPage.ppNationality).click();
                driver.findElement(By.xpath("//*[text()='India']")).click();
                Thread.sleep(1000);
                driver.findElement(FlightPage.ppIssuingCountry).click();
                driver.findElement(By.xpath("(//*[text()='India'])[2]")).click();


            }}catch (NullPointerException ne){

        }

        driver.findElement(FlightPage.contnue).click();
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(AddOnsPage.AddOns));
            WebElement addonsPage = driver.findElement(AddOnsPage.AddOns);
            try{
                assrt.assertTrue(addonsPage.isDisplayed(), "Traveller details is not added");
                if (!addonsPage.isDisplayed()){
                    m.takeScreenshot(driver, Paths.screenshotFolder);
                }
            } catch (NullPointerException ne){

            }
        } catch (NoSuchElementException e){
            e.printStackTrace();
        }


        //To complete Test
        assrt.assertAll("Test Completed");

    }

}
