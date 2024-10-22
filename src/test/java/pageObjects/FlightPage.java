package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import testmethods.Method;

public class FlightPage {

    private WebDriver driver;
    public Method m = new Method();

    public FlightPage(WebDriver driver) {
        this.driver = driver;


    }
    public static By airline1 = By.xpath("(//span[@class='air_name pl-1'])[1]");
    public static By airline2 = By.xpath("(//span[@class='air_name pl-1'])[2]");
    public static By airline3 = By.xpath("(//span[@class='air_name pl-1'])[3]");
    public static By flightReviewPage = By.xpath("//div[@class='col-sm-12 col-md-12 col-lg-9 p-0']");
    public static By mobileNo = By.id("mobileNumber");
    public static By email = By.id("email");
    public static By whatsApp = By.xpath("//*[@class='slider_switch']");
    public static By flightNumberOneway = By.xpath("//span[@class='flt_num d-lg-block']");
    public static By depCityOneway = By.xpath("(//label[@class='dept_city'])[2]");
    public static By arrCityOneway = By.xpath("(//label[@class='arr_city'])[2]");
    public static By fare = By.xpath("(//label[@class='amt'])[1]");

    //Traveller details inputs


    public static By mr = By.xpath("//div[@id='gender0']//div[1]//label[1]//input[1]");
    public static By ms = By.xpath("//div[@id='gender0']//div[2]//label[1]//input[1]");
    public static By mrs = By.xpath("//div[@id='gender0']//div[3]//label[1]//input[1]");

    public void titleSelector(int value, String title) {

        value = value+1;
        String paxTitleValue = String.valueOf(value);

        WebElement paxTitle = null;
        if (title.equalsIgnoreCase("Mr")){
        String mr = String.format("//div[@id='gender%s']//div[1]//label[1]//input[1]", paxTitleValue);
        paxTitle = driver.findElement(By.xpath(mr));
        paxTitle.click();
        } else if (title.equalsIgnoreCase("Ms")) {
            String mr = String.format("//div[@id='gender%s']//div[2]//label[1]//input[1]", paxTitleValue);
            paxTitle = driver.findElement(By.xpath(mr));
            paxTitle.click();
        }else if (title.equalsIgnoreCase("Mrs")) {
            String mr = String.format("//div[@id='gender%s']//div[3]//label[1]//input[1]", paxTitleValue);
            paxTitle = driver.findElement(By.xpath(mr));
            paxTitle.click();
        }
    }

    public static By firstName = By.id("firstName0");

    public void inputFirstName(int value, String firstName){
        value = value+1;
        String firstNameValue = String.valueOf(value);
        String firstNameID = String.format("firstName%s", firstNameValue);
        WebElement paxFirstName = driver.findElement(By.id(firstNameID));
        paxFirstName.sendKeys(firstName);
    }

    public static By lastName = By.id("lastName0");

    public void inputLastName(int value, String lastName){
        value = value+1;
        String lastNameValue = String.valueOf(value);
        String lastNameID = String.format("lastName%s", lastNameValue);
        WebElement paxLastName = driver.findElement(By.id(lastNameID));
        paxLastName.sendKeys(lastName);
    }

    public static By dayDOB = By.xpath("(//*[@name='selectedDay'][@formcontrolname='dobDay'])[1]");

    // Select date of birth
    public void selectDateOfBirth(int value, String date) throws InterruptedException {
        value = value+1;
        String DOBValue = String.valueOf(value);
        String DOBXpath = String.format("(//*[@name='selectedDay'][@formcontrolname='dobDay'])[%s]", DOBValue);
        WebElement DOBElement = driver.findElement(By.xpath(DOBXpath));
//        Select DOBSelect = new Select(DOBElement);
//        DOBSelect.selectByIndex(Integer.parseInt(date));
        m.selectFromDropDown(driver, DOBElement, date);

    }
    public static By monthDOB = By.xpath("(//*[@formcontrolname='dobMonth'])[1]");

    // Select date of birth
    public void selectMonthOfBirth(int value, String month) throws InterruptedException {
        value = value+1;
        String MOBValue = String.valueOf(value);
        String MOBXpath = String.format("(//*[@formcontrolname='dobMonth'])[%s]", MOBValue);
        WebElement MOBElement = driver.findElement(By.xpath(MOBXpath));
//        Select MOBSelect = new Select(MOBElement);
//        MOBSelect.selectByIndex(Integer.parseInt(month));
        m.selectFromDropDown(driver, MOBElement, month);

    }
    public static By yearDOB = By.xpath("(//*[@placeholder='year'][@formcontrolname='dobYear'])[1]");

