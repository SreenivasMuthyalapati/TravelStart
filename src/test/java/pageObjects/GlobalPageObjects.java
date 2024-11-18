package pageObjects;

import org.openqa.selenium.By;

public class GlobalPageObjects {

    public static By myAccountIconNonLoggedIn = By.xpath("//img[@alt='my account']");
    public static By myAccountLoggedIn = By.xpath("// li[@class='nav-item username ng-star-inserted']");
    public static By manageBookings = By.xpath("//li[@class='nav-item ng-star-inserted']//span[@class='manage_lbl']");



}
