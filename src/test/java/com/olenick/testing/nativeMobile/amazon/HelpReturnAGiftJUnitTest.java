package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HelpReturnAGiftJUnitTest {
    private static final Logger log = LoggerFactory.getLogger(HelpReturnAGiftJUnitTest.class);

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
    }

    @AfterEach
    public void tearDown() {
        this.driver.quit();
    }

    @Test
    public void testAddToCart() {
        // When
        log.info("WHEN open app");
        StartupView startupView = new StartupView(this.driver);
        log.debug("startupView.waitToBeLoaded");
        startupView.waitToBeLoaded();

        log.info("open menu");
        startupView.openMenu();

        // Then
        log.info("THEN menu opens");
        startupView.waitForMenuToBeOpen();

        // When
        log.info("WHEN tap 'Customer Service' link");
        CustomerServiceView customerServiceView = startupView.tapMenuItem("Customer Service");

        // Then
        log.info("THEN Customer Service view loads up");
        customerServiceView.waitToBeLoaded();

        // When
        log.info("WHEN searchHelp 'Returns'");
        HelpSearchResultsView helpSearchResultsView = customerServiceView.searchHelp("Returns");

        // Then
        log.info("THEN go to 'Return a Gift'");
        HelpReturnAGiftView helpReturnAGiftView = helpSearchResultsView.goToReturnAGift();

        // And
        log.info("scroll to bottom and end test");
        helpReturnAGiftView.scrollToBottom();
    }
}
