package testmethods;

import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.FlightPage;

import java.io.IOException;

public class TravellerDetailsPageMethods {

    Method m = new Method();

    //Assert Travellerdetails page redirection
    public boolean isTravellerPageLoaded(WebDriver driver){

        boolean isTravellerPageLoaded = false;

        isTravellerPageLoaded = m.verifyRedirection(driver, FlightPage.flightReviewPage, "Traveller details Page");

        return isTravellerPageLoaded;

    }

    public boolean isFareIncreased(WebDriver driver, double increasedFare){

        boolean isFareIncreased = false;
        increasedFare = 0;
        String increasedFareInString = "";

        try{
            WebElement fareIncreasePopUp;
            fareIncreasePopUp = driver.findElement(FlightPage.fareIncreasePopUp);

            if (fareIncreasePopUp.isDisplayed()){

                driver.findElement(FlightPage.fareIncreaseContinue).click();
                isFareIncreased = true;
                increasedFare = m.amountToDouble(increasedFareInString);

            }



        }catch (NoSuchElementException e){

        }

        return isFareIncreased;
    }

    public double getFlightCost(WebDriver driver){

        double flightPrice = 0;
        String flightPriceInString = "";

        // Get Fare displayed
        try{

            flightPriceInString = driver.findElement(FlightPage.flightCost).getText();

        }catch (NoSuchElementException e){

        }

        flightPrice = m.amountToDouble(flightPriceInString);

        return flightPrice;
    }

    public double selectWhatsapp(WebDriver driver, boolean addWhatsapp){

        double whatsAppPrice = 0;
        String whatsAppPriceInSting = "";

        if (addWhatsapp){

            try {

                whatsAppPriceInSting = driver.findElement(FlightPage.whatsAppPrice).getText();
                whatsAppPrice = m.amountToDouble(whatsAppPriceInSting);


            } catch (NoSuchElementException | ElementNotInteractableException e){


            }
        }else if (!addWhatsapp){
            try {
                driver.findElement(FlightPage.whatsApp).click();
            }catch (NoSuchElementException| ElementNotInteractableException e){

            }
        }

        return whatsAppPrice;
    }

    public boolean verifyContactDetailsPreFill(WebDriver driver){

        boolean areContactDetailsPrefilled = false;

        String prefilledMobileNumber = driver.findElement(FlightPage.mobileNo).getAttribute("value");
        String prefilledMailID = driver.findElement(FlightPage.email).getAttribute("value");

        if (!prefilledMobileNumber.isEmpty() && !prefilledMailID.isEmpty()){

            areContactDetailsPrefilled = true;

        }

        return areContactDetailsPrefilled;
    }

    public void enterContactDetails(WebDriver driver, String mobileNumber, String mailID){

        // Sending mobile number
        driver.findElement(FlightPage.mobileNo).clear();
        driver.findElement(FlightPage.mobileNo).sendKeys(mobileNumber);

        // Sending mail ID
        driver.findElement(FlightPage.email).clear();
        driver.findElement(FlightPage.email).sendKeys(mailID);


    }

