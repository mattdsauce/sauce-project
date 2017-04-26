package com.saucelabs;


import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class LocalChromeScreenshotTest {

    // start chromedriver in terminal with --verbose --log-path=chromedriver.log and use RemoteWebDriver
    //DesiredCapabilities caps = new DesiredCapabilities();
    //WebDriver driver = new RemoteWebDriver(new URL("http://localhost:9515"), caps);

    WebDriver driver = new ChromeDriver();

    public LocalChromeScreenshotTest() throws MalformedURLException {
    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }

    @Test
    public void testMethod() throws InterruptedException, IOException {
        driver.get("https://amazon.co.uk/");

        takeScreenshot("/Users/mattdunn/temp/scr1.jpg");

        List<WebElement> anchors = driver.findElements(By.tagName("a"));

        Thread.sleep(5000);

        driver.quit();
    }

}

