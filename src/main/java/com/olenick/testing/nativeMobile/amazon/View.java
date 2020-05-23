package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.time.Duration;

public abstract class View<V> {
    protected final AppiumDriver<? extends MobileElement> driver;

    protected View(AppiumDriver<? extends MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    protected <T> T waitFor(Duration timeout, ExpectedCondition<T> condition) {
        return new AppiumFluentWait<>(this.driver)
                .withTimeout(timeout)
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class)
                .ignoring(NullPointerException.class)
                .ignoring(TimeoutException.class)
                .until(condition);
    }

    public abstract V waitToBeLoaded();
}
