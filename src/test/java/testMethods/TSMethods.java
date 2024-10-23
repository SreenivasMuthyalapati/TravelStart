package testMethods;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import pageObjects.*;
import pageObjects.B2B.WebPush;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TSMethods {
    
    
    static ExcelUtils excelUtils = new ExcelUtils();
    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = configs.dataPaths.dataPath;
    static String browser;
    static String environment;
    static String outputExcel = configs.dataPaths.excelOutputPath;
    static String baseURL;
    static String runTime;
    static String screenShotPath ="";
    static String testStatus = "";
    //Initializing wait explicitly
    static WebDriverWait wait;


    // Constructor to initialize WebDriverWait
    public TSMethods(WebDriver driver) {
        try {

            TSMethods.driver = driver;
            wait = new WebDriverWait(driver, 90);
        }catch (NullPointerException e){

        }
    }



    public void searchFlight(String testCaseNumber, String tripType, String origin, String destination, String departureDate, String departureMonth, String returnDate, String returnMonth, String adultCount, String youngAdultCount, String childCount, String infantCount) throws InterruptedException, IOException {


        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(HomePage.oneWay));

        }catch (TimeoutException e){

        }

        //Selecting trip type in search if the trip type is oneway
        if (tripType.equalsIgnoreCase("Oneway")){

            driver.findElement(HomePage.oneWay).click();

        }

        // Entering departure city
        Thread.sleep(1000);

        driver.findElement(HomePage.departureCity).click();
        driver.findElement(HomePage.departureCity).sendKeys(origin);

        // Waits for 2 seconds for city suggestions to come up
        Thread.sleep(1700);

        // Selecting city from suggestion
        driver.findElement(HomePage.option).click();

        // Entering arrival city
        driver.findElement(HomePage.arrivalCity).click();
        driver.findElement(HomePage.arrivalCity).sendKeys(destination);

        // Waits for 2 seconds for city suggestions to come up
        Thread.sleep(2000);

        // Selecting city from suggestion
        driver.findElement(HomePage.option).click();

        //Creates instance to select date provided in test case
        HomePage dateSelect = new HomePage(driver);

        //Selecting date if trip is oneway
        if (tripType.equalsIgnoreCase("Oneway")){

            // Clicks on departure date selection calendar
            driver.findElement(HomePage.departureDate).click();

            // Selects month provided in test case
            m.departureMonthSelector(driver, departureMonth);

            // Call the date selector method with a dynamic parameter
            String DepartureDate = m.doubleToString(departureDate);

            // Clicks on desired departure date in calendar
            WebElement departureDateElement = dateSelect.dateSelector(DepartureDate);
            departureDateElement.click();

        }

        // Selecting date if trip is return
        else if (tripType.equalsIgnoreCase("Return")) {

            // Clicks on departure date selection calendar
            driver.findElement(HomePage.departureDate).click();

            // Selects departure month provided in test case
            m.departureMonthSelector(driver, departureMonth);

            // Call the date selector method with a dynamic parameter
            String DepartureDate = m.doubleToString(departureDate);

            // Clicks on desired departure date in calendar
            WebElement departureDateElement = dateSelect.dateSelector(DepartureDate);
            departureDateElement.click();

            // Selects return month provided in test case
            m.returnMonthSelector(driver,departureMonth, returnMonth);

            // Call the date selector method with a dynamic parameter
            String ReturnDate = m.doubleToString(returnDate);

            // Clicks on desired return date in calendar
            WebElement returnDateElement = dateSelect.dateSelector(ReturnDate);
            returnDateElement.click();

        }


        // Selecting Pax type and count
        driver.findElement(HomePage.passengerSelector).click();

        //Instance of homepage for pax selection
        HomePage paxSelect = new HomePage(driver);

        // Selecting Pax type and count
        paxSelect.paxSelector(adultCount, youngAdultCount, childCount, infantCount);

        // Clicks on apply after selecting passengers
        driver.findElement(HomePage.applyPax).click();

        //Clicking search
        driver.findElement(HomePage.search).click();
        Thread.sleep(2000);



        //Handling notification
        try {
            driver.switchTo().frame("webpush-onsite");
            driver.findElement(HomePage.denyNotification).click();

            driver.switchTo().defaultContent();
        } catch (NoSuchElementException | NoSuchFrameException e) {

        }

        System.out.println("Correlation ID: "+ m.getCID(driver));

        // Asserting result

        boolean isResultAvailable = false;

        isResultAvailable = m.verifyRedirection(driver, SRP.results, SRP.errorModel);

        Assert.assertTrue(isResultAvailable, "Result not loaded");


    }


    public void SelectAirline(String testCaseNumber, String tripType,String isBundled, String departureAirline, String returnAirline) throws IOException, InterruptedException {


        // Selecting airline from filter
        // Airline selection if trip type is oneway
        if (tripType.equalsIgnoreCase("Oneway")){

            // Clicks on filters button on SRP
            driver.findElement(SRP.filters).click();

            // Creating instance to select airline
            Filters airlineInstance = new Filters(driver);

            // Call the airlineFilter method with a dynamic parameter
            try {

                // Selects airline in filters
                WebElement airlineFilterElement = driver.findElement(airlineInstance.airlineFilter(departureAirline));
                airlineFilterElement.click();

            }

            // Ignores test if desired airline is not available in result loaded
            catch (NoSuchElementException e){


                // Skips the test as desired airline is not available in result
                throw new SkipException("Test is skipped as the given airline " +departureAirline+ " was not available in search result");
            }

            // Clicks on apply filter button
            try {
                driver.findElement(Filters.apply).click();
            }catch (ElementNotInteractableException e){
                driver.findElement(By.xpath("(//span[@class='close_icn'])[2]")).click();
            }
            // Waits for 1 second to refresh DOM
            Thread.sleep(1000);


        }

        // Selecting airlines in filters if flights are bundled and trip type is return
        else if (tripType.equalsIgnoreCase("return") && isBundled.equalsIgnoreCase("Yes" )){

            // Clicks on filters
            driver.findElement(SRP.filters).click();

            // Creating instance to select airline from filters
            Filters airlineInstance = new Filters(driver);

            // Call the airlineFilter method with a dynamic parameter
            try {

                Thread.sleep(1000);
                // Selects desired airline in filters
                WebElement airlineFilterElement = driver.findElement(airlineInstance.airlineFilter(departureAirline));
                airlineFilterElement.click();

            }

            // Skipping test if desired airline is not available in result
            catch (NoSuchElementException e){


                // Skips test execution as desired airline is not available in result
                throw new SkipException("Test is skipped as the given airline " +departureAirline+ " was not available in search result");

            }

            // Clicks on apply filters button
            try {
                driver.findElement(Filters.apply).click();
            }catch (ElementNotInteractableException e){
                driver.findElement(By.xpath("(//span[@class='close_icn'])[2]")).click();
            }


            // Wait for 1 second for the DOM to get referesh
            Thread.sleep(1000);


        }

        // Selecting airline from filter if the searched flights are unbundled
        else if (isBundled.equalsIgnoreCase("No")) {

            // Clicks on filters on SRP
            driver.findElement(SRP.unBundledFilter).click();

            // Creating instance to select airline in filters
            Filters airlineInstance = new Filters(driver);

            // Call the airlineFilter method with a dynamic parameter
            try {

                // Selects desired airline in filters
                WebElement airlineFilterElement = driver.findElement(airlineInstance.airlineFilter(departureAirline));
                airlineFilterElement.click();

            }

            // Skips test if the resired airline is not available in result
            catch (NoSuchElementException e){

                // Skips test as the desired airline is not available in result
                throw new SkipException("Test is skipped as the given airline " +departureAirline+ " was not available in search result");
            }

            // Clicks on return airline filter button
            driver.findElement(Filters.returnAirline).click();

            // Call the airlineFilter method with a dynamic parameter
            try {

                // Selects desired return airline
                WebElement airlineFilterElement = driver.findElement(airlineInstance.airlineFilter(departureAirline));
                airlineFilterElement.click();

            }

            // Skips test if the desired airline is not available in result
            catch (NoSuchElementException e){


                // Skips test execution as desired airline is not available in result
                throw new SkipException("Test is skipped as the given airline " +returnAirline+ " was not available in search result");

            }

            // Clicks on apply filters button
            try {
                driver.findElement(Filters.apply).click();
            }catch (ElementNotInteractableException e){
                driver.findElement(By.xpath("(//span[@class='close_icn'])[2]")).click();
            }

            // Selects outbound flight in result
            driver.findElement(SRP.outboundFlightUnbundled).click();

            // Selects inbound flight in result
            driver.findElement(SRP.inboundFlightUnbundled).click();


    }



}

    public void clickBook(String testCaseNumber, String triptype, String isBundled) throws InterruptedException, IOException {

        if (triptype.equalsIgnoreCase("Oneway")){

            driver.findElement(SRP.book).click();

        } else if (triptype.equalsIgnoreCase("Return") && isBundled.equalsIgnoreCase("No")) {
            driver.findElement(SRP.domBook).click();

            Thread.sleep(500);
                try {

                    // Clicks on proceed on airport change pop up
                    driver.findElement(SRP.airPortChange).click();

                }catch (NoSuchElementException ne){

                }

        } else if (triptype.equalsIgnoreCase("Return") && isBundled.equalsIgnoreCase("Yes")) {
            driver.findElement(SRP.book).click();
            Thread.sleep(500);
            try {

                // Clicks on proceed on airport change pop up
                driver.findElement(SRP.airPortChange).click();

            }catch (NoSuchElementException ne){

            }
        }

        try {

            // Clicks on proceed on airport change pop up
            driver.findElement(By.xpath("//button[@class='btn ok-btn mr-3 text-center primary_btn']")).click();

        }catch (NoSuchElementException| ElementNotInteractableException e){

        }

        // Waits for 1 second for DOM to get refreshed
        Thread.sleep(1000);

        // Asserting Traveller Page
        WebElement travellerPage = null;
        try {

            // Waits until flight details page is loaded for maximum 60 seconds
            wait.until(ExpectedConditions.visibilityOfElementLocated(FlightPage.flightReviewPage));
            travellerPage = driver.findElement(FlightPage.flightReviewPage);

        }
        catch (NoSuchElementException | TimeoutException e) {

        }

        // Initializing boolean variable to asser flight details page
        boolean istravellerPageAvailable = false;

        try{

            // Assigning boolean value to assertion variable if flight details page is available
            istravellerPageAvailable = travellerPage.isDisplayed();

        }catch (NullPointerException e){


        }
        if (istravellerPageAvailable){

            System.out.println("Traveller page loaded");

        }

        // To return test failure information into test result document if flight details page is not loaded
        else {

            System.out.println("Traveller Page not loaded");

        }

        // Proceeding if fare increase pop up appears
        try {

            // Clicks on proceed on fare increase pop up
            driver.findElement(FlightPage.fareIncreaseContinue).click();

        }catch (Exception e){


        }

        // Initializing boolean variable to assert traveller details page
        boolean isTravellerPageAvailable = false;

        try {

            // Stores boolean value as true if traveller details page is loaded
            isTravellerPageAvailable = travellerPage.isDisplayed();

        } catch (NullPointerException e){


        }

        // Asserting if traveller details page is available or not
        Assert.assertTrue(isTravellerPageAvailable, "Traveller page  not loaded");

    }


    public void enterPaxDetails(String isLoggedIn, String testCaseNumber, String tripType, String adultCount, String youngAdultCount, String childCount, String infantCount, String departureAirline, String returnAirline, String mailID, String mobileNumber, String title, String firstName, String middleName, String lastName, String dateOfBirth, String monthOfBirth, String yearOfBirth, String passPortNumber, String dateOfPassportExpiry, String monthOfPassportExpiry, String yearOfPassportExpiry, String passPortNationality, String passPortIssuingCountry, String addBaggage, String whatsApp) throws IOException, InterruptedException {


        // To check is the airline is flysafair for test surname
        boolean isFAFlight = false;

        // To check is the airline is flysafair for test surname
        if (tripType.equalsIgnoreCase("Oneway") && departureAirline.equalsIgnoreCase("FA")){

            isFAFlight = true;

        } else if (tripType.equalsIgnoreCase("Return") && departureAirline.equalsIgnoreCase("FA") && returnAirline.equalsIgnoreCase("FA")) {

            isFAFlight = true;

        }


        String lastname;
        if (isFAFlight) {

            // Storing surname as "Test" if the airline is FlySafair
            lastname = "Test";

        } else {

            // Storing surname as actual if the airline is not FlySafair
            lastname = lastName;

        }


        // Sending contact details in booking
        // Sending mobile number
        driver.findElement(FlightPage.mobileNo).clear();
        driver.findElement(FlightPage.mobileNo).sendKeys(mobileNumber);


        // Sending mail ID
        driver.findElement(FlightPage.email).clear();
        driver.findElement(FlightPage.email).sendKeys(mailID);

        // Deselecting whatsapp add on if product is included in test case
        if (whatsApp.equalsIgnoreCase("No")){
            try {
                // Deselects whatsapp
                driver.findElement(FlightPage.whatsApp).click();
            }catch (NoSuchElementException e){

            }
        }

        Thread.sleep(500);

        //Adding PAX details

        // Initializing a webelement to store primary pax title
        WebElement paxTitle = null;

        // Storing "Mr" title into webelement if test case has the title as Mr
        if (title.equalsIgnoreCase("mr")){

            paxTitle =  driver.findElement(FlightPage.mr);

        }

        // Storing "Ms" title into webelement if test case has the title as Ms
        else if (title.equalsIgnoreCase("ms")){

            paxTitle = driver.findElement(FlightPage.ms);

        }

        // Storing "Mrs" title into webelement if test case has the title as Mrs
        else if (title.equalsIgnoreCase("mrs")) {

            paxTitle = driver.findElement(FlightPage.mrs);

        }

        // Selecting passenger title
        paxTitle.click();

        // Sending First Name
        driver.findElement(FlightPage.firstName).sendKeys(firstName);

        // Sending Last Name
        driver.findElement(FlightPage.lastName).sendKeys(lastname);

        // Date of birth selection
        // Storing date of birth selections into webelements
        WebElement day = driver.findElement(FlightPage.dayDOB);
        WebElement month = driver.findElement(FlightPage.monthDOB);
        WebElement year = driver.findElement(FlightPage.yearDOB);


        // Selection of date of birth

        m.selectFromDropDown(driver, day, dateOfBirth);
        m.selectFromDropDown(driver, month, monthOfBirth);
        m.selectFromDropDown(driver, year, yearOfBirth);



        //Add checked baggage
        if (addBaggage.equalsIgnoreCase("Yes")){

            try {

                List<WebElement> addBaggageChecks = driver.findElements(FlightPage.addCheckedBaggage);
                for (WebElement addBaggageCheck : addBaggageChecks) {
                    addBaggageCheck.click();
                }
            }catch (NoSuchElementException e){
                System.out.println("Baggage addition is not available for this flight");
            }
        }

        //Passport details
        WebElement passPortInfo = null;
        try {
            passPortInfo = driver.findElement(FlightPage.ppInfo);
            passPortInfo = driver.findElement(FlightPage.ppInfo);
        } catch (NoSuchElementException ne) {
            System.out.println("PassPort details not required for this flight");
        }
        try {
            if (passPortInfo.isDisplayed()) {
                WebElement passportNumber = driver.findElement(FlightPage.ppNumber);
                passportNumber.sendKeys(passPortNumber);

                WebElement passportExpiryday = driver.findElement(FlightPage.ppExpiryDate);
                WebElement passportExpirymonth = driver.findElement(FlightPage.ppExpiryMonth);
                WebElement passportExpiryyear = driver.findElement(FlightPage.ppExpiryYear);


                // Select passport expiry using click method

                m.selectFromDropDown(driver,passportExpiryday,dateOfPassportExpiry);
                m.selectFromDropDown(driver,passportExpirymonth, monthOfPassportExpiry);
                m.selectFromDropDown(driver,passportExpiryyear, yearOfPassportExpiry);

                driver.findElement(FlightPage.ppNationality).click();
                driver.findElement(FlightPage.ppnationalityIndia).click();

                Thread.sleep(1000);
                driver.findElement(FlightPage.ppIssuingCountry).click();
                driver.findElement(FlightPage.ppInsuingCountryIndia).click();

            }
        } catch (Exception e) {

        }

        int adultCountTiInt = Integer.parseInt(adultCount);
        int youngAdultCountTiInt = Integer.parseInt(youngAdultCount);
        int childCountTiInt = Integer.parseInt(childCount);
        int infantCountTiInt = Integer.parseInt(infantCount);

        if (adultCountTiInt > 1 || youngAdultCountTiInt > 0 || childCountTiInt > 0 || infantCountTiInt > 0){
            m.paxSender(driver, adultCount, youngAdultCount, childCount, infantCount, departureAirline, returnAirline);
        }

        //Handling notification
        try {
            driver.switchTo().frame("webpush-onsite");
            driver.findElement(HomePage.denyNotification).click();

            driver.switchTo().defaultContent();
        } catch (NoSuchElementException | NoSuchFrameException e) {

        }

        driver.findElement(FlightPage.contnue).click();
        System.out.println("Traveller details have been added");
        Thread.sleep(200);

    }

    public double add_seats(String addSeats) throws InterruptedException {

        String totalPrice;
        
        double totalPriceinDouble = 0;

        boolean seatsOffered = false;

        try{
            WebElement seats = driver.findElement(SeatsPage.seatSelectionBlock);
            seatsOffered = true;
        }catch (Exception e){
            seatsOffered = false;
            System.out.println("Seats not offered for this flight");
        }

        if (addSeats.equalsIgnoreCase("No") && seatsOffered) {

            try {

                if (driver.findElement(By.xpath("(//h4[@class='seat-map-drawer__header-text d-inline seat_header'])[1]")).isDisplayed()) {

                    Thread.sleep(1000);

                    driver.findElement(By.xpath("//button[@class='btn btn-link st_btn st_btn_clear']")).click();

                    Thread.sleep(1000);

                }
            } catch (NoSuchElementException e) {

            }
        } else if (addSeats.equalsIgnoreCase("Yes") && seatsOffered) {

            WebElement seatsSelectionBlock;
            boolean isSeatsOffered = false;

            try {

                seatsSelectionBlock = driver.findElement(SeatsPage.seatSelectionBlock);
                isSeatsOffered = true;

            } catch (NoSuchElementException e) {

                isSeatsOffered = false;
                System.out.println("Seats selection not available for this flight");

            }

                if (isSeatsOffered) {

                    Thread.sleep(1000);

                    // Asserting seat map
                    // Initializes webelement result to store displayed result
                    WebElement seatMap = null;

                    try {
                        // To wait until result is loaded (Waits for 60 seconds maximum)
                        wait.until(ExpectedConditions.visibilityOfElementLocated(SeatsPage.seatMap));
                        seatMap = driver.findElement(SeatsPage.availableSeats);

                    } catch (NoSuchElementException | TimeoutException e) {

                    }

                    // Initializing a boolean variable for result assertion
                    boolean isSeatMapAvailable = false;

                    try{
                        // Stores true if result is available
                        isSeatMapAvailable = seatMap.isDisplayed();

                    }catch (NullPointerException e){

                    }

                    if (isSeatMapAvailable){
                        System.out.println("Seat map loaded");
                    }

                    // Initializing a boolean variable for result assertion
                    isSeatMapAvailable = false;

                    try{
                        // Stores true if result is avaiable
                        isSeatMapAvailable = seatMap.isDisplayed();

                    }catch (NullPointerException e){

                    }

                    if (!isSeatMapAvailable){

                        m.takeScreenshot(driver, configs.dataPaths.screenshotFolder, screenShotPath);
                        totalPrice = "";

                    }


                    Assert.assertTrue(isSeatMapAvailable, "Seats page  not loaded");


                    //Selecting seats

                    int totalSegmentCount = 0;

                    List<WebElement> segments  = driver.findElements(SeatsPage.unselectedSegment);

                    totalSegmentCount = segments.size()+1;

                    SeatsPage seats = new SeatsPage(driver);

                    Thread.sleep(100);
                    driver.findElement(SeatsPage.availableSeats).click();

                    Thread.sleep(2000);

                    try {

                        totalPrice = driver.findElement(SeatsPage.totalCostOfSeats).getText();

                    } catch (NoSuchElementException e){

                        driver.findElement(SeatsPage.availableSeats).click();

                        Thread.sleep(2000);

                        totalPrice = driver.findElement(SeatsPage.totalCostOfSeats).getText();

                    }


                   // totalPriceinDouble = (m.amountToDouble(totalPrice));


                    driver.findElement(SeatsPage.continueToAddons).click();

                    Thread.sleep(500);

                    try{

                        driver.findElement(SeatsPage.continueInPopUp).click();

                    }catch (Exception e){

                    }

                    Thread.sleep(1000);
                }


        }


        return totalPriceinDouble;
    }

    public void add_Addons(String domain, String addFlexi) throws InterruptedException {
        // To deselect addOns

        boolean addOnsOffered = false;

        try{

            WebElement addOns = driver.findElement(AddOnsPage.addOnsBlock);
            addOnsOffered = true;

        }catch (Exception e){
            addOnsOffered = false;
        }

        if (addOnsOffered) {

            try {
                List<WebElement> selectedAddons = driver.findElements(AddOnsPage.selectedAddons);
                int numberOfSelectedAddOns = selectedAddons.size();

                for (int i = 0; i < numberOfSelectedAddOns; ) {
                    WebElement selectedAddon = selectedAddons.get(i);
                    if (selectedAddon.isDisplayed()) {
                        selectedAddon.click();
                    }
                    // Re-find the list of selected addons after each interaction
                    selectedAddons = driver.findElements(AddOnsPage.selectedAddons);
                    int newNumberOfSelectedAddOns = selectedAddons.size();

                    // Check if the number of selected addons has decreased
                    if (newNumberOfSelectedAddOns < numberOfSelectedAddOns) {
                        numberOfSelectedAddOns = newNumberOfSelectedAddOns;
                        // Restart the loop as the indices may have shifted
                        continue;
                    }

                    numberOfSelectedAddOns = newNumberOfSelectedAddOns;
                    i++; // Proceed to the next addon
                }
            } catch (Exception e) {
                System.out.println("No addons selected by default");
            }
        }

        if (addOnsOffered) {

            // To add flexi
            try {
                List<WebElement> availableAddons = driver.findElements(AddOnsPage.addOnName);
                List<WebElement> SelectAddons = driver.findElements(AddOnsPage.selectAddon);
                int numberOfAddOns = availableAddons.size();
                if (addFlexi.equalsIgnoreCase("Yes")) {
                    for (int i = 0; i < numberOfAddOns; ) {
                        WebElement availableAddon = availableAddons.get(i);
                        WebElement SelectaddOn = SelectAddons.get(i);
                        if (availableAddon.getText().contains("Flexible Travel Dates") && SelectaddOn.isDisplayed()) {
                            SelectaddOn.click();
                        }
                        // Re-find the list of selected addons after each interaction
                        availableAddons = driver.findElements(AddOnsPage.addOnName);
                        int newNumberOfAddOns = availableAddons.size();

                        // Check if the number of selected addons has decreased
                        if (newNumberOfAddOns < numberOfAddOns) {
                            numberOfAddOns = newNumberOfAddOns;
                            // Restart the loop as the indices may have shifted
                            continue;
                        }

                        numberOfAddOns = newNumberOfAddOns;
                        i++; // Proceed to the next addon
                    }
                }
            } catch (NoSuchElementException e) {
                System.out.println("Add ons not available");
            }


            if (domain.equalsIgnoreCase("NG") || domain.equalsIgnoreCase("B2B_NG")) {

                Thread.sleep(2000);
                driver.findElement(AddOnsPage.checkBoxNG).click();
            }
        }

        if (addOnsOffered){
        driver.findElement(AddOnsPage.contnue).click();
        Thread.sleep(200);
        try {
            driver.findElement(AddOnsPage.noIWillRiskIt).click();
        } catch (Exception e){

        }}

        try{
            driver.findElement(AddOnsPage.contnue).click();
        }catch (NoSuchElementException e){

        }

        Thread.sleep(1000);

        try{

            String duplicateBookingRef = driver.findElement(By.xpath("//div[@class='fare_txt']")).getText();

            String duplicateBookingRefArray [] = duplicateBookingRef.split("check ");
            duplicateBookingRef = duplicateBookingRefArray[1];

            duplicateBookingRefArray = duplicateBookingRef.split("booking status");

            duplicateBookingRef = duplicateBookingRefArray[0];

            System.out.println("This appears to be a duplicate booking. There is another recent booking with reference "+ duplicateBookingRef);

            driver.findElement(By.xpath("//button[@aria-label='Yes, Continue']")).click();

        }catch (Exception e){

        }
    }

    public void paymentAndBooking(String environment, String testCaseNumber, String domain, String paymentMethod, String bankNameEFT, String isToBeCancelled) throws IOException, InterruptedException {

        Thread.sleep(2000);

        String bookingReference = "";

        // Asserting Payment Page
        WebElement paymentPage = null;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.bookingSummary));
            paymentPage = driver.findElement(PaymentPage.bookingSummary);

        } catch (NoSuchElementException | TimeoutException e) {

        }
        boolean ispaymentPageAvailable = false;
        try{
            ispaymentPageAvailable = paymentPage.isDisplayed();
        }catch (NullPointerException e){

        }
        if (ispaymentPageAvailable){
            System.out.println("Payment page loaded");
            try {
                bookingReference = driver.findElement(PaymentPage.bookingReference).getText();
            } catch (NoSuchElementException e){

            }
        }else {
            m.takeScreenshot(driver, configs.dataPaths.screenshotFolder, screenShotPath);
            m.getConsole(driver);
            File screenShotFile = new File(screenShotPath);
           // m.sendNotification(testCaseNumber, "Not redirected to payment screen or not redirected within 60 seconds");

        }

        Assert.assertTrue(ispaymentPageAvailable, "Payment page not loaded");

        String timeOne = "";
        String timeTwo = "";

        //Payment in ZA - EFT
        if ((domain.equalsIgnoreCase("ZA") || domain.equalsIgnoreCase("FS"))&& paymentMethod.equalsIgnoreCase("EFT")){
            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.EFT));
            driver.findElement(PaymentPage.EFT).click();

            switch (bankNameEFT) {

                //Banks
                case "Nedbank" -> driver.findElement(PaymentPage.nedBank).click();
                case "FNB" -> driver.findElement(PaymentPage.fnb).click();
                case "Standard Bank" -> driver.findElement(PaymentPage.standardBank).click();
                case "ABSA" -> driver.findElement(PaymentPage.absa).click();


                default -> System.out.println("Invalid bank name");

            }

            driver.findElement(PaymentPage.payNow).click();

        }

        //Paying with card
        else if (paymentMethod.equalsIgnoreCase("cc/dc") && (domain.equalsIgnoreCase("ZA") || domain.equalsIgnoreCase("FS"))){
            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.credicCardOrDebitCard));
            driver.findElement(PaymentPage.credicCardOrDebitCard).click();
            String cardNumber = excelUtils.readDataFromExcel(dataPath, "Card detals", 2,1);
            String cardHolderName = excelUtils.readDataFromExcel(dataPath, "Card detals", 2,2);
            String cardExpiryMonth = (excelUtils.readDataFromExcel(dataPath, "Card detals", 2,3));
            String cardExpiryYear = excelUtils.readDataFromExcel(dataPath, "Card detals", 2,4);
            String CVV = m.doubleToString(excelUtils.readDataFromExcel(dataPath, "Card detals", 2,5));
            String AddressLine1 = excelUtils.readDataFromExcel(dataPath, "Card detals", 2,6);
            String AddressLine2 = excelUtils.readDataFromExcel(dataPath, "Card detals", 2,7);
            String PostalCode = m.doubleToString(excelUtils.readDataFromExcel(dataPath, "Card detals", 2,8));
            String city = excelUtils.readDataFromExcel(dataPath, "Card detals", 2,9);
            String country = excelUtils.readDataFromExcel(dataPath, "Card detals", 2,10);
            String contactNumber = excelUtils.readDataFromExcel(dataPath, "Card detals", 2,11);



            driver.findElement(PaymentPage.cardNumber).sendKeys(cardNumber);
            driver.findElement(PaymentPage.cardHolderName).sendKeys(cardHolderName);

            WebElement CardExpiryMonthElement = driver.findElement(PaymentPage.cardExpiryMonth);
            WebElement CardExpiryYearElement = driver.findElement(PaymentPage.cardExpiryYear);


            m.selectFromDropDown(driver, CardExpiryMonthElement, cardExpiryMonth);


            m.selectFromDropDown(driver, CardExpiryYearElement, cardExpiryYear);

            driver.findElement(PaymentPage.CVV).sendKeys(CVV);

            driver.findElement(PaymentPage.payNow).click();


        }

        // Pay using iPay
        else if (domain.equalsIgnoreCase("ZA") && (paymentMethod.equalsIgnoreCase("Instant EFT") || paymentMethod.equalsIgnoreCase("IPAY"))) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.iPay));
            driver.findElement(PaymentPage.iPay).click();
            //driver.findElement(PaymentPage.payNow).click();

        }

        // Pay using NG EFT
        else if (domain.equalsIgnoreCase("NG") && paymentMethod.equalsIgnoreCase("EFT")) {

            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.EFT));
            driver.findElement(PaymentPage.EFT).click();

            switch (bankNameEFT) {

                //Banks
                case "travelstart" -> driver.findElement(PaymentPage.travelStart).click();
                case "Access" -> driver.findElement(PaymentPage.access).click();
                case "UBA" -> driver.findElement(PaymentPage.UBA).click();
                case "Zenith" -> driver.findElement(PaymentPage.zenithBank).click();


                default -> System.out.println("Invalid bank name");

            }
            driver.findElement(PaymentPage.reserve).click();


        }
        //Pay using Paystack
        else if (domain.equalsIgnoreCase("NG") && (paymentMethod.equalsIgnoreCase("Instant EFT")||paymentMethod.equalsIgnoreCase("Paystack"))) {

            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.payStack));
            driver.findElement(PaymentPage.payStack).click();

            driver.findElement(PaymentPage.payNow).click();


        }

        // B2B NG Wallet
        else if (domain.equalsIgnoreCase("B2B_NG") && paymentMethod.equalsIgnoreCase("Wallet")) {

            Thread.sleep(2000);

        driver.findElement(pageObjects.B2B.PaymentPage.payFromWallet).click();


        System.out.println("Domain and payment method matched");

        }

        // B2B FS Wallet

        else if (domain.equalsIgnoreCase("B2B_FS") && paymentMethod.equalsIgnoreCase("Wallet")) {

            wait.until(ExpectedConditions.elementToBeClickable(pageObjects.B2B.PaymentPage.payFromWallet));
            driver.findElement(pageObjects.B2B.PaymentPage.payFromWallet).click();

        }

        // B2B FS Reserve only
        else if (domain.equalsIgnoreCase("B2B_FS") && paymentMethod.equalsIgnoreCase("Reserve")) {

            driver.findElement(pageObjects.B2B.PaymentPage.reserveOnly).click();

        }

        // B2B CT EFT
        else if (domain.equalsIgnoreCase("B2B_CT") && paymentMethod.equalsIgnoreCase("EFT")) {

            driver.findElement(pageObjects.B2B.PaymentPage.EFTTab).click();

            Thread.sleep(1000);

            driver.findElement(pageObjects.B2B.PaymentPage.nedBank).click();
            driver.findElement(pageObjects.B2B.PaymentPage.paywithEFT).click();


        }

        // B2B CT Card
        else if (domain.equalsIgnoreCase("B2B_CT") && paymentMethod.equalsIgnoreCase("cc/dc")) {



            String cardNumber = excelUtils.readDataFromExcel(dataPath, "Card detals", 2,1);
            String cardHolderName = excelUtils.readDataFromExcel(dataPath, "Card detals", 2,2);
            String cardExpiryMonth = (excelUtils.readDataFromExcel(dataPath, "Card detals", 2,3));
            String cardExpiryYear = excelUtils.readDataFromExcel(dataPath, "Card detals", 2,4);
            String CVV = m.doubleToString(excelUtils.readDataFromExcel(dataPath, "Card detals", 2,5));

            driver.findElement(pageObjects.B2B.PaymentPage.cardNumber).sendKeys(cardNumber);


            String enteredCardNumber = driver.findElement(pageObjects.B2B.PaymentPage.cardNumber).getAttribute("value");


            try {
                if (!enteredCardNumber.equalsIgnoreCase(cardNumber)) {

                    driver.findElement(pageObjects.B2B.PaymentPage.cardNumber).clear();

                    driver.findElement(pageObjects.B2B.PaymentPage.cardNumber).sendKeys(cardNumber);

                }
            } catch (NullPointerException e){

            }

            driver.findElement(pageObjects.B2B.PaymentPage.cardHolderName).sendKeys(cardHolderName);

            WebElement CardExpiryMonthElement = driver.findElement(pageObjects.B2B.PaymentPage.cardExpiryMonth);
            WebElement CardExpiryYearElement = driver.findElement(pageObjects.B2B.PaymentPage.cardExpiryYear);

            m.selectFromDropDown(driver, CardExpiryYearElement, cardExpiryYear);
            m.selectFromDropDown(driver, CardExpiryMonthElement, cardExpiryMonth);

            m.selectFromDropDown(driver, CardExpiryYearElement, cardExpiryYear);
            driver.findElement(pageObjects.B2B.PaymentPage.CVV).sendKeys(CVV);


            driver.findElement(pageObjects.B2B.PaymentPage.payWithCard).click();


        }

        else {

            System.out.println("Domain or payment method is not found :"+" Domain is "+ domain+" and payment method is "+ paymentMethod);

        }

        timeOne = m.getCurrentTime();


        Thread.sleep(10000);

        // Asserting Booking confirmation Page
        WebElement bookingConfirm = null;

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(BookingConfirmationPage.isBookingConfirmed));
            timeTwo = m.getCurrentTime();
            bookingConfirm = driver.findElement(BookingConfirmationPage.isBookingConfirmed);
        } catch (NoSuchElementException | TimeoutException e) {

        }
        boolean isbookingRefAvailable = false;
        try{
            isbookingRefAvailable = bookingConfirm.isDisplayed();
        }catch (NullPointerException e){

        } catch (TimeoutException te){
            System.out.println("Booking failed due to this booking took more than 1 minute");
        }
        if (isbookingRefAvailable){
            bookingReference = driver.findElement(BookingConfirmationPage.refNumber).getText();
            bookingReference = bookingReference.trim();
            System.out.println("Booking completed. "+ bookingReference);

            if (isToBeCancelled.equalsIgnoreCase("Yes")){
                m.cancelBooking(environment, bookingReference);
            }



        }else {
            m.takeScreenshot(driver, configs.dataPaths.screenshotFolder, screenShotPath);
            m.getConsole(driver);
            File screenShotFile = new File(screenShotPath);
            //m.sendNotification(testCaseNumber, "Booking not succeeded");
        }

        Assert.assertTrue(isbookingRefAvailable, "Booking failed");

    }



    public void login(String username, String password) throws InterruptedException {

        Thread.sleep(500);
        driver.findElement(HomePage.myAccount).click();
        Thread.sleep(800);
        driver.findElement(LoginPage.username).sendKeys(username);
        driver.findElement(LoginPage.password).sendKeys(password);
        driver.findElement(LoginPage.login).click();
        Thread.sleep(5000);

        Thread.sleep(2000);

        try {
            driver.findElement(HomePage.profile).click();
            Thread.sleep(1000);
            driver.findElement(HomePage.myProfile).click();
            Thread.sleep(1000);
            String loggedInMail = driver.findElement(By.xpath("//input[@placeholder='name@gmail.com']")).getAttribute("value");

            Assert.assertTrue(loggedInMail.equalsIgnoreCase(username), "Login Failed");

        } catch (NoSuchElementException e){

        }

        try {
            WebElement logout = driver.findElement(HomePage.logout);

            Assert.assertTrue(logout.isDisplayed(), "Login uncessessful");

        } catch (NoSuchElementException e){

        }

        driver.findElement(By.xpath("(//img[@class='logoTS branding_img'])[3]")).click();
        Thread.sleep(1000);

    }

    public Map<String, String> getPriceBreakdown(By tablesElement){

        List<WebElement> tables = driver.findElements(tablesElement);

        int priceBreakdownCategoriesCount = tables.size();

        Map<String, String> dataMap = new HashMap<>();

        for (int i =0; i < priceBreakdownCategoriesCount; i++){
        // Find all rows within the table

            WebElement table = driver.findElement(FlightPage.priceTable(String.valueOf(i+1)));

            List<WebElement> rows = table.findElements(By.tagName("tr"));

        // Iterate through each row
        for (WebElement row : rows) {
            // Find th and td elements within the row

            List<WebElement> cells = row.findElements(By.tagName("th"));
            cells.addAll(row.findElements(By.tagName("td")));

            // Extract text from th and td elements
            if (cells.size() == 2) {
                // Assuming each row has exactly one th and one td
                String key = cells.get(0).getText().trim();
                String value = cells.get(1).getText().trim();

                // Store in the map (if both key and value are not empty)
                if (!key.isEmpty() && !value.isEmpty()) {
                    dataMap.put(key, value);
                }
            }
        }}

        return dataMap;
    }


    public void manageNotification(WebDriver driver, String action){

        WebElement iframe = null;
        boolean isIframeAvailable = false;

        try{
            iframe = driver.findElement(WebPush.webPushiFrame);

            if (iframe.isDisplayed()){

                isIframeAvailable = true;

            }else{

                isIframeAvailable = false;
            }

        }catch (NoSuchElementException | NullPointerException e){

        }

        if (isIframeAvailable){

            driver.switchTo().frame(iframe);

            if (action.equalsIgnoreCase("Allow")){

                driver.findElement(WebPush.allowNotification).click();

            } else if (action.equalsIgnoreCase("Deny")) {

                driver.findElement(WebPush.deyNotification).click();

            }

            driver.switchTo().defaultContent();

        }

    }

}
