package test.keywordScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import test.Log.LogMessage;
import test.objectLocator.WebObjectSearch;

public class UILink {

    private WebDriver webDriver;

    public UILink(WebDriver driver) {
        this.webDriver = driver ;
    }

    public UILink(){

    }

    public LogMessage ClickLink(String objectLocatorData, String Linkname){
        try{
            UIBase uiBase= new UIBase(webDriver);
            WebElement webElement;
            if(null!=objectLocatorData && !objectLocatorData.isEmpty() ) {
                if(null!=WebObjectSearch.getWebElement(webDriver,objectLocatorData)) {
                    webElement = WebObjectSearch.getWebElement(webDriver, objectLocatorData);
                    webElement = webElement.findElement(By.linkText(Linkname));
                }
                else{
                    webElement = webDriver.findElement(By.linkText(Linkname));
                }
            }
            else{
                webElement = webDriver.findElement(By.linkText(Linkname));
            }
            uiBase.Click(webElement);
            return new LogMessage(true,"web element is clicked");
        }catch (Exception e){
            e.printStackTrace();
            return new LogMessage(false,"Exception occured"+e.getMessage());
        }
    }

}
