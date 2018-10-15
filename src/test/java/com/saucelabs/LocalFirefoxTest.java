package com.saucelabs;


import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

public class LocalFirefoxTest {

    private WebDriver driver;

    @Test
    public void testUpload() throws InterruptedException {

        System.setProperty("webdriver.gecko.driver", "/Users/mattdunn/dev/geckodrivers/0.21.0/geckodriver");

        driver = new FirefoxDriver();

        /*driver.get("http://the-internet.herokuapp.com/javascript_alerts");
        driver.findElements(By.cssSelector("button")).get(1).click();
        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.alertIsPresent());
        Thread.sleep(5000);
        driver.switchTo().alert().accept();*/

        driver.get("https://vcs1946:vcs1946@ccstore-test-z61a.oracleoutsourcing.com");

        WebElement el = waitForElementByXPath("//div[@class=\"navone\"]//following::a[@class=\"navone-link-main\"][contains(text(),'Women')]");

        hoverOverElement(el);

        el = waitForElementByXPath("(//a[contains(text(),'New for Women')])[2]");
        //el.click();
        jsClick(el);

        Thread.sleep(10000);



        //driver.quit();
    }

    public WebElement waitForElementByXPath(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        return driver.findElement(By.xpath(locator));
    }

    public void hoverOverElement(WebElement element) {
        try {
            //click(element);
            Actions action = new Actions(driver);
            Action mouseOver = action.moveToElement(element).build();
            mouseOver.perform();
        } catch (UnsupportedCommandException e) {
            System.out.println("Hover Action not supported, trying with JS");
            jsHoverOverElement(element);
        }
    }

    public void jsHoverOverElement(WebElement element) {
        String script = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";
        executeScriptOnElement(script, element);
        focus(element);
    }

    public void focus(WebElement element) {
        String script = "arguments[0].focus();return true;";
        executeScriptOnElement(script, element);
    }

    public void executeScriptOnElement(String script, WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(script, element);
    }

    public void jsClick(WebElement element) {
        String script = "arguments[0].click();";
        executeScriptOnElement(script, element);
    }

}

