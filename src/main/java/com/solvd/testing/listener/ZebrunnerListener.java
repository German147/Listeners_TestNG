package com.solvd.testing.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.testing.helper.ConfigPropertiesHelper;
import com.solvd.testing.helper.DateFormatting;
import com.solvd.testing.zebrunner.api.AuthToken;
import com.solvd.testing.zebrunner.api.RestApiWrapper;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
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
    private int testRunId;
    private int specificTestId;
    private TestResultStatus result;

    public ZebrunnerListener() {
    }

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
        String endPoint = "/reporting/v1/test-runs/" + String.valueOf(testRunId) + "/tests";
        System.out.println("on test start endpoint: " + endPoint);

        onTestStartParameters = Map.of("name", result.getName(), "startedAt", DateFormatting.getCurrentTime(), "framework", TEST_NG, "className", result.getClass().getName(), "methodName", result.getMethod().getMethodName());

        //Api CALL: Test execution start
        //Endpoint: POST /api/reporting/v1/test-runs/{testRunId}/tests
        //Mandatory fields: name, className, methodName, startedAt
        Response response = RestApiWrapper.callApi(endPoint, testReportData(onTestStartParameters));
        System.out.println("onTestStart: " + response);

        try {
            JSONObject auxResponse = new JSONObject(response.body().string());
            specificTestId = auxResponse.getInt("id");
            System.out.println("Specified testID: " + specificTestId);

            //String asdtestRunId = new JSONObject(response.body()).toString();
            //System.out.println("Test Run Id: " + asdtestRunId);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        String endPoint = "/reporting/v1/test-runs/" + String.valueOf(testRunId)+ "/tests/" + String.valueOf(specificTestId);
        this.result = TestResultStatus.SUCCESS;
        onFinishParameters = Map.of("result", this.result.name(), "endedAt", DateFormatting.getCurrentTime(), "name", result.getName(), "startedAt", DateFormatting.getCurrentTime(), "framework", TEST_NG, "className", result.getClass().getName(), "methodName", result.getMethod().getMethodName());
        Response response = RestApiWrapper.callApi(endPoint, testReportData(onFinishParameters));
        System.out.println("on test success: " + response);
        try {
            JSONObject showResponse = new JSONObject(response.body().string());
            System.out.println(showResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Api CALL: Test execution finish
        //Mandatory fields: result, endedAt
        //Endpoint: PUT /api/reporting/v1/test-runs/{testRunId}/tests/{testId(id)}
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        String endPoint = "/reporting/v1/test-runs/" + String.valueOf(testRunId)+ "/tests/" + String.valueOf(specificTestId);
        this.result = TestResultStatus.FAILED;
        onFinishParameters = Map.of("result", this.result.name(), "endedAt", DateFormatting.getCurrentTime(), "name", result.getName(), "startedAt", DateFormatting.getCurrentTime(), "framework", TEST_NG, "className", result.getClass().getName(), "methodName", result.getMethod().getMethodName());
        Response response = RestApiWrapper.callApi(endPoint, testReportData(onFinishParameters));
        System.out.println("on test fail: " + response);
        try {
            JSONObject showResponse = new JSONObject(response.body().string());
            System.out.println(showResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Api CALL: Test execution finish
        //Mandatory fields: result, endedAt
        //Endpoint: PUT /api/reporting/v1/test-runs/{testRunId}/tests/{testId(uuid)}
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
        String endPoint = "/reporting/v1/test-runs/" + String.valueOf(testRunId)+ "/tests/" + String.valueOf(specificTestId);
        this.result = TestResultStatus.SKIPPED;
        onFinishParameters = Map.of("result", this.result.name(), "endedAt", DateFormatting.getCurrentTime(), "name", result.getName(), "startedAt", DateFormatting.getCurrentTime(), "framework", TEST_NG, "className", result.getClass().getName(), "methodName", result.getMethod().getMethodName());
        Response response = RestApiWrapper.callApi(endPoint, testReportData(onFinishParameters));
        System.out.println("on test skipped: " + response);
        try {
            JSONObject showResponse = new JSONObject(response.body().string());
            System.out.println(showResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Api CALL: Test execution finish
        //Mandatory fields: result, endedAt
        //Endpoint: PUT /api/reporting/v1/test-runs/{testRunId}/tests/{testId(uuid)}
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
        String endPoint = "/reporting/v1/test-runs?projectKey=BETA";
        onTestStartParameters = Map.of("name", context.getName(), "startedAt", DateFormatting.getCurrentTime(), "framework", TEST_NG);

        Response response = RestApiWrapper.callApi(endPoint, testReportData(onTestStartParameters));
        System.out.println("onStart: " + response);
        try {
            JSONObject asdtestRunId = new JSONObject(response.body().string());
            System.out.println("Test Run Id: " + asdtestRunId);
            testRunId = asdtestRunId.getInt("id");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Api CALL: Test run start
        //Mandatory fields: name, startedAt, framework,
        //Endpoint: POST /api/reporting/v1/test-runs?projectKey={projectKey}
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        onFinishParameters = Map.of("result", this.result.name(), "endedAt", DateFormatting.getCurrentTime());

        //Api CALL: Test run execution finish
        //Mandatory fields: endedAt
        //Endpoint: PUT /api/reporting/v1/test-runs/{id}
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