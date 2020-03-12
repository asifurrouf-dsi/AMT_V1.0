package test.keywordScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.Log.LogMessage;
import test.objectLocator.WebObjectSearch;
import test.utility.PropertyConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class UITree {
    private WebDriver webDriver;

    public UITree(WebDriver driver) {
        this.webDriver = driver ;
    }
    public LogMessage AssetTreeMenuExpand(String objectLocatorData, String elements){
        try {
            String[] listOfElements;
            System.out.println(webDriver.getCurrentUrl());
            UtilKeywordScript.delay(20);
            JavascriptExecutor jse = (JavascriptExecutor)webDriver;
            jse.executeScript("document.getElementById('RTVTree').setAttribute('style','overflow:hidden');");
            WebElement element=webDriver.findElement(By.xpath("//*[@id='RTVTree']"));
            WebElement element1;
            /*if(null==elements || elements.equals(""))
                return new LogMessage(false,"Nothing to expand");
            else
                listOfElements=elements.split(",");
            if(null==objectLocatorData || objectLocatorData.equals(""))
                return new LogMessage(false,"Object Locator not defined");
            else
                element1=webDriver.findElement(By.xpath("//*[@id='navTreePane']//*[@id='RTVTree']//*[@class='rtPlus']"));
                System.out.println(element1);
                element = webDriver.findElement(By.xpath(objectLocatorData+"//*[@class='rtMinus']"));
                System.out.println(element);
                if(null!=element) {
                    element.click();
                    System.out.println(element.getText());
                }*/

            return new LogMessage(true,"web element is expanded");
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return new LogMessage(false,"web element is not expanded");
        }
    }
}
