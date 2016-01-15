package my.com.test.chm.pages;


import my.com.test.common.webdriver.SeleniumWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Contains all methods to interact with Dashboard page.
 */
public class DashboardPage extends UserBasePage {

    private By usernameLinkLocator = By.xpath("//*[@class='container']//div[contains(@style, 'color:#fff;font-size')]");
    private By logoutButtonLocator = By.cssSelector(".btn.btn-inverse.btn-mini");


    public DashboardPage(SeleniumWebDriver webDriver) {
        super(webDriver);

    }
    @Override
    protected ExpectedCondition getPageLoadCondition() {
        return null;
    }

    @Override
    protected String getPageUrl() {
        return null;
    }

    public String getLoggedInUsername(){
        webDriver.waitUntilElementIsNotVisible(usernameLinkLocator);
        return webDriver.getFieldText(usernameLinkLocator);
    }
}
