import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyListener implements ITestListener {
    public static final String FRAMEWORK = "testng";
    @Override
    public void onTestStart(ITestResult testResult){
        Helper.jsonMap.put("name", (String) testResult.getName());
        //jsonMap.put("udid", testResult.id());
        Helper.jsonMap.put("startedAt", Helper.getStartTime());
        Helper.jsonMap.put("Framework", FRAMEWORK);
        Helper.jsonMap.put("className", testResult.getTestClass().toString());
        Helper.jsonMap.put("methodName", testResult.getMethod().toString());
    }
    @Override
    public void onTestSuccess(ITestResult testResult) {
        Helper.setResult(testResult);
    }
    @Override
    public void onTestFailure(ITestResult testResult) {
        Helper.setResult(testResult);
    }
    @Override
    public void onTestSkipped(ITestResult testResult) {
        Helper.setResult(testResult);
    }
}
