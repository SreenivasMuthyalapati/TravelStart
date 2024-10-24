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

    public boolean validateSelectedSeatsInBookingConfirmationPage(List<String> seatsSelectedWhileSeatSelection, List<String> seatsDisplayedOnBookingConfirmation) {

        // If the size of the two lists is different, return false immediately
        if (seatsSelectedWhileSeatSelection.size() != seatsDisplayedOnBookingConfirmation.size()) {
            return false;
        }

        // Check if all seats in both lists are the same
        for (String seatSelected : seatsSelectedWhileSeatSelection) {
            if (!seatsDisplayedOnBookingConfirmation.contains(seatSelected)) {
                return false;  // Return false if any seat is not found in the booking confirmation list
            }
        }

        // If all seats match, return true
        return true;
    }

    public List<String> getAllSelectedSeats(WebDriver driver){

        List<WebElement> displayedSeatsElements = new ArrayList<>();
        List<String> displayedSeatsNumbers = new ArrayList<>();

        try{

            displayedSeatsElements = driver.findElements(BookingConfirmationPage.allSelectedSeats);

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

    public void expandInvoiceDetails(WebDriver driver) throws InterruptedException {

        WebElement expandInvoiceDetails = null;
        boolean isInvoiceCollapsed = false;

        try{
            expandInvoiceDetails = driver.findElement(BookingConfirmationPage.expandInvoice);
        }catch (Exception e){

        }

        try{

            isInvoiceCollapsed = expandInvoiceDetails.isDisplayed();

        }catch (NullPointerException e){

        }

        if (isInvoiceCollapsed){

            expandInvoiceDetails.click();
            Thread.sleep(200);

        }

    }

    public Map<String, String> getPricebreakDown (WebDriver driver) throws InterruptedException {

        this.expandInvoiceDetails(driver);

        List<WebElement> tables = driver.findElements(BookingConfirmationPage.priceBreakDownTable);

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

    public Map<String, String> getPriceBreakdown (WebDriver driver) throws InterruptedException {

        this.expandInvoiceDetails(driver);

        // Initialize a map to store the table data
        Map<String, String> tableData = new HashMap<>();

        // Find the table using XPath
        WebElement table = driver.findElement(BookingConfirmationPage.priceBreakDownTable);

        // Get all rows in the table
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        // Loop through each row
        for (WebElement row : rows) {
            // Get the columns (td) for each row
            List<WebElement> columns = row.findElements(By.tagName("td"));

            // Ensure there are at least 2 columns (key and value)
            if (columns.size() >= 2) {
                // Get the text from the first column (key) and second column (value)
                String key = columns.get(0).getText().trim();
                String value = columns.get(1).getText().trim();

                // Put the key-value pair into the map
                tableData.put(key, value);
            }
        }

        // Return the map containing the table data
        return tableData;
    }

    public String getTotalAmount(WebDriver driver){

        String totalCost = driver.findElement(PaymentPage.totoalPrice).getText();
        totalCost = m.removeSpecialCharacters(totalCost);

        return totalCost;
    }

    public boolean isSeatsPresentInInvoice(Map<String, String> map){

        String searchKey = "Seat";
        boolean isSeatsPresent = false;

        String value = m.retriveValueFromMap(map, searchKey);

        if (!(value == null)){

            isSeatsPresent = true;

        } else {

            isSeatsPresent = false;

        }


        return isSeatsPresent;
    }

    public int getSeatsPriceFromInvoiceBreakdown(Map<String, String> map){

        boolean isSeatsPresentInInvoice = this.isSeatsPresentInInvoice(map);
        String value;
        int seatsCost = 0;

        if (isSeatsPresentInInvoice) {

            String searchKey = "Seat";

            value = m.retriveValueFromMap(map, searchKey);

            seatsCost = m.stringToInt(value);

        }

        return seatsCost;
    }

    public boolean isWhatsappPresentInInvoice(Map<String, String> map){

        String searchKey = "WhatsApp";
        boolean isWhatsappPresentInInvoice = false;

        String value = m.retriveValueFromMap(map, searchKey);

        if (!(value == null)){

            isWhatsappPresentInInvoice = true;

        } else {

            isWhatsappPresentInInvoice = false;

        }


        return isWhatsappPresentInInvoice;
    }

    public int getWhatsappPrice(Map<String, String> map){

        boolean isWhatsappPresentInInvoice = this.isWhatsappPresentInInvoice(map);
        String value;
        int whatsApp = 0;

        if (isWhatsappPresentInInvoice) {

            String searchKey = "WhatsApp";

            value = m.retriveValueFromMap(map, searchKey);
            whatsApp = m.stringToInt(value);

        }

        return whatsApp;
    }



    public int getAdultPrice(Map<String, String> map){

        String value;
        int adultCost = 0;


            String searchKey = "Adult";

            value = m.retriveValueFromMap(map, searchKey);


        adultCost = m.stringToInt(value);

        return adultCost;
    }


    public boolean isMealsPresentInInvoice(Map<String, String> map){

        String searchKey = "WhatsApp";
        boolean isMealsPresentInInvoice = false;

        String value = m.retriveValueFromMap(map, searchKey);

        if (!(value == null)){

            isMealsPresentInInvoice = true;

        } else {

            isMealsPresentInInvoice = false;

        }


        return isMealsPresentInInvoice;
    }

    public int geMealsPrice(Map<String, String> map){

        boolean isMealsPresentInInvoice = this.isMealsPresentInInvoice(map);
        String value;
        int mealsPrice = 0;

        if (isMealsPresentInInvoice) {

            String searchKey = "Meal";

            value = m.retriveValueFromMap(map, searchKey);
            mealsPrice = m.stringToInt(value);

        }

        return mealsPrice;
    }

}
