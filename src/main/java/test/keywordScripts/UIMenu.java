package test.keywordScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import test.Log.LogMessage;

public class UIMenu {
    private WebDriver webDriver;

    public UIMenu(WebDriver driver) {
        this.webDriver = driver ;
    }

    public UIMenu(){

    }

    public LogMessage SelectMenu(String objectLocator,String testData ) {
        try {

             String menuItems[] = testData.split(",");
             for(String menuItem : menuItems) {
                 webDriver.findElement(By.linkText(menuItem)).click();
             }
             return new LogMessage(true,"menu items are clicked");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new LogMessage(false,"exception occured:- " + ex.getMessage());
        }
    }
}
