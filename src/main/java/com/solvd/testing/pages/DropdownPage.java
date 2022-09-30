package com.solvd.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DropdownPage extends BasePage{
    public DropdownPage(WebDriver driver) {
        super(driver);
    }
    private By productList = By.xpath("//a[contains(., 'Lista de Productos')] ");

    public ProductListPage clickOnProductList(){
        driver.findElement(productList).click();
        return new ProductListPage(driver);
    }



}
