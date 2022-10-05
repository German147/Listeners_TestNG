package com.solvd.testing.pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class SidePanelPage extends BasePage {
    public SidePanelPage(WebDriver driver) {
        super(driver);
    }

    private final By platFormLink = By.xpath("//li/label[contains(.,'Testing platforms')]");
    private final By zebRunnerDeviceLink = By.xpath("//li/a[contains(.,'Zebrunner Device Farm')]");

    public void clickOnPlatformLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(platFormLink).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(zebRunnerDeviceLink)));
        TakesScreenshot ts = (TakesScreenshot) driver;
        File file = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file,new File("./screenshots/Image1.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ZebrunnerDeviceFarmPage clickOnZebrunnerDevicePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(zebRunnerDeviceLink).click();
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(zebRunnerDeviceLink)));
        TakesScreenshot ts = (TakesScreenshot) driver;
        File file = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file,new File("./screenshots/OpenedDevice.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ZebrunnerDeviceFarmPage(driver);
    }

}
