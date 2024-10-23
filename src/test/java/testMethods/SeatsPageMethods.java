package testMethods;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.SeatsPage;

import java.util.ArrayList;
import java.util.List;

public class SeatsPageMethods {

    Method method = new Method();

    public void skipSeats(WebDriver driver, boolean seatsOffered){

        WebDriverWait wait = new WebDriverWait(driver, 5);

        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(SeatsPage.skipsSeats));
            driver.findElement(SeatsPage.skipsSeats).click();

        }catch (TimeoutException te){

            System.out.println("Failed to skip seats");

        }catch (NoSuchElementException ne){

        }

    }

    public boolean verifySeatsDisplayed(WebDriver driver){

        boolean isSeatsDisplayed = false;

        isSeatsDisplayed = method.verifyRedirection(driver, SeatsPage.seatMap, "Seats");

        return isSeatsDisplayed;
    }


    public int getAvailableSegementsCount(WebDriver driver){

        int segmentsCount = 0;

        try{

            segmentsCount = driver.findElements(SeatsPage.segmentTab).size();

        } catch (Exception e) {

            System.out.println("Failed to get available seats segments");

        }


        return segmentsCount;
    }

    public void switchSegment(WebDriver driver, String segmentNumber) throws InterruptedException {

        String previousSegment = "";
        String currentSegment = "";

        try {
            previousSegment = driver.findElement(SeatsPage.currentSegment).getText();
            driver.findElement(SeatsPage.switchSegment(segmentNumber)).click();
            Thread.sleep(1000);
            currentSegment = driver.findElement(SeatsPage.currentSegment).getText();

            if (!previousSegment.isEmpty() && !currentSegment.isEmpty()){
            if (previousSegment.equalsIgnoreCase(currentSegment)){

                System.out.println("Switching segment failed");

            }}

        } catch (NoSuchElementException e){
            System.out.println("Switching segment failed");
        }

    }

    public void switchPassenger(WebDriver driver, String paxNumber) throws InterruptedException {


        try {

            driver.findElement(SeatsPage.switchPassenger(paxNumber)).click();
            Thread.sleep(1000);

        } catch (NoSuchElementException e){

            System.out.println("Switching passenger failed");

        }

    }

    public int getAvaiilablePaxTabs(WebDriver driver){

        int count = 0;

        List<WebElement> paxInDom = new ArrayList<>();
        List<WebElement> paxAvailable = new ArrayList<>();

        try{

            paxInDom = driver.findElements(SeatsPage.passengerTab);

        } catch (Exception e) {

            System.out.println("Passenger seats count retrival failed");

        }

        for (int i = 0; i < paxInDom.size(); i++){
            try{
            if (paxInDom.get(i).isDisplayed()){

                paxAvailable.add(paxInDom.get(i));

            }}catch (Exception e){

            }

        }

        count = paxAvailable.size();

        return count;
    }


    public void clearAllSelectedSeatsForPax(WebDriver driver, String paxNumber) throws InterruptedException {


        try {

            driver.findElement(SeatsPage.clearAllSelectedSeatsForPax(paxNumber)).click();
            Thread.sleep(1000);

        } catch (NoSuchElementException e){

            System.out.println("Clearing selected seats for pax number "+ paxNumber+ " is failed");

        }

    }

    public List<WebElement> getAllAvailableSeats(WebDriver driver){

        List<WebElement> availableSeats = new ArrayList<>();

        try{

            availableSeats = driver.findElements(SeatsPage.availableSeats);

        } catch (Exception e) {

        }

        return availableSeats;
    }

    public void selectSeats(WebDriver driver, boolean selectSeatsForAllPaxAndAllSegments) throws InterruptedException {

        int segmentsCount = this.getAvailableSegementsCount(driver);
        int paxCount = this.getAvaiilablePaxTabs(driver);

        for (int i = 1; i <= segmentsCount; i++) {

            // Switch to the current segment
            driver.findElement(SeatsPage.switchSegment(String.valueOf(i))).click();
            Thread.sleep(500); // Adjust wait time if needed


            for (int j = 1; j <= paxCount; j++) {

                // Switch to the current passenger
                driver.findElement(SeatsPage.switchPassenger(String.valueOf(j))).click();
                Thread.sleep(500); // Adjust wait time if needed
                driver.findElement(SeatsPage.switchSegment(String.valueOf(i))).click();
                Thread.sleep(500); // Adjust wait time if needed

                boolean seatSelected = false;
                int retries = 2; // Number of retries for stale element
                while (!seatSelected && retries > 0) {
                    try {
                        // Fetch available seats after switching passenger
                        List<WebElement> availableSeats = driver.findElements(SeatsPage.availableSeats);

                        // Check if there are enough available seats
                        if (availableSeats.size() > 0) {

                            // Select a seat based on the passenger index, using modulo to cycle through seats
                            int seatIndex = j;
                            try {
                                availableSeats.get(seatIndex).click();

                            } catch (ElementClickInterceptedException e){

                            }

                            seatSelected = true; // Exit loop if click succeeds
                        } else {
                            System.out.println("No available seats found for passenger " + j);
                        }
                    } catch (StaleElementReferenceException e) {
                        System.out.println("Encountered stale element, retrying...");
                        retries--; // Retry locating the seat
                        Thread.sleep(500); // Short wait before retrying
                    }
                }

                if (!seatSelected) {
                    System.out.println("Failed to select seat for passenger " + j + " after retries.");
                }

                Thread.sleep(500); // Adjust wait time if needed
            }
        }
    }

    public List<String> getSelectedSeatNumbers(WebDriver driver){

        List<WebElement> selectedSeatsElements = new ArrayList<>();
        List<String> selectedSeatsNumbers = new ArrayList<>();

        try{

            selectedSeatsElements = driver.findElements(SeatsPage.selectedSeatNumbers);

        } catch (NoSuchElementException | ElementNotInteractableException e){

        }

        for (int i = 0; i<selectedSeatsElements.size(); i++){

            try{
                String seatNumber = selectedSeatsElements.get(i).getText();
                method.removeSpecialCharacters(seatNumber);

                selectedSeatsNumbers.add(seatNumber);
            } catch (Exception e) {

            }

        }

        return selectedSeatsNumbers;
    }


    public double getTotalSeatsCost(WebDriver driver){

        double seatsCost = 0;
        WebElement costElement = null;

        try{
            costElement = driver.findElement(SeatsPage.totalCostOfSeats);

        }catch (NoSuchElementException e){

        }

        try {

            String seatsCostString = costElement.getText();
            seatsCost = method.stringToInteger(seatsCostString);

        } catch (NullPointerException e){

        }

        return seatsCost;
    }

    public void continueToNextStep(WebDriver driver) throws InterruptedException {

        try{
            driver.findElement(SeatsPage.continueToAddons).click();
        } catch (Exception e){
            e.printStackTrace();
        }
        Thread.sleep(200);

        try{
            driver.findElement(SeatsPage.continueInPopUp).click();
        } catch (Exception e) {


        }

    }





}
