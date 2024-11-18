package pageObjects;

import org.openqa.selenium.By;

public class BookingSummaryPage {

    public static By travellerDetailsBlock = By.xpath("(//section[@class='travellers_details ng-star-inserted'])[1]");

    public static By selectesSeats = By.xpath("//div[@class='seat_Info mr-2 ng-star-inserted']//p[@class='mb-0'][2]");

    public static By invoiceLineTable = By.xpath("//table[@aria-describedby='fares']");

}
