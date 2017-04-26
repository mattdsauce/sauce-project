package com.saucelabs.appium;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.saucelabs.saucerest.SauceREST;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;


public class AndroidAppTest implements SauceOnDemandSessionIdProvider {

    private AppiumDriver<WebElement> driver;

    private String sessionId;

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    public @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    String sauceUserName = authentication.getUsername();
    String sauceAccessKey = authentication.getAccessKey();

    /**
     * Sets up appium.  You will need to either explictly set the sauce username/access key variables, or set
     * SAUCE_USERNAME and SAUCE_ACCESS_KEY environment variables to reference your Sauce account details.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        //HttpResponse<JsonNode> response = uploadApp("/Users/mattdunn/temp/android-debug.apk");
        //System.out.println("App upload response: " + response.getStatus() + " " + response.getStatusText());

        String appPath = "/Users/mattdunn/temp/android-debug.apk";

        // use SauceREST to upload app to sauce storage
        SauceREST r = new SauceREST(sauceUserName, sauceAccessKey);
        File f = new File(appPath);
        String response = r.uploadFile(f, "android-debug.apk", true);
        System.out.println("App upload response: " + response);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("deviceName", "Android GoogleAPI Emulator");
        capabilities.setCapability("deviceOrientation","portrait");
        capabilities.setCapability("appiumVersion", "1.6.3");
        //capabilities.setCapability("automationName", "Selendroid");
        capabilities.setCapability("name", "Android App Test");
        capabilities.setCapability("app", "sauce-storage:android-debug.apk");
        //capabilities.setCapability("app", "sauce-storage:ApiDemos-debug.apk");
        //capabilities.setCapability("appPackage", "io.appium.android.apis");
        //capabilities.setCapability("appActivity", ".ApiDemos");
        //capabilities.setCapability("javascriptEnabled", true);

        driver = new AndroidDriver<>(new URL(MessageFormat.format("http://{0}:{1}@ondemand.saucelabs.com:80/wd/hub", sauceUserName, sauceAccessKey)),
                capabilities);
        this.sessionId = driver.getSessionId().toString();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }


    @Test
    public void apiDemo() throws InterruptedException, UnirestException {

        WebElement el = driver.findElementByClassName("android.widget.TextView");

        Thread.sleep(10000);
    }

    public HttpResponse<JsonNode> uploadApp(String filepath) throws UnirestException {

        HttpResponse<JsonNode> jsonResponse = Unirest.post("https://saucelabs.com/rest/v1/storage/mattdsauce/android-debug.apk?overwrite=true")
                .basicAuth(sauceUserName, sauceAccessKey)
                .header("Content-Type", "application/octet-stream")
                .field("file", new File(filepath))
                .asJson();

        return jsonResponse;
    }

    public String getSessionId() {
        return sessionId;
    }
}