package testmethods;

import org.openqa.selenium.*;
import org.testng.SkipException;
import pageObjects.Filters;
import pageObjects.FlightPage;
import pageObjects.SRP;

import java.awt.datatransfer.FlavorListener;
import java.io.File;

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

                airlineCheckBox.click();

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


        }}


    }

    public void applyFilters(WebDriver driver){

        // Clicks on apply filters button
        try {
            driver.findElement(Filters.apply).click();
        }catch (ElementNotInteractableException e){
            driver.findElement(By.xpath("(//span[@class='close_icn'])[2]")).click();
        }

    }

    public void selectUnbundledFlights(WebDriver driver, boolean isBundled){

        if (!isBundled){

            // Selects outbound flight in result
            driver.findElement(SRP.outboundFlightUnbundled).click();

            // Selects inbound flight in result
            driver.findElement(SRP.inboundFlightUnbundled).click();

        } else if (isBundled) {

        }

    }

    public void proceedToTravellerPage(WebDriver driver, boolean isBundled) throws InterruptedException {

        if (!isBundled) {

            driver.findElement(SRP.domBook).click();

            Thread.sleep(500);

            try {

                // Clicks on proceed on airport change pop up
                driver.findElement(SRP.airPortChange).click();

            }catch (NoSuchElementException ne){

            }

        } else if (isBundled) {
            driver.findElement(SRP.book).click();
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


}
