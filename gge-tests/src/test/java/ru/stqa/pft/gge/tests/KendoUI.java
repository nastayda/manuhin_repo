package ru.stqa.pft.gge.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.gge.pages.DisplayedElementLocatorFactory;
import ru.stqa.pft.gge.pages.MessageObject;

import java.lang.reflect.Field;

/**
 * Created by Юрий on 22.05.2016.
 */
public class KendoUI {

  private WebDriver wd;
  //private WebDriverWait wait;
  private MessageObject message;

  @BeforeMethod
  private void init() {
    wd = new FirefoxDriver();
    wd.get("http://demos.telerik.com/aspnet-ajax/webmail/default.aspx");
    message = new MessageObject();
    PageFactory.initElements(new DisplayedElementLocatorFactory(wd, 30), message);
    //wait = new WebDriverWait(wd, 30);
  }

  @Test
  public void testsKendoUI2() throws InterruptedException {

   // wait.until(visibilityOf(message.kendo));
    new Actions(wd).doubleClick(message.kendo).perform();
    if (message.subMenus.size() > 0) {
     // message.subMenus.iterator().next();

      for (WebElement sm : message.subMenus) {
        sm.click();
        //wait.until(visibilityOf(sm)).click();

       // message.dateTitles.iterator().next();
        for (WebElement dt : message.dateTitles) {
          //wait.until(visibilityOf(dt));
        }
      }
    }



//      WebElement element = elements.iterator().next();
//      wait.until(visibilityOfElementLocated(By.xpath(xpathButton)));
//      new Actions(wd).doubleClick(element).perform();
//
//      List<WebElement> elements2 = wd.findElements(By.xpath(xpathButton2));
//      WebElement element2 = elements2.iterator().next();
//
//      // проверка текста блока 1 - Даты
//      String xpathS1 = "//span[@class=\"rgGroupHeaderText\"]";
//
//      // проверка текста блока 2 - Таблица
//      String xpathS2 = "//tr/td[contains(text(),.)]";
//
//      for (WebElement e: elements2) {
//        wait.until(visibilityOf(e)).click();
//
//        List<WebElement> block1Elements = wd.findElements(By.xpath(xpathS1));
//        block1Elements.iterator().next();
//        //wait.until(visibilityOfAllElements(block1Elements));
//
//        if (block1Elements.size() > 0) {
//          int i = 0;
//          for (WebElement eB1 : block1Elements) {
//            i++;
//
//            wait.until(presenceOfElementLocated(By.xpath("(" + xpathS1 + ")[" + i + "]")));
//            wait.until(visibilityOfElementLocated(By.xpath("(" + xpathS1 + ")[" + i + "]")));
//          }
//        }
//
//        List<WebElement> block2Elements = wd.findElements(By.xpath(xpathS2));
//        block2Elements.iterator().next();
//        if (block1Elements.size() > 0) {
//          for (WebElement eB2 : block1Elements) {
//            wait.until(visibilityOf(eB2));
//          }
//        }
//      }
  }

//  @Test
//  public void testsKendoUI() throws InterruptedException {
//    String xpathButton = "//span[contains(text(),\"Kendo\")]";
//    String xpathButton2 = "//li[@class=\"rtLI editable\"]//ul[@class=\"rtUL\"]//span[@class=\"rtIn\"]";
//
//   // WebDriverWait wait = new WebDriverWait(wd, 30);
//
//    wait.until(visibilityOfElementLocated(By.xpath(xpathButton)));
//    List<WebElement> elements = wd.findElements(By.xpath(xpathButton));
//
//    if (elements.size() == 1) {
//      WebElement element = elements.iterator().next();
//      wait.until(visibilityOfElementLocated(By.xpath(xpathButton)));
//      new Actions(wd).doubleClick(element).perform();
//
//      List<WebElement> elements2 = wd.findElements(By.xpath(xpathButton2));
//      WebElement element2 = elements2.iterator().next();
//
//      // проверка текста блока 1 - Даты
//      String xpathS1 = "//span[@class=\"rgGroupHeaderText\"]";
//
//      // родительская таблицы
//      String xpathParent = "//tr[@style=\"visibility: visible; display: table-row; font-weight: 500;\"]";
//
//      // проверка текста блока 2 - Subject
//      String xpathS2 = xpathParent + "//td[@class=\"subject\"]";
//
//      // проверка текста блока 3 - Date
//      String xpathS3 = xpathParent + "//td[5]";
//
//      // проверка текста блока 4 - From
//      String xpathS4 = xpathParent + "//td[3]";
//
//      for (WebElement e: elements2) {
//        String text = e.getText();
//        String text2 = text.replaceAll(".[\\d]","");
//        wait.until(visibilityOf(e)).click();
//
//
//
//        List<WebElement> block1Elements = getWebElements(xpathS1);
//        List<WebElement> block2Elements = getWebElements(xpathS2);
//        List<WebElement> block3Elements = getWebElements(xpathS3);
//        List<WebElement> block4Elements = getWebElements(xpathS4);
//
//      }
//      System.out.println("Прокликаны все папки под папкой 'Kendo UI'");
//    } else if (elements.size() == 0) {
//      System.out.println("Папка 'Kendo UI' не найдена");
//    } else {
//      System.out.println("Папок 'Kendo UI' более одной, не понятно по какой кликать");
//    }
//  }

//  private List<WebElement> getWebElements(String locator) {
//    List<WebElement> elements = wd.findElements(By.xpath(locator));
//    elements.iterator().next();
//
//    if (elements.size() > 0) {
//      int i = 0;
//      for (WebElement eB1 : elements) {
//        i++;
//        wait.until(presenceOfElementLocated(By.xpath("(" + locator + ")[" + i + "]")));
//        wait.until(visibilityOfElementLocated(By.xpath("(" + locator + ")[" + i + "]")));
//      }
//    }
//    return elements;
//  }

  @AfterMethod(alwaysRun = true)
  private void finish() {
    if (wd != null) {
      wd.quit();
    }
  }
}
