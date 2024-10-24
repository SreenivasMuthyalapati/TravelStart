package pageObjects;

import org.openqa.selenium.By;

public class BookingConfirmationPage {

    public static By refNumber = By.cssSelector("label[class='ref_details'] span:nth-child(2)");
    public static By isBookingConfirmed = By.xpath("//img[@alt='bookingConfirmed']");
    public static By mailID = By.xpath("(//strong[@class='booking_details_send'])[1]");
    public static By origin = By.xpath("(//span[@class='city_labls'])[1]");
    public static By destination = By.xpath("(//span[@class='city_labls ng-star-inserted'])[1]");

    public static By flightNumbers = By.xpath("//span[@class='airl_numb d-lg-block']");


    public static By allSelectedSeats = By.xpath("//div[contains(@class, 'seat_info')]//div[2]");
    public static By seatsSelected (String paxCount) {
        By seat = By.xpath("(//div[contains(@class, 'seat_info')]//div[2])["+paxCount+"]");

        return seat;
    }
    public static By expandInvoice = By.xpath("//section[@class='confirmation_Card invoiceDetailsCard_expanded']//i[@class='fa fa-angle-down']");
    public static By expandFlightDetails = By.xpath("//section[@class='confirmation_Card flightDetailsCard_expanded ng-star-inserted']//i[@class='fa fa-angle-down']");
    public static By expandPaymentDetails = By.xpath("//section[@class='confirmation_Card paymentDetailsCard_expanded ng-star-inserted']//i[@class='fa fa-angle-down']");
    public static By collapseInvoice = By.xpath("//section[@class='confirmation_Card invoiceDetailsCard_expanded']//i[@class='fa fa-angle-up']");
    public static By collapseFlightDetails = By.xpath("//section[@class='confirmation_Card flightDetailsCard_expanded ng-star-inserted']//i[@class='fa fa-angle-up']");
    public static By collapsePaymentDetails = By.xpath("//section[@class='confirmation_Card paymentDetailsCard_expanded ng-star-inserted']//i[@class='fa fa-angle-up']");

    //EFT related
    public static By bankDetailsSection = By.xpath("(//div[@class='bank_details'])[1]");
    public static By bankName = By.xpath("//section[@class='confirmation_Card paymentDetailsCard_expanded ng-star-inserted']//tr[1]//td[2]");
    public static By pendingAmount = By.xpath("//div[@class='price ng-star-inserted']//label[@class='price_labl ng-star-inserted']");

    //PAX details
    public static By allPaxNames = By.xpath("//div[@class='d-block d-lg-inline-flex pax_info']");
    public static By PaxName(String paxNumber){

        By paxName = By.xpath("(//div[@class='d-block d-lg-inline-flex pax_info'])["+paxNumber+"]");

        return paxName;
    }

    // Invoice
    public static By priceBreakDownTable = By.xpath("//div[contains(@class, 'fares')]//table");
    public static By totalAmount = By.xpath("(//label[@class='amt'])[1]");

}
