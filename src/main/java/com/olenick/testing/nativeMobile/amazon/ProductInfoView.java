package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ProductInfoView extends View<ProductInfoView> {
    private static final Logger log = LoggerFactory.getLogger(ProductInfoView.class);

    @AndroidFindBy(id = "add-to-cart-button")
    private WebElement addToCartButton;

    @AndroidFindBy(id = "atc-success")
    private WebElement addToCartSuccessIndicator;

    @AndroidFindBy(className = "android.webkit.WebView")
    private WebElement webView;

    public ProductInfoView(AppiumDriver driver) {
        super(driver);
    }

    @Override
    public ProductInfoView waitToBeLoaded() {
        this.waitFor(Duration.ofSeconds(10), ExpectedConditions.visibilityOf(this.webView));
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public ProductInfoView addToCart() {
        this.scrollToElement(this.addToCartButton);
        this.addToCartButton.click();
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public Boolean wasAddedToCartSuccessfully() {
        return this.waitFor(Duration.ofSeconds(2), ExpectedConditions.visibilityOf(this.addToCartSuccessIndicator)) != null;
    }
}
