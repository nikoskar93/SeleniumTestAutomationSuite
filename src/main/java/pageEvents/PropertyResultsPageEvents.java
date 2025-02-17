package pageEvents;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.ElementUtils;
import utils.PageUtils;
import pageObjects.PropertyResultsPageElements;

public class PropertyResultsPageEvents {

	ElementUtils ele;
    WebDriver driver;
    ExtentTest logger;

    public PropertyResultsPageEvents(WebDriver driver, ExtentTest logger) {
        this.driver = driver;
        this.ele = new ElementUtils(driver);
        this.logger = logger;
    }
	

	public void assertPropertyAdResultPageHasLoaded() {
		List<WebElement> propertyAdResults = ele.waitForElements("CSS",PropertyResultsPageElements.propertyAd);
		Assert.assertTrue(propertyAdResults.size() > 0, "Property results not found");
	}

	public void filterByPrice(String minPrice, String maxPrice){
		WebElement priceFilter = ele.waitForElement("CSS", PropertyResultsPageElements.priceFilterButton);
		priceFilter.click();
		WebElement minPriceInput = ele.getWebElement("CSS", PropertyResultsPageElements.minPriceInput);
		WebElement maxPriceInput = ele.getWebElement("CSS", PropertyResultsPageElements.maxPriceInput);
		minPriceInput.sendKeys(minPrice);
		maxPriceInput.sendKeys(maxPrice);
		priceFilter.click();
		logger.log(Status.INFO, "Filtered: minPrice = €" + minPrice + ", maxPrice = €" + maxPrice);
	}

	public void filterBySquareFootage(String minSize, String maxSize) {
		WebElement sizeFilter = ele.waitForElement("CSS", PropertyResultsPageElements.sqrFootFilterButton);
		sizeFilter.click();
		WebElement minSizeInput = ele.getWebElement("CSS", PropertyResultsPageElements.minSizeInput);
		WebElement maxSizeInput = ele.getWebElement("CSS", PropertyResultsPageElements.maxSizeInput);
		minSizeInput.sendKeys(minSize);
		maxSizeInput.sendKeys(maxSize);
		sizeFilter.click();
		logger.log(Status.INFO, "Filtered: minSize = " + minSize + "m2, maxSize =" + maxSize + "m2");
	}
	
	public void verifySquareFootageAndPriceRanges(String minSqftStr, String maxSqftStr, String minPriceStr, String maxPriceStr) throws InterruptedException {
		int minSqft = Integer.parseInt(minSqftStr);
	    int maxSqft = Integer.parseInt(maxSqftStr);
	    int minPrice = Integer.parseInt(minPriceStr);
	    int maxPrice = Integer.parseInt(maxPriceStr);
	    
	    List<String> allSqftValues = new ArrayList<>();
	    List<String> allPriceValues = new ArrayList<>();
	    boolean isLastPage = false;
	    int pageNumber = 1;

	    while (!isLastPage) {
	    	PageUtils.scrollIncrementallyToBottomOfPage(driver);
		    List<WebElement> ads = ele.getWebElements("CSS", PropertyResultsPageElements.propertyAd);
		    logger.log(Status.INFO, "Total ads: " + ads.size() + ", current page: " + pageNumber);
		    
		    for (WebElement ad : ads) {
		        String sqftText = ad.findElement(By.cssSelector(PropertyResultsPageElements.adTitle)).getText().replaceAll("[^0-9]", "");
		        String priceText = ad.findElement(By.cssSelector(PropertyResultsPageElements.adPrice)).getText().replaceAll("[^0-9]", "");
		        
		        allSqftValues.add(sqftText);
		        allPriceValues.add(priceText);
		    }
		    logger.log(Status.INFO, "Square Footages from ads: " + String.join(", ", allSqftValues));
		    logger.log(Status.INFO, "Prices from ads: " + String.join(", ", allPriceValues));
		    
		    boolean allValid = true;
		    
		    for (int i = 0; i < ads.size(); i++) {
		        int sqft = Integer.parseInt(allSqftValues.get(i));
		        int price = Integer.parseInt(allPriceValues.get(i));

		        if (sqft < minSqft || sqft > maxSqft) {
	                logger.log(Status.FAIL, "Square footage " + sqft + " of ad on page " + pageNumber + " is out of range " + minSqft + " - " + maxSqft);
	                allValid = false;
	            }

	            if (price < minPrice || price > maxPrice) {
	                logger.log(Status.FAIL, "Price " + price + " of ad on page " + pageNumber + " is out of range " + minPrice + " - " + maxPrice);
	                allValid = false;
	            }
		    }

            if (allValid) {
                logger.log(Status.PASS, "All ads on page " + pageNumber + " have square footage and price within the specified ranges.");
            }
		    
		    boolean nextPage = PageUtils.navigateToNextPage(driver);
	        if(nextPage) {
	        	pageNumber++;
	        } else {
	        	isLastPage = true;
	        }
	    }   
	}

