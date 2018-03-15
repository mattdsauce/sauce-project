package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@RunWith(ConcurrentParameterized.class)
public class Bhargava45498Test implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(System.getenv("SAUCE_USERNAME"),
            System.getenv("SAUCE_ACCESS_KEY"));

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    /**
     * Represents the browser to be used as part of the test run.
     */
    private String browser;
    /**
     * Represents the operating system to be used as part of the test run.
     */
    private String os;
    /**
     * Represents the version of the browser to be used as part of the test run.
     */
    private String version;
    /**
     * Instance variable which contains the Sauce Job Id.
     */
    private String sessionId;

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private RemoteWebDriver driver;

    /**
     * Constructs a new instance of the test.  The constructor requires three string parameters, which represent the operating
     * system, version and browser to be used when launching a Sauce VM.  The order of the parameters should be the same
     * as that of the elements within the {@link #browsersStrings()} method.
     * @param os
     * @param version
     * @param browser
     */
    public Bhargava45498Test(String os, String version, String browser) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
    }

    /**
     * @return a LinkedList containing String arrays representing the browser combinations the test should be run against. The values
     * in the String array are used as part of the invocation of the test constructor
     */
    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList browsers = new LinkedList();
        browsers.add(new String[]{"Windows 8.1", "11", "internet explorer"});
        //browsers.add(new String[]{"Windows 10", "15", "MicrosoftEdge"});
        //browsers.add(new String[]{"Windows 10", "latest", "chrome"});
        return browsers;
    }


    /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined by the {@link #browser},
     * {@link #version} and {@link #os} instance variables, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @throws Exception if an error occurs during the creation of the {@link RemoteWebDriver} instance.
     */
    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        if (version != null) {
            capabilities.setCapability(CapabilityType.VERSION, version);
        }
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability("screenResolution", "1280x1024");
        capabilities.setCapability("avoidProxy", true);
        //capabilities.setCapability("seleniumVersion", "3.7.1");
        capabilities.setCapability("name", "Bhargava 45498 Test: " + browser + " " + version + ", " + os);
        this.driver = new RemoteWebDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com/wd/hub"),
                //new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@localhost:4445/wd/hub"),
                capabilities);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

    }

    /**
     * Runs a simple test
     * @throws Exception
     */
    @Test
    public void doTest() throws Exception {

        driver.manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);

        System.out.println("Open Website");
        driver.get("https://www.interflora.ie.uat2.interfloratest.co.uk/");
        Thread.sleep(5000);

        System.out.println("Check Occasions visibility");
        System.out.println(driver.findElement(By.xpath("//label[contains(text(),'Occasions')]")).isDisplayed());
        Thread.sleep(5000);

/*		Actions action = new Actions(driver);
		WebElement occasion = driver.findElement(By.xpath("//label[contains(text(),'Occasions')]"));
		action.moveToElement(occasion).perform();
		System.out.println("mouseOver happened on Occasions");
		Thread.sleep(3000);*/

        System.out.println("Mouse Over");
        driver.get("https://www.interflora.ie.uat2.interfloratest.co.uk/");
        Thread.sleep(5000);

        System.out.println("Check Occasions visibility");
        System.out.println(driver.findElement(By.xpath("//label[contains(text(),'Occasions')]")).isDisplayed());
        Thread.sleep(5000);

/*		Actions action = new Actions(driver);
		WebElement occasion = driver.findElement(By.xpath("//label[contains(text(),'Occasions')]"));
		action.moveToElement(occasion).perform();
		System.out.println("mouseOver happened on Occasions");
		Thread.sleep(3000);*/

        System.out.println("Mouse Over");
        WebElement occasion = driver.findElement(By.xpath("//label[contains(text(),'Occasions')]"));
        String javaScript = "var evObj = document.createEvent('MouseEvents');evObj.initMouseEvent('mouseover',true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);arguments[0].dispatchEvent(evObj);";
        ((JavascriptExecutor) driver).executeScript(javaScript, occasion);

        driver.findElement(By.linkText("Anniversary Flowers")).click();
        System.out.println("Clicked on Anniversary flowers");
        Thread.sleep(5000);

        driver.findElement(By.xpath("//a[@title='Vibrant Hand-tied']")).click();
        System.out.println("Clicked on the subcategory");
        Thread.sleep(3000);

        System.out.println(driver.findElement(By.xpath("//div[@class='breadcrumbs']/span/following-sibling::a[@class='breadcrumb_link'][contains(text(),'Anniversary Flowers')]")).isDisplayed());
        System.out.println("click on next for choose your size");
        driver.findElement(By.xpath("//div[@id='sku_options']//input[@value='Next']")).click();
        Thread.sleep(3000);

        Select Country = new Select(driver.findElement(By.id("county_list")));
        Country.selectByVisibleText("Cork");
        System.out.println("Country selected");
        System.out.println(driver.getCapabilities().toString());

        Select town = new Select(driver.findElement(By.id("town_list")));
        town.selectByVisibleText("Cork");
        System.out.println("town selected");
        Thread.sleep(2000);

        System.out.println("Click on next for delivery");
        driver.findElement(By.xpath("//div[@id='delivery_address']//input[@value='Next']")).click();
        Thread.sleep(2000);

        System.out.println("Click on Delivered Today ?");
        driver.findElement(By.id("nextAvailableDate")).click();

        boolean ele = driver.findElement(By.xpath("//div[@id='delivery_date']//input[@id='delivery_date_submit']")).isDisplayed();
        System.out.println(ele);

        if(ele==false)
        {
            driver.findElement(By.xpath("//div[@class='listBoxSelected list']")).click();
            Thread.sleep(2000);

            driver.findElement(By.xpath("//div[@id='listView_list']/div[@class='list_item list_serviceItem listrow']/label[contains(text(),'Delivery Today (8am - 6pm)')]")).click();
            //driver.findElement(By.xpath("//div[@id='listView_list']/div[@class='list_item list_serviceItem listrow']/label[contains(text(),'Standard Delivery (8am - 6pm)')]")).click();
            Thread.sleep(3000);
        }


/*		driver.findElement(By.id("//div[@id='anotherDate']//td[contains(text,'Another Date')]")).click();
		driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']//*[text()='17']/parent::td//following::td[not(contains(@class,'ui-datepicker-unselectable')) and not(contains(@class,'week-end'))][1]/a")).click();
		Thread.sleep(3000);

		driver.findElement(By.xpath("//div[@class='listBoxSelected list']")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//div[@id='listView_list']/div[@class='list_item list_serviceItem listrow'][contains(text(),'Standard Delivery (8am - 6pm)'')]")).click();
		Thread.sleep(3000);*/

        driver.findElement(By.xpath("//div[@id='delivery_date']//input[@id='delivery_date_submit']")).click();
        Thread.sleep(3000);

        driver.findElement(By.xpath("//input[@value='Add to Cart']")).click();
        System.out.println("Clicked on order now");
        Thread.sleep(2000);

        driver.findElement(By.xpath("//td[@id='sd-rheader']/input[@alt='Continue to Shopping Basket']")).click();
        System.out.println("clicked on continue to shopping basket");
        Thread.sleep(3000);


        driver.findElement(By.id("coupon_code")).sendKeys("DONTLEAVE");
        System.out.println("entered the discount coupon");
        Thread.sleep(3000);

        driver.findElement(By.id("submit_coupon")).click();
        System.out.println("clicked on submit button");
        Thread.sleep(10000);

    }

    /**
     * Closes the {@link WebDriver} session.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    /**
     *
     * @return the value of the Sauce Job id.
     */
    @Override
    public String getSessionId() {
        return sessionId;
    }
}
