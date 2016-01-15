package my.com.test.common.controls;


import my.com.test.common.utils.Common;
import my.com.test.common.webdriver.SeleniumWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Class for interacting with telerik drop down list component.
 */
public class TelerikDropDownList {

    private SeleniumWebDriver webDriver;

    public TelerikDropDownList(SeleniumWebDriver webDriver){
        this.webDriver = webDriver;
    }

    public void selectDropDownValue(String id, String value){
        String dropDownArrowLocator, partOfListLocator, partOfFieldLocator, valueFromDropDownLocator, partOfLoadingItemLocator;
        dropDownArrowLocator = "_Arrow";
        partOfListLocator = "_DropDown";
        partOfFieldLocator = "_Input";
        partOfLoadingItemLocator = "_LoadingDiv";
        valueFromDropDownLocator = "//*[@id='" + id + partOfListLocator + "']//li[text()='" + value + "']";

//remove this part when issue with search will be fixed
        if(value.contains(" -")){
            String splitValue[] = value.split(" -");
            value = splitValue[0];
        }
        if(value.contains(", ")){
            String splitValue[] = value.split(", ");
            value = splitValue[0];
        }
//
        WebElement dropDownArrow = webDriver.findElement(By.id(id + dropDownArrowLocator));
        webDriver.clickUsingJs(dropDownArrow);
        Common.pause(500);
        webDriver.waitUntilElementIsNotVisible(By.id(id + partOfListLocator));
//        if(!webDriver.findElement(By.id(id + partOfMoreResultsLocator)).getText().contains("Items")){
//            webDriver.click(By.id(id + partOfMoreResultsLocator));
//            Common.pause(500);
//        }

        webDriver.clearAndType(By.id(id + partOfFieldLocator), value);
        Common.pause(500);
        webDriver.waitUntilElementIsVisible(By.id(id + partOfLoadingItemLocator));

        webDriver.waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath(valueFromDropDownLocator)));

        WebElement dropDownItem = webDriver.findElement(By.xpath(valueFromDropDownLocator));
        try{
            webDriver.scrollToElementUsingJs(dropDownItem);
        }
        catch (StaleElementReferenceException e){
            Common.pause(1000);
            webDriver.scrollToElementUsingJs(dropDownItem);
        }
        webDriver.clickUsingJs(dropDownItem);
//        dropDownItem.click();
        Common.pause(500);
        webDriver.waitUntilElementIsVisible(By.id(id + partOfListLocator));
    }
}
