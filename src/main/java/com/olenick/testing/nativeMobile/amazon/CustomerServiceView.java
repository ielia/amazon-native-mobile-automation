package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class CustomerServiceView extends View<CustomerServiceView> {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceView.class);

    @AndroidFindBy(xpath = "//*[contains(@text, 'What can we help you with')]")
    private MobileElement greetingLabel;

    @AndroidFindBy(id = "cslp-help-search-text-input")
    private MobileElement helpSearchInput;

    @AndroidFindBy(id = "help-search-submit")
    private MobileElement helpSearchSubmitButton;

    public CustomerServiceView(AppiumDriver<? extends MobileElement> driver) {
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
        this.driver.hideKeyboard();
        this.helpSearchSubmitButton.click();
        return new HelpSearchResultsView(this.driver);
    }
}
