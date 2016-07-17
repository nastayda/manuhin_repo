package ru.stqa.pft.gge.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

public class CardsTestsGGE extends TestBase {

  String fileNameFromJson = "src/test/resources/vitrinas_andropov_all_test-eis.json";
  String fileNameForFailCards = "src/test/resources/cards_andropov_all_test-eis_bad.json";

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(
            new File(fileNameFromJson)))) {
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
  public void testCardsTestsGGE(GeneratorData vitrina) throws Exception {

    boolean isProdServer = false;
    boolean isContainsUrlProdServer =
            vitrina.getBaseUrl().contains("https://eis.gge.ru/");
    if (isContainsUrlProdServer) {
      isProdServer = true;
    }

    String password = "21";

    String adminLogin = "galactica_admin1";
    String adminPassword = "21";

    if (isProdServer) {
      password = "Ukfdujc21";
      adminPassword = "Ukfdujc21";
    }

    assertThat(app.successInit, equalTo(true));
    app.session().loginGGEMGE(vitrina.getLoginUser(), password, vitrina.getBaseUrl());

    assertThat(app.vitrina().selectVitrina(vitrina, isProdServer), equalTo(true));
    assertThat(app.vitrina().isMistakes(isProdServer), equalTo(false));

    if (app.vitrina().isWithCatagorize(isProdServer)) {
      app.vitrina().clickCategorizes(isProdServer);
    }
    List<String> hrefs = app.vitrina().allLink(isProdServer);

    if (hrefs.size() > 0) {
      assertThat(app.card().openCards(hrefs, isProdServer, vitrina, fileNameForFailCards, adminLogin, adminPassword),
              equalTo(true));
    }
  }
}
