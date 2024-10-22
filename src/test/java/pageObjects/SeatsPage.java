package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.HdrDocumentImpl;

public class SeatsPage {

    private WebDriver driver;

    public SeatsPage(WebDriver driver) {
        this.driver = driver;


    }

    public static By skipsSeats = By.xpath("//button[@class='btn btn-link st_btn st_btn_clear']");
    public static By seatSelectionBlock = By.xpath("(//h4[@class='seat-map-drawer__header-text d-inline seat_header'])[1]");
    public static By seatMap = By.xpath("(//div[@class='segmentSection mt-2 ng-star-inserted'])[1]");

    // Segment related
    public static By selectedSegment = By.xpath("//li[@class='seg-labl selected ng-star-inserted']");
    public static By segmentTab = By.xpath("//ul[@class='segmentsList segmentListItems mb-1']/li[contains(@class, 'seg-labl')]");
    public static By unselectedSegment = By.xpath("//div[@class='segmentListItems mr-2 ng-star-inserted unselected']");

    public static By switchSegment(String segmentNumber) {

        By segmentLocator = By.xpath("(//ul[@class='segmentsList segmentListItems mb-1']/li[contains(@class, 'seg-labl')])[" + segmentNumber + "]");

        return segmentLocator;
    }
    public static By currentSegment = By.xpath("(//span[@class='labl-high'])[1]");

    // Travellers related
    public static By selectedPassenger = By.xpath("//li[@class='labl-txt ng-star-inserted selected']");
    public static By passengerTab = By.xpath("//ul[@class='travelerList']/li[contains(@class, 'labl-txt')]");

    public static By switchPassenger(String passengerNumber) {

        By passengerLocator = By.xpath("(//ul[@class='travelerList']/li[contains(@class, 'labl-txt')])[" + passengerNumber + "]");

        return passengerLocator;

    }

    public static By clearAllSelectedSeatsForPax(String paxNumber) {

        By locator = By.xpath("(//button[@class='clear_btn'])[" + paxNumber + "]");

        return locator;
    }

    public static By availableSeats = By.xpath("//td[@class='seatmap-table-cell seat ng-star-inserted']/button[contains(@class, 'AVAILABLE')]");
    public static By selectedSeatNumbers = By.xpath("(//span[@class='seatNumber ng-star-inserted'])");

    public static By selectedSeatNumbersPaxWise(String paxNumber) {

        By locator = By.xpath("//ul[@class='travelerList']/li[contains(@class, 'labl-txt')][" + paxNumber + "]//span[@class='seatNumber ng-star-inserted']");

        return locator;
    }

    public static By continueToAddons = By.xpath("(//button[@class='btn ml-auto st_btn addons_continueBtn float-right onHover'])[1]");
    public static By totalCostOfSeats = By.xpath("//span[@class='mr-3 seat_price ng-star-inserted']");


    public static By seatsAlertPopUp = By.xpath("(//h5[contains(text(),'Are you sure?')])[1]");
    public static By chooseSeats_On_Alert_PopUp = By.xpath("(//button[@class='mat-focus-indicator btn fare-btn default_btn mr-2 pl-2 pr-2 mat-button mat-button-base'])[1]");
    public static By continueInPopUp = By.xpath("(//button[@class='mat-focus-indicator btn proceedBtn primary_btn onHover mat-button mat-button-base'])[1]");



}