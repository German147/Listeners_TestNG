package com.solvd.testing.tests;

import com.solvd.testing.listener.ZebrunnerListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ZebrunnerListener.class)
public class Test2 {
    private final Logger LOGGER = LogManager.getLogger(Test2.class);
    @Test
    public void successTestTwo() {
        LOGGER.info("Im a test in run 3");
        Assert.assertEquals(3, 3);
    }
}
