package com.saucelabs.appium;

import com.saucelabs.helper.RDCAssetHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AndroidAppMRDCTest {

    /* Sets the test name to the name of the test method. */
    @Rule
    public TestName testName = new TestName();

    /* Takes care of sending the result of the tests over to TestObject. */
    @Rule
    public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private AppiumDriver<WebElement> driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("testobject_api_key", "366E6A2F39B94F12BB30742DE3571761"); //csteam/calculator2
        //capabilities.setCapability("testobject_api_key", "68322B53094644A58EE1E685D2AE1B08"); //legotest/lego-boost-android
        //capabilities.setCapability("testobject_api_key", "4495AD9FF5854E1489E645ADA9DDA21C"); //adizaharie/dlr-app-debug-4-11-nr-20180323-1
        //capabilities.setCapability("testobject_api_key", "5F945828EA2948DD9B75721667CA02E8"); //zonky/zonky-b-1043-dev
        //capabilities.setCapability("testobject_api_key", "6D1132B7EDAD466C81F569539C247C3D"); //csteam/apricot-capico-admin
        //capabilities.setCapability("testobject_device", "Google_Pixel_real");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "8.1");
        //capabilities.setCapability("testobject_cache_device", true);
        //capabilities.setCapability("testobject_appium_version", "1.6.4");
        driver = new AndroidDriver<>(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);
    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }


    @Test
    public void doTest() throws InterruptedException, IOException {

        for (int x = 0; x < 2; x++) {
            testCommands();
            Thread.sleep(2000);
        }

    }

    public void testCommands() throws IOException {

        WebElement el = driver.findElementByClassName("android.widget.EditText");
        el.click();

        driver.getPageSource();

        takeScreenshot("/Users/mattdunn/temp/scr1.jpg");

    }

    @After
    public void tearDown() throws Exception {
        String apiUrl = driver.getCapabilities().getCapability("testobject_test_report_api_url").toString();
        System.out.println("Test report API URL: " + apiUrl);
        RDCAssetHelper rdchelper = new RDCAssetHelper("csteam", "366E6A2F39B94F12BB30742DE3571761", apiUrl);
        //rdchelper.getAssets();
        System.out.println(rdchelper.getReport());
    }

}