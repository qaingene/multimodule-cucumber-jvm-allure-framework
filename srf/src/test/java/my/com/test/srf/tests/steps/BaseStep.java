package my.com.test.srf.tests.steps;

import my.com.test.common.utils.Common;
import my.com.test.srf.pages.LoginPage;
import my.com.test.srf.tests.SuiteRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that includes all base method that are related for SRF application state.
 */
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

    /**
     * Get clean app state by navigating to login page and removing cookies.
     */
    public static void getCleanState(){

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

        String currentURL = getSuite().getDriver().getCurrentUrl();

        getSuite().getDriver().get(Common.getTestEnv());
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
        uniqueValue = value.replace("-", "");
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
            value = getUniqueValue(email[0]) + "@" + email[1];
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

    public String getUniqueNotificationText(String text) {
        Matcher m = Pattern.compile("\'([^\']*)\'").matcher(text);
        while(m.find()) {
            text = text.replace("\'" + m.group(1) + "\'", getTextFieldValue(m.group(1)));
        }
        return text;
    }

    public ArrayList<String> sortList(ArrayList<String> unsortedList, String sortType) {
        ArrayList<String> sortedList = new ArrayList<String>();
        for(String value : unsortedList) {
            sortedList.add(value);
        }
        Collections.sort(sortedList, String.CASE_INSENSITIVE_ORDER);
        if(sortType.equalsIgnoreCase("descending")) {
            Collections.reverse(sortedList);
        }
        return sortedList;
    }
}
