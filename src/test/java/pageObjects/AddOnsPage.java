package pageObjects;

import org.openqa.selenium.By;

public class AddOnsPage {
    public static By AddOns = By.xpath("//div[@class='add_ons_selection ng-star-inserted']");
    public static By SelectBtn = By.xpath("//*[@class='select_btns']");
    public static By editSeats = By.xpath("//button[@class='btn btn-lg edit_btn ng-star-inserted']//span//img");
    public static By contnue = By.xpath("//button[@class='btn primary_btn onHover']//span[contains(text(),'Continue')]");
    public static By checkBoxNG = By.xpath("//label[@class='mat-checkbox-layout']");
}
