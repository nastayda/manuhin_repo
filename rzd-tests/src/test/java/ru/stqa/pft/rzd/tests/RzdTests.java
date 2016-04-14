package ru.stqa.pft.rzd.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import ru.stqa.pft.rzd.model.Train;
import ru.stqa.pft.rzd.model.Trains;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RzdTests extends TestBase{

    @Test
    public void RzdTests() throws InterruptedException, ParseException {
      Integer countPlaceMin = 10;

      String type1 = "Сидячий";
      Date from1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("12.06.2016 17:00:00");
      Date to1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("12.06.2016 23:59:59");

      String type2 = "Купе";
      Date from2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("12.06.2016 17:00:00");
      Date to2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("13.06.2016 05:59:59");


      app.ticket().chooseDirection();
      app.ticket().chooseDate(By.xpath("//div[@class='bg']/div/div[3]/div/ul/li[21]/span"));

      app.ticket().chooseType(type1);
      Trains trains1 = app.ticket().all();
      Train train1 = app.ticket().chooseTrain(trains1, type1, countPlaceMin, from1, to1, "до");

      app.ticket().chooseType(type2);
      Trains trains2 = app.ticket().all();
      Train train2 = app.ticket().chooseTrain(trains2, type2, countPlaceMin, from2, to2, "после");

      Train train = new Train();
      if (train1.getPrice() < train2.getPrice()) {
        train = train1;
      } else {
        train = train2;
      }

      app.ticket().chosenTrain(By.xpath("//table[@class='trlist']/tbody/tr[6]/td[1]/input[2]"));
    }
}