	public void countImagesForAdsInAllPages() throws InterruptedException {
		int pageNumber = 1;
		while (true) {
		    PageUtils.scrollIncrementallyToBottomOfPage(driver);

		    List<WebElement> searchResults = ele.getWebElements("CSS", PropertyResultsPageElements.propertyAd);
		    logger.log(Status.INFO, "Total ads: " + searchResults.size() + ", current page: " + pageNumber);
		    boolean imageCountIsValid = true;
		    for (WebElement ad : searchResults) {
		        List<WebElement> imageElements = ad.findElements(By.cssSelector(PropertyResultsPageElements.adImage));
		        int imageCount = imageElements.size();
		        
		        if (imageCount > 30) {
		            logger.log(Status.FAIL, "Found ad with more than 30 images. Image count: " + imageCount);
		            imageCountIsValid = false;
		        }
		        Assert.assertTrue(imageCount <= 30);
		    }
		    if(imageCountIsValid) {
		    	logger.log(Status.PASS, "All ads have 30 images or less");
		    }
		    boolean nextPage = PageUtils.navigateToNextPage(driver);
		    if(!nextPage) break;
		}
	}
	
	public void sortByDescendingPrice() {
		WebElement sortButton = ele.waitForElement("CSS",PropertyResultsPageElements.sortingButton);
		sortButton.click();
		WebElement sortByDescPrice = ele.waitForElement("CSS",PropertyResultsPageElements.sortByPriceDescending);
		sortByDescPrice.click();
	}
	
	public void assertAdsSortedByDescendingPrice() throws InterruptedException {
		
		List<Double> allPrices = new ArrayList<>();
	    boolean isLastPage = false;

	    while (!isLastPage) {
	    	PageUtils.scrollIncrementallyToBottomOfPage(driver);
	        List<WebElement> adPrices = ele.getWebElements("CSS", PropertyResultsPageElements.adPrice);
	        logger.log(Status.INFO, "Total ad prices found: " + adPrices.size());

	        List<Double> prices = adPrices.stream()
	                .map(e -> e.getText().replaceAll("[^0-9.]", ""))
	                .filter(s -> !s.isEmpty())
	                .map(Double::parseDouble)
	                .collect(Collectors.toList());

	        allPrices.addAll(prices);
	        logger.log(Status.INFO, "All prices so far: " + allPrices);
	        
	        boolean nextPage = PageUtils.navigateToNextPage(driver);
	        if(!nextPage) {
	        	isLastPage = true;
	        }
	    }

	    for (int i = 0; i < allPrices.size() - 1; i++) {
	        Assert.assertTrue(allPrices.get(i) >= allPrices.get(i + 1), 
	            "Prices are not sorted in descending order!");
	    }
	    logger.log(Status.INFO, "Final list of all prices (should be sorted by dexcending price): " + allPrices);
    }
}
