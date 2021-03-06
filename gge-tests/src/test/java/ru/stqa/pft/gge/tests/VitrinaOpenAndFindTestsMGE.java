package ru.stqa.pft.gge.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.gge.model.GeneratorData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by manuhin on 31.05.2016.
 */
public class VitrinaOpenAndFindTestsMGE extends TestBase{

  @DataProvider
  public Iterator<Object[]> validGroupsFromXml() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(
            new File("src/test/resources/vitrinas3.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(GeneratorData.class);
      List<GeneratorData> vitrinas = (List<GeneratorData>) xstream.fromXML(xml);
      return vitrinas.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(
            new File("src/test/resources/vitrinas_mge_galactica_admin1_all_expertiza_new2.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<GeneratorData> vitrinas = gson.fromJson(json, new TypeToken<List<GeneratorData>>(){}.getType()); // List<GroupData>.class
      return vitrinas.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "validGroupsFromJson", timeOut = 250000)
  public void testVitrinaOpenAndFindMGE(GeneratorData vitrina) throws Exception {

    boolean isProdServer = false;
    String password = "Htytufn6*6";
    if (isProdServer) {
      password = "Htytufn6*6";
    }
    //BEG Переменные для теста. Для предбоя:
    String domen = "https://expertiza-tech.mos.ru";
    String userlogout = "VASILENKORN";
//    assertThat(app.successInit, equalTo(true));
//    app.session().loginGGEMGE(vitrina.getLoginUser(), password, vitrina.getBaseUrl());
//
//    assertThat(app.vitrina().selectVitrina(vitrina, isProdServer), equalTo(true));
//    if (app.vitrina().checkVitrinaIs(vitrina, isProdServer)) {
//      assertThat(app.vitrina().isMistakes(isProdServer), equalTo(false));
//      app.vitrina().vizovRasshPoiskMGE(isProdServer);
//      app.vitrina().fillAllFilters(isProdServer);
//      app.vitrina().buttonFind(isProdServer);
//      assertThat(app.vitrina().isMistakes(isProdServer), equalTo(false));
//    } else {
//      assertThat(app.vitrina().isMistakesOtchet(isProdServer), equalTo(false));
//    }

    assertThat(app.successInit, equalTo(true));
    app.session().loginGGEMGE(vitrina.getLoginUser(), password, vitrina.getBaseUrl());

    assertThat(app.vitrina().selectVitrinaMGE(vitrina, isProdServer), equalTo(true));
    assertThat(app.vitrina().isMistakesMGE(isProdServer), equalTo(false));

    app.vitrina().vizovRasshPoiskMGE(isProdServer); //вызов формы поиска.
    app.vitrina().fillAllFilters(isProdServer); // заполнение полей формы поиска.
    app.vitrina().buttonFind(isProdServer); // клик на кнопку поиска.
    assertThat(app.vitrina().isMistakesMGE(isProdServer), equalTo(false));
  }
}
