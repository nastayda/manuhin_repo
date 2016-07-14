package ru.stqa.pft.gge.appmanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.gge.model.GeneratorData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manuhin on 21.04.2016.
 */
public class CardHelper extends HelperBase {

  String file;

  public CardHelper(WebDriver wd) {
    super(wd);
  }

  public Boolean openCards(List<String> hrefs, Boolean isProdServer,
                           GeneratorData vitrina, String fileNameForFailCards)
          throws InterruptedException, IOException {
    Boolean isOpenWithoutMistakes = false;
    int iMax = 5;
    int i = 0;
    for (String s : hrefs) {
      isOpenWithoutMistakes = openCard(s, isProdServer, vitrina, fileNameForFailCards);
      i++;
      if (i >= iMax || !isOpenWithoutMistakes) {
        break;
      }
    }

    return  isOpenWithoutMistakes;
  }

  private Boolean openVkladka(Boolean isProdServer, GeneratorData vitrina,
                              String fileNameForFailCards) throws InterruptedException, IOException {
    Boolean isOpenWithoutMistakes = false;
    String s = wd.getCurrentUrl();
//    vitrina.withCardUrl(s);

    if (!isWaitedCboxOverlay(isProdServer)) {
      failCardToJson(vitrina, fileNameForFailCards);
      return isOpenWithoutMistakes;
    };
    waitLoadPage(isProdServer);
    Thread.sleep(500);

    isOpenWithoutMistakes = checkCardMistakes(isProdServer);

    if (!isOpenWithoutMistakes) {
      failCardToJson(vitrina, fileNameForFailCards);
      return isOpenWithoutMistakes;
    }

    return isOpenWithoutMistakes;
  }

  private Boolean openCard(String s, Boolean isProdServer, GeneratorData vitrina,
                           String fileNameForFailCards)
          throws InterruptedException, IOException {
    Boolean isOpenWithoutMistakes = false;

    //    s = s + "2";
    vitrina.withCardUrl(s);
    wd.get(s);
    String tab2 = ""; //getTab2(s);

    isOpenWithoutMistakes = openVkladka(isProdServer, vitrina, fileNameForFailCards);
    List<WebElement> elements = getWebElements(isProdServer, tab2);

    // При нажатии на следующую вкладку грузится другая страница

    int iMax = elements.size();
    for (int ii = 1; ii < iMax; ii++) {
      WebElement element = wd.findElement(By.xpath("//body"));
      int iii = 0;
      for (WebElement element1 : elements) {
        element = element1;
        if (iii == ii) {
          break;
        }
        iii++;
      }
      waitForDisplayed(element);

      clickTabCardVkladka(element, isProdServer);
      s = wd.getCurrentUrl();
      tab2 = getTab2(s);
      System.out.println("Открыта вкладка (" + tab2 + ") карточки по ссылке : " + s + "\n");

      isOpenWithoutMistakes = openVkladka(isProdServer, vitrina, fileNameForFailCards);
      if (!isOpenWithoutMistakes) {
        break;
      }
      elements = getWebElements(isProdServer, tab2);
    }

    return  isOpenWithoutMistakes;
  }

  private String getTab2(String s) {
    int tabIndexStart = s.indexOf("tab2=");
    String tab2Start = s.substring(tabIndexStart+5);
    int tabIndexEnd1 = tab2Start.indexOf("&");
    if(tabIndexEnd1 < 0) {
      tabIndexEnd1 = 1000;
    }

    int tabIndexEnd2 = tab2Start.indexOf("#");
    if(tabIndexEnd2 < 0) {
      tabIndexEnd2 = 1000;
    }

    int tabIndexEnd = Math.min(tabIndexEnd1, tabIndexEnd2);
    if (tabIndexEnd < 0) {
       tab2Start = tab2Start;
    }
    String tab2 = tab2Start.substring(0, tabIndexEnd);

    return tab2;
  }

