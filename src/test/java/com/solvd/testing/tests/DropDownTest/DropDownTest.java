package com.solvd.testing.tests.DropDownTest;

import com.solvd.testing.pages.DropdownPage;
import com.solvd.testing.pages.ProductListPage;
import com.solvd.testing.tests.BaseTests;
import org.testng.annotations.Test;


import static org.testng.Assert.assertEquals;

public class DropDownTest extends BaseTests {


    @Test
    public void testSelectedOptions() {

        DropdownPage dropdownPage = homePage.clickOnDropdownLink();
        ProductListPage productListPage = dropdownPage.clickOnProductList();
        String pageTitle = productListPage.getPageTitle();

        assertEquals(pageTitle,"Listado de NFT publicados", "The page was not opened");
    }
}
