package base;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.Constants;



public class BaseTest {
	public static WebDriver driver;
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest logger;

	@BeforeTest
	public void beforeTestMethod() 
	{
		sparkReporter = new ExtentSparkReporter
				(System.getProperty("user.dir") +
						File.separator + "reports" +
						File.separator + "nikoletaReport.html");
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		sparkReporter.config().setTheme(Theme.DARK);
		extent.setSystemInfo("UserName", "root");
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Automated Test Results - Nikoleta Skarpa");
	}

	@BeforeMethod
	@Parameters("browser")
	public void beforeMethodMethod(@Optional("firefox")String browser, Method testMethod)
	{
		logger = extent.createTest(testMethod.getName());
		setupDriver(browser);
		driver.manage().window().maximize();
		driver.get(Constants.url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}

	@AfterMethod
	public void afterMethodMethod(ITestResult result)
	{
		if(result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test case failed", ExtentColor.RED));
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + "- Test case failed", ExtentColor.RED));
		}
		else if(result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+ " - Test case skipped", ExtentColor.ORANGE));
		}
		else if(result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+ " - Test case passed", ExtentColor.GREEN));
		}
	}

	@AfterTest
	public void afterTestMethod()
	{
		extent.flush();
		if (driver != null) {
	        driver.quit();
	    }
	}

	public void setupDriver(String browser) {
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().clearDriverCache().setup();
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		else {
	        throw new IllegalArgumentException("Browser not supported: " + browser);
	    }
	}
	
	public static WebDriver getDriver() {
	    return driver;
	}
}
