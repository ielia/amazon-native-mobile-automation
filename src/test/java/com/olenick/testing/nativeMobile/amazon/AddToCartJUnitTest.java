package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AddToCartJUnitTest {
    private static final Logger log = LoggerFactory.getLogger(AddToCartJUnitTest.class);

    private AppiumDriver<MobileElement> driver;

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
        this.driver = new AndroidDriver<>(hubURL, capabilities);
        this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        // log.info("setup: {}", this);
    }

    @AfterEach
    public void tearDown() {
        this.driver.quit();
    }

    // @ParameterizedTest
    // @Execution(ExecutionMode.CONCURRENT)
    // @ValueSource(strings = { "computer keyboard", "computer mouse", "computer monitor" })
    // @ValueSource(strings = { "computer keyboard" })
    public void testAddToCart(String searchText) {
        // When
        log.info("WHEN open app");
        StartupView startupView = new StartupView(this.driver);
        log.debug("startupView.waitToBeLoaded");
        startupView.waitToBeLoaded();

        log.info("searchFor '{}'", searchText);
        startupView.searchFor(searchText);

        log.info("tap first suggestion");
        startupView.tapSuggestion(0);

        log.info("search results show");
        SearchResultsView searchResultsView = new SearchResultsView(this.driver);
        log.debug("searchResultsView.waitToBeLoaded");
        searchResultsView.waitToBeLoaded();

        // TODO: Continue test
        log.info("Number of results: {}", searchResultsView.getNumberOfResultsAsShown());
    }

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
}
