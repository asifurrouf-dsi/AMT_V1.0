package test.beforeTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import test.Log.LogMessage;
import test.keywordScripts.*;
import test.objectLocator.WebObjectSearch;
import test.utility.PropertyConfig;

import java.util.*;

public class PropertyCreateAndSearch {
    private WebDriver webDriver;

    public PropertyCreateAndSearch(WebDriver driver) {
        this.webDriver = driver ;
    }

    public PropertyCreateAndSearch(){

    }



    public LogMessage createProperty(Map data) {
        UtilKeywordScript utilKeywordScript = new UtilKeywordScript(webDriver);
        try {
            String[] textFields = new String[] {"propertyName","propertyCode","address1" , "postal" , "city" , "sqFtRentable"} ;
            String[] dropDownFields = new String[] {"country","state","codeType" , "status" , "currency","chartType","propertyGroup1", "propertyGroup2", "propertyGroup3"} ;
            String autoManageChkBox = "autoManage" ;
            String  objectlocatorPrefix = "Common.Property." ;
            UIText uiText=new UIText(webDriver);
            UIMenu menu = new UIMenu(webDriver);
            menu.SelectMenu("Common.Homepage.pgAMTHome" , "Portfolio Insight,Add,Property") ;
            UtilKeywordScript.switchLastTab(webDriver);
            UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS *2);

            for(String elementName : textFields) {
                WebElement element = WebObjectSearch.getWebElement(webDriver,objectlocatorPrefix  + elementName) ;
                element.clear();
                element.sendKeys( (String) data.get(elementName));
                UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*3);
            }

            for(String elementName : dropDownFields) {
                WebElement element = WebObjectSearch.getWebElement(webDriver,objectlocatorPrefix  + elementName) ;
                Select select = new Select(element);
                for(Object key: data.keySet()){
                    String temp= (String)key;
                    if(temp.contains(elementName))
                        elementName=temp;

                }
                select.selectByVisibleText( (String)data.get(elementName));
                if(elementName.equals("codeType") || elementName.equals("chartType")) {
                    webDriver.switchTo().alert().accept();
                    UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*3);
                }
                UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*3);
            }
            WebElement checkBoxItem = WebObjectSearch.getWebElement(webDriver,objectlocatorPrefix + autoManageChkBox) ;
            if(  data.get(autoManageChkBox).toString().toLowerCase().equals("true"))
                checkBoxItem.click();
            WebElement element = WebObjectSearch.getWebElement(webDriver,objectlocatorPrefix + "save") ;
            element.click();
            LogMessage logMessage=uiText.WaitForVisibilityOfText(objectlocatorPrefix+"propertyCodeCheck", (String) data.get("propertyCode"));
            if(logMessage.isPassed())
            {
                utilKeywordScript.redirectHomePage();
                return new LogMessage(true, "Property"+data.get("propertyName")+"-"+data.get("propertyCode") +" is created successfully");
            }
            else {
                utilKeywordScript.redirectHomePage();
                return new LogMessage(false, "Property"+data.get("propertyName")+"-"+data.get("propertyCode") +" is not created");
            }
        } catch ( Exception ex) {

            ex.printStackTrace();
            utilKeywordScript.redirectHomePage();
            return new LogMessage(false, "Property"+data.get("propertyName")+"-"+data.get("propertyCode") +" is not created") ;
        }


    }


    public LogMessage isPropertyExist(Map data){
        UtilKeywordScript utilKeywordScript = new UtilKeywordScript(webDriver);
        try{
            String  objectLocatorPrefix = "Common.Property.";
            UITable uiTable  = new UITable(webDriver);
            utilKeywordScript.globalSearch((String)data.get("propertyCode"),"Property");

            Map<String, WebElement> row = uiTable.getSingleRowfromTable(objectLocatorPrefix +"tbProperty", "Property Code",(String)data.get("propertyCode"),null);
            if(null == row || row.isEmpty()){
                utilKeywordScript.redirectHomePage();
                return new LogMessage(false, "Property not found");
            }
            else{
                utilKeywordScript.redirectHomePage();
                return new LogMessage(true, "Property  found");
            }
        }catch (Exception e){
            utilKeywordScript.redirectHomePage();
            return new LogMessage(false, "Exception occur "+ e.getMessage());

        }
    }

    public LogMessage navigateToProperty(Map data){
        try{
            String  objectLocatorPrefix = "Common.Property.";
            String columnName = "Property Name";
            String columnValue = (String)data.get("propertyName");
            UITable uiTable  = new UITable(webDriver);
            UtilKeywordScript utilKeywordScript = new UtilKeywordScript(webDriver);
            LogMessage searchLog = utilKeywordScript.globalSearch((String)data.get("propertyCode"),"Property");
            if (!searchLog.isPassed())
                return new LogMessage(false,"Exception occur in global search");
            Map<String, WebElement> propertyRow = uiTable.getSingleRowfromTable(objectLocatorPrefix +"tbProperty", "Property Code",(String)data.get("propertyCode"),null);
            if(null == propertyRow || propertyRow.isEmpty()){
                utilKeywordScript.redirectHomePage();
                return new LogMessage(false, "Property not found");
            }
            for (String key : propertyRow.keySet()) {
                String clName = Optional.ofNullable(key).orElse("noValue");
                if(clName.contains(columnName)){
                    WebElement element = propertyRow.get(key) ;
                    String text = element.getText();
                    if(columnValue.equals(text)) {
                        WebElement elm = element.findElement(By.linkText(columnValue));
                        elm.click();
                    }
                    else {
                        return new LogMessage(false, "Property name is not matching");

                    }
                }
            }


            UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS);
            webDriver.close();
            UtilKeywordScript.switchLastTab(webDriver);
            UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS);
            return new LogMessage(true, "Navigate to property complete");
        }catch (Exception e){
            return new LogMessage(false, "Exception occur " + e.getMessage());
        }
    }
    public LogMessage deleteProperty(String propertyName, String propertyCode){
        UtilKeywordScript utilKeywordScript=new UtilKeywordScript(webDriver);
        try{
            String  objectLocatorPrefix = "Common.Property.";
            Map<String,String> map =new HashMap<>();
            map.put("propertyCode",propertyCode);
            map.put("propertyName",propertyName);
            UIBase uiBase=new UIBase(webDriver);
            UIText uiText= new UIText(webDriver);
            LogMessage logMessage=navigateToProperty(map);
            if(logMessage.isPassed()) {
                WebElement webElement;
                uiText.WaitForVisibilityOfText(objectLocatorPrefix+"propertyCodeCheck", propertyCode);
                webElement = WebObjectSearch.getWebElement(webDriver, objectLocatorPrefix + "delete");
                uiBase.Click(webElement);
                while ( utilKeywordScript.isAlertPresent()) {
                    webDriver.switchTo().alert().accept();
                }
                UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS);
                if (uiBase.VerifyPageLoadedTrue("Common.Homepage.pgAMTHome").isPassed()) {
                    utilKeywordScript.redirectHomePage();
                    return new LogMessage(true, "Property is deleted");
                }
                else{
                    utilKeywordScript.redirectHomePage();
                    return new LogMessage(false, "Property is not deleted");
                }
            }
            else {
                utilKeywordScript.redirectHomePage();
                return new LogMessage(false, "Property is doesnot exists");
            }

        }catch (Exception e){
            utilKeywordScript.redirectHomePage();
            return new LogMessage(false, "Exception occur " + e.getMessage());
        }

    }


}