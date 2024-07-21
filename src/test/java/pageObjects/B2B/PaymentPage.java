package pageObjects.B2B;

import org.openqa.selenium.By;

public class PaymentPage {


    public static By payFromWallet = By.xpath("(//button[@class='btn pay_btn ml-lg-3 mt-3 mt-lg-0 ng-star-inserted'])[1]");
    public static By addMoneyToWallet = By.xpath("(//button[@class='btn add_money_btn ng-star-inserted'])[1]");
    public static By reserveOnly = By.xpath("(//button[@class='btn reserveNowCTA default_btn'])[1]");
    public static By EFTTab = By.xpath("//a[@id='eft-Tab']");
    public static By ccOrDcTab = By.xpath("//a[@id='credit-tab']");
    public static By nedBank = By.xpath("(//*[@alt='nedbank'])[2]");
    public static By cardNumber = By.xpath("//input[@title='Credit card number']");
    public static By cardHolderName = By.xpath("//input[@formcontrolname='cardName']");
    public static By cardExpiryMonth = By.xpath("//mat-select[@title='Select expiry month']");
    public static By cardExpiryYear = By.xpath("//*[@formcontrolname='cardExpiry']");
    public static By CVV = By.xpath("//input[@placeholder='123']");
    public static By payWithCard = By.xpath("//button[@class='btn pull-right primary_btn mt-3']");
    public static By paywithEFT = By.xpath("//button[@class='btn eftPaynowBtn primary_btn']");

}
