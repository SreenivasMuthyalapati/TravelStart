package pageObjects;

import org.openqa.selenium.By;

public class UpgradePaymentPage {
    public static By cardDetails = By.xpath("//body/app-root/div[@class='theme-wrapper']/div[@class='app-body']/div[@class='content']/app-ts-plus-payments[@class='ng-star-inserted']/div[@class='ts_plus_payments']/div[@class='ts_plus_bg']/div[@class='container']/div[@class='row']/div[@class='col-sm-12']/div[@class='ts_plus_payments']/div[@class='ts_plus_cards_pay mt-3']/div[@class='card']/div[@class='card-body']/div[@class='row']/div[2]");
    public static By cardNumber = By.xpath("//input[@name='card.number']");
    public static By cardHolder = By.xpath("//input[@name='card.holder']");
    public static By CVV = By.xpath("//input[@name='card.cvv']");
    public static By expiry = By.xpath("//input[@class='wpwl-control wpwl-control-expiry']");
    public static By payNow = By.xpath("//button[text()='Pay now']");
    public static By welcome = By.xpath("//div[@class='exp_ts_plus']");


}
