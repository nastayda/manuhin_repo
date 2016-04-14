package ru.stqa.pft.rzd.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import ru.stqa.pft.rzd.model.Trains;

import java.text.ParseException;

public class RzdTests extends TestBase{

    @Test
    public void RzdTests() throws InterruptedException, ParseException {
      app.ticket().chooseDirection();
      app.ticket().chooseDate(By.xpath("//div[@class='bg']/div/div[3]/div/ul/li[21]/span"));

      //app.ticket().chooseType("Купе");
      app.ticket().chooseType("Сидячий");
      Trains trains = app.ticket().all();

      app.ticket().chooseTrain(trains, dateFrom, dateTo);

      app.ticket().chosenTrain(By.xpath("//table[@class='trlist']/tbody/tr[6]/td[1]/input[2]"));
    }
}
