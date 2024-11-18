package testCaseMethods.bookingFlowE2E;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pageObjects.SRP;
import testMethods.*;
import utils.TestReport;

import java.util.ArrayList;
import java.util.List;

public class BookingFlowTestCases {

    static Method method = new Method();
    static TestReport testReport = new TestReport();
    static SRPMethods srpMethods = new SRPMethods();
    static TravellerDetailsPageMethods travellerDetailsPageMethods = new TravellerDetailsPageMethods();
    static SeatsPageMethods seatsPageMethods = new SeatsPageMethods();
    static AddOnsPageMethods addOnsPageMethods = new AddOnsPageMethods();
    static PaymentPageMethods paymentPageMethods = new PaymentPageMethods();
    static ThreeDSecureMethods threeDSecureMethods = new ThreeDSecureMethods();
    static BookingConfirmationPageMethods bookingConfirmationPageMethods = new BookingConfirmationPageMethods();

List<String[]> testCases = new ArrayList<>();

int tcID = 0;
int tcSummary = 1;
int runAutomation = 2;

// Get test cases from excel sheet

    public void BookingFlowE2E_Search_01(WebDriver driver, SoftAssert softAssert, String testScenarioID, List<String[]> testCases, List<String[]> testResultData, String CID, String origin, String destination) throws InterruptedException {

        int rowIndex = 0;

        if (Boolean.parseBoolean(testCases.get(rowIndex)[runAutomation])) {
            StringBuilder failMessageBuilder = new StringBuilder();

            // Verify search initiation
            boolean searchInitiated = method.verifyRedirection(driver, SRP.loader, "Search Loader");
            if (!searchInitiated) {
                failMessageBuilder.append("-> Search was not initiated with valid search data for test scenario: ").append(testScenarioID);
            }

            // Verify search result loading
            boolean isResultLoaded = srpMethods.isSRPLoaded(driver);
            if (!isResultLoaded) {
                failMessageBuilder.append(" | -> Search result did not load for test scenario: ").append(testScenarioID)
                        .append(". Waited for 75 seconds.");
            }

            // Verify search result matches search parameters
            boolean isSearchResultMatchedWithSearchParameters = srpMethods.isSearchRoutesMatched(driver, origin, destination);
            if (!isSearchResultMatchedWithSearchParameters) {
                failMessageBuilder.append(" | -> Search result did not match search parameters for test scenario: ").append(testScenarioID);
            }


            // Verify session timer
            boolean isCountDownStarted = srpMethods.verifyCountdownPresence();
            boolean isCountDownRunning = srpMethods.verifyCountdownPresence();
            if (!isCountDownStarted) {
                failMessageBuilder.append(" | -> Session timer has not started on search initiation for test scenario: ").append(testScenarioID);
            }

            if (!isCountDownRunning) {
                failMessageBuilder.append(" | -> Session timer has not started on search initiation for test scenario: ").append(testScenarioID);
            }

            boolean countDownWorking = isCountDownStarted && isCountDownRunning;


            // Compile test result
            boolean testPassed = searchInitiated && isResultLoaded && isSearchResultMatchedWithSearchParameters && countDownWorking;
            String failMessage = failMessageBuilder.toString();

            // Update test report
            testReport.updateTestResult(testResultData, driver, testScenarioID, testCases.get(rowIndex)[tcID], testCases.get(rowIndex)[tcSummary], CID, "-", failMessage, testPassed);

            // Asserts all test Cases
            Assert.assertTrue(searchInitiated, "Search was not initiated with valid search data for test scenario: "+testScenarioID);
            Assert.assertTrue(isResultLoaded, "Search result did not load for test scenario: "+(testScenarioID)+". Waited for 75 seconds.");
            softAssert.assertTrue(isSearchResultMatchedWithSearchParameters, "Search result did not match search parameters for test scenario: "+(testScenarioID));
            softAssert.assertTrue(countDownWorking, "Session timer has not started on search initiation for test scenario: "+testScenarioID);

        }
    }


    public void BookingFlowE2E_SRP_01(WebDriver driver, String testScenarioID, List<String[]> testCases, List<String[]> testResultData, String CID, boolean isBundled) {

        int rowIndex = 1;

        if (Boolean.parseBoolean(testCases.get(rowIndex)[runAutomation])) {
            StringBuilder failMessageBuilder = new StringBuilder();

            // Verify search result loading
            boolean isTravellerDetailsLoaded = travellerDetailsPageMethods.isTravellerPageLoaded(driver);

            if (!isTravellerDetailsLoaded) {
                failMessageBuilder.append("-> Traveller details page did not load for test scenario: ").append(testScenarioID)
                        .append(". Waited for 75 seconds.");
            }


            // Compile test result
            boolean testPassed = isTravellerDetailsLoaded;
            String failMessage = failMessageBuilder.toString();

            // Update test report
            testReport.updateTestResult(testResultData, driver, testScenarioID, testCases.get(rowIndex)[tcID], testCases.get(rowIndex)[tcSummary], CID, "-", failMessage, testPassed);
            Assert.assertTrue(isTravellerDetailsLoaded, "Traveller details page did not load for test scenario: "+(testScenarioID)
                    +". Waited for 75 seconds.");


        }

    }

    public void BookingFlowE2E_TDP_01(WebDriver driver, String testScenarioID, List<String[]> testCases, List<String[]> testResultData, String CID, boolean isBundled) {

        int rowIndex = 2;

        if (Boolean.parseBoolean(testCases.get(rowIndex)[runAutomation])) {
            StringBuilder failMessageBuilder = new StringBuilder();

            // Verify search result loading
            boolean isTravellerDetailsLoaded = travellerDetailsPageMethods.isTravellerPageLoaded(driver);

            if (!isTravellerDetailsLoaded) {
                failMessageBuilder.append("-> Traveller details page did not load for test scenario: ").append(testScenarioID)
                        .append(". Waited for 75 seconds.");
            }

            // Compile test result
            boolean testPassed = isTravellerDetailsLoaded;
            String failMessage = failMessageBuilder.toString();

            // Update test report
            testReport.updateTestResult(testResultData, driver, testScenarioID, testCases.get(rowIndex)[tcID], testCases.get(rowIndex)[tcSummary], CID, "-", failMessage, testPassed);
            Assert.assertTrue(isTravellerDetailsLoaded, "Traveller details page did not load for test scenario: "+(testScenarioID)
                    +(". Waited for 75 seconds."));

        }

    }

}
