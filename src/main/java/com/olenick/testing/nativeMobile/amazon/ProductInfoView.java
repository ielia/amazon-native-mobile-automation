package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ProductInfoView extends View<ProductInfoView> {
    private static final Logger log = LoggerFactory.getLogger(ProductInfoView.class);

    @AndroidFindBy(id = "add-to-cart-button")
    private MobileElement addToCartButton;

    @AndroidFindBy(id = "atc-success")
    private MobileElement addToCartSuccessIndicator;

    @AndroidFindBy(className = "android.webkit.WebView")
    private MobileElement webView;

    public ProductInfoView(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    @Override
    public ProductInfoView waitToBeLoaded() {
        this.waitFor(Duration.ofSeconds(10), ExpectedConditions.visibilityOf(this.webView));
        return this;
    }

    public ProductInfoView addToCart() {
        this.scrollToElement(this.addToCartButton);
        this.addToCartButton.click();
        return this;
    }

    public Boolean wasAddedToCartSuccessfully() {
        return this.waitFor(Duration.ofSeconds(2), ExpectedConditions.visibilityOf(this.addToCartSuccessIndicator)) != null;
    }
}
