package my.com.test.common.pages;

import my.com.test.common.utils.Common;
import my.com.test.common.webdriver.SeleniumWebDriver;

import org.openqa.selenium.support.ui.ExpectedCondition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Based abstract class for all pages that contains all supported methods for page initialization
 * and page navigation.
 * @param <T> child class
 */
public abstract class AbstractPage<T> {

    public static final String BASE_URL = Common.getTestEnv();
    protected final SeleniumWebDriver webDriver;

    public AbstractPage(SeleniumWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    /**
     * Get new class instance and wait page load condition.
     * @param clazz class name of the instance that you want to get
     * @param <T>   new class object
     * @return      new class instance
     */
    public <T> T getPage(Class<T> clazz) {

        try {
            try {
                Constructor<T> constructor = clazz.getConstructor(SeleniumWebDriver.class);
                webDriver.switchTo().defaultContent();
                T newPageInstance = constructor.newInstance(webDriver);
                ExpectedCondition pageLoadCondition = ((AbstractPage) newPageInstance).getPageLoadCondition();
                if(pageLoadCondition != null){
                    waitForPageToLoad(pageLoadCondition);
                }
                return newPageInstance;
            } catch (NoSuchMethodException e) {
                return clazz.newInstance();
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    /**
     * Get new class instance, wait page load condition, and navigate to the specific page.
     * @param clazz  class name of the instance that you want to get and navigate to
     * @param <T>    new class object
     * @return       new class instance
     */
    public <T> T navigateToPage(Class<T> clazz) {

        try {
            try {
                Constructor<T> constructor = clazz.getConstructor(SeleniumWebDriver.class);
                T newPageInstance = constructor.newInstance(webDriver);
                webDriver.get(BASE_URL + ((AbstractPage) newPageInstance).getPageUrl());
                ExpectedCondition pageLoadCondition = ((AbstractPage) newPageInstance).getPageLoadCondition();
                waitForPageToLoad(pageLoadCondition);
                return newPageInstance;
            } catch (NoSuchMethodException e) {
                return clazz.newInstance();
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private void waitForPageToLoad(ExpectedCondition pageLoadCondition) {
        webDriver.waitUntil(pageLoadCondition);
    }

    /**
     * Provides condition when page can be considered as fully loaded.
     *
     * @return
     */
    protected abstract ExpectedCondition getPageLoadCondition();

    /**
     * Provides page relative URL/
     *
     * @return
     */
    protected abstract String getPageUrl();


}
