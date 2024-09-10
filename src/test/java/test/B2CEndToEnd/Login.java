package test.B2CEndToEnd;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import testmethods.NetworkTabReader;

public class Login {

    @Test
    public void Login() {
        // Instantiate SendEmail class
//        SendEmail emailSender = new SendEmail();
//
//        // Call the sendEmail method
//        emailSender.sendEmail();
        // Initialize ChromeDriver
        ChromeOptions options = new ChromeOptions();
        // Add options as needed, e.g., options.addArguments("--remote-debugging-port=9222");
        ChromeDriver driver = new ChromeDriver(options);
        // Initialize the NetworkTabReader
        NetworkTabReader networkReader = new NetworkTabReader((ChromeDriver) driver);
        networkReader.enableNetworkTracking();

        // Retrieve network logs if needed
        networkReader.getNetworkLogs();

        // Cleanup
        networkReader.stopNetworkTracking();
    }
}
