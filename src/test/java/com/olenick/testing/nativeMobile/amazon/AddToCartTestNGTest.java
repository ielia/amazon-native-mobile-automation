package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AddToCartTestNGTest {
    private static final Logger log = LoggerFactory.getLogger(AddToCartTestNGTest.class);

    private AppiumDriver<MobileElement> driver;
    private String searchText;

    @BeforeClass
    public static void setupTestClass(ITestContext context) {
        context.getCurrentXmlTest().getSuite().setDataProviderThreadCount(2);
        context.getCurrentXmlTest().getSuite().setPreserveOrder(false);
    }

    /*
    @BeforeMethod
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

        log.info("setup: {}", this);
    }
    */

    @AfterMethod
    public void tearDown() {
        log.info("{} - tearDown", this);
        this.driver.quit();
    }

    private AppiumDriver<MobileElement> driver(int port, String udid, String deviceName) throws MalformedURLException {
        final URL hubURL = new URL("http://127.0.0.1:" + port + "/wd/hub");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability("appPackage", "com.amazon.mShop.android.shopping");
        capabilities.setCapability("appActivity", "com.amazon.mShop.splashscreen.StartupActivity");

        log.info("Starting driver on {}", this);
        AppiumDriver<MobileElement> driver = new AndroidDriver<>(hubURL, capabilities);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        return driver;
    }

    @DataProvider(name = "search", parallel = true)
    public Object[][] searchProvider() throws MalformedURLException {
        return new Object[][] {
                { this.driver(4723, "emulator-5554", "Nexus 5 API 28 (001)"), "computer keyboard" },
                { this.driver(4724, "emulator-5556", "Nexus 5 API 28 (002)"), "computer mouse" },
                { this.driver(4723, "emulator-5554", "Nexus 5 API 28 (001)"), "computer monitor" }
        };
    }

    @Test(dataProvider = "search", threadPoolSize = 2)
    public void testAddToCart(AppiumDriver<MobileElement> driver, String searchText) {
        // Given
        log.info("{} - testAddToCart", this);
        this.driver = driver;

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
}
