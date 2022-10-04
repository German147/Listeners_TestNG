package com.solvd.testing.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.testing.helper.ConfigPropertiesHelper;
import com.solvd.testing.helper.DateFormatting;
import com.solvd.testing.helper.JsonFormatter;
import com.solvd.testing.zebrunner.api.RestApiWrapper;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.IOException;
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
    private int testRunId;
    private int specificTestId;
    private TestResultStatus testResult;

    @Override
    public void onTestStart(ITestResult result) {
        //Primera llamada
        ITestListener.super.onTestStart(result);
        String endPoint = "/reporting/v1/test-runs/" + String.valueOf(testRunId) + "/tests";
        // System.out.println("on test start endpoint: " + endPoint);
        onTestStartParameters = Map.of("name", result.getName(), "startedAt", DateFormatting.getCurrentTime(), "framework", TEST_NG, "className", result.getClass().getName(), "methodName", result.getMethod().getMethodName());

        //Api CALL: Test execution start
        //Endpoint: POST /api/reporting/v1/test-runs/{testRunId}/tests
        //Mandatory fields: name, className, methodName, startedAt
        Response response = RestApiWrapper.callPostApi(endPoint, JsonFormatter.testDataJsonString(onTestStartParameters));
        //  System.out.println("onTestStart: " + response);

        try {
            JSONObject auxResponse = new JSONObject(response.body().string());
            specificTestId = auxResponse.getInt("id");
            // System.out.println("Specified testID: " + specificTestId);
            //String asdtestRunId = new JSONObject(response.body()).toString();
            //System.out.println("Test Run Id: " + asdtestRunId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        //Api CALL: Test execution finish
        //Mandatory fields: result, endedAt
        //Endpoint: PUT /api/reporting/v1/test-runs/{testRunId}/tests/{testId(id)}
        ITestListener.super.onTestSuccess(result);
        String endPoint = "/reporting/v1/test-runs/" + String.valueOf(testRunId) + "/tests/" + String.valueOf(specificTestId);
        testResult = TestResultStatus.SUCCESS;
        onTestFinish(testResult, result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        //Api CALL: Test execution finish
        //Mandatory fields: result, endedAt
        //Endpoint: PUT /api/reporting/v1/test-runs/{testRunId}/tests/{testId(uuid)}
        ITestListener.super.onTestFailure(result);
        String endPoint = "/reporting/v1/test-runs/" + String.valueOf(testRunId) + "/tests/" + String.valueOf(specificTestId);
        testResult = TestResultStatus.FAILED;
        onTestFinish(testResult, result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        //Api CALL: Test execution finish
        //Mandatory fields: result, endedAt
        //Endpoint: PUT /api/reporting/v1/test-runs/{testRunId}/tests/{testId(uuid)}
        ITestListener.super.onTestSkipped(result);
        testResult = TestResultStatus.SKIPPED;
        onTestFinish(testResult, result);
    }

    @Override
    public void onStart(ITestContext context) {
        //Api CALL: Test run start
        //Mandatory fields: name, startedAt, framework,
        //Endpoint: POST /api/reporting/v1/test-runs?projectKey={projectKey}
        ITestListener.super.onStart(context);
        String endPoint = "/reporting/v1/test-runs?projectKey=BETA";
        onTestStartParameters = Map.of("name", context.getName(), "startedAt", DateFormatting.getCurrentTime(), "framework", TEST_NG);
        Response response = RestApiWrapper.callPostApi(endPoint, JsonFormatter.testDataJsonString(onTestStartParameters));
        System.out.println("onStart: " + response);
        try {
            JSONObject asdtestRunId = new JSONObject(response.body().string());
            //  System.out.println("Test Run Id: " + asdtestRunId);
            testRunId = asdtestRunId.getInt("id");
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        //Api CALL: Test run execution finish
        //Mandatory fields: endedAt
        //Endpoint: PUT /api/reporting/v1/test-runs/{id(uuid)}
        String endPoint = "/reporting/v1/test-runs/" + String.valueOf(testRunId);
        Map<String, String> onFinishParameters = new HashMap<>();
        ITestListener.super.onFinish(context);
        onFinishParameters = Map.of("result", testResult.name(), "endedAt", DateFormatting.getCurrentTime());
        Response response = RestApiWrapper.callPutApi(endPoint, JsonFormatter.testDataJsonString(onFinishParameters));
        //System.out.println("on Finish:" + response);
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

    private void onTestFinish(TestResultStatus testResultStatus, ITestResult result) {
        Map<String, String> onTestFinishParameters = new HashMap<>();
        String endPoint = "/reporting/v1/test-runs/" + String.valueOf(testRunId) + "/tests/" + String.valueOf(specificTestId);
        onTestFinishParameters = Map.of("result", testResultStatus.name(), "endedAt", DateFormatting.getCurrentTime(), "name", result.getName(), "startedAt", DateFormatting.getCurrentTime(), "framework", TEST_NG, "className", result.getClass().getName(), "methodName", result.getMethod().getMethodName());
        Response response = RestApiWrapper.callPostApi(endPoint, JsonFormatter.testDataJsonString(onTestFinishParameters));
//        System.out.println("on test skipped: " + response);
//        try {
//            JSONObject showResponse = new JSONObject(response.body().string());
////            System.out.println(showResponse);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    private enum TestResultStatus {
        SUCCESS, FAILED, SKIPPED;
    }
}