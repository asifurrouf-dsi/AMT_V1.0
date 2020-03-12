package test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import test.beforeTest.AccountCreateJeMapping;
import test.driver.DriverFactory;
import test.keywordScripts.UtilKeywordScript;
import test.utility.PropertyConfig;
import test.utility.ReadExcel;

import java.util.List;
import java.util.Map;

public class JMapping {

    private static WebDriver webDriver ;

    @BeforeClass
    public static void login() {
        webDriver = DriverFactory.createDriver("firefox", false);
        new UtilKeywordScript(webDriver).login(PropertyConfig.getLoginUrl(),PropertyConfig.getPropertyValue("userName"),PropertyConfig.getPropertyValue("password"),PropertyConfig.getPropertyValue("client"));

    }

    @Test
    public void createAccount() {
        try {
            long start = System.currentTimeMillis();
            ClassLoader classLoader = getClass().getClassLoader();
            ReadExcel readExcel = new ReadExcel(classLoader.getResource("dataCreate/AccountCreateJmapping.xlsx").getPath());
            List<Map> accountRecords = null;
            List<Map> jMappingRecords= readExcel.read("JMapping");
            String chartOfAccount = (String) jMappingRecords.get(0).get("Lease Types");
            if (chartOfAccount.equals("Expense")){
                accountRecords = readExcel.read("Expense");
            }else{
                accountRecords = readExcel.read("Income");
            }
            AccountCreateJeMapping accountCreateJeMapping = new AccountCreateJeMapping(webDriver) ;
            accountCreateJeMapping.createAccounts(accountRecords, (String) jMappingRecords.get(0).get("chartOfAccount"));
            accountCreateJeMapping.createJEMappping(accountRecords,jMappingRecords.get(0));
            long end = System.currentTimeMillis();
            System.out.println((end-start)/1000);


        }catch ( Exception ex) {
            ex.printStackTrace();
        }

    }



}
