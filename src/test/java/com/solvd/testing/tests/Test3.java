package com.solvd.testing.tests;

import listeners.reporter.ReporterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ReporterClass.class)
public class Test3 {

    @Test
    public void simpleWebTest() {

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");

        WebDriver driver = new ChromeDriver();

        driver.get("http://www.google.com");

        driver.manage().window().maximize();

        driver.close();

        System.out.println("This is the test function - Class Test Pack1");

    }

    @Test
    public void f0(){
        Assert.assertTrue(true);
    }

    @Test
    public void f1(){
        Assert.assertTrue(false);
    }

    @Test
    public void f2(){
        Assert.assertTrue(true);
    }

    @Test
    public void f3(){
        Assert.assertTrue(false);
    }

    @Test(dependsOnMethods = "f1")
    public void f4(){
        Assert.assertTrue(true);
    }
}
