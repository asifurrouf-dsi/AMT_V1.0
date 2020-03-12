package test;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import test.Log.LogMessage;
import test.beforeTest.LeaseCreateAndSearch;
import test.beforeTest.PropertyCreateAndSearch;
import test.driver.DriverFactory;
import test.keywordScripts.UtilKeywordScript;
import test.utility.ReadExcel;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    /*
    @Test
    public void testingReadAndExecute() {
        WebDriver driver = DriverFactory.createDriver("chrome", false);
        ExecuteTests executeTests = new ExecuteTests(driver);
        executeTests.readAndExecute("Fasb2","TC001_TC050");
    }
    @Test
    public void testingCreateAndExecute() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            String appConfigPath = classLoader.getResource("default.properties").getPath();
            Properties appProps = new Properties();
            appProps.load(new FileInputStream(appConfigPath));
            String browser = appProps.getProperty("browser");
            String headless = appProps.getProperty("headless");
            boolean hdless = headless.equals("true") ? true : false;
            // System.out.println(appVersion);
            WebDriver driver = DriverFactory.createDriver(browser, hdless);
            MainController mc = new MainController(driver);
            mc.createAndExecute();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/



    @Test
    public void propertyCreate() {

        ClassLoader classLoader = getClass().getClassLoader();
        ReadExcel readExcel = new ReadExcel(classLoader.getResource("dataCreate/DataCreate.xlsx").getPath());
        List<Map> records = readExcel.read("Property");
        WebDriver driver = DriverFactory.createDriver("chrome", false);
        UtilKeywordScript utilKeywordScript = new UtilKeywordScript(driver);
        utilKeywordScript.login("https://qa4.testamt.com/","saimaalam01","amtDirect01!", "201483");
        PropertyCreateAndSearch propertyCreateAndSearch = new PropertyCreateAndSearch(driver);
        for(Map record : records) {
            propertyCreateAndSearch.createProperty(record);
        }
        UtilKeywordScript.closeAllPages(driver);

    }

    @Test
    public void leaseCreate(){

        ClassLoader classLoader = getClass().getClassLoader();
        ReadExcel readExcel = new ReadExcel(classLoader.getResource("dataCreate/DataCreate.xlsx").getPath());
        List<Map> records = readExcel.read("Lease");
        WebDriver driver = DriverFactory.createDriver("chrome", false);
        UtilKeywordScript utilKeywordScript = new UtilKeywordScript(driver);
        utilKeywordScript.login("https://qa4.testamt.com/","saimaalam01","amtDirect01!", "201483");
        LeaseCreateAndSearch leaseCreateAndSearch = new LeaseCreateAndSearch(driver);
        for(Map record : records){
            LogMessage logMessage =  leaseCreateAndSearch.createLease(record);
            assertTrue(logMessage.isPassed());
        }
        UtilKeywordScript.closeAllPages(driver);

    }

}
