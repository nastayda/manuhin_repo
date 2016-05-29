package ru.stqa.pft.gge.tests;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.gge.pages.DisplayedElementLocatorFactory;
import ru.stqa.pft.gge.pages.MessageObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
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

  private void checkVisibilityOfChanges(WebElement sm, MessageObject message) throws InterruptedException {
    String s = sm.getText().replaceAll("[^\\d]", "");
    int num = Integer.parseInt(s);

    int dateTitlesCount = countElements(num, message.getDateTitles());
    int fromsCount = countElements(num-1,message.getFroms());
    int subjectsCount = countElements(num-1,message.getSubjects());
    int datesCount = countElements(num-1,message.getDates());
    String fromActualText = message.fromActual.getText();
    String fromActual2Text = message.fromActual2.getText();
    String dateActualText = message.dateActual.getText();
    String dateActual2Text = message.dateActual2.getText();
    int bodyActualCount = message.getBodyActual().size();

    System.out.println("Подпапка " + sm.getText() + ":");
    System.out.println("dateTitlesCount = " + dateTitlesCount);
    System.out.println("fromsCount = " + fromsCount);
    System.out.println("subjectsCount = " + subjectsCount);
    System.out.println("datesCount = " + datesCount);
    System.out.println("bodyActualCount = " + bodyActualCount);
    System.out.println("");

    assertThat(fromActualText, equalTo(fromActual2Text));
    assertThat(dateTitlesCount, equalTo(num));
    assertThat(fromsCount, equalTo(num-1));
    assertThat(subjectsCount, equalTo(num-1));
    assertThat(datesCount, equalTo(num-1));
  }

  private int countElements(int num, List<WebElement> elements) throws InterruptedException {
    int countElements = 0;
    int i = 0;
    int maxI = 60;
    do {
      countElements = elements.size();
      if (countElements == num) {
        break;
      } else {
        Thread.sleep(500);
      }
      i++;
    } while (i < maxI);
    return countElements;
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
