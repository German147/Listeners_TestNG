package com.solvd.testing.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Optional;

public class RequestUpdate {

    private static final Logger LOGGER = LogManager.getLogger(RequestUpdate.class);
    private static final File propertiesPath = new File(System.getProperty("user.dir")
            + "/src/main/resources/endpoint.properties");

    public static String addQueryParamsValue(String endpointKey, String value) {
        String endpoint = null;
        try (Reader reader = new FileReader(propertiesPath);
             BufferedReader bufferReader = new BufferedReader(reader)) {

                Optional<String> targetLine = bufferReader.lines()
                        .filter(k -> k.contains(endpointKey))
                        .findFirst();

                if (targetLine.isPresent()) {

                    endpoint = targetLine.get().split("=", 2)[1].replace(" ", "").concat(value);
                }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return endpoint;
    }

    public static String completeEndpoint(String endpointKey, String restOfEndpoint) {
        File idPath = new File(System.getProperty("user.dir")
                + "/src/main/resources/id.properties");

        return addQueryParamsValue(endpointKey, FileUtils.readValueInProperties(idPath, "id")).concat(restOfEndpoint);
    }
    

}