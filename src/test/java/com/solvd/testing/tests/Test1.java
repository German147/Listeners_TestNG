package com.solvd.testing.tests;

import com.solvd.testing.listener.ZebrunnerListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(ZebrunnerListener.class)
public class Test1 {

    private static final Logger LOGGER = LogManager.getLogger(Test1.class);

    @Test
    public void successTest() {
        LOGGER.info("I am form logger");
        LOGGER.info("im the first test");
    }

    @Test
    public void failTest() {
        LOGGER.info("im the second test");
        Assert.fail();
    }
    @Test(dependsOnMethods = {"failTest"})
    public void skippedTest(){
        LOGGER.info("Im a skipped test");
    }

}
