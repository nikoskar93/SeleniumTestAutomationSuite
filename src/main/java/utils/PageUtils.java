package utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.PropertyResultsPageElements;

public class PageUtils {

	public static void scrollIncrementallyToBottomOfPage(WebDriver driver) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int lastHeight = ((Number) js.executeScript("return document.body.scrollHeight")).intValue();
		int currentHeight = 0;

		while (lastHeight - currentHeight > 50) {
			js.executeScript("window.scrollBy(0, 1000);");
			Thread.sleep(2000);

			currentHeight = ((Number) js.executeScript("return window.scrollY + window.innerHeight")).intValue();
			lastHeight = ((Number) js.executeScript("return document.body.scrollHeight")).intValue();
		}
	}

	public static boolean navigateToNextPage(WebDriver driver) {
		try {
			List<WebElement> nextPageButton = driver.findElements(By.cssSelector(PropertyResultsPageElements.nextPageButton));
			if (nextPageButton.size() > 0 && nextPageButton.get(0).isDisplayed()) {
				String currentUrl = driver.getCurrentUrl();
				nextPageButton.get(0).click();
				new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
				return true; 
			}
		} catch (Exception e) {
			System.out.println("Failed to navigate to the next page: " + e.getMessage());
		}
		return false;
	}
}
