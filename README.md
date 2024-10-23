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
Run the following commands in your terminal:

bash
git clone <your-repo-url>
cd <project-directory>
Maven Commands:

To clean and build the project:
mvn clean install
To execute test suites:
bash
mvn test -DsuiteXmlFile=<suite-file-name>.xml


Project Structure

Project Root Directory contains the following important folders and files:

pom.xml: Maven configuration file
src/test/java/testRunners: Contains TestNG suites for running tests
src/test/java/testMethods: Contains test methods
src/test/java/pageObjects: Contains Page Object classes for web interactions
src/test/java/testClasses: Contains test case classes that use page objects
src/test/java/listeners: Contains custom TestNG listeners for logging or reporting
src/test/java/configs: Contains paths for Excel data files
TestData: Folder for storing test data and test cases
Components Description

TestNG Suites: Entry points for running tests.
Test Methods: Defined using TestNG annotations (@Test).
Page Objects: Represent web page elements and actions using POM.
Test Classes: Test cases using page objects and performing operations.
Listeners: Custom listeners for logging or reporting during test execution.
Test Data: Excel files for data-driven testing, managed by Apache POI.
Prerequisites

Make sure the following are installed on your system:

Java JDK 8+
Maven 3.6+
TestNG
Selenium WebDriver
External Dependencies
All required dependencies are managed through Maven. Make sure you configure pom.xml appropriately.

Running Tests

Test Suites:
Tests are grouped into TestNG suites.
To run a suite, use the following command:

bash
mvn test -DsuiteXmlFile=src/test/java/testRunners/<suite-file-name>.xml
Individual Tests:
You can also run individual test classes or methods using this command:

php
mvn -Dtest=<ClassName>#<methodName> test
Adding New Tests

Page Objects:
Add new page object classes in src/test/java/pageObjects to represent new web pages or components.

Test Cases:
Add new test cases in src/test/java/testClasses and ensure they use reusable methods from the page objects.

Test Data:
Update or add new Excel sheets in the TestData folder to manage test data for data-driven testing.

Additional Notes

Dynamic Paths:
Replace <project-directory> with the actual project path on your machine when using file paths.

Environment Config:
Store environment-specific variables in .env files, such as src/test/resources/configFiles/environmentFiles.env.