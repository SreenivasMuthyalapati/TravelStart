package pageObjects.B2B;

import org.openqa.selenium.By;

public class WebPush {

    public static By webPushiFrame = By.xpath("//iframe[@id='webpush-onsite']");
    public static By allowNotification = By.xpath("//button[@id='allow']");
    public static By deyNotification = By.xpath("//button[@id='deny']");

}
