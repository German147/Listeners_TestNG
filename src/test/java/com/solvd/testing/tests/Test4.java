package com.solvd.testing.tests;

import listeners.invokedmethod.InvokedMethodClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(InvokedMethodClass.class)
public class Test4 {

    @Test
    public void successTest(){
        System.out.println("This is a succesfully test");
    }
}
