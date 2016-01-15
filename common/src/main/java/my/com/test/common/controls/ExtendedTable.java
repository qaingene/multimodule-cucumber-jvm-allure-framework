package my.com.test.common.controls;


import my.com.test.common.utils.Common;
import my.com.test.common.webdriver.SeleniumWebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ExtendedTable extends Table{

    private By ajaxSpinnerLocator = By.xpath("//*[@class='raColor raTransp']");
    private By addNewRecordButtonLocator = By.cssSelector("[title='Add new record']");
    private By updateButtonLocator = By.linkText("Update");
    private By refreshButtonLocator = By.cssSelector("[title='Refresh']");

    public ExtendedTable(SeleniumWebDriver webDriver) {
        super(webDriver);
    }

    /**
     * Click Add New Record button.
     */
    public void clickAddNewRecordButton(){
        webDriver.click(addNewRecordButtonLocator);
        webDriver.waitUntilElementIsNotVisible(updateButtonLocator);
    }

    /**
     * Click Refresh button.
     */
    public void clickRefreshButton(){
        webDriver.waitUntil(ExpectedConditions.elementToBeClickable(refreshButtonLocator));
        webDriver.click(refreshButtonLocator);
        waitUntilSpinnerVisible();
    }

    /**
     * Click on the specific button on row.
     * @param row    Row Web Element
     * @param button Button name
     */
    public void clickOnButtonFromRow(WebElement row, String button){
        By buttonLocator = By.xpath(".//td/a[text()='" + button + "']");
        row.findElement(buttonLocator).click();
        if(!webDriver.isAlertPresent()){
            waitUntilSpinnerVisible();
        }
    }

    /**
     * Wait until spinner is Visible on the web page.
     */
    public void waitUntilSpinnerVisible(){
        Common.pause(500);
        try{
            webDriver.waitUntilElementIsVisible(ajaxSpinnerLocator);
        }
        catch (WebDriverException e){
            Common.pause(2000);
            webDriver.waitUntilElementIsVisible(ajaxSpinnerLocator);
        }
    }

    public void typeTextToCell(WebElement row, By by, String text){
        WebElement cell = row.findElement(by);
        cell.click();
        cell.clear();
        cell.sendKeys(text);
    }

    public void typeTextToDropDown(WebElement row, By dropDownLocator, String text){
        WebElement dropDown = row.findElement(dropDownLocator);
        dropDown.click();
        dropDown.clear();
        dropDown.sendKeys(text);
        dropDown.sendKeys(Keys.ENTER);
    }

    public void interactWithCheckBox(WebElement row, By by, String status){
        WebElement checkBox = row.findElement(by);
        if(status.equalsIgnoreCase("checked")){
            if(!checkBox.isSelected()){
                checkBox.click();
            }
        }
        else if(status.equalsIgnoreCase("unchecked")){
            if(checkBox.isSelected()){
                checkBox.click();
            }
        }
    }

    /**
     * Click on the specific icon on row.
     * @param row    Row Web Element
     * @param by    icon locator
     */
    public void clickOnIconFromRow(WebElement row, By by){
        WebElement icon = row.findElement(by);
        webDriver.clickUsingJs(icon);
    }
}
