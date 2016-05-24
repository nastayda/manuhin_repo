package ru.stqa.pft.gge.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by manuhin on 24.05.2016.
 */
public class MessageObject extends PageFactory{

  @FindBy(xpath = "//span[contains(text(),\"Kendo\")]")
  public WebElement kendo;

  @FindBy(xpath = "//li[@class=\"rtLI editable\"]//ul[@class=\"rtUL\"]//span[@class=\"rtIn\"]")
  public List<WebElement> subMenus;

  @FindBy(xpath = "//td[@class=\"subject\"")
  public List<WebElement> subjects;

  @FindBy(xpath = "//span[@class=\\\"rgGroupHeaderText\\\"]")
  public List<WebElement> dateTitles;

}
