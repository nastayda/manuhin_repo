package ru.stqa.pft.gge.appmanager;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.openqa.selenium.*;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.stqa.pft.gge.model.GeneratorData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by manuhin on 21.04.2016.
 */
public class CardHelper extends HelperBase {
  public CardHelper(WebDriver wd) {
    super(wd);
  }


  public Boolean openCards(List<String> hrefs, Boolean isProdServer) throws InterruptedException {
    Boolean isOpenWithoutMistakes = false;
    int iMax = 5;
    int i = 0;
    for (String s : hrefs) {
      isOpenWithoutMistakes = openCard(s, isProdServer);
      i++;
      if (i >= iMax || !isOpenWithoutMistakes) {
        break;
      }
    }

    return  isOpenWithoutMistakes;
  }

  private Boolean openCard(String s, Boolean isProdServer) throws InterruptedException {
    Boolean isOpenWithoutMistakes = false;

    wd.get(s);
    waitLoadPageCard(isProdServer);
    Thread.sleep(500);

    isOpenWithoutMistakes = checkCardMistakes(isProdServer);

    List<WebElement> elements = new ArrayList<WebElement>();

    for (int i = 1; i < 20; i++) {
      waitLoadPageCard(isProdServer);
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

    waitLoadPageCard(isProdServer);
    Thread.sleep(500);

    List<WebElement> elements =
            wd.findElements(By.xpath("//body//*[contains(text(),\"Faled\") or contains(text(),\"xception\")]"));

    if (elements.size() == 0) {
      isOpenWithoutMistakes = true;
    }

    return isOpenWithoutMistakes;
  }

  private void openCardVkladka(WebElement element, Boolean isProdServer, String textVkladki) throws InterruptedException {
    element.click();
    waitLoadPageCard(isProdServer);
    Thread.sleep(500);
  }

  public void waitLoadPageCard(boolean isProdServer) throws InterruptedException {
    waitElement(By.xpath("//div[@id=\"cboxOverlay\" and contains(@style,\"display: none\")]"));
  }
}
