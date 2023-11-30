package pageObjects;

import org.openqa.selenium.By;

public class HomePage {
    public static By departureCity = By.xpath("//input[@id='dept_city0']");
    public static By arrivalCity = By.xpath("//input[@id='arr_city0']");
    public static By departureDate = By.xpath("//input[@id='dept_date0']");
    public static By returnDate = By.xpath("//input[@id='arr_date0']");
    public static By oneWay = By.xpath("/html/body/app-root/div[1]/div/div/app-home/div[1]/div[1]/div/app-search/section/div/div[1]/div/div[1]/ul/li[2]/label");
    public static By search = By.xpath("//*[@aria-label='Search Flights']");
    public static By option = By.xpath("//*[@class='mat-option-text']");
    public static By nextMonth = By.xpath("//*[@title='Next month']");
    public static By calender = By.xpath("//*[@class='dropdown-menu show ng-star-inserted']");
    public static By day = By.xpath("(//*[@class='ngb-dp-day ng-star-inserted'])[15]");
    public static By day2 = By.xpath("(//*[@class='ngb-dp-day ng-star-inserted'])[18]");

}
