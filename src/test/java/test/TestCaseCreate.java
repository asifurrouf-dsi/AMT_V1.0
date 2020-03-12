package test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import test.coreModule.MainController;
import test.driver.DriverFactory;
import test.keywordScripts.UtilKeywordScript;
import test.utility.PropertyConfig;

public class TestCaseCreate {
    private static WebDriver driver;
    private ClassLoader classLoader = getClass().getClassLoader();;

    @Test(priority = 1)
    public static void login(){
        driver = DriverFactory.createDriver("firefox", false);
        new UtilKeywordScript(driver).login(PropertyConfig.getLoginUrl(),PropertyConfig.getPropertyValue("userName"),PropertyConfig.getPropertyValue("password"),PropertyConfig.getPropertyValue("client"));
        MainController mainController= new MainController(driver);
        mainController.createAndExecute();
    }
}
