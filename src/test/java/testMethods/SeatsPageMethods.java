package testMethods;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.SeatsPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SeatsPageMethods {

    private final Method method = new Method();
    private static final int DEFAULT_WAIT_TIME = 5;

    public void skipSeats(WebDriver driver, boolean seatsOffered) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(SeatsPage.skipsSeats));
            driver.findElement(SeatsPage.skipsSeats).click();
            driver.findElement(SeatsPage.continueInPopUp).click();
        } catch (TimeoutException e) {
            System.out.println("Failed to skip seats due to timeout.");
        } catch (NoSuchElementException ignored) {
        } catch (ElementNotInteractableException intr){
            try {
                driver.findElement(SeatsPage.continueInPopUp).click();
            } catch (ElementNotInteractableException e2){

            }
        }
    }

    public boolean verifySeatsDisplayed(WebDriver driver) throws InterruptedException {
        return method.verifyRedirection(driver, SeatsPage.seatMap, By.xpath("//div[@class='seat-map-drawer__error seat_Error_Sec ng-star-inserted']"));
    }

    public int getAvailableSegmentsCount(WebDriver driver) {
        try {
            return driver.findElements(SeatsPage.segmentTab).size();
        } catch (Exception e) {
            System.out.println("Failed to get available seats segments");
            return 0;
        }
    }

    public void switchPassenger(WebDriver driver, String paxNumber) throws InterruptedException {
        try {
            driver.findElement(SeatsPage.switchPassenger(paxNumber)).click();
            Thread.sleep(400);
        } catch (NoSuchElementException e) {
            System.out.println("Switching passenger failed for passenger number: " + paxNumber);
        }
    }

    public int getAvailablePaxTabs(WebDriver driver) {
        List<WebElement> paxInDom;
        List<WebElement> paxAvailable = new ArrayList<>();

        try {
            paxInDom = driver.findElements(SeatsPage.passengerTab);
            for (WebElement pax : paxInDom) {
                if (pax.isDisplayed()) {
                    paxAvailable.add(pax);
                }
            }
        } catch (Exception e) {
            System.out.println("Passenger seats count retrieval failed");
        }
        return paxAvailable.size();
    }

    public void clearAllSelectedSeatsForPax(WebDriver driver, String paxNumber) throws InterruptedException {
        try {
            driver.findElement(SeatsPage.clearAllSelectedSeatsForPax(paxNumber)).click();
            Thread.sleep(400);
        } catch (NoSuchElementException e) {
            System.out.println("Clearing selected seats for pax number " + paxNumber + " failed");
        }
    }

    public List<WebElement> getAllAvailableSeats(WebDriver driver) {
        try {
            return driver.findElements(SeatsPage.availableSeats);
        } catch (Exception e) {
            System.out.println("Failed to retrieve available seats.");
            return new ArrayList<>();
        }
    }

    public boolean switchToSegment(WebDriver driver, String segmentNumber) {
        int segmentIndex = Integer.parseInt(segmentNumber)-1;
        List<WebElement> availableSegments = driver.findElements(SeatsPage.segmentTab);

        if (segmentIndex < availableSegments.size()) {
            availableSegments.get(segmentIndex).click();
            return true;
        } else {
            System.out.println("Given segment number " + segmentNumber + " is not available.");
            return false;
        }
    }

    private void selectSeatsSegmentWise(WebDriver driver, String segmentNumber, String paxCountToHaveSeatsSelection) throws InterruptedException {


        int paxCount = 0;

        int availablePaxInSeatsMap = this.getAvailablePaxTabs(driver);

        if (paxCountToHaveSeatsSelection.equalsIgnoreCase("All")) {

            paxCount = availablePaxInSeatsMap;

        } else if (!paxCountToHaveSeatsSelection.equalsIgnoreCase("All") && Integer.parseInt(paxCountToHaveSeatsSelection) >= availablePaxInSeatsMap) {

            paxCount = availablePaxInSeatsMap;

        } else if (!paxCountToHaveSeatsSelection.equalsIgnoreCase("All") && Integer.parseInt(paxCountToHaveSeatsSelection) < availablePaxInSeatsMap) {

            paxCount = Integer.parseInt(paxCountToHaveSeatsSelection);

        } else if (paxCountToHaveSeatsSelection.equalsIgnoreCase("0") || paxCountToHaveSeatsSelection.isEmpty() || paxCountToHaveSeatsSelection.equalsIgnoreCase("-")) {

            paxCount = 0;
        }

        boolean SegmentSwitched = this.switchToSegment(driver, segmentNumber);

        if (SegmentSwitched && paxCount>0){
        for (int j = 1; j <= paxCount; j++) {

            // Switch to the current passenger
            driver.findElement(SeatsPage.switchPassenger(String.valueOf(j))).click();
            Thread.sleep(400); // Adjust wait time if needed

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
                            Thread.sleep(100);
                            availableSeats.get(seatIndex).click();
                            Thread.sleep(100);
                        } catch (ElementClickInterceptedException e) {

                        }

                        seatSelected = true; // Exit loop if click succeeds
                    } else {
                        System.out.println("No available seats found for passenger " + j);
                    }
                } catch (StaleElementReferenceException e) {
                    System.out.println("Encountered stale element, retrying...");
                    retries--; // Retry locating the seat
                    Thread.sleep(400); // Short wait before retrying
                }
            }
        }
        } else if (paxCount == 0) {


        }
    }





    public void selectSeats(WebDriver driver, boolean selectSeatsForAllPaxAndAllSegments) throws InterruptedException {

        int segmentsCount = this.getAvailableSegmentsCount(driver);
        int paxCount = this.getAvailablePaxTabs(driver);

        for (int i = 1; i <= segmentsCount; i++) {

            // Switch to the current segment
            driver.findElement(SeatsPage.switchSegment(String.valueOf(i))).click();
            Thread.sleep(400); // Adjust wait time if needed


            for (int j = 1; j <= paxCount; j++) {

                // Switch to the current passenger
                driver.findElement(SeatsPage.switchPassenger(String.valueOf(j))).click();
                Thread.sleep(400); // Adjust wait time if needed

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
                                Thread.sleep(100);
                                availableSeats.get(seatIndex).click();
                                Thread.sleep(100);
                            } catch (ElementClickInterceptedException e){

                            }

                            seatSelected = true; // Exit loop if click succeeds
                        } else {
                            System.out.println("No available seats found for passenger " + j);
                        }
                    } catch (StaleElementReferenceException e) {
                        System.out.println("Encountered stale element, retrying...");
                        retries--; // Retry locating the seat
                        Thread.sleep(400); // Short wait before retrying
                    }
                }

                if (!seatSelected) {
                    System.out.println("Failed to select seat for passenger " + j + " after retries.");
                }

                Thread.sleep(400); // Adjust wait time if needed
            }
        }
    }

    public void selectSeats(WebDriver driver, boolean selectSeatsForAllPaxAndAllSegments, String segment1SelectionCount, String segment2SelectionCount,String segment3SelectionCount,String segment4SelectionCount, String segment5SelectionCount, String segment6SelectionCount) throws InterruptedException {

        int segmentsCount = this.getAvailableSegmentsCount(driver);
        int paxCount = this.getAvailablePaxTabs(driver);

        String[] seatsPerSegment = {segment1SelectionCount, segment2SelectionCount, segment3SelectionCount, segment4SelectionCount, segment5SelectionCount, segment6SelectionCount};

        for (int i = 0; i < segmentsCount; i++) {

            String seatsCount = seatsPerSegment[i];
            int segmentNumber = i+1;
            String strValueOfSegment = String.valueOf(segmentNumber);
            this.selectSeatsSegmentWise(driver, strValueOfSegment, seatsCount);

        }
    }


    public List<String> getSelectedSeatNumbers(WebDriver driver) {
        List<String> seatNumbers = new ArrayList<>();

        try {
            List<WebElement> selectedSeats = driver.findElements(SeatsPage.selectedSeatNumbers);
            for (WebElement seat : selectedSeats) {
                seatNumbers.add(method.removeSpecialCharacters(seat.getText()));
            }
        } catch (Exception e) {
            System.out.println("Failed to retrieve selected seat numbers.");
        }

        return seatNumbers;
    }

    public int getTotalSeatsCost(WebDriver driver) {
        try {
            WebElement costElement = driver.findElement(SeatsPage.totalCostOfSeats);
            return method.stringToInt(costElement.getText());
        } catch (Exception e) {
            System.out.println("Failed to retrieve the total seats cost.");
            return 0;
        }
    }

    public void continueToNextStep(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        try {
            Thread.sleep(1000);
            driver.findElement(SeatsPage.continueToAddons).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(SeatsPage.continueInPopUp));
            driver.findElement(SeatsPage.continueInPopUp).click();
            Thread.sleep(100);
        } catch (Exception e){

        }
    }
}
