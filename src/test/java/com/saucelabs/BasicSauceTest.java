package com.saucelabs;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class BasicSauceTest {

    public static final String USERNAME = "taklaus44";
    public static final String ACCESS_KEY = "b002875c-539a-46cd-ac24-78f636023a8c";
    public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";

    @Test
    public  void doTest() throws Exception {

        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("version", "latest");

        WebDriver driver = new RemoteWebDriver(new URL(URL), caps);

        /**
         * Goes to Sauce Lab's guinea-pig page and prints title
         */

        driver.get("https://saucelabs.com/test/guinea-pig");
        System.out.println("title of page is: " + driver.getTitle());

        driver.quit();
    }
}