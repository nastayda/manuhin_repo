package ru.stqa.pft.gge.tests;

import org.testng.annotations.Test;


public class VitrinaOpenAndFindTests extends TestBase {

  @Test
  public void testVitrinaOpenAndFind() throws Exception {

//    app.getDriver();
    app.vitrina().selectVitrina();
    app.vitrina().vizovRasshPoisk();
    app.vitrina().fillAllFilters();
    app.vitrina().buttonFind();

  }

}
