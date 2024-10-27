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

            driver.findElement(SeatsPage.continueInPopUp).click();

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

    public boolean switchToSegment(WebDriver driver, String segmentNumber) throws InterruptedException {

        int availableSegemtsCount = this.getAvailableSegementsCount(driver);

        boolean segmentSwitched;

        List<WebElement> availableSegments = driver.findElements(SeatsPage.segmentTab);

        int requiredSegmentNumber = Integer.parseInt(segmentNumber);

        if (requiredSegmentNumber<=availableSegemtsCount){

            availableSegments.get(requiredSegmentNumber-1).click();
            Thread.sleep(200);
            segmentSwitched = true;

        }else {

            System.out.println("Given segment number "+ segmentNumber +" is not available in the seat map. Seat selection available for "+availableSegemtsCount+"segments only.");
            segmentSwitched = false;
        }


        return segmentSwitched;
    }

    // Selects all seats in a segment
    private void selectAllSeatsInSegment(WebDriver driver, String segmentNumber) throws InterruptedException {

        boolean isSegmentSwitched = this.switchToSegment(driver, segmentNumber);
        int availablePaxCount = this.getAvailableSegementsCount(driver);

        if (isSegmentSwitched){
            List<WebElement> availableSeatsInSegment = this.getAllAvailableSeats(driver);

            for (int i=0; i<availablePaxCount; i++){

                availableSeatsInSegment.get(i).click();
                Thread.sleep(100);
            }

        }

    }

    public void selectSeats(WebDriver driver, boolean selectAllSeats) throws InterruptedException {

        if (selectAllSeats){

            List<WebElement> availableSegments = driver.findElements(SeatsPage.segmentTab);
            int segmentCount = this.getAvailableSegementsCount(driver);

            for (int i=0; i< segmentCount; i++){

                this.switchToSegment(driver, String.valueOf(i+1));
                this.selectAllSeatsInSegment(driver, String.valueOf(i+1));

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
