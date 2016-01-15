package my.com.test.chm.pages;


import my.com.test.common.pages.AbstractPage;
import my.com.test.common.utils.Common;
import my.com.test.common.webdriver.SeleniumWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Class all method to interact with Login Page.
 */
public class LoginPage extends AbstractPage<LoginPage> {

    private By usernameFieldLocator = By.id("loginusername");
    private By passwordFieldLocator = By.id("loginpassword");
    private By loginButtonLocator = By.cssSelector(".btn.btn-success");

    public LoginPage(SeleniumWebDriver webDriver) {
        super(webDriver);
    }

    protected ExpectedCondition getPageLoadCondition() {
        return ExpectedConditions.visibilityOfElementLocated(usernameFieldLocator);
    }

    public String getPageUrl() {
        return "/auth.jsp";
    }

    public void typeUsername(String username){
        webDriver.waitUntilElementIsNotVisible(usernameFieldLocator);
        webDriver.clearAndType(usernameFieldLocator, username);
    }

    public void typePassword(String password){
        webDriver.clearAndType(passwordFieldLocator, password);
        webDriver.waitUntil(ExpectedConditions.textToBePresentInElementValue(passwordFieldLocator, password));
    }

    public void clickLoginButton(){
        webDriver.waitUntilElementIsNotVisible(loginButtonLocator);
        webDriver.click(loginButtonLocator);
    }

    public void loginAsValidUser(String username, String password){
        typeUsername(username);
        typePassword(password);
        clickLoginButton();
    }
}
