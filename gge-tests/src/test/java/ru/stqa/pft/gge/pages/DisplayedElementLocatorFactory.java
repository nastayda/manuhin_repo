package ru.stqa.pft.gge.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

/**
 * Created by Юрий on 25.05.2016.
 */
public class DisplayedElementLocatorFactory implements ElementLocatorFactory {
  private final WebDriver driver;
  private final int timeOutInSeconds;

  public DisplayedElementLocatorFactory(WebDriver driver, int timeOutInSeconds) {
    this.driver = driver;
    this.timeOutInSeconds = timeOutInSeconds;
  }

  public ElementLocator createLocator(Field field) {
    return new DisplayedElementLocator(driver, field, timeOutInSeconds);
  }
}
