package test.ContactUsForms;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.ContactUsZA;
import pageObjects.Paths;
import testmethods.Method;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ContactUsForms_ZA {

    static XSSFWorkbook workbook;
    static WebDriver driver;
    static Method m = new Method();
    static String dataPath = Paths.contactUsDataPath;
    static String browser;
    static String environment;
    static String outputExcel = Paths.excelOutputPath;
    static String baseURL;
    static String runTime;
    static String screenShotPath ="";
    static String bookingReferenceNumber;
    static String name;
    static String surname;
    static String contactNumber;
    static String message;
    static String email;
    static String paxName;
    static String newDepartureDate;
    static String newReturnDate;
    static String route;

    @BeforeClass
    public void launchBrowser() throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", Paths.chromeDriver);
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get(m.readDataFromExcel(dataPath,"ContactUsForms_ZA", 0,1));
        Thread.sleep(3000);
    }

    public static void selectDateFromCalendar(WebElement calendarElement, String date){

        String ddmmyyy []= date.split("/");

        String newDate = ddmmyyy[0];
        String newMonth = ddmmyyy[1];
        String newYear = ddmmyyy[2];

        calendarElement.click();

        Select yearSelect = new Select(driver.findElement(ContactUsZA.selectYear));

        yearSelect.selectByValue(newYear);

        Select monthSelect = new Select(driver.findElement(ContactUsZA.selectMonth));

        monthSelect.selectByIndex(Integer.parseInt(newMonth));

        driver.findElement(By.xpath("//div[text()='"+newDate+"']")).click();



    }

    @Test(description = "Name change Request - Directly with the airline")
    public void ContactUsZA_01() throws InterruptedException {

        driver.findElement(ContactUsZA.existingBooking).click();

        Select categorySelect = new Select(driver.findElement(ContactUsZA.categoryDropDown));

        categorySelect.selectByIndex(1);

        Select subCategorySelect = new Select(driver.findElement(ContactUsZA.subCategotyDropDown));

        subCategorySelect.selectByIndex(1);

        String mainWindowHandle = null;
        try {
            // Get the handle of the main window
            mainWindowHandle = driver.getWindowHandle();

            // Click on the FlySafair link
            driver.findElement(ContactUsZA.flySafair).click();

            // Get all window handles
            Set<String> windowHandles = driver.getWindowHandles();

            // Switch to the new window
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(mainWindowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            String newWindowURL = driver.getCurrentUrl();

            String expectedURL = "https://www.flysafair.co.za/";

            // Assert things on the new window
            Assert.assertEquals(newWindowURL, expectedURL, "The new window didn't navigate to the expected URL");

            driver.close();

        } catch (NoSuchElementException e) {

            System.out.println("Flysafair link was not available");

        } finally {

            // switch back to the main window:
            driver.switchTo().window(mainWindowHandle);
        }

        try {
            // Get the handle of the main window
            mainWindowHandle = driver.getWindowHandle();

            // Click on the LIFT link
            driver.findElement(ContactUsZA.LIFT).click();

            // Get all window handles
            Set<String> windowHandles = driver.getWindowHandles();

            // Switch to the new window
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(mainWindowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            String newWindowURL = driver.getCurrentUrl();

            String expectedURL = "https://www.lift.co.za/en";

            // Assert things on the new window
            Assert.assertEquals(newWindowURL, expectedURL, "The new window didn't navigate to the expected URL");

            driver.close();

        } catch (NoSuchElementException e) {

            System.out.println("LIFT link was not available");

        } finally {

            // switch back to the main window:
            driver.switchTo().window(mainWindowHandle);
        }


        }

    @Test(description = "Name change Request - Date change request with flexi ticket")
    public void ContactUsZA_02() throws InterruptedException, IOException {

        driver.findElement(ContactUsZA.existingBooking).click();

        Select categorySelect = new Select(driver.findElement(ContactUsZA.categoryDropDown));

        categorySelect.selectByIndex(1);

        Select subCategorySelect = new Select(driver.findElement(ContactUsZA.subCategotyDropDown));

        subCategorySelect.selectByIndex(2);

        bookingReferenceNumber = m.readDataFromExcel(dataPath,"ContactUsForms_ZA", 4,3);

        driver.findElement(ContactUsZA.bookingReference).sendKeys(bookingReferenceNumber);

        name = m.readDataFromExcel(dataPath,"ContactUsForms_ZA", 4,4);

        driver.findElement(ContactUsZA.name).sendKeys(name);

        surname = m.readDataFromExcel(dataPath,"ContactUsForms_ZA", 4,5);

        driver.findElement(ContactUsZA.surName).sendKeys(surname);

        contactNumber = m.readDataFromExcel(dataPath,"ContactUsForms_ZA", 4,6);

        driver.findElement(ContactUsZA.contactNumber).sendKeys(contactNumber);

        email = m.readDataFromExcel(dataPath,"ContactUsForms_ZA", 4,7);

        driver.findElement(ContactUsZA.email).sendKeys(email);

        paxName = m.readDataFromExcel(dataPath,"ContactUsForms_ZA", 4,8);

        driver.findElement(ContactUsZA.paxName).sendKeys(paxName);

        newDepartureDate = m.readDataFromExcel(dataPath,"ContactUsForms_ZA", 4,9);

        WebElement newDepartureDateElement = driver.findElement(ContactUsZA.newDepartureDate);

        selectDateFromCalendar(newDepartureDateElement, newDepartureDate);

        message = m.readDataFromExcel(dataPath,"ContactUsForms_ZA", 4,12);

        driver.findElement(ContactUsZA.message).sendKeys(message);

        driver.findElement(ContactUsZA.sendMessage).click();


    }
}
