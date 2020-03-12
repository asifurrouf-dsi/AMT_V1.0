package test.keywordScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.Log.LogMessage;
import test.objectLocator.ObjectLocatorDataStorage;
import test.objectLocator.WebObjectSearch;
import test.utility.PropertyConfig;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;


import java.util.Map;
import java.util.Optional;

public class UIText {
    private WebDriver webDriver;
    public UIText(){

    }
    public UIText(WebDriver webDriver){
        this.webDriver = webDriver ;
    }

    public LogMessage SetText(String objectLocator, String textData){
        try {
            UIBase uiBase = new UIBase(webDriver);
            WebElement userWeb = WebObjectSearch.getWebElement(webDriver,objectLocator);
            if(null == userWeb )
                return new LogMessage(false,"webElement is not founding");
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", userWeb);
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND);
            uiBase.Click(userWeb);
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND);
            userWeb.sendKeys(textData);
            return new LogMessage(true,"text is set up");
        } catch(Exception ex){
            ex.printStackTrace();
            return new LogMessage(false,"exception occured:- " + ex.getMessage());
        }
    }


    public LogMessage WaitForVisibilityOfText(String objectLocator, String textData){
        try {
            String[] splittedTestData = textData.split(",");
            int time = PropertyConfig.WAIT_TIME_EXPLICIT_WAIT;
            if (splittedTestData.length >= 2) {
                time = Integer.parseInt(splittedTestData[1]);
                textData = splittedTestData[0];
            }

            Map objectLocatorData = ObjectLocatorDataStorage.getObjectLocator(objectLocator);
            String objectLocatorPath= (String) objectLocatorData.get(PropertyConfig.OBJECT_LOCATORS);
            WebDriverWait wait = new WebDriverWait(webDriver, time);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(objectLocatorPath +"//*[contains(text(),'" + textData + "')]")));
            return new LogMessage(true, "Text is visible");
        }catch (Exception e){
            e.printStackTrace();
            return new LogMessage(false, "Exception occur " + e.getMessage());
        }
    }

    public LogMessage WaitForInvisibilityOfText(String objectLocator, String textData){
        try {
            String[] splittedTestData = textData.split(",");
            int time = PropertyConfig.WAIT_TIME_EXPLICIT_WAIT;
            if (splittedTestData.length >= 2) {
                time = Integer.parseInt(splittedTestData[1]);
                textData = splittedTestData[0];
            }
            WebDriverWait wait = new WebDriverWait(webDriver, time);
            Map objectLocatorData = ObjectLocatorDataStorage.getObjectLocator(objectLocator);
            String objectLocatorPath= (String) objectLocatorData.get(PropertyConfig.OBJECT_LOCATORS);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(objectLocatorPath + "//*[contains(text(),'" + textData + "')]")));
            return new LogMessage(true, "Text is invisible");
        }catch (Exception e){
            return new LogMessage(false, "Exception occur " + e.getMessage());
        }
    }

    public LogMessage SetTextWithoutClear(String objectLocator, String textData){
        try {
            WebElement element = WebObjectSearch.getWebElement(webDriver,objectLocator);
            if(null == element )
                return new LogMessage(false,"Element not found");
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND);
            UIBase uiBase = new UIBase(webDriver);
            uiBase.Click(element);
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND);
            element.sendKeys(textData);
            return new LogMessage(true,"Text is set up");
        } catch(Exception ex){
            ex.printStackTrace();
            return new LogMessage(false,"exception occured:- " + ex.getMessage());
        }
    }

    public String getText(String objectLocator){
        try {
            WebElement webElement = WebObjectSearch.getWebElement(webDriver, objectLocator);
            if (null == webElement) {
                return "";
            }
            return Optional.ofNullable(webElement.getAttribute("textContent")).orElse("").trim();
        }catch (Exception e){
            return "";
        }
    }
    public LogMessage compareText(String objectLocator, String testData){
        try{
            String[] splittedTestData=testData.split(",");
            String attribute = getText(objectLocator);
            if(attribute.equals(splittedTestData[0].trim()))
                return new LogMessage(true, "Value is verified");
            else
                return new LogMessage(false, "Value is not verified");
        }catch (Exception e){
            return new LogMessage(false,"Exception occur" + e.getMessage());
        }
    }


}