  private List<WebElement> getWebElements(Boolean isProdServer,
                                          String tab2) throws InterruptedException {
    List<WebElement> elements = new ArrayList<WebElement>();

    // Проверка на загрузку вкладок
    for (int i = 1; i < 20; i++) {
      waitLoadPage(isProdServer);
      Thread.sleep(500);

      elements = wd.findElements(By.xpath("//ul[@id=\"tabs_group\"]//a"));
      if (elements.size() > 0) {

        String locator = "";

        if (tab2.equals("")) {
          locator = "//p[@class=\"sectionTitle\"]";
        } else if (tab2.equals("TEP")) {
          locator = "//table[@class=\"data_create\"]//td//span[@class=\"color_light_gray\"]";
        } else if (tab2.equals("PD_FILES")) {
          locator = "//div[contains(@id,\"dirTree\")]";
        } else if (tab2.equals("OPIS")) {
          locator = "//*[@id='vitrinaTableHeader']//th";
        } else if (tab2.equals("CARD")) {
          locator = "//h2[@class=\"expert-title\"]";
        } else if (
                tab2.equals("STATUS_PGU") ||
                tab2.equals("PROCESS") ||
                tab2.equals("ERRAND_CARD_TREE") ||
                tab2.equals("0C741BD4A8CB43C4AB0C7B80490F2635") ||
                tab2.equals("EMPL_EMPTY")
                ) {
          locator = "//thead[@id=\"vitrinaTableHeader\"]//th";
        } else {
          locator = "//body";
        }

        // Проверка на загрузку заголовков внутри вкладки
        Boolean isWebElements = isWebElements(isProdServer, By.xpath(locator));
        if (isWebElements) {
          break;
        }
      }
    }
    return elements;
  }

  private Boolean isWebElements(Boolean isProdServer, By locator) throws InterruptedException {
    Boolean isWebElements = false;
    for (int ii = 1; ii < 20; ii++) {
      waitLoadPage(isProdServer);
      Thread.sleep(500);

      List<WebElement> elements2 = wd.findElements(locator);
      if (elements2.size() > 0) {
        isWebElements = true;
        break;
      }
    }
    return isWebElements;
  }

  private void failCardToJson(GeneratorData vitrina, String fileName) throws IOException {
    List<GeneratorData> vitrinas = vitrinasFromJson(fileName);
    vitrinas.add(vitrina);
    saveAsJson(vitrinas, new File(fileName));
  }

  public List<GeneratorData> vitrinasFromJson(String fileName) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<GeneratorData> vitrinas = gson.fromJson(json, new TypeToken<List<GeneratorData>>(){}.getType()); // List<GroupData>.class
      return vitrinas;
    } catch (Exception e) {
      Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
              .excludeFieldsWithoutExposeAnnotation().create();
      List<GeneratorData> vitrinas = new ArrayList<GeneratorData>();
      String json = gson.toJson(vitrinas);
      try (Writer writer = new FileWriter(fileName)) {
        writer.write(json);
      }
      return vitrinas;
    }
  }


  private Boolean checkCardMistakes(Boolean isProdServer) throws InterruptedException {
    Boolean isOpenWithoutMistakes = false;

    waitLoadPage(isProdServer);
    Thread.sleep(500);

    String xPathForBadCard =
            "//body//*[contains(text(),\"Faled\") or contains(text(),\"xception\") or contains(text(),\"Доступ ограничен\")]";
    List<WebElement> elements = wd.findElements(By.xpath(xPathForBadCard));

    if (elements.size() == 0) {
      isOpenWithoutMistakes = true;
    }

    return isOpenWithoutMistakes;
  }

  private void clickTabCardVkladka(WebElement element, Boolean isProdServer) throws InterruptedException {
    waitLoadPage(isProdServer);
    Thread.sleep(500);

    for (int i = 0; i < 10; i++) {
      try {
        element.click();
        break;
      } catch (Exception e) {
        waitLoadPage(isProdServer);
        Thread.sleep(500);
      }
    }
  }
}
