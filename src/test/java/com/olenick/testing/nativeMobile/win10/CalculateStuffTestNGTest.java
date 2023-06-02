package com.olenick.testing.nativeMobile.win10;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class CalculateStuffTestNGTest {
    private static final Logger log = LoggerFactory.getLogger(CalculateStuffTestNGTest.class);

    private static AppiumDriver driver;

    @BeforeMethod
    public static void setup() throws MalformedURLException, URISyntaxException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        // capabilities.setCapability("app", "C:\\Windows\\System32\\calc.exe");
        capabilities.setCapability("app", "calc");
        capabilities.setCapability("platformName", "Windows");
        capabilities.setCapability("deviceName", "WindowsPC");
        capabilities.setCapability("automationName", "Windows");
        capabilities.setCapability("ms:experimental-webdriver", true);
        capabilities.setCapability("ms:waitForAppLaunch", 5);
        driver = new AppiumDriver(new URI("http://127.0.0.1:9000").toURL(), capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testAddition() {
        Double results = new CalculatorWindow(driver).inputNumber(123).push("+").inputNumber(456).getResults();
        Assert.assertEquals(results.doubleValue(), 579.0);
    }
}
