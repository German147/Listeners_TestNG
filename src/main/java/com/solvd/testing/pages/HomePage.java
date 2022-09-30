package com.solvd.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{
    public HomePage(WebDriver driver) {
        super(driver);
    }

    private By introductionLink = By.xpath("//a [contains(.,'Introduction')] ");
    public IntroductionPage clickOnIntroductionLink(){
        driver.findElement(introductionLink).click();
        return new IntroductionPage(driver);
    }
}
