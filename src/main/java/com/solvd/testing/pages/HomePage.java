package com.solvd.testing.pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class HomePage extends BasePage{
    public HomePage(WebDriver driver) {
        super(driver);
    }
    private final By burgerButton = By.xpath("//label[@class='md-header__button md-icon'][1]/*[local-name()='svg']");

    public SidePanelPage clickOnBurgerButton(){
      driver.findElement(burgerButton).click();
        TakesScreenshot ts = (TakesScreenshot) driver;
        File file = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file,new File("./screenshots/burgerButtonClick.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new SidePanelPage(driver);
    }
}
