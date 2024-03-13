package pageObjects;

import org.openqa.selenium.By;
import testmethods.Method;

public class SRP {

    Method m = new Method();

    public static By filters = By.xpath("//*[text()='Show Filters']");
    public static By results = By.xpath("//*[@class='row flightsViewSection ng-star-inserted']");
    public static By book = By.xpath("//div[@class='label_Itin ng-star-inserted']//div[1]//app-flight-card[1]//div[1]//div[1]//div[1]//div[2]//div[1]//div[2]//button[1]");
    public static By domBook = By.xpath("(//button[@aria-label='Book this flight'])[1]");
    public static By airPortChange = By.xpath("//button[@class='btn ok-btn onHover refreshBtn primary_btn ng-star-inserted']");
    public static By unbundledOnwardRouteDetails = By.xpath("(//span[@class='route_info'])[1]");
    public static By unbundledReturnRouteDetails = By.xpath("(//span[@class='route_info'])[2]");
    public static By flightPrice = By.xpath("(//div[@class='price_labl'])[1]");
    public static By baggageInfo = By.xpath("(//div[@class='baggage_Info ng-star-inserted'])[1]");
    public static By depDateInput = By.xpath("//input[@aria-label='dept_date']");
    public static By flightNumberOneway = By.xpath("(//span[@class='airl_numb d-none d-lg-block ng-star-inserted'])[1]");
    public static By depTimeOnwardUnbundled = By.xpath("(//div[@class='row m-0 outBound return active']//div[@class='time'])[1]");
    public static By arrTimeOnwardUnbundled = By.xpath("(//div[@class='row m-0 outBound return active']//div[@class='time'])[2]");
    public static By depTimeReturnUnbundled = By.xpath("((//div[@class='row m-0 inBound return active']//div[@class='time'])[1]");
    public static By arrTimeReturnUnbundled = By.xpath("(//div[@class='row m-0 inBound return active']//div[@class='time'])[2]");
    public static By depTimeOnward = By.xpath("(//div[@class='time'])[1]");
    public static By arrTimeOnward = By.xpath("(//div[@class='time'])[2]");
    public static By depTimeReturn = By.xpath("(//div[@class='time'])[3]");
    public static By arrTimeReturn = By.xpath("(//div[@class='time'])[4]");
    public static By allAirlines = By.xpath("//span[@class='airl_name']");
    public static By totalFareBundled = By.xpath("//div[@class='fare']");



}
