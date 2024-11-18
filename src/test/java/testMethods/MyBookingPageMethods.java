package testMethods;

import org.openqa.selenium.WebDriver;
import pageObjects.BookingSummaryPage;
import pageObjects.MyBookings;

public class MyBookingPageMethods {

    Method method = new Method();

    public boolean viewItineraryDetails(WebDriver driver, String bookingRef){

        boolean isRequiredBookingAvailable = false;

        String position = MyBookings.getBookingPosition(driver, bookingRef);

        if (position == "0" || position == null){

            isRequiredBookingAvailable = false;

        } else {

            driver.findElement(MyBookings.viewItineraryButton(position)).click();

        }

        return isRequiredBookingAvailable;
    }

    
}
