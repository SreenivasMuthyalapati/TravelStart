package testClasses.UnitFunctionalTest;

import configs.dataPaths;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import testMethods.*;
import utils.TestReport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GFSDeeplinks {

    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = "C:\\Users\\Sreen\\IdeaProjects\\travelStart\\TestData\\GFSDeeplinksTest.xls";
    static String environment;
    static String browser;
    static String runTime;
    static ExcelUtils excelUtils = new ExcelUtils();
    static WapiMethods wapiMethods = new WapiMethods();
    static TravellerDetailsPageMethods travellerDetailsPageMethods = new TravellerDetailsPageMethods();

    static SoftAssert softAssert = new SoftAssert();

    static TestReport testReport = new TestReport();
    static List<String> attachments = new ArrayList<>();

    static List<String[]> testCases = new ArrayList<>();
    static SendEmail sendEmail = new SendEmail();
    List<Object[]> itineraryScenarios = new ArrayList<>();


    // Extracting environment from test data sheet
    static {
        try {
            environment = excelUtils.readDataFromExcel(dataPaths.URLs, "URL's", 1, 1);
            browser = excelUtils.readDataFromExcel(dataPaths.URLs, "URL's", 0, 1);
        } catch (IOException e) {

            System.out.println("Failed to get URL or Browser data");
            throw new RuntimeException(e);
        }
    }


    public GFSDeeplinks() throws IOException {
    }

    @BeforeTest
    public void createTestReports() throws IOException {

        // Get test cases
        //testCases = m.getTestCasesFromTestCasesDocument(dataPaths.seatsTest, "Test Cases");
        excelUtils.createExcelReport();


    }

    String testScenatioIDIntoExcel = "";
    String IDIntoExcel = "";
    String deeplinkIntoExcel = "";
    String priceIntoExcel = "";
    String flightNumbersIntoExcel = "";
    boolean isPriceMatching = false;
    boolean isFlightNumbersMatching = false;



    @AfterMethod
    public void close(ITestResult result) throws IOException {
        if (driver != null && result.getStatus() == ITestResult.FAILURE) {
            // If test fails and driver is not null, print the correlation ID
            System.out.println("Test Failed! Correlation ID: " + m.getCID(driver));
        }


    }


    @AfterTest
    public void reportTestResult() throws IOException, InterruptedException {

        String excelPath = excelUtils.saveExcelReport();
        attachments.add(excelPath);
        sendEmail.sendEmail("GFS Deeplink Test Result", attachments);

        if (driver != null) {

            driver.quit();

        }

    }


    @DataProvider(name = "SearchScenarios")
    public Object[][] getTestCase() throws IOException {
        List<Object[]> testCase = new ArrayList<>();

        // Extracting all test data from test cases in test data sheet
        int totalPaxCount = excelUtils.getRowCount(dataPath, "Search Scenarios");

        for (int i = 2; i < totalPaxCount; i++) {

            String testScenatioID = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 0);
            String shouldRun = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 1);
            String domain = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 2);
            String tripType = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 3);
            String origin = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 4);
            String destination = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 5);
            String departureDate = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 6);
            String returnDate = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 7);
            String adultCount = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 8);
            String youngAdultCount = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 9);
            String childCount = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 10);
            String infantCount = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 11);
            String cabinClass = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 12);
            String airline = excelUtils.readDataFromExcel(dataPath, "Search Scenarios", i, 13);

            testCase.add(new Object[]{testScenatioID,
                    shouldRun,
                    domain,
                    tripType,
                    origin,
                    destination,
                    departureDate,
                    returnDate,
                    adultCount,
                    youngAdultCount,
                    childCount,
                    infantCount,
                    cabinClass,
                    airline
            });
        }
        return testCase.toArray(new Object[0][]);
    }


    @Test(dataProvider = "SearchScenarios")
    public void makeSearch(String testScenatioID, String shouldRun, String domain, String tripType, String origin, String destination, String departureDate, String returnDate, String adultCount, String youngAdultCount, String childCount, String infantCount, String cabinClass, String airlineCode) throws IOException, InterruptedException {


        runTime = m.getCurrentTime();

        if (!shouldRun.equalsIgnoreCase("TRUE")) {

            System.out.println("Skipped this test");
            throw new SkipException("Test is skipped as this test case " + testScenatioID + " is not approved to run");

        }

        JSONObject searchResponse = new JSONObject();

        searchResponse = wapiMethods.hitSearchEndPoint(environment, tripType, cabinClass, origin, destination, departureDate, returnDate, adultCount, youngAdultCount, childCount, infantCount);

        boolean isSearchReturned = true;

        if (searchResponse == null){

            isSearchReturned = false;

        } else {

            isSearchReturned = true;

        }

        Assert.assertTrue(isSearchReturned, "Search response wasn't returned");

        JSONArray itineraries = new JSONArray();
        itineraries = wapiMethods.getItinerariesFromSearchResponse(searchResponse);
        int itinerariesCount = itineraries.length();

        int i = 0;
        int itineraryCountToTest = 1;
        while (i < itinerariesCount && itineraryCountToTest <= 2){

            String airline = wapiMethods.getAirlineCodesFromItineraryObject(itineraries.getJSONObject(i));

            if (airline.contains(airlineCode)) {
                String[] data = new String[5];
                data[0] = testScenatioID;
                String deeplink = wapiMethods.getItineraryDeeplinkFromSearchResponse(itineraries.getJSONObject(i));
                data[1] = String.valueOf(wapiMethods.getItineraryIDFromSearchResponse(itineraries.getJSONObject(i)));
                data[2] = deeplink;
                data[3] = String.valueOf(wapiMethods.getItineraryPriceFromSearchResponse(itineraries.getJSONObject(i)));
                data[4] = wapiMethods.getFlightNumbersFromItineraryObject(itineraries.getJSONObject(i));
                itineraryScenarios.add(data);
                itineraryCountToTest++;
            } else {

            }
                i++;
        }
        }

    @DataProvider(name = "itineraryTest")
    public Object[][] getItineraryData() throws IOException {
        return itineraryScenarios.toArray(new Object[0][]);
    }

    int itineraryTestDataIndex = 0;


    @Test (dataProvider = "itineraryTest")
        public void testDeeplinks(String testScenatioID, String ID, String deeplink, String price, String flightNumbers) throws InterruptedException {


            softAssert = new SoftAssert();
            String failMessage = "";

            boolean isItineraryHasDeeplink = false;


            if (!(deeplink == null)){

                isItineraryHasDeeplink = true;

            }

            if (!isItineraryHasDeeplink){

                failMessage = "Itinerary ID: "+ ID +" in search scenario: "+ testScenatioID +" had no deeplink";

            }
            Assert.assertTrue(isItineraryHasDeeplink, failMessage);

            if (driver == null) {
                driver = m.launchBrowser(driver, browser);
            }

            driver.manage().window().maximize();

            driver.get(deeplink);
            Thread.sleep(5000);

            boolean isRedirectionPass = travellerDetailsPageMethods.isTravellerPageLoaded(driver);

            if (!isRedirectionPass){

                failMessage = "Deeplink in search scenario: "+ testScenatioID+", ID: "+ID+" hasn't redirected to flight details page";
                attachments.add(testReport.takeScreenshotIfFailed(driver, testScenatioID, ID, isRedirectionPass));
                isFlightNumbersMatching = false;
                isPriceMatching = false;
                excelUtils.writeToExcelReport(testScenatioID, ID, deeplink, price, flightNumbers, false, false);


            }
            Assert.assertTrue(isRedirectionPass, failMessage);

            int amountAfterRedirection = travellerDetailsPageMethods.getFlightCost(driver);
            int amountInSearchResponse = m.stringToInt(price);

            isPriceMatching = (amountAfterRedirection == amountInSearchResponse);


        List<String> flightNumbersAfterDeeplinkRedirection = travellerDetailsPageMethods.getFlightNumbers(driver);
        List<String> flightNumbersInSearchResponse = new ArrayList<>();
        flightNumbersInSearchResponse = List.of(flightNumbers.split(","));

        isFlightNumbersMatching = false;
        isFlightNumbersMatching = m.validateTwoListsMatching(flightNumbersInSearchResponse, flightNumbersAfterDeeplinkRedirection);


        if (!isPriceMatching){

                failMessage = "Mismatch between amount in search response("+amountInSearchResponse+") and amount displayed after deeplink redirection("+amountAfterRedirection+") for search scenario: "+ testScenatioID+", ID: "+ID;
                attachments.add(testReport.takeScreenshotIfFailed(driver, testScenatioID, ID, isPriceMatching));

            }

            softAssert.assertTrue(isPriceMatching, failMessage);





            if (!isFlightNumbersMatching){

                failMessage = "Mismatch between flight numbers in search response("+flightNumbersInSearchResponse+") and flight numbers displayed after deeplink redirection("+flightNumbersAfterDeeplinkRedirection+") for search scenario: "+ testScenatioID+", ID: "+ID;
                attachments.add(testReport.takeScreenshotIfFailed(driver, testScenatioID, ID, isFlightNumbersMatching));

            }

        excelUtils.writeToExcelReport(testScenatioID, ID, deeplink, price, flightNumbers, isPriceMatching, isFlightNumbersMatching);


        softAssert.assertTrue(isFlightNumbersMatching, failMessage);
            softAssert.assertAll();
            softAssert = null;

        }

    }

