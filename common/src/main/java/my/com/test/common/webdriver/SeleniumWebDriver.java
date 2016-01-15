package my.com.test.common.webdriver;

import my.com.test.common.utils.Common;
import com.google.common.base.Function;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Main class for interact with web elements on the page.
 */
public class SeleniumWebDriver implements WebDriver, SearchContext{

    private final WebDriver webdriver;
    private final Wait<WebDriver> driverWait;
    private final Wait<WebDriver> driverLongWait;
    private static final int ELEMENT_LOAD_TIMEOUT_SEC = 30;
    private static final int ELEMENT_LOAD_LONG_TIMEOUT_SEC = 100;
    private static final int REFRESH_RATE_MS = 500;
    private static final int PAGE_LOAD_TIMEOUT_SEC = 60;
    private static final int SCRIPT_LOAD_TIMEOUT_SEC = 30;
    private Actions action;
    private String browserName;

    public SeleniumWebDriver(BrowserType browserType){
         browserName = browserType.name();
        //Instantiate driver object
        switch (browserType) {
            case CHROME:
                webdriver = new ChromeDriver(generateDesiredCapabilities(browserType));
                break;
            case IE:
                webdriver = new InternetExplorerDriver(generateDesiredCapabilities(browserType));
                break;
            case REMOTE_CHROME:
                webdriver = getRemoveWebDriver(browserType);
                break;
            case REMOTE_IE7:
                webdriver = getRemoveWebDriver(browserType);
                break;
            case REMOTE_IE8:
                webdriver = getRemoveWebDriver(browserType);
                break;
            case REMOTE_IE9:
                webdriver = getRemoveWebDriver(browserType);
                break;
            case REMOTE_IE10:
                webdriver = getRemoveWebDriver(browserType);
                break;
            case REMOTE_IE11:
                webdriver = getRemoveWebDriver(browserType);
                break;
            case REMOTE_FIREFOX:
                webdriver = getRemoveWebDriver(browserType);
                break;
            case FIREFOX:
                webdriver = new FirefoxDriver(generateDesiredCapabilities(browserType));
                break;
            default:
                webdriver = new FirefoxDriver(generateDesiredCapabilities(browserType));
                break;
        }

//        webdriver.manage().window().maximize();

        useAlternativeMaximizeWindowMethod();

        webdriver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT_SEC, TimeUnit.SECONDS);
        webdriver.manage().timeouts().setScriptTimeout(SCRIPT_LOAD_TIMEOUT_SEC, TimeUnit.SECONDS);
        this.driverWait = new WebDriverWait(this.webdriver, ELEMENT_LOAD_TIMEOUT_SEC, REFRESH_RATE_MS)
                                            .ignoring(WebDriverException.class, StaleElementReferenceException.class);
        this.driverLongWait = new WebDriverWait(this.webdriver, ELEMENT_LOAD_LONG_TIMEOUT_SEC, REFRESH_RATE_MS)
                .ignoring(WebDriverException.class, StaleElementReferenceException.class);
    }

    private URL getRemoteUrl(){
        URL remoteUrl = null;
        try {
            remoteUrl = new URL(Common.getConfigValue("selenium.grid.hub.address") + "/wd/hub" );
        } catch (MalformedURLException e) { };
        return remoteUrl;
    }

    private RemoteWebDriver getRemoveWebDriver(BrowserType browserType){
        return new RemoteWebDriver(getRemoteUrl(), generateDesiredCapabilities(browserType));
    }

    private static DesiredCapabilities generateDesiredCapabilities(BrowserType capabilityType) {
        DesiredCapabilities capabilities;
        HashMap<String, String> chromePreferences;

        switch (capabilityType) {
            case CHROME:
                capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
                chromePreferences = new HashMap<String, String>();
                chromePreferences.put("profile.password_manager_enabled", "false");
                capabilities.setCapability("chrome.prefs", chromePreferences);
                break;
            case REMOTE_CHROME:
                capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
                chromePreferences = new HashMap<String, String>();
                chromePreferences.put("profile.password_manager_enabled", "false");
                capabilities.setCapability("chrome.prefs", chromePreferences);
                break;
            case IE:
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
//                capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
                break;
            case REMOTE_IE7:
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability("version", "7.0");
                break;
            case REMOTE_IE8:
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability("version", "8.0");
                break;
            case REMOTE_IE9:
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability("version", "9.0");
//                capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
                break;
            case REMOTE_IE10:
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability("version", "10.0");
                break;
            case REMOTE_IE11:
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability("version", "11.0");
                break;
            case REMOTE_FIREFOX:
                capabilities = DesiredCapabilities.firefox();
                FirefoxProfile remote_profile = new FirefoxProfile();
                capabilities.setCapability(FirefoxDriver.PROFILE, remote_profile);
            default:
                capabilities = DesiredCapabilities.firefox();

//                URL fireBug = Thread.currentThread().getContextClassLoader().getResource("ffAddOns/firebug-1.12.8-fx.xpi");
//                URL firePath = Thread.currentThread().getContextClassLoader().getResource("ffAddOns/firepath-0.9.7-fx.xpi");
                FirefoxProfile profile = new FirefoxProfile();
//                try {
//                    profile.addExtension(new File(Common.getSharedResource("ffAddOns/firebug-1.12.8-fx.xpi")));
//                    profile.addExtension(new File(Common.getSharedResource("ffAddOns/firepath-0.9.7-fx.xpi")));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                capabilities.setCapability(FirefoxDriver.PROFILE, profile);
                break;
        }

        return capabilities;
    }

    public void clearAndType(By by, String text){
        try {
            waitUntilElementIsNotVisible(by);
            WebElement element = findElement(by);
            element.click();
            element.clear();
            if (!text.contains("empty")) {
                if (text.contains("<space>")) {
                    element.sendKeys(Keys.SPACE);
                } else {
                    element.sendKeys(text);
                }
            }
        } catch (WebDriverException e){
            //* For readability tests ( to avoid issue - WebDriverException: unknown error: Element is not clickable at point)
            Common.pause(500);
            waitUntilElementIsNotVisible(by);
            WebElement element = findElement(by);
            element.click();
            element.clear();
            if (!text.contains("empty")) {
                if (text.contains("<space>")) {
                    element.sendKeys(Keys.SPACE);
                } else {
                    element.sendKeys(text);
                }
            }
        }
    }

    public void type(By by, String text){
        WebElement element = findElement(by);
        element.sendKeys(text);
    }

    public void click(By locator){
        WebElement element = findElement(locator);
        waitUntil(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void get(String s) {
        webdriver.get(s);
    }

    public String getCurrentUrl() {
        return webdriver.getCurrentUrl();
    }

    public String getTitle() {
        return webdriver.getTitle();
    }

    public List<WebElement> findElements(By by) {
        return webdriver.findElements(by);
    }

    public WebElement findElement(By by) {
        return webdriver.findElement(by);
    }

    public String getPageSource() {
        return webdriver.getPageSource();
    }

    public void close() {
        webdriver.close();
    }

    public void quit() {
        webdriver.quit();
    }

    public Set<String> getWindowHandles() {
        return webdriver.getWindowHandles();
    }

    public String getWindowHandle() {
        return webdriver.getWindowHandle();
    }

    public TargetLocator switchTo() {
        return webdriver.switchTo();
    }

    public Navigation navigate() {
        return webdriver.navigate();
    }

    public Options manage() {
        return webdriver.manage();
    }

    public <V> V waitUntil(Function<? super WebDriver, V> function) {
        return waitUntil(function, "");
    }

    public <V> V waitUntil(Function<? super WebDriver, V> function, String timeoutMessage) {
        Object result;
        try {
            result = driverWait.until(function);
        } catch (TimeoutException timeException) {
            throw new TimeoutException(timeException.getMessage() +
                    "\nTimeOut while wait Until " + timeoutMessage, timeException.getCause());
        }
        return (V) result;
    }

    public <V> V longWaitUntil(Function<? super WebDriver, V> function, String timeoutMessage) {
        Object result;
        try {
            result = driverLongWait.until(function);
        } catch (TimeoutException timeException) {
            throw new TimeoutException(timeException.getMessage() +
                    "\nTimeOut while wait Until " + timeoutMessage, timeException.getCause());
        }
        return (V) result;
    }

    public WebElement waitUntilElementIsNotClickable(By by) {
        return waitUntil(ExpectedConditions.elementToBeClickable(by), "Element is still not clickable");
    }

    public WebElement waitUntilElementIsNotVisible(By by) {
        return waitUntil(ExpectedConditions.visibilityOfElementLocated(by), "Element is still invisible");
    }

    public boolean waitUntilElementIsVisible(By by) {
//        return waitUntil(ExpectedConditions.invisibilityOfElementLocated(by), "Element is still visible");
        return invisibilityOfElementLocated(by);
    }

    public boolean invisibilityOfElementLocated(By locator) {
        int waitingTime = 300;
        Common.pause(500);
        for(int i=0; i<waitingTime; i++){
            try {
                if(this.isElementDisplayed(locator)){
                    Common.pause(1000);
                }
                else{
                    return true;
                }
            } catch (NoSuchElementException var3) {
                return Boolean.valueOf(true);
            } catch (StaleElementReferenceException var4) {
                return Boolean.valueOf(true);
            }
        }
        throw new WebDriverException(String.format("%s - element is still visible", locator));
    }

    public boolean isElementDisplayed(By by){
        boolean elementVisible = false;
        if (isElementPresent(by)) {
            try {
                elementVisible = this.findElement(by).isDisplayed();
            } catch (WebDriverException e) {
                elementVisible = false;
            }
        }
        return elementVisible;
    }

    public boolean isElementPresent(By by){

        return this.findElements(by).size() > 0;
    }

    public JavascriptExecutor getJsExecutor() {
        return (JavascriptExecutor) webdriver;
    }

    public Actions getActionsExecutor(){
        if(action == null){
            action = new Actions(webdriver);
        }
        return action;
    }

    public void setAttributeUsingJs(String attribute, String value, WebElement element) {
        getJsExecutor().executeScript("arguments[0].setAttribute(\"" + attribute + "\",\"" + value + "\")", element);
    }

    public void setFocusUsingJs(WebElement element){
        getJsExecutor().executeScript("arguments[0].focus();", element);
    }

    public void moveToElementUsingJs(WebElement element){
        getJsExecutor().executeScript("window.scrollTo(0," + element.getLocation().getY() + ")");
    }

    public void scrollToElementUsingJs(WebElement element){
        try {
            getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", element);
        }catch (StaleElementReferenceException e){
            Common.pause(1000);
            getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", element);
        }
    }

    public byte[] makeScreenshot(){
        byte[] screenshot = null;
        String remote = System.getProperty("remote");
        if("true".equalsIgnoreCase(remote)){
            WebDriver augmentedDriver = new Augmenter().augment(webdriver);
            screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);
        }
        else{
            screenshot = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.BYTES);
        }
        return screenshot;
    }

    public void check(By by){
        WebElement element = webdriver.findElement(by);
        if(!element.isSelected()){
           element.click();
        }
    }

    public void unCheck(By by){
        WebElement element = webdriver.findElement(by);
        if(element.isSelected()){
            element.click();
        }
    }

    public void interactWithCheckBox(By by, String action){

        if(action.equalsIgnoreCase("checked")){
            check(by);
        }
        else if(action.equalsIgnoreCase("unchecked")){
            unCheck(by);
        }
        else{
            throw new IllegalArgumentException(String.format(
                    "'%s' is not supported, please use 'checked' or 'unchecked' options", action));
        }
    }

    public boolean isElementEditable(By by){
        boolean isCheckBoxEditable = true;
        WebElement element = webdriver.findElement(by);
        String disabled = element.getAttribute("disabled");
        if(StringUtils.isNotEmpty(disabled) && disabled.equalsIgnoreCase("true")){
           isCheckBoxEditable = false;
        }
        return isCheckBoxEditable;
    }

    public boolean isFieldEditable(By by){
        boolean isFieldEditable = true;
        WebElement element = webdriver.findElement(by);
        String type = element.getAttribute("type");
        if(StringUtils.isEmpty(type)){
            isFieldEditable = false;
        }
        return isFieldEditable;
    }

    public boolean isAlertPresent(){
        try{
            webdriver.switchTo().alert();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public String getSessionId(){
        return String.valueOf(((RemoteWebDriver) webdriver).getSessionId());
    }

    /**
     * Check whether checkbox is checked
     * @return String "checked" or "unchecked"
     */
    public String isCheckBoxChecked(By by){
        Boolean checked = webdriver.findElement(by).isSelected();
        if (checked)
            return "checked";
        else
            return "unchecked";
    }


    /**
     * Get value from field.
     * @return String field value.
     */
    public String getFieldValue(By by){
        waitUntilElementIsNotVisible(by);
        try {
            webdriver.findElement(by).getAttribute("value");
        } catch (StaleElementReferenceException e){
            Common.pause(1000);
            webdriver.findElement(by).getAttribute("value");
        }
        return webdriver.findElement(by).getAttribute("value");
    }

    /**
     * Get text from uneditable field.
     * @return String field text.
     */
    public String getFieldText(By by){
        return webdriver.findElement(by).getText();
    }

    /**
     * Click on element using Java Script
     * @param element Element to click
     */
    public void clickUsingJs(WebElement element){
        try {
            getJsExecutor().executeScript("arguments[0].click();", element);
        } catch (org.openqa.selenium.StaleElementReferenceException e){
            Common.pause(2000);
            getJsExecutor().executeScript("arguments[0].click();", element);
        }
    }

    public void moveMouseOverElement(WebElement element) {
        getActionsExecutor().moveToElement(element).build().perform();
//        element.click();
    }

    public void selectDropDownValue(By dropDownLocator, String value) {
        Select select = new Select(findElement(dropDownLocator));
        select.selectByVisibleText(value);
    }

    public String getBrowserName(){
        return browserName;
    }

    private void useAlternativeMaximizeWindowMethod(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = (int) toolkit.getScreenSize().getWidth();
        int height = (int)toolkit.getScreenSize().getHeight();
        webdriver.manage().window().setSize(new Dimension(width,height));
        webdriver.manage().window().maximize();
    }

    public void scrollBottomOfPage(){
        Actions actions = new Actions(webdriver);
        actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
    }

    public void scrollTopOfPage(){
        Actions actions = new Actions(webdriver);
        actions.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).perform();
    }

    public String getPropertyOfElement(By locator, String property) {
        String elementId = webdriver.findElement(locator).getAttribute("id");
        String resultValue = "";
        try {
            resultValue = (String) getJsExecutor().executeScript("return $("+elementId+").prop('"+property+"');");
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            Common.pause(2000);
            resultValue = (String) getJsExecutor().executeScript("return $("+elementId+").prop('"+property+"');");
        }
        return resultValue;
    }

    public void setPropertyForElement(By locator, String property, String value){
        String elementId = webdriver.findElement(locator).getAttribute("id");
        try{
            getJsExecutor().executeScript("$("+elementId+").prop('"+property+"', '"+value+"')");
        } catch (org.openqa.selenium.StaleElementReferenceException e ){
            Common.pause(2000);
            getJsExecutor().executeScript("$("+elementId+").prop('"+property+"', '"+value+"')");
        }
    }

    /**
     *
     * @param elementLocator element locator
     * @return id value or throw IllegalArgumentException
     */
    public String getElementIdByLocator(By elementLocator){
        waitUntilElementIsNotVisible(elementLocator);
        String elementId = findElement(elementLocator).getAttribute("id");
        if(elementId!=null){
         return elementId;
        }
        else{
            throw new IllegalArgumentException(String.format("%s element has not 'id' attribute", elementLocator));
        }
    }

    /**
     * Method checks if element displayed or not with expected condition
     * @param condition expected condition, "should" or "should not"
     * @param elementLocator locator of element
     * @return true or false
     */
    public boolean isElementDisplayed (String condition, By elementLocator) {
        if (condition.equalsIgnoreCase("should")) {
            try {
                this.waitUntilElementIsNotVisible(elementLocator);
                return this.isElementDisplayed(elementLocator);
            } catch (Exception e) {
                return false;
            }
        }
        else if (condition.equalsIgnoreCase("should not")) {
            return this.isElementDisplayed(elementLocator);
        }
        else {
            throw new IllegalArgumentException(String.format("%s - unsupported condition", condition));
        }
    }

    /**
     * Get visible element from the list of elements with same locator
     * @param locator locator of element
     * @return WebElement object
     */
    public WebElement getVisibleWebElementByLocator(By locator){
        WebElement visibleSaveButtonElement = null;
        List<WebElement> webElementList = findElements(locator);
        if(webElementList.size()!=0){
            for(WebElement element: webElementList){
                if(element.isDisplayed()){
                    visibleSaveButtonElement = element;
                }
            }
        }
        else{
            throw new IllegalArgumentException(String.format("%s - element is absent", locator));
        }
        return visibleSaveButtonElement;
    }
}
