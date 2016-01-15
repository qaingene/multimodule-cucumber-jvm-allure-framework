package my.com.test.chm.tests.steps;

import my.com.test.chm.pages.DashboardPage;
import my.com.test.common.utils.Common;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Class to describe steps related to login functionality.
 */
public class LoginSteps extends BaseStep {

    Map<String, String> credentials = null;

    @Given("^I login to application as '(.*)'$")
    public void login_to_application(String username){
        credentials = Common.getUserCredentials(username);
        getSuite().getPageInit().getLoginPage().loginAsValidUser(credentials.get("username"), credentials.get("password"));
    }

    @Then("^I should be logged in as '(.*)'$")
    public void should_be_logged_in_as(String userOrFirstLastName) {
        // if we were given a username key, get the display name from the configuration
        String displayName = userOrFirstLastName;
        if (displayName.startsWith("user.")) {
            displayName = Common.getUserDisplayName(userOrFirstLastName, userOrFirstLastName);
        }
        DashboardPage dashboardPage = getSuite().getPageInit().getDashboardPage();
        assertThat("User display name is wrong:", dashboardPage.getLoggedInUsername(), containsString(displayName));
    }
}
