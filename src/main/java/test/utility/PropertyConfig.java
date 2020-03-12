package test.utility;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyConfig {
    public static String  MODULE_CONTROLLER = "ModuleController" ;
    public static String  CONTROLLER = "Controller" ;
    public static String OBJECT_REFERENCE = "ObjReference" ;
    public static String  MODULE_NAME = "ModuleName" ;
    public static String OBJECT_SEARCH_KEY = "ObjSearchKey" ;
    public static String OBJECT_LOCATORS = "ObjectLocators" ;
    public static String PARENT_LOCATOR = "ParentLocator" ;
    public static String ACTION = "Action" ;
    public static String TEST_DATA = "TestData" ;
    public static String EXECUTION_FLAG = "ExecutionFlag" ;
    public static String PAGE_REFRESH = "PageRefresh";
    public static String TCID= "TCID" ;
    public static String TC_ID= "TC_ID" ;
    public static String TEST_CASE_NAME= "TestCaseName" ;
    public static String TEST_Category_Name= "CategoryName" ;
    public static String STEP_NO = "StepNo" ;
    public static String  TEST_STEP_DESCRIPTION = "TestStepDescription" ;
    public static String FIELD_NAME = "FieldName" ;
    public static String CRITICAL = "Critical"  ;
    public static String SHEET_NAME = "SheetName"  ;
    public static String  PREREQ_COMMAND = "#PREQ" ;
    public static String  CREATE_COMMAND = "#CREATE" ;
    public static String  DELETE_COMMAND = "#DELETE" ;
    public static String  COMMON_COMMAND = "#COMMON" ;
    public static Integer INIT = 0 ;
    public static Integer CREATED = 1 ;
    public static Integer TESTED = 2 ;
    public static Integer SHORT_WAIT_TIME_SECONDS = 5 ;
    public static Integer LONG_WAIT_TIME_SECONDS = 10 ;
    public static Integer NUMBER_OF_ITERATIONS =5;
    public static Integer WAIT_TIME_EXPLICIT_WAIT=60;
    public static Integer ONE_SECOND =1;
    public static String LOGMESSAGE="LogMessage";
    public static String DELAY="Delay";
    public static String PARENT="Parent";

    public static String  getPropertyValue(String property){
        try {
            ClassLoader classLoader = PropertyConfig.class.getClassLoader();
            String appConfigPath = classLoader.getResource("default.properties").getPath();
            Properties appProps = new Properties();
            appProps.load(new FileInputStream(appConfigPath));
            //
            //appProps.se
            return appProps.getProperty(property);
        }catch (Exception ex) {
            ex.printStackTrace();
            return null ;

        }
    }
    public static String  getLoginUrl(){

        try {
            String loginUrl = getPropertyValue("loginUrl");
            String env = getPropertyValue("env");
            return loginUrl.replaceAll("#env",env);
        }catch (Exception ex) {
            ex.printStackTrace();
            return null ;

        }
    }
    public static String  getHomeLoginUrl(){

        try {
            String homeUrl = getPropertyValue("homeUrl");
            String env = getPropertyValue("env");
            return homeUrl.replaceAll("#env",env);
        }catch (Exception ex) {
            ex.printStackTrace();
            return null ;

        }
    }

}
