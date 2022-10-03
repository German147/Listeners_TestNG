package com.solvd.testing.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.testing.helper.ConfigPropertiesHelper;
import com.solvd.testing.zebrunner.api.RestApiWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZebrunnerListener implements ITestListener, IReporter {
    public static final Logger LOGGER = LogManager.getLogger(ZebrunnerListener.class);
    public static final String TEST_NG = "TestNG";
    private static String apiUrl = "https://" + ConfigPropertiesHelper.getProperty("reporting.server.hostname");
    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, String> onTestStartParameters = new HashMap<>();
    private Map<String, String> onStartParameters = new HashMap<>();
    private Map<String, String> onFinishParameters = new HashMap<>();
    private TestResultStatus result;

    public ZebrunnerListener() {
    }

    public static String getCurrentTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String dateString = sdf.format(date);
        return dateString;
    }

    public Map<String, String> getOnTestStartParameters() {
        return onTestStartParameters;
    }

    public Map<String, String> getOnStartParameters() {
        return onStartParameters;
    }

    public Map<String, String> getOnFinishParameters() {
        return onFinishParameters;
    }

    public TestResultStatus getResult() {
        return result;
    }

    //Map.of("name", testName, "startedAt", startedAt, "framework", framework)
    private String testReport(Map<String, String> testParameters) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(testParameters);
    }

    public String testReportData(Map<String, String> testParameters) {
        try {
            return testReport(testParameters);
        } catch (JsonProcessingException e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public void onTestStart(ITestResult result) {
        //Primera llamada
        ITestListener.super.onTestStart(result);
        onTestStartParameters = Map.of("name", result.getName(), "startedAt", getCurrentTime(), "framework", TEST_NG);
        try {
            RestApiWrapper.postJson(apiUrl, testReportData(getOnTestStartParameters()), "");
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        this.result = TestResultStatus.SUCCESS;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        this.result = TestResultStatus.FAILED;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
        this.result = TestResultStatus.SKIPPED;
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
        onTestStartParameters = Map.of("name", context.getName(), "startedAt", getCurrentTime(), "framework", TEST_NG);

    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        onFinishParameters = Map.of("result", this.result.name(), "endedAt", getCurrentTime());
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        for (ISuite suite : suites) {
            String suiteName = suite.getName();
            Map<String, ISuiteResult> suiteResult = suite.getResults();
            for (ISuiteResult iSuiteResult : suiteResult.values()) {
                ITestContext context = iSuiteResult.getTestContext();
                System.out.println("Capture all passed Test results: " + suiteName + "No. of Test Cases: " +
                        context.getPassedTests().getAllResults().size());
                System.out.println("Capture all failed Test results: " + suiteName + "No. of Test Cases: " +
                        context.getFailedTests().getAllResults().size());
                System.out.println("Capture all skipped Test results: " + suiteName + "No. of Test Cases: " +
                        context.getSkippedTests().getAllResults().size());
            }
        }
    }

    public enum TestResultStatus {
        SUCCESS, FAILED, SKIPPED;
    }
}