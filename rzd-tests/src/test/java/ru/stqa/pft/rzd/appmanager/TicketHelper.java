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

  public Trains all() throws InterruptedException {
    Thread.sleep(10000);
    Trains trains = new Trains();
    List<WebElement> rows = wd.findElements(By.xpath("//*[@class=\"trlist__trlist-row trslot \"]"));
    for (WebElement row: rows) {
     // List<WebElement> cells = row.findElements(By.tagName("td"));
      String number = //cells.get(3)
              row.findElement(By.xpath("//*[@class=\"trlist__cell-pointdata__tr-num train-num-0\"]")).getText();
      // наименование поезда
      String name = //cells.get(3).
              row.findElement(By.xpath("//*[@class=\"trlist__cell-pointdata__tr-brand\"]")).getText();
      // станция отправления
      String stationFrom = //cells.get(4).
              row.findElement(By.xpath("(//*[@class=\"trlist__cell-pointdata__station-name\"])[1]")).getText();
      // дата отправления
      String dateFrom = //cells.get(4).
              row.findElement(By.xpath("(//*[@class=\"trlist__cell-pointdata__date-sub\"])[1]")).getText();
      // время отправления
      String timeFrom = //cells.get(4).
              row.findElement(By.xpath("(//*[@class=\"trlist__cell-pointdata__time\"])[1]")).getText();
      // станция назначения
      String stationTo = //cells.get(8).
              row.findElement(By.xpath("(//*[@class=\"trlist__cell-pointdata__station-name\"])[2]")).getText();
      // дата прибытия
      String dateTo = //cells.get(8).
              row.findElement(By.xpath("(//*[@class=\"trlist__cell-pointdata__date-sub\"])[2]")).getText();
      // время прибытия
      String timeTo = //cells.get(8).
              row.findElement(By.xpath("(//*[@class=\"trlist__cell-pointdata__time\"])[2]")).getText();
      // тип вагона (сидячий, купе, св, плацкарт...)
      String typePlace = row.findElement(By.xpath("//*[@class=\"trlist__table-price\"]/tbody/tr/td[1]")).getText();
      // Кол-во мест нужного типа
      String countPlace = row.findElement(By.xpath("//*[@class=\"trlist__table-price\"]//*[@class=\"trlist__table-price__freeseats\"]")).getText();
      // Цена билета
      String price = row.findElement(By.xpath("//*[@class=\"trlist__table-price\"]//*[@class=\"trlist__table-price__price\"]")).getText();

    //  int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));

      //trains.add(new Train().//withId(id).withFirstname(firstname).withLastname(lastname)
      //.withAllphones(allphones).withAddress(address).withAllmails(allemails)
      //);
    }
    return trains;
  }

  public void chooseType(String typePl) throws InterruptedException {
    Thread.sleep(10000);
    click(By.xpath("//*[@class=\"dottedLink\" and @name=\"nothing\"]"));
    if (typePl.equals("Купе")) {
      click(By.xpath("//*[@type=\"checkbox\" and @name=\"car-type4\"]"));
    }
    click(By.xpath("//*[@id='Submit']/span[2]"));
  }
}
