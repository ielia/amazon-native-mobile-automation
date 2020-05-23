package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class HelpReturnAGiftView extends View<HelpReturnAGiftView> {
    private static final Logger log = LoggerFactory.getLogger(HelpReturnAGiftView.class);

    @AndroidFindBy(xpath = "//*[contains(@text, 'Return a Gift')]")
    private MobileElement title;

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/wish_list_bottom_sheet_fragment")
    private MobileElement bottomSheetFragment;

    public HelpReturnAGiftView(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    @Override
    public HelpReturnAGiftView waitToBeLoaded() {
        this.waitFor(Duration.ofSeconds(3), ExpectedConditions.visibilityOf(this.title));
        return this;
    }

    public HelpReturnAGiftView scrollToBottom() {
        this.scrollToElement(this.bottomSheetFragment, true);
        return this;
    }
}
