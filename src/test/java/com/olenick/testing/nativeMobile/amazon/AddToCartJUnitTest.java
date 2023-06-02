package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AddToCartJUnitTest {
    private static final Logger log = LoggerFactory.getLogger(AddToCartJUnitTest.class);

    private AppiumDriver driver;

    @BeforeEach
    public void setup() throws MalformedURLException {
        final URL hubURL = new URL("http://127.0.0.1:4723/wd/hub");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability("appPackage", "com.amazon.mShop.android.shopping");
        capabilities.setCapability("appActivity", "com.amazon.mShop.splashscreen.StartupActivity");

        log.info("Starting driver on {}", this);
        this.driver = new AndroidDriver(hubURL, capabilities);
        this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        // log.info("setup: {}", this);
    }

    @AfterEach
    public void tearDown() {
        this.driver.quit();
    }

    @ParameterizedTest
    // @Execution(ExecutionMode.CONCURRENT)
    // @ValueSource(strings = { "computer keyboard", "computer mouse", "computer monitor" })
    @ValueSource(strings = { "computer monitor" })
    public void testAddToCart(String searchText) {
        // When
        log.info("WHEN open app");
        StartupView startupView = new StartupView(this.driver);
        log.debug("startupView.waitToBeLoaded");
        startupView.waitToBeLoaded();

        log.info("searchFor '{}'", searchText);
        startupView.searchFor(searchText);

        log.info("tap first suggestion");
        SearchResultsView searchResultsView = startupView.tapSuggestion(0);

        // Then
        log.info("THEN search results show");
        log.debug("searchResultsView.waitToBeLoaded");
        searchResultsView.waitToBeLoaded();
        // log.info("Number of results: {}", searchResultsView.getNumberOfResultsAsShown());

        // When
        log.info("WHEN tap first result");
        ProductInfoView productInfoView = searchResultsView.tapOnFirstResultImage();

        // Then
        log.debug("productInfoView.waitToBeLoaded");
        productInfoView.waitToBeLoaded();

        // When
        log.debug("WHEN add to cart");
        productInfoView.addToCart();

        // Then
        log.debug("THEN product was added successfully");
        productInfoView.wasAddedToCartSuccessfully();
    }

    /*
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    public void testAddKeyboardToCart() {
        this.testAddToCart("computer keyboard");
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    public void testAddMouseToCart() {
        this.testAddToCart("computer mouse");
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    public void testAddMonitorToCart() {
        this.testAddToCart("computer monitor");
    }
    */
}
