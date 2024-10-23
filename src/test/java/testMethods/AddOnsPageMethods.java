package testMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.AddOnsPage;
import pageObjects.FlightPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddOnsPageMethods {

    static Method method = new Method();

    public boolean isAddOnsLoaded(WebDriver driver, boolean isAddOnsOffered){

        boolean isAddOnsLoaded = false;

        if (isAddOnsOffered){

          isAddOnsLoaded = method.verifyRedirection(driver, AddOnsPage.AddOns, "Add Ons");


        }

        return isAddOnsLoaded;
    }

    public void unselectAllAddOns(WebDriver driver){

        try {

            List<WebElement> selectedAddons = driver.findElements(AddOnsPage.selectedAddons);
            int numberOfSelectedAddOns = selectedAddons.size();

            for (int i = 0; i < numberOfSelectedAddOns; ) {
                WebElement selectedAddon = selectedAddons.get(i);
                if (selectedAddon.isDisplayed()) {
                    selectedAddon.click();
                }
                // Re-find the list of selected addons after each interaction
                selectedAddons = driver.findElements(AddOnsPage.selectedAddons);
                int newNumberOfSelectedAddOns = selectedAddons.size();

                // Check if the number of selected addons has decreased
                if (newNumberOfSelectedAddOns < numberOfSelectedAddOns) {
                    numberOfSelectedAddOns = newNumberOfSelectedAddOns;
                    // Restart the loop as the indices may have shifted
                    continue;
                }

                numberOfSelectedAddOns = newNumberOfSelectedAddOns;
                i++; // Proceed to the next addon
            }
        } catch (Exception e) {

        }

    }

    public void selectFlexiProduct(WebDriver driver, boolean addFlexi){

        try {
            List<WebElement> availableAddons = driver.findElements(AddOnsPage.addOnName);
            List<WebElement> SelectAddons = driver.findElements(AddOnsPage.selectAddon);
            int numberOfAddOns = availableAddons.size();
            if (addFlexi) {
                for (int i = 0; i < numberOfAddOns; ) {
                    WebElement availableAddon = availableAddons.get(i);
                    WebElement SelectaddOn = SelectAddons.get(i);
                    if (availableAddon.getText().contains("Flexible Travel Dates") && SelectaddOn.isDisplayed()) {
                        SelectaddOn.click();
                    }
                    // Re-find the list of selected addons after each interaction
                    availableAddons = driver.findElements(AddOnsPage.addOnName);
                    int newNumberOfAddOns = availableAddons.size();

                    // Check if the number of selected addons has decreased
                    if (newNumberOfAddOns < numberOfAddOns) {
                        numberOfAddOns = newNumberOfAddOns;
                        // Restart the loop as the indices may have shifted
                        continue;
                    }

                    numberOfAddOns = newNumberOfAddOns;
                    i++; // Proceed to the next addon
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Flexi AddOn not available");
        }

    }

    public void selectMedicalCancellationProduct(WebDriver driver, boolean addMedicalCancellationProduct){

        try {
            List<WebElement> availableAddons = driver.findElements(AddOnsPage.addOnName);
            List<WebElement> SelectAddons = driver.findElements(AddOnsPage.selectAddon);
            int numberOfAddOns = availableAddons.size();
            if (addMedicalCancellationProduct) {
                for (int i = 0; i < numberOfAddOns; ) {
                    WebElement availableAddon = availableAddons.get(i);
                    WebElement SelectaddOn = SelectAddons.get(i);
                    if (availableAddon.getText().contains("Medical Cancellation Refund") && SelectaddOn.isDisplayed()) {
                        SelectaddOn.click();
                    }
                    // Re-find the list of selected addons after each interaction
                    availableAddons = driver.findElements(AddOnsPage.addOnName);
                    int newNumberOfAddOns = availableAddons.size();

                    // Check if the number of selected addons has decreased
                    if (newNumberOfAddOns < numberOfAddOns) {
                        numberOfAddOns = newNumberOfAddOns;
                        // Restart the loop as the indices may have shifted
                        continue;
                    }

                    numberOfAddOns = newNumberOfAddOns;
                    i++; // Proceed to the next addon
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Medical Cancellation Refund product is not available");
        }

    }

    public void selectAirlineLiquidation(WebDriver driver, boolean addAirlineLiquidationProduct){

        try {
            List<WebElement> availableAddons = driver.findElements(AddOnsPage.addOnName);
            List<WebElement> SelectAddons = driver.findElements(AddOnsPage.selectAddon);
            int numberOfAddOns = availableAddons.size();
            if (addAirlineLiquidationProduct) {
                for (int i = 0; i < numberOfAddOns; ) {
                    WebElement availableAddon = availableAddons.get(i);
                    WebElement SelectaddOn = SelectAddons.get(i);
                    if (availableAddon.getText().contains("Medical Cancellation Refund") && SelectaddOn.isDisplayed()) {
                        SelectaddOn.click();
                    }
                    // Re-find the list of selected addons after each interaction
                    availableAddons = driver.findElements(AddOnsPage.addOnName);
                    int newNumberOfAddOns = availableAddons.size();

                    // Check if the number of selected addons has decreased
                    if (newNumberOfAddOns < numberOfAddOns) {
                        numberOfAddOns = newNumberOfAddOns;
                        // Restart the loop as the indices may have shifted
                        continue;
                    }

                    numberOfAddOns = newNumberOfAddOns;
                    i++; // Proceed to the next addon
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Airline Liquidation Protection product is not available");
        }

    }

    // Retrieves breakdown of price from payment page and returns in "MAP" (Keys have product name and values have amount)
    public Map<String, String> getPriceBreakdown(WebDriver driver){

        List<WebElement> tables = driver.findElements(AddOnsPage.fareBreakdownTables);

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

    public void checkNGTandCCheckbox(WebDriver driver, String domain) throws InterruptedException {

        if (domain.equalsIgnoreCase("NG") || domain.equalsIgnoreCase("B2B_NG")) {

            try {
                Thread.sleep(1000);

                if (!driver.findElement(AddOnsPage.checkBoxNG).isSelected()) {
                    driver.findElement(AddOnsPage.checkBoxNG).click();
                }

            } catch (Exception e){

            }
        }

    }

    public void continueToNextStep(WebDriver driver) throws InterruptedException {

        driver.findElement(AddOnsPage.contnue).click();
        Thread.sleep(200);
        try {
            driver.findElement(AddOnsPage.noIWillRiskIt).click();
        } catch (Exception e){

        }

    }


}
