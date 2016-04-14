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
      Date from1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("12.06.2016 18:00:00");
      Date to1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("12.06.2016 23:15:00");

      String type2 = "Купе";
      Date from2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("12.06.2016 20:00:00");
      Date to2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("13.06.2016 05:59:59");

      app.ticket().chooseDirection();
      app.ticket().chooseDate(By.xpath("//div[@class='bg']/div/div[3]/div/ul/li[21]/span"));

      Train train1 = app.ticket().getTrain(countPlaceMin, type1, from1, to1, "до");
      Train train2 = app.ticket().getTrain(countPlaceMin, type2, from2, to2, "после");
      Train train = app.ticket().bestTrain(train1, train2);

      app.ticket().chooseTypes(type1, type2);
      app.ticket().chosenTrain(train.getId());
    }


}
