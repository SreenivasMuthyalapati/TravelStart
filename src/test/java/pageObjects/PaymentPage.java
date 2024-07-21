package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PaymentPage {


    // Fare Breakdown
    public static By fareAndTaxesBreakdownTable = By.xpath("(//table[@class='table'])[1]");
    public static By fareBreakdownTable_1 = By.xpath("(//div[@class='add_ons ng-star-inserted']//table[@class='table'])[1]");
    public static By fareBreakdownTable_2 = By.xpath("(//div[@class='add_ons ng-star-inserted']//table[@class='table'])[2]");


    public static By bookingSummary = By.xpath("//h4[text()='Booking summary']");


    public static By EFT = By.xpath("//a[@id='eft-tab']");
    public static By nedBank = By.xpath("//*[@alt='nedbank']");
    public static By fnb = By.xpath("//*[@alt='fnb']");
    public static By standardBank = By.xpath("//*[@alt='standard-bank']");
    public static By absa = By.xpath("//*[@alt='absa']");


    //Card payment elements
    public static By credicCardOrDebitCard = By.xpath("//a[@id='credit-Card']");
    public static By cardNumber = By.xpath("//input[@title='Credit card number']");
    public static By cardHolderName = By.xpath("//input[@formcontrolname='cardName']");
    public static By cardExpiryMonth = By.xpath("//*[@formcontrolname='cardExpiryMonth']");
    public static By cardExpiryYear = By.xpath("//*[@formcontrolname='cardExpiry']");
    public static By CVV = By.xpath("//input[@placeholder='cvv']");
    public static By addressLine1 = By.xpath("//input[@placeholder='Address line 1']");
    public static By addressLine2 = By.xpath("//input[@placeholder='Address line 2']");
    public static By postalCode = By.xpath("//input[@placeholder='Postal code']");
    public static By city = By.xpath("//input[@placeholder='City']");
    public static By country = By.xpath("//*[@formcontrolname='country']");
    public static By contactNo = By.xpath("//input[@formcontrolname='contactNumber']");

    //Coutries in dropdown

    public static By countryIndia = By.xpath("//*[text()='India']");


    // Instant EFT
    public static By iPay = By.xpath("//a[@id='ipay-tab']");

    // NG EFT's

    public static By travelStart = By.xpath("//*[@alt='travelstart'][@class='bankLogo TsLogoCls' ]");
    public static By access = By.xpath("//*[@alt='access']");
    public static By UBA = By.xpath("//*[@alt='UBA']");
    public static By zenithBank = By.xpath("//*[@alt='zenith']");

    //Paystack

    public static By payStack = By.xpath("//a[@id='paystack-tab']");






    public static By payNow = By.xpath("//button[@aria-label='Pay Now']");
    public static By reserve = By.xpath("//button[@aria-label='Pay Now']");

    public static By bookingReference = By.cssSelector("div[class='refernce'] strong");


    public static By departureDate = By.xpath("(//label[@class='dt_labl'])[1]");

    public static By returnDate = By.xpath("(//label[@class='dt_labl'])[3]");

    public static By departureFlightNumber = By.xpath("(//label[@class='air_labl'])[1]");

    public static By returnFlightNumber = By.xpath("(//label[@class='air_labl'])[2]");
    



}
