package testmethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Method {
	public static WebDriver driver;
	public static Properties prop = new Properties();
	public static FileReader fr;

	public static void launch(String URL) {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(URL);
	}

	public static void close() {
		driver.quit();
	}
	
	public static void maximize() {
		driver.manage().window().maximize();
	}
	
	public static void wait(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	public static String ReadPropertyFile(String path, String key) throws Exception {

		FileReader f = new FileReader(path);
		Properties p = new Properties();
		p.load(f);
		return p.getProperty(key);

	}


}
