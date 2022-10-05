package com.solvd.testing.tests;

import listeners.reporter.ReporterClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ReporterClass.class)
public class Test3 {
    private final static Logger LOGGER = LogManager.getLogger(Test3.class);
    @Test
    public void simpleWebTest() {

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");

        WebDriver driver = new ChromeDriver();

        driver.get("http://www.google.com");

        driver.manage().window().maximize();

        driver.close();

        LOGGER.info("This is the test function - Class Test Pack1");

    }

    @Test
    public void Test0() {
        Assert.assertTrue(true);
    }

    @Test
    public void f1() {
        Assert.assertTrue(false);
    }

    @Test
    public void f2() {
        Assert.assertTrue(true);
    }

    @Test
    public void f3() {
        Assert.assertTrue(false);
    }

    @Test(dependsOnMethods = "f1")
    public void f4() {
        Assert.assertTrue(true);
    }

}
