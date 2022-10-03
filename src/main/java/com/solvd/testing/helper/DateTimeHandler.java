package com.solvd.testing.helper;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHandler {
    public static String getNow(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return formatter.format(ZonedDateTime.now());
    }
}
