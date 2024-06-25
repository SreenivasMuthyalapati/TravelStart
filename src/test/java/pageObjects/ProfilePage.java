package pageObjects;

import org.openqa.selenium.By;

public class ProfilePage {

    public static By travellersTab = By.xpath("//a[@id='three-tab']");


    public static By titleMr = By.xpath("//input[@value='Mr']");
    public static By titleMs = By.xpath("//input[@value='Ms']");
    public static By titleMrs = By.xpath("//input[@value='Mrs']");
    public static By firstName = By.xpath("//input[@class='form-control ng-pristine ng-invalid ng-touched']");
    public static By middleName = By.xpath("//input[@placeholder='Middle name']");
    public static By surName = By.xpath("//div[@class='col-md-3 mb-3 formColPad']//input[@placeholder='Surname']");
    public static By DOBDateSelect = By.xpath("//mat-select[@id='mat-select-18']");
    public static By DOBMonthSelect = By.xpath("//mat-select[@id='mat-select-20']");
    public static By DOBYearSelect = By.xpath("//div[@id='mat-select-value-23']");
    public static By passPortNumber = By.xpath("//input[@placeholder='Passport number']");
    public static By PassPortDateSelect = By.xpath("//div[@id='mat-select-value-25']");
    public static By PassportMonthSelect = By.xpath("//span[@class='mat-select-placeholder ng-tns-c75-22 ng-star-inserted']");
    public static By PassportYearSelect = By.xpath("//span[@class='mat-select-placeholder ng-tns-c75-23 ng-star-inserted']");
    public static By PPIssuingCountry = By.xpath("//mat-select[@id='mat-select-30']");
    public static By PPIssuingNationality = By.xpath("//mat-select[@id='mat-select-32']");
    public static By editPax = By.xpath("(//img[@class='show mr-2'])[1]");
    public static By deletePax = By.xpath("(//img[@class='show'])[1]");
    //public static By










}
