package my.com.test.srf.tests;

import my.com.test.common.utils.Common;
import my.com.test.srf.tests.steps.BaseStep;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Class uses to define pre/post action that should be performed
 * before or after test suite/feature run.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CukesFeaturesRunner.class})
public class MainRun extends BaseStep {

    @BeforeClass
    public static void beforeSuite(){
        setUniqueValue(Common.generateRandomValue());
        setRandomString();
    }

    @Before
    public static void setUp(){
        getCleanState();
    }

    @After
    public static void tearDown(Scenario scenario){

        if(scenario.isFailed()) {
            scenario.embed(getSuite().getDriver().makeScreenshot(), "image/png");
        }
    }

    @AfterClass
    public static void afterSuite(){
        getSuite().getDriver().quit();
    }

}
