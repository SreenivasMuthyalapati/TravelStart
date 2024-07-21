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
    public static By segmentCount = By.xpath("segmentListItems mr-2 selected ng-star-inserted");

    public WebElement switchSegment(String segmentNumber){

        String segmentElementXpath = "//div[@id='segmentId11'])["+segmentNumber+"]";

        //return segmentElement;
        return driver.findElement(By.xpath(segmentElementXpath));
    }
}
