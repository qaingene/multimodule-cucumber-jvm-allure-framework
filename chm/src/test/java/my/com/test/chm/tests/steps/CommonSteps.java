package my.com.test.chm.tests.steps;

import cucumber.api.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Class for gather all common steps that can be used in all features
 * like interaction with menu, logout, etc.
 */
public class CommonSteps extends BaseStep {


    @When("^press '(.*)' button$")
    public void press_xxx_button(String button) {
        getSuite().getPageInit().getUserBasePage().pressButton(button);
    }
}
