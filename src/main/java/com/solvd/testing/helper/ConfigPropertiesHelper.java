package com.solvd.testing.helper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigPropertiesHelper {

    private static final String PROP_FILE_NAME = "src/main/resources/agent.properties";

    public static String getProperty(String propertyKey) {

        FileReader reader = null;
        Properties p = new Properties();
        try {
            reader = new FileReader(PROP_FILE_NAME);
            p.load(reader);
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return p.getProperty(propertyKey);
    }

    public static void writeProperty(String property, String value) {

    }

}
