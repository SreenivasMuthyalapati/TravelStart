package testmethods;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageObjects.SeatsPage;

public class SeatsPageMethods {

    Method method = new Method();

    public boolean verifySeatsDisplayed(WebDriver driver){

        boolean isSeatsDisplayed = false;

        isSeatsDisplayed = method.verifyRedirection(driver, SeatsPage.seatMap, "Seats");


        return isSeatsDisplayed;
    }


    public int getAvailableSegementsCount(WebDriver driver){

        int segmentsCount = 0;


        return segmentsCount;
    }


}
