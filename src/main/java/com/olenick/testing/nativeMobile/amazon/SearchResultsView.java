package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class SearchResultsView extends View<SearchResultsView> {
    private static final Logger log = LoggerFactory.getLogger(SearchResultsView.class);
    private static final String progressBarClassName = "android.widget.ProgressBar";

    @AndroidFindBy(id = "search")
    private WebElement searchPanel;

    @AndroidFindBy(xpath = "//*[@resource-id='search']//*[@resource-id='s-all-filters']/..//*[contains(@text, 'results')]")
    private WebElement numberOfResults;

    @AndroidFindBy(className = progressBarClassName)
    private WebElement progressBar;

    @FindBy(css = "[data-component-type='s-search-result'] .s-image")
    private List<WebElement> resultProductImages;

    public SearchResultsView(AppiumDriver driver) {
        super(driver);
    }

    @Override
    public SearchResultsView waitToBeLoaded() {
        return this.waitForSearchToBeDone();
    }

    public String getNumberOfResultsAsShown() {
        return this.numberOfResults.getText();
    }

    public ProductInfoView tapOnFirstResultImage() {
        // String originalContextHandle = this.driver.getContext();
        // //noinspection OptionalGetWithoutIsPresent
        // this.driver.context(this.driver.getContextHandles().stream().filter(c -> c.contains("WEBVIEW")).findFirst().get());
        // this.resultProductImages.get(index).click();
        // this.driver.context(originalContextHandle);
        this.driver.findElement(
                By.xpath(
                        "//*[@resource-id='search']/*[not(normalize-space(@text)='')][not(contains(@text, 'Filter by'))]"
                )
        ).click();
        return new ProductInfoView(this.driver);
    }

    public SearchResultsView waitForSearchToBeDone() {
        final By progressBarBy = MobileBy.className(progressBarClassName);
        // try {
        //     this.waitFor(Duration.ofSeconds(2), ExpectedConditions.visibilityOf(this.progressBar));
        // } catch (Exception ex) {
        //     // Do nothing.
        // }
        this.waitFor(
                Duration.ofSeconds(2),
                new ExpectedCondition<WebElement>() {
                    @NullableDecl
                    @Override
                    public WebElement apply(@NullableDecl WebDriver webDriver) {
                        try {
                            //noinspection ConstantConditions
                            return webDriver.findElement(progressBarBy);
                        } catch (NoSuchElementException ex) {
                            return null;
                        }
                    }
                }
                // ExpectedConditions.visibilityOf(this.progressBar)
        );
        this.waitFor(
                Duration.ofSeconds(10),
                new ExpectedCondition<Boolean>() {
                    @NullableDecl
                    @Override
                    public Boolean apply(@NullableDecl WebDriver webDriver) {
                        try {
                            //noinspection ConstantConditions
                            webDriver.findElement(progressBarBy);
                            return null;
                        } catch (NoSuchElementException ex) {
                            return true;
                        }
                    }
                }
                // ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(progressBarBy))
        );
        return this;
    }
}
