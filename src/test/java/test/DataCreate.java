package test;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import test.Log.LogMessage;
import test.beforeTest.LeaseCreateAndSearch;
import test.beforeTest.PropertyCreateAndSearch;
import test.beforeTest.RecurringPaymentCreateandSearch;
import test.beforeTest.SpaceCreateAndSearch;
import test.driver.DriverFactory;
import test.keywordScripts.UtilKeywordScript;
import test.utility.PropertyConfig;
import test.utility.ReadExcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;


public class DataCreate {
    private static WebDriver driver;
    private ClassLoader classLoader = getClass().getClassLoader();;

    @Test (priority = 1)
    public static void login(){
        driver = DriverFactory.createDriver("chrome", false);
        new UtilKeywordScript(driver).login(PropertyConfig.getLoginUrl(),PropertyConfig.getPropertyValue("userName"),PropertyConfig.getPropertyValue("password"),PropertyConfig.getPropertyValue("client"));

    }
    @Test (priority = 2)
    public void createProperty(){
        if(PropertyConfig.getPropertyValue("dataCreate").contains("all") || PropertyConfig.getPropertyValue("dataCreate").toLowerCase().contains("property")) {
            ReadExcel readExcel = new ReadExcel(classLoader.getResource("dataCreate/DataCreate.xlsx").getPath());
            List<Map> propertyRecords = readExcel.read("Property");

            PropertyCreateAndSearch propertyCreateAndSearch = new PropertyCreateAndSearch(driver);
            for (Map propertyRecord : propertyRecords) {
                if (null == propertyRecord.get(PropertyConfig.EXECUTION_FLAG) || propertyRecord.get(PropertyConfig.EXECUTION_FLAG).toString().isEmpty() || !propertyRecord.get(PropertyConfig.EXECUTION_FLAG).toString().toLowerCase().equals("yes"))
                    continue;
                LogMessage logMessage = propertyCreateAndSearch.createProperty(propertyRecord);
                assertTrue(logMessage.isPassed());
            }
        }
    }
    @Test (priority = 3)
    public void createLease(){
        if(PropertyConfig.getPropertyValue("dataCreate").contains("all") || PropertyConfig.getPropertyValue("dataCreate").toLowerCase().contains("lease")) {
            ReadExcel readExcel = new ReadExcel(classLoader.getResource("dataCreate/DataCreate.xlsx").getPath());
            List<Map> leaseRecords = readExcel.read("Lease");
            LeaseCreateAndSearch leaseCreateAndSearch = new LeaseCreateAndSearch(driver);
            List<Map> leaseDatas=new ArrayList<>();
            for (Map leaseRecord : leaseRecords) {
                if (null == leaseRecord.get(PropertyConfig.EXECUTION_FLAG) || leaseRecord.get(PropertyConfig.EXECUTION_FLAG).toString().isEmpty() || !leaseRecord.get(PropertyConfig.EXECUTION_FLAG).toString().toLowerCase().equals("yes"))
                    continue;
                leaseDatas.add(leaseRecord);
            }
            List<LogMessage> logMessages = leaseCreateAndSearch.createMultipleLeases(leaseDatas);
            assertTrue(logMessages.stream().allMatch(o -> o.isPassed().equals(true)));
        }

    }
    @Test (priority = 4)
    public void createSpace() {
        if (PropertyConfig.getPropertyValue("dataCreate").contains("all") || PropertyConfig.getPropertyValue("dataCreate").toLowerCase().contains("space")) {
            ReadExcel readExcel = new ReadExcel(classLoader.getResource("dataCreate/DataCreate.xlsx").getPath());
            List<Map> spaceRecords = readExcel.read("Space");
            SpaceCreateAndSearch spaceCreateAndSearch = new SpaceCreateAndSearch(driver);
            Map<String, List<Map>> spacesList = new HashMap<>();
            for (Map spaceRecord : spaceRecords) {
                if (null == spaceRecord.get(PropertyConfig.EXECUTION_FLAG) || spaceRecord.get(PropertyConfig.EXECUTION_FLAG).toString().isEmpty() || !spaceRecord.get(PropertyConfig.EXECUTION_FLAG).toString().toLowerCase().equals("yes"))
                    continue;
                if (!spacesList.containsKey(spaceRecord.get("LeaseName"))) {
                    List<Map> record = new ArrayList<Map>();
                    record.add(spaceRecord);
                    spacesList.put((String) spaceRecord.get("LeaseName"), record);
                } else {
                    spacesList.get(spaceRecord.get("LeaseName")).add(spaceRecord);
                }

            }
            for (String key : spacesList.keySet()) {
                spaceCreateAndSearch.createMultipleSpaces(spacesList.get(key));
            }
        }
    }

    @Test (priority = 5)
    public void createRecurrentPayment(){
        if(PropertyConfig.getPropertyValue("dataCreate").contains("all") || PropertyConfig.getPropertyValue("dataCreate").toLowerCase().contains("recurringpayment")) {
            ReadExcel readExcel = new ReadExcel(classLoader.getResource("dataCreate/DataCreate.xlsx").getPath());
            List<Map> spaceRecords = readExcel.read("RecurringPayment");
            RecurringPaymentCreateandSearch recurringPaymentCreateandSearch = new RecurringPaymentCreateandSearch(driver);
            Map<String, List<Map>> spacesList = new HashMap<>();

            for (Map spaceRecord : spaceRecords) {
                if (null == spaceRecord.get(PropertyConfig.EXECUTION_FLAG) || spaceRecord.get(PropertyConfig.EXECUTION_FLAG).toString().isEmpty() || !spaceRecord.get(PropertyConfig.EXECUTION_FLAG).toString().toLowerCase().equals("yes"))
                    continue;
                if (!spacesList.containsKey(spaceRecord.get("LeaseName"))) {
                    List<Map> record = new ArrayList<Map>();
                    record.add(spaceRecord);
                    spacesList.put((String) spaceRecord.get("LeaseName"), record);
                } else {
                    spacesList.get(spaceRecord.get("LeaseName")).add(spaceRecord);
                }

            }
            //System.out.println(spacesList);
            for (String key : spacesList.keySet()) {
                recurringPaymentCreateandSearch.addMultipleRecurringPayments(spacesList.get(key));
            }
        }

    }
}
