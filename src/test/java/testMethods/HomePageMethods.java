package testMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageObjects.HomePage;

import java.time.Month;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class HomePageMethods {

    Method m = new Method();

    // AssertHomePage redirection

    public boolean assertHomePageRedirection(WebDriver driver){

        boolean isRedirectedToHomePage = false;

        isRedirectedToHomePage = m.verifyRedirection(driver, HomePage.search, "HomePage");

        return isRedirectedToHomePage;
    }

    public void selectTripType(WebDriver driver, String tripType) throws InterruptedException {

        //Selecting trip type in search if the trip type is oneway
        if (tripType.equalsIgnoreCase("Oneway")){

            driver.findElement(HomePage.oneWay).click();
            Thread.sleep(1000);

        }

    }


    // enter departure city or airport

    public void enterOrigin(WebDriver driver, String origin) throws InterruptedException {

        driver.findElement(HomePage.departureCity).click();
        driver.findElement(HomePage.departureCity).sendKeys(origin);

        // Waits for 2 seconds for city suggestions to come up
        Thread.sleep(1700);

        // Selecting city from suggestion
        driver.findElement(HomePage.option).click();

    }

    // enter arrival city or airport

    public void enterDestination(WebDriver driver, String destination) throws InterruptedException {

        // Entering arrival city
        driver.findElement(HomePage.arrivalCity).click();
        driver.findElement(HomePage.arrivalCity).sendKeys(destination);

        // Waits for 2 seconds for city suggestions to come up
        Thread.sleep(1700);

        // Selecting city from suggestion
        driver.findElement(HomePage.option).click();

    }


    // To select dates from search
    private void dateSelector(WebDriver driver, String value) {

        String date = String.format("(//*[@class='ngb-dp-day ng-star-inserted'])[%s]", value);
        driver.findElement(By.xpath(date)).click();

    }

    private void departureMonthSelector (WebDriver driver, String departureMonth) throws InterruptedException {

        // Define a map to map month numbers to month names
        Map<String, Integer> monthMap = new HashMap<>();
        monthMap.put("JANUARY", 1);
        monthMap.put("FEBRUARY", 2);
        monthMap.put("MARCH", 3);
        monthMap.put("APRIL", 4);
        monthMap.put("MAY", 5);
        monthMap.put("JUNE", 6);
        monthMap.put("JULY", 7);
        monthMap.put("AUGUST", 8);
        monthMap.put("SEPTEMBER", 9);
        monthMap.put("OCTOBER", 10);
        monthMap.put("NOVEMBER", 11);
        monthMap.put("DECEMBER", 12);

        // Get the current YearMonth
        YearMonth currentYearMonth = YearMonth.now();

        // Get the current month
        Month currentMonth = currentYearMonth.getMonth();

        String currentMonthInString = String.valueOf(currentMonth);

        String selectedMonth = driver.findElement(By.xpath("(//div[@class='ngb-dp-month-name ng-star-inserted'])[1]")).getText();
        String selectedMonthArr[] = selectedMonth.split(" ");

        driver.findElement(HomePage.departureDate).click();

        Thread.sleep(500);

        selectedMonth  =selectedMonthArr[0];

        boolean isCalenderPrefilled = false;

        if (!selectedMonth.equalsIgnoreCase(currentMonthInString)){

            isCalenderPrefilled = true;

        }

        currentMonthInString = selectedMonth;

        Thread.sleep(2000);


        int numberOfMonthsDifference = monthMap.get(departureMonth.toUpperCase()) - monthMap.get(currentMonthInString.toUpperCase());

        if (numberOfMonthsDifference > 0){
            for (int a = 0; a < numberOfMonthsDifference; a++) {
                Thread.sleep(200);

                driver.findElement(HomePage.nextMonth).click();
            }} else if (numberOfMonthsDifference < 0) {

            if (isCalenderPrefilled){

                for (int a = 0; a < numberOfMonthsDifference; a++) {
                    Thread.sleep(200);

                    driver.findElement(By.xpath("(//button[@title='Previous month'])[1]")).click();
                }

            }

            numberOfMonthsDifference = (12 - monthMap.get(currentMonthInString.toUpperCase()));
            numberOfMonthsDifference = (monthMap.get(departureMonth.toUpperCase())) + numberOfMonthsDifference;

            for (int a = 0; a < numberOfMonthsDifference; a++) {
                Thread.sleep(200);

                driver.findElement(HomePage.nextMonth).click();

            }
        }
    }

    private void returnMonthSelector (WebDriver driver, String departureMonth, String returnMonth) throws InterruptedException {

        // Define a map to map month numbers to month names
        Map<String, Integer> monthMap = new HashMap<>();
        monthMap.put("JANUARY", 1);
        monthMap.put("FEBRUARY", 2);
        monthMap.put("MARCH", 3);
        monthMap.put("APRIL", 4);
        monthMap.put("MAY", 5);
        monthMap.put("JUNE", 6);
        monthMap.put("JULY", 7);
        monthMap.put("AUGUST", 8);
        monthMap.put("SEPTEMBER", 9);
        monthMap.put("OCTOBER", 10);
        monthMap.put("NOVEMBER", 11);
        monthMap.put("DECEMBER", 12);

        int numberOfMonthsDifference = monthMap.get(returnMonth.toUpperCase()) - monthMap.get(departureMonth.toUpperCase());

        driver.findElement(By.xpath("//input[@id='arr_date0']")).click();

        if (numberOfMonthsDifference > 0){
            for (int a = 0; a < numberOfMonthsDifference; a++) {
                Thread.sleep(200);

                driver.findElement(HomePage.nextMonth).click();
            }} else if (numberOfMonthsDifference < 0) {
            numberOfMonthsDifference = (12 - monthMap.get(departureMonth.toUpperCase()));
            numberOfMonthsDifference = (monthMap.get(returnMonth.toUpperCase())) + numberOfMonthsDifference;

            for (int a = 0; a < numberOfMonthsDifference; a++) {
                Thread.sleep(200);

                driver.findElement(HomePage.nextMonth).click();

            }
        }
    }

    public void selectTravelDates(WebDriver driver, String tripType, String departureMonth, String departureDate) throws InterruptedException {


        if (tripType.equalsIgnoreCase("Oneway")) {

            // Clicks on departure date selection calendar
            driver.findElement(HomePage.departureDate).click();

            // Selects month provided in test case
            this.departureMonthSelector(driver, departureMonth);

            // Call the date selector method with a dynamic parameter
            String DepartureDate = m.doubleToString(departureDate);

            // Clicks on desired departure date in calendar
            this.dateSelector(driver, DepartureDate);

        }
    }

    public void selectTravelDates(WebDriver driver, String tripType, String departureMonth, String departureDate, String returnMonth, String returnDate) throws InterruptedException {


        if (tripType.equalsIgnoreCase("Oneway")) {

            // Clicks on departure date selection calendar
            driver.findElement(HomePage.departureDate).click();

            // Selects month provided in test case
            this.departureMonthSelector(driver, departureMonth);

            // Call the date selector method with a dynamic parameter
            String DepartureDate = m.doubleToString(departureDate);

            // Clicks on desired departure date in calendar
            this.dateSelector(driver, DepartureDate);

        }

        else if (tripType.equalsIgnoreCase("Return")) {

            // Clicks on departure date selection calendar
            driver.findElement(HomePage.departureDate).click();

            // Selects departure month provided in test case
            m.departureMonthSelector(driver, departureMonth);

            // Call the date selector method with a dynamic parameter
            String DepartureDate = m.doubleToString(departureDate);

            // Clicks on desired departure date in calendar
            this.dateSelector(driver, DepartureDate);

            // Selects return month provided in test case
            this.returnMonthSelector(driver, departureMonth, returnMonth);

            // Call the date selector method with a dynamic parameter
            String ReturnDate = m.doubleToString(returnDate);

            // Clicks on desired return date in calendar
            this.dateSelector(driver, ReturnDate);
        }

    }

    // To select cabin class
    public void cabinClassSelector(WebDriver driver, String cabinClass) throws InterruptedException {

        if (cabinClass.equalsIgnoreCase("Economy")){


        } else if (cabinClass.equalsIgnoreCase("Premium")){

            // Click on dropdown
            try{
                driver.findElement(HomePage.cabinClassDropDown).click();
            }catch (Exception e){
                System.out.println("Unable to click cabin class dropdown");
                e.printStackTrace();
            }
            Thread.sleep(100);

            try{
                driver.findElement(HomePage.premiumClass).click();
            }catch (Exception e){
                System.out.println("Unable to select premium class");
                e.printStackTrace();
            }

        } else if (cabinClass.equalsIgnoreCase("Business")){

            // Click on dropdown
            try{
                driver.findElement(HomePage.cabinClassDropDown).click();
            }catch (Exception e){
                System.out.println("Unable to click cabin class dropdown");
                e.printStackTrace();
            }
            Thread.sleep(100);

            try{
                driver.findElement(HomePage.businessClass).click();
            }catch (Exception e){
                System.out.println("Unable to select Business class");
                e.printStackTrace();
            }

        } else if (cabinClass.equalsIgnoreCase("First")){

            // Click on dropdown
            try{
                driver.findElement(HomePage.cabinClassDropDown).click();
            }catch (Exception e){
                System.out.println("Unable to click cabin class dropdown");
                e.printStackTrace();
            }
            Thread.sleep(100);

            try{
                driver.findElement(HomePage.firstClass).click();
            }catch (Exception e){
                System.out.println("Unable to select First class");
                e.printStackTrace();
            }

        } else {

            System.out.println("Given cabin class is not available, by default Economy got selected");

        }

    }


    // To select pax count from search section
    public void paxSelector(WebDriver driver, String adultCount, String youngAdultCount, String childCount, String infantCount) throws InterruptedException {

        driver.findElement(HomePage.passengerSelector).click();

        Thread.sleep(300);
        String adultSelector = String.format("//li[@data-adult-id='%s']", adultCount);
        driver.findElement(By.xpath(adultSelector)).click();

        Thread.sleep(300);
        String youngAdultSelector = String.format("//li[@data-youngadults-id='%s']", youngAdultCount);
        driver.findElement(By.xpath(youngAdultSelector)).click();

        Thread.sleep(300);
        String childSelector = String.format("//li[@data-child-id='%s']", childCount);
        driver.findElement(By.xpath(childSelector)).click();

        Thread.sleep(300);
        String infantSelector = String.format("//li[@data-infants-id='%s']", infantCount);
        driver.findElement(By.xpath(infantSelector)).click();

        driver.findElement(HomePage.applyPax).click();


    }

    public void makeFlightSearch(WebDriver driver, String tripType, String origin, String destination, String deartureMonth, String departureDate, String adultCount, String youngAdultCount, String childCount, String infantCount, String cabinClass) throws InterruptedException {

        this.selectTripType(driver, tripType);
        this.enterOrigin(driver, origin);
        this.enterDestination(driver, destination);
        this.selectTravelDates(driver,tripType, deartureMonth,departureDate);
        this.cabinClassSelector(driver, cabinClass);
        this.paxSelector(driver, adultCount, youngAdultCount, childCount, infantCount);

        driver.findElement(HomePage.search).click();

        Thread.sleep(1000);

    }

    public void makeFlightSearch(WebDriver driver, String tripType, String origin, String destination, String deartureMonth, String departureDate, String returnMonth, String returnDate, String adultCount, String youngAdultCount, String childCount, String infantCount, String cabinClass) throws InterruptedException {

        this.selectTripType(driver, tripType);
        this.enterOrigin(driver, origin);
        this.enterDestination(driver, destination);
        this.selectTravelDates(driver,tripType, deartureMonth,departureDate, returnMonth, returnDate);
        this.cabinClassSelector(driver, cabinClass);
        this.paxSelector(driver, adultCount, youngAdultCount, childCount, infantCount);

        driver.findElement(HomePage.search).click();

        Thread.sleep(1000);

    }











}
