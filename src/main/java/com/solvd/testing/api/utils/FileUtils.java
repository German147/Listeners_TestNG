package com.solvd.testing.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileUtils {
    private static final Logger LOGGER = LogManager.getLogger(FileUtils.class);

    public static Map<String, String> propertyValue(String... keys) {
        Map<String, String> properties = new HashMap<>();
        File agentFile = new File(System.getProperty("user.dir") + "/src/main/resources/agent.yaml");
        try (Reader reader = new FileReader(agentFile);
             BufferedReader bufferReader = new BufferedReader(reader)) {

            for (int i = 0; i < keys.length; i++) {
                final int keyIndex = i;

                Optional<String> targetLine = bufferReader.lines()
                        .filter(k -> k.contains(keys[keyIndex]))
                        .findFirst();

                if (targetLine.isPresent()) {
                    String removedIndentation = removeInitialSpaces(targetLine.get());
                    String[] keyValueProperty = removedIndentation.split("[:=]");

                    properties.put(keyValueProperty[0], removeInitialSpaces(keyValueProperty[1]));
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return properties;
    }

    public static String removeInitialSpaces(String word) {
        for (char character : word.toCharArray()) {
            if (!Character.isSpaceChar(character)) {
                word = word.substring(word.indexOf(character));
                break;
            }
        }
        return word;
    }

    public static String readValueInProperties(File file, String target) {
        String value = "";
        try (Reader reader = new FileReader(file);
             BufferedReader bufferReader = new BufferedReader(reader)) {

            Optional<String> targetLine = bufferReader.lines()
                    .filter(k -> k.contains(target))
                    .findFirst();

            if (targetLine.isPresent()) {
                String[] tokenHeader = targetLine.get().split("=");

                tokenHeader[1] = tokenHeader[1].split(target)[0];

                value = tokenHeader[1];
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return value;
    }
}