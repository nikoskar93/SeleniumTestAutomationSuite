package pageObjects;

public interface PropertyResultsPageElements {
	String propertyAdResult = "[data-testid='property-ad-image']";
	String priceFilterButton = "button[data-testid='price-filter-button']";
	String minPriceInput = "input[data-testid='minimum_price_input']";
	String maxPriceInput = "input[data-testid='maximum_price_input']";
	String sqrFootFilterButton = "button[data-testid='size-filter-button']";
	String minSizeInput = "input[data-testid='minimum_size_input']";
	String maxSizeInput = "input[data-testid='maximum_size_input']";
	String nextPageButton = "a[role='button'][rel='next'][aria-label='Next page']";
	String propertyAd = ".common-ad";
	String adTitle = "[data-testid='property-ad-title']";
	String adPrice = "[data-testid='property-ad-price']";
	String adImage = ".slick-slide:not(.slick-cloned)";
	String sortingButton = "button[data-testid='open-property-sorting-dropdown']";
    String sortByPriceDescending = "[data-id='price_desc']";
    
}
