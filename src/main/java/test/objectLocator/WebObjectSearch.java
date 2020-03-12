package test.objectLocator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import test.keywordScripts.UtilKeywordScript;
import test.utility.PropertyConfig;

import java.util.List;
import java.util.Map;

public class WebObjectSearch {
    static public List<WebElement> searchWebObject(WebDriver driver,Map objectLocator) throws Exception {
        try {
            String objectSearchType = ( (String)objectLocator.get(PropertyConfig.OBJECT_SEARCH_KEY)).toUpperCase();
            WebObjectSearchType webObjectSearchType = WebObjectSearchType.valueOf(objectSearchType);
            List<WebElement> webElements =  webObjectSearchType.findElement(driver,(String) objectLocator.get(PropertyConfig.OBJECT_LOCATORS));
            int count=0;
            while(count<PropertyConfig.NUMBER_OF_ITERATIONS) {
                if (null == webElements || webElements.isEmpty()) {
                    UtilKeywordScript.delay(PropertyConfig.SHORT_WAIT_TIME_SECONDS);
                    webElements = webObjectSearchType.findElement(driver, (String) objectLocator.get(PropertyConfig.OBJECT_LOCATORS));
                    count++;
                }
                else
                    return webElements;
            }

             if(null == webElements || webElements.isEmpty())
                 return null;
             else
                return webElements;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("webElements not founding");
        }
    }

    static public  boolean validateObjectLocator(Map objectLocator){
        try {
            if(objectLocator.get(PropertyConfig.OBJECT_SEARCH_KEY).toString().isEmpty()) return  false ;
            if(objectLocator.get(PropertyConfig.OBJECT_LOCATORS).toString().isEmpty()) return  false ;
            return true ;
        } catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    static public WebElement getWebElement(WebDriver webDriver  ,String objectLocator) {
        try {
            Map objectLocatorData = ObjectLocatorDataStorage.getObjectLocator(objectLocator);
            String parentLocator=(String)objectLocatorData.get(PropertyConfig.PARENT);
            if(null!=parentLocator)
            {
                if(!(parentLocator.trim().isEmpty())) {
                    String[] splitedLocators = objectLocator.split("\\.");
                    return getChildWebElement(webDriver, splitedLocators[0] + "." + splitedLocators[1] + "." + parentLocator, objectLocator);
                }
            }
            List<WebElement> userelemnts = searchWebObject(webDriver, objectLocatorData);
            if(null == userelemnts)
                return null;
            else
                return userelemnts.get(userelemnts.size()-1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    static public WebElement getChildWebElement(WebDriver webDriver,String parentObjectLocator,String childObjectLocator){
        try{
            Map parentObjectLocatorData = ObjectLocatorDataStorage.getObjectLocator(parentObjectLocator);
            Map childObjectLocatorData = ObjectLocatorDataStorage.getObjectLocator(childObjectLocator);
            List<WebElement> webElements=webDriver.findElements(By.xpath((String)parentObjectLocatorData.get(PropertyConfig.OBJECT_LOCATORS)+(String)childObjectLocatorData.get(PropertyConfig.OBJECT_LOCATORS)));
            if(null == webElements || webElements.isEmpty())
                return null;
            else
                return webElements.get(webElements.size()-1);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    static public List<WebElement> getWebElements(WebDriver webDriver  ,String objectLocator) {
        try {
            Map objectLocatorData = ObjectLocatorDataStorage.getObjectLocator(objectLocator);
            return searchWebObject(webDriver, objectLocatorData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
