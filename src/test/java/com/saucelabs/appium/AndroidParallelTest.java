package com.saucelabs.appium;

import com.saucelabs.junit.ConcurrentParameterized;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@RunWith(ConcurrentParameterized.class)
public class AndroidParallelTest {

    /* Sets the test name to the name of the test method. */
    @Rule
    public TestName testName = new TestName();

    /* Takes care of sending the result of the tests over to TestObject. */
    @Rule
    public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();

    /**
     * Represents the device to be used for the test run.
     */
    private String device;

    /**
     * Represents the DC URL to be used for the test run.
     */
    private String dcURL;

    private static String eudc = "https://eu1.appium.testobject.com/wd/hub";
    private static String usdc = "https://us1.appium.testobject.com/wd/hub";

    private SimpleDateFormat time_formatter = new SimpleDateFormat("HHmmssSSS");

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private AppiumDriver<WebElement> driver;

    /**
     * Constructs a new instance of the test.  The constructor requires two string parameters, which represent the device
     * and DC URL to be used when launching the test. The order of the parameters should be the same
     * as that of the elements within the {@link #testsStrings()} method.
     * @param device
     * @param dcURL
     */
    public AndroidParallelTest(String device, String dcURL) {
        super();
        this.device = device;
        this.dcURL = dcURL;
    }

    /**
     * @return a LinkedList containing String arrays representing the device and DC combinations the test should be run against.
     * The values in the String array are used as part of the invocation of the test constructor
     */
    @ConcurrentParameterized.Parameters
    public static LinkedList testsStrings() {
        LinkedList tests = new LinkedList();
        tests.add(new String[]{"Motorola_Moto_G_3rd_gen_real", eudc});
        tests.add(new String[]{"Samsung_Galaxy_J5_real", eudc});
        tests.add(new String[]{"Motorola_Moto_G_3rd_gen_real", usdc});
        tests.add(new String[]{"Samsung_Galaxy_J5_real", usdc});
        return tests;
    }

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("testobject_api_key", "366E6A2F39B94F12BB30742DE3571761"); //csteam/calculator2
        //capabilities.setCapability("deviceName", device);\
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "6.0");
        //capabilities.setCapability("testobject_cache_device", true);
        //capabilities.setCapability("testobject_appium_version", "1.6.4");
        driver = new AndroidDriver<>(new URL(dcURL), capabilities);

        /* IMPORTANT! We need to provide the Watcher with our initialized AppiumDriver */
        resultWatcher.setRemoteWebDriver(driver);
    }

    private void takeScreenshot(String path) throws IOException {
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path));
    }


    @Test
    public void doTest() throws InterruptedException, IOException {

        // do nothing much in this test - just want to verify that tests start
        takeScreenshot("/Users/mattdunn/temp/scr" + time_formatter.format(System.currentTimeMillis()) + ".jpg");

        Thread.sleep(10000);
    }

}