package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class CustomerServiceView extends View<CustomerServiceView> {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceView.class);

    @AndroidFindBy(xpath = "//*[contains(@text, 'What can we help you with')]")
    private WebElement greetingLabel;

    @AndroidFindBy(id = "cslp-help-search-text-input")
    private WebElement helpSearchInput;

    @AndroidFindBy(id = "help-search-submit")
    private WebElement helpSearchSubmitButton;

    public CustomerServiceView(AppiumDriver driver) {
        super(driver);
    }

    @Override
    public CustomerServiceView waitToBeLoaded() {
        this.waitFor(Duration.ofSeconds(3), ExpectedConditions.visibilityOf(this.greetingLabel));
        return this;
    }

    public HelpSearchResultsView searchHelp(String text) {
        this.scrollToElement(this.helpSearchInput);
        this.helpSearchInput.sendKeys(text);
        this.driver.resetInputState(); // TODO: Check this is the same as .hideKeyboard()
        this.helpSearchSubmitButton.click();
        return new HelpSearchResultsView(this.driver);
    }
}
