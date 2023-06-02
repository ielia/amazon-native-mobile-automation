package com.olenick.testing.nativeMobile.win10;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WindowsFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotepadWindow {
    protected final AppiumDriver driver;

    // @WindowsFindBy(windowsAutomation = "CommandButton_7")
    @FindBy(name = "Don't Save")
    protected WebElement dontSaveButton;
    @WindowsFindBy(className = "Edit")
    protected WebElement editPane;
    // @WindowsFindBy(name = "File")
    // protected WebElement fileMenu;
    // @WindowsFindBys({ @WindowsBy(accessibility = "MenuBar"), @FindBy(name = "File") })
    @FindBy(name = "File")
    protected WebElement fileMenu;
    // @WindowsFindBys({ @WindowsBy(accessibility = "MenuBar"), @FindBy(name = "Exit") })
    @FindBy(name = "Exit")
    protected WebElement exitOption;

    public NotepadWindow(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public NotepadWindow sendText(String text) {
        editPane.sendKeys(text);
        return this;
    }

    public String getText() {
        return editPane.getText();
    }

    @SuppressWarnings("UnusedReturnValue")
    public NotepadWindow exitWithoutSaving() {
        // FIXME
        fileMenu.click();
        exitOption.click();
        dontSaveButton.click();
        return this;
    }
}
