package my.com.test.common.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for different methods not related to selenium/webdriver.
 */
public class Common {

    /**
     * Pause execution for some period of time.
     *
     * @param msec pause in mills seconds
     */
    public static void pause(int msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a configuration value.
     * <p/>
     * Checks for the requested key in the user, host and config resource bundles in that order, returning
     * the value of the first property found.
     *
     * @param key property key
     * @return configuration property value
     * @throws MissingResourceException if the property is not found
     */
    public static String getConfigValue(String key) {
        String username = System.getenv("USERNAME");
        if (username != null) {
            username = username.toLowerCase();
        }

        String hostname = System.getenv("COMPUTERNAME");
        if (hostname != null) {
            hostname = hostname.toLowerCase();
        }

        String hostnameUsername = (hostname == null || hostname.isEmpty() || username == null || username.isEmpty()) ? null : hostname + "-" + username;

        // bundle search order, highest priority first
        String[] baseNames = {hostnameUsername, username, hostname, "config"};

        // search each bundle in order, returning the value from the first bundle that defines the key
        for (String baseName : baseNames) {
            try {
                ResourceBundle config = ResourceBundle.getBundle(baseName);
                if (config != null && config.containsKey(key)) {
                    System.out.printf("%s::%s='%s'\n", baseName, key, config.getString(key));
                    return config.getString(key);
                }
            } catch (Exception e) {
                // ignore missing bundles
            }
        }

        throw new MissingResourceException(String.format("Can't find resource for bundle java.util.PropertyResourceBundle, key %s", key), "Common", key);
    }

    /**
     * Get application url under test.
     *
     * @return application url
     */
    public static String getTestEnv() {

        String env = System.getProperty("env");
        if (StringUtils.isEmpty(env)) {
            env = getConfigValue("app.url");
        }
        return env;
    }



    /**
     * Get browser that is using to run automation tests.
     *
     * @return browser name
     */
    public static String getBrowserUnderTest() {
        return System.getProperty("browser");
    }

    /**
     * Get current date in UTC zone.
     *
     * @return current date
     */
    public static DateTime getDate() {
        return new DateTime().withZone(DateTimeZone.UTC);
    }

    /**
     * Get shifted day in the future based on current date.
     *
     * @param days   days to shift
     * @param format date format
     * @return shifted date in specific format
     */
    public static String getShiftedFutureDateInSpecificFormat(int days, String format) {
        DateTime dateTime = getDate().plusDays(days);
        String date = dateTime.toString(DateTimeFormat.forPattern(format));
        return date;
    }

    /**
     * Get shifted day in the past based on current date.
     *
     * @param format date format
     * @return shifted date in specific format
     */
    public static String getYesterdayDateInSpecificFormat(String format) {
        DateTime dateTime = getDate().minusDays(1);
        String date = dateTime.toString(DateTimeFormat.forPattern(format));
        return date;
    }

    /**
     * Get current day
     *
     * @param format date format
     * @return shifted date in specific format
     */
    public static String getCurrentDateInSpecificFormat(String format) {
        DateTime dateTime = getDate().minusDays(0);
        String date = dateTime.toString(DateTimeFormat.forPattern(format));
        return date;
    }


    /**
     * Get shifted day based on current date using more native set for date.
     *
     * @param date   days to shift (like 'in X day')
     * @param format date format
     * @return shifted date in specific format
     */
    public static String convertAbstractDateToStringInSpecificFormat(String date, String format) {

        date = date.split(" ")[1]; //parse date from the following format 'in X day' to X
        return getShiftedFutureDateInSpecificFormat(Integer.parseInt(date), format);
    }

    /**
     * Gets access for share resources from common module.
     *
     * @param resourcePath path to resource inside resources folder
     * @return absolute path for resource
     */
    public static String getSharedResource(String resourcePath) {

        String pathToSharedResources = File.separator +
                "target" + File.separator +
                "shared_resources" + File.separator + resourcePath;
        File file;
        String modulePath, rootProjectPath, pathToResource;
        file = new File("");   //Dummy file
        modulePath = file.getAbsolutePath();
        rootProjectPath = modulePath.substring(0, modulePath.lastIndexOf(File.separator)); //remove module folder from the path
        pathToResource = rootProjectPath + pathToSharedResources;
        file = new File(pathToResource);
        if (!file.exists()) {
            pathToResource = rootProjectPath + File.separator + "test" + pathToSharedResources; //get resources when running outside of maven
        }
        return pathToResource;
    }

    /**
     * Get user credentials from config file.
     *
     * @param user user name from config
     * @return HashMap that contains "username" and "password" keys
     */
    public static Map<String, String> getUserCredentials(String user) {
        String[] credentials = getConfigValue(user).split("/");
        Map<String, String> credHashMap = new HashMap<String, String>();
        String username, password;
        username = credentials[0];
        password = credentials[1];
        credHashMap.put("username", username);
        credHashMap.put("password", password);
        return credHashMap;
    }

    /**
     * Get display name for a user
     *
     * @param user        The user name.  e.g. "user.admin"
     * @param defaultName Name to return if there is no <user>.name property defined.  Default: the user name.
     * @return The display name for the user, or defaultName if no display name has been configured.
     */
    public static String getUserDisplayName(String user, String defaultName) {
        String displayName = null;
        try {
            String nameKey = user + ".name";
            displayName = getConfigValue(nameKey);
        } catch (Exception e) {
            // if <user>.name property is missing then use defaultName
            displayName = defaultName;
        }

        return displayName;
    }

    /**
     * Get display name for a user
     *
     * @param user The user name.  e.g. "admin"
     * @return The display name for the user, or the user name if no display name has been configured.
     */
    public static String getUserDisplayName(String user) {
        return getUserDisplayName(user, user);
    }

    /**
     * Get current seconds to generate unique value for test data.
     *
     * @return String seconds value
     */
    public static String getCurrentTimeStampString() {

        java.util.Date date = new java.util.Date();

        SimpleDateFormat sd = new SimpleDateFormat("ss");
        TimeZone timeZone = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance(new SimpleTimeZone
                (timeZone.getOffset(date.getTime()), "GMT"));
        sd.setCalendar(cal);
        return sd.format(date);
    }

    /**
     * Generates random value for test data.
     *
     * @return String contains dash and seconds
     */
    public static String generateRandomValue() {
        return "-" + RandomStringUtils.randomAlphabetic(2).toLowerCase();
    }

    /**
     * Get first value from the line that contains brackets.
     *
     * @param text text
     * @return String first value in brackets
     */
    public static String getValueFromBrackets(String text) {
        String value = null;
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            value = matcher.group(1).split(",")[0];
        }
        return value;
    }

