package com.saucelabs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.junit.Assert.assertEquals;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WorkingSauce {

    public static final String USERNAME = System.getenv("SAUCE_USERNAME");
    public static final String ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
    public static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";

    private WebDriver driver;

	/**
	* Task I: Update the test code so when it runs, the test clicks the "I am a link" link.
	*
	* Task II - Comment out the code from Task I. Update the test code so when it runs, 
	* the test is able to write "Sauce" in the text box that currently says "I has no focus".
	*
	* Task III - Update the test code so when it runs, it adds an email to the email field, 
	* adds text to the comments field, and clicks the "Send" button.
	* Note that email will not actually be sent!
	*
	* Task IV - Add a capability that adds a tag to each test that is run.
        * See this page for instructions: https://wiki.saucelabs.com/display/DOCS/Test+Configuration+Options
	*/

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setCapability("platform", "Windows 7");
        caps.setCapability("browserName", "chrome");
        caps.setCapability("version", "48");
        caps.setCapability("name", "Guinea-Pig Sauce");

        String[] tags = {"task1", "task2", "task3"};
        //caps.setCapability("tags", ["tag1", "tag2", "tag3"])
        //List<String> tags = new ArrayList<String>();
        //tags.add("tags1");
        //tags.add("tags2");
        //tags.add("tags3");
        caps.setCapability("tags", tags);
        driver = new RemoteWebDriver(new URL(URL), caps);
    }

    @Test
    public void loadpage() throws Exception {

        /**
          * Goes to Sauce Lab's guinea-pig page and verifies the title
          */

        driver.get("https://saucelabs.com/test/guinea-pig");
        assertEquals("I am a page title - Sauce Labs", driver.getTitle());

        // Task I
        //
        //Click on the link
        //WebElement element = driver.findElement(By.linkText("i am a link"));
        //element.click();
        // Make sure that we are on the right page
        //assertEquals("I am another page title - Sauce Labs", driver.getTitle());
        //
        // Task II
        // Find the "i has no focus" textbox, clear its contents,
        // and replace the text with "Sauce"
        WebElement text = driver.findElement(By.name("i_am_a_textbox"));
        //Clear the text field
        text.clear();
        //Enter Sauce into the now empty text field
        text.sendKeys("Sauce");
        //Verify that the text field has Sauce in it
        assertEquals(text.getAttribute("value"), "Sauce");
        // Task III
        //Find the Email text field and enter text
        driver.findElement(By.id("fbemail")).sendKeys("test@example.com");
        //Find the Comments textarea and enter text
        driver.findElement(By.id("comments")).sendKeys("Test comments");
        //Click the Send button
        driver.findElement(By.name("submit")).click();
        //assertEquals("Your comments: Test comments", driver.findElement(By.id("comments")).getText());

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
