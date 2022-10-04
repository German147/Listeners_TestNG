package listeners.reporter;

import listeners.suite.SuiteClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import java.util.List;
import java.util.Map;

public class ReporterClass implements IReporter {

    private static final Logger logger = LoggerFactory.getLogger(ReporterClass.class);

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
}
