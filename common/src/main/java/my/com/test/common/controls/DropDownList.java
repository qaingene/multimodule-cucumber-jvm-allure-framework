package my.com.test.common.controls;

import my.com.test.common.utils.Common;
import my.com.test.common.webdriver.SeleniumWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Class for interacting with drop down list elements.
 */
public class DropDownList {

    private SeleniumWebDriver webDriver;

    public DropDownList(SeleniumWebDriver webDriver){
        this.webDriver = webDriver;
    }


    /**
     * Select value from drop down list element.
     *
     * @param id    identifier of drop down element
     * @param value value that should be selected from drop down list
     */
    public void selectDropDownValue(String id, String value){
        if(StringUtils.isNotEmpty(value)){
            webDriver.waitUntilElementIsNotClickable(By.id(id));
            webDriver.findElement(By.id(id)).click();
            Common.pause(500);
            try{
                String valueFromDropDownLocator = "//*[@id='" + id + "']/ancestor::form//li[contains(.,'" + value + "')]";
                webDriver.waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath(valueFromDropDownLocator)));
                WebElement dropDownItem = webDriver.findElement(By.xpath(valueFromDropDownLocator));
                webDriver.scrollToElementUsingJs(dropDownItem);
                webDriver.clickUsingJs(dropDownItem);
            }
            catch(ElementNotVisibleException e){
                String valueFromDropDownLocator = "//*[@id='" + id + "']/ancestor::form//li[text()='" + value + "']";
                webDriver.waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath(valueFromDropDownLocator)));
                WebElement dropDownItem = webDriver.findElement(By.xpath(valueFromDropDownLocator));
                webDriver.scrollToElementUsingJs(dropDownItem);
                webDriver.clickUsingJs(dropDownItem);
            }
            Common.pause(500);
        }
    }


    /**
     * Check is value presented in the drop down list
     * @param dropDownId    identifier of drop down element
     * @param value value
     * @return true if value is presented and false if value in not presented
     */
    public boolean isValuePresentedInTheDropDownList(String dropDownId, String value){
        webDriver.waitUntilElementIsNotClickable(By.id(dropDownId));
        webDriver.findElement(By.id(dropDownId)).click();
        Common.pause(500);
        String valueFromDropDownLocator = "//*[@id='" + dropDownId + "']/ancestor::form//li[contains(.,'" + value + "')]";
        return webDriver.isElementDisplayed(By.xpath(valueFromDropDownLocator));
    }

    /**
     * Select strict value from drop down list element.
     *
     * @param id    identifier of drop down element
     * @param value value that should be selected from drop down list
     */
    public void selectDropDownStrictValue(String id, String value){
        if(StringUtils.isNotEmpty(value)){
            webDriver.waitUntilElementIsNotClickable(By.id(id));
            webDriver.findElement(By.id(id)).click();
            Common.pause(500);
            try{
                String valueFromDropDownLocator = "//*[@id='" + id + "']/ancestor::form//li[text()='" + value + "']";
                webDriver.waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath(valueFromDropDownLocator)));
                WebElement dropDownItem = webDriver.findElement(By.xpath(valueFromDropDownLocator));
                webDriver.scrollToElementUsingJs(dropDownItem);
                webDriver.clickUsingJs(dropDownItem);
            }
            catch(ElementNotVisibleException e){
                String valueFromDropDownLocator = "//*[@id='" + id + "']/ancestor::form//li[text()='" + value + "']";
                webDriver.waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath(valueFromDropDownLocator)));
                WebElement dropDownItem = webDriver.findElement(By.xpath(valueFromDropDownLocator));
                webDriver.scrollToElementUsingJs(dropDownItem);
                webDriver.clickUsingJs(dropDownItem);
            }
            Common.pause(500);
        }
    }

    /**
     * Clear default value and select value from drop down list element.
     *
     * @param id    identifier of drop down element
     * @param value value that should be selected from drop down list
     */
    public void clearAndSelectDropDownValue(String id, String value){
        if(StringUtils.isNotEmpty(value)){
            webDriver.waitUntilElementIsNotClickable(By.id(id));
            webDriver.findElement(By.id(id)).click();
            Common.pause(500);
            try{
                webDriver.findElement(By.id(id)).clear();
                webDriver.findElement(By.id(id)).sendKeys(value);
                String valueFromDropDownLocator = "//*[@id='" + id + "']/ancestor::form//li[contains(.,'" + value + "')]";
                webDriver.waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath(valueFromDropDownLocator)));
                WebElement dropDownItem = webDriver.findElement(By.xpath(valueFromDropDownLocator));
                webDriver.scrollToElementUsingJs(dropDownItem);
                webDriver.clickUsingJs(dropDownItem);
            }
            catch(ElementNotVisibleException e){
                String valueFromDropDownLocator = "//*[@id='" + id + "']/ancestor::form//li[text()='" + value + "']";
                webDriver.waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath(valueFromDropDownLocator)));
                WebElement dropDownItem = webDriver.findElement(By.xpath(valueFromDropDownLocator));
                webDriver.scrollToElementUsingJs(dropDownItem);
                webDriver.clickUsingJs(dropDownItem);
            }
            Common.pause(500);
        }
    }
}
