package com.solvd.testing.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(MyListener.class)
public class Test1 {

    private static final Logger LOGGER = LogManager.getLogger(Test1.class);

    @Test
    public void successTest() {
        LOGGER.info("I am form logger");
        System.out.println("im the first test");
    }

    @Test
    public void failTest() {
        System.out.println("im the second test");
        Assert.fail();
    }


}
