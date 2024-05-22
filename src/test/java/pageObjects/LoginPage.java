package pageObjects;

import org.openqa.selenium.By;

public class LoginPage {
    public static By username = By.xpath("//input[@formcontrolname='username']");
    public static By password = By.xpath("//input[@autocomplete='current-password']");
    public static By login = By.xpath("//button[@aria-label='Login']");

    public static By email = By.xpath("// input[@formcontrolname='email']");
    public static By signUPPassword = By.xpath("//input[@name='signUpPassword']");
    public static By firstName = By.xpath("//input[@name='firstName']");
    public static By lastName = By.xpath("//input[@name='surName']");
    public static By TandCCheckBox = By.xpath("//input[@name='agreeTerms']");
    public static By signUpButton = By.xpath("//button[@type='submit']//span[contains(text(),'Sign up')]");
    public static By signUpOption = By.xpath("(//a[normalize-space()='Signup'])[1]");

}
