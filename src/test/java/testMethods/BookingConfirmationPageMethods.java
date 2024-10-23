package testMethods;

import org.apache.poi.hssf.record.PageBreakRecord;
import org.openqa.selenium.*;
import pageObjects.BookingConfirmationPage;
import pageObjects.FlightPage;
import pageObjects.PaymentPage;
import pageObjects.SeatsPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingConfirmationPageMethods {

    static Method m = new Method();

    public boolean verifyBookingConfirmationRedirect(WebDriver driver) throws InterruptedException {

        boolean isBookingConfirmationLoaded = false;

        isBookingConfirmationLoaded = m.verifyRedirection(driver, BookingConfirmationPage.isBookingConfirmed, PaymentPage.bookingFailPopUP);


        return isBookingConfirmationLoaded;
    }

    public String getBookingRefernece(WebDriver driver){

        String bookingRef = "";

        try {
            bookingRef = driver.findElement(BookingConfirmationPage.refNumber).getText();
        } catch (Exception e){
            System.out.println("Failed to get booking reference from confirmation page");
            e.printStackTrace();

        }
        return bookingRef;
    }

    public boolean validateSelectedSeatsInBookingConfirmationPage(List<String> seatsSelectedWhileSeatSelection, List<String> seatsDisplayedOnBookingConfirmation){

        List<String> seatsInSeatMap = new ArrayList<>();
        List<String> seatsInBookingConfirmation = new ArrayList<>();

        seatsInSeatMap = seatsSelectedWhileSeatSelection;
        seatsInBookingConfirmation = seatsDisplayedOnBookingConfirmation;

        boolean isSeatsMatching = false;

        boolean isCountMatching = false;

        if (seatsInSeatMap.size() == seatsInBookingConfirmation.size()){

            isCountMatching = true;

        } else {

            isCountMatching = false;
        }

        if (isCountMatching){


            for (int i = 0; i< seatsInSeatMap.size(); i++){

                String seatSelectedWhileInSeatMap = seatsInSeatMap.get(i);
                int seatsCountOnBookingConfirmation = seatsInBookingConfirmation.size();

                for (int j = 0; j< seatsCountOnBookingConfirmation; j++){

                    String seatOnBookingConfirmation = seatsInBookingConfirmation.get(j);

                    if (seatSelectedWhileInSeatMap == seatOnBookingConfirmation){

                        isSeatsMatching = true;
                        seatsInBookingConfirmation.remove(j);
                        break;

                    }else {

                        isSeatsMatching = false;

                    }

                }

            }


        }



        return isSeatsMatching;
    }

    public List<String> getAllSelectedSeats(WebDriver driver){

        List<WebElement> displayedSeatsElements = new ArrayList<>();
        List<String> displayedSeatsNumbers = new ArrayList<>();

        try{

            displayedSeatsElements = driver.findElements(SeatsPage.selectedSeatNumbers);

        } catch (NoSuchElementException | ElementNotInteractableException e){

        }

        for (int i = 0; i<displayedSeatsElements.size(); i++){

            try{
                String seatNumber = displayedSeatsElements.get(i).getText();
                m.removeSpecialCharacters(seatNumber);

                displayedSeatsNumbers.add(seatNumber);
            } catch (Exception e) {

            }

        }

        return displayedSeatsNumbers;
    }

    public Map<String, String> getBaseFareAndTaxesbreakDown (WebDriver driver){

        List<WebElement> tables = driver.findElements(BookingConfirmationPage.flightFareAndTaxTable);

        int priceBreakdownCategoriesCount = tables.size();

        Map<String, String> dataMap = new HashMap<>();

        for (int i =0; i < priceBreakdownCategoriesCount; i++){
            // Find all rows within the table

            WebElement table = driver.findElement(FlightPage.priceTable(String.valueOf(i+1)));

            List<WebElement> rows = table.findElements(By.tagName("tr"));

            // Iterate through each row
            for (WebElement row : rows) {
                // Find th and td elements within the row

                List<WebElement> cells = row.findElements(By.tagName("th"));
                cells.addAll(row.findElements(By.tagName("td")));

                // Extract text from th and td elements
                if (cells.size() == 2) {
                    // Assuming each row has exactly one th and one td
                    String key = cells.get(0).getText().trim();
                    String value = cells.get(1).getText().trim();

                    // Store in the map (if both key and value are not empty)
                    if (!key.isEmpty() && !value.isEmpty()) {
                        dataMap.put(key, value);
                    }
                }
            }
        }

        return dataMap;
    }

    public Map<String, String> getProductsCost (WebDriver driver){

        List<WebElement> tables = driver.findElements(BookingConfirmationPage.productsBreakdownTable);

        int priceBreakdownCategoriesCount = tables.size();

        Map<String, String> dataMap = new HashMap<>();

        for (int i =0; i < priceBreakdownCategoriesCount; i++){
            // Find all rows within the table

            WebElement table = driver.findElement(FlightPage.priceTable(String.valueOf(i+1)));

            List<WebElement> rows = table.findElements(By.tagName("tr"));

            // Iterate through each row
            for (WebElement row : rows) {
                // Find th and td elements within the row

                List<WebElement> cells = row.findElements(By.tagName("th"));
                cells.addAll(row.findElements(By.tagName("td")));

                // Extract text from th and td elements
                if (cells.size() == 2) {
                    // Assuming each row has exactly one th and one td
                    String key = cells.get(0).getText().trim();
                    String value = cells.get(1).getText().trim();

                    // Store in the map (if both key and value are not empty)
                    if (!key.isEmpty() && !value.isEmpty()) {
                        dataMap.put(key, value);
                    }
                }
            }
        }

        return dataMap;
    }

    public String getTotalAmount(WebDriver driver){

        String totalCost = driver.findElement(PaymentPage.totoalPrice).getText();
        totalCost = m.removeSpecialCharacters(totalCost);

        return totalCost;
    }

}
