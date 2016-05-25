package ru.stqa.pft.gge.tests;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.gge.pages.DisplayedElementLocatorFactory;
import ru.stqa.pft.gge.pages.MessageObject;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Юрий on 22.05.2016.
 */
public class KendoUI {

  private WebDriver wd;
  private MessageObject message;

  @BeforeMethod
  private void init() {
    wd = new FirefoxDriver();
    wd.get("http://demos.telerik.com/aspnet-ajax/webmail/default.aspx");
    message = new MessageObject();
    PageFactory.initElements(new DisplayedElementLocatorFactory(wd, 30), message);
  }

  @Test
  public void testsKendoUI() throws InterruptedException {
    doubleClick(wd, message.kendo);
    if (message.subMenus.size() > 0) {
      for (WebElement sm : message.subMenus) {
        try {
          sm.click();
          checkVisibilityOfChanges(sm, message);
        } catch (StaleElementReferenceException e) {
          System.out.println("Подпапка " + sm.getText() + ": ОШИБКА");
          continue;
        }
      }
    }
  }

  private void checkVisibilityOfChanges(WebElement sm, MessageObject message) {
    int dateTitlesCount = message.getDateTitles().size();
    int fromsCount = this.message.getFroms().size();
    int subjectsCount = this.message.getSubjects().size();
    int datesCount = this.message.getDates().size();
    String fromActualText = this.message.fromActual.getText();
    String fromActual2Text = this.message.fromActual2.getText();
    String dateActualText = this.message.dateActual.getText();
    String dateActual2Text = this.message.dateActual2.getText();
    int bodyActualCount = this.message.getBodyActual().size();

    assertThat(fromActualText, equalTo(fromActual2Text));

    System.out.println("Подпапка " + sm.getText() + ":");
    System.out.println("dateTitlesCount = " + dateTitlesCount);
    System.out.println("fromsCount = " + fromsCount);
    System.out.println("subjectsCount = " + subjectsCount);
    System.out.println("datesCount = " + datesCount);
    System.out.println("bodyActualCount = " + bodyActualCount);
    System.out.println("");
  }

  private void doubleClick(WebDriver wd, WebElement button) {
    new Actions(wd).doubleClick(button).perform();
  }

  @AfterMethod(alwaysRun = true)
  private void tearDown() {
    if (wd != null) {
      wd.quit();
    }
  }
}
