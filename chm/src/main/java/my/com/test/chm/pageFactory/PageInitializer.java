package my.com.test.chm.pageFactory;

import my.com.test.common.pageFactory.Page;
import my.com.test.common.webdriver.SeleniumWebDriver;
import my.com.test.chm.pages.*;

public class PageInitializer extends Page {

    public PageInitializer(SeleniumWebDriver webDriver) {
        super(webDriver);
    }

    public DashboardPage getDashboardPage(){
        return getPage(DashboardPage.class);
    }

    public UserBasePage getUserBasePage(){
        return getPage(UserBasePage.class);
    }

    public LoginPage getLoginPage(){
        return getPage(LoginPage.class);
    }
}

