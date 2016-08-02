package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.*;
import ru.stqa.pft.gge.model.ProcessData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

  public Boolean razdelUrl(Boolean isProdServer, String urlAD) throws InterruptedException {
    wd.manage().window().maximize();
    wd.get(urlAD);

    waitLoadPage(isProdServer);
    Thread.sleep(1000);

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
    fillTab(isProdServer, 1);
    clickDifficalt(isProdServer, "//a[@href=\"#tab_TAB_GROUP_02\"]");
    fillTab(isProdServer, 2);

    // Нажать "Сохранить" (submit)
    String xpathSubmitButton = "//input[@type=\"submit\"][@value=\"Сохранить\"]";
    waitElement(By.xpath(xpathSubmitButton));

    // Перечень окон до открытия нового
    Set<String> winOld = wd.getWindowHandles();

    // Нажать "Сохранить" (save)
    waitElement(By.xpath(xpathSubmitButton));
    click(By.xpath(xpathSubmitButton));


    Set<String> wNewSet = wd.getWindowHandles();
    int attempt = 0;
    while (wNewSet.size() > 1 && attempt < 15) {
      Thread.sleep(1000);
      wNewSet = wd.getWindowHandles();
      attempt++;
    }

//    wNewSet.removeAll(winOld);
    String wNew = wNewSet.iterator().next();
    wd.switchTo().window(wNew);


    return true;
  }

  private Boolean fillTab(boolean isProdServer, int numTab) throws InterruptedException {
    Boolean isFillTab = false;
    if (!waitLoadForm(isProdServer, "//*[@class=\"header\"]")) {
      return isFillTab;
    }

    fillAllFilters(isProdServer, ".//div[@id=\"tab_TAB_GROUP_0" + numTab + "\"]", numTab);
    isFillTab = true;
    return isFillTab;
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

  public void fillAllFilters(boolean isProdServer, String xpathAllElements, int numTab) throws InterruptedException {
    waitLoadPage(isProdServer);
    Thread.sleep(500);
    List<WebElement> elements = wd.findElements(By.xpath(xpathAllElements));
    if (elements.size() == 1) {
      WebElement element = elements.iterator().next();
      if (numTab == 1) {
        fillFiltrCombobox(element);
        fillFiltrTypeEdit(element, "Test_001");
        fillFiltrTypeTextArea(element, "Test_001");
        fillFiltrMultiSelect(element);
        fillFiltrTypeButtonGen(element, isProdServer);
      } else {
        fillFiltrTypeDate(element, "Test_001");
      }

//      fillFiltrReference(element, 16, isProdServer);
//      fillFiltrReference(element, 15, isProdServer);
//      fillFiltrCheckbox(element);
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

  private void fillFiltrTypeDate(WebElement element, String text) throws InterruptedException {
    String xpathlocator = ".//input[count(ancestor::span[contains(@class,\"autoFormDATE\")]" +
            "[contains(@class,\"hide\")])=0]" +
            "[count(ancestor::div[@panel_id][contains(@class,\"hide\")])=0]" +
            "[not(@disabled=\"disabled\")][@typeview=\"DATE\"]";
    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date currentDate = new Date();
        Long time = currentDate.getTime();
        long anotherDate = 7;
        time = time + (60*60*24*1000*anotherDate);
        currentDate = new Date(time);
        String dateString = sdf.format(currentDate);
        System.out.println("currentDate = " + dateString);

        for (WebElement w : elements) {
          if (w.isDisplayed()) {
            waitForDisplayed(w);
            type(w, dateString);
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

  private void fillFiltrTypeButtonGen(WebElement element, boolean isProdServer) throws InterruptedException {
    String xpathlocator = ".//button[@class=\"numberGeneratorButton\"]";
    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
          if (w.isDisplayed()) {
            String xPathValue = "../input[@disabled=\"disabled\"]";
            List<WebElement> elementsDisabled = w.findElements(By.xpath(xPathValue));
            // Если элемент ввода рег.номера задизейблен, то жмем на кнопку рег. номера
            if (elementsDisabled.size() > 0) {
              clickWithWaiting(w, isProdServer);
            }
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
      if (elements.size() >= 1) {
        for (WebElement w : elements) {

          // Если элемент видим, незадезейблен, без значения - тогда выставить последнее
          String xPathValue = "../../li[@class=\"token-input-token\"]/p";
          List<WebElement> valElements = w.findElements(By.xpath(xPathValue));
          if (valElements.size() > 0) {
            continue;
          }
          multiSelect();
        }
      }
    }
  }

  private void multiSelect() throws InterruptedException {
    String jstriptString;
    int maxI = 10;

    for (int i = 0; i < maxI; i++) {
      jstriptString = "$('.autoFormMULTISELECT').find('.token-input-list:visible .token-input-input-token:only-child').closest('.token-input-list').eq(" + i + ").click();";
      ((JavascriptExecutor) wd).executeScript(jstriptString);
      Thread.sleep(200);
      jstriptString = "$('.token-input-dropdown li').eq(1).mousedown();";
      ((JavascriptExecutor) wd).executeScript(jstriptString);
    }

    for (int i = 0; i < maxI; i++) {
      jstriptString = "$('.autoFormMULTISELECT_BTN').find('.token-input-list:visible .token-input-input-token:only-child').closest('.token-input-list').eq(" + i + ").click();";
      ((JavascriptExecutor) wd).executeScript(jstriptString);
      Thread.sleep(200);
      jstriptString = "$('.token-input-dropdown li').eq(1).mousedown();";
      ((JavascriptExecutor) wd).executeScript(jstriptString);
    }
  }

  public Boolean openCardWithProcess(boolean isProdServer, ProcessData process) throws InterruptedException {
    String xpathTypeDocument = "//span[contains(text(),'Служебные записки ЦА')]";
    waitLoadPage(isProdServer);
    Thread.sleep(200);
    waitElement(By.xpath(xpathTypeDocument));

    String xpathSecondTab = "(//ul[@id=\"tabs_group\"]//a)[2]";
    waitLoadPage(isProdServer);
    List<WebElement> elements = wd.findElements(By.xpath(xpathSecondTab));
    if (elements.size() > 0) {
      elements.iterator().next().click();
      String xpathCardProcess = "//a[contains(@href,'tabInfo.action?tab=PROCESS_CARD&tab2=PROCESS_CARD')]";
      waitLoadPage(isProdServer);
      Thread.sleep(200);
      waitElement(By.xpath(xpathCardProcess));

      List<WebElement> elementsProcess = wd.findElements(By.xpath(xpathCardProcess));
      WebElement next = elementsProcess.iterator().next();
      String href = next.getAttribute("href");

      process.withUrlCardProcess(href);
    }
    return true;
  }
}
