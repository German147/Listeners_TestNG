package com.solvd.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{
    public HomePage(WebDriver driver) {
        super(driver);
    }
    private By dropdown = By.xpath("//a[@href='#']/i");

    public DropdownPage clickOnDropdownLink() {
       driver.findElement(dropdown).click();
        return new DropdownPage(driver);
    }
}
