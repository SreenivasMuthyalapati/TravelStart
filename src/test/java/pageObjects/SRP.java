package pageObjects;

import org.openqa.selenium.By;

public class SRP {

    public static By filters = By.xpath("//*[text()='Show Filters']");
    public static By results = By.xpath("//*[@class='row flightsViewSection ng-star-inserted']");
    public static By book = By.cssSelector("button.btn.primary_btn.book_btn.onHover");

}
