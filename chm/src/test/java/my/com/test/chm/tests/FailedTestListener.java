package my.com.test.chm.tests;


import my.com.test.chm.tests.steps.BaseStep;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import ru.yandex.qatools.allure.annotations.Attachment;

public class FailedTestListener extends RunListener {

    public void testRunStarted(Description description) throws Exception {
    }

    public void testRunFinished(Result result) throws Exception {
    }

    public void testStarted(Description description) throws Exception {
    }

    public void testFinished(Description description) throws Exception {
    }

    public void testFailure(Failure failure) throws Exception {
        saveScreenshotForAllure();
    }

    public void testAssumptionFailure(Failure failure) {
    }

    public void testIgnored(Description description) throws Exception {
    }

    @Attachment(value = "screenshot", type = "image/png")
    private static byte[] saveScreenshotForAllure(){
        byte[] screenshot = BaseStep.getSuite().getDriver().makeScreenshot();
        return screenshot;
    }
}