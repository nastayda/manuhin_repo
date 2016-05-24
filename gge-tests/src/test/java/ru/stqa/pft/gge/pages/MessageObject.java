package ru.stqa.pft.gge.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by manuhin on 24.05.2016.
 */
public class MessageObject extends PageFactory{

  @FindBy(xpath = "//span[contains(text(),\"Kendo\")]")
  public WebElement kendo;

  @FindBy(xpath = "//li[@class=\"rtLI editable\"]//ul[@class=\"rtUL\"]//span[@class=\"rtIn\"]")
  public WebElement subMenu;

  @FindBy(xpath = "//td[@class=\"subject\"")
  public WebElement subject;

  public WebElement getSubject() {
    return subject;
  }

  public WebElement getKendo() {
    return kendo;
  }

  public void setKendo(WebElement kendo) {
    this.kendo = kendo;
  }

  public WebElement getSubMenu() {

    return subMenu;
  }

  public void setSubMenu(WebElement subMenu) {
    this.subMenu = subMenu;
  }
}
