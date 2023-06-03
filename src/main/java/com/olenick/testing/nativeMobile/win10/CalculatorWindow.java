package com.olenick.testing.nativeMobile.win10;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WindowsFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;

public class CalculatorWindow {
    protected final AppiumDriver driver;

    @WindowsFindBy(accessibility="CalculatorResults")
    protected WebElement results;
    @FindBy(name="Divide by")
    protected WebElement divide;
    @FindBy(name="Equals")
    protected WebElement equals;
    @FindBy(name="Minus")
    protected WebElement minus;
    @FindBy(name="Multiply by")
    protected WebElement multiply;
    @FindBy(name="Plus")
    protected WebElement plus;
    @FindBy(name="Zero")
    protected WebElement num0;
    @FindBy(name="One")
    protected WebElement num1;
    @FindBy(name="Two")
    protected WebElement num2;
    @FindBy(name="Three")
    protected WebElement num3;
    @FindBy(name="Four")
    protected WebElement num4;
    @FindBy(name="Five")
    protected WebElement num5;
    @FindBy(name="Six")
    protected WebElement num6;
    @FindBy(name="Seven")
    protected WebElement num7;
    @FindBy(name="Eight")
    protected WebElement num8;
    @FindBy(name="Nine")
    protected WebElement num9;

    protected final Map<String, WebElement> buttons = new HashMap<>();

    public CalculatorWindow(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
        initializeButtons();
    }

    protected void initializeButtons() {
        buttons.put("/", divide);
        buttons.put("=", equals);
        buttons.put("-", minus);
        buttons.put("*", multiply);
        buttons.put("0", num0);
        buttons.put("1", num1);
        buttons.put("2", num2);
        buttons.put("3", num3);
        buttons.put("4", num4);
        buttons.put("5", num5);
        buttons.put("6", num6);
        buttons.put("7", num7);
        buttons.put("8", num8);
        buttons.put("9", num9);
        buttons.put("+", plus);
    }

    public CalculatorWindow inputNumber(Integer number) {
        for (String digit : number.toString().split("")) {
            buttons.get(digit).click();
        }
        return this;
    }

    public CalculatorWindow push(String buttonFace) {
        buttons.get(buttonFace).click();
        return this;
    }

    public Double getResults() {
        // TODO: See why it doesn't find any children.
        String displayText = results.getText();
        return Double.parseDouble(displayText.replace("Display is ", ""));
    }
}
