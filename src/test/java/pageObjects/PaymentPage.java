package pageObjects;

import org.openqa.selenium.By;

public class PaymentPage {

    public static By EFT = By.xpath("//a[@id='eft-tab']");
    public static By nedBank = By.xpath("//a[@id='eft-tab']");
    public static By payNow = By.xpath("//span[@class='ng-star-inserted'][normalize-space()='Pay Now']");
    public static By reserve = By.xpath("//button[@aria-label='Pay Now']");


}
