package pageObjects;

import org.openqa.selenium.By;

public class FlightPage {
    public static By airline1 = By.xpath("(//span[@class='air_name pl-1'])[1]");
    public static By airline2 = By.xpath("(//span[@class='air_name pl-1'])[2]");
    public static By airline3 = By.xpath("(//span[@class='air_name pl-1'])[3]");
    public static By flightReviewPage = By.xpath("//div[@class='col-sm-12 col-md-12 col-lg-9 p-0']");
    public static By mobileNo = By.id("mobileNumber");
    public static By email = By.id("email");
    public static By whatsApp = By.xpath("//*[@class='slider_switch']");
    public static By flightNumberOneway = By.xpath("//span[@class='flt_num d-lg-block']");
    public static By depCityOneway = By.xpath("(//label[@class='dept_city'])[2]");
    public static By arrCityOneway = By.xpath("(//label[@class='arr_city'])[2]");
    public static By fare = By.xpath("(//label[@class='amt'])[1]");

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

    //Passport details

    public static By ppInfo = By.xpath("//span[text()='Passport Information']");
    public static By ppNumber = By.xpath("//input[@id='passportNumber0']");
    public static By ppNationality = By.xpath("//div[@id='nationality0']");
    public static By ppIssuingCountry = By.xpath("//div[@id='passPortCountry0']");
    public static By ppExpiryDate = By.xpath("//select[@placeholder='day']");
    public static By ppExpiryMonth = By.xpath("//select[@formcontrolname='psExpMonth']");
    public static By ppExpiryYear = By.xpath("//select[@formcontrolname='psExpYear']");
    public static By departureDate = By.xpath("(//span[@class='date_Info'])[1]");
    public static By depTimeOnward = By.xpath("(//label[@class='time_labl'])[1]");
    public static By arrTimeOnward = By.xpath("(//label[@class='time_labl'])[2]");
    public static By depTimeReturn = By.xpath("(//label[@class='time_labl'])[3]");
    public static By arrTimeReturn = By.xpath("(//label[@class='time_labl'])[4]");
    public static By ppnationalityIndia = By.xpath("//*[text()='India']");
    public static By ppInsuingCountryIndia = By.xpath("(//*[text()='India'])[2]");



    public static By vegetarianMeal = By.xpath("//*[text()='Vegetarian Meal']");
    public static By contnue = By.xpath("//button[@class='btn addons_continueBtn primary_btn onHover mt-3']");









}
