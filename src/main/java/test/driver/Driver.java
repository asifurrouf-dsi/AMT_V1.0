package test.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface Driver {
    public WebDriver getDriver(DesiredCapabilities desiredCapabilities, Boolean headless);
}
