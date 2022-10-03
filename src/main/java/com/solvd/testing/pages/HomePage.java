package com.solvd.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{
    public HomePage(WebDriver driver) {
        super(driver);
    }
    private final By burgerButton = By.xpath("//label[@class='md-header__button md-icon'][1]/*[local-name()='svg']");

    public SidePanelPage clickOnBurgerButton(){
      driver.findElement(burgerButton).click();
        return new SidePanelPage(driver);
    }
}
