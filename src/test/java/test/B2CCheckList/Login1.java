package test.B2CCheckList;

import org.testng.annotations.Test;
import test.BrowserStack.BrowserStackBaseTest;

public class Login1 extends BrowserStackBaseTest {

    public void launchTest() throws InterruptedException {
        driver.navigate().to("https://preprod-flightsite.myota.travel/");
        Thread.sleep(5000);

    }

    @Test(priority = 1)
    public void launch() throws InterruptedException {
        launchTest();
    }

}
