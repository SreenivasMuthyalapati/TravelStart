package pageObjects.B2B;

import org.openqa.selenium.By;

public class Dashboard {

    public static By dashboard = By.xpath("//div[@class='agent_dashboard d-flex']");
    public static By searchFlight = By.xpath("//span[@class='backtoFlights d-flex align-items-center ng-star-inserted']");
    public static By moreActions = By.xpath("(//img[@alt='more_actions'])[1]");
    public static By makePaymentAction = By.xpath("(//span[@class='float-left mr-2'])[2]");




}
