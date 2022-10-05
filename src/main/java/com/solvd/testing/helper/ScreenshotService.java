package com.solvd.testing.helper;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ScreenshotService{
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");

    public static byte[] getScreenshot() throws Exception
    {
        Calendar now = Calendar.getInstance();
        Robot robot = new Robot();
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        String fileName = formatter.format(now.getTime()) + ".jpg";
        ImageIO.write(screenShot, "JPG", new File("src/main/resources/screenshot/"+fileName));
        System.out.println(formatter.format(now.getTime()));
        ByteArrayOutputStream byteArray;
        try {
            BufferedImage toByteArray = ImageIO.read(new File("src/main/resources/screenshot/"+fileName));
            byteArray = new ByteArrayOutputStream();
            ImageIO.write(toByteArray, "JPG", byteArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteArray.toByteArray();
    }
}