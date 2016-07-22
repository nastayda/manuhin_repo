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
      fillFiltrType(element, 0, "ав");
      fillFiltrType(element, 49, "ав");
      fillFiltrType(element, 47, "ав");
      fillFiltrType(element, 6, "01.04.2015");
      fillFiltrType(element, 9, "01.04.2015");
//      fillFiltrReference(element, 16, isProdServer);
//      fillFiltrReference(element, 15, isProdServer);
    }
  }

  private void fillFiltrCombobox(WebElement element) throws InterruptedException {
    String xpathlocator = ".//div[contains(@class,\"autoFormGROUP_VERTICALLY\")]" +
            "[not(contains(@class,\"hide\"))]//span[contains(@class,\" autoFormCOMBOBOX\")]" +
            "[not(contains(@class,\"hide\"))]//div[contains(@class,\"customSelect\")]" +
            "[not(contains(@class,\"disabled\"))]//select";

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


}
