package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import testMethods.Method;

import java.util.ArrayList;
import java.util.List;

public class SRP {

    Method m = new Method();

    public static By filters = By.xpath("//*[text()='Show Filters']");
    public static By unBundledFilter = By.xpath("(//button[@class='btn showFilter_btn'])[1]");
    public static By results = By.xpath("(//div[@class='row flightsViewSection ng-star-inserted'])[1]");
    public static By book = By.xpath("(//button[@aria-label='Book Now'])[1]");

    // Returns first flight in the list displayed on SRP
    public static List<By> pickFlight(boolean isBundled){

        List<By> bookButton = null;

        if(isBundled) {
            bookButton.add(By.xpath("(//button[@aria-label='Book Now'])[1]"));
        } else if (!isBundled) {

            bookButton.add(By.xpath("(//div[@class='row m-0 outBound return'])[1]"));
            bookButton.add(By.xpath("(//div[@class='row m-0 inBound return'])[1]"));

        }

        return bookButton;
    }

    private static final By SUPPLIER_NAME_BUNDLED = By.xpath("//div[contains(@class, 'flight_card ng-star-inserted')]//div[contains(@class, 'flight_row')][1]//span[contains(@class, 'airl_name')]");
    private static final By SUPPLIER_NAME_UNBUNDLED_OUTBOUND = By.xpath("//div[contains(@class, 'row m-0 outBound return')]//div[contains(@class, 'flight_row')][1]//span[contains(@class, 'airl_name')]");
    private static final By SUPPLIER_NAME_UNBUNDLED_INBOUND = By.xpath("//div[contains(@class, 'row m-0 inBound return')]//div[contains(@class, 'flight_row')][1]//span[contains(@class, 'airl_name')]");

    /**
     * Returns first flight's book button(s) from search results
     */
    public static List<By> pickFirstFlight(boolean isBundled) {
        List<By> bookButtons = new ArrayList<>();

        if (isBundled) {
            bookButtons.add(By.xpath("(//button[@aria-label='Book Now'])[1]"));
        } else {
            bookButtons.add(By.xpath("(//div[@class='row m-0 outBound return'])[1]"));
            bookButtons.add(By.xpath("(//div[@class='row m-0 inBound return'])[1]"));
        }

        return bookButtons;
    }

    /**
     * Returns book button for specific segment in bundled flights
     */
    public static By pickBundledFlight(String segmentNumber) {
        if (segmentNumber == null || segmentNumber.isEmpty()) {
            throw new IllegalArgumentException("Segment number cannot be empty");
        }
        return By.xpath("(//button[@aria-label='Book Now'])[" + segmentNumber + "]");
    }

    /**
     * Returns book buttons for specific segments in unbundled flights
     */
    public static List<By> pickUnbundledFlight(String outboundSegment, String inboundSegment) {
        if (outboundSegment == null || outboundSegment.isEmpty() ||
                inboundSegment == null || inboundSegment.isEmpty()) {
            throw new IllegalArgumentException("Segment numbers cannot be empty");
        }

        List<By> bookButtons = new ArrayList<>();
        bookButtons.add(By.xpath("(//div[@class='row m-0 outBound return'])[" + outboundSegment + "]"));
        bookButtons.add(By.xpath("(//div[@class='row m-0 inBound return'])[" + inboundSegment + "]"));
        return bookButtons;
    }

    /**
     * Gets itinerary number for bundled flight by supplier
     * @return itinerary number if found, empty string if not found
     * @throws SkipException if error occurs during search
     */
    public static String getRequiredItnryNumberBySupplier(WebDriver driver, String supplierCode) {
        try {
            if (supplierCode == null || supplierCode.isEmpty()) {
                throw new IllegalArgumentException("Supplier code cannot be empty");
            }

            String supplierName = getSupplierDisplayName(supplierCode);
            List<WebElement> suppliersElements = driver.findElements(SUPPLIER_NAME_BUNDLED);

            if (suppliersElements.isEmpty()) {
                System.out.println("No flights found on the page");
                return "";
            }

            for (int i = 0; i < suppliersElements.size(); i++) {
                if (suppliersElements.get(i).getText().contains(supplierName)) {
                    return String.valueOf(i + 1);
                }
            }

            System.out.println("No flights found for supplier: " + supplierName);
            throw new SkipException("Skipped this test as no flights found for supplier: " + supplierName);

        } catch (Exception e) {
            String errorMsg = "Error searching for flights: " + e.getMessage();
            System.err.println(errorMsg);
            throw new SkipException("Skipped this test as no flights found for supplier: " + supplierCode);
        }

    }

