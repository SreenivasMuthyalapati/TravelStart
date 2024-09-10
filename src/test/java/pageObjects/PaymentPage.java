package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PaymentPage {


    public static By paymentMethodsSection = By.xpath("//section[@class='payment_cards_section ng-star-inserted']");

    public static By bookingSummary = By.xpath("//h4[text()='Booking summary']");

    public static By paymentTabs = By.xpath("nav-item ng-star-inserted");

    public static By bookingFee = By.xpath("//span[@class='float-right feeAmount']");

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


    // Voucher
    public static By voucherField = By.xpath("(//input[@class='form-control voucher_color ng-pristine ng-invalid ng-touched'])[1]");

    public static By applyVoucher = By.xpath("(//button[@fdprocessedid='hqyxas'])[1]");

    public static By voucherValidationMessage = By.xpath("(//div[@class='validation_msg ng-star-inserted'])[1]");


    // Price details
    public static By totoalPrice = By.xpath("(//span[@class='total'])[1]");

    public static By fareBreakdownTables = By.xpath("//table[@aria-describedby='fares']");

    public static By priceTable(String value){

        String locator = "(//table[@aria-describedby='fares'])["+value+"]";

        By tableElement = By.xpath(locator);

        return tableElement;
    }


    // Budget Credit Card

    public static By bccTab = By.xpath("(//span[normalize-space()='Budget Credit Card'])[1]");

    public static By saveCards = By.xpath("(//div[@class='user_cards ng-star-inserted'])[1]");

    public static By selectSavedCard = By.xpath("(//img[@alt='selected'])[1]");

    public static By bookingFailPopUP = By.xpath("(//div[@class='modal-content dialog_content add_txt'])[1]");

    public static By bookingFailMessage = By.xpath("(//div[@class='mt-3'])[1]");

    public static By bookingFailPopUpOKCTA = By.xpath("(//button[@class='btn ok-btn primary_btn ng-star-inserted'])[1]");



}