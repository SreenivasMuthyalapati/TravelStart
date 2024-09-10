package testmethods;

import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pageObjects.FlightPage;
import pageObjects.PaymentPage;
import pageObjects.ThreeDSecurePage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pageObjects.Paths.dataPath;

public class PaymentPageMethods {

    static Method m = new Method();

    // Asserting payment page loading or not returns "BOOLEAN"
    public boolean assertPaymentPage(WebDriverWait wait){

        By targetElementLocator = PaymentPage.paymentMethodsSection;

        String targetElementName = "Payment Page";

        boolean isPaymentPageLoaded = m.verifyElementAvailability(targetElementLocator, targetElementName);

        return isPaymentPageLoaded;

    }

    // Retrieves booking reference from payment page and returns "STRING"
    public String getBookingReference(WebDriver driver){

        String bookingReference = "";
        try {
            bookingReference = driver.findElement(PaymentPage.bookingReference).getText();
        } catch (NoSuchElementException e){

        }
        return bookingReference;

    }

    // Retrieves booking fee from payment page, removes currency, "," and any other charsand returns in "STRING"
    public double getBookingFee(WebDriver driver){

        String bookingFee = "";

        try {
            bookingFee = driver.findElement(PaymentPage.bookingFee).getText();
        }
        catch (Exception e){
            bookingFee = "0";
        }

        bookingFee = bookingFee.substring(1);

        double bookingFeeNew = m.amountToDouble(bookingFee);

        return bookingFeeNew;

    }

