package test.B2CCheckList;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.Paths;
import test.BrowserStack.BrowserStackBaseTest;
import testmethods.Method;
import testmethods.TSMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Login{

    static XSSFWorkbook workbook;
    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = Paths.B2CChecklistDataPath;
    static String browser;
    static String environment;
    static String outputExcel = Paths.excelOutputPath;

    static String baseURL;
    static String runTime;
    static String screenShotPath = "";
    static String shouldRun;

    static {
        try {
            shouldRun = m.readDataFromExcel(dataPath, "CheckList Scenarios", 3, 1);
            System.out.println("Should Run: " + shouldRun);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String domain = "ZA";

    // Extracting environment and browser details from test data sheet
    static {
        try {
            browser = m.readDataFromExcel(dataPath, "URL's", 0, 1).toUpperCase();
            environment = m.readDataFromExcel(dataPath, "URL's", 1, 1).toUpperCase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Login() throws IOException {
    }

    @AfterMethod
    public void close(ITestResult result) {
        // If test fails and driver is not null, print the correlation ID
        if (driver != null && result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Test Failed! Correlation ID: " + m.getCID(driver));
        }
        // Closes all active windows in the automation session
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "TestCase")
    public Object[][] getTestCase() throws IOException {
        List<Object[]> testCase = new ArrayList<>();
        int loginCount = m.getRowCount(dataPath, "Login")-1;
        System.out.println("Total logins Count: " + loginCount);

        for (int i = 1; i <= loginCount; i++) {
            String testCaseNumber = m.readDataFromExcel(dataPath, "Login", i, 0);
            String testSummary = m.readDataFromExcel(dataPath, "Login", i, 1);
            String username = m.readDataFromExcel(dataPath, "Login", i, 2);
            String password = m.readDataFromExcel(dataPath, "Login", i, 3);
            testCase.add(new Object[]{testCaseNumber, testSummary, username, password});
        }

        return testCase.toArray(new Object[0][]);
    }

    @Test(dataProvider = "TestCase")
    public void login(String testCaseNumber, String testSummary, String username, String password) throws IOException, InterruptedException {
        // Storing runtime into a variable
        runTime = m.getCurrentTime();
        String testStatus;

        // To skip test if the test case is not included in test
        if (!shouldRun.equalsIgnoreCase("Yes")) {
            // Storing test details into result document
            m.writeToExcel(testCaseNumber, 0, outputExcel); // Writes test case number
            m.writeToExcel("-", 1, outputExcel); // Writes booking reference
            testStatus = "Skipped";
            m.writeToExcel(testStatus, 2, outputExcel); // Writes test status
            m.writeToExcel("Skipped this test case as this test case is not approved to run", 3, outputExcel); // Writes skip reason
            m.writeToExcel("-", 4, outputExcel); // Prints correlation ID
            m.writeToExcel(runTime, 5, outputExcel);
            throw new SkipException("Test is skipped as this test case " + testCaseNumber + " is not approved to run");
        }

        // Printing the test case number when executing test
        System.out.println("Test Case: " + testCaseNumber + ", " + testSummary);

        // Launch browser
        if (browser.equalsIgnoreCase("Chrome")) {
            System.setProperty("webdriver.chrome.driver", Paths.chromeDriver);
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("Edge")) {
            System.setProperty("webdriver.edge.driver", Paths.edgeDriver);
            driver = new EdgeDriver();
        } else if (browser.equalsIgnoreCase("Firefox")) {
            System.setProperty("webdriver.gecko.driver", Paths.geckoDriver);
            driver = new FirefoxDriver();
        }

        // Maximize window
        driver.manage().window().maximize();

        // Setting up URL based on domain and environment
        switch (domain.toUpperCase()) {
            case "ZA":
                switch (environment) {
                    case "LIVE" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 4, 1);
                    case "BETA" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 6, 1);
                    case "PREPROD" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 8, 1);
                    case "ALPHA" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 10, 1);
                    default -> System.out.println("Invalid environment name");
                }
                break;
            case "NG":
                switch (environment) {
                    case "LIVE" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 5, 1);
                    case "BETA" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 7, 1);
                    case "PREPROD" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 9, 1);
                    case "ALPHA" -> baseURL = m.readDataFromExcel(dataPath, "URL's", 11, 1);
                    default -> System.out.println("Invalid environment name");
                }
                break;
            default:
                System.out.println("Invalid domain name");
        }

        // Launch URL
        driver.get(baseURL);

        TSMethods m = new TSMethods(driver);

        m.login(username, password);

    }
}
