package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Юрий on 28.02.2016.
 */
public class HelperBase {
  protected WebDriver driver;

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
}
