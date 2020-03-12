package test.driver;

import org.openqa.selenium.WebDriver;

public class DriverFactory {
    public static WebDriver createDriver(String browser,Boolean headless) {
        DriverType driverType =  DriverType.valueOf(browser.toUpperCase());
       return  driverType.getDriver(null,headless);
    }
}
