package testmethods;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.PaymentPage;
import pageObjects.ThreeDSecurePage;

public class ThreeDSecureMethods {

    static Method m = new Method();

    // Verifies redirection to 3ds for Card Payments and returns "BOOLEAN" value
    public boolean assert3dsRedirectionForCard(WebDriver driver, WebDriverWait wait, String paymentMethod){

        boolean is3dsRedirected = false;

        if (paymentMethod.equalsIgnoreCase("cc/dc")){

            is3dsRedirected=m.verifyElementAvailability(ThreeDSecurePage.threeDSecureIFrame, "ThreeDSecure iframe");

        }

        return is3dsRedirected;
    }

    public boolean checkIfBookingFailed(WebDriver driver){

        boolean isBookingFailed = false;

        WebElement bookingFailPopUp = null;

        try{

            bookingFailPopUp = driver.findElement(PaymentPage.bookingFailPopUP);

        }catch (NoSuchElementException e){

        }

        try {
            if (bookingFailPopUp.isDisplayed()) {

                isBookingFailed = true;

            }
        }catch (NullPointerException e){

        }
        return isBookingFailed;
    }

    public String getBookingFailMessage(WebDriver driver, WebDriverWait wait){

        String failMessage = "";

        boolean isBookingFailed;

        isBookingFailed = m.verifyElementAvailability(PaymentPage.bookingFailPopUP, "Booking Fail Pop Up");

        if (isBookingFailed){

            failMessage = driver.findElement(PaymentPage.bookingFailMessage).getText();

        }

        return failMessage;
    }

    public void clickOKCTAOnFailPopUp(WebDriver driver) throws InterruptedException {

        Thread.sleep(500);
        driver.findElement(PaymentPage.bookingFailPopUpOKCTA).click();

    }

}
