package com.saucelabs;


import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LocalChromeTest {

    @Test
    public void testPaste() throws InterruptedException {
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("enable-logging", "v=4", "start-fullscreen");
        WebDriver chrome = new ChromeDriver(opts);
        chrome.get("https://www.adobe.com/software/flash/about/");

        //Thread.sleep(3000);
        //chrome.get("http://www.google.com");
        //WebElement element = chrome.findElement(By.name("q"));

        //element.sendKeys("some text");
        //Thread.sleep(3000);

        //element.sendKeys(Keys.COMMAND + "a");
        //element.sendKeys(Keys.COMMAND + "c");
        //element.clear();
        //Thread.sleep(3000);

        //element.sendKeys(Keys.COMMAND + "v");
        //element.sendKeys(Keys.COMMAND + "v");
        //Thread.sleep(3000);

        //element.sendKeys("abc");

        Thread.sleep(3000);
        chrome.get("file:///Users/mattdunn/chrome_debug.log");
        Thread.sleep(30000);


        chrome.close();
    }

}

