package com.solvd.testing.tests;

import listeners.invokedmethod.InvokedMethodClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(InvokedMethodClass.class)
public class Test4 {
    private final static Logger LOGGER = LogManager.getLogger(Test4.class);
    @Test
    public void successTest() {
        LOGGER.info("This is a succesfully test");
    }
}
