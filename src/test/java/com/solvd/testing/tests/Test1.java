package com.solvd.testing.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MyListener.class)
public class Test1 {
    @Test
    public void successTest(){
        System.out.println("im the first test");
    }
    @Test
    public void failTest(){
        System.out.println("im the second test");
        Assert.fail();
    }
}
