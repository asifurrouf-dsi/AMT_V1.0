package test.beforeTest;

import org.openqa.selenium.*;
import test.Log.LogMessage;
import test.keywordScripts.*;
import test.objectLocator.ObjectLocatorDataStorage;
import test.objectLocator.WebObjectSearch;
import test.utility.PropertyConfig;

import java.util.*;

public class LeaseCreateAndSearch {

    private WebDriver webDriver;
    private String  objectlocatorPrefix;
    private UIBase uiBase;
    private String mainWindow;
    public LeaseCreateAndSearch(WebDriver driver){
        this.webDriver = driver;
    }

    public LeaseCreateAndSearch(){

    }

    public List<LogMessage> createMultipleLeases(List<Map> datas)
    {
        UtilKeywordScript utilKeywordScript = new UtilKeywordScript(webDriver);
        List<LogMessage> logMessageList = new ArrayList<>();
        try{
            objectlocatorPrefix = "Common.Lease.";
            UIMenu menu = new UIMenu(webDriver);
            menu.SelectMenu("Common.Homepage.pgAMTHome" , "Portfolio Insight,Add,Lease,"+datas.get(0).get("codeType")+","+datas.get(0).get("contractType"));
            UtilKeywordScript.switchLastTab(webDriver);
            webDriver.manage().window().maximize();
            UtilKeywordScript.delay(3);
            for(int i=0; i < datas.size();i++)
            {
                if(i!=0){

                    uiBase.Click(objectlocatorPrefix + "addNewButton");
                    uiBase.WaitingForPageLoad();
                }
                LogMessage lm = createLease(datas.get(i));
                logMessageList.add(lm);

            }
            utilKeywordScript.redirectHomePage();
            UtilKeywordScript.delay(3);
            return logMessageList;
        }catch (Exception ex){
            ex.printStackTrace();
            utilKeywordScript.redirectHomePage();
            logMessageList.add(new LogMessage(false, "Exception occurred while creating multiple leases" + ex.getMessage()));
            return logMessageList;
        }

    }

    public LogMessage createLease(Map data){

        try {
            String[] dropDownFields = new String[] {"leaseStatus","leaseType","billingType","leaseGroup1"};
            String[] textFields = new String[] {"dbaName","leaseCode","beginDate","expirationDate"};
            Map objectLocatorData = ObjectLocatorDataStorage.getObjectLocator(objectlocatorPrefix + "propertyList");
            uiBase = new UIBase(webDriver);
            UIText uiText = new UIText(webDriver);
            UIDropDown uiDropDown = new UIDropDown(webDriver);
            UtilKeywordScript utilKeywordScript=new UtilKeywordScript(webDriver);
            webDriver.manage().window().maximize();
            Map objectLocatorDataCode = ObjectLocatorDataStorage.getObjectLocator(objectlocatorPrefix +"codeType");
            if(webDriver.findElements(By.xpath((String) objectLocatorDataCode.get(PropertyConfig.OBJECT_LOCATORS))).size()>0){
                webDriver.manage().window().maximize();
                uiDropDown.SelectItem(objectlocatorPrefix +"codeType", (String) data.get("codeType"));
                UtilKeywordScript.delay( PropertyConfig.ONE_SECOND*2);
                uiDropDown.SelectItem(objectlocatorPrefix +"contractType", (String) data.get("contractType"));
                UtilKeywordScript.delay( PropertyConfig.ONE_SECOND*2);
                uiBase.Click(objectlocatorPrefix+"selectContract");
                uiBase.WaitingForPageLoad();
                UtilKeywordScript.delay( PropertyConfig.ONE_SECOND*2);
                utilKeywordScript.switchLastTab(webDriver);
            }

            uiDropDown.searchAndSelectItem(objectlocatorPrefix + "propertyName",(String) objectLocatorData.get(PropertyConfig.PARENT_LOCATOR),(String)data.get("propertyName"));

            uiBase.WaitingForPageLoad();

            for(String elementName: textFields) {
                uiText.SetText(objectlocatorPrefix + elementName, (String) data.get(elementName));
                UtilKeywordScript.delay(5);
            }
            String objectLocatorName="";
            for(String elementName : dropDownFields) {
                for (Object key : data.keySet()) {
                    String temp = (String) key;
                    if (temp.contains(elementName)) {
                        objectLocatorName=elementName;
                        elementName = temp;
                        break;
                    }
                }
                uiDropDown.SelectItem(objectlocatorPrefix + objectLocatorName, (String)data.get(elementName));
                UtilKeywordScript.delay(5);
            }

            uiBase.Click(objectlocatorPrefix + "saveButton");

            LogMessage lm = uiBase.WaitingForSuccessfullPopup();
            if(lm.isPassed())
                return new LogMessage(lm.isPassed(),"Lease "+data.get("dbaName") +" is created successfully under "+data.get("propertyName"));
            else
                return new LogMessage(lm.isPassed(),"Lease "+data.get("dbaName") +" is not created under "+data.get("propertyName"));
        }catch (Exception ex){
            ex.printStackTrace();
            return new LogMessage(false, "Lease "+data.get("dbaName") +" is not created under "+data.get("propertyName"));
        }

    }

