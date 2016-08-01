package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by manuhin on 21.07.2016.
 */
public class ProcessHelperGGE extends HelperBase {
  public ProcessHelperGGE(WebDriver wd) {
    super(wd);
  }

  public Boolean razdel(Boolean isProdServer, String xpathAD) throws InterruptedException {
    wd.manage().window().maximize();
    clickDifficalt(isProdServer, xpathAD);

    String currentUrl = wd.getCurrentUrl();
    boolean isAD = currentUrl.contains("OFFICEWORK");

    return isAD;
  }

  public Boolean selectTypeDoc(boolean isProdServer) throws InterruptedException {
    // Клик по кнопке "Мои действия"
    String xpath = "//div[contains(@class,\"singleButton\")][contains(text(),\"Мои действия\")]";
    clickDifficalt(isProdServer, xpath);

    // Перечень окон до открытия нового
    Set<String> winOld = wd.getWindowHandles();

    // Клик по кнопке "Создать документ"
    xpath = "//ul[@class=\"notUgd HEADER showSubMenu showme\"]/li/a";
    clickDifficalt(isProdServer, xpath);

    String newWindow = switchToNewWindow(winOld);

    // Проверка, что мы в том окне, куда и надо
    if (!isUrlContainsText("CREST_CORR_LIST")) {
      return false;
    }

    // Выбор "Внутренние документы"
    String xpathTypesDoc = "//span[contains(text(),\"Внутренние документы\")]";
    waitElement(By.xpath(xpathTypesDoc));
    click(By.xpath(xpathTypesDoc));

    // Выбор "Служебная записка ЦА"
    String xpathSlugZapRadioButton = "//label[@for=\"item_SLZP_2\"]/input";
    waitElement(By.xpath(xpathSlugZapRadioButton));
    Thread.sleep(500);
    click(By.xpath(xpathSlugZapRadioButton));

    // Перечень окон до открытия нового
    Set<String> winOld2 = wd.getWindowHandles();

    // Нажать "Выбрать" (submit)
    String xpathSubmitButton = "//input[@type=\"button\"][@value=\"Выбрать\"]";
    waitElement(By.xpath(xpathSubmitButton));
    click(By.xpath(xpathSubmitButton));

    String newWindow2 = switchToNewWindow(winOld2);

    // Проверка, что мы в том окне, куда и надо
    if (!isUrlContainsText("formId=ADM_INNER")) {
      return false;
    }

    wd.manage().window().maximize();
    waitLoadPage(isProdServer);

    return true;
  }


  public Boolean fillForm(boolean isProdServer) throws InterruptedException {
    fillTab1(isProdServer);
    fillTab2(isProdServer);

    // Нажать "Сохранить" (submit)
    String xpathSubmitButton = "//input[@type=\"submit\"][@value=\"Сохранить\"]";
    waitElement(By.xpath(xpathSubmitButton));
    click(By.xpath(xpathSubmitButton));

    return true;
  }

  private Boolean fillTab1(boolean isProdServer) throws InterruptedException {
    // Проверяем что активна вкладка 01 ("Документ")
//    "//li[@class=\"autoFormTabItem active\"]//a[contains(@href,\"TAB_GROUP_01\")]"

    Boolean isFillTab = false;
    if (!waitLoadForm(isProdServer, "//*[@class=\"header\"]")) {
      return isFillTab;
    }

    fillAllFilters(isProdServer, ".//div[@id=\"tab_TAB_GROUP_01\"]");

    isFillTab = true;

    return isFillTab;
  }

  private void fillTab2(boolean isProdServer) {

  }

  private Boolean waitLoadForm(Boolean isProdServer, String locator) throws InterruptedException {
    Boolean isWebElements = false;
    List<WebElement> elements = new ArrayList<WebElement>();

    // Проверка на загрузку вкладок
    for (int i = 1; i < 20; i++) {
      waitLoadPage(isProdServer);
      Thread.sleep(500);

      elements = wd.findElements(By.xpath("//li[contains(@class,\"autoFormTabItem\")]//a[contains(@href,\"TAB_GROUP\")]"));
      if (elements.size() > 0) {
        // Проверка элементов внутри вкладки
        isWebElements = isWebElements(isProdServer, By.xpath(locator));
        if (isWebElements) {
          break;
        }
      }
    }
    return isWebElements;
  }

  public void fillAllFilters(boolean isProdServer, String xpathAllElements) throws InterruptedException {
    waitLoadPage(isProdServer);
    Thread.sleep(500);
    List<WebElement> elements = wd.findElements(By.xpath(xpathAllElements));
    if (elements.size() == 1) {
      WebElement element = elements.iterator().next();
      fillFiltrCombobox(element);
//      fillFiltrCheckbox(element);
      fillFiltrTypeEdit(element, "Test_001");
      fillFiltrTypeTextArea(element, "Test_001");
      fillFiltrMultiSelect(element);
//      fillFiltrReference(element, 16, isProdServer);
//      fillFiltrReference(element, 15, isProdServer);
    }
  }

