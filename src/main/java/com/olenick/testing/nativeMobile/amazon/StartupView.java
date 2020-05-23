package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class StartupView extends View<StartupView> {
    private static final Logger log = LoggerFactory.getLogger(StartupView.class);

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text")
    private MobileElement searchBox;

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/iss_search_suggestions_list_view")
    private MobileElement suggestionsList;

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/iss_search_dropdown_item_suggestions")
    private List<MobileElement> searchSuggestions;

    @AndroidFindBys({
            @AndroidBy(id = "com.amazon.mShop.android.shopping:id/iss_search_dropdown_item_suggestions"),
            @AndroidBy(id = "com.amazon.mShop.android.shopping:id/iss_search_dropdown_item_text")
    })
    private List<MobileElement> searchSuggestionTexts;

    public StartupView(AppiumDriver<? extends MobileElement> driver) {
        super(driver);
    }

    @Override
    public StartupView waitToBeLoaded() {
        this.waitFor(Duration.ofSeconds(30), ExpectedConditions.elementToBeClickable(this.searchBox));
        return this;
    }

    public List<String> getSuggestions() {
        return this.searchSuggestionTexts.stream().map(RemoteWebElement::getText).collect(Collectors.toList());
    }

    public boolean isSuggestionsListDisplayed() {
        return this.suggestionsList.isDisplayed();
    }

    public StartupView searchFor(String text) {
        this.searchBox.click();
        this.waitForSuggestionsListToBeVisible();
        this.searchBox.sendKeys(text);
        return this;
    }

    public StartupView tapSuggestion(String text) {
        this.searchSuggestions.get(this.getSuggestions().indexOf(text)).click();
        return this;
    }

    public StartupView tapSuggestion(int index) {
        this.searchSuggestions.get(index).click();
        return this;
    }

    public void waitForSuggestionsListToBeVisible() {
        this.waitFor(Duration.ofSeconds(10), ExpectedConditions.visibilityOf(this.suggestionsList));
    }

    public StartupView waitForSuggestionsToContain(String text) {
        final StartupView view = this;
        ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl WebDriver webDriver) {
                return view.searchSuggestionTexts.stream().anyMatch(e -> e.getText().contains(text)) ? true : null;
            }
        };
        this.waitFor(Duration.ofSeconds(5), condition);
        return this;
    }
}
