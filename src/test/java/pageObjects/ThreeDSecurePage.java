package pageObjects;

import org.openqa.selenium.By;

public class ThreeDSecurePage {

    public static By redirectGatewayHeader = By.xpath("//span[@class='redirect_head']");
    public static By threeDSecureIFrame = By.id("threeDSecureIFrame");
    public static By paymentFailPopUp = By.id("payment_failed_Modal");
    public static By OKCTAPaymentFail = By.xpath("(//button[@aria-label='Ok'])[1]");
    public static By bookingFailMessage = By.cssSelector("div[class='mt-3'] div strong");

}
