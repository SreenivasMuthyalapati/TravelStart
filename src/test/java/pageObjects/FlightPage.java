package pageObjects;

import org.openqa.selenium.By;

public class FlightPage {

    public static By flightReviewPage = By.xpath("//*[@class='booking_view_page ng-star-inserted']");
    public static By mobileNo = By.id("mobileNumber");
    public static By email = By.id("email");
    public static By whatsApp = By.xpath("//*[@class='slider_switch']");

    //Traveller details inputs
    public static By mr = By.xpath("(//input[@formcontrolname='gender'])[1]");
    public static By ms = By.xpath("(//input[@formcontrolname='gender'])[2]");
    public static By mrs = By.xpath("(//input[@formcontrolname='gender'])[3]");
    public static By firstName = By.id("firstName0");
    public static By lastName = By.id("lastName0");
    public static By dayDOB = By.name("selectedDay");
    public static By monthDOB = By.xpath("//select[@formcontrolname='dobMonth']");
    public static By yearDOB = By.xpath("//select[@placeholder='year']");
    public static By meal = By.xpath("//*[@formcontrolname='mealSelection']");
    public static By vegetarianMeal = By.xpath("//*[text()='Vegetarian Meal']");
    public static By contnue = By.xpath("//button[@class='btn addons_continueBtn primary_btn onHover mt-3']");









}
