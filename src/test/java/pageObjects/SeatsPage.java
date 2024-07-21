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

    public static By seatSelectionBlock = By.xpath("(//h4[@class='seat-map-drawer__header-text d-inline seat_header'])[1]");
    public static By seatMap = By.xpath("(//div[@id='seatmap'])[1]");
    public static By selectedSegment = By.xpath("//div[@class='segmentListItems mr-2 ng-star-inserted selected']");
    public static By unselectedSegment = By.xpath("//div[@class='segmentListItems mr-2 ng-star-inserted unselected']");
    public static By availableSeat = By.xpath("(//div[contains(@class, 'smp-free')])[2]");
    public static By continueToAddons = By.xpath("(//button[@class='btn primary_btn ml-auto st_btn addons_continueBtn float-right onHover'])[1]");
    public static By totalCostOfSeats = By.xpath("//span[@class='mr-3 ng-star-inserted']");

    public static By continueInPopUp = By.xpath("(//button[@class='mat-focus-indicator btn proceedBtn primary_btn onHover mat-button mat-button-base'])[1]");
    public static By areYouSurePopUp = By.xpath("(//h5[contains(text(),'Are you sure?')])[1]");


    public WebElement switchSegment(String segmentNumber){

        String segmentElementXpath = "(//span[@class='paxSeat'])["+segmentNumber+"]";

        //return segmentElement;
        return driver.findElement(By.xpath(segmentElementXpath));
    }
}
