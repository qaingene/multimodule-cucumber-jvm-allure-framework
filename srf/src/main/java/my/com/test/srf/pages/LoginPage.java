package my.com.test.srf.pages;


import my.com.test.common.pages.AbstractPage;
import my.com.test.common.utils.Common;
import my.com.test.common.webdriver.SeleniumWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginPage extends AbstractPage<LoginPage> {

    private By usernameFieldLocator = By.id("username");
    private By passwordFieldLocator = By.id("password");

    public LoginPage(SeleniumWebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected ExpectedCondition getPageLoadCondition() {
        return ExpectedConditions.visibilityOfElementLocated(usernameFieldLocator);
    }

    @Override
    protected String getPageUrl() {
        return "/Login/";
    }


    public LoginPage typeUsername(String username){
        webDriver.clearAndType(usernameFieldLocator, username);
        return this;
    }
}
