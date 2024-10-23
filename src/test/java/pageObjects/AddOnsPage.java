package pageObjects;

import org.openqa.selenium.By;

public class AddOnsPage {
    public static By addOnsBlock = By.xpath("//div[@class='add_on_head']");
    public static By AddOns = By.xpath("//div[@class='add_ons_selection ng-star-inserted']");
    public static By SelectBtn = By.xpath("//*[@class='select_btns']");
    public static By selectedAddons = By.xpath("//*[@alt='tick_selected']");
    public static By editSeats = By.xpath("//button[@class='btn btn-lg edit_btn ng-star-inserted']//span//img");
    public static By contnue = By.xpath("//button[@class='btn primary_btn onHover']//span[contains(text(),'Continue')]");
    public static By checkBoxNG = By.xpath("(// label[@class='mat-checkbox-layout'])[1]");
    public static By noIWillRiskIt = By.xpath("//button[@class='mat-focus-indicator btn fare-btn mr-3 pl-4 pr-4 default_btn mat-button mat-button-base']");
    public static By addOnName = By.xpath("//div[@class='card-title mb-0']");
    public static By selectAddon = By.xpath("//span[@class='select_btns']");

    // Fare breakdown elements
    public static By fareBreakdownTables = By.xpath("//table[@aria-describedby='fares']");

    public static By priceTable(String value){

        String locator = "(//table[@aria-describedby='fares'])["+value+"]";

        By tableElement = By.xpath(locator);

        return tableElement;
    }

}
