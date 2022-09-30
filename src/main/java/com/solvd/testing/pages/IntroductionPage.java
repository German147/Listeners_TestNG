package com.solvd.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IntroductionPage extends BasePage {
    public IntroductionPage(WebDriver driver) {
        super(driver);
    }

    private By introductionTitle = By.xpath("//h1[contains(.,'Meet Zebrunner')]");

    public String getPageTitle() {
        return driver.findElement(introductionTitle).getText();
    }


}
