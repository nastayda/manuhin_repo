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

  @FindBy(xpath = "//span[@class=\"rgGroupHeaderText\"]")
  public List<WebElement> dateTitles;

  @FindBy(xpath = "//tr[@style=\"visibility: visible; display: table-row; font-weight: 500;\"]/td[3]")
  public List<WebElement> froms;

  @FindBy(xpath = "//tr[@style=\"visibility: visible; display: table-row; font-weight: 500;\"]/td[4]")
  public List<WebElement> subjects;

  @FindBy(xpath = "//tr[@style=\"visibility: visible; display: table-row; font-weight: 500;\"]/td[5]")
  public List<WebElement> dates;

  @FindBy(xpath = "//*[@id='ctl00_MainContent_messages_ctl00__0']/td[3]")
  public WebElement fromActual;

  @FindBy(xpath = "//*[@id='from']")
  public WebElement fromActual2;

  @FindBy(xpath = "//*[@id='ctl00_MainContent_messages_ctl00__0']/td[4]")
  public WebElement subjectActual;

  @FindBy(xpath = "//*[@id='subject']")
  public WebElement subjectActual2;

  @FindBy(xpath = "//*[@id='ctl00_MainContent_messages_ctl00__0']/td[5]")
  public WebElement dateActual;

  @FindBy(xpath = "//*[@id='recieved']")
  public WebElement dateActual2;

  @FindBy(xpath = "//*[@id='body']/p")
  public List<WebElement> bodyActual;

  public List<WebElement> getFroms() {
    return froms;
  }

  public List<WebElement> getSubjects() {
    return subjects;
  }

  public List<WebElement> getDateTitles() {
    return dateTitles;
  }

  public List<WebElement> getDates() {
    return dates;
  }

  public List<WebElement> getBodyActual() {
    return bodyActual;
  }
}
