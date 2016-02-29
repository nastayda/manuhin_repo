package ru.stqa.pft.addressbook414.appmanager;

import org.openqa.selenium.*;

/**
 * Created by Юрий on 28.02.2016.
 */
public class HelperBase {
  protected WebDriver driver;
  private boolean acceptNextAlert = true;

  public HelperBase(WebDriver driver) {
    this.driver = driver;
  }

  protected void type(By locator, String text) {
    driver.findElement(locator).clear();
    driver.findElement(locator).sendKeys(text);
  }

  protected void click(By locator) {
    driver.findElement(locator).click();
  }

  public boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }

}
