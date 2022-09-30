package com.solvd.testing.tests.zebrunnerPageTests;

import com.solvd.testing.pages.IntroductionPage;
import com.solvd.testing.tests.BaseTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ZebTest extends BaseTests {


    @Test
    public void testIntroductionLink(){
        IntroductionPage introPage = homePage.clickOnIntroductionLink();
        String introPagePageTitle = introPage.getPageTitle();

        assertEquals(introPagePageTitle,"Meet Zebrunner", "The page was not opened");

    }
}