    /**
     * Gets itinerary numbers for unbundled flights by suppliers
     * @return List containing outbound and inbound itinerary numbers. Empty strings if not found.
     * @throws SkipException if error occurs during search
     */
    public static List<String> getRequiredItnryNumberBySupplier(WebDriver driver,
                                                                String outboundSupplierCode, String inboundSupplierCode) {
        try {
            if (outboundSupplierCode == null || outboundSupplierCode.isEmpty() ||
                    inboundSupplierCode == null || inboundSupplierCode.isEmpty()) {
                throw new IllegalArgumentException("Supplier codes cannot be empty");
            }

            List<String> itineraryNumbers = new ArrayList<>();
            String outboundSupplierName = getSupplierDisplayName(outboundSupplierCode);
            String inboundSupplierName = getSupplierDisplayName(inboundSupplierCode);

            // Find outbound flight
            List<WebElement> outboundElements = driver.findElements(SUPPLIER_NAME_UNBUNDLED_OUTBOUND);
            String outboundNumber = "";

            if (!outboundElements.isEmpty()) {
                for (int i = 0; i < outboundElements.size(); i++) {
                    if (outboundElements.get(i).getText().contains(outboundSupplierName)) {
                        outboundNumber = String.valueOf(i + 1);
                        break;
                    }
                }
            }

            if (outboundNumber.isEmpty()) {
                System.out.println("No outbound flight found for supplier: " + outboundSupplierName);
            }

            // Find inbound flight
            List<WebElement> inboundElements = driver.findElements(SUPPLIER_NAME_UNBUNDLED_INBOUND);
            String inboundNumber = "";

            if (!inboundElements.isEmpty()) {
                for (int i = 0; i < inboundElements.size(); i++) {
                    if (inboundElements.get(i).getText().contains(inboundSupplierName)) {
                        inboundNumber = String.valueOf(i + 1);
                        break;
                    }
                }
            }

            if (inboundNumber.isEmpty()) {
                System.out.println("No inbound flight found for supplier: " + inboundSupplierName);
            }

            itineraryNumbers.add(outboundNumber);
            itineraryNumbers.add(inboundNumber);
            return itineraryNumbers;

        } catch (Exception e) {
            String errorMsg = "Error searching for flights: " + e.getMessage();
            System.err.println(errorMsg);
            throw new RuntimeException(errorMsg, e);
        }
    }

    /**
     * Maps supplier code to display name
     */
    private static String getSupplierDisplayName(String supplierCode) {
        switch (supplierCode.toUpperCase()) {
            case "VT": return "Verteil";
            case "1A": return "Amadeus";
            case "RX": return "Radix";
            case "AC": return "AeroCRS";
            case "TX": return "Thomalex";
            default: throw new IllegalArgumentException("Unknown supplier code: " + supplierCode);
        }
    }


    public static By loader = By.xpath("//div[@class='loader']");

    public static By bookUnbundled = By.xpath("(//button[@aria-label='Book this flight'])[1]");
    public static By airPortChange = By.xpath("//button[@class='btn ok-btn onHover refreshBtn primary_btn ng-star-inserted']");
    public static By flightPriceUnbundled = By.xpath("(//div[normalize-space()='Total Price'])[1]");
    public static By baggageInfo = By.xpath("(//div[@class='baggage_Info ng-star-inserted'])[1]");
    public static By depDateInput = By.xpath("//input[@aria-label='dept_date']");

    public static By totalFareUnbundled = By.xpath("(//div[@class='fare'])[1]");
    public static By totalFareBundled = By.xpath("(//div[@class='price_labl'])[1]");

    public static By priceOfSelectedPrice(boolean isunBudled, String flightPosition){

        By element;

        if (isunBudled){

            element = By.xpath("(//div[contains(@class, 'flight_card ng-star-inserted')])["+flightPosition+"]//div[contains(@class, 'price_labl')]");

        }else {

            element = SRP.flightPriceUnbundled;

        }


        return element;
    }

    public static By inboundFlightUnbundled = By.xpath("(//div[@class='row m-0 inBound return'])[1]");
    public static By outboundFlightUnbundled = By.xpath("(//div[@class='row m-0 outBound return'])[1]");

    public static By visaRequiredPopUp = By.xpath("//div[text()='ATTENTION GOOD LOOKING!']");
    public static By visaProceed = By.xpath("//button[@class='btn ok-btn onHover refreshBtn primary_btn ng-star-inserted']");

    public static By errorModel = By.xpath("//div[@class='err_page text-center']");


    public static By expandFlightDetails(){

        return By.xpath("(//a[@class='flight_details_link'])[1]");
    }

    public static By expandFlightDetails(boolean isBundled, String flightPosition){

        if (flightPosition.isEmpty() || flightPosition == null || flightPosition.equalsIgnoreCase("-")){

            return By.xpath("(//a[@class='flight_details_link'])[1]");

        } else {

            return By.xpath("(//div[contains(@class, 'flight_card ng-star-inserted')])["+flightPosition+"]//a[contains(@class, 'flight_details_link')]");

        }

    }


    // Flight details:
    // Flight numbers:
    public static By flightNumberOutBoundUnbundled = By.xpath("(//div[contains(@class, 'row m-0 outBound return active')]//span[@class='airl_numb d-none d-lg-block ng-star-inserted'])[1]");
    public static By flightNumberInBoundUnbundled = By.xpath("(//div[contains(@class, 'row m-0 inBound return active')]//span[@class='airl_numb d-none d-lg-block ng-star-inserted'])[1]");

    public static By flightNumberOnward = By.xpath("(//span[@class='airl_numb d-none d-lg-block ng-star-inserted'])[1]");
    public static By flightNumberReturn = By.xpath("(//span[@class='airl_numb d-none d-lg-block ng-star-inserted'])[2]");

    //Segment wise flightNumber
    public static By flightNumberFirstSegment = By.xpath("(//span[@class='airl_numb'])[1]");
    public static By flightNumberSecondSegment = By.xpath("(//span[@class='airl_numb'])[2]");
    public static By flightNumberThirdSegment = By.xpath("(//span[@class='airl_numb'])[3]");
    public static By flightNumberFourthSegment = By.xpath("(//span[@class='airl_numb'])[4]");
    public static By flightNumberFifthSegment = By.xpath("(//span[@class='airl_numb'])[5]");
    public static By flightNumberSixthSegment = By.xpath("(//span[@class='airl_numb'])[6]");

    // Stops info
    public static By onwardStopsAndCabinInfo = By.xpath("(//div[@class='stops ng-star-inserted'])[1]");
    public static By returnStopsAndCabinInfo = By.xpath("(//div[@class='stops ng-star-inserted'])[2]");


    //Countdown
    public static By sessionCountdown = By.cssSelector("span.countdown");
}
