package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import pageObjects.Paths;
import testmethods.SendEmail;

public class ExtentReportListener implements ITestListener {
    private static ExtentReports extent;
    private static ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter spark = new ExtentSparkReporter(Paths.dataBasePath+"\\TestResult\\report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Test passed");
        //test.info("");

    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    @AfterSuite
    public void Login() {
        //Instantiate SendEmail class
        SendEmail emailSender = new SendEmail();
//
//        // Call the sendEmail method
        emailSender.sendEmail();
//        // Initialize ChromeDriver
//        ChromeOptions options = new ChromeOptions();
//        // Add options as needed, e.g., options.addArguments("--remote-debugging-port=9222");
//        ChromeDriver driver = new ChromeDriver(options);
//        // Initialize the NetworkTabReader
//        NetworkTabReader networkReader = new NetworkTabReader((ChromeDriver) driver);
//        networkReader.enableNetworkTracking();
//
//        // Retrieve network logs if needed
//        networkReader.getNetworkLogs();
//
//        // Cleanup
//        networkReader.stopNetworkTracking();
    }
}
