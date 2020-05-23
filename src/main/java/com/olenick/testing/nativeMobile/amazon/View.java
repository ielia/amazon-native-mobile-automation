package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
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

    protected void scrollToElement(MobileElement element) {
        this.scrollToElement(element, false);
    }

    protected void scrollToElement(MobileElement element, boolean bottom) {
        // This method is ok for now, but going forward we should fix it for the cases where the element is way down.
        Point elementLocation = element.getLocation();
        int elementY = elementLocation.getY();
        if (bottom) {
            elementY += element.getSize().getHeight();
        }
        Dimension windowSize = this.driver.manage().window().getSize();
        int windowHeight = windowSize.getHeight();
        int windowWidth = windowSize.getWidth();
        if (windowHeight < elementY) {
            int anchor = windowWidth / 2;
            int startPoint = windowHeight - 100;
            int endPoint = elementY - windowHeight - 200;
            //noinspection rawtypes
            new TouchAction(this.driver)
                    .longPress(PointOption.point(anchor, startPoint))
                    .moveTo(PointOption.point(anchor, endPoint > 0 ? endPoint : 100)) // TODO: Fix.
                    .release()
                    .perform();
        }
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
