# Hybrid Test Automation Framework

A robust, scalable test automation framework built with Selenium, Java, and TestNG, implementing Page Object Model (POM) and Data-Driven Testing (DDT) approaches.

## 🚀 Key Features

- **Page Object Model (POM)**: Maintainable and reusable page-specific classes
- **Data-Driven Testing**: Excel-based test data management using Apache POI
- **Cross-Browser Testing**: Support for Chrome, Firefox, and Edge
- **Parallel Execution**: TestNG-powered concurrent test execution
- **Detailed Reporting**: Automated test execution reports with screenshots
- **Configurable**: Environment-specific settings management
- **CI/CD Ready**: Jenkins pipeline integration support

## 📋 Prerequisites

### Required Software
- Java JDK 11 or higher
- Maven 3.6+
- Chrome/Firefox/Edge browser
- Git

### IDE Setup (Recommended)
- IntelliJ IDEA or Eclipse with TestNG plugin
- Cucumber plugin (for BDD features)
- Maven Integration

## 🛠️ Technology Stack

| Component        | Technology           |
|-----------------|---------------------|
| Build Tool      | Maven              |
| Test Framework  | TestNG             |
| UI Automation   | Selenium WebDriver |
| Language        | Java               |
| Reporting       | ExtentReports      |
| Data Handling   | Apache POI         |
| Version Control | Git                |

## 📁 Project Structure

```
project-root/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── base/
│   │       │   ├── BaseTest.java
│   │       │   └── BrowserFactory.java
│   │       ├── utils/
│   │       │   ├── ExcelUtils.java
│   │       │   ├── ConfigReader.java
│   │       │   └── ScreenshotUtils.java
│   │       └── constants/
│   └── test/
│       ├── java/
│       │   ├── pageObjects/
│       │   ├── testCases/
│       │   └── testData/
│       └── resources/
│           ├── config/
│           │   └── config.properties
│           └── testData/
│               └── TestData.xlsx
├── test-output/
├── logs/
├── screenshots/
└── pom.xml
```

## 🚀 Getting Started

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd <project-directory>
   ```

2. **Install Dependencies**
   ```bash
   mvn clean install
   ```

3. **Update Configuration**
    - Navigate to `src/test/resources/config/config.properties`
    - Update browser, environment, and other settings

4. **Run Tests**
   ```bash
   # Run all tests
   mvn test

   # Run specific test suite
   mvn test -DsuiteXmlFile=testng.xml

   # Run specific test class
   mvn test -Dtest=LoginTest
   ```

## 📝 Writing Tests

### Creating a New Test Case

1. **Create Page Object**
   ```java
   public class LoginPage extends BasePage {
       @FindBy(id = "username")
       private WebElement usernameField;

       public LoginPage(WebDriver driver) {
           super(driver);
           PageFactory.initElements(driver, this);
       }

       public void login(String username, String password) {
           // Implementation
       }
   }
   ```

2. **Create Test Class**
   ```java
   @Test(groups = {"regression"})
   public class LoginTest extends BaseTest {
       @Test(dataProvider = "loginData")
       public void validLogin(String username, String password) {
           LoginPage loginPage = new LoginPage(driver);
           loginPage.login(username, password);
           Assert.assertTrue(loginPage.isLoggedIn());
       }
   }
   ```

### Data-Driven Testing

1. Create test data in `src/test/resources/testData/TestData.xlsx`
2. Use `@DataProvider` to feed data to tests:
   ```java
   @DataProvider(name = "loginData")
   public Object[][] getLoginData() {
       return ExcelUtils.getTestData("LoginTest");
   }
   ```

## 📊 Reporting

- **HTML Reports**: Located in `test-output/html/`
- **Screenshots**: Captured automatically on test failure
- **Logs**: Detailed execution logs in `logs/`

## 🔧 Configuration

### Environment Variables
```properties
browser=chrome
headless=false
implicitWait=10
baseUrl=https://example.com
```

### Cross-Browser Testing
Supported Browsers:
- Chrome (default)
- Firefox
- Edge
- Chrome-headless

## 🤝 Contributing

1. Create a feature branch
2. Commit changes
3. Push to the branch
4. Create Pull Request

## 📚 Best Practices

- Follow Page Object Model
- Maintain test independence
- Use meaningful naming conventions
- Add comments for complex logic
- Regular code reviews
- Keep tests atomic and focused

## 🔍 Troubleshooting

Common Issues:
- **Test Failure Screenshots**: Check `screenshots/` directory
- **Driver Issues**: Update webdriver in `pom.xml`
- **Timing Issues**: Adjust waits in config
- **Data Issues**: Verify Excel data format

## 📫 Support

For issues and support:
1. Check existing issues
2. Create detailed bug reports
3. Contact framework maintainers

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