    public LogMessage searchLease(Map data){

        try{
            String  objectLocatorPrefix = "Common.GlobalSearch.";
            UIText uiText = new UIText(webDriver);
            UIBase uiBase = new UIBase(webDriver);
            UITable uiTable  = new UITable(webDriver);

            uiBase.Click(objectLocatorPrefix + "search");
            uiText.SetText(objectLocatorPrefix +"txtSearch",(String)data.get("LeaseName"));
            uiBase.Click(objectLocatorPrefix + "btnSearch");

            UtilKeywordScript.delay(10);
            UtilKeywordScript.switchLastTab(webDriver);
            UtilKeywordScript.delay(10);
            LogMessage logMessage =  uiTable.ClickLinkInTable(objectLocatorPrefix + "leasetable","DBA Name," + (String)data.get("LeaseName"));
            UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS *PropertyConfig.NUMBER_OF_ITERATIONS);
            webDriver.close();
            UtilKeywordScript.switchLastTab(webDriver);
            UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS *PropertyConfig.NUMBER_OF_ITERATIONS);

            return new LogMessage(true, " Lease found successfully");
        }catch (Exception e){
            e.printStackTrace();
            return new LogMessage(false, " Exception occurred " + e.getMessage());
        }

    }

    public LogMessage isLeaseExistWithinProperty(Map data){
        try{
            String  objectLocatorPrefix = "Common.Property.";
            UILink uiLink = new UILink(webDriver);
            PropertyCreateAndSearch propertyCreateAndSearch = new PropertyCreateAndSearch(webDriver);

            LogMessage navigationLog = propertyCreateAndSearch.navigateToProperty(data);
            if (!navigationLog.isPassed())
                return new LogMessage(false, "Incomplete navigation");

            LogMessage clickLinkLog = uiLink.ClickLink(null,"Expand All");

            if (!clickLinkLog.isPassed())
                return new LogMessage(false, "exception occur during expanding property information");

            UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS);

            UIPanel uiPanel = new UIPanel(webDriver);
            LogMessage log = uiPanel.VerifyPanelContentTrue(objectLocatorPrefix +"tbLease", (String)data.get("dbaName"));
            if (log.isPassed()){
                return new LogMessage(true, "Lease already exist");
            }else {
                return new LogMessage(false, "Lease not exist");
            }

        }catch (Exception e){
            e.printStackTrace();
            return new LogMessage(false, "Exception occur" + e.getMessage());
        }
    }

    public LogMessage navigateToLeasePageFromProperty(String leaseName){
        String  objectLocatorPrefix = "Common.Property.";
        UILink uiLink = new UILink(webDriver);
        LogMessage log =uiLink.ClickLink(objectLocatorPrefix +"tbLease", leaseName);
        UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS);
        UtilKeywordScript.switchLastTab(webDriver);
        if (log.isPassed()){
            return new LogMessage(true, "Navigated to lease page");
        }else {
            return new LogMessage(false, "Lease does not exist");
        }
    }
    public List<LogMessage> isLeaseSpaceRecurExistsWithinAProperty(List<Map> leaseList, List<Map> spaceList, List<Map> recurList){
        List<LogMessage> logMessages= new ArrayList<>();
        try{
            PropertyCreateAndSearch propertyCreateAndSearch=new PropertyCreateAndSearch(webDriver);
            //property information is avaiable in lease information
            propertyCreateAndSearch.navigateToProperty(leaseList.get(0));
            String leaseObjectLocator="Common.Property.tbLease";
            String spaceObjectLocator="Common.Property.tbSpace";
            String recurObjectLocator="Common.Lease.tbRPayment";
            UILink uiLink=new UILink(webDriver);
            UIPanel uiPanel = new UIPanel(webDriver);
            LogMessage clickLinkLog = uiLink.ClickLink(null,"Expand All");

            if (!clickLinkLog.isPassed()){
                logMessages.add(new  LogMessage(false, "Error occur while expanding all in property named "+leaseList.get(0).get("propertyName")+" and code "+leaseList.get(0).get("propertyCode")));
                return logMessages;
            }
            UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS);
            for(Map lease:leaseList){
                LogMessage logMessage=uiPanel.VerifyPanelContentTrue(leaseObjectLocator, (String) lease.get("dbaName"));
                if(logMessage.isPassed()) {
                    logMessages.add(new LogMessage(true, "Lease named " + lease.get("dbaName") + " found under " + lease.get("propertyName") + " and code " + lease.get("propertyCode")));
                }
                else {
                    logMessages.add(new LogMessage(false, "Lease named " + lease.get("dbaName") + " not found under " + lease.get("propertyName") + " and code " + lease.get("propertyCode")));
                }
            }
            for(Map space:spaceList){
                LogMessage logMessage=uiPanel.VerifyPanelContentTrue(spaceObjectLocator, (String) space.get("Space"));
                if(logMessage.isPassed()) {
                    logMessages.add(new LogMessage(true, "Space named " + space.get("Space") + " found under " + space.get("propertyName") + " and code " + space.get("propertyCode")));
                }
                else {
                    logMessages.add(new LogMessage(false, "Space named " + space.get("dbaName") + " not found under " + space.get("propertyName") + " and code " + space.get("propertyCode")));
                }
            }
            Map<String, List<Map>> allRecur = new HashMap<>();

            for (Map recur : recurList) {
                if (!allRecur.containsKey(recur.get("LeaseName"))) {
                    List<Map> record = new ArrayList<Map>();
                    record.add(recur);
                    allRecur.put((String) recur.get("LeaseName"), record);
                } else {
                    allRecur.get(recur.get("LeaseName")).add(recur);
                }

            }
            for (String key : allRecur.keySet()) {
                mainWindow=webDriver.getWindowHandle();
                navigateToLeasePageFromProperty(key);
                UtilKeywordScript.switchLastTab(webDriver);
                UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS);
                LogMessage log = uiLink.ClickLink(null,"Expand All");
                UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS);
                if (!log.isPassed()) {
                    logMessages.add(new LogMessage(false, "exception occur during expanding lease information"));
                    return logMessages;
                }
                List<Map> recurUnderALease=allRecur.get(key);
                for(Map recur:recurUnderALease){
                    logMessages.add(isRecurExistsWithinLease(recurObjectLocator,recur));
                }
                Set<String> set =webDriver.getWindowHandles();
                Iterator<String> itr= set.iterator();
                while(itr.hasNext()){
                    String childWindow=itr.next();
                    if(!mainWindow.equals(childWindow)){
                        webDriver.switchTo().window(childWindow);
                        webDriver.close();
                    }

                }
                webDriver.switchTo().window(mainWindow);

            }
            return logMessages;
        }catch (Exception e){
            logMessages.add(new LogMessage(false, "Error occured in searching"+e.getMessage()));
            return logMessages;
        }
    }
     public LogMessage isRecurExistsWithinLease(String recurObjectLocator,Map recur){
            try{
                UIPanel uiPanel = new UIPanel(webDriver);
                String chargetype=(String) recur.get("chargeType");
                String  objectLocatorPrefix = "Common.Lease.";
                String columnValue1 = (String)recur.get("chargeType");
                String columnValue2 = (String)recur.get("spaceInfo");
                UITable uiTable=new UITable(webDriver);
                LogMessage logMessage = uiTable.VerifyCorrespondingColumnDataTrue(objectLocatorPrefix + "tbRPayment","Type,"+columnValue1.split("-")[0].trim() +",Space,"+columnValue2);
                if(logMessage.isPassed()) {
                    return new LogMessage(true, "Recurring Payment with charge type " + recur.get("chargeType") + " found under lease named " + recur.get("LeaseName"));
                }
                else {
                    return new LogMessage(false, "Recurring Payment with charge type " + recur.get("chargeType") + " was not found under lease named " + recur.get("LeaseName"));
                }
            }catch (Exception e){
                e.printStackTrace();
                return new LogMessage(false, "Exception occured in Recurring Payment with charge type " + recur.get("chargeType") + " was not found under lease named " + recur.get("LeaseName")+e.getMessage());
            }
     }
    public List<LogMessage> deleteLeases(String propertyName,String propertyCode, List<Map> leases){
        UtilKeywordScript utilKeywordScript=new UtilKeywordScript(webDriver);
        List<LogMessage> logMessages=new ArrayList<>();
        try {
            PropertyCreateAndSearch propertyCreateAndSearch = new PropertyCreateAndSearch(webDriver);
            UIBase uiBase=new UIBase(webDriver);
            UIPanel uiPanel=new UIPanel(webDriver);
            String objectLocatorData="Common.Lease.";
            Map<String, String> data = new HashMap<>();
            data.put("propertyName", propertyName);
            data.put("propertyCode", propertyCode);
            LogMessage logMessageProperty = propertyCreateAndSearch.navigateToProperty(data);
            String mainWindow=webDriver.getWindowHandle();
            UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS);
            if(logMessageProperty.isPassed()) {
                for (Map lease : leases) {
                    LogMessage logMessageSpace = navigateToLeasePageFromProperty((String)lease.get("dbaName"));
                    UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS);
                    if (logMessageSpace.isPassed()) {
                        UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*3);
                        WebElement webElement = WebObjectSearch.getChildWebElement(webDriver, objectLocatorData + "header", objectLocatorData + "delete");
                        uiBase.Click(webElement);
                        while (utilKeywordScript.isAlertPresent()) {
                            webDriver.switchTo().alert().accept();
                        }
                        //webDriver.close();
                        utilKeywordScript.switchToPreviousTab(webDriver,mainWindow);
                        uiBase.refreshPage();
                        UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*2);
                        if (uiPanel.VerifyPanelContentFalse("Common.Property.tbLease",(String) lease.get("dbaName")).isPassed()) {
                            logMessages.add(new LogMessage(true, "Lease is deleted"));
                        }
                        else {
                            logMessages.add(new LogMessage(false, "Lease cannot be deleted"));
                        }
                    } else
                        logMessages.add(new LogMessage(false, "Lease doesnot exist"));

                }
            }
            else {
                logMessages.add(new LogMessage(false, "Lease doesnot exist"));
            }
            utilKeywordScript.redirectHomePage();
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*2);
            return logMessages;
        }catch (Exception e){
            e.printStackTrace();
            utilKeywordScript.redirectHomePage();
            logMessages.add(new LogMessage(false,"Exception occur in lease delete "+e.getMessage()));
            return logMessages;
        }
    }

}
