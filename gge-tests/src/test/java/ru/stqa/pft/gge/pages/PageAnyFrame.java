package ru.stqa.pft.gge.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by Юрий on 04.06.2016.
 */
public class PageAnyFrame extends PageFactory {

  @FindBy (xpath="//a[@class=\"page-up\"][contains(text(),\"Qwerty123\")]")
  public List<WebElement> elementsToFind;

  @FindBy(xpath = "//frame")
  public List<WebElement> frames;

  @FindBy(xpath = "//iframe")
  public List<WebElement> iframes;

  public List<WebElement> getElementsToFind() {
    return elementsToFind;
  }

  public List<WebElement> getFrames() {
    return frames;
  }

  public List<WebElement> getIframes() {
    return iframes;
  }
}