    public void enterPaxDetails(WebDriver driver, String tripType, String departureAirline, String returnAirline, String title, String firstName, String lastname, String dateOfBirth, String monthOfBirth, String yearOfBirth, String passPortNumber, String dateOfPassportExpiry, String monthOfPassportExpiry, String yearOfPassportExpiry, String adultCount, String youngAdultCount, String childCount, String infantCount) throws InterruptedException, IOException {

        // To check is the airline is flysafair for test surname
        boolean isFAFlight = false;

        // To check is the airline is flysafair for test surname
        if (tripType.equalsIgnoreCase("Oneway") && departureAirline.equalsIgnoreCase("FA")){

            isFAFlight = true;

        } else if (tripType.equalsIgnoreCase("Return") && departureAirline.equalsIgnoreCase("FA") && returnAirline.equalsIgnoreCase("FA")) {

            isFAFlight = true;

        }


        if (isFAFlight) {

            // Storing surname as "Test" if the airline is FlySafair
            lastname = "Test";

        }

        Thread.sleep(500);

        //Adding PAX details

        // Initializing a webelement to store primary pax title
        WebElement paxTitle = null;

        // Storing "Mr" title into webelement if test case has the title as Mr
        if (title.equalsIgnoreCase("mr")){

            paxTitle =  driver.findElement(FlightPage.mr);

        }

        // Storing "Ms" title into webelement if test case has the title as Ms
        else if (title.equalsIgnoreCase("ms")){

            paxTitle = driver.findElement(FlightPage.ms);

        }

        // Storing "Mrs" title into webelement if test case has the title as Mrs
        else if (title.equalsIgnoreCase("mrs")) {

            paxTitle = driver.findElement(FlightPage.mrs);

        }

        // Selecting passenger title
        paxTitle.click();

        // Sending First Name
        driver.findElement(FlightPage.firstName).sendKeys(firstName);

        // Sending Last Name
        driver.findElement(FlightPage.lastName).sendKeys(lastname);

        // Date of birth selection
        // Storing date of birth selections into webelements
        WebElement day = driver.findElement(FlightPage.dayDOB);
        WebElement month = driver.findElement(FlightPage.monthDOB);
        WebElement year = driver.findElement(FlightPage.yearDOB);


        // Selection of date of birth

        m.selectFromDropDown(driver, day, dateOfBirth);
        m.selectFromDropDown(driver, month, monthOfBirth);
        m.selectFromDropDown(driver, year, yearOfBirth);


        //Passport details
        WebElement passPortInfo = null;
        try {
            passPortInfo = driver.findElement(FlightPage.ppInfo);
            passPortInfo = driver.findElement(FlightPage.ppInfo);
        } catch (NoSuchElementException ne) {
            System.out.println("PassPort details not required for this flight");
        }
        try {
            if (passPortInfo.isDisplayed()) {
                WebElement passportNumber = driver.findElement(FlightPage.ppNumber);
                passportNumber.sendKeys(passPortNumber);

                WebElement passportExpiryday = driver.findElement(FlightPage.ppExpiryDate);
                WebElement passportExpirymonth = driver.findElement(FlightPage.ppExpiryMonth);
                WebElement passportExpiryyear = driver.findElement(FlightPage.ppExpiryYear);


                // Select passport expiry using click method

                m.selectFromDropDown(driver,passportExpiryday,dateOfPassportExpiry);
                m.selectFromDropDown(driver,passportExpirymonth, monthOfPassportExpiry);
                m.selectFromDropDown(driver,passportExpiryyear, yearOfPassportExpiry);

                driver.findElement(FlightPage.ppNationality).click();
                driver.findElement(FlightPage.ppnationalityIndia).click();

                Thread.sleep(1000);
                driver.findElement(FlightPage.ppIssuingCountry).click();
                driver.findElement(FlightPage.ppInsuingCountryIndia).click();

            }
        } catch (Exception e) {

        }

        // Entering remaining Pax details
        int adultCountTiInt = Integer.parseInt(adultCount);
        int youngAdultCountTiInt = Integer.parseInt(youngAdultCount);
        int childCountTiInt = Integer.parseInt(childCount);
        int infantCountTiInt = Integer.parseInt(infantCount);

        if (adultCountTiInt > 1 || youngAdultCountTiInt > 0 || childCountTiInt > 0 || infantCountTiInt > 0){

            m.paxSender(driver, adultCount, youngAdultCount, childCount, infantCount, departureAirline, returnAirline);

        }

    }

    public boolean isBaggageSelectionOffered(WebDriver driver){

        boolean isBaggageOffered = false;
        WebElement baggageSection = null;

        try{
            baggageSection = driver.findElement(FlightPage.baggageSection);
        }catch (NoSuchElementException e){

        }
        try {
            if (baggageSection.isDisplayed()) {
                isBaggageOffered = true;
            }
        }catch (NullPointerException e){

        }
        return isBaggageOffered;
    }


    public String getBaggageType(WebDriver driver, boolean isBaggageSelectionOffered){

        WebElement checkedBaggage = null;
        WebElement checkInBaggage = null;

        String baggageType = "";

        try{

            checkedBaggage = driver.findElement(FlightPage.addCheckedBaggage);

        }catch (NoSuchElementException|NullPointerException e){

        }

        try{

            checkInBaggage = driver.findElement(FlightPage.addCheckInBaggage);

        }catch (NoSuchElementException|NullPointerException e){

        }

        boolean isCheckedBaggageAvailable = false;
        boolean isCheckInBaggageAvailable = false;

        try {
            isCheckedBaggageAvailable = checkedBaggage.isDisplayed();
        }catch (NullPointerException e){

        }

        try {
            isCheckInBaggageAvailable = checkInBaggage.isDisplayed();
        }catch (NullPointerException e){

        }

        if (isCheckedBaggageAvailable){
            baggageType = "Checked";
        }else if (isCheckInBaggageAvailable){
            baggageType = "CheckIn";
        }
        return baggageType;
    }

}
