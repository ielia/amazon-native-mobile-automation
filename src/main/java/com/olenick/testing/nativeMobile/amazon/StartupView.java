package com.olenick.testing.nativeMobile.amazon;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Move menu panel and all its elements to its own View.
public class StartupView extends View<StartupView> {
    private static final Logger log = LoggerFactory.getLogger(StartupView.class);

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/chrome_action_bar_burger_icon")
    private WebElement menuBurger;

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/gno_menu_container")
    private WebElement menuPanel;

    @AndroidFindBys({
            @AndroidBy(id = "com.amazon.mShop.android.shopping:id/gno_menu_container"),
            @AndroidBy(id = "com.amazon.mShop.android.shopping:id/anp_drawer_item")
    })
    private List<WebElement> menuLinks;

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text")
    private WebElement searchBox;

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/iss_search_suggestions_list_view")
    private WebElement suggestionsList;

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/iss_search_dropdown_item_suggestions")
    private List<WebElement> searchSuggestions;

    @AndroidFindBys({
            @AndroidBy(id = "com.amazon.mShop.android.shopping:id/iss_search_dropdown_item_suggestions"),
            @AndroidBy(id = "com.amazon.mShop.android.shopping:id/iss_search_dropdown_item_text")
    })
    private List<WebElement> searchSuggestionTexts;

    public StartupView(AppiumDriver driver) {
        super(driver);
    }

    @Override
    public StartupView waitToBeLoaded() {
        this.waitFor(Duration.ofSeconds(30), ExpectedConditions.elementToBeClickable(this.searchBox));
        return this;
    }

    public List<String> getSuggestions() {
        return this.searchSuggestionTexts.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean isSuggestionsListDisplayed() {
        return this.suggestionsList.isDisplayed();
    }

    public StartupView openMenu() {
        this.menuBurger.click();
        return this;
    }

    public StartupView searchFor(String text) {
        this.searchBox.click();
        this.waitForSuggestionsListToBeVisible();
        this.searchBox.sendKeys(text);
        return this;
    }

    public CustomerServiceView tapMenuItem(String linkText) {
        //noinspection OptionalGetWithoutIsPresent
        this.menuLinks.stream().filter(
                e -> e.findElement(By.className("android.widget.TextView")).getText().contains(linkText)
        ).findFirst().get().click();
        return new CustomerServiceView(this.driver);
    }

    public StartupView tapSuggestion(String text) {
        this.searchSuggestions.get(this.getSuggestions().indexOf(text)).click();
        return this;
    }

    public SearchResultsView tapSuggestion(int index) {
        this.searchSuggestions.get(index).click();
        return new SearchResultsView(this.driver);
    }

    @SuppressWarnings("UnusedReturnValue")
    public StartupView waitForMenuToBeOpen() {
        this.waitFor(Duration.ofSeconds(2), ExpectedConditions.visibilityOf(this.menuPanel));
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public StartupView waitForSuggestionsListToBeVisible() {
        this.waitFor(Duration.ofSeconds(10), ExpectedConditions.visibilityOf(this.suggestionsList));
        return this;
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