    // Select year of birth
    public void selectYearOfBirth(int value, String year) throws InterruptedException {
        value = value+1;
        String YOBValue = String.valueOf(value);
        String YOBXpath = String.format("(//*[@placeholder='year'][@formcontrolname='dobYear'])[%s]", YOBValue);
        WebElement YOBElement = driver.findElement(By.xpath(YOBXpath));
//        Select YOBSelect = new Select(YOBElement);
//        YOBSelect.selectByValue(year);

        m.selectFromDropDown(driver, YOBElement, year);

    }

    //Passport details

    public static By ppInfo = By.xpath("(//span[text()='Passport Information'])[1]");
    public static By ppNumber = By.xpath("//input[@id='passportNumber0']");

    // Enter passport number
    public void enterPassportNumber(int value, String ppNumber){
        value = value+1;
        String ppNumberVlue = String.valueOf(value);
        String ppNumberXpath = String.format("//input[@id='passportNumber%s']", ppNumberVlue);
        WebElement ppNumberElement = driver.findElement(By.xpath(ppNumberXpath));
        ppNumberElement.sendKeys(ppNumber);
    }
    public static By ppNationality = By.xpath("//div[@id='nationality0']");
    // Enter passport number
    public void selectppNationality(int value, String ppNationality) throws InterruptedException {
        value = value+1;
        String ppNationalityVlue = String.valueOf(value);
        String ppNationalityXpath = String.format("//div[@id='nationality%s']", ppNationalityVlue);
        WebElement ppNationalityElement = driver.findElement(By.xpath(ppNationalityXpath));
        ppNationalityElement.click();
        Thread.sleep(300);

        if (value == 2){
            value = value+1;
        }
        else if (value == 3){
            value = value+2;
        }
        else if (value == 4){
            value = value+3;
        }else if (value == 5){
            value = value+4;
        }else if (value == 6){
            value = value+5;
        }else if (value == 7){
            value = value+6;
        }else if (value == 8){
            value = value+7;
        }else if (value == 9){
            value = value+8;
        }

        String ppNationalityIndiaXpath = String.format("(//*[text()='India'])[%s]", value+2);
        WebElement ppNationalityIndiaElement = driver.findElement(By.xpath(ppNationalityIndiaXpath));
        ppNationalityIndiaElement.click();
    }

    public static By ppIssuingCountry = By.xpath("//div[@id='passPortCountry0']");

    public void selectppIssuingCountry(int value, String ppCountry) throws InterruptedException {
        value = value+1;
        String ppCountryVlue = String.valueOf(value);
        String ppCountryXpath = String.format("//div[@id='passPortCountry%s']", ppCountryVlue);
        WebElement ppCountryElement = driver.findElement(By.xpath(ppCountryXpath));
        ppCountryElement.click();
        Thread.sleep(300);

        if (value == 2){
            value = value+1;
        }
        else if (value == 3){
            value = value+2;
        }
        else if (value == 4){
            value = value+3;
        }else if (value == 5){
            value = value+4;
        }else if (value == 6){
            value = value+5;
        }else if (value == 7){
            value = value+6;
        }else if (value == 8){
            value = value+7;
        }else if (value == 9){
            value = value+8;
        }
        String ppNationalityIndiaXpath = String.format("(//*[text()='India'])[%s]", value+3);
        WebElement ppNationalityIndiaElement = driver.findElement(By.xpath(ppNationalityIndiaXpath));
        ppNationalityIndiaElement.click();
    }


    public static By ppExpiryDate = By.xpath("(//*[@placeholder='day'][@formcontrolname='psExpDay'])[1]");

    // Select passport expiry date
    public void selectDateOfppExpiry(int value, String date) throws InterruptedException {
        value = value+1;
        String ppDateValue = String.valueOf(value);
        String ppDateXpath = String.format("(//*[@placeholder='day'][@formcontrolname='psExpDay'])[%s]", ppDateValue);
        WebElement ppDateElement = driver.findElement(By.xpath(ppDateXpath));
//        Select ppDateSelect = new Select(ppDateElement);
//        ppDateSelect.selectByIndex(Integer.parseInt(date));

        m.selectFromDropDown(driver, ppDateElement, date);

    }
    public static By ppExpiryMonth = By.xpath("(//*[@formcontrolname='psExpMonth'])[1]");

