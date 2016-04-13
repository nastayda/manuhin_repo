package ru.stqa.pft.rzd.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.rzd.model.Train;
import ru.stqa.pft.rzd.model.Trains;


import java.util.List;

/**
 * Created by Юрий on 05.03.2016.
 */
public class TicketHelper extends HelperBase {

  private WebElement locator;

  public TicketHelper(WebDriver wd) {
    super(wd);
  }

  public void chooseDirection() throws InterruptedException {
    type(By.id("name0"), "Москва");
    click(By.cssSelector("div.station"));
    Thread.sleep(10000);
    type(By.id("name1"), "Сан");
    click(By.xpath("//div[7]/div[1]"));
    Thread.sleep(10000);
  }

  public void chooseDate(By dateLocator) {
    click(By.id("buttonDate"));
    click(dateLocator);
    click(By.id("Submit"));
  }

  public void chosenTrain(By locator) {
    List<WebElement> elements = wd.findElements(By.xpath("//*[@class=\"trlist__trlist-row trslot \"]"));


    radioButton(locator);
  }

  public Trains all() {
    Trains trains = new Trains();
    List<WebElement> rows = wd.findElements(By.xpath("//*[@class=\"trlist__trlist-row trslot \"]"));
    for (WebElement row: rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String allphones = cells.get(5).getText();
      String address = cells.get(3).getText();
      String allemails = cells.get(4).getText();
      int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));

      //trains.add(new Train().//withId(id).withFirstname(firstname).withLastname(lastname)
              //.withAllphones(allphones).withAddress(address).withAllmails(allemails)
              //);
    }
    return trains;
  }

}