    // Applies voucher and returns voucher validation message in "STRING"
    public String applyVoucher(WebDriver driver, WebDriverWait wait, String voucher){

        boolean voucherValidationResult = false;
        String voucherValidationMessage = "";

        driver.findElement(PaymentPage.voucherField).clear();
        driver.findElement(PaymentPage.voucherField).sendKeys(voucher);

        driver.findElement(PaymentPage.applyVoucher).click();

        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(PaymentPage.voucherValidationMessage));
            voucherValidationMessage = driver.findElement(PaymentPage.voucherValidationMessage).getText();


        }catch (NoSuchElementException | TimeoutException e){

            System.out.println("Voucher validation not done or validation took more than expected time");
            voucherValidationResult = false;

        }

        Assert.assertTrue(voucherValidationResult, "Voucher application failed");

        return voucherValidationMessage;

    }

    // Retrieves total price, removes currency, "," and any other chars and returns in "STRING"
    public String getTotalPrice(WebDriver driver){

        String totalPrice = driver.findElement(PaymentPage.totoalPrice).getText();

        double totalPriceNew = m.amountToDouble(totalPrice);

        return totalPrice;

    }

    // Retrieves breakdown of price from payment page and returns in "MAP" (Keys have product name and values have amount)
    public Map<String, String> getPriceBreakdown(WebDriver driver, By tablesElement){

        List<WebElement> tables = driver.findElements(tablesElement);

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

    //  Retrieves available payment methods from payment page and returns "LIST" that has payment methods
    public List<String> getPaymentMethods(WebDriver driver){

        List<WebElement> paymentMethodElements = driver.findElements(PaymentPage.paymentTabs);
        List<String> paymentMethods = List.of();

        for (int i = 0; i < paymentMethods.size(); i++){

            paymentMethods.add((paymentMethodElements.get(i)).getText());

        }

        return paymentMethods;
    }

    // Selects desired payment method
    public void selectPaymentMethod(WebDriver driver, WebDriverWait wait, String domain, String paymentMethod){

        paymentMethod = paymentMethod.toUpperCase();

        WebElement paymentMethodElement = null;
        By paymentMethodLocator = null;

        if (domain.equalsIgnoreCase("ZA")){

            if (paymentMethod.equalsIgnoreCase("EFT")){

                paymentMethodElement = driver.findElement(PaymentPage.EFT);
                paymentMethodLocator = PaymentPage.EFT;

            }

            else if (paymentMethod.equalsIgnoreCase("cc/dc")) {

                paymentMethodElement = driver.findElement(PaymentPage.credicCardOrDebitCard);
                paymentMethodLocator = PaymentPage.credicCardOrDebitCard;

            }

            else if (paymentMethod.equalsIgnoreCase("IPAY")){

                paymentMethodElement = driver.findElement(PaymentPage.iPay);
                paymentMethodLocator = PaymentPage.iPay;

            }

            else if (paymentMethod.equalsIgnoreCase("BCC")){

                paymentMethodElement = driver.findElement(PaymentPage.bccTab);
                paymentMethodLocator = PaymentPage.bccTab;

            }


        } else if (domain.equalsIgnoreCase("NG")) {

            if (paymentMethod.equalsIgnoreCase("EFT")){

                paymentMethodElement = driver.findElement(PaymentPage.EFT);
                paymentMethodLocator = PaymentPage.EFT;

            }

            else if (paymentMethod.equalsIgnoreCase("PayStack")) {

                paymentMethodElement = driver.findElement(PaymentPage.payStack);
                paymentMethodLocator = PaymentPage.payStack;

            }

        } else if (domain.equalsIgnoreCase("B2B_NG")) {



        }

        else if (domain.equalsIgnoreCase("B2B_FS")) {

            if (paymentMethod.equalsIgnoreCase("Wallet")){

                paymentMethodElement = driver.findElement(pageObjects.B2B.PaymentPage.payFromWallet);
                paymentMethodLocator = pageObjects.B2B.PaymentPage.payFromWallet;

            }

            else if (paymentMethod.equalsIgnoreCase("Reserve")) {

                paymentMethodElement = driver.findElement(pageObjects.B2B.PaymentPage.reserveOnly);
                paymentMethodLocator = pageObjects.B2B.PaymentPage.reserveOnly;

            }

        }

        else if (domain.equalsIgnoreCase("B2B_CT")) {

            if (paymentMethod.equalsIgnoreCase("cc/dc")){

                paymentMethodElement = driver.findElement(pageObjects.B2B.PaymentPage.ccOrDcTab);
                paymentMethodLocator = pageObjects.B2B.PaymentPage.ccOrDcTab;

            }

            else if (paymentMethod.equalsIgnoreCase("Reserve")) {

                paymentMethodElement = driver.findElement(pageObjects.B2B.PaymentPage.EFTTab);
                paymentMethodLocator = pageObjects.B2B.PaymentPage.EFTTab;

            }

        }

        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(paymentMethodLocator));
            paymentMethodElement.click();


        }catch (NoSuchElementException | TimeoutException | NullPointerException e){

            System.out.println("Desired payment method not loaded");

        }

    }

    // Enters card details into payment fields
    public void enterCardDetails(WebDriver driver, String domain) throws IOException, InterruptedException {

        if (domain.equalsIgnoreCase("ZA")) {
            String cardNumber = m.readDataFromExcel(dataPath, "Card detals", 2, 1);
            String cardHolderName = m.readDataFromExcel(dataPath, "Card detals", 2, 2);
            String cardExpiryMonth = (m.readDataFromExcel(dataPath, "Card detals", 2, 3));
            String cardExpiryYear = m.readDataFromExcel(dataPath, "Card detals", 2, 4);
            String CVV = m.doubleToString(m.readDataFromExcel(dataPath, "Card detals", 2, 5));
            String AddressLine1 = m.readDataFromExcel(dataPath, "Card detals", 2, 6);
            String AddressLine2 = m.readDataFromExcel(dataPath, "Card detals", 2, 7);
            String PostalCode = m.doubleToString(m.readDataFromExcel(dataPath, "Card detals", 2, 8));
            String city = m.readDataFromExcel(dataPath, "Card detals", 2, 9);
            String country = m.readDataFromExcel(dataPath, "Card detals", 2, 10);
            String contactNumber = m.readDataFromExcel(dataPath, "Card detals", 2, 11);


            driver.findElement(PaymentPage.cardNumber).sendKeys(cardNumber);
            driver.findElement(PaymentPage.cardHolderName).sendKeys(cardHolderName);

            WebElement CardExpiryMonthElement = driver.findElement(PaymentPage.cardExpiryMonth);
            WebElement CardExpiryYearElement = driver.findElement(PaymentPage.cardExpiryYear);


            m.selectFromDropDown(driver, CardExpiryMonthElement, cardExpiryMonth);


            m.selectFromDropDown(driver, CardExpiryYearElement, cardExpiryYear);

            driver.findElement(PaymentPage.CVV).sendKeys(CVV);
        }

        else if (domain.equalsIgnoreCase("B2B_CT")) {

            String cardNumber = m.readDataFromExcel(dataPath, "Card detals", 2,1);
            String cardHolderName = m.readDataFromExcel(dataPath, "Card detals", 2,2);
            String cardExpiryMonth = (m.readDataFromExcel(dataPath, "Card detals", 2,3));
            String cardExpiryYear = m.readDataFromExcel(dataPath, "Card detals", 2,4);
            String CVV = m.doubleToString(m.readDataFromExcel(dataPath, "Card detals", 2,5));

            driver.findElement(pageObjects.B2B.PaymentPage.cardNumber).sendKeys(cardNumber);


            String enteredCardNumber = driver.findElement(pageObjects.B2B.PaymentPage.cardNumber).getAttribute("value");


            try {
                if (!enteredCardNumber.equalsIgnoreCase(cardNumber)) {

                    driver.findElement(pageObjects.B2B.PaymentPage.cardNumber).clear();

                    driver.findElement(pageObjects.B2B.PaymentPage.cardNumber).sendKeys(cardNumber);

                }
            } catch (NullPointerException _){

            }

            driver.findElement(pageObjects.B2B.PaymentPage.cardHolderName).sendKeys(cardHolderName);

            WebElement CardExpiryMonthElement = driver.findElement(pageObjects.B2B.PaymentPage.cardExpiryMonth);
            WebElement CardExpiryYearElement = driver.findElement(pageObjects.B2B.PaymentPage.cardExpiryYear);

            m.selectFromDropDown(driver, CardExpiryYearElement, cardExpiryYear);
            m.selectFromDropDown(driver, CardExpiryMonthElement, cardExpiryMonth);

            m.selectFromDropDown(driver, CardExpiryYearElement, cardExpiryYear);
            driver.findElement(pageObjects.B2B.PaymentPage.CVV).sendKeys(CVV);


        }

    }

    // Selects desired Bank for EFT Payment
    public void selectEFTBank(WebDriver driver, String domain, String bankName){

        if (domain.equalsIgnoreCase("ZA")){

            bankName = bankName.toUpperCase();

            switch (bankName) {


                //Banks
                case "NEDBANK" -> driver.findElement(PaymentPage.nedBank).click();
                case "FNB" -> driver.findElement(PaymentPage.fnb).click();
                case "STANDARD BANK" -> driver.findElement(PaymentPage.standardBank).click();
                case "ABSA" -> driver.findElement(PaymentPage.absa).click();


                default -> System.out.println("Invalid bank name");

            }

        }
        else if (domain.equalsIgnoreCase("NG")){

            bankName = bankName.toUpperCase();

            switch (bankName) {

                //Banks
                case "TAVELSTART" -> driver.findElement(PaymentPage.travelStart).click();
                case "ACCESS" -> driver.findElement(PaymentPage.access).click();
                case "UBA" -> driver.findElement(PaymentPage.UBA).click();
                case "ZENITH" -> driver.findElement(PaymentPage.zenithBank).click();


                default -> System.out.println("Invalid bank name");

            }

        }

        else if (domain.equalsIgnoreCase("B2B_CT")) {

            driver.findElement(pageObjects.B2B.PaymentPage.nedBank).click();

        }


    }

    // Enters payment details (Card details or EFT Bank) according to dynamic parameters (Method overloaded with parameter count, if there is no extra parameter (BankName) for card payment)
    public void enterPaymentDetails(WebDriver driver, String domain, String paymentMethod) throws IOException, InterruptedException {

        if (paymentMethod.equalsIgnoreCase("cc/dc")){

            this.enterCardDetails(driver, domain);

        }

    }

    // Enters payment details (Card details or EFT Bank) according to dynamic parameters (Method overloaded with parameter count, if there is extra parameter (BankName) for EFT payment)
    public void enterPaymentDetails(WebDriver driver, String domain, String paymentMethod, String bankName) throws IOException, InterruptedException {

        if (paymentMethod.equalsIgnoreCase("EFT")){

            this.selectEFTBank(driver, domain, bankName);

        } else if (paymentMethod.equalsIgnoreCase("cc/dc")){

            this.enterCardDetails(driver, domain);

    }}

    // Clicks Pay button according to dynamic parameters (domain and payment method)
    public void clickPay(WebDriver driver, String domain, String paymentMethod) throws InterruptedException {

        //Payment in ZA - EFT
        if ((domain.equalsIgnoreCase("ZA") || domain.equalsIgnoreCase("FS"))&& paymentMethod.equalsIgnoreCase("EFT")){

            driver.findElement(PaymentPage.payNow).click();

        }

        //Paying with card
        else if (paymentMethod.equalsIgnoreCase("cc/dc") && (domain.equalsIgnoreCase("ZA") || domain.equalsIgnoreCase("FS"))){

            driver.findElement(PaymentPage.payNow).click();


        }

        // Pay using iPay
        else if (domain.equalsIgnoreCase("ZA") && (paymentMethod.equalsIgnoreCase("Instant EFT") || paymentMethod.equalsIgnoreCase("IPAY"))) {

            driver.findElement(PaymentPage.iPay).click();


        }

        // Pay using NG EFT
        else if (domain.equalsIgnoreCase("NG") && paymentMethod.equalsIgnoreCase("EFT")) {

            driver.findElement(PaymentPage.reserve).click();


        }
        //Pay using Paystack
        else if (domain.equalsIgnoreCase("NG") && (paymentMethod.equalsIgnoreCase("Instant EFT")||paymentMethod.equalsIgnoreCase("Paystack"))) {

            driver.findElement(PaymentPage.payNow).click();

        }

        // B2B NG Wallet
        else if (domain.equalsIgnoreCase("B2B_NG") && paymentMethod.equalsIgnoreCase("Wallet")) {

            Thread.sleep(2000);

            driver.findElement(pageObjects.B2B.PaymentPage.payFromWallet).click();


        }

        // B2B FS Wallet

        else if (domain.equalsIgnoreCase("B2B_FS") && paymentMethod.equalsIgnoreCase("Wallet")) {

            driver.findElement(pageObjects.B2B.PaymentPage.payFromWallet).click();

        }

        // B2B FS Reserve only
        else if (domain.equalsIgnoreCase("B2B_FS") && paymentMethod.equalsIgnoreCase("Reserve")) {

            driver.findElement(pageObjects.B2B.PaymentPage.reserveOnly).click();

        }

        // B2B CT EFT
        else if (domain.equalsIgnoreCase("B2B_CT") && paymentMethod.equalsIgnoreCase("EFT")) {

            Thread.sleep(1000);
            driver.findElement(pageObjects.B2B.PaymentPage.paywithEFT).click();


        }

        // B2B CT Card
        else if (domain.equalsIgnoreCase("B2B_CT") && paymentMethod.equalsIgnoreCase("cc/dc")) {

            driver.findElement(pageObjects.B2B.PaymentPage.payWithCard).click();

        }

    }

    public boolean validateSavedCardPopulated(WebDriverWait wait){

        boolean isSavedCardPopulated = false;

        isSavedCardPopulated = m.verifyElementAvailability(PaymentPage.saveCards, "Saved Card(s)");

        return isSavedCardPopulated;
    }

    public boolean assertSavedCardDetailsPrefil(WebDriver driver) throws InterruptedException {

        boolean areCardFieldsFilled = false;

        this.selectSavedCard(driver, "");

        Thread.sleep(1000);

        String cardNumber = "";
        String cardHolderName = "";
        String expiryMonth = "";
        String expiryYear = "";

        cardNumber = driver.findElement(PaymentPage.cardNumber).getAttribute("value");
        cardHolderName = driver.findElement(PaymentPage.cardHolderName).getAttribute("value");
        expiryMonth = driver.findElement(PaymentPage.cardExpiryMonth).getText();
        expiryYear = driver.findElement(PaymentPage.cardExpiryYear).getText();

        if (!cardNumber.isEmpty()){

            areCardFieldsFilled = true;

        }else {
            System.out.println("Card Number field wasn't filled with saved card");
        }

        if (!cardHolderName.isEmpty()){

            areCardFieldsFilled = true;

        }else {
            System.out.println("Card holder name wasn't filled with saved card");
        }

        if (!expiryMonth.isEmpty()){

            areCardFieldsFilled = true;

        }else {
            System.out.println("Card expiry month wasn't selected with saved card");
        }

        if (!expiryYear.isEmpty()){

            areCardFieldsFilled = true;

        }else {
            System.out.println("Card expiry year wasn't selected with saved card");
        }


        return areCardFieldsFilled;
    }

    public void selectSavedCard(WebDriver driver, String CVV){

        driver.findElement(PaymentPage.selectSavedCard).click();

        driver.findElement(PaymentPage.CVV).sendKeys(CVV);

    }

    public boolean checkIfBookingFailed(WebDriver driver){

        boolean isBookingFailed = false;

        WebElement bookingFailPopUp = null;

        try{

            bookingFailPopUp = driver.findElement(PaymentPage.bookingFailPopUP);

        }catch (NoSuchElementException e){

        }

        try {
            if (bookingFailPopUp.isDisplayed()) {

                isBookingFailed = true;

            }
        }catch (NullPointerException e){

        }
        return isBookingFailed;
    }

    public String getBookingFailMessage(WebDriver driver, WebDriverWait wait){

        String failMessage = "";

        boolean isBookingFailed;

        isBookingFailed = m.verifyElementAvailability(PaymentPage.bookingFailPopUP, "Booking Fail Pop Up");

        if (isBookingFailed){

            failMessage = driver.findElement(PaymentPage.bookingFailMessage).getText();

        }

        return failMessage;
    }

    public void clickOKCTAOnFailPopUp(WebDriver driver) throws InterruptedException {

        Thread.sleep(500);
        driver.findElement(PaymentPage.bookingFailPopUpOKCTA).click();

    }



}
