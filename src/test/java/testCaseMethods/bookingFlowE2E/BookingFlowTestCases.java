package testCaseMethods.bookingFlowE2E;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageObjects.SRP;
import testMethods.Method;
import utils.TestReport;

import java.util.ArrayList;
import java.util.List;

public class BookingFlowTestCases {

    static Method method = new Method();
    static TestReport testReport = new TestReport();

List<String[]> testCases = new ArrayList<>();

int tcID = 0;
int tcSummay = 1;
int runAutomation = 2;

// Get test cases from excel sheet

    public void BookingFlowE2E_Search_01(WebDriver driver, String testScenarioID, List<String[]> testCases, List<String[]> testResultData, String CID){

        int rowNumber = 0;

        if(Boolean.parseBoolean(testCases.get(rowNumber)[runAutomation])) {

                boolean searchInitiated = method.verifyRedirection(driver, SRP.loader, "Search Loader");
                String failMessage = "Search was not initiated with valid search data given for test scenario:"+ testScenarioID;
                testReport.updateTestResult(testResultData, driver, testScenarioID, testCases.get(rowNumber)[tcID], testCases.get(rowNumber)[tcSummay], CID, "-", failMessage, searchInitiated);
                Assert.assertTrue(searchInitiated, failMessage);


        }
    }

    public void BookingFlowE2E_SRP_01(WebDriver driver, String testScenarioID, List<String[]> testCases, List<String[]> testResultData, String CID){

        int rowNumber = 1;

        if(Boolean.parseBoolean(testCases.get(rowNumber)[runAutomation])) {

            boolean searchInitiated = method.verifyRedirection(driver, SRP.loader, "Search Loader");
            String failMessage = "Search was not initiated with valid search data given for test scenario:"+ testScenarioID;
            testReport.updateTestResult(testResultData, driver, testScenarioID, testCases.get(rowNumber)[tcID], testCases.get(rowNumber)[tcSummay], CID, "-", failMessage, searchInitiated);

            Assert.assertTrue(searchInitiated, failMessage);


        }
    }


}