    // Select date of birth
    public void selectMonthOfppExpiry(int value, String month) throws InterruptedException {
        value = value+1;
        String ppMonthValue = String.valueOf(value);
        String ppMonthXpath = String.format("(//*[@formcontrolname='psExpMonth'])[%s]", ppMonthValue);
        WebElement ppMothElement = driver.findElement(By.xpath(ppMonthXpath));
//        Select ppMonthSelect = new Select(ppMothElement);
//        ppMonthSelect.selectByIndex(Integer.parseInt(month));

        m.selectFromDropDown(driver, ppMothElement, month);

    }
    public static By ppExpiryYear = By.xpath("(//*[@formcontrolname='psExpYear'])[1]");

    // Select year of birth
    public void selectYearppExpiry(int value, String year) throws InterruptedException {
        value = value+1;
        String ppYearValue = String.valueOf(value);
        String ppYearXpath = String.format("(//*[@formcontrolname='psExpYear'])[%s]", ppYearValue);
        WebElement ppYearElement = driver.findElement(By.xpath(ppYearXpath));
//        Select ppYearSelect = new Select(ppYearElement);
//        ppYearSelect.selectByValue(year);

        m.selectFromDropDown(driver, ppYearElement, year);

    }
    public static By ppnationalityIndia = By.xpath("(//*[text()='India'])[1]");
    public static By ppInsuingCountryIndia = By.xpath("(//*[text()='India'])[2]");



    public static By flightNumber1 = By.xpath("(//span[@class='flt_num d-lg-block'])[1]");
    public static By flightNumber2 = By.xpath("(//span[@class='flt_num d-lg-block'])[2]");

    public static By departureDate = By.xpath("(//label[@class='dept_city'])[1]");
    public static By returnDate = By.xpath("(//label[@class='dept_city'])[3]");

    public static By departureCity = By.xpath("(//label[@class='dept_city'])[2]");
    public static By arrivalCity = By.xpath("(//label[@class='arr_city'])[2]");



    // Meals

    public static By mealDropDown(String value){

        By dropDownLocator = By.xpath("(//input[@formcontrolname='mealSelection'])["+value+"]");

        return dropDownLocator;
    }

    public static By mealOption = By.xpath("(//div[@class='mat_option_names'])[2]");

    public static By contnue = By.xpath("(//button[@class='btn addons_continueBtn primary_btn onHover mt-3'])[1]");


    public static By fareIncreaseContinue = By.xpath("//button[@aria-label='Continue']");
    public static By fareIncreasePopUp = By.xpath("//div[@class='fare ng-star-inserted']");




    // Fare breakdown elements
    public static By fareBreakdownTables = By.xpath("//table[@aria-describedby='fares']");

    public static By priceTable(String value){

        String locator = "(//table[@aria-describedby='fares'])["+value+"]";

        By tableElement = By.xpath(locator);

        return tableElement;
    }


    public static By flightNotAvailablePopUp = By.xpath("(//div[@id='noFlightsModal'])[1]");

    public static By flightCost = By.xpath("(//span[@class='total mt-2'])[1]");

    public static By whatsAppPrice = By.xpath("(//span[@class='mr-1 whatsapp_Amount ng-star-inserted'])[1]");


    // Baggage Elements
    public static By baggageSection = By.xpath("(//div[@class='cat_colpse_two collapse_card mb-2 baggageCard_Expanded mb_100 ng-star-inserted'])[1]");

    public static By checkedBaggageSection = By.xpath("(//div[@class='checked_baggage mt-3'])[1]");

    public static By checkInBaggageSection = By.xpath("(//div[@class='check_In_baggage_sec ng-star-inserted'])[1]");

    public static By addCheckedBaggage = By.xpath("(//span[@class='select_baggage ml-auto d-inline-flex'])[1]");

    public static By addCheckInBaggage(String paxNumber){

        By addBaggageElement = By.xpath("(//label[@class='baggage_select mb-0'])["+paxNumber+"]");


        return addBaggageElement;

    }

    public static By checkInBaggageItinerary = By.xpath("//div[@class='baggageItin']");

    public static By checkedBaggageToastMessage = By.xpath("(//div[@class='toastMsg ng-star-inserted'])[1]");

    public static By checkInBaggageCost = By.xpath("//span[@class='float-right ng-star-inserted']");



    // Meals objects

    public static By selectedMealCost = By.xpath("(//span[@class='pull-right ng-star-inserted'])[1]");

    public static By seatsCollapsedCard = By.xpath("(//div[@class='cat_colpse_three collapse_card mb-2 seatCard_Expanded ng-star-inserted'])[1]");

}
