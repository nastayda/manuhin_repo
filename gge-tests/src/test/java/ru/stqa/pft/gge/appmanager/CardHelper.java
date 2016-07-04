package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.gge.model.GeneratorData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manuhin on 21.04.2016.
 */
public class CardHelper extends HelperBase {
  public CardHelper(WebDriver wd) {
    super(wd);
  }


  public Boolean openCards(List<String> hrefs, Boolean isProdServer, GeneratorData vitrina) throws InterruptedException {
    Boolean isOpenWithoutMistakes = false;
    int iMax = 5;
    int i = 0;
    for (String s : hrefs) {
      isOpenWithoutMistakes = openCard(s, isProdServer, vitrina);
      i++;
      if (i >= iMax || !isOpenWithoutMistakes) {
        break;
      }
    }

    return  isOpenWithoutMistakes;
  }

  private Boolean openCard(String s, Boolean isProdServer, GeneratorData vitrina) throws InterruptedException {
    Boolean isOpenWithoutMistakes = false;

    s = s + "2";
    vitrina.withBaseUrl(s);

    wd.get(s);

    if (!isWaitedCboxOverlay(isProdServer)) {
      return isOpenWithoutMistakes;
    };
    waitLoadPage(isProdServer);
    Thread.sleep(500);

    isOpenWithoutMistakes = checkCardMistakes(isProdServer);

    if (!isOpenWithoutMistakes) {
      return isOpenWithoutMistakes;
    }

    List<WebElement> elements = new ArrayList<WebElement>();

    for (int i = 1; i < 20; i++) {
      waitLoadPage(isProdServer);
      Thread.sleep(500);

      elements = wd.findElements(By.xpath("//ul[@id=\"tabs_group\"]//a"));
      if (elements.size() > 0) {
        break;
      }
    }

//    по вкладкам остаются открытыми вопросы:
//      что происходит при нажатии на следующую вкладку: DOM для вкладок меняется?
//      ссылка по вкладке такая же?

//    int i = 0;
//    for (WebElement element : elements) {
//      waitForDisplayed(element);
//      String textVkladki = element.getText();
//      if (i > 0) {
//        openCardVkladka(element, isProdServer, textVkladki);
//      }
//      System.out.println("Открыта вкладка (" + textVkladki + ") карточки по ссылке : " + s + "\n");
//      i++;
//    }

    return  isOpenWithoutMistakes;
  }

  private Boolean checkCardMistakes(Boolean isProdServer) throws InterruptedException {
    Boolean isOpenWithoutMistakes = false;

    waitLoadPage(isProdServer);
    Thread.sleep(500);

    List<WebElement> elements =
            wd.findElements(
                    By.xpath("//body//*[contains(text(),\"Faled\") or contains(text(),\"xception\")]"));

    if (elements.size() == 0) {
      isOpenWithoutMistakes = true;
    }

    return isOpenWithoutMistakes;
  }

  private void openCardVkladka(WebElement element, Boolean isProdServer, String textVkladki) throws InterruptedException {
    element.click();
    waitLoadPage(isProdServer);
    Thread.sleep(500);
  }
}
