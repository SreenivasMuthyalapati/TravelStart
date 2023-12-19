package pageObjects;

import org.openqa.selenium.By;

public class LoginPage {
    public static By username = By.xpath("//input[@formcontrolname='username']");
    public static By password = By.xpath("//input[@autocomplete='current-password']");
    public static By login = By.xpath("//button[@aria-label='Login']");

}
