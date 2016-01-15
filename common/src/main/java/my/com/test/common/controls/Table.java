package my.com.test.common.controls;

import my.com.test.common.webdriver.SeleniumWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for interacting with table elements.
 */
public class Table {

    protected SeleniumWebDriver webDriver;
    private By columnHeaderLocator = By.xpath("//*[@class='rgHeader']");

    public Table(SeleniumWebDriver webDriver){
        this.webDriver = webDriver;
    }

    /**
     * Get Row element that match criteria.
     * @param columnValue  column value
     * @param by           general locator for all rows from the table
     * @param columnNumber column number that contains specific column value
     * @return            List<WebElement> rows
     */
    public List<WebElement> getRows(String columnValue, By by, int columnNumber){
        webDriver.waitUntilElementIsNotVisible(by);
        List<WebElement> rows, rowToFind;
        rowToFind = new ArrayList<WebElement>();
        String value;
        rows = webDriver.findElements(by);

        for(WebElement row : rows){
            value = this.getColumnTextFromRow(row, columnNumber).trim();
            if(value.equalsIgnoreCase(columnValue)){
                rowToFind.add(row);
            }
        }

        if(rowToFind.size() == 0){
            throw new IllegalStateException(
                    String.format("Row that contains '%s' does not exist", columnValue));
        }
        return rowToFind;
    }

    /**
     * Get Row element that match criteria.
     * @param columnValue  column value
     * @param by           general locator for all rows from the table
     * @param columnNumber column number that contains specific column value
     * @return            List<WebElement> rows
     */
    public List<WebElement> getRowThatContainsColumnValue(String columnValue, By by, int columnNumber){

        List<WebElement> rows, rowToFind;
        rowToFind = new ArrayList<WebElement>();
        String value;
        rows = webDriver.findElements(by);

        for(WebElement row : rows){
            value = this.getColumnTextFromRow(row, columnNumber).trim();
            if(value.toLowerCase().contains(columnValue.toLowerCase())){
                rowToFind.add(row);
            }
        }

        if(rowToFind.size() == 0){
            throw new IllegalStateException(
                    String.format("Row that contains '%s' does not exist", columnValue));
        }
        return rowToFind;
    }

    /**
     * Get Row element that match criteria.
     * @param columnValue  column value
     * @param by           general locator for all rows from the table
     * @param columnNumber column number that contains specific column value
     * @return            List<WebElement> rows
     */
    public List<WebElement> getRowsByValue(String columnValue, By by, int columnNumber){

        List<WebElement> rows, rowToFind;
        rowToFind = new ArrayList<WebElement>();
        String value;
        rows = webDriver.findElements(by);

        for(WebElement row : rows){
            value = this.getColumnValueFromRow(row, columnNumber).trim();
            if(value.equalsIgnoreCase(columnValue)){
                rowToFind.add(row);
            }
        }

        if(rowToFind.size() == 0){
            throw new IllegalStateException(
                    String.format("Row that contains '%s' does not exist", columnValue));
        }
        return rowToFind;
    }

    public WebElement getRow(String columnValue1, String columnValue2, By by, int columnNumber1, int columnNumber2){
        List<WebElement> rows;
        WebElement userRow = null;
        String surnameValue, sourceText;
        rows = webDriver.findElements(by);

        end:
        for(WebElement row : rows){
            surnameValue = this.getColumnTextFromRow(row, columnNumber1); //get text from Surname Column
            if(surnameValue.equalsIgnoreCase(columnValue1)){
                sourceText = this.getColumnTextFromRow(row, columnNumber2); //get text from Source Column
                if(sourceText.equalsIgnoreCase(columnValue2)){
                    userRow = row;
                    break end;
                }
            }
        }
        if(userRow == null){
            throw new IllegalStateException(
                    String.format(
                            "User row that contains '%s' and '%s' does not exist", columnValue1, columnValue2));
        }
        return userRow;
    }

