package pageObjects;

import org.openqa.selenium.By;

public class BookingConfirmationPage {

    public static By refNumber = By.cssSelector("label[class='ref_details'] span:nth-child(2)");
    public static By isBookingConfirmed = By.xpath("//img[@alt='bookingConfirmed']");
}
