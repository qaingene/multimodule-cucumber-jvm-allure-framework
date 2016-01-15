package my.com.test.chm.pages;

import my.com.test.common.pages.AbstractPage;
import my.com.test.common.utils.Common;
import my.com.test.common.webdriver.SeleniumWebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Class contains all methods to interact with common elements for
 * user view.
 *
 */
public class UserBasePage extends AbstractPage<UserBasePage> {

    private By mainMenuLocator = By.cssSelector(".app");

    public UserBasePage(SeleniumWebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected ExpectedCondition getPageLoadCondition() {
        return ExpectedConditions.visibilityOfElementLocated(mainMenuLocator);
    }

    @Override
    protected String getPageUrl() {
        return null;
    }

    public void pressButton(String button) {
        By buttonLocator = By.cssSelector("[value='" + button + "']");
        webDriver.click(buttonLocator);
    }
}

