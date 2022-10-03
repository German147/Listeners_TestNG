package listeners.suite;

import com.solvd.testing.helper.DateTimeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.time.LocalDateTime;

public class SuiteClass implements ISuiteListener {

    private static final Logger logger = LoggerFactory.getLogger(SuiteClass.class);

    @Override
    public void onStart(ISuite suite) {

        logger.info("this is on start method " + suite.getName() + " at: " + DateTimeHandler.getNow());
    }

    @Override
    public void onFinish(ISuite suite) {
        logger.info("this is on finish method" + suite.getName() + " at: " + DateTimeHandler.getNow());
    }
}
