package listeners.invokedmethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class InvokedMethodClass implements IInvokedMethodListener {
    private final Logger LOGGER = LogManager.getLogger(InvokedMethodClass.class);
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        LOGGER.info("This is before method invocation. For instance, it is useful for " +
                "checking conditions before method is executed");
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        LOGGER.info("This is after method invocation. It can be used for checking" +
                " outputs before method is executed");
    }
}
