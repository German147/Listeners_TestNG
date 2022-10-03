package com.solvd.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ZebrunnerDeviceFarmPage extends BasePage {
    public ZebrunnerDeviceFarmPage(WebDriver driver) {
        super(driver);
    }

    private final By pageTitle = By.id("zebrunner-device-farm");

    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }

}
