package testMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.BookingSummaryPage;
import pageObjects.FlightPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingSummaryPageMethods {

    Method method = new Method();

    public boolean verifyBookingDetailsLoaded(WebDriver driver){

        boolean isBookingDetailsAppeared = method.verifyRedirection(driver, BookingSummaryPage.travellerDetailsBlock, "Booking Details");

        return isBookingDetailsAppeared;
    }

    public Map<String, String> getBreakDownAsMap(WebDriver driver, By tablesElement) {
        List<WebElement> tables = driver.findElements(tablesElement);

        int priceBreakdownCategoriesCount = tables.size();

        Map<String, String> dataMap = new HashMap<>();

        for (int i = 0; i < priceBreakdownCategoriesCount; i++) {
            // Find all rows within the table
            WebElement table = driver.findElement(FlightPage.priceTable(String.valueOf(i + 1)));

            List<WebElement> rows = table.findElements(By.tagName("tr"));

            // Iterate through each row
            for (WebElement row : rows) {
                // Find th and td elements within the row
                List<WebElement> cells = row.findElements(By.tagName("th"));
                cells.addAll(row.findElements(By.tagName("td")));

                // Extract text from th and td elements
                if (cells.size() >= 2) {
                    String key = cells.get(1).getText().trim();
                    String value = cells.get(2).getText().trim();

                    // Store in the map (if both key and value are not empty)
                    if (!key.isEmpty() && !value.isEmpty()) {
                        // If key already exists, sum the values
                        if (dataMap.containsKey(key)) {
                            String existingValue = dataMap.get(key);

                            // Remove 'R' and any commas from both values
                            double existingAmount = method.stringToInt(existingValue);
                            double newAmount = method.stringToInt(existingValue);

                            // Sum the amounts
                            double totalAmount = existingAmount + newAmount;

                            // Format back to original string format
                            dataMap.put(key, "R" + String.format("%,.0f", totalAmount));
                        } else {
                            dataMap.put(key, value);
                        }
                    }
                }
            }
        }

        return dataMap;
    }

    public int getSeatsServiceChargeFromInvoice(WebDriver driver){

        Map<String, String> invoiceLinesMap = this.getBreakDownAsMap(driver, BookingSummaryPage.invoiceLineTable);
        int seatsServiceCharge = method.stringToInt(this.retriveValueFromMap(invoiceLinesMap, "Service charges"));

        return seatsServiceCharge;
    }

    public int getSeatsBasePriceFromInvoice(WebDriver driver){

        Map<String, String> invoiceLinesMap = this.getBreakDownAsMap(driver, BookingSummaryPage.invoiceLineTable);
        int seatsBasePrice = method.stringToInt(this.retriveValueFromMap(invoiceLinesMap, "Seats"));

        return seatsBasePrice;
    }

    public int getSeatsTotalCost(WebDriver driver){

        int totalSeatsCost = this.getSeatsBasePriceFromInvoice(driver)+this.getSeatsServiceChargeFromInvoice(driver);

        return totalSeatsCost;

    }

    private String retriveValueFromMap(Map<String, String> map, String searchKey) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(searchKey)) {
                return entry.getValue();
            }
        }
        return null; // return null or a default value if no match is found
    }


    public List<String> getSelectedSeats(WebDriver driver){

        List<String> selecedSeatsNumbers = new ArrayList<>();
        List<WebElement> selecedSeatsNumbersElements = driver.findElements(BookingSummaryPage.selectesSeats);

        for (int i=0; i< selecedSeatsNumbersElements.size(); i++){

            selecedSeatsNumbers.add(selecedSeatsNumbersElements.get(i).getText());

        }

        return selecedSeatsNumbers;
    }

    public boolean validateSelectedSeatsInBookingSummary(List<String> seatsSelectedWhileSeatSelection, List<String> seatsDisplayedOnBookingSummary) {

        // If the size of the two lists is different, return false immediately
        if (seatsSelectedWhileSeatSelection.size() != seatsDisplayedOnBookingSummary.size()) {
            return false;
        }

        // Check if all seats in both lists are the same
        for (String seatSelected : seatsSelectedWhileSeatSelection) {
            if (!seatsDisplayedOnBookingSummary.contains(seatSelected)) {
                return false;  // Return false if any seat is not found in the booking confirmation list
            }
        }

        // If all seats match, return true
        return true;
    }


}
