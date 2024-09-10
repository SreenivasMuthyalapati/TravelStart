package pageObjects;

import org.openqa.selenium.By;
import testmethods.Method;

public class SRP {

    Method m = new Method();

    public static By filters = By.xpath("//*[text()='Show Filters']");
    public static By unBundledFilter = By.xpath("(//button[@class='btn showFilter_btn'])[1]");
    public static By results = By.xpath("(//div[@class='row flightsViewSection ng-star-inserted'])[1]");
    public static By book = By.xpath("(//button[@aria-label='Book Now'])[2]");
    public static By domBook = By.xpath("(//button[@aria-label='Book this flight'])[1]");
    public static By airPortChange = By.xpath("//button[@class='btn ok-btn onHover refreshBtn primary_btn ng-star-inserted']");
    public static By flightPrice = By.xpath("(//div[@class='price_labl'])[1]");
    public static By flightPriceUnbundled = By.xpath("(//div[normalize-space()='Total Price'])[1]");
    public static By baggageInfo = By.xpath("(//div[@class='baggage_Info ng-star-inserted'])[1]");
    public static By depDateInput = By.xpath("//input[@aria-label='dept_date']");

    public static By totalFareBundled = By.xpath("//div[@class='fare']");

    public static By inboundFlightUnbundled = By.xpath("(//div[@class='row m-0 inBound return'])[1]");
    public static By outboundFlightUnbundled = By.xpath("(//div[@class='row m-0 outBound return'])[1]");

    public static By visaRequiredPopUp = By.xpath("//div[text()='ATTENTION GOOD LOOKING!']");
    public static By visaProceed = By.xpath("//button[@class='btn ok-btn onHover refreshBtn primary_btn ng-star-inserted']");

    public static By errorModel = By.xpath("//div[@class='err_page text-center']");

    public static By expandFlightDetails = By.xpath("(//a[@class='flight_details_link'])[1]");


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

}
