package com.solvd.testing.helper;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.text.SimpleDateFormat;
import java.util.*;


public class ScreenshotService{
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");

    public void getScreenshot() throws Exception
    {
        Calendar now = Calendar.getInstance();
        Robot robot = new Robot();
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(screenShot, "JPG", new File("src/main/resources/screenshot/"+formatter.format(now.getTime())+".jpg"));
        System.out.println(formatter.format(now.getTime()));
    }

    public static void main(String[] args) throws Exception
    {
        ScreenshotService s2i = new ScreenshotService();
        s2i.getScreenshot();
    }
}