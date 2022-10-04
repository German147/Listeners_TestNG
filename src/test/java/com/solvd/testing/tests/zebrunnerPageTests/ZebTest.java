package com.solvd.testing.tests.zebrunnerPageTests;

import com.solvd.testing.listener.ZebrunnerListener;
import com.solvd.testing.pages.SidePanelPage;
import com.solvd.testing.pages.ZebrunnerDeviceFarmPage;
import com.solvd.testing.tests.BaseTests;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Listeners({ZebrunnerListener.class})
public class ZebTest extends BaseTests {

    @Test
    public void testIntroductionLink() {
        SidePanelPage panelPage = homePage.clickOnBurgerButton();
        panelPage.clickOnPlatformLink();
        ZebrunnerDeviceFarmPage zebPage = panelPage.clickOnZebrunnerDevicePage();

        assertEquals(zebPage.getPageTitle(), "Zebrunner Device Farm", "The page was not opened");

    }
}
