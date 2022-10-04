package com.solvd.testing.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.testing.listener.ZebrunnerListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class JsonFormatter {
    public static final Logger LOGGER = LogManager.getLogger(ZebrunnerListener.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static String testReport(Map<String, String> testParameters) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(testParameters);
    }

    public static String testDataJsonString(Map<String, String> testParameters) {
        try {
            return testReport(testParameters);
        } catch (JsonProcessingException e) {
            LOGGER.error(e);
            return null;
        }
    }
}
