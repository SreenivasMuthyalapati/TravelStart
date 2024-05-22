package pageObjects;

import org.openqa.selenium.By;

public class ContactUsZA {
    public static By existingBooking = By.xpath("//a[@id='post-booking']");
    public static By newBooking = By.xpath("//a[@id='new-booking']");
    public static By name = By.xpath("(//input[@formcontrolname='name'])[1]");
    public static By surName = By.xpath("(//input[@formcontrolname='surName'])[2]");
    public static By email = By.xpath("(// input[@formcontrolname='email'])[2]");
    public static By contactNumber =By.xpath("(//input[@formcontrolname='contactNumber'])[1]");
    public static By categoryDropDown = By.xpath("(//select[@id='category'])[1]");
    public static By subCategotyDropDown = By.xpath("(//select[@id='subCategory'])[1]");
    public static By bookingReference = By.xpath("(//input[@formcontrolname='bookingReference'])[1]");
    public static By message = By.xpath("(//textarea[@formcontrolname='message'])[1]");
    public static By flySafair = By.xpath("//a[normalize-space()='Flysafair']");
    public static By LIFT = By.xpath("//a[normalize-space()='Lift']");
    public static By paxName = By.xpath("(//input[@attr.id='paxName'])[1]");
    public static By newDepartureDate = By.xpath("(//input[@attr.id='deptDate'])[1]");
    public static By newReturnDate = By.xpath("(//input[@attr.id='returnDate'])[1]");

    public static By selectMonth = By.xpath("(//select[@title='Select month'])[1]");
    public static By selectYear = By.xpath("(//select[@title='Select year'])[1]");
    public static By sendMessage = By.xpath("(//span[text()='Send Message'])");

}
