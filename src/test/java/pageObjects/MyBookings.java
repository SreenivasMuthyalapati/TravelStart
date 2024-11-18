package pageObjects;

import org.bouncycastle.est.LimitedSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class MyBookings {

    public static By allBookings = By.xpath("(//span[normalize-space()='All'])[1]");
    public static By upcomingBookings = By.xpath("(//span[normalize-space()='Upcoming'])[1]");
    public static By pastBookings = By.xpath("(//span[normalize-space()='Past'])[1]");
    public static By cancelledBookings = By.xpath("(//span[normalize-space()='Cancelled'])[1]");
    public static By suspendedBookings = By.xpath("(//span[normalize-space()='Suspended'])[1]");

    public static By bookingsDataTable = By.xpath("(//ngx-datatable[@class='ngx-datatable material fixed-header fixed-row virtualized'])[1]");
    public static By bookingIDs = By.xpath("(//span[@class='bk_id'])");
    public static By BookingID(String bookingPosision){

        By bookingID = By.xpath("(//span[@class='bk_id'])["+bookingPosision+"]");

        return bookingID;
    }

    public static String getBookingPosition(WebDriver driver, String bookingReference) {

        List<WebElement> allBookingIDsElements = driver.findElements(MyBookings.bookingIDs);
        String position = null;

        for (int i = 0; i<allBookingIDsElements.size(); i++){

            if (allBookingIDsElements.get(i).getText().contains(bookingReference)){

                position = String.valueOf(i+1);
                break;
            }

        }

        return position;
    }
    public static By bookingStatusesInList(String bookingPosition) {


        return By.xpath("((//div[@class='datatable-row-center datatable-row-group ng-star-inserted'])["+bookingPosition+"]//div[@class='datatable-body-cell-label'])[3]");


    }

    public static By viewItineraryButton(String bookingPosition){

        return By.xpath("((//div[@class='datatable-row-center datatable-row-group ng-star-inserted'])["+bookingPosition+"]//a[@class='q_recordLink'])[2]");

    }



}
