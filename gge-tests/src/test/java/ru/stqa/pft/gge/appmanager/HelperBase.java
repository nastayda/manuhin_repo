package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

import static org.testng.Assert.fail;

/**
 * Created by Юрий on 05.03.2016.
 */
public class HelperBase {
  protected WebDriver wd;

  public HelperBase(WebDriver wd) {
    this.wd = wd;
  }

  public void click(By locator) {
    wd.findElement(locator).click();
  }

  protected void type(By locator, String text) {
    if (text != null) {
      String existingText = wd.findElement(locator).getAttribute("value");
      if (! text.equals(existingText)) {
        click(locator);
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(text);
      }
    }
  }

  protected void type(WebElement webElement, String text) {
    if (text != null) {
      String existingText = webElement.getAttribute("value");
      if (! text.equals(existingText)) {
        webElement.click();
        webElement.clear();
        webElement.sendKeys(text);
      }
    }
  }

  protected void attach(By locator, File file) {
    if (file != null) {
      wd.findElement(locator).sendKeys(file.getAbsolutePath());
    }
  }

  protected boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  public void waitLoadPage() throws InterruptedException {
    waitElement(By.xpath("//div[@id=\"overlay\" and @style=\"display: none;\"]"));   // test-eis, 82-й
    //waitElement(By.xpath("//div[@id=\"overlay\" and @style=\"display: none; width: 100%;\"]")); // eis
  }

  protected void waitElement(By locator) throws InterruptedException {
    for (int second = 0;; second++) {
      if (second >= 60) {
        fail("timeout");
      }
      try {
        if (isElementPresent(locator)) {
          break;
        }
      }
      catch (Exception e) {
      }
      Thread.sleep(1000);
    }
  }

  protected boolean isElementPresent(By locator) {
    try {
      wd.findElement(locator);
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }
}
