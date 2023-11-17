package test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;


import io.github.bonigarcia.wdm.WebDriverManager;

public class BrokenImgs {
	
	static WebDriver driver;
	static String Path = "C:\\Users\\Sreen\\eclipse-workspace\\travelStart\\src\\test\\resources\\configFiles\\config.properties";
	static testmethods.Method m = new testmethods.Method();

	@Test
	public void homepage() throws Exception {
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sreen\\OneDrive\\Documents\\QA\\Selenium\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(m.ReadPropertyFile(Path, "LiveZAUrl"));
		Thread.sleep(20000);
		
		List<WebElement> images = driver.findElements(By.tagName("img"));

		System.out.println(images.size());
		for (int i=0; i< images.size();i++) {
			WebElement image;
			image = images.get(i);
			
			String imageUrl = image.getAttribute("src");
			
			
			try {
				URL url = new URL(imageUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();

				int responseCode = connection.getResponseCode();
				if (responseCode != 200) {
					System.out.println("Broken image found: " + imageUrl);
					System.out.println("-------------------------------------------");
					System.out.println("number = "+ i);
				}

				connection.disconnect();
			} catch (IOException e) {
				System.out.println("Exception occurred while checking image: " + imageUrl);
				e.printStackTrace();
			}
		}

		driver.close();
		
		
		
		
		
	}
	

}
