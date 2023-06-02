package com.olenick.testing.nativeMobile.win10;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WindowsFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;

public class CalculatorWindow {
    protected final AppiumDriver driver;

    @WindowsFindBy(windowsAutomation="CalculatorResults")
    protected WebElement results;
    @WindowsFindBy(windowsAutomation="divideButton")
    protected WebElement divide;
    @WindowsFindBy(windowsAutomation="equalButton")
    protected WebElement equal;
    @WindowsFindBy(windowsAutomation="minusButton")
    protected WebElement minus;
    @WindowsFindBy(windowsAutomation="multiplyButton")
    protected WebElement multiply;
    @WindowsFindBy(windowsAutomation="plusButton")
    protected WebElement plus;
    @WindowsFindBy(windowsAutomation="num0Button")
    protected WebElement num0;
    @WindowsFindBy(windowsAutomation="num1Button")
    protected WebElement num1;
    @WindowsFindBy(windowsAutomation="num2Button")
    protected WebElement num2;
    @WindowsFindBy(windowsAutomation="num3Button")
    protected WebElement num3;
    @WindowsFindBy(windowsAutomation="num4Button")
    protected WebElement num4;
    @WindowsFindBy(windowsAutomation="num5Button")
    protected WebElement num5;
    @WindowsFindBy(windowsAutomation="num6Button")
    protected WebElement num6;
    @WindowsFindBy(windowsAutomation="num7Button")
    protected WebElement num7;
    @WindowsFindBy(windowsAutomation="num8Button")
    protected WebElement num8;
    @WindowsFindBy(windowsAutomation="num9Button")
    protected WebElement num9;

    protected final Map<String, WebElement> buttons = new HashMap<String, WebElement>() {{
        put("/", divide);
        put("-", minus);
        put("*", multiply);
        put("0", num0);
        put("1", num1);
        put("2", num2);
        put("3", num3);
        put("4", num4);
        put("5", num5);
        put("6", num6);
        put("7", num7);
        put("8", num8);
        put("9", num9);
        put("+", plus);
    }};

    public CalculatorWindow(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
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
        return Double.parseDouble(results.getText());
    }
}
