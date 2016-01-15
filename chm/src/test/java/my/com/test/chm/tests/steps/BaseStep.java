package my.com.test.chm.tests.steps;


import my.com.test.common.utils.Common;
import my.com.test.chm.pages.LoginPage;
import my.com.test.chm.tests.SuiteRunner;
import org.openqa.selenium.WebDriverException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseStep {

    private static SuiteRunner suiteRunner;
    private static String uniqueValue;
    private static String randomValue;

    public static SuiteRunner getSuite(){
        if(suiteRunner == null){
            suiteRunner = new SuiteRunner();
        }
        return suiteRunner;
    }

    public static SuiteRunner quit(){
        return suiteRunner = null;
    }

    /**
     * Get clean app state by navigating to login page and removing cookies.
     */
    public static void getCleanState(){

        String pageTitle;
        if(getSuite().getDriver().isAlertPresent()){
            String text = getSuite().getDriver().switchTo().alert().getText();
            getSuite().getDriver().switchTo().alert().accept();
            throw new IllegalStateException(String.format("Unexpected alert with text: '%s'", text));
        }
        try{
            getSuite().getDriver().manage().deleteAllCookies();
        }
        catch (org.openqa.selenium.WebDriverException e){
            System.out.println("Cookies were not deleted properly:" + e.getMessage());
        }

        pageTitle = getSuite().getDriver().getTitle();
        if(pageTitle.contains("Login") || pageTitle.contains("WebDriver")){
            getSuite().getPageInit().navigateToPage(LoginPage.class);
        }
        else{
            try {
                getSuite().getDriver().get(Common.getTestEnv() + "/auth.jsp");
            }
            catch (WebDriverException e) {
                getSuite().getDriver().navigate().refresh();
                getSuite().getDriver().get(Common.getTestEnv() + "/auth.jsp");
            }
        }
    }

    /**
     * Get unique value for test run.
     * @param value value to convert to unique value
     * @return      String converted unique value
     */
    public String getUniqueValue(String value){

        if(value.equalsIgnoreCase("uniqueValue")){
            value = uniqueValue;
        }
        else if(value.contains("pre-setup")){
            value = getPreSetupValue(value);
        }
        else if(value.contains("random")){
           String[] parsedString = value.split("\\+");
           int numberOfRandomSymbols = Common.getDigitFromString(parsedString[1]);
           value = parsedString[0].trim() + randomValue.substring(0, numberOfRandomSymbols);
        }
        else if(value.contains("pre-browser")){
            value = generateCodeValueAccordingToBrowserType(value);
        }
        else{
            value = value + uniqueValue;
        }
        return value;
    }

    /**
     * Set unic value for test run.
     * @param value value to set
     */
    public static void setUniqueValue(String value){
        uniqueValue = value;
    }

    /*
      Generate Random string that contains more than 1000 symbols.
     */
    public static void setRandomString(){
       randomValue = Common.generateRandomBasedOnString("Generate String more than 1000");
    }

    public String getTextFieldValue(String value){
        if(value.contains("@")){
            String[] email = value.split("@");
            if(email.length > 1){
                value = getUniqueValue(email[0]) + "@" + email[1];
            }
        }
        else{
            value = getUniqueValue(value);
        }
        return value;
    }

    /**
     * Get pre setup value for static data.
     * @param object value to convert
     * @return       String converted value
     */
    public String getPreSetupValue(String object){
        return object.replace("pre-setup", "").trim();
    }

    /**
     * Generate random value for code that contains only 2 symbols.
     * @param code value to convert
     * @return     String converted value
     */
    public String generateCodeValue(String code){
        code = getUniqueValue(code);
        Pattern pattern = Pattern.compile("(-\\w)");
        Matcher matcher = pattern.matcher(code);
        if(matcher.find()){
            code = code.replace(matcher.group(0), "");
        }
        return code;
    }

    /**
     * Generate value for code that contains only 2 symbols according to browser name.
     * @param code value to convert
     * @return     String converted value
     */
    public String generateCodeValueAccordingToBrowserType(String code){
        code = code.replace("pre-browser", "").trim();

        String browser = System.getProperty("browser");
        if(browser == null){
            return generateCodeValue(code);
        }
        else if(browser.equalsIgnoreCase("ie7")){
            return code+"7";
        }
        else if(browser.equalsIgnoreCase("ie8")){
            return code+"8";
        }
        else if(browser.equalsIgnoreCase("ie9")){
            return code+"9";
        }
        else if(browser.equalsIgnoreCase("ie10")){
            return code+"0";
        }
        else if(browser.equalsIgnoreCase("ie11")){
            return code+"1";
        }
        else if(browser.equalsIgnoreCase("ie11")){
            return code+"1";
        }
        else if(browser.equalsIgnoreCase("chrome")){
            return code+"c";
        }
        else if(browser.equalsIgnoreCase("firefox")){
            return code+"f";
        }
        else return generateCodeValue(code);
    }


    /**
     * Adding unique surname and first name to message.
     * @param message message from step
     * @return     message from step with unique surname and first name
     */
    public String generateTextOfConfirmationMessageWithSpecifiedPerson(String message){
        String[] messageText = message.split("'");
        if(messageText.length>4){
            String surname = getTextFieldValue(messageText[1]);
            String firstName = getTextFieldValue(messageText[3]);
            return surname+", "+firstName+messageText[4];
        }
        else return message;
    }
}
