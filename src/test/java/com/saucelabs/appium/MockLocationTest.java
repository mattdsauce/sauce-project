package com.saucelabs.appium;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testobject.appium.junit.TestObjectTestResultWatcher;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class MockLocationTest {
    private AppiumDriver driver;

    @Rule
    public TestObjectTestResultWatcher resultWatcher = new TestObjectTestResultWatcher();

    @Rule
    public TestName testName = new TestName();

    @Before
    public void connect() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //capabilities.setCapability("testobject_api_key", "C71CF006B7FA4385A0FD77DC2C363D21"); // navitasqa/bpme-uk-curlupload
        capabilities.setCapability("testobject_api_key", "CF5816B1F0764A0F98B7419E6A757E5D"); // csteam/bpmeandroid
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Samsung Galaxy S.*|LG.*|Google.*");
        //capabilities.setCapability("deviceName", "Samsung Galaxy S6.*");
        capabilities.setCapability("platformVersion", "7");
        capabilities.setCapability("phoneOnly", "true");
        capabilities.setCapability("tabletOnly", "false");
        //capabilities.setCapability("appiumVersion", "1.6.5");
        capabilities.setCapability("testobject_test_name", testName.getMethodName());
        capabilities.setCapability("testobject_suite_name", getClass().getName());
        this.driver = new AndroidDriver(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        resultWatcher.setRemoteWebDriver(driver);

        System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        System.out.println("Test report: "+driver.getCapabilities().getCapability("testobject_test_report_url"));
        System.out.println("Live view: "+driver.getCapabilities().getCapability("testobject_test_live_view_url"));
    }

    @Test
    public void mockLocationTest(){
        MobileElement loginButton = (MobileElement) driver.findElement(By.id("com.bp.mobile.bpme.uk:id/login_button"));
        loginButton.click();

        List<MobileElement> emailEditText = (List<MobileElement>) driver.findElements(By.xpath("//android.widget.EditText"));
        emailEditText.get(0).sendKeys("bptestobject+8@gmail.com");

        List<MobileElement> passwordEditText = (List<MobileElement>) driver.findElements(By.xpath("//android.widget.EditText"));
        passwordEditText.get(1).sendKeys("Mir01Mar");

        MobileElement loginButton2 = (MobileElement) driver.findElement(By.xpath("//android.widget.Button[@content-desc='Log in']"));
        loginButton2.click();

        MobileElement securityAnswer = (MobileElement) driver.findElement(By.id("com.bp.mobile.bpme.uk:id/answer_security_question_pin_edit_text"));
        securityAnswer.sendKeys("Rex");

        MobileElement doneButton = (MobileElement) driver.findElement(By.id("com.bp.mobile.bpme.uk:id/enter_mfa_done_button"));
        doneButton.click();

        MobileElement enableLocationButton = (MobileElement) driver.findElement(By.id("com.bp.mobile.bpme.uk:id/iamreadybutton"));
        enableLocationButton.click();

        MobileElement allowLocationButton = (MobileElement) driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"));
        allowLocationButton.click();

        MobileElement pageTitle = (MobileElement) driver.findElement(By.id("com.bp.mobile.bpme.uk:id/toolbar_text_title"));

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.textToBePresentInElement(pageTitle, "Pay for Fuel"));

        Location location = new Location(51.5044484, -0.105654, 0.0);
        driver.setLocation(location);

        MobileElement stationNameText = (MobileElement) driver.findElement(By.id("com.bp.mobile.bpme.uk:id/confirm_station_text_header"));
        wait.until(ExpectedConditions.textToBePresentInElement(stationNameText, "BP test 1 Southbank office"));

        MobileElement menuButton = (MobileElement) driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"));
        menuButton.click();

        MobileElement logOutMenuItem = (MobileElement) driver.findElement(By.xpath("//android.widget.CheckedTextView[@resource-id='com.bp.mobile.bpme.uk:id/design_menu_item_text' and @text='Sign out']"));
        logOutMenuItem.click();

        MobileElement bpmeLogo = (MobileElement) driver.findElement(By.id("com.bp.mobile.bpme.uk:id/bpme_image_view"));
        wait.until(ExpectedConditions.visibilityOf(bpmeLogo));
    }

}
