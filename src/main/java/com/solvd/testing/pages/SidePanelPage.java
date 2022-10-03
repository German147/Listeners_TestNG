package com.solvd.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    }

    public ZebrunnerDeviceFarmPage clickOnZebrunnerDevicePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(zebRunnerDeviceLink).click();
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(zebRunnerDeviceLink)));
        return new ZebrunnerDeviceFarmPage(driver);
    }

}
