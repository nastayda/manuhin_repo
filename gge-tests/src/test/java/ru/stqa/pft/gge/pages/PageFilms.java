package ru.stqa.pft.gge.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by Юрий on 28.05.2016.
 */
public class PageFilms extends PageFactory{

  @FindBy(xpath = "//div[@class=\"searchbox\"]/input[@id=\"q\"]")
  public WebElement find;

  @FindBy(xpath = "//div[@class=\"title\"]")
  public List<WebElement> titles;

  public List<WebElement> getTitles() {
    return titles;
  }
}
