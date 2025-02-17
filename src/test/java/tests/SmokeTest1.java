package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pageEvents.HomePageEvents;
import pageEvents.PropertyResultsPageEvents;
import utils.ElementUtils;
import utils.Constants;

public class SmokeTest1 extends BaseTest {
	ElementUtils ele;
	HomePageEvents homePage;
	PropertyResultsPageEvents propertyResultsPage;

	@Test
	public void performSmokeTest() throws InterruptedException {
		ele = new ElementUtils(driver);
		homePage = new HomePageEvents(driver, logger);
		propertyResultsPage = new PropertyResultsPageEvents(driver, logger);

		homePage.closeCookieBannerIfArises();
		homePage.assertRentalProperties();
		homePage.selectAllDropdownOptionsAndSearch();

		propertyResultsPage.assertPropertyAdResultPageHasLoaded();
		propertyResultsPage.filterByPrice(Constants.minPriceFilter, Constants.maxPriceFilter);
		propertyResultsPage.filterBySquareFootage(Constants.minSizeFilter, Constants.maxSizeFilter);
		propertyResultsPage.verifySquareFootageAndPriceRanges(Constants.minSizeFilter, Constants.maxSizeFilter, Constants.minPriceFilter, Constants.maxPriceFilter);
		propertyResultsPage.countImagesForAdsInAllPages();
		propertyResultsPage.sortByDescendingPrice();
		propertyResultsPage.assertAdsSortedByDescendingPrice();
	}
}
