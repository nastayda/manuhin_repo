package ru.stqa.pft.gge.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
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
    String xpathButton2 = "./../span[@class=\"rtPlus\"]";

    WebDriverWait wait = new WebDriverWait(wd, 30);

    wait.until(visibilityOfElementLocated(By.xpath(xpathButton)));
    List<WebElement> elements = wd.findElements(By.xpath(xpathButton));

    if (elements.size() > 0) {
      WebElement element = elements.iterator().next();
      //List<WebElement> elements2 = element.findElements(By.xpath(xpathButton2));
      wait.until(visibilityOfElementLocated(By.xpath(xpathButton))).click();
      //Thread.sleep(100);
      //element.click();
      //Thread.sleep(100);

    } else {
      wait.until(presenceOfElementLocated(By.xpath(xpathButton)));

    }
  }

  @AfterMethod(alwaysRun = true)
  private void finish() {
    if (wd != null) {
      wd.quit();
    }
  }
}
