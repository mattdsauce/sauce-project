package com.saucelabs;


import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;

public class LocalChromeTest {

    @Test
    public void testChrome() throws InterruptedException {
        ChromeOptions opts = new ChromeOptions();

        HashMap<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "iPhone 8");
        opts.setExperimentalOption("mobileEmulation", mobileEmulation);

        opts.addArguments("start-maximized",
                "disable-webgl",
                "blacklist-webgl",
                "blacklist-accelerated-compositing",
                "disable-accelerated-2d-canvas",
                "disable-accelerated-compositing",
                "disable-accelerated-layers",
                "disable-accelerated-plugins",
                "disable-accelerated-video",
                "disable-accelerated-video-decode",
                "disable-gpu",
                "disable-infobars",
                "test-type");
        WebDriver chrome = new ChromeDriver(opts);

        chrome.get("https://cleanandclear.com/");

        Thread.sleep(2000);

        WebElement el = chrome.findElement(By.cssSelector("#touch-menu"));
        el.click();

        Thread.sleep(30000);

        chrome.close();
    }

}

