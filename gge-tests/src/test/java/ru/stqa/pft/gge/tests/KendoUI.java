package ru.stqa.pft.gge.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Created by Юрий on 22.05.2016.
 */
public class KendoUI {

  WebDriver wd = new FirefoxDriver();

  @BeforeMethod
  private void init() {
    wd.get("http://demos.telerik.com/aspnet-ajax/webmail/default.aspx");
  }

  @Test
  public void testsKendoUI() throws InterruptedException {
    String xpathButton = "//span[contains(text(),\"Kendo\")]";
    String xpathButton2 = "//li[@class=\"rtLI editable\"]//ul[@class=\"rtUL\"]//span[@class=\"rtIn\"]";

    WebDriverWait wait = new WebDriverWait(wd, 30);

    wait.until(visibilityOfElementLocated(By.xpath(xpathButton)));
    List<WebElement> elements = wd.findElements(By.xpath(xpathButton));

    if (elements.size() == 1) {
      WebElement element = elements.iterator().next();
      wait.until(visibilityOfElementLocated(By.xpath(xpathButton)));
      new Actions(wd).doubleClick(element).perform();

      List<WebElement> elements2 = wd.findElements(By.xpath(xpathButton2));
      WebElement element2 = elements2.iterator().next();

      for (WebElement e: elements2) {
        wait.until(visibilityOf(e)).click();
        //прикрутить скриншот
      }
      System.out.println("Прокликаны все папки под папкой 'Kendo UI'");
    } else if (elements.size() == 0) {
      System.out.println("Папка 'Kendo UI' не найдена");
    } else {
      System.out.println("Папок 'Kendo UI' более одной, не понятно по какой кликать");
    }
  }

  @AfterMethod(alwaysRun = true)
  private void finish() {
    if (wd != null) {
      wd.quit();
    }
  }
}
