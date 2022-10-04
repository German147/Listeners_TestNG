package com.solvd.testing.tests;

import com.solvd.testing.listener.ZebrunnerListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ZebrunnerListener.class)
public class Test2 {
    @Test
    public void successTestTwo() {
        //LOGGER.info("Im a test in run 3");
        Assert.assertEquals(3, 3);
    }
}
