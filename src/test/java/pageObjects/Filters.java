package pageObjects;

import org.openqa.selenium.By;

public class Filters {
    public static By filters = By.xpath("//*[text()='Show Filters']");
    public static By nonStop = By.xpath("(//*[text()='Non Stop'])[2]");
    public static By apply = By.xpath("(//span[text()='Show Results'])[2]");
}
