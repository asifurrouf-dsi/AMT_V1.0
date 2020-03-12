package test.keywordScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import test.Log.LogMessage;
import test.objectLocator.ObjectLocatorDataStorage;
import test.objectLocator.WebObjectSearch;
import test.utility.PropertyConfig;

import java.util.Map;

public class UIPanel {

    private WebDriver webDriver;

    public UIPanel(WebDriver driver) {
        this.webDriver = driver ;
    }

    public UIPanel(){

    }

    public LogMessage VerifyPanelContentTrue(String objectLocatorData,String testData){
        try{
            String elementPath = "//*[contains(text(),'" + testData + "')]";
            WebElement rootPanel = WebObjectSearch.getWebElement(webDriver,objectLocatorData);
            if (null == rootPanel)
                return new LogMessage(false,"Panel not found");
            WebElement elementToFind = rootPanel.findElement(By.xpath(elementPath));
            if (null == elementToFind)
                return new LogMessage(false,"Panel content not found");

            return new LogMessage(true, "Panel content verified");
        }catch (Exception e){
            e.printStackTrace();
            return new LogMessage(false, "Exception occur " +e.getMessage());
        }
    }
    public LogMessage VerifyPanelContentFalse(String objectLocatorData,String testData){
        try{
            String elementPath = "//*[contains(text(),'" + testData + "')]";
            WebElement rootPanel = WebObjectSearch.getWebElement(webDriver,objectLocatorData);
            if (null == rootPanel)
                return new LogMessage(false,"Panel not found");
            if(null==rootPanel.findElements(By.xpath(elementPath)) || rootPanel.findElements(By.xpath(elementPath)).size()<=0)
                return new LogMessage(true,"Panel content found");
            else
                return new LogMessage(false,"Panel content not found");
        }catch (Exception e){
            e.printStackTrace();
            return new LogMessage(false, "Exception occur " +e.getMessage());
        }
    }

}
