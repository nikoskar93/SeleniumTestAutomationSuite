package pageEvents;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pageObjects.HomePageElements;
import utils.ElementUtils;
import utils.Constants;

public class HomePageEvents {

	ElementUtils ele;
    WebDriver driver;
    ExtentTest logger;

    public HomePageEvents(WebDriver driver, ExtentTest logger) {
        this.driver = driver;
        this.ele = new ElementUtils(driver);
        this.logger = logger;
    }
    
    public void assertRentalProperties() {
    	List<WebElement> properties = ele.getWebElements("CSS", "#property-tab");
    	Assert.assertTrue(properties.size() > 0);
    	WebElement dropdownButton = ele.getWebElement("CSS", "button[data-testid='open-property-transaction-dropdown']");
    	dropdownButton.click();
    	WebElement rent = ele.getWebElement("CSS", "[data-testid='rent']");
    	String hasClass = rent.getDomAttribute("class");
    	boolean isSelected = hasClass.equalsIgnoreCase("selected");
    	if (isSelected) {
    		logger.log(Status.INFO, "Rental properties are selected");
    	}
    	Assert.assertTrue(isSelected);
    }

	public void selectAllDropdownOptionsAndSearch() throws InterruptedException {
		WebElement submitSearch = ele.getWebElement("CSS", HomePageElements.submitButton);
		WebElement searchInput = ele.getWebElement("CSS", HomePageElements.searchInput);
		searchInput.click();

		Set<String> alreadySelectedTexts = new HashSet<>();

		while (true) {
			searchInput.clear();
			searchInput.sendKeys(Constants.inputTextPagrati);
			Thread.sleep(500);

			List<WebElement> dropdownOptionsNew = ele.getWebElements("CSS", HomePageElements.dropdownOption);
			boolean newSelectionMade = false;

			for (WebElement option : dropdownOptionsNew) {
				String optionText = option.getText().trim().toLowerCase();

				if (!alreadySelectedTexts.contains(optionText)) {
					option.click();
					Thread.sleep(500);
					alreadySelectedTexts.add(optionText);
					newSelectionMade = true;
					break;
				}
			}

			if (!newSelectionMade) {
				break;
			}
		}
		logger.log(Status.INFO, "Perform search for " + alreadySelectedTexts.size() + " areas:" + alreadySelectedTexts.toString());
		submitSearch.click();
	}

	public void closeCookieBannerIfArises() {
		try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        WebElement cookieBannerButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(HomePageElements.acceptCookiesButton)));
	        cookieBannerButton.click();
	    } catch (StaleElementReferenceException e) {
	        System.out.println("Cookie banner element became stale. Retrying...");
	        WebElement cookieBannerButton = ele.getWebElement("XPATH", HomePageElements.acceptCookiesButton);
	        cookieBannerButton.click();
	        logger.log(Status.INFO, "Accepted cookies");
	    }
	}
}
