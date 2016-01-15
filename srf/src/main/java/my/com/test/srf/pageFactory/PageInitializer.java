package my.com.test.srf.pageFactory;

import my.com.test.common.pageFactory.Page;
import my.com.test.common.webdriver.SeleniumWebDriver;
import my.com.test.srf.pages.LoginPage;
import my.com.test.srf.pages.UserBasePage;


/**
 * Class for make a bridge between pages and Steps for SRF application.
 */
public class PageInitializer extends Page {

    public PageInitializer(SeleniumWebDriver webDriver) {
        super(webDriver);
    }

    public LoginPage getLoginPage() {
        return getPage(LoginPage.class);
    }

    public UserBasePage getUserBasePage()   {
        return getPage(UserBasePage.class);
    }

}
