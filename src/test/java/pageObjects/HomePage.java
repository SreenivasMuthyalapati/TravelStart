package pageObjects;

import org.openqa.selenium.By;

public class HomePage {
    public static By departureCity = By.xpath("//input[@id='dept_city0']");
    public static By arrivalCity = By.xpath("//input[@id='arr_city0']");
    public static By departureDate = By.xpath("//input[@id='dept_date0']");
    public static By returnDate = By.xpath("//input[@id='arr_date0']");
    public static By search = By.xpath("//button[@aria-label='Search Flights']");
    public static By option = By.xpath("//*[@class='mat-option-text']");
    public static By nextMonth = By.xpath("//*[@title='Next month']");
    public static By calender = By.xpath("//*[@class='dropdown-menu show ng-star-inserted']");
    public static By day = By.xpath("(//*[@class='ngb-dp-day ng-star-inserted'])[15]");
    public static By day2 = By.xpath("(//*[@class='ngb-dp-day ng-star-inserted'])[18]");
    public static By denyNotification = By.xpath("//button[@id='deny']");
    public static By myAccount = By.xpath("//span[text()='My Account']");
    public static By profile = By.xpath("//button[@class='mat-focus-indicator mat-menu-trigger user_btn mat-button mat-button-base']");
    public static By myProfile = By.xpath("//button[@name='profile']");
    public static By logout = By.xpath("//button[@aria-label='Logout']");
    public static By oneWay = By.xpath("//label[normalize-space()='One-way']");
    public static By upgradeIcon = By.xpath("//div[@class='px-3']//a[@class='intro-banner-vdo-play-btn pinkBg']");
    public static By multiCity = By.xpath("//label[normalize-space()='Multi-city']");
    public static By mltCtyDepCity1 = By.xpath("//input[@id='dept_city0']");
    public static By mltCtyDepCity2 = By.xpath("//input[@id='dept_city1']");
    public static By mltCtyDepCity3 = By.xpath("//input[@id='dept_city2']");
    public static By mltCtyArrCity1 = By.xpath("//input[@id='arr_city0']");
    public static By mltCtyArrCity2 = By.xpath("//input[@id='arr_city1']");
    public static By mltCtyArrCity3 = By.xpath("//input[@id='arr_city2']");
    public static By addFlight = By.xpath("//button[@aria-label='Add another flight']");

}
