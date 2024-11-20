package testClasses.B2CEndToEnd;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.BookingSummaryPage;
import testMethods.*;
import utils.TestReport;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static org.testng.Assert.assertNotNull;

public class CodeTestClass {

    static Method m = new Method();
    static SendEmail sendEmail = new SendEmail();
    static WebDriver driver;

    public static void main(String[] args) throws IOException, InterruptedException, AWTException {


        driver = m.launchBrowser(driver, "chrome");
        driver.manage().window().maximize();

        String url = m.getBaseURL("preprod", "ZA", "-");
        System.out.println(url);

        BookingConfirmationPageMethods bookingConfirmationPageMethods = new BookingConfirmationPageMethods();

        bookingConfirmationPageMethods.gotoMyBookings(driver, url);

        MyBookingPageMethods myBookingPageMethods = new MyBookingPageMethods();
        BookingSummaryPageMethods bookingSummaryPageMethods = new BookingSummaryPageMethods();


        myBookingPageMethods.viewItineraryDetails(driver, "ZA00110928");

        boolean isBookingDetailsLoaded = bookingSummaryPageMethods.verifyBookingDetailsLoaded(driver);

        List<String> seatsNumbersOnBookingsSummary = bookingSummaryPageMethods.getSelectedSeats(driver);

        System.out.println(seatsNumbersOnBookingsSummary);


        int seatsPriceInBreakDownViewItinerary = bookingSummaryPageMethods.getSeatsTotalCost(driver);

        System.out.println(bookingSummaryPageMethods.getBreakDownAsMap(driver, BookingSummaryPage.invoiceLineTable));
        System.out.println(seatsPriceInBreakDownViewItinerary);







    }


    }


