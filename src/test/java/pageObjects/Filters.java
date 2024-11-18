package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import testMethods.Method;

public class Filters {

    private WebDriver driver;

    public Filters(WebDriver driver) {
        this.driver = driver;

    }

    Method m = new Method();
    public static By filters = By.xpath("//*[text()='Show Filters']");
    public static By nonStop = By.xpath("(//*[text()='Non Stop'])[2]");
    public static By apply = By.xpath("(//span[text()='Show Results'])[2]");
    public static By clear = By.xpath("(//span[text()='Reset All'])[2]");
    public static By close = By.xpath("(//span[@class='close_icn'])[2]");
    public static By returnAirline = By.xpath("(//button[@class='btn route_btns'])[2]");

    //Airline

    public static By airlineFilter(String value) {

        String xpath = String.format("(//input[@value='%s']/parent::div[@class='mat-checkbox-inner-container mat-checkbox-inner-container-no-side-margin'])[2]", value);
        By airlineSelect = By.xpath(xpath);

        return airlineSelect;
    }

    public static By airlineFilter(String value, boolean isElementNotInteractable) {

        By airlineSelect = null;
        
        if (isElementNotInteractable) {
            String xpath = String.format("(//input[@value='%s']/parent::div[@class='mat-checkbox-inner-container mat-checkbox-inner-container-no-side-margin'])[1]", value);
            airlineSelect = By.xpath(xpath);
        }

        return airlineSelect;
    }

    public static By closeFilters = By.xpath("(//span[@class='close_icn'])[2]");


}
