package test;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import test.Log.CreateLog;
import test.beforeTest.LeaseCreateAndSearch;
import test.beforeTest.PropertyCreateAndSearch;
import test.beforeTest.SpaceCreateAndSearch;
import test.beforeTest.TestData;
import test.coreModule.*;

import test.Log.EmailSend;
import test.driver.DriverFactory;
import test.keywordScripts.UIBase;
import test.keywordScripts.UITree;
import test.keywordScripts.UtilKeywordScript;
import test.objectLocator.ObjectLocatorDataStorage;
import test.objectLocator.OrRead;
import test.Log.LogMessage;
import test.utility.PropertyConfig;
import test.utility.ReadExcel;

import java.io.File;
import java.io.SerializablePermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static test.utility.PropertyConfig.*;

public class UnitTesting {

    @Test
    public void readExcelsheetTest(){
        ClassLoader classLoader = getClass().getClassLoader();
        ReadExcel readExcel = new ReadExcel(classLoader.getResource("modules/sample.xlsx").getPath());
        List<Map> records = readExcel.read("TC001_TC050");
        assertTrue(records.size() > 1 );
        Map  data = records.get(0);
        assertTrue(data.containsKey(TC_ID));
        assertTrue("001".equals( (String) data.get(PropertyConfig.TC_ID)));

    }
    @Test
    public void orReadTesting(){
        OrRead orRead  = new OrRead("Common.Login.txtUserName");
        Map records = orRead.getOrFromSheet();
        assertTrue(records.size() > 1 );
        int size = records.size();
        assertTrue(records.containsKey("Common.Login.txtUserName") );
        assertTrue(records.containsKey("Common.Login.txtPassword") );
         orRead  = new OrRead("Common.Login.txtUserNameFake");
         records = orRead.getOrFromSheet();
        assertTrue(records.size() == size);
        assertFalse(records.containsKey("Common.Login.txtUserNameFake"));
    }
    @Test
    public void objectLocatorStorageTesting(){
        ObjectLocatorDataStorage objectLocatorDataStorage = new ObjectLocatorDataStorage();
        try {
            System.out.println(objectLocatorDataStorage.getObjectLocator("Common.RecurringPayment.chargeType"));
            long start = System.currentTimeMillis();
            Map record = objectLocatorDataStorage.getObjectLocator("Common.Login.txtUserName");
            long finish = System.currentTimeMillis();
            long timeElapsed1 = finish - start;
            start = System.currentTimeMillis();
             record = objectLocatorDataStorage.getObjectLocator("Common.Login.txtUserName");
             finish = System.currentTimeMillis();
            long timeElapsed2 = finish - start;
            assertTrue(timeElapsed2 < timeElapsed1);
            start = System.currentTimeMillis();
            record = objectLocatorDataStorage.getObjectLocator("Common.Login.txtPassword");
            finish = System.currentTimeMillis();
            long timeElapsed3 = finish - start;
            assertTrue(timeElapsed3 == timeElapsed2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testingInvokeMethod() {

        WebDriver driver = null ;
        ExecuteTests executeTests = new ExecuteTests(driver);
         String test = "test" ;
        Object[] object = new Object[]{test};
        LogMessage logMessage = executeTests.invokeMethod("UIBase","test_click",1,object);
        assertFalse(!logMessage.isPassed().booleanValue());
        assertTrue(logMessage.getLogMessage().equals(test));
    }
    @Test
    public void testinCreateTestPlan() {
        MainController mc = new MainController();
        TestPlan testPlan =  mc.createTestPlan() ;
        List<TestModule> modules = testPlan.getAllTesModules() ;
        for(TestModule md : modules){
            assertTrue(md.getModuleName().equals("sample"));
        }
    }
    @Test
    public void testingCreateTestPlanAndModule() {
        MainController mc = new MainController();
        TestPlan testPlan = mc.createTestPlanAndModule();
        List<TestModule> modules = testPlan.getAllTesModules() ;
        for(TestModule md : modules){
            List<TestSuite> tsc = md.getAllTestSuits();
            for(TestSuite ts : tsc){
                List<TestCase> tcs = ts.getAllTestCases();
                for(TestCase tc: tcs){
                  assertTrue(tc.getTestCaseNumber().equals("001"));
                }
            }
        }
    }

    @Test
    public void testCreateLog(){
        TestCase TC = new TestCase();
        CreateLog createLog = new CreateLog("Log") ;
        createLog.createLogger("sample test",TC.getcategoryName());
        createLog.writeLog("sample test","test",true);
        File file = new File("./Report/Log.html");
        assertTrue(file.exists());
    }

    @Test
    public void testingEmailSent() {
        EmailSend.sendLogReport();
    }
    @Test
    public void visibilityCheck(){
        WebDriver driver = DriverFactory.createDriver("chrome", false);
        UIBase uiBase = new UIBase(driver);
        uiBase.VerifyPageLoadedTrue("Common.Homepage.search");
    }
    @Test
    public void testSplit(){
        String data = "https://qa2.testamt.com/portfolioInsight/Lease/RecurringChargeDetailNew?lease_id=173143&EntityValue=546072&suite_index_id=178622&currency_to=";
        String objectData="https://*testamt.com*RecurringCharge*";
        String[] splittedObjectData= objectData.split("(\\*)|(\\s+)");
        String matchString="(.*)";
        for(String split:splittedObjectData){
            matchString=matchString+split+"(.*)";
        }
        if(data.matches(matchString)){
            System.out.println("hi");
        }
        System.out.println(matchString);
        Map<String, Integer> hash_map = new HashMap<String, Integer>();

        // Mapping int values to string keys
        hash_map.put("Geeks", 10);
        hash_map.put("4", 15);
        hash_map.put("Geeks", 20);
        hash_map.put("Welcomes", 25);
        hash_map.put("You", 30);
        //List<String> keys = (List<String>) hash_map.keySet();
        for(String key: hash_map.keySet()){
            if(key.contains("Ge"))
                System.out.println("hi");
        }
    }
    @Test
    public void testUITree(){
        WebDriver driver = DriverFactory.createDriver("chrome", false);
        new UtilKeywordScript(driver).login(PropertyConfig.getLoginUrl(),PropertyConfig.getPropertyValue("userName"),PropertyConfig.getPropertyValue("password"),PropertyConfig.getPropertyValue("client"));
        UITree uiTree= new UITree(driver);
        String obj="//*[@id='treeContainer']";
        uiTree.AssetTreeMenuExpand(obj,"tree,taka");

    }
    @Test
    public void Testdataupdate(){
        WebDriver driver=DriverFactory.createDriver("chrome",false);
        ExecuteTests executeTests=new ExecuteTests(driver);
        System.out.println(executeTests.updateTestData("666677777","Property,$Lease_dbaName,$Property_propertyCode_0",null));
    }
    @Test
    public void searchalltest(){
        try {
            WebDriver driver = DriverFactory.createDriver("chrome", false);
            new UtilKeywordScript(driver).login(PropertyConfig.getLoginUrl(), PropertyConfig.getPropertyValue("userName"), PropertyConfig.getPropertyValue("password"), PropertyConfig.getPropertyValue("client"));
           //LeaseCreateAndSearch leaseCreateAndSearch = new LeaseCreateAndSearch(driver);
            SpaceCreateAndSearch spaceCreateAndSearch=new SpaceCreateAndSearch(driver);
            /*TestData testData = TestData.getInstance();
            List<Map> spaceRecords = testData.getData("SPACE","666677777");
            List<Map> leaseRecords = testData.getData("LEASE","666677777");
            List<Map> recurRecords = testData.getData("RECURRINGPAYMENT","666677777");
            List<LogMessage> logMessages = leaseCreateAndSearch.isLeaseSpaceRecurExistsWithinAProperty(leaseRecords, spaceRecords, recurRecords);
            for (LogMessage logMessage : logMessages) {
                System.out.println(logMessage.getLogMessage());
            }*/
            List<String> stringList=new ArrayList<>();
            stringList.add("Test_Property_Auto_Final_10_Lease_Space_32");
            stringList.add("Test_Property_Auto_Final_10_Lease_Space_38");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testgetPrequisiteTestCase() {
        TestPlan tp =  new MainController().createTestPlanAndModule() ;
        PreRequiste preRequiste = new PreRequiste() ;
        //make sure in excel correspoding test case  row is yes.
        Optional<TestCase> ts =   preRequiste.getPrequisiteTestCase("FASB", "5689132");
        assertTrue(ts.isPresent());
        assertTrue(ts.get().getTestCaseName().contains("FASB/IASB Lessee - Capitalization Threshold - Edit Revision - When IASB ROU Asset Capitalization Threshold is made less than the calculated ROU Asset, Lease Classification should not be Small Ticket Item"));

    }

    @Test
    public void testJsontoMap(){
        Optional<Map> map =  UtilKeywordScript.jsonStringToMap( "p:0,l:0,s:2") ;
        Map m = map.get() ;
        assertTrue(m.get("p").equals("0"));
        assertTrue(m.get("s").equals("2"));

    }


}


