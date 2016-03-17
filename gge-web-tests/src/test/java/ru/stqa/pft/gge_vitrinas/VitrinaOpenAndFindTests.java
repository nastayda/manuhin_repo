package test.java.ru.stqa.pft.gge_vitrinas;

import org.testng.annotations.Test;

public class VitrinaOpenAndFindTests extends TestBase {

  @Test
  public void testVitrinaOpenAndFind() throws Exception {

    selectVitrina();
    vizovRasshPoisk();
    fillAllFilters();
    buttonFind();

  }

}
