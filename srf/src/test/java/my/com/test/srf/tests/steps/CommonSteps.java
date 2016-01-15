package my.com.test.srf.tests.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Class for gather all common steps that can be used in all features
 * like interaction with menu, logout, etc.
 */
public class CommonSteps extends BaseStep {

    @When("^press '(.*)' button$")
    public void press_xxx_button(String button){
        getSuite().getPageInit().getUserBasePage().pressButton(button);
    }

    @When("^I navigate to '(.*)'$")
    public void I_navigate_to_xxx(String url){
        getSuite().getDriver().get(url);
    }

    @Then("^I should see google logo$")
    public void I_should_see_google_logo(){
        assertThat("Google logo doesnot present", getSuite().getPageInit().getUserBasePage().isGoogleLogoPresent(), equalTo(true));
    }
}
