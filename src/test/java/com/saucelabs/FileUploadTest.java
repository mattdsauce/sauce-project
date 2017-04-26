package com.saucelabs;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;


@RunWith(ConcurrentParameterized.class)
public class FileUploadTest implements SauceOnDemandSessionIdProvider {

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

    /**
     * @return a LinkedList containing String arrays representing the browser combinations the test should be run against. The values
     * in the String array are used as part of the invocation of the test constructor
     */
    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList browsers = new LinkedList();
        //browsers.add(new String[]{"Windows 8", "10", "internet explorer"});
        //browsers.add(new String[]{"Windows 7", "10", "internet explorer"});
        browsers.add(new String[]{"OS X 10.10", "8", "safari"});
        return browsers;
    }

    public FileUploadTest(String os, String version, String browser) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
    }

    public void elementHighlight(WebElement element) {
        for (int i = 0; i < 2; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element, "color: red; border: 3px solid red;");
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element, "");
        }
    }


    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        if (version != null) {
            capabilities.setCapability(CapabilityType.VERSION, version);
        }
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability("avoidProxy", true);

        capabilities.setCapability("name", "File Upload Test");
        driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
        driver.setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        sessionId = (driver.getSessionId()).toString();


    }


    @Test
    public void upload() throws Exception {
        driver.get("http://ucmprep.icontracts.com");

        // Company Name
        WebElement cName=driver.findElement(By.xpath("//html/body/form/table/tbody/tr[2]/td/table/tbody/tr/td/div/div[1]/table/tbody/tr[1]/td[2]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[2]/td[2]/table/tbody/tr[2]/td[2]/div/div/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[2]/td[2]/input"));
        cName.sendKeys("iContracts Regional Health");

        //UserName
        WebElement uname=driver.findElement(By.xpath("//html/body/form/table/tbody/tr[2]/td/table/tbody/tr/td/div/div[1]/table/tbody/tr[1]/td[2]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[2]/td[2]/table/tbody/tr[2]/td[2]/div/div/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[3]/td[2]/input"));
        uname.sendKeys("eureqaAdminFF");

        //Password
        WebElement upass=driver.findElement(By.xpath("//html/body/form/table/tbody/tr[2]/td/table/tbody/tr/td/div/div[1]/table/tbody/tr[1]/td[2]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[2]/td[2]/table/tbody/tr[2]/td[2]/div/div/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[4]/td[2]/input"));
        upass.sendKeys("A123456");

        //login
        WebElement login=driver.findElement(By.id("ctl00_MainContent_Login1_Button1"));
        login.click();

        WebElement entry=driver.findElement(By.xpath("//img[@alt='Direct Entry']"));
        Actions act=new Actions(driver);
        act.moveToElement(entry).build().perform();
        entry.click();

        //Sending data to input field
        WebElement doc=driver.findElement(By.id("ctl00_MainContent_ContractEnter1_txtDocumentTitle"));
        act.moveToElement(doc).build().perform();
        doc.sendKeys("TestingFileUpload");

        //sending the file
        WebElement fileUp=driver.findElement(By.id("ctl00_MainContent_ContractEnter1_AjaxFileUpload1_Html5InputFile"));
        fileUp.sendKeys("/Users/mattdunn/temp/neg37.jpg");

        //Clicking on the upload button
	/*
		This is the Element where we are facing problem.
	*/
        WebElement upload=driver.findElement(By.id("ctl00_MainContent_ContractEnter1_AjaxFileUpload1_UploadOrCancelButton"));
        //WebElement upload=driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_ContractEnter1_AjaxFileUpload1_UploadOrCancelButton\"]"));
        elementHighlight(upload);
        act.moveToElement(upload).build().perform();
        driver.executeScript("var tmp = arguments[0]; tmp.click()", upload);
        //upload.click();
        Thread.sleep(10000);

        WebElement status=driver.findElement(By.id("ctl00_MainContent_ContractEnter1_AjaxFileUpload1_FileStatusContainer"));
        Assert.assertEquals("File Uploaded.", status.getText());

        Thread.sleep(5000);

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
