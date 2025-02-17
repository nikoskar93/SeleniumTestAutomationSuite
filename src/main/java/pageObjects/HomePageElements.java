package pageObjects;

public interface HomePageElements {
	
	String searchInput = "input[data-testid='area-input']";
	String dropdownOption = "[data-testid='geo_place_id_dropdown_panel'] .dropdown-panel-option";
	String submitButton = "input[data-testid='submit-input']";
	String alreadySelectedFromDropdown = ".area-tag";
	String cookieBanner = "#qc-cmp2-ui";
	String acceptCookiesButton = "//*[@id=\"qc-cmp2-ui\"]/div[2]/div/button[3]";
}
