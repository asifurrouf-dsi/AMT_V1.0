package test.keywordScripts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import test.Log.LogMessage;
import test.objectLocator.WebObjectSearch;

public class UIRadio {
    private WebDriver webDriver;
    public UIRadio(WebDriver driver) {
        this.webDriver = driver ;
    }
    public LogMessage SelectRadioBtn(String objectLocatorData)
    {
        try{
            WebElement element = WebObjectSearch.getWebElement(webDriver,objectLocatorData);
            if(null == element)
                return  new LogMessage(false, "Element is not found");
            element.click();
            return new LogMessage(true, "Radio Button is clicked");

        }catch (Exception e)
        {
            e.printStackTrace();
            return new LogMessage(false, "Radio Button is not clicked");
        }
    }


}
