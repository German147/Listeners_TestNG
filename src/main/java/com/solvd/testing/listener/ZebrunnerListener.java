package com.solvd.testing.listener;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solvd.testing.api.ZebrunnerAPI;
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
    private JsonObject outputAux = new JsonObject();
    private TestResultStatus testResult;
    private ZebrunnerAPI zebApi = ZebrunnerAPI.getInstance();
    private void onTestFinish(TestResultStatus testResultStatus, ITestResult result) {
        outputAux.addProperty("result", testResultStatus.name());
        outputAux.addProperty("endedAt", DateFormatting.getCurrentTime());
        zebApi.testExecutionFinishRequest(outputAux);
    }

    @Override
    public void onStart(ITestContext context) {
        //Api CALL: Test run start
        //Mandatory fields: name, startedAt, framework,
        //Endpoint: POST /api/reporting/v1/test-runs?projectKey={projectKey}
        outputAux.addProperty("name", context.getName());
        outputAux.addProperty("framework", TEST_NG);
        zebApi.tokenGeneration();
        zebApi.testStartRequest(outputAux);

    }

    @Override
    public void onTestStart(ITestResult result) {
        //Api CALL: Test execution start
        //Endpoint: POST /api/reporting/v1/test-runs/{testRunId}/tests
        //Mandatory fields: name, className, methodName, startedAt
        outputAux.addProperty("name", result.getName());
        outputAux.addProperty("className", result.getClass().getName());
        outputAux.addProperty("methodName", result.getMethod().getMethodName());
        zebApi.testExecutionStart(outputAux);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        //Api CALL: Test execution finish
        //Mandatory fields: result, endedAt
        //Endpoint: PUT /api/reporting/v1/test-runs/{testRunId}/tests/{testId(id)}
        testResult = TestResultStatus.PASSED;
        onTestFinish(testResult, result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        //Api CALL: Test execution finish
        //Mandatory fields: result, endedAt
        //Endpoint: PUT /api/reporting/v1/test-runs/{testRunId}/tests/{testId(uuid)}
        testResult = TestResultStatus.FAILED;
        onTestFinish(testResult, result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        //Api CALL: Test execution finish
        //Mandatory fields: result, endedAt
        //Endpoint: PUT /api/reporting/v1/test-runs/{testRunId}/tests/{testId(uuid)}
        testResult = TestResultStatus.SKIPPED;
        onTestFinish(testResult, result);
    }

    @Override
    public void onFinish(ITestContext context) {
        //Api CALL: Test run execution finish
        //Mandatory fields: endedAt
        //Endpoint: PUT /api/reporting/v1/test-runs/{id(uuid)}
        zebApi.testRunFinishRequest(outputAux);
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
        PASSED, FAILED, SKIPPED;
    }
}