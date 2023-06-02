package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.time.Duration;
import java.util.Collections;

public abstract class View<V> {
    protected final AppiumDriver driver;

    protected View(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    protected void scrollToElement(WebElement element) {
        this.scrollToElement(element, false);
    }

    protected void scrollToElement(WebElement element, boolean bottom) {
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
            PointerInput finger = new PointerInput(org.openqa.selenium.interactions.PointerInput.Kind.TOUCH, "finger");
            driver.perform(
                    Collections.singletonList(
                            new Sequence(finger, 1)
                                    .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), anchor, startPoint))
                                    .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                                    .addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), anchor, endPoint > 0 ? endPoint : 100)) // TODO: Fix.
                                    .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))
                    )
            );
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
