package com.solvd.testing.tests;

import com.google.common.io.Files;
import com.solvd.testing.pages.HomePage;
import io.github.bonigarcia.wdm.webdriver.WebDriverBrowser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public  class BaseTests {

    private static final Logger LOGGER = LogManager.getLogger(BaseTests.class);

    protected WebDriverBrowser driverBrowser;

    WebDriver driver;

    protected HomePage homePage;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        LOGGER.info("Opening remote driver");
        ChromeOptions chromeOptions = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "rcs/chromedriver-mac");

        driver = new ChromeDriver(new ChromeOptions());
        driver.manage().window().fullscreen();
        driver.get("https://zebrunner.com/documentation/integrations/");
        homePage = new HomePage(driver);
    }

    @AfterClass
    public void clean() {
        driver.quit();
    }

    @AfterMethod
    public void takeScreenshot(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                Files.move(screenshot, new File("rcs/screenshots/", result.getName() + ".png"));
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

}
