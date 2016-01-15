package my.com.test.srf.pages;

import my.com.test.common.pages.AbstractPage;
import my.com.test.common.utils.Common;
import my.com.test.common.webdriver.SeleniumWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Class contains all methods to interact with common elements for
 * user view.
 *
 */
public class UserBasePage extends AbstractPage<UserBasePage> {

    private By googleLogoLocator = By.cssSelector("#hplogo");

    public UserBasePage(SeleniumWebDriver webDriver) {
        super(webDriver);
        Common.pause(1000);
    }

    protected ExpectedCondition getPageLoadCondition() {
        return null;
    }


    protected String getPageUrl() {
        return null;
    }

    public void pressButton(String button){
        By buttonLocator = By.xpath(".//button[contains (., '" + button + "')]");
        webDriver.waitUntilElementIsNotVisible(buttonLocator);
        webDriver.waitUntil(ExpectedConditions.elementToBeClickable(buttonLocator));
        webDriver.click(buttonLocator);
    }

    public boolean isGoogleLogoPresent(){
        return webDriver.isElementDisplayed(googleLogoLocator);
    }
}
