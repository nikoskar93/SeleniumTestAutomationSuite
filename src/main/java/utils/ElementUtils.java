package utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtils {
	WebDriver driver;
	
	public ElementUtils(WebDriver driver) {
        this.driver = driver;
    }

	
	public WebElement getWebElement(String identifierType, String identifierValue) {
		switch(identifierType) {
		
		case "XPATH":
			return driver.findElement(By.xpath(identifierValue));
		case "CSS":
			return driver.findElement(By.cssSelector(identifierValue));		
		case "ID":
			return driver.findElement(By.id(identifierValue));		
		case "NAME":
			return driver.findElement(By.name(identifierValue));			
		case "TAGNAME":
			return driver.findElement(By.tagName(identifierValue));
		
		default:
			return null;
		}
	}
	
	public List<WebElement> getWebElements(String identifierType, String identifierValue) {
		switch(identifierType) {
		
		case "XPATH":
			return driver.findElements(By.xpath(identifierValue));
		case "CSS":
			return driver.findElements(By.cssSelector(identifierValue));		
		case "ID":
			return driver.findElements(By.id(identifierValue));		
		case "NAME":
			return driver.findElements(By.name(identifierValue));			
		case "TAGNAME":
			return driver.findElements(By.tagName(identifierValue));
		
		default:
			return null;
		}
	}
	
	public WebElement waitForElement(String identifierType, String selector) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = getWebElement(identifierType, selector);
        wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }
	
	public List<WebElement> waitForElements(String identifierType, String selector) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    List<WebElement> elements = getWebElements(identifierType, selector);
	    wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	    return elements;
	}

	
	 public WebDriver getDriver() {
	        return driver;
	    }
}
