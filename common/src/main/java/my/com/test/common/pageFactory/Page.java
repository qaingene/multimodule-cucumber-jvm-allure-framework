package my.com.test.common.pageFactory;

import my.com.test.common.webdriver.SeleniumWebDriver;
import my.com.test.common.pages.AbstractPage;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Class is the bridge between page Initializer and Page Object.
 */
public class Page extends AbstractPage<Page> {

    public Page(SeleniumWebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected ExpectedCondition getPageLoadCondition() {
        return null;
    }

    @Override
    public String getPageUrl() {
        return null;
    }
}
