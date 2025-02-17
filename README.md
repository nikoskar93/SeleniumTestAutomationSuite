# Automated Selenium Test Suite

##  Overview
This project is an automated test suite for verifying search results and image count constraints using Selenium WebDriver, TestNG, and Extent Reports.

## Prerequisites
Before running the tests, ensure you have the following installed:

- Java (JDK 11 or later)
- Maven (for dependency management)
- WebDriverManager (handles browser drivers automatically)
- Firefox/Chrome/Edge (at least one browser installed)

## Project Structure
- /project-root/: Project root directory  
- src/main/java/: Main source folder  
  - base/: BaseTest class (handles setup, teardown, and WebDriver initialization)  
  - tests/: Test scripts (e.g., `SmokeTest1`)  
  - utils/: Utility classes (e.g., `SuiteListener`, `Constants`)  
- pom.xml: Maven dependencies and project configuration  
- testng.xml: TestNG test suite configuration  
- reports/: Folder for storing generated test reports  
- README.md: Project documentation 

### **Key Features:**

- **Search Results Validation**: Ensures results are correctly sorted when sorted by descending price.
- **Image Count Check**: Verifies that no ad contains more than **30 images** in its carousel.
- **Pagination Handling**: Automatically navigates through multiple search result pages to verify all ads.
- **Cross-Browser Testing**: Supports Chrome, Firefox, and Edge, configurable via `testng.xml`.
- **Test Reports**: Generates detailed test execution reports using **Extent Reports** (`reports/nikoletaReport.html`).

## Dependencies
The following dependencies are used (from `pom.xml`):
- Selenium WebDriver (`4.27.0`)
- TestNG (`7.10.2`)
- WebDriverManager (`5.9.2`)
- ExtentReports (`5.0.9`)

Maven automatically handles these dependencies when you build the project.

## Running the Tests

1. Using Maven
   Run the following command in the project root:
   mvn test

2. Using TestNG (testng.xml)
   mvn clean test -DsuiteXmlFile=testng.xml

The default browser is Firefox, as defined in testng.xml.
To change the browser, edit testng.xml:
   <parameter name="browser" value="chrome"/>

3. Running Directly from IDE (Eclipse/IntelliJ)
    Open SmokeTest1.java and right-click → Run as TestNG Test.
    Or, open testng.xml and run as TestNG Suite.


Test Report

After execution, the report is generated at:
reports/nikoletaReport.html
Open this file in a browser to view test results.
Uses Extent Reports with pass/fail details.

Troubleshooting
"Browser Not Supported" Error

    Ensure the browser name passed in testng.xml matches:
        "chrome" for Chrome
        "firefox" for Firefox
        "edge" for Edge

Test Fails Due to Missing WebDriver
    Run: mvn clean test
	Ensure WebDriverManager downloads the correct driver version.

---

### ** Summary**
- This **README.md** includes **setup instructions, dependencies, and how to run the tests**.
- The **test report is generated as `nikoletaReport.html` inside the `reports` folder**.
