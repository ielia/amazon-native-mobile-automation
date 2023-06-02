package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class HelpSearchResultsView extends View<HelpSearchResultsView> {
    private static final Logger log = LoggerFactory.getLogger(HelpSearchResultsView.class);

    @AndroidFindBy(xpath = "//*[contains(@text, 'Help Search Results')]")
    private WebElement title;

    @AndroidFindBy(xpath = "//*[starts-with(@text, 'Return a Gift')]")
    private WebElement returnAGiftLink;

    public HelpSearchResultsView(AppiumDriver driver) {
        super(driver);
    }

    @Override
    public HelpSearchResultsView waitToBeLoaded() {
        this.waitFor(Duration.ofSeconds(5), ExpectedConditions.visibilityOf(this.title));
        return this;
    }

    public HelpReturnAGiftView goToReturnAGift() {
        this.scrollToElement(this.returnAGiftLink);
        this.returnAGiftLink.click();
        return new HelpReturnAGiftView(this.driver);
    }
}
