package test.keywordScripts;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import test.Log.LogMessage;
import test.objectLocator.WebObjectSearch;
import test.utility.PropertyConfig;
import java.util.Optional;

public class UIInput {
    private WebDriver webDriver;

    public UIInput(WebDriver driver) {
        this.webDriver = driver ;
    }
    public LogMessage compareValue(String objectLocator, String testData){
        try{
            String[] splittedTestData=testData.split(",");
            String attribute = getValue(objectLocator);
            if(attribute.equals(splittedTestData[0]))
                return new LogMessage(true, "Value is verified");
            else
                return new LogMessage(false, "Value is not verified");
        }catch (Exception e){
            return new LogMessage(false,"Exception occur" + e.getMessage());
        }
    }
    public String getValue(String objectLocator){
        try {
            WebElement webElement = WebObjectSearch.getWebElement(webDriver, objectLocator);
            if (null == webElement) {
                return "";
            }
            return Optional.ofNullable(webElement.getAttribute("value")).orElse("").trim();
        }catch (Exception e){
            return "";
        }
    }

    public LogMessage setValue(String objectLocator, String value){
        try{
            JavascriptExecutor jse = (JavascriptExecutor)webDriver;

            WebElement element = WebObjectSearch.getWebElement(webDriver,objectLocator);
            if(null == element )
                return new LogMessage(false,"webElement is not founding");
            jse.executeScript("arguments[0].scrollIntoView(true);", element);
            UtilKeywordScript.delay(PropertyConfig.ONE_SECOND);
            jse.executeScript("arguments[0].value='" + value + "';",element);
            return new LogMessage(true,"Value set successfully");
        }catch (Exception e){
            return new LogMessage(false,"Exception occur" + e.getMessage());
        }
    }

}
