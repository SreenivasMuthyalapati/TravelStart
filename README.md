Hybrid Test Framework
This project is a Maven-based hybrid test automation framework built using Selenium, Java, and TestNG. It employs Page Object Model (POM) and Data-Driven Testing (DDT) using Apache POI for reading data from Excel files.

Table of Contents
Getting Started
Project Structure
Prerequisites
Running Tests
Adding New Tests
Additional Notes
Getting Started
Clone the repository:
bash
Copy code
git clone <your-repo-url>
cd <project-directory>
Maven Commands:
To clean and build the project:

bash
Copy code
mvn clean install
To execute test suites:

bash
Copy code
mvn test -DsuiteXmlFile=<suite-file-name>.xml
Project Structure
bash
Copy code
<project-root>
│
├── pom.xml                         # Maven configuration file
├── src
│   ├── test
│   │   ├── java
│   │   │   ├── testRunners         # TestNG Suites
│   │   │   ├── testmethods         # Test methods
│   │   │   ├── pageObjects         # Page Object classes
│   │   │   ├── testClasses         # Test case classes
│   │   │   ├── listeners           # Test Listeners
│   │   └── configs                 # Paths for Excel data files
│   └── TestData                    # Folder for test data and cases
Components:
TestNG Suites: Test execution entry points.
Test Methods: Individual test cases defined using TestNG annotations.
Page Objects: The POM layer for web page interactions.
Test Classes: Test cases that use page objects and perform operations.
Listeners: Custom TestNG listeners for logging or reporting.
Test Data: Excel files for data-driven testing using Apache POI.
Prerequisites
Ensure the following are installed on your system:

Java JDK 8+
Maven 3.6+
TestNG
Selenium WebDriver
External Dependencies:
All required dependencies are managed through Maven. Ensure you have configured the pom.xml appropriately.

Running Tests
Test Suites:
Tests are grouped into TestNG suites. Navigate to the testRunners directory to find the TestNG XML files.

Example to run a suite:

bash
Copy code
mvn test -DsuiteXmlFile=src/test/java/testRunners/<suite-file-name>.xml
Individual Tests:
You can also run individual test classes or methods via TestNG.

Example:

bash
Copy code
mvn -Dtest=<ClassName>#<methodName> test
Adding New Tests
Page Objects:
Add new page object classes in src/test/java/pageObjects. These classes represent web page elements and actions.

Test Cases:
Add new test cases in src/test/java/testClasses. Make sure your tests use reusable methods from the page objects.

Test Data:
Update or add new Excel sheets in src/test/java/configs or TestData folders to manage test data for DDT.

Additional Notes
Dynamic Paths:
Replace <project-directory> with the actual project path on your local machine when using file paths.

Environment Config:
Store environment-specific variables in .env files for easier management, such as in src/test/resources/configFiles/environmentFiles.env.