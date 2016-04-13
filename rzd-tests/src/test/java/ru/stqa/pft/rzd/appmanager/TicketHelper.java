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

  public Trains all(String typePl) {
    Trains trains = new Trains();
    List<WebElement> rows = wd.findElements(By.xpath("//*[@class=\"trlist__trlist-row trslot \"]"));
    for (WebElement row: rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      // номер поезда
      if (cells.get(8)
              .findElement(By.xpath("//*[@class=\"trlist__cell-pointdata__time\"]")).getText().equals(typePl)) {

      }
      String number = cells.get(3)
              .findElement(By.xpath("//*[@class=\"trlist__cell-pointdata__tr-num train-num-0\"]")).getText();
      // наименование поезда
      // trlist__cell-pointdata__tr-brand
      String name = cells.get(3)
              .findElement(By.xpath("//*[@class=\"trlist__cell-pointdata__tr-brand\"]")).getText();
      // станция отправления
      // trlist__cell-pointdata__station-name
      String stationFrom = cells.get(4)
              .findElement(By.xpath("//*[@class=\"trlist__cell-pointdata__station-name\"]")).getText();
      // дата отправления
      // trlist__cell-pointdata__date-sub
      String dateFrom = cells.get(4)
              .findElement(By.xpath("//*[@class=\"trlist__cell-pointdata__date-sub\"]")).getText();
      // время отправления
      // trlist__cell-pointdata__time
      String timeFrom = cells.get(4)
              .findElement(By.xpath("//*[@class=\"trlist__cell-pointdata__time\"]")).getText();
      // станция назначения
      // trlist__cell-pointdata__station-name
      String stationTo = cells.get(7)
              .findElement(By.xpath("//*[@class=\"trlist__cell-pointdata__station-name\"]")).getText();
      // дата прибытия
      String dateTo = cells.get(7)
              .findElement(By.xpath("//*[@class=\"trlist__cell-pointdata__date-sub\"]")).getText();
      // время прибытия
      String timeTo = cells.get(7)
              .findElement(By.xpath("//*[@class=\"trlist__cell-pointdata__time\"]")).getText();
      // тип вагона (сидячий, купе, св, плацкарт...)
      // 8
      // Купе
      //
      String typePlace = row.findElement(By.xpath("")).getText();
      // Кол-во мест нужного типа
      String countPlace = row.findElement(By.xpath("")).getText();
      // Цена билета
      String price = row.findElement(By.xpath("")).getText();




      int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));

      //trains.add(new Train().//withId(id).withFirstname(firstname).withLastname(lastname)
              //.withAllphones(allphones).withAddress(address).withAllmails(allemails)
              //);
    }
    return trains;
  }

}
