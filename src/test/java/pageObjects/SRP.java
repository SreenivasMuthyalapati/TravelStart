package pageObjects;

import org.openqa.selenium.By;

public class SRP {

    public static By filters = By.xpath("//*[text()='Show Filters']");
    public static By results = By.xpath("//*[@class='row flightsViewSection ng-star-inserted']");
    public static By book = By.xpath("//div[@class='label_Itin ng-star-inserted']//div[1]//app-flight-card[1]//div[1]//div[1]//div[1]//div[2]//div[1]//div[2]//button[1]");

    public static By airPortChange = By.xpath("//button[@class='btn ok-btn onHover refreshBtn primary_btn ng-star-inserted']");

}