    /**
     * Get first value from the line that contains single quotes.
     *
     * @param text text
     * @return String first value in quotes
     */
    public static String getValueFromSingleQuotes(String text) {
        String value = null;
        Pattern pattern = Pattern.compile("'(.*?)'");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            value = matcher.group(1);
        }
        return value;
    }

    /**
     * Generate random string that can be used to check fields lengths.
     *
     * @param text text
     * @return String random string
     */
    public static String generateRandomBasedOnString(String text) {
        int value = getDigitFromString(text);
        return RandomStringUtils.randomAlphabetic(value + 1);
    }

    /**
     * Get digit from specified string.
     *
     * @param text String to parse
     * @return int digit
     */
    public static int getDigitFromString(String text) {
        int value = 0;
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            value = Integer.parseInt(matcher.group(0));
        }
        return value;
    }

//    /**
//    public static String getStringMoreValue(String value){
//        if(value.contains("String more than")){
//            value = Common.generateRandomBasedOnString(value);
//        }
//        else if(value.contains("pre-setup")){
//            value = value.replace("pre-setup", "").trim();
//        }
//        return value;
//    }


    /**
     * Get number of the day of week
     *
     * @param dayOfWeek
     * @return number of the day of week
     */

    private static int getNumberOfDayOfWeek(String dayOfWeek) {
        int numberOfDayOfWeek;
        if (dayOfWeek.equalsIgnoreCase("monday")) {
            numberOfDayOfWeek = 1;
        } else if (dayOfWeek.equalsIgnoreCase("tuesday")) {
            numberOfDayOfWeek = 2;
        } else if (dayOfWeek.equalsIgnoreCase("wednesday")) {
            numberOfDayOfWeek = 3;
        } else if (dayOfWeek.equalsIgnoreCase("thursday")) {
            numberOfDayOfWeek = 4;
        } else if (dayOfWeek.equalsIgnoreCase("friday")) {
            numberOfDayOfWeek = 5;
        } else if (dayOfWeek.equalsIgnoreCase("saturday")) {
            numberOfDayOfWeek = 6;
        } else if (dayOfWeek.equalsIgnoreCase("sunday")) {
            numberOfDayOfWeek = 7;
        } else {
            throw new IllegalArgumentException(String.format(
                    "'%s' - the day of week is incorrect", dayOfWeek));
        }
        return numberOfDayOfWeek;
    }

    /**
     * Get date of the next selected day of week
     *
     * @param dayOfWeek day of week
     * @return date of day of week
     */
    private static LocalDate calcNextDayOfWeek(String dayOfWeek) {
        LocalDate today = new LocalDate(DateTimeZone.forID("Australia/Melbourne"));

        if (today.getDayOfWeek() < getNumberOfDayOfWeek(dayOfWeek)) {
            return today.withDayOfWeek(getNumberOfDayOfWeek(dayOfWeek));
        } else if (today.getDayOfWeek() == getNumberOfDayOfWeek(dayOfWeek)) {
            return today;
        } else {
            return today.plusWeeks(1).withDayOfWeek(getNumberOfDayOfWeek(dayOfWeek));
        }
    }

    /**
     * Calculate date considering input parameters.
     * e.g. "Monday + 3 days"
     *
     * @param dayParameters Day of week, sign (+ or -), number of days. Separated by space.
     * @return LocalDate Date
     */
    public static LocalDate calculateDateFromDayOfWeek(String dayParameters) {
        LocalDate today = new LocalDate(DateTimeZone.forID("Australia/Brisbane"));
        LocalDate date;
        String[] parsedDate = dayParameters.split(" ");
        String sign;
        String numberOfDays;
        String dayOfWeek = parsedDate[0];

        if (dayOfWeek.equalsIgnoreCase("today")) {
            date = today;
        } else if (dayOfWeek.equalsIgnoreCase("tomorrow")) {
            date = today.plusDays(1);
        }  else if (dayOfWeek.equalsIgnoreCase("yesterday")) {
            date = today.minusDays(1);
        }
        else {
            date = calcNextDayOfWeek(dayOfWeek);
        }

        if (parsedDate.length > 1) {
            sign = parsedDate[1];
            numberOfDays = parsedDate[2];

            if (sign.equalsIgnoreCase("+")) {
                date = date.plusDays(Integer.parseInt(numberOfDays));
            } else if (sign.equalsIgnoreCase("-")) {
                date = date.minusDays(Integer.parseInt(numberOfDays));
            }
        }
        return date;
    }


    /**
     * Calculate date considering input parameters.
     * e.g. "Monday + 3 days"
     *
     * @param dayParameters Day of week, sign (+ or -), number of days. Separated by space.
     * @return String Date in pattern dd/MM/yyyy
     */
    public static String getDateFromDayOfWeek(String dayParameters) {
        return calculateDateFromDayOfWeek(dayParameters).toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
    }


    /**
     * Calculate date considering input parameters.
     * e.g. "Monday + 3 days"
     *
     * @param dayParameters Day of week, sign (+ or -), number of days. Separated by space.
     * @param pattern       pattern e.g "EEE dd/MM/yyyy"
     * @return String Date in specified pattern
     */
    public static String getDateFromDayOfWeek(String dayParameters, String pattern) {
        return calculateDateFromDayOfWeek(dayParameters).toString(DateTimeFormat.forPattern(pattern));
    }

    /**
     * Get color hex from color name
     *
     * @param colorName color name
     * @return String color hex
     */
    public static String getColorHexByColorName(String colorName) {
        if (colorName.equalsIgnoreCase("Yellow")) {
            return "#F3EA6B";
        } else if (colorName.equalsIgnoreCase("Magenta")) {
            return "#E27D92";
        } else if (colorName.equalsIgnoreCase("Ice Blue")) {
            return "#74C3F2";
        } else if (colorName.equalsIgnoreCase("Chartreuse")) {
            return "#7FC424";
        } else if (colorName.equalsIgnoreCase("Autumn Orange")) {
            return "#F3A972";
        } else if (colorName.equalsIgnoreCase("Light Viloet")) {
            return "#F29BC0";
        } else if (colorName.equalsIgnoreCase("Blue Purple")) {
            return "#911B79";
        } else if (colorName.equalsIgnoreCase("Sky Blue")) {
            return "#0079C5";
        } else if (colorName.equalsIgnoreCase("Grey")) {
            return "#969594";
        } else if (colorName.equalsIgnoreCase("Spring Green")) {
            return "#3F916D";
        } else if (colorName.equalsIgnoreCase("Brick Red")) {
            return "#AD2810";
        } else if (colorName.equalsIgnoreCase("Green")) {
            return "#039717";
        } else if (colorName.equalsIgnoreCase("Light Green")) {
            return "#84B40E";
        } else if (colorName.equalsIgnoreCase("Mustard")) {
            return "#99D196";
        } else if (colorName.equalsIgnoreCase("Bright Green")) {
            return "#0BE233";
        } else if (colorName.equalsIgnoreCase("Black")) {
            return "#31090D";
        } else if (colorName.equalsIgnoreCase("Light Red")) {
            return "#E63567";
        } else if (colorName.equalsIgnoreCase("Light Green")) {
            return "#84B40E";
        } else if (colorName.equalsIgnoreCase("Red/Orange")) {
            return "#F45251";
        } else if (colorName.equalsIgnoreCase("Dark Blue")) {
            return "#416978";
        } else if (colorName.equalsIgnoreCase("Lime Green")) {
            return "LightGreen";        //or "#90EE90"
        } else if (colorName.equalsIgnoreCase("Blue")) {
            return "#4763FF";
        } else if (colorName.equalsIgnoreCase("Light Blue")) {
            return "#66CCFF";
        } else if (colorName.equalsIgnoreCase("Bright Purple")) {
            return "#D670DA";
        } else if (colorName.equalsIgnoreCase("Purple")) {
            return "#9966FF";
        } else if (colorName.equalsIgnoreCase("Pale Pink")) {
            return "#B7ABEC";
        } else if (colorName.equalsIgnoreCase("white blue")) {
            return "#428BCA";
        }
        else throw new IllegalArgumentException(String.format("'%s' - is incorrect color name", colorName));
    }

    /**
     * Convert color Hex into color RGB
     *
     * @param colorHex e.g. "#FFFFFF"
     * @return String color RGB
     */
    public static String convertHexToRGBColor(String colorHex) {
        Color colorRGB = new Color(
                Integer.valueOf(colorHex.substring(1, 3), 16),
                Integer.valueOf(colorHex.substring(3, 5), 16),
                Integer.valueOf(colorHex.substring(5, 7), 16));
        return colorRGB.toString().replaceAll("[^0-9,]", "");
    }

    /**
     * Convert element condition to boolean value
     *
     * @param condition - condition of element
     * @return boolean true or false
     */
    public static boolean isShouldDisplayed(String condition){
        if(condition.equalsIgnoreCase("should")){
            return true;
        }
        else if(condition.equalsIgnoreCase("should not")){
            return false;
        }
        else{
            throw new IllegalArgumentException(String.format("%s - unsupported condition", condition));
        }
    }
}
