package test.objectLocator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.utility.PropertyConfig;

import java.util.List;

public enum WebObjectSearchType implements WebObject {

    BY_ID {

        @Override
        public List<WebElement> findElement(WebDriver driver, String searchKey) {
            List <WebElement> elements= driver.findElements(By.id(searchKey));
            return elements;
        }
    },
    BY_NAME {
        @Override
        public List<WebElement> findElement(WebDriver driver, String searchKey) {
            List <WebElement> elements=driver.findElements(By.name(searchKey));

            return elements;
        }
    },
    BY_CLASSNAME{
        @Override
        public List<WebElement> findElement(WebDriver driver, String searchKey) {

            List <WebElement> elements=driver.findElements(By.className(searchKey));
            return elements;

        }
    },
    TEST{
        @Override
        public List<WebElement> findElement(WebDriver driver, String searchKey) {
            return null ;
        }
    },
    BY_XPATH{
        @Override
        public List<WebElement> findElement(WebDriver driver, String searchKey) {
            List <WebElement> elements= driver.findElements(By.xpath(searchKey));
            return elements;

        }
    },
    BY_LINKTEXT{
        @Override
        public List<WebElement> findElement(WebDriver driver, String searchKey) {
            List <WebElement> elements= driver.findElements(By.linkText(searchKey));
            return elements;

        }
    },
}
