package ru.stqa.pft.gge.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.gge.tests.TestBase;

public class VitrinaOpenAndFindTests extends TestBase {

  @Test
  public void testVitrinaOpenAndFind() throws Exception {

//    app.getDriver();
    app.selectVitrina();
    app.vizovRasshPoisk();
    app.fillAllFilters();
    app.buttonFind();

  }

}
