package com.solvd.testing.tests;

import com.solvd.testing.listener.ZebrunnerListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners (ZebrunnerListener.class)
public class TestZebrunnerListener
{
    @Test
    public void examplePass () {
        Assert.assertEquals("", "");
    }

    @Test
    public void exampleFail () {
        Assert.assertEquals("", "s");
    }

    //This test will be skipped
    @Test(dependsOnMethods={"exampleFail"})
    public void sampleSkipTest() {
        Assert.assertEquals("", "");
    }

}
