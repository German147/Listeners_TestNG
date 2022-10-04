package com.solvd.testing.listener;

import com.google.gson.Gson;
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
    private Map<String, String> onTestStartParameters = new HashMap<>();
    private Map<String, String> onStartParameters = new HashMap<>();
    private Map<String, String> onTestFinishParameters = new HashMap<>();
    private Map<String, String> onFinishParameters = new HashMap<>();
    private Gson outputAux = new Gson();
    private int testRunId;
    private int specificTestId;
    private TestResultStatus testResult;

    private void onTestFinish(TestResultStatus testResultStatus, ITestResult result) {
        String endPoint = "/reporting/v1/test-runs/" + String.valueOf(testRunId) + "/tests/" + String.valueOf(specificTestId);
        onTestFinishParameters = Map.of("result", testResultStatus.name(), "endedAt", DateFormatting.getCurrentTime(), "name", result.getName(), "startedAt", DateFormatting.getCurrentTime(), "framework", TEST_NG, "className", result.getClass().getName(), "methodName", result.getMethod().getMethodName());
        Response response = RestApiWrapper.callPostApi(endPoint, JsonFormatter.testDataJsonString(onTestFinishParameters));
        //OUTPUT
        outputAux.toJson(onTestFinishParameters);

//        LOGGER.info("on test skipped: " + response);
//        try {
//            JSONObject showResponse = new JSONObject(response.body().string());
////            LOGGER.info(showResponse);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void onStart(ITestContext context) {
        //Api CALL: Test run start
        //Mandatory fields: name, startedAt, framework,
        //Endpoint: POST /api/reporting/v1/test-runs?projectKey={projectKey}
        ITestListener.super.onStart(context);
        String endPoint = "/reporting/v1/test-runs?projectKey=BETA";
        onStartParameters = Map.of("name", context.getName(), "startedAt", DateFormatting.getCurrentTime(), "framework", TEST_NG);
        Response response = RestApiWrapper.callPostApi(endPoint, JsonFormatter.testDataJsonString(onStartParameters));
        //LOGGER.info("onStart: " + response);  Print response

        // OUTPUT
        outputAux.toJson(onStartParameters);

        try {
            JSONObject asdtestRunId = new JSONObject(response.body().string());
            //  LOGGER.info("Test Run Id: " + asdtestRunId);
            testRunId = asdtestRunId.getInt("id");
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        //Api CALL: Test execution start
        //Endpoint: POST /api/reporting/v1/test-runs/{testRunId}/tests
        //Mandatory fields: name, className, methodName, startedAt

        ITestListener.super.onTestStart(result);
        String endPoint = "/reporting/v1/test-runs/" + String.valueOf(testRunId) + "/tests";
        // LOGGER.info("on test start endpoint: " + endPoint);
        onTestStartParameters = Map.of("name", result.getName(), "startedAt", DateFormatting.getCurrentTime(), "framework", TEST_NG, "className", result.getClass().getName(), "methodName", result.getMethod().getMethodName());

        //OUTPUT
        outputAux.toJson(onTestStartParameters);
        Response response = RestApiWrapper.callPostApi(endPoint, JsonFormatter.testDataJsonString(onTestStartParameters));
        //  LOGGER.info("onTestStart: " + response);   Visualizar response

        try {
            JSONObject auxResponse = new JSONObject(response.body().string());
            specificTestId = auxResponse.getInt("id");
            // LOGGER.info("Specified testID: " + specificTestId);       Visualizar id de cada test
            //String asdtestRunId = new JSONObject(response.body()).toString();     Visualizar el body de la response
            //LOGGER.info("Test Run Id: " + asdtestRunId);                   Ver el test run ID
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
    public void onFinish(ITestContext context) {
        //Api CALL: Test run execution finish
        //Mandatory fields: endedAt
        //Endpoint: PUT /api/reporting/v1/test-runs/{id(uuid)}
        String endPoint = "/reporting/v1/test-runs/" + String.valueOf(testRunId);
        ITestListener.super.onFinish(context);
        onFinishParameters = Map.of("result", testResult.name(), "endedAt", DateFormatting.getCurrentTime());
        Response response = RestApiWrapper.callPutApi(endPoint, JsonFormatter.testDataJsonString(onFinishParameters));
        //OUTPUT
        outputAux.toJson(onFinishParameters);
        //LOGGER.info("on Finish:" + response);
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        for (ISuite suite : suites) {
            String suiteName = suite.getName();
            Map<String, ISuiteResult> suiteResult = suite.getResults();
            for (ISuiteResult iSuiteResult : suiteResult.values()) {
                ITestContext context = iSuiteResult.getTestContext();
                LOGGER.info("Capture all passed Test results: " + suiteName + "No. of Test Cases: " +
                        context.getPassedTests().getAllResults().size());
                LOGGER.info("Capture all failed Test results: " + suiteName + "No. of Test Cases: " +
                        context.getFailedTests().getAllResults().size());
                LOGGER.info("Capture all skipped Test results: " + suiteName + "No. of Test Cases: " +
                        context.getSkippedTests().getAllResults().size());
            }
        }
    }

    private enum TestResultStatus {
        SUCCESS, FAILED, SKIPPED;
    }
}