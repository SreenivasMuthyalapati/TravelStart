package testmethods;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageObjects.B2B.Dashboard;
import pageObjects.B2B.LoginPage;
import pageObjects.B2B.MakePaymentConfirmationPage;
import pageObjects.B2B.SearchPage;
import pageObjects.Paths;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static testmethods.TSMethods.screenShotPath;

public class B2BMethods {

    private WebDriverWait wait;
    private WebDriver driver;
    private Method m;
    String dataPath = Paths.b2bTestData;

    // Constructor to initialize WebDriverWait
    public B2BMethods(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 60);
        this.m = new Method(); // assuming Method is initialized like this
    }

    public void login(String mailID, String password) throws InterruptedException {

        Thread.sleep(500);

        // Login
        driver.findElement(LoginPage.emailId).clear();
        driver.findElement(LoginPage.emailId).sendKeys(mailID);

        driver.findElement(LoginPage.password).clear();
        driver.findElement(LoginPage.password).sendKeys(password);

        driver.findElement(LoginPage.loginButton).click();

        Thread.sleep(1000);

        WebElement dashboard = null;

        try {
            // Wait for the dashboard to be visible (up to 60 seconds)
            dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(Dashboard.dashboard));

        } catch (NoSuchElementException | TimeoutException e) {
            System.err.println("Dashboard not found after login: " + e.getMessage());
        }

        boolean isDashboardAvailable = dashboard != null && dashboard.isDisplayed();

        if (isDashboardAvailable) {
            System.out.println("Login successful, Dashboard displayed.");
        } else {
            System.out.println("Login failed, Dashboard not displayed.");

            // Take a screenshot if login fails
            m.takeScreenshot(driver, Paths.screenshotFolder, screenShotPath);

            // Get console logs if any error exists in the console
            m.getConsole(driver);

            // Store the screenshot file
            File screenShotFile = new File(screenShotPath);

            Assert.fail("Login Failed, Dashboard not displayed.");
        }
    }


    public List<String> RetrieveLoginCreds(String domain, String environment) throws IOException {

        List<String> loginCredentials = new ArrayList<>();

        // Add user

        if (domain.equalsIgnoreCase("B2B_NG")){

            if (environment.equalsIgnoreCase("LIVE")){

                loginCredentials.add(0, m.readDataFromExcel(dataPath, "Login Creds", 3, 1));
                loginCredentials.add(1, m.readDataFromExcel(dataPath, "Login Creds", 3, 2));

            } else if (environment.equalsIgnoreCase("BETA")) {

                loginCredentials.add(0, m.readDataFromExcel(dataPath, "Login Creds", 4, 1));
                loginCredentials.add(1, m.readDataFromExcel(dataPath, "Login Creds", 4, 2));

            } else if (environment.equalsIgnoreCase("PREPROD")) {

                loginCredentials.add(0, m.readDataFromExcel(dataPath, "Login Creds", 5, 1));
                loginCredentials.add(1, m.readDataFromExcel(dataPath, "Login Creds", 5, 2));

            }


        } else if (domain.equalsIgnoreCase("B2B_FS")){

            if (environment.equalsIgnoreCase("LIVE")){

                loginCredentials.add(0, m.readDataFromExcel(dataPath, "Login Creds", 9, 1));
                loginCredentials.add(1, m.readDataFromExcel(dataPath, "Login Creds", 9, 2));

            } else if (environment.equalsIgnoreCase("BETA")) {

                loginCredentials.add(0, m.readDataFromExcel(dataPath, "Login Creds", 10, 1));
                loginCredentials.add(1, m.readDataFromExcel(dataPath, "Login Creds", 10, 2));

            } else if (environment.equalsIgnoreCase("PREPROD")) {

                loginCredentials.add(0, m.readDataFromExcel(dataPath, "Login Creds", 11, 1));
                loginCredentials.add(1, m.readDataFromExcel(dataPath, "Login Creds", 11, 2));

            }


        } else if (domain.equalsIgnoreCase("B2B_CT")){

            if (environment.equalsIgnoreCase("LIVE")){

                loginCredentials.add(0, m.readDataFromExcel(dataPath, "Login Creds", 15, 1));
                loginCredentials.add(1, m.readDataFromExcel(dataPath, "Login Creds", 15, 2));

            } else if (environment.equalsIgnoreCase("BETA")) {

                loginCredentials.add(0, m.readDataFromExcel(dataPath, "Login Creds", 16, 1));
                loginCredentials.add(1, m.readDataFromExcel(dataPath, "Login Creds", 16, 2));

            } else if (environment.equalsIgnoreCase("PREPROD")) {

                loginCredentials.add(0, m.readDataFromExcel(dataPath, "Login Creds", 17, 1));
                loginCredentials.add(1, m.readDataFromExcel(dataPath, "Login Creds", 17, 2));

            }


        }


        return loginCredentials;
    }


    public void clickSearchFlight(WebDriver driver) throws InterruptedException {

        driver.findElement(Dashboard.searchFlight).click();

        Thread.sleep(5000);

        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(SearchPage.bookingFlowiFreame));

            WebElement bookingIframe = driver.findElement(SearchPage.bookingFlowiFreame);

            driver.switchTo().frame(bookingIframe);

        } catch (NoSuchElementException | TimeoutException e){

            System.out.println("Booking flow iframe was not loaded");

        }

    }


    public void clickMakePaymentFromDashboard(WebDriver driver, String bookingReference) throws InterruptedException {

        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(Dashboard.moreActions));
            driver.findElement(Dashboard.moreActions).click();
            Thread.sleep(300);

        } catch (NoSuchElementException | TimeoutException e){

            System.out.println("Booking summary actions were not loaded");

        }

        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(Dashboard.makePaymentAction));
            driver.findElement(Dashboard.makePaymentAction).click();
            Thread.sleep(300);

        } catch (NoSuchElementException | TimeoutException e){

            System.out.println("Booking summary actions were not loaded");

        }


        WebElement makePaymentCTA;

        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(MakePaymentConfirmationPage.makePaymentCTA));

            makePaymentCTA = driver.findElement(MakePaymentConfirmationPage.makePaymentCTA);

            Thread.sleep(1000);

            makePaymentCTA.click();

            Thread.sleep(300);

        } catch (NoSuchElementException | TimeoutException e){

            System.out.println("Payment confirmation page was not loaded");


        }


    }



    public void makePaymentForReservedBooking(WebDriver driver, String domain, String paymentMethod) throws InterruptedException {

        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(SearchPage.bookingFlowiFreame));

            WebElement bookingIframe = driver.findElement(SearchPage.bookingFlowiFreame);

            driver.switchTo().frame(bookingIframe);

        } catch (NoSuchElementException | TimeoutException e){

            System.out.println("Booking flow iframe was not loaded");

        }


        boolean isPaymentPageLoaded = false;

        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()='Booking summary']")));
            WebElement bookingSummary = driver.findElement(SearchPage.bookingFlowiFreame);
            isPaymentPageLoaded = bookingSummary.isDisplayed();
        }catch (NoSuchElementException | NullPointerException | TimeoutException e){

        }

       // Assert.assertTrue(isPaymentPageLoaded, "Failed to load payment page when making payment for reserved booking");

    }

}
