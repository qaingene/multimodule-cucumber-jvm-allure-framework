package my.com.test.chm.pages;


import my.com.test.common.webdriver.SeleniumWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Contains all methods to interact with Dashboard page.
 */
public class DashboardPage extends UserBasePage {

    private By usernameLinkLocator = By.xpath("//*[@class='col-lg-4 col-md-4 col-sm-4 ng-binding']");
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
