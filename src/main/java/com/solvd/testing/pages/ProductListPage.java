package com.solvd.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductListPage extends BasePage {
    public ProductListPage(WebDriver driver) {
        super(driver);
    }

    private By pageTitle = By.xpath("/html/body/main/p");

    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }
}
