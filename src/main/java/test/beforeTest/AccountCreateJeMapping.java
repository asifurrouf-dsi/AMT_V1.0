package test.beforeTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.keywordScripts.*;
import test.objectLocator.ObjectLocatorDataStorage;
import test.utility.PropertyConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountCreateJeMapping {

    private WebDriver webDriver ;

    public AccountCreateJeMapping(WebDriver  webDriver) {
        this.webDriver = webDriver ;
    }
    public String removeSpacesaAndNewline(String inputString){
        String outputString=inputString.replaceAll("\\s+","").toUpperCase();
        return outputString;
    }
    public void createAccount(Map data) {
        try {

            UIBase uiBase = new UIBase(webDriver);
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*2);
            uiBase.Click(webDriver.findElement(By.linkText("Add New")));
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*2);
            UITable uiTable = new UITable(webDriver);
            uiTable.ClickCellData("Common.GlobalSearch.accountTable","*Account Number,0,"+(String)data.get("Account Number"));
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND);
            uiTable.EnterCellData("Common.GlobalSearch.accountTable","*Account Number,0,"+(String)data.get("Account Number"));
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*2);
            uiTable.ClickCellData("Common.GlobalSearch.accountTable","*Description,0,"+(String)data.get("Description"));
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND);
            uiTable.EnterCellData("Common.GlobalSearch.accountTable","*Description,0,"+(String)data.get("Description"));
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*2);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public void createAccounts(List<Map> accountDatas, String chartOfAccount){
        try {
            UIBase uibase = new UIBase(webDriver);
            UIMenu uiMenu = new UIMenu(webDriver);

            uiMenu.SelectMenu("Common.Homepage.pgAMTHome" , "Accounting,Accounting Setup") ;
            uibase.WaitingForPageLoad();
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*3);
            uiMenu.SelectMenu("","Account Numbers");
            uibase.WaitingForPageLoad();
            UIDropDown uiDropDown = new UIDropDown(webDriver);
            Map objectLocatorData = ObjectLocatorDataStorage.getObjectLocator("Common.GlobalSearch.chartOfAccountList");
            uiDropDown.searchAndSelectItem("Common.GlobalSearch.chartOfAccountInput", (String) objectLocatorData.get(PropertyConfig.PARENT_LOCATOR), chartOfAccount);
            UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS *2);
            for (Map accountData : accountDatas) {
                if (null == accountData.get(PropertyConfig.EXECUTION_FLAG) || accountData.get(PropertyConfig.EXECUTION_FLAG).toString().isEmpty() || !accountData.get(PropertyConfig.EXECUTION_FLAG).toString().toLowerCase().equals("yes"))
                    continue;
                else {
                    createAccount(accountData);
                    UtilKeywordScript.delay(PropertyConfig.ONE_SECOND);
                }
            }
            webDriver.findElement(By.linkText("Update")).click();
            uibase.WaitingForPageLoad();
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void  createJEMappping(List<Map> accountDatas , Map JMappingData) {
        try {

            UIBase uibase = new UIBase(webDriver);
            UIMenu uiMenu = new UIMenu(webDriver);
            WebDriverWait wait = new WebDriverWait(webDriver, 5*60);
            uiMenu.SelectMenu("Common.Homepage.pgAMTHome" , "Accounting,Accounting Setup") ;
            uibase.WaitingForPageLoad();
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*3);
           // UIMenu uiMenu = new UIMenu(webDriver);
            uiMenu.SelectMenu("","JE Mapping");
            uibase.WaitingForPageLoad();
            UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS *3);
            UIDropDown uiDropDown = new UIDropDown(webDriver);
            Map objectLocatorData = ObjectLocatorDataStorage.getObjectLocator("Common.GlobalSearch.leaseTypes");
            uiDropDown.SelectItem("Common.GlobalSearch.leaseTypes", (String) objectLocatorData.get(PropertyConfig.PARENT_LOCATOR), (String) JMappingData.get("Lease Types"));
           // uibase.WaitingForPageLoad();
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND);
            objectLocatorData = ObjectLocatorDataStorage.getObjectLocator("Common.GlobalSearch.jMappingchartOfAccountList");
            uiDropDown.SelectItem("Common.GlobalSearch.jMappingchartOfAccountList", (String) objectLocatorData.get(PropertyConfig.PARENT_LOCATOR), (String) JMappingData.get("chartOfAccount"));
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND);
            objectLocatorData = ObjectLocatorDataStorage.getObjectLocator("Common.GlobalSearch.jMappingMaintainCodeType");
            uiDropDown.SelectItem("Common.GlobalSearch.jMappingMaintainCodeType", (String) objectLocatorData.get(PropertyConfig.PARENT_LOCATOR), (String) JMappingData.get("Maintain Code Type"));
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND);

            WebElement element = webDriver.findElement(By.linkText("Add New"));
            uibase.Click(element);
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*3);
            UIDropDown dropDown = new UIDropDown(webDriver);
            dropDown.SelectItem("Common.GlobalSearch.contractType", (String) JMappingData.get("Contract Type"));
            UITable uiTable = new UITable(webDriver);
            uiTable.ClickCellData("Common.GlobalSearch.jeMappingTable","*Payment Type,0,test");
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*3);
            dropDown.SelectItem("Common.GlobalSearch.paymentType",(String) JMappingData.get("Payment Type"));

            Map accountRecord = new HashMap<String,Map>() ;
            for(Map accountData : accountDatas  ) {
                if (null == accountData.get(PropertyConfig.EXECUTION_FLAG) || accountData.get(PropertyConfig.EXECUTION_FLAG).toString().isEmpty() || !accountData.get(PropertyConfig.EXECUTION_FLAG).toString().toLowerCase().equals("yes"))
                    continue;
                accountRecord.put((String) accountData.get("Description"),accountData);
            }
            updateJMappingSecquentially("Common.GlobalSearch.jeMappingTable",accountRecord);
            /*
            for(Map accountData: accountDatas) {
                if (null == accountData.get(PropertyConfig.EXECUTION_FLAG) || accountData.get(PropertyConfig.EXECUTION_FLAG).toString().isEmpty() || !accountData.get(PropertyConfig.EXECUTION_FLAG).toString().toLowerCase().equals("yes"))
                    continue;

                LogMessage lm = uiTable.ClickCellData("Common.GlobalSearch.jeMappingTable", accountData.get("Description")+",0,test");
                if(!lm.isPassed()) {
                    System.out.println(accountData.get("Description"));
                    System.out.println(lm.getLogMessage());
                    continue;
                }
                UtilKeywordScript.delay(1);

                uiTable.ClickCellData("Common.GlobalSearch.jeMappingTable", accountData.get("Description")+",0,test,button");
                uibase.WaitingForPageLoad();
                UtilKeywordScript.delay(2);
                uiTable.filterTableByColumn("Common.GlobalSearch.jMappingAccountTable", "Account Number,"+accountData.get("Account Number"));
                UtilKeywordScript.delay(4);
                uiTable.DoubleClickCellInTable("Common.GlobalSearch.jMappingAccountTable", "Account Number,"+accountData.get("Account Number"));        //FASB/IASB - Expense Debit - Financing
                UtilKeywordScript.delay(2);
            } */
            webDriver.findElement(By.linkText("Update")).click();
            uibase.WaitingForSuccessfullPopup();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void updateJMappingSecquentially(String objectLocatorData,Map accountData) {
        try {
            UITable uiTable = new UITable(webDriver);
            UIBase uibase = new UIBase(webDriver);
            Map<String, WebElement> row = uiTable.getSingleRowfromTable(objectLocatorData, null, null, Integer.valueOf(0));
            //System.out.println(row);
            System.out.println(row);
            if (null == row || row.isEmpty())
                return;
            for (String key : row.keySet()) {
                String clName = Optional.ofNullable(key).orElse("noValue");
                if (!accountData.containsKey(clName)) {
                    System.out.println("Not Found Column Name"+clName);
                    continue;
                }
                System.out.println("Column Name"+clName);
                //System.out.println("here");
                WebElement element = row.get(key);
                //System.out.println(element);
                element.click();
                UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*2);
                element.findElement(By.tagName("button")).click();
                uibase.WaitingForPageLoad();
                UtilKeywordScript.delay(PropertyConfig.ONE_SECOND);
                Map account = (Map) accountData.get(clName);
                System.out.println(account.get("Account Number"));
                uiTable.filterTableByColumn("Common.GlobalSearch.jMappingAccountTable", "Account Number," + account.get("Account Number"));
                UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*3);
                uiTable.DoubleClickCellInTable("Common.GlobalSearch.jMappingAccountTable", "Account Number," + account.get("Account Number"));        //FASB/IASB - Expense Debit - Financing
                UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*2);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }





}
