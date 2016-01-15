package my.com.test.common.webdriver;

import my.com.test.common.utils.Common;

/**
 * Class for start webdriver services like IE or Chrome.
 */
public class DriverService {

    private void startService(String propertyName, String pathToExecutable){

        String pathToDriver = Common.getSharedResource(pathToExecutable);
        System.setProperty(propertyName, pathToDriver);
    }

    public void startIeDriverService(){
        this.startService("webdriver.ie.driver", "ieDriver/IEDriverServer.exe");
    }

    public void startChromeDriverService(){
        this.startService("webdriver.chrome.driver", "chromeDriver/chromedriver.exe");
    }
}