    /**
     * Get text from any column from user row in the search form.
     * @param row          user row
     * @param columnNumber column number
     * @return             column text
     */
    public String getColumnTextFromRow(WebElement row, int columnNumber){
        try{
            return row.findElement(By.xpath(".//td[" + Integer.toString(columnNumber) + "]")).getText();
        }
        catch (StaleElementReferenceException e){
            try {
                Thread.sleep(2000);
                return row.findElement(By.xpath(".//td[" + Integer.toString(columnNumber) + "]")).getText();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        return row.findElement(By.xpath(".//td[" + Integer.toString(columnNumber) + "]")).getText();
    }

    /**
     * Get value from any column from user row in the search form.
     * @param row          user row
     * @param columnNumber column number
     * @return             column text
     */
    public String getColumnValueFromRow(WebElement row, int columnNumber){
        return row.findElement(By.xpath(".//td[" + Integer.toString(columnNumber) + "]/input")).getAttribute("value");
    }

    /**
     * Get value from any column from user row in the search form.
     * @param row          user row
     * @param locator  locator row
     * @return             column text
     */
    public String getColumnValueFromRow(WebElement row, By locator){
        return row.findElement(locator).getAttribute("value");
    }

    /**
     * Get text from any column from user row in the search form.
     * @param row          user row
     * @param columnNumber column number
     * @return             column text
     */
    public String getColumnCheckBoxStateFromIcon(WebElement row, int columnNumber){
        String icon = row.findElement(By.xpath(".//td[" + Integer.toString(columnNumber) + "]/img")).getAttribute("src");
        if(icon.contains("images/Tick.gif")){
            return "checked";
        }
        else if (icon.contains("images/NoTick.gif")){
            return "unchecked";
        }
        else throw new IllegalStateException(
                    String.format("'%s' - incorrect image", icon));
    }

    public String getColumnCheckBoxStateFromRow(WebElement row, int columnNumber){
        String checkBoxState = row.findElement(By.xpath(
                ".//td[" + Integer.toString(columnNumber) + "]//input")).getAttribute("checked");
        if(StringUtils.isEmpty(checkBoxState) || !checkBoxState.equalsIgnoreCase("true")){
           checkBoxState = "unchecked";
        }
        else{
            checkBoxState = "checked";
        }
        return checkBoxState;
    }

    /**
     * Click Edit button from specific row.
     * @param row           row webElement
     * @param columnNumber  column number that contains button
     */
    public void clickOnEditButtonFromColumn(WebElement row, int columnNumber){
        clickOnButtonFromColumn(row, columnNumber, "Edit");
    }

    /**
     * Click Edit button from specific row.
     * @param row           row webElement
     * @param columnNumber  column number that contains button
     */
    public void clickOnViewButtonFromColumn(WebElement row, int columnNumber){
        clickOnButtonFromColumn(row, columnNumber, "View");
    }

    /**
     * Click Select button from specific row.
     * @param row           row webElement
     * @param columnNumber  column number that contains button
     */
    public void clickOnSelectButtonFromColumn(WebElement row, int columnNumber){
        clickOnButtonFromColumn(row, columnNumber, "Select");
    }

    /**
     * Click Delete button from specific row.
     * @param row           row webElement
     * @param columnNumber  column number that contains button
     */
    public void clickOnDeleteButtonFromColumn(WebElement row, int columnNumber){
        clickOnButtonFromColumn(row, columnNumber, "Delete");
    }

    /**
     * Click Update button from specific row.
     * @param row           row webElement
     * @param columnNumber  column number that contains button
     */
    public void clickOnUpdateButtonFromColumn(WebElement row, int columnNumber){
        clickOnButtonFromColumn(row, columnNumber, "Update");
    }

    /**
     * Click Insert button from specific row.
     * @param row           row webElement
     * @param columnNumber  column number that contains button
     */
    public void clickOnInsertButtonFromColumn(WebElement row, int columnNumber){
        clickOnButtonFromColumn(row, columnNumber, "Insert");
    }

    /**
     * Click Locate People button from specific row.
     * @param row           row webElement
     * @param columnNumber  column number that contains button
     */
    public void clickOnLocatePeopleButtonFromColumn(WebElement row, int columnNumber){
        clickOnButtonFromColumn(row, columnNumber, "Locate People");
    }

    /**
     * Click Cancel button from specific row.
     * @param row           row webElement
     * @param columnNumber  column number that contains button
     */
    public void clickOnCancelButtonFromColumn(WebElement row, int columnNumber){
        clickOnButtonFromColumn(row, columnNumber, "Cancel");
    }

    /**
     * Click Remove button from specific row.
     * @param row           row webElement
     * @param columnNumber  column number that contains button
     */
    public void clickOnRemoveButtonFromColumn(WebElement row, int columnNumber){
        clickOnButtonFromColumn(row, columnNumber, "Remove");
    }

    /**
     * Click Camps button from specific row.
     * @param row           row webElement
     * @param columnNumber  column number that contains button
     */
    public void clickOnCampsButtonFromColumn(WebElement row, int columnNumber){
        clickOnButtonFromColumn(row, columnNumber, "Camps");
    }

    public void clickOnButtonFromColumn(WebElement row, int columnNumber, String button){
        By linkLocator = By.xpath(".//td['" + Integer.toString(columnNumber) + "']//a[text()='" + button + "']");
        if(button.equalsIgnoreCase("Edit")){
            webDriver.waitUntil(ExpectedConditions.elementToBeClickable(linkLocator));
        }
        webDriver.waitUntilElementIsNotVisible(linkLocator);
        row.findElement(linkLocator).click();
    }

    /**
     * Type value to specific column from the row.
     * @param row           row webElement
     * @param columnNumber  column number to which value should be inputted
     * @param value         value to input
     */
    public void typeValueToColumn(WebElement row, int columnNumber, String value){
        By inputLocator = By.xpath(".//td[" + Integer.toString(columnNumber) + "]//input");
        WebElement inputField = row.findElement(inputLocator);
        inputField.clear();
        inputField.sendKeys(value);
    }

    /**
     * Get value from any column from user row in the search form from table.
     * @param row          user row
     * @param columnNumber column number
     * @param tableIdLocator  tableIdLocator
     * @return             column text
     */
    public String getColumnValueInTableFromRow(String tableIdLocator, WebElement row, int columnNumber){
        return row.findElement(By.xpath("//*[@id='"+tableIdLocator+"']//td[" + Integer.toString(columnNumber) + "]")).getText();
    }

    /**
     * Get Row element that contains column value.
     * @param columnValue  column value
     * @param by           general locator for all rows from the table
     * @param columnNumber column number that contains specific column value
     * @return            List<WebElement> rows
     */
    public List<WebElement> getRowsWhichContainsColumnValue(String columnValue, By by, int columnNumber){

        List<WebElement> rows, rowToFind;
        rowToFind = new ArrayList<WebElement>();
        String value;
        rows = webDriver.findElements(by);

        for(WebElement row : rows){
            value = this.getColumnTextFromRow(row, columnNumber).trim();
            if(value.contains(columnValue)){
                rowToFind.add(row);
            }
        }

        if(rowToFind.size() == 0){
            throw new IllegalStateException(
                    String.format("Row that contains '%s' does not exist", columnValue));
        }
        return rowToFind;
    }

    /**
     * Get index of table column using specified column name
     * @param columnName column name
     * @return index om column
     */
    public int getColumnIndexByColumnName(String columnName){
        List<WebElement> namesOfColumnList = webDriver.findElements(columnHeaderLocator);
        int columnIndex = 1;
        for(WebElement column: namesOfColumnList){
            if (column.getText().equalsIgnoreCase(columnName)){
                return columnIndex;
            }
            else{
                columnIndex++;
            }
        }
        throw new IllegalArgumentException(String.format("%s - unknown column name", columnName));
    }
}
