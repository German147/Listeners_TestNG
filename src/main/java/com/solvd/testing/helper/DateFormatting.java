package com.solvd.testing.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatting {
    
    public static String getCurrentTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String dateString = sdf.format(date);
        return dateString;
    }
}
