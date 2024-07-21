package pageObjects.B2B;

import org.openqa.selenium.By;

public class LoginPage {

    public static By emailId = By.xpath("(//input[@placeholder='Email ID'])[1]");
    public static By password = By.xpath("(//input[@placeholder='Password'])[1]");
    public static By loginButton = By.xpath("(//button[@class='btn primary_CTA loginCTA'])[1]");

}