  private void fillFiltrCombobox(WebElement element) throws InterruptedException {
    String xpathlocator = ".//span[contains(@class,\"autoFormCOMBOBOX\")][not(contains(@class,\"hide\"))]" +
            "//select[count(ancestor::div[@panel_id][contains(@class,\"hide\")])=0][not(@disabled=\"disabled\")]";

    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
          // Если элемент видим, незадезейблен, без значения - тогда выставить последнее
          if (!isVisibleEnabledWithoutValue(w)) {
            continue;
          }
          List<WebElement> elementsValue = w.findElements(By.xpath("./option[last()]"));
          if (elementsValue.size() == 1) {
            WebElement ww = elementsValue.iterator().next();
            String id = w.getAttribute("id");
            String jstriptString =
                    "$('select#" + id + "').find('option:last').attr('selected', 'selected').end().change()";
            ((JavascriptExecutor) wd).executeScript(jstriptString);
          }
        }
      }
    }
  }

  private boolean isVisibleEnabledWithoutValue(WebElement element) {
//    if (!element.isDisplayed()) {
//      return false;
//    }

    if (!element.isEnabled()) {
      return false;
    }

    String value = element.getAttribute("value");
    if (value.equals("")) {
      return false;
    }

    return true;
  }

  private void fillFiltrCheckbox(WebElement element) throws InterruptedException {
    String xpathlocator = ".//input[@type=\"checkbox\" and @class!=\"masked\"]";

    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
          if (w.isEnabled()) {
            String id = w.getAttribute("id");
            String jstriptString = "$('input#" + id + "').click()";
            ((JavascriptExecutor) wd).executeScript(jstriptString);
          }
        }
      }
    }
  }

  private void fillFiltrType(WebElement element, int attr, String text) throws InterruptedException {
    String xpathlocator = ".//*[@type=\"text\" and @attr_type=\"" + attr + "\"]";
    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
          if (w.isDisplayed()) {
            type(w, text);
          }
        }
      }
    }
  }

  private void fillFiltrTypeEdit(WebElement element, String text) throws InterruptedException {
    String xpathlocator = ".//input[count(ancestor::div[@panel_id][contains(@class,\"hide\")])=0]" +
            "[not(@disabled=\"disabled\")][@typeview=\"EDIT\"]";
    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
          if (w.isDisplayed()) {
            type(w, text);
          }
        }
      }
    }
  }

  private void fillFiltrTypeTextArea(WebElement element, String text) throws InterruptedException {
    String xpathlocator = ".//textarea[count(ancestor::div[@panel_id][contains(@class,\"hide\")])=0]" +
            "[not(@disabled=\"disabled\")][@typeview=\"TEXTAREA\"]";
    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
          if (w.isDisplayed()) {
            type(w, text);
          }
        }
      }
    }
  }

  private void fillFiltrMultiSelect(WebElement element) throws InterruptedException {
    String xpathlocator = ".//ul[@class=\"token-input-list\"]/li[@class=\"token-input-input-token\"]" +
            "/input[count(ancestor::div[@panel_id][contains(@class,\"hide\")])=0][not(@disabled=\"disabled\")]";

    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      int ii = -1;
      if (elements.size() >= 1) {
        for (WebElement w : elements) {

          // Если элемент видим, незадезейблен, без значения - тогда выставить последнее
          String xPathValue = "../../li[@class=\"token-input-token\"]/p";
          List<WebElement> valElements = w.findElements(By.xpath(xPathValue));
          if (valElements.size() > 0) {
            continue;
          }
          ii++;
          multiSelect(ii);
//          List<WebElement> elementsValue = w.findElements(By.xpath("./option[last()]"));
//          if (elementsValue.size() == 1) {
//            WebElement ww = elementsValue.iterator().next();
//            String id = w.getAttribute("id");
//            String jstriptString =
//                    "$('select#" + id + "').find('option:last').attr('selected', 'selected').end().change()";
//            ((JavascriptExecutor) wd).executeScript(jstriptString);
//          }
        }
      }
    }
  }

  private void multiSelect(int i) throws InterruptedException {
    String jstriptString = "$('.autoFormMULTISELECT, .autoFormMULTISELECT_BTN')." +
            "find('.token-input-list:visible .token-input-input-token:only-child').closest('.token-input-list').eq("
            + i + ").click();";
    ((JavascriptExecutor) wd).executeScript(jstriptString);

    Thread.sleep(2000);

    jstriptString = "$('.token-input-dropdown li').eq(1).mousedown();";
    ((JavascriptExecutor) wd).executeScript(jstriptString);
  }

}
