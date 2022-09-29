package com.solvd.testing.listener;

import com.solvd.testing.zebrunner.api.AuthToken;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ZebrunnerListener implements ITestListener {

    public void onTestStart(ITestResult result) {

        //On test start, a call to the authentication api is done as a proof of concept.
        System.out.println("ZebrunnerListener >> onTestStart ::" + result.getMethod().getMethodName());
        System.out.println("ZebrunnerListener >> getAuthToken ::" + AuthToken.getInstance().getAuthToken());
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("ZebrunnerListener >> onTestSuccess ::" + result.getMethod()
                .getMethodName());
    }


    public void onTestFailure(ITestResult result) {
        System.out.println("ZebrunnerListener >> onTestFailure ::" + result.getMethod()
                .getMethodName());
    }

    public void onTestSkipped(ITestResult result) {
        System.out.println("TestingDocs>> onTestSkipped ::" + result.getMethod()
                .getMethodName());
    }

    public void onStart(ITestContext context) {
        System.out.println("TestingDocs>> OnStart ::" + context.getName());
    }

    public void onFinish(ITestContext context) {
        System.out.println("****************TestRun Report Header ********************" );
        System.out.println("Total Passed" + context.getPassedTests());
        System.out.println("Total Failed" + context.getFailedTests());
        System.out.println("Total Skipped" + context.getSkippedTests());
        System.out.println("***************** TestRun Report Footer ********************" );
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }
}