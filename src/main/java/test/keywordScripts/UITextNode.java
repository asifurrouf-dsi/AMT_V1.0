package test.keywordScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import test.Log.LogMessage;
import test.objectLocator.ObjectLocatorDataStorage;
import test.objectLocator.WebObjectSearch;
import test.utility.PropertyConfig;

import java.util.List;
import java.util.Map;

public class UITextNode {
    private WebDriver webDriver;
    public UITextNode(){

    }
    public UITextNode(WebDriver webDriver){
        this.webDriver = webDriver ;
    }

    public LogMessage ClickTextNode(String objectLocator, String testData) {
        try {
            Map objectLocatorData = ObjectLocatorDataStorage.getObjectLocator(objectLocator);
            String objectLocatorPath= (String) objectLocatorData.get(PropertyConfig.OBJECT_LOCATORS);
            String testDatas[] = testData.split(",");

            List<WebElement> clickElement = webDriver.findElements(By.xpath(objectLocatorPath +"//*[text()='" +testDatas[0] + "']"));
            //WebElement rootElement = WebObjectSearch.getWebElement(webDriver,objectLocator);
            if (null == clickElement || clickElement.isEmpty())
                return new LogMessage(false,"element is not found.");

            UIBase uiBase = new UIBase(webDriver);
           return uiBase.ClickDbClickRClick(clickElement.get(0),testDatas[1]);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new LogMessage(false,"excetion occured: " + ex.getMessage());
        }
    }

}
