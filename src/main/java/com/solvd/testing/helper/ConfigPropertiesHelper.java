package com.solvd.testing.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropertiesHelper {
    public static final Logger LOGGER = LogManager.getLogger(ConfigPropertiesHelper.class);

    private static final String PROP_FILE_NAME = "src/main/resources/config.properties";

    private static Properties properties = new Properties();
    private static ClassLoader loader = Thread.currentThread().getContextClassLoader();
    private static InputStream inputStream = loader.getResourceAsStream(PROP_FILE_NAME);


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

    public static void writeProperty(String property, String value) throws IOException {
        properties.load(inputStream);
        properties.setProperty(property, value);
    }

    public static void writeInAPFile(String property, String value) {
        try {
            writeProperty(property, value);
        } catch (IOException e) {
            LOGGER.error("IOException caught", e);
        }
    }

}
