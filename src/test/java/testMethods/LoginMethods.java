package testMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageObjects.GlobalPageObjects;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testClasses.B2CEndToEnd.Login;

import java.time.Duration;



public class LoginMethods {

    Method method = new Method();

    public boolean loginWithPassword(WebDriver driver, String username, String password) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.findElement(GlobalPageObjects.myAccountIconNonLoggedIn).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.loginOrSignUpForm));

        driver.findElement(LoginPage.loginWithPasswordButton).click();

        Thread.sleep(100);

        driver.findElement(LoginPage.username).sendKeys(username);
        Thread.sleep(200);
        driver.findElement(LoginPage.password).sendKeys(password);
        driver.findElement(LoginPage.rememberMeCheckBox).click();
        driver.findElement(LoginPage.login).click();

        boolean isLoginSuccess = method.verifyRedirection(driver, GlobalPageObjects.myAccountLoggedIn, LoginPage.errorMessage);

        Thread.sleep(300);

        return isLoginSuccess;
    }

}
