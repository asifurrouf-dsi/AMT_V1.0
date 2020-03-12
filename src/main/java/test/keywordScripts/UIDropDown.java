package test.keywordScripts;

import com.aventstack.extentreports.model.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import test.Log.LogMessage;
import test.objectLocator.ObjectLocatorDataStorage;
import test.objectLocator.WebObject;
import test.objectLocator.WebObjectSearch;
import test.utility.PropertyConfig;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UIDropDown {

    private WebDriver webDriver;
    public UIDropDown(WebDriver driver) {
        this.webDriver = driver ;
    }

    public UIDropDown(){

    }

    public LogMessage SelectItem(String objectLocator,String testData) {
        try {
            Map objectLocatorData = ObjectLocatorDataStorage.getObjectLocator(objectLocator);
            if(null != objectLocatorData.get(PropertyConfig.PARENT_LOCATOR)) {
                return SelectItem(objectLocator,(String) objectLocatorData.get(PropertyConfig.PARENT_LOCATOR),testData);
            }

            WebElement dropDownElement = WebObjectSearch.getWebElement(webDriver,objectLocator);
            if (null == dropDownElement)
                return new LogMessage(false," element is not found.");
            Select dropDown = new Select(dropDownElement);
               dropDown.selectByVisibleText(testData);
            return new LogMessage(true, "drop down item is selected") ;
        } catch (Exception ex) {
           return  new LogMessage(false, "exception occured: " + ex.getMessage()) ;
        }
    }


    public LogMessage SelectItem(String objectLocatorData,String dropDownObjectLocatorData,String testData) {
        try {
            UIBase uiBase = new UIBase(webDriver);
            WebElement dropDownElement = WebObjectSearch.getWebElement(webDriver,objectLocatorData);
            if (null == dropDownElement)
                return new LogMessage(false," drop down element is not found.");
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*2);
            uiBase.Click(dropDownElement);
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*3);
            List<WebElement> dropDownDataElements = webDriver.findElements(By.xpath(dropDownObjectLocatorData + "//*[contains(text() , '" + testData + "')]"));
            if (null == dropDownDataElements || dropDownDataElements.isEmpty())
                return new LogMessage(false," drop down list element is not found.");
            WebElement dropDownDataElement = dropDownDataElements.get(dropDownDataElements.size()-1);
            uiBase.Click(dropDownDataElement);
            return new LogMessage(true, "drop down item is selected") ;
        } catch ( Exception ex) {
            ex.printStackTrace();
            return new LogMessage(false, "exception occured: " + ex.getMessage()) ;
        }
    }
    public LogMessage searchAndSelectItem(String objectLocatorData,String dropDownObjectLocatorData,String testData) {
        try {
            WebElement dropDownElement = WebObjectSearch.getWebElement(webDriver,objectLocatorData);
            if (null == dropDownElement)
                return new LogMessage(false," drop down element is not found.");
            dropDownElement.clear();
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND);
            dropDownElement.sendKeys(testData);
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND*3);
            List<WebElement> dropDownDataElements = webDriver.findElements(By.xpath(dropDownObjectLocatorData + "//*[text() = '" + testData + "']"));
            if (null == dropDownDataElements || dropDownDataElements.isEmpty())
                return new LogMessage(false," drop down list element is not found.");
            dropDownDataElements.get(dropDownDataElements.size()-1).click();
            //UIBase uiBase = new UIBase(webDriver);
            // uiBase.Click(dropDownDataElement);
            return new LogMessage(true, "drop down item is selected") ;
        } catch ( Exception ex) {
            ex.printStackTrace();
            return new LogMessage(false, "exception occured: " + ex.getMessage()) ;
        }
    }
    public String getDropDownValue(String objectLocator){
        try {
            Map objectLocatorData = ObjectLocatorDataStorage.getObjectLocator(objectLocator);
            String objectLocatorPath= (String) objectLocatorData.get(PropertyConfig.OBJECT_LOCATORS);
            if(null != objectLocatorData.get(PropertyConfig.PARENT_LOCATOR)) {
                WebElement webElement=webDriver.findElement(By.xpath(objectLocatorPath+"//*[@class='k-input']"));
                if(null == webElement)
                    return "";
                return Optional.ofNullable(webElement.getAttribute("textContent")).orElse("").trim();
            }
            WebElement dropDownElement = WebObjectSearch.getWebElement(webDriver,objectLocator);
            if (null == dropDownElement)
                return "";
            Select dropDown = new Select(dropDownElement);
            return Optional.ofNullable(dropDown.getFirstSelectedOption().getAttribute("textContent")).orElse("");

        }catch (Exception e){
            return "";
        }

    }
    public LogMessage compareDropDownValue(String objectLocator,String testData){
        try{
            String[] splittedTestData=testData.split(",");
            String attribute = getDropDownValue(objectLocator);
            if(attribute.equals(splittedTestData[0].trim()))
                return new LogMessage(true, "Value is verified");
            else
                return new LogMessage(false, "Value is not verified");
        }catch (Exception e){
            return new LogMessage(false,"Exception occur" + e.getMessage());
        }
    }




}
