package ru.stqa.pft.gge.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;

import java.lang.reflect.Field;

/**
 * Created by Юрий on 25.05.2016.
 */
public class DisplayedElementLocator extends AjaxElementLocator {
  public DisplayedElementLocator(WebDriver driver, Field field, int timeOutInSeconds) {
    super(driver, field, timeOutInSeconds);
  }
  protected boolean isElementUsable(WebElement element) {
    return element.isDisplayed();
  }
}
