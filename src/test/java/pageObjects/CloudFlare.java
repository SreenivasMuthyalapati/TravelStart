package pageObjects;

import org.openqa.selenium.By;

public class CloudFlare {

    public static By cloudFlareLogo = By.xpath("//div[@class='base_AccessLogo']");
    public static By email = By.xpath("//input[@type='email']");
    public static By sendCode = By.xpath("//button[@type='submit']");
    public static By codeInput = By.xpath("//input[@name='code']");
    public static By submitCode = By.xpath("//button[@type='submit']");


}
