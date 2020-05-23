package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class SearchResultsView extends View<SearchResultsView> {
    private static final Logger log = LoggerFactory.getLogger(SearchResultsView.class);

    @AndroidFindBy(id = "search")
    private MobileElement searchPanel;

    @AndroidFindBy(xpath = "//*[@resource-id='search']//*[@resource-id='s-all-filters']/..//*[contains(@text, 'results')]")
    private MobileElement numberOfResults;

    public SearchResultsView(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    @Override
    public SearchResultsView waitToBeLoaded() {
        return this.waitForSearchToBeDone();
    }

    public String getNumberOfResultsAsShown() {
        return this.numberOfResults.getText();
    }

    public SearchResultsView waitForSearchToBeDone() {
        this.waitFor(Duration.ofSeconds(10), ExpectedConditions.visibilityOf(this.numberOfResults));
        return this;
    }
}
