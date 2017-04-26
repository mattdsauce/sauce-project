package com.saucelabs.appium;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.sikuli.basics.Debug;
import org.sikuli.script.Finder;
import org.sikuli.script.Match;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;


import static org.junit.Assert.assertNotNull;

/**
 * Simple test which demonstrates how to run an <a href="https://github.com/appium/appium">Appium</a>
 * using <a href="http://saucelabs.com">Sauce Labs</a>.
 *
 * This test also includes the <a href="https://github.com/saucelabs/sauce-java/tree/master/junit">Sauce JUnit</a> helper classes, which will use the Sauce REST API to mark the Sauce Job as passed/failed.
 *
 * In order to use the {@link SauceOnDemandTestWatcher}, the test must implement the {@link SauceOnDemandSessionIdProvider} interface.
 *
 * <p/>
 * The test relies on SAUCE_USER_NAME and SAUCE_ACCESS_KEY environment variables being set which reference
 * the Sauce username/access key.
 *
 * @author Ross Rowe
 */
public class iOSLegoAppTest implements SauceOnDemandSessionIdProvider {

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

    /**
     * Sets up appium.  You will need to either explictly set the sauce username/access key variables, or set
     * SAUCE_USERNAME and SAUCE_ACCESS_KEY environment variables to reference your Sauce account details.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        String sauceUserName = authentication.getUsername();
        String sauceAccessKey = authentication.getAccessKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "10.0");
        capabilities.setCapability("deviceName", "iPad Simulator");
        capabilities.setCapability("deviceOrientation", "landscape");
        //capabilities.setCapability("appiumVersion", "1.5.3");
        capabilities.setCapability("autoAcceptAlerts", "true");
        capabilities.setCapability("name", "iOS Lego App Test");
        //capabilities.setCapability("app", "sauce-storage:WeDoApp.zip");
        capabilities.setCapability("app", "/Users/mattdunn/temp/WeDoApp.zip");

        //driver = new IOSDriver<WebElement>(new URL(MessageFormat.format("http://{0}:{1}@ondemand.saucelabs.com:80/wd/hub", sauceUserName, sauceAccessKey)), capabilities);
        driver = new IOSDriver<WebElement>(new URL("http://localhost:4723/wd/hub"), capabilities);
        this.sessionId = driver.getSessionId().toString();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }


    @Test
    public void checkApp() throws Exception {

        tapOnImage("/Users/mattdunn/temp/OK_EULA4.png");

        Thread.sleep(5000);
    }

    /**
     * clickByImage is the main method that you should be using to tap on elements on screen using an image.
     * @param targetImgPath takes path to the screenshot of an element that you want to find.
     */
    boolean tapOnImage(String targetImgPath) throws IOException {
        Point2D coords = getCoords(takeScreenshot(), targetImgPath);
        System.out.println ("\nClick X: "+coords.getX() + " Y: " + coords.getY());
        if ((coords.getX() >= 0) && (coords.getY() >= 0)) {
            //make sure we're in native context before performing tap
            String originalContext = driver.getContext();
            System.out.println("\noriginal context: " + originalContext);
            if (originalContext != "NATIVE_APP") {
                driver.context("NATIVE_APP");
            }
            //new TouchAction(driver).longPress((int) coords.getX(),(int) coords.getY()).perform();
            //adding 20 to the Y coordinate - https://github.com/appium/appium/issues/7957
            int y = (int) coords.getY();
            //y = y+20;
            new TouchAction(driver).tap((int) coords.getX(),y).perform();
            //driver.tap(1, (int) coords.getX(), (int) coords.getY(), 500);
            return true;
        }
        throw new ElementNotVisibleException("Element not found - " + targetImgPath);
    }

    /**
     * getCoords returns the coordinates of the FIRST element that matches the specified
     * @param baseImg is the screenshot of the device
     * @param targetImgPath is the image of the element that you want to find
     * @return coordinates of the centre of the element found as Point2D
     */
    Point2D getCoords(BufferedImage baseImg, String targetImgPath) {
        Match m;
        Finder f = new Finder(baseImg);
        Point2D coords = new Point2D.Double(-1, -1);

        f.find(targetImgPath);
        if (f.hasNext()) {
            m = f.next();
            coords.setLocation(m.getTarget().getX(), m.getTarget().getY());
        }
        return coords;
    }

    /**
     * Convenience method that takes a screenshot of the device and returns a BufferedImage for further processing.
     * @return screenshot from the device as BufferedImage
     */
    BufferedImage takeScreenshot() throws IOException {
        BufferedImage bufferedImage = null;
        //Debug.setDebugLevel(3);
        if (driver != null) {
            File scrFile = driver.getScreenshotAs(OutputType.FILE);
            //FileUtils.copyFile(scrFile, new File("/Users/mattdunn/temp/screenshot.png"));
        //Screen screen = new Screen()
        //File srcFile = screen.capture().getImage()
            try {
                bufferedImage = ImageIO.read(scrFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new ElementNotVisibleException("Element not found - ");
        }
        return bufferedImage;
    }

    public String getSessionId() {
        return sessionId;
    }
}