package testMethods;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import pageObjects.Filters;
import pageObjects.HomePage;
import pageObjects.SRP;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SRPMethods {

    Method m = new Method();

    // Assert search result loaded or not
    public boolean isSRPLoaded(WebDriver driver) throws InterruptedException {

        boolean isSRPLoaded = false;

        isSRPLoaded = m.verifyRedirection(driver, SRP.results, SRP.errorModel);


        return isSRPLoaded;
    }

    public void openFilters(WebDriver driver, boolean isBundled) throws InterruptedException {


        if (isBundled) {
            try {

                WebElement filters = driver.findElement(Filters.filters);

                if (filters.isDisplayed()) {

                    filters.click();
                    Thread.sleep(1000);

                }
            } catch (NoSuchElementException e) {

            }
        } else if (!isBundled) {

            // Clicks on filters on SRP
            driver.findElement(SRP.unBundledFilter).click();
            Thread.sleep(1000);

        }

    }

    public void selectAirlineFilter(WebDriver driver, String tripType, boolean isBundled, String departAirline){

            try {
                WebElement airlineCheckBox = driver.findElement(Filters.airlineFilter(departAirline));

                try {
                    airlineCheckBox.click();
                } catch (ElementNotInteractableException e){

                    airlineCheckBox = driver.findElement(Filters.airlineFilter(departAirline, true));
                    airlineCheckBox.click();
                }

            } catch (NoSuchElementException e){

                System.out.println("Test is skipped as the given airline " +departAirline+ " was not available in search result");
                // Skips test execution as desired airline is not available in result
                throw new SkipException("Test is skipped as the given airline " +departAirline+ " was not available in search result");

            }

    }




    public void selectAirlineFilter(WebDriver driver, String tripType, boolean isBundled, String departAirline, String returnAirline){

        if (tripType.equalsIgnoreCase("Oneway")){

            this.selectAirlineFilter(driver, tripType, isBundled, departAirline);

        } else if (tripType.equalsIgnoreCase("Return")) {


        if (isBundled){

            this.selectAirlineFilter(driver, tripType, isBundled, departAirline);

        } else if (!isBundled) {

            this.selectAirlineFilter(driver, tripType, isBundled, departAirline);

            // Clicks on return airline filter button
            driver.findElement(Filters.returnAirline).click();



            this.selectAirlineFilter(driver, tripType, isBundled, returnAirline);


        }
        }


    }

    public void applyFilters(WebDriver driver){

        // Clicks on apply filters button
        try {
            driver.findElement(Filters.apply).click();
        }catch (ElementNotInteractableException e){
            try {
                driver.findElement(By.xpath("(//span[@class='close_icn'])[2]")).click();
            } catch (ElementNotInteractableException e1){

            }
        }

    }

    public void selectUnbundledFlights(WebDriver driver, boolean isBundled) throws InterruptedException {

        List<By> flights = SRP.pickFirstFlight(isBundled);

        if (!isBundled){

            driver.findElement(flights.get(0)).click();
            Thread.sleep(200);
            driver.findElement(flights.get(1)).click();

        } else if (isBundled) {

        }

    }

    public void selectUnbundledFlights(WebDriver driver, boolean isBundled, String outBoundSupplierCode, String inboundSupplierCode) throws InterruptedException {

        List<String> flightNumbers = SRP.getRequiredItnryNumberBySupplier(driver, outBoundSupplierCode, inboundSupplierCode);

        String outboundFlightNumber = flightNumbers.get(0);
        String inboundFlightNumber = flightNumbers.get(1);

        List<By> flights = SRP.pickUnbundledFlight(outboundFlightNumber, inboundFlightNumber);

        if (!isBundled){

            driver.findElement(flights.get(0)).click();
            Thread.sleep(200);
            driver.findElement(flights.get(1)).click();

        } else if (isBundled) {

        }

    }


    public void proceedToTravellerPage(WebDriver driver, boolean isBundled) throws InterruptedException {

        List<By> bookLocator = SRP.pickFirstFlight(isBundled);

        if (!isBundled) {

            this.selectUnbundledFlights(driver, isBundled);
            driver.findElement(SRP.bookUnbundled).click();

            Thread.sleep(500);

            try {

                // Clicks on proceed on airport change pop up
                driver.findElement(SRP.airPortChange).click();

            }catch (NoSuchElementException ne){

            }

        } else if (isBundled) {
            Thread.sleep(500);
            driver.findElement(bookLocator.get(0)).click();
            Thread.sleep(500);

            try {

                // Clicks on proceed on airport change pop up
                driver.findElement(SRP.airPortChange).click();

            }catch (NoSuchElementException ne){

            }
        }

        // Waits for 1 second for DOM to get refreshed
        Thread.sleep(1000);

    }

    public void proceedToTravellerPage(WebDriver driver, boolean isBundled, String supplierCode) throws InterruptedException {


        String itineraryNumber = SRP.getRequiredItnryNumberBySupplier(driver, supplierCode);
        By bookLocator = null;

        if (!itineraryNumber.isEmpty()) {

            bookLocator = SRP.pickBundledFlight(itineraryNumber);

        }

       if (isBundled) {
            Thread.sleep(500);
            try {
                driver.findElement(bookLocator).click();
            } catch (NullPointerException e){

            }
            Thread.sleep(500);

            try {

                // Clicks on proceed on airport change pop up
                driver.findElement(SRP.airPortChange).click();

            }catch (NoSuchElementException ne){


            }
        }

        // Waits for 1 second for DOM to get refreshed
        Thread.sleep(1000);

    }

    public void proceedToTravellerPage(WebDriver driver, boolean isBundled, String outboundSupplier, String inboundSupplier) throws InterruptedException {

        if (outboundSupplier.isEmpty() || outboundSupplier == null || outboundSupplier.equalsIgnoreCase("-")){

            proceedToTravellerPage(driver, isBundled);

        } else {

            if (!isBundled) {

                this.selectUnbundledFlights(driver, isBundled, outboundSupplier, inboundSupplier);
                driver.findElement(SRP.bookUnbundled).click();
                Thread.sleep(500);

                try {

                    // Clicks on proceed on airport change pop up
                    driver.findElement(SRP.airPortChange).click();

                } catch (NoSuchElementException ne) {

                }

            } else if (isBundled) {

                this.proceedToTravellerPage(driver, isBundled, outboundSupplier);

            }
        }

        // Waits for 1 second for DOM to get refreshed
        Thread.sleep(1000);

    }



    public int getTotalSegmentCount(WebDriver driver, String tripType, boolean isBundled){

        int segmentsCount = 0;
        int onwardSegmentCount = 0;
        int ReturnSegmentCount = 0;

        if (tripType.equalsIgnoreCase("Oneway")) {

            String onwardStops = driver.findElement(SRP.onwardStopsAndCabinInfo).getText();
            String onwardStopsArr[] = onwardStops.split("Stop");
            onwardStops = onwardStopsArr[0];


            if (!onwardStops.equalsIgnoreCase("Non ")) {

                onwardStops = onwardStops.trim();
                //System.out.println(onwardStops);
                onwardSegmentCount = Integer.parseInt(onwardStops) + 1;
            } else if (onwardStops.equalsIgnoreCase("Non ")) {

                onwardSegmentCount = 1;
            }
        }

        else if (tripType.equalsIgnoreCase("Return") && isBundled){

            String onwardStops = driver.findElement(SRP.onwardStopsAndCabinInfo).getText();
            String onwardStopsArr[] = onwardStops.split("Stop");
            onwardStops = onwardStopsArr[0];

            if (!onwardStops.equalsIgnoreCase("Non ")) {

                onwardStops = onwardStops.trim();
                onwardSegmentCount = Integer.parseInt(onwardStops) + 1;
            } else if (onwardStops.equalsIgnoreCase("Non ")) {

                onwardSegmentCount = 1;
            }



            String returnStops = driver.findElement(SRP.returnStopsAndCabinInfo).getText();
            String returnStopsArr[] = returnStops.split("Stop");
            returnStops = returnStopsArr[0];

            if (!returnStops.equalsIgnoreCase("Non ")) {

                returnStops = returnStops.trim();
                ReturnSegmentCount = Integer.parseInt(returnStops) + 1;

            } else if (returnStops.equalsIgnoreCase("Non ")) {

                ReturnSegmentCount= 1;

            }
        } else if (tripType.equalsIgnoreCase("Return") && !isBundled) {

            onwardSegmentCount = 1;
            ReturnSegmentCount = 1;

        }

        segmentsCount = onwardSegmentCount+ReturnSegmentCount;


        return segmentsCount;

    }

    public String getCurrency(WebDriver driver, boolean isBundled){

        String currency = "";

        if (isBundled){

            currency = driver.findElement(SRP.totalFareBundled).getText();

        } else if (!isBundled) {

            currency = driver.findElement(SRP.flightPriceUnbundled).getText();

        }

        currency = currency.replace(" ","");

        char currencyChar;

        currencyChar = currency.charAt(0);

        currency = String.valueOf(currencyChar);

        return currency;
    }

    public boolean isCurrencyValid(WebDriver driver, String domain, boolean isBundled){

        String expectedCurrency = "";
        boolean isCurrencyValid = false;

        String currency = this.getCurrency(driver, isBundled);

        if (domain.equalsIgnoreCase("ZA")){

            expectedCurrency = "R";

        }else if (domain.equalsIgnoreCase("NG")){

            expectedCurrency = "₦";

        }

        if (currency.equalsIgnoreCase(expectedCurrency)){

            isCurrencyValid = true;

        }else {

            isCurrencyValid = false;

        }

        return isCurrencyValid;
    }

    public boolean isSearchRoutesMatched(WebDriver driver, String origin, String destination){

        String from = driver.findElement(HomePage.departureCity).getAttribute("value");
        String to = driver.findElement(HomePage.arrivalCity).getAttribute("value");

        boolean isSearchRouteMatching = false;

        if (from.contains(origin) && to.contains(destination)){

            isSearchRouteMatching = true;

        }

        return isSearchRouteMatching;
    }


    public boolean verifyCountdownTimerStarted(WebDriver driver) {
        // Initialize wait with 10 seconds timeout
        WebDriverWait wait = null;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the countdown element to be visible
        WebElement countdownElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        SRP.sessionCountdown
                )
        );

        // Get initial time
        String initialTime = countdownElement.getText();

        // Wait for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get updated time
        String updatedTime = countdownElement.getText();

        // Convert times to seconds for comparison
        int initialSeconds = convertTimeToSeconds(initialTime);
        int updatedSeconds = convertTimeToSeconds(updatedTime);

        // Verify countdown is actually decreasing
        boolean sessionTtimerRunning = updatedSeconds < initialSeconds;
        return sessionTtimerRunning;
    }

    private int convertTimeToSeconds(String time) {
        String[] parts = time.split(":");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        return minutes * 60 + seconds;
    }

    public boolean verifyCountdownFormat(WebDriver driver) {
        WebDriverWait wait = null;
        WebElement countdownElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        SRP.sessionCountdown
                )
        );

        String timePattern = "\\d{2}:\\d{2}";
        boolean countDownForamtMatched = (countdownElement.getText().matches(timePattern));
        return countDownForamtMatched;
    }

    public boolean verifyCountdownPresence() {
        // Verify the countdown container is present
        WebDriverWait wait = null;
        WebElement countdownContainer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        SRP.sessionCountdown
                )
        );

        boolean countDownAppeared = (countdownContainer.isDisplayed());

        return countDownAppeared;
    }

}
