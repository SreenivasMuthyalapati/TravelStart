package pageObjects;

import org.openqa.selenium.By;

public class AddOnsPage {
    public static By AddOns = By.xpath("//body[1]/app-root[1]/div[1]/div[1]/div[1]/app-booking-view[1]/div[1]/div[1]/div[1]/div[1]/div[4]/div[1]/div[1]/span[1]/h4[1]");
    public static By SelectBtn = By.xpath("//*[@class='select_btns']");
    public static By editSeats = By.xpath("//button[@class='btn btn-lg edit_btn ng-star-inserted']//span//img");
    public static By contnue = By.xpath("//button[@class='btn primary_btn onHover']//span[contains(text(),'Continue')]");

}
