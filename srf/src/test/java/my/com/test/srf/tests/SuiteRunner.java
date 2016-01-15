package my.com.test.srf.tests;

import my.com.test.common.webdriver.BrowserType;
import my.com.test.common.webdriver.DriverService;
import my.com.test.common.webdriver.SeleniumWebDriver;
import my.com.test.srf.pageFactory.PageInitializer;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to initialize selenium and browsers to run cucumber features.
 */
public class SuiteRunner {

    private PageInitializer pageInitializer;
    private static DriverService driverService;
    private static List<SeleniumWebDriver> webDrivers = Collections.synchronizedList(new ArrayList<SeleniumWebDriver>());
    private static ThreadLocal<SeleniumWebDriver> driverForThread = new ThreadLocal<SeleniumWebDriver>() {

        @Override
        protected SeleniumWebDriver initialValue() {

            SeleniumWebDriver driver = new SeleniumWebDriver(getBrowserType());
            webDrivers.add(driver);
            return driver;
        }
    };

    public SeleniumWebDriver getDriver() {
        return driverForThread.get();
    }

    public PageInitializer getPageInit(){
        if(pageInitializer == null){
            pageInitializer = new PageInitializer(getDriver());
        }
        return pageInitializer;
    }

    public static DriverService getDriverService(){
        if(driverService == null){
            driverService = new DriverService();
        }
        return driverService;
    }

    private static BrowserType getBrowserType(){
        BrowserType browserType = null;
        String browser, remote;
        browser = System.getProperty("browser");
        remote = System.getProperty("remote");
        if("true".equalsIgnoreCase(remote)){
            remote = "remote_";
        }
        else{
            remote = "";
        }

        browser = remote + browser;


        if(browser.equals("null") || StringUtils.isEmpty(browser) || "firefox".equalsIgnoreCase(browser)){
            browserType = BrowserType.FIREFOX;
        }
        else if("ie".equalsIgnoreCase(browser)){
            getDriverService().startIeDriverService();
            browserType = BrowserType.IE;
        }
        else if(browser.equalsIgnoreCase("chrome")){
            getDriverService().startChromeDriverService();
            browserType = BrowserType.CHROME;
        }
        else if(browser.equalsIgnoreCase("remote_chrome")){
            browserType = BrowserType.REMOTE_CHROME;
        }
        else if(browser.equalsIgnoreCase("remote_firefox")){
            browserType = BrowserType.REMOTE_FIREFOX;
        }
        else if(browser.equalsIgnoreCase("remote_ie8")){
            browserType = BrowserType.REMOTE_IE8;
        }
        else if(browser.equalsIgnoreCase("remote_ie9")){
            browserType = BrowserType.REMOTE_IE9;
        }
        else if(browser.equalsIgnoreCase("remote_ie10")){
            browserType = BrowserType.REMOTE_IE10;
        }
        else if(browser.equalsIgnoreCase("remote_ie11")){
            browserType = BrowserType.REMOTE_IE11;
        }
        else{
            throw new IllegalArgumentException(String.format("'%s' - browser is not supported, please use 'ie' or 'chrome'", browser));
        }
        return browserType;
    }

}

