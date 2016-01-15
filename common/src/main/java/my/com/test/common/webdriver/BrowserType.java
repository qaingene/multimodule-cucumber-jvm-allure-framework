package my.com.test.common.webdriver;

/**
 * Enumerator for browsers that automation test suite supports.
 */
public enum BrowserType {
    FIREFOX("firefox"),
    CHROME("chrome"),
    IE("ie"),
    REMOTE_IE7("remote_ie7"),
    REMOTE_IE8("remote_ie8"),
    REMOTE_IE9("remote_ie9"),
    REMOTE_IE10("remote_ie10"),
    REMOTE_IE11("remote_ie11"),
    REMOTE_CHROME("remote_chrome"),
    REMOTE_FIREFOX("remote_firefox");

    private final String browser;

    BrowserType(String browser) {
        this.browser = browser;
    }

    public static BrowserType getTypeByValue(String value) {
        for (BrowserType element : BrowserType.values()) {
            if (element.toString().equalsIgnoreCase(value)) {
                return element;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
