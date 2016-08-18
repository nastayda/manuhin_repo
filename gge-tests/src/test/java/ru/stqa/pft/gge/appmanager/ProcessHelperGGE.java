package ru.stqa.pft.gge.appmanager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.*;
import ru.stqa.pft.gge.model.DbConnect;
import ru.stqa.pft.gge.model.ProcessTestCases;
import ru.stqa.pft.gge.model.TaskProcessData;
import ru.stqa.pft.gge.model.UpLoadFileData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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
    wd.get(urlAD);

    waitLoadPage(isProdServer);
    Thread.sleep(1000);

    String currentUrl = wd.getCurrentUrl();
    boolean isAD = currentUrl.contains("OFFICEWORK");

    Thread.sleep(200);
    wd.manage().window().maximize();
    return isAD;
  }

  public Boolean selectTypeDoc(boolean isProdServer,
                               String typeGroupDoc,
                               String typeDoc,
                               String checkTypeDoc, String checkTypeForm) throws InterruptedException {

    // Клик по кнопке "Мои действия"
    String xpath = "//div[contains(@class,'singleButton')][contains(text(),'Мои действия')]";
    clickDifficalt(isProdServer, xpath);

    // Перечень окон до открытия нового
    Set<String> winOld = wd.getWindowHandles();

    // Клик по кнопке "Создать документ"
    xpath = "//ul[@class='notUgd HEADER showSubMenu showme']/li/a";
    clickDifficalt(isProdServer, xpath);

    String newWindow = switchToNewWindow(winOld);

    // Проверка, что мы в том окне, куда и надо
    if (!isUrlContainsText(checkTypeDoc)) {
      return false;
    }

    // Выбор типа группы документов (например, "Внутренние документы")
    String xpathTypesDoc = typeGroupDoc;
    waitElement(By.xpath(xpathTypesDoc));
    click(By.xpath(xpathTypesDoc));

    // Выбор конкретного типа документа (например, "Служебная записка ЦА")
    String xpathSlugZapRadioButton = typeDoc;
    waitElement(By.xpath(xpathSlugZapRadioButton));
    Thread.sleep(500);
    click(By.xpath(xpathSlugZapRadioButton));

    // Перечень окон до открытия нового
    Set<String> winOld2 = wd.getWindowHandles();

    // Нажать "Выбрать" (submit)
    String xpathSubmitButton = "//input[@type='button'][@value='Выбрать']";
    waitElement(By.xpath(xpathSubmitButton));
    click(By.xpath(xpathSubmitButton));

    String newWindow2 = switchToNewWindow(winOld2);

    // Проверка, что мы в том окне, куда и надо
    if (!isUrlContainsText(checkTypeForm)) {
      return false;
    }

    wd.manage().window().maximize();
    waitLoadPage(isProdServer);

    return true;
  }

  public Set<Cookie> getCookies() {
    Set<Cookie> cookies = wd.manage().getCookies();
    return cookies;
  }


  public Boolean fillForm(boolean isProdServer, String typeDoc) throws InterruptedException {
    fillTab(isProdServer, typeDoc, 1);

    if (typeDoc.equals("SitizenLetter")) {

      return true;
    }

    clickDifficalt(isProdServer, "//a[@href='#tab_TAB_GROUP_02']");
    fillTab(isProdServer, typeDoc, 2);

    if (typeDoc.equals("SlZap")) {
      // Получить ФИО утверждающего
      String xpathFIOUtv = "//div[@class='curSelect']/span[contains(text(),'Утверждение')]/../../../../..//p";
      waitElement(By.xpath(xpathFIOUtv));
      WebElement element = wd.findElement(By.xpath(xpathFIOUtv));
      String fioUtv = element.getText();

      // Перейти на вкладку 01
      clickDifficalt(isProdServer, "//a[@href='#tab_TAB_GROUP_01']");

      // Заполнить поле "Кому" значением ФИО утверждающего
      String xpathKomu = "//div[@class='curSelect']/span[contains(text(),'Утверждение')]/../../../../..//p";
      waitElement(By.xpath(xpathKomu));
      fioUtv = fioUtv.substring(0, 20);
      multiSelectString(fioUtv);
    }

    return true;
  }

  public Boolean submitForm(boolean isProdServer) throws InterruptedException {
    // Нажать "Сохранить" (submit)
    String xpathSubmitButton = "//input[@type='submit'][@value='Сохранить']";
    waitElement(By.xpath(xpathSubmitButton));

    // Перечень окон до открытия нового
    Set<String> winOld = wd.getWindowHandles();

    // Нажать "Сохранить" (save)
    waitElement(By.xpath(xpathSubmitButton));
    clickDifficalt(isProdServer, xpathSubmitButton);

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

  public Boolean fillFormTask(boolean isProdServer,
                              TaskProcessData taskProcess,
                              int numberActiveTaskFromProcessCard) throws InterruptedException {
    String xpathTBody = "//tbody";
    String xpathActionWithTaskAllButton = "//div[contains(@class,'assignResponsibleAction')]//input";
    String xpathActionWithTask = "//div[contains(@class,'assignResponsibleAction')]" +
            "//input[contains(@value, '" + taskProcess.getActionWithTask() + "')]";


    List<WebElement> elements = wd.findElements(By.xpath(xpathTBody));
    if (elements.size() > 0) {
      WebElement element = elements.iterator().next();
      waitLoadPage(isProdServer);
      Thread.sleep(500);

      fillReportTextArea(element, "замечания от " + taskProcess.getFio());

//      fillFiltrCombobox(element);
//      fillFiltrTypeEdit(element, "Test_001");
//      fillFiltrTypeTextArea(element, "Test_001");
//      fillFiltrMultiSelect(element);
//      fillFiltrTypeButtonGen(element, isProdServer);
//      fillFiltrTypeDate(element, "Test_001");
    }

    // Нажать кнопку согласования/на доработку (submit)
    waitLoadPage(isProdServer);
    Thread.sleep(500);

    String xpathActionButton = "";
    List<WebElement> allButtons = wd.findElements(By.xpath(xpathActionWithTaskAllButton));
    if (allButtons.size() == 1) {
      xpathActionButton = xpathActionWithTaskAllButton;
    } else if (allButtons.size() > 1) {
//      Random random = new Random();
//
//      int ii = random.nextInt(allButtons.size()) + 1;

//    Рандомное нажатие на кнопки
//      xpathActionButton = "(" + xpathActionWithTaskAllButton + ")[" + ii + "]";

//    Конкретное задание кнопки - из файла fileProcessTestCase
//    Из файла fileProcessTestCase (это 1 строка из цифр) получить значение символа с номером numberActiveTask
//    Это будет номер для xpath кнопок

      String substring = taskProcess.getProcessTestCase()
              .substring(numberActiveTaskFromProcessCard, numberActiveTaskFromProcessCard + 1);
      int ii = Integer.parseInt(substring);
      xpathActionButton = "(" + xpathActionWithTaskAllButton + ")[" + ii + "]";
    } else {
      return false;
    }

    // Перечень окон до открытия нового
    Set<String> winOld = wd.getWindowHandles();

    // Нажать кнопку согласовать/на доработку/.. и т.д
    if (allButtons.size() >= 1) {
      waitElement(By.xpath(xpathActionButton));
      clickDifficalt(isProdServer, xpathActionButton);
    }

    Set<String> wNewSet = wd.getWindowHandles();
    int attempt = 0;
    while (wNewSet.size() > 1 && attempt < 15) {
      Thread.sleep(1000);
      wNewSet = wd.getWindowHandles();
      attempt++;
    }

    String wNew = wNewSet.iterator().next();
    wd.switchTo().window(wNew);

    waitLoadPage(isProdServer);
    Thread.sleep(10000);
    return true;
  }

  private Boolean fillTab(boolean isProdServer, String typeDoc, int numTab) throws InterruptedException {
    Boolean isFillTab = false;
    if (!waitLoadForm(isProdServer, "//*[@class='header']")) {
      return isFillTab;
    }

    fillAllFilters(isProdServer, typeDoc, ".//div[@id='tab_TAB_GROUP_0" + numTab + "']", numTab);


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

      elements = wd.findElements(By.xpath("//li[contains(@class,'autoFormTabItem')]//a[contains(@href,'TAB_GROUP')]"));
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

  public void fillAllFilters(boolean isProdServer, String typeDoc, String xpathAllElements, int numTab) throws InterruptedException {
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
        if (!fillFiltrTypeButtonGen(element, isProdServer)) {
          fillFiltrGenEdit(element, "Test_001");
        }
        if (typeDoc.equals("SitizenLetter")) {
          fillFiltrTypeDate(element, "Test_001", 0);
        }
      } else {
        fillFiltrTypeDate(element, "Test_001", 7);
      }

//      fillFiltrReference(element, 16, isProdServer);
//      fillFiltrReference(element, 15, isProdServer);
//      fillFiltrCheckbox(element);
    }
  }

  private void fillFiltrCombobox(WebElement element) throws InterruptedException {
    String xpathlocator = ".//span[contains(@class,'autoFormCOMBOBOX')][not(contains(@class,'hide'))]" +
            "//select[count(ancestor::div[@panel_id][contains(@class,'hide')])=0][not(@disabled='disabled')]";

    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
          // Если элемент видим, незадезейблен, без значения - тогда выставить последнее
          if (isVisibleEnabledWithoutValue(w)) {
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

  public Boolean checkEnableButtonUpLoad() throws InterruptedException {
    String xpathlocator =
            "//div[@nick='FILE_GROUP']//span[contains(@class,'last')]//div[@class='qq-upload-button']/input";

    List<WebElement> elements = wd.findElements(By.xpath(xpathlocator));
    if (elements.size() >= 1) {
      WebElement w = elements.iterator().next();
      // Если элемент видим, незадезейблен то все ок
      if (w.isEnabled()) {
        return true;
      }
    }

    return false;
  }


  private void fillFiltrCheckbox(WebElement element) throws InterruptedException {
    String xpathlocator = ".//input[@type='checkbox' and @class!='masked']";

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
    String xpathlocator = ".//input[count(ancestor::div[@panel_id][contains(@class,'hide')])=0]" +
            "[not(@disabled='disabled')][@typeview='EDIT']";
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

  private void fillFiltrTypeDate(WebElement element,
                                 String text,
                                 long anotherDate) throws InterruptedException {
    String xpathlocator = ".//input[count(ancestor::span[contains(@class,'autoFormDATE')]" +
            "[contains(@class,'hide')])=0]" +
            "[count(ancestor::div[@panel_id][contains(@class,'hide')])=0]" +
            "[not(@disabled='disabled')][@typeview='DATE']";
    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date currentDate = new Date();
        Long time = currentDate.getTime();
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
    String xpathlocator = ".//textarea[count(ancestor::div[@panel_id][contains(@class,'hide')])=0]" +
            "[not(@disabled='disabled')][@typeview='TEXTAREA']";
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

  private void fillReportTextArea(WebElement baseElement, String text) throws InterruptedException {
    String xpathlocator = "//textarea[@id='reportText']";
    List<WebElement> elements = new ArrayList<>();

    for (int i = 0; i < 20; i++) {
      elements = baseElement.findElements(By.xpath(xpathlocator));
      if (elements.size() == 0) {
        Thread.sleep(500);
      } else {
        break;
      }
    }
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
          waitForDisplayed(w);
          if (w.isDisplayed()) {
            type(w, text);
          }
        }
      }
    }
  }

  private Boolean fillFiltrTypeButtonGen(WebElement element, boolean isProdServer) throws InterruptedException {
    String xpathlocator = ".//button[@class='numberGeneratorButton']";
    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
          if (w.isDisplayed()) {
            String xPathValue = "../input[@disabled='disabled']";
            List<WebElement> elementsDisabled = w.findElements(By.xpath(xPathValue));
            // Если элемент ввода рег.номера задизейблен, то жмем на кнопку рег. номера
            if (elementsDisabled.size() > 0) {
              clickWithWaiting(w, isProdServer);
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  private void fillFiltrGenEdit(WebElement element, String text) {
    String xpathGenNumber = "//input[contains(@class,'generatorInput')]";
    List<WebElement> elements2 = element.findElements(By.xpath(xpathGenNumber));
    if (elements2.size() == 1) {
      WebElement ww = elements2.iterator().next();
      if (ww.isEnabled()) {
        type(ww, text);
      }
    }
  }

  private void fillFiltrMultiSelect(WebElement element) throws InterruptedException {
    String xpathlocator = ".//ul[@class='token-input-list']/li[@class='token-input-input-token']" +
            "/input[count(ancestor::div[@panel_id][contains(@class,'hide')])=0][not(@disabled='disabled')]";

    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {

          // Если элемент видим, незадезейблен, без значения - тогда выставить последнее
          String xPathValue = "../../li[@class='token-input-token']/p";
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

  private void multiSelectString(String stringValue) throws InterruptedException {
    String jstriptString;

    jstriptString = "$('[nick=\"RECIPIENT_GROUP_2\"]').find('.token-input-input-token input[type=\"text\"]')" +
            ".click().val('" + stringValue + "')";
    ((JavascriptExecutor) wd).executeScript(jstriptString);
    Thread.sleep(500);
    jstriptString = "$('.token-input-dropdown li').eq(0).mousedown();";
    ((JavascriptExecutor) wd).executeScript(jstriptString);
    Thread.sleep(200);
  }

  public Boolean openCardTabWithProcess(boolean isProdServer, TaskProcessData process) throws InterruptedException {
    Boolean isOpenWithoutMistakes = false;
    // Проверка на реальные баги
    isOpenWithoutMistakes = checkCardMistakes(isProdServer, "//*[contains(text(),\"Faled\") or contains(text(),\"xception\")]");

    if (!isOpenWithoutMistakes) {
      return false;
    }

    String xpathTypeDocument = "//span[contains(text(),'Служебные записки ЦА')]";
    waitLoadPage(isProdServer);
    Thread.sleep(200);
    waitElement(By.xpath(xpathTypeDocument));

    String xpathSecondTab = "(//ul[@id='tabs_group']//a)[2]";
    waitLoadPage(isProdServer);
    List<WebElement> elements = wd.findElements(By.xpath(xpathSecondTab));
    if (elements.size() > 0) {
      clickWithWaiting(elements.iterator().next(), isProdServer);
      String xpathCardProcess = "//a[contains(@href,'tabInfo.action?tab=PROCESS_CARD&tab2=PROCESS_CARD')]";
      waitLoadPage(isProdServer);
      Thread.sleep(200);
      waitElement(By.xpath(xpathCardProcess));

      List<WebElement> elementsProcess = wd.findElements(By.xpath(xpathCardProcess));
      WebElement next = elementsProcess.iterator().next();
      String href = next.getAttribute("href");

      process.withUrlCardProcess(href);
      return true;
    }
    return false;
  }

  public Boolean openCardProcess(boolean isProdServer, TaskProcessData taskProcess) throws InterruptedException {
    String ss = taskProcess.getUrlCardProcess();
//    ss = ss + "2";

    wd.get(ss);

    Boolean isOpenWithoutMistakes = false;
    // Проверка на реальные баги
    isOpenWithoutMistakes = checkCardMistakes(isProdServer, "//*[contains(text(),\"Faled\") or contains(text(),\"xception\")]");

    if (!isOpenWithoutMistakes) {
      return false;
    }

    String xpathTypeDocument = "//table[@id='tabsWrap']//a[contains(@href,'tabInfo.action?documentId')]";
    waitLoadPage(isProdServer);
    Thread.sleep(200);
    waitElement(By.xpath(xpathTypeDocument));

    return true;
  }

  public Boolean openCardProcessNext(boolean isProdServer, TaskProcessData taskProcess) throws InterruptedException {
    wd.manage().window().maximize();
    waitLoadPage(isProdServer);
    Thread.sleep(200);
    wd.get(taskProcess.getUrlCardProcess());

    Boolean isOpenWithoutMistakes = false;
    // Проверка на реальные баги
    isOpenWithoutMistakes = checkCardMistakes(isProdServer, "//*[contains(text(),\"Faled\") or contains(text(),\"xception\")]");

    if (!isOpenWithoutMistakes) {
      return false;
    }

    String xpathTypeDocument = "//td[@class='type']//img[contains(@src,'Cube.png')]";
    waitLoadPage(isProdServer);
    Thread.sleep(200);
    waitElement(By.xpath(xpathTypeDocument));

    return true;
  }

  public Boolean readActiveTaskProcessData(boolean isProdServer,
                                           TaskProcessData taskProcess,
                                           DbConnect dbConnect) throws Exception {
    String xpathTask = "//table[@id='tabsWrap']//a[contains(@href,'tabInfo.action?documentId')]";
    String xpathTaskURL = xpathTask;
    String xpathTaskNumber = xpathTask;
    String xpathTaskName = xpathTask + "/../../td[@class='task']";
    String xpathTaskExecutor = xpathTask + "/../../td[@class='executor']/div[1]";
    String xpathProcessNumDate = "//div[@class='bold appeal']";

    waitElement(By.xpath(xpathTask));
    waitElement(By.xpath(xpathTaskName));
    waitElement(By.xpath(xpathTaskExecutor));

    // Номер и ссылка на задачу
    List<WebElement> elements = wd.findElements(By.xpath(xpathTask));
    if (elements.size() > 0) {
      WebElement element = elements.iterator().next();
      String numTask = element.getText();

      taskProcess.withNumberTask(numTask).withUrlCardTask(xpathTaskNumber);
    }

    // Наименование задачи
    elements = wd.findElements(By.xpath(xpathTaskName));
    if (elements.size() > 0) {
      WebElement element = elements.iterator().next();
      String nameTask = element.getText();

      taskProcess.withNameTask(nameTask);
    }

    // Исполнитель (ФИО)
    elements = wd.findElements(By.xpath(xpathTaskExecutor));
    if (elements.size() > 0) {
      WebElement element = elements.iterator().next();
      String taskExecutor = element.getText();

      taskProcess.withFio(taskExecutor);
      String loginFromBD = getLoginFromBD(taskProcess, dbConnect);
      taskProcess.withLogin(loginFromBD);
    }

    // Номер и дата процесса
    elements = wd.findElements(By.xpath(xpathProcessNumDate));
    if (elements.size() > 0) {
      WebElement element = elements.iterator().next();
      String processNumberDate = element.getText();

      taskProcess.withNumberProcess(processNumberDate);
    }

    return true;
  }

  public Boolean readActiveTaskProcessDataNext(boolean isProdServer,
                                               TaskProcessData taskProcess,
                                               DbConnect dbConnect) throws Exception {
    String xpathCheckActiveTask = "(//td[@class='type']//img[contains(@src,'whiteCube.png')])[1]";

    String xpathTaskNumber = xpathCheckActiveTask + "/../../td[@class='number']/a";
    String xpathTaskName = xpathCheckActiveTask + "/../../td[@class='task']";
    String xpathTaskExecutor = xpathCheckActiveTask + "/../../td[@class='executor']/div[1]";
    String xpathProcessNumDate = "//div[@class='bold appeal']";

    for (int i = 0; i < 10; i++) {
      List<WebElement> whiteCubeElements = wd.findElements(By.xpath(xpathCheckActiveTask));
      if (whiteCubeElements.size() == 0) {
        Thread.sleep(500);
      } else {
        waitElement(By.xpath(xpathCheckActiveTask));
        waitElement(By.xpath(xpathTaskNumber));
        waitElement(By.xpath(xpathTaskName));
        waitElement(By.xpath(xpathTaskExecutor));

        // Номер и ссылка на задачу
        List<WebElement> elements = wd.findElements(By.xpath(xpathTaskNumber));
        if (elements.size() > 0) {
          WebElement element = elements.iterator().next();
          String numTask = element.getText();

          taskProcess.withNumberTask(numTask).withUrlCardTask(xpathTaskNumber);
        }

        // Наименование задачи
        elements = wd.findElements(By.xpath(xpathTaskName));
        if (elements.size() > 0) {
          WebElement element = elements.iterator().next();
          String nameTask = element.getText();

          taskProcess.withNameTask(nameTask);
        }

        // Исполнитель (ФИО)
        elements = wd.findElements(By.xpath(xpathTaskExecutor));
        if (elements.size() > 0) {
          WebElement element = elements.iterator().next();
          String taskExecutor = element.getText();

          taskProcess.withFio(taskExecutor);
        }

        // Вычисляем логин (лучше через БД)
        String login = getLoginFromBD(taskProcess, dbConnect);
        taskProcess.withLogin(login);

        // Номер и дата процесса
        elements = wd.findElements(By.xpath(xpathProcessNumDate));
        if (elements.size() > 0) {
          WebElement element = elements.iterator().next();
          String processNumberDate = element.getText();

          taskProcess.withNumberProcess(processNumberDate);
        }

        return false;
      }
    }

    return true;
  }

  private String getLoginFromBD(TaskProcessData taskProcess, DbConnect dbConnect) throws Exception {
    String login = "";

    String dbserver = dbConnect.getDbserver();//"vm-082-oradb-gge.mdi.ru";
    String port = dbConnect.getPort();//"1521";
    String sid = dbConnect.getSid();//"db";

    String user = dbConnect.getUser();//"galactica";
    String password = dbConnect.getPassword();//"galactica";

    String dbConnUrl = "jdbc:oracle:thin:@" + dbserver + ":" + port + "/" + sid;

    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
    } catch (Exception exc) {
      throw new RuntimeException("Couldn't load Oracle JDBC driver", exc);
    }

    System.out.println("Connecting to: SERVER=" + dbConnUrl + ", USER=" + user + ", PASSWORD=" + password + " ...");

    Connection conn = DriverManager.getConnection(dbConnUrl, user, password);
    String sql = String.format("select u.username\n" +
            "from galactica.userinfo u\n" +
            "where u.fio = '%s'\n" +
            "  and HIDE=0", taskProcess.getFio());

    Statement stmt = conn.createStatement();
    ResultSet rs;
    String res = "";
    try {
      stmt = conn.createStatement();
      stmt.execute(sql);

      rs = stmt.getResultSet();
      if (rs.next()) {
        res = rs.getString(1);
      } else {
        throw new Exception("Object not found");
      }

      rs.close();

      login = res;
    } catch (SQLException e) {
      final String err = "Failed to execute sql:\n" + sql;
      throw new Exception(err, e);
    } catch (Throwable e) {
      final String err = "Error at function";
      throw new Exception(err, e);
    } finally {
    }
    return login;
  }

  public boolean writeActiveTaskProcessDataToJson(boolean isProdServer,
                                                  List<TaskProcessData> taskProcessDatas,
                                                  TaskProcessData taskProcess,
                                                  String fileName) throws IOException {
    taskProcessDatas.add(taskProcess);
    saveAsJsonProcessTask(taskProcessDatas, new File(fileName));

    return true;
  }

  public boolean changeFirstTaskProcessDataInJson(boolean isProdServer,
                                                  List<TaskProcessData> taskProcessDatas,
                                                  TaskProcessData taskProcess,
                                                  String fileName) throws IOException {
    List<TaskProcessData> taskProcessDatasNew = new ArrayList<>();

    // Добавляем текущую инфу по задаче
    taskProcessDatasNew.add(taskProcess);

    // Удаляем первый элемент списка (замена)
    taskProcessDatas.remove(0);

    // Дописываем в список задач остальные
    for (TaskProcessData tpd : taskProcessDatas) {
      taskProcessDatasNew.add(tpd);
    }

    saveAsJsonProcessTask(taskProcessDatasNew, new File(fileName));

    return true;
  }

  public boolean deleteFirstTaskProcessDataInJson(boolean isProdServer,
                                                  List<TaskProcessData> taskProcessDatas,
                                                  String fileName) throws IOException {
    // Удаляем первый элемент списка
    taskProcessDatas.remove(0);
    saveAsJsonProcessTask(taskProcessDatas, new File(fileName));

    return true;
  }

  public List<TaskProcessData> readActiveTaskProcessDataFromJson(String fileProcessTestCases) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(
            new File(fileProcessTestCases)))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<TaskProcessData> taskProcessDatas = gson.fromJson(json, new TypeToken<List<TaskProcessData>>(){}.getType()); // List<ProcessTestCases>.class
      return taskProcessDatas;
    }
  }

  public boolean writeActiveProcessDataToJson(boolean isProdServer,
                                              ProcessTestCases processTestCase,
                                              String fileName) throws IOException {
    List<ProcessTestCases> processTestCases = new ArrayList<>();
    processTestCases.add(processTestCase);
    saveAsJsonProcessTestCase(processTestCases, new File(fileName));

    return true;
  }

  public boolean openCardTask(boolean isProdServer, TaskProcessData taskProcess) throws InterruptedException {
    String xpathNameCard = "//div[@class='nameObj']";
    String xpathSections = "//p[@class='sectionTitle']";

    wd.manage().window().maximize();

    waitLoadPage(isProdServer);
    Thread.sleep(200);
    waitElement(By.xpath(taskProcess.getUrlCardTask()));
    clickDifficalt(isProdServer, taskProcess.getUrlCardTask());

    waitLoadPage(isProdServer);
    Thread.sleep(200);
    waitElement(By.xpath(xpathNameCard));

    for (int i = 1; i < 20; i++) {
      waitLoadPage(isProdServer);
      Thread.sleep(500);

      // Проверка на загрузку заголовков
      Boolean isWebElements = isWebElements(isProdServer, By.xpath(xpathSections));
      if (isWebElements) {
        return true;
      }
    }

    return false;
  }

  public boolean waitingOpenCardTask(boolean isProdServer, TaskProcessData taskProcess) throws InterruptedException {
    Boolean isOpenWithoutMistakes = false;
    // Проверка на реальные баги
    isOpenWithoutMistakes = checkCardMistakes(isProdServer, "//*[contains(text(),\"Faled\") or contains(text(),\"xception\")]");

    if (!isOpenWithoutMistakes) {
      return false;
    }


    String xpathNameCard = "//div[@class='nameObj']";
    String xpathSections = "//p[@class='sectionTitle']";

    waitLoadPage(isProdServer);
    Thread.sleep(200);
    waitElement(By.xpath(xpathNameCard));

    for (int i = 1; i < 20; i++) {
      waitLoadPage(isProdServer);
      Thread.sleep(500);

      // Проверка на загрузку заголовков
      Boolean isWebElements = isWebElements(isProdServer, By.xpath(xpathSections));
      if (isWebElements) {
        break;
      }
    }

    return true;
  }

  public boolean deleteJsonZapis(String fileName) throws IOException {
    List<TaskProcessData> processTasks = new ArrayList<TaskProcessData>();
    saveAsJsonProcessTask(processTasks, new File(fileName));
    return true;
  }

  public boolean checkUpLoadFile(boolean isProdServer) throws InterruptedException {
    String locator = "//span[contains(@class,'file_name_js')][contains(@class,'AttachmentFileName')]";

    waitLoadPage(isProdServer);
    Thread.sleep(200);
    waitElement(By.xpath(locator));

    for (int i = 1; i < 20; i++) {
      waitLoadPage(isProdServer);
      Thread.sleep(500);

      // Проверка на загрузку ссылки на файл
      Boolean isWebElements = isWebElements(isProdServer, By.xpath(locator));
      if (isWebElements) {
        return true;
      }
    }

    return false;
  }

  public boolean showFile(boolean isProdServer, UpLoadFileData upLoadFileData) throws InterruptedException {
    String fieldIdString = "//span[@nick='SYS_FILE']";

    waitLoadPage(isProdServer);
    Thread.sleep(200);
    waitElement(By.xpath(fieldIdString));

    String field_id = "";
    // Найти секцию, в которой расположена инфа про файл
    List<WebElement> elements = wd.findElements(By.xpath(fieldIdString));
    if (elements.size() == 1) {
      WebElement element = elements.iterator().next();
      field_id = element.getAttribute("field_id");
    }

    String jstriptString = String.format("var fileId='%s',\n" +
            "fileSize='%s',\n" +
            "fileName='%s',\n" +
            "fieldId = '%s',\n" +
            "fileNameArr = fileName.split('.'),\n" +
            "fileItem= '<span class=\"file_name_js AttachmentFileName\"></span>' +\n" +
            "                                '<span class=\"file_input_js\"></span>' +\n" +
            "                                '<span class=\"fileSize\"></span>' +\n" +
            "                                '<span class=\"file_delete_js deleteElem\"><a href=\"#\">&times;</a></span>' +\n" +
            "                                '<span class=\"checkSign\"></span>';\n" +
            "\n" +
            "var fileRow = $('<li/>').addClass('attachmentFileRow').attr('id', fileId).append(fileItem);\n" +
            "fileRow.find('.AttachmentFileName').attr('title', fileName).html('<span class=\"limitedName\">'+fileNameArr[0]+'</span>.'+fileNameArr[1]);\n" +
            "fileRow.find('.fileSize').html('('+fileSize+')');\n" +
            "$('[field_id=\"'+fieldId+'\"] ul.attachmentsTable ').append(fileRow);\n" +
            "fileRow.closest('.attachmentsTableWrapper').siblings('.fileSign').removeClass('disabled');"
            ,upLoadFileData.getId()
            ,upLoadFileData.getSize()
            ,upLoadFileData.getName()
            ,field_id);

    ((JavascriptExecutor) wd).executeScript(jstriptString);
    Thread.sleep(200);

    return true;
  }

  public boolean writeEP(boolean isProdServer) throws InterruptedException {
    String locator = "//span[@class='sign sign_js']";

    List<WebElement> elements = wd.findElements(By.xpath(locator));
    if (elements.size() == 1) {
      WebElement element = elements.iterator().next();
      clickWithWaiting(element, isProdServer);
    }

    locator = "//a[@class='changeCertificateLinkName'][contains(text(),'Егор Юрьевич')]";
    elements = wd.findElements(By.xpath(locator));
    if (elements.size() >= 1) {
      WebElement element = elements.iterator().next();
      clickWithWaiting(element, isProdServer);

      locator = "//input[@class='button OK']";
      elements = wd.findElements(By.xpath(locator));
      if (elements.size() == 1) {
        element = elements.iterator().next();
        clickWithWaiting(element, isProdServer);
        return true;
      }
    }

    return false;
  }

  public boolean checkEP(boolean isProdServer) throws InterruptedException {
    String locator = "//span[contains(@class,'file_name_js')][contains(@class,'AttachmentFileName')]";

    waitLoadPage(isProdServer);
    Thread.sleep(200);
    waitElement(By.xpath(locator));

    List<WebElement> elements = wd.findElements(By.xpath(locator));
    if (elements.size() >= 1) {
      WebElement element = elements.iterator().next();
      clickWithWaiting(element, isProdServer);
    }

    locator = "//a[@class='fileBaloonBtn baloonCkeckECP']";
    elements = wd.findElements(By.xpath(locator));
    if (elements.size() >= 1) {
      WebElement element = elements.iterator().next();
      clickWithWaiting(element, isProdServer);
    }

    Thread.sleep(500);
    locator = "//p[@class='status']";
    elements = wd.findElements(By.xpath(locator));
    if (elements.size() == 1) {
      WebElement element = elements.iterator().next();
      String text = element.getText();
      if (text.equals("ЭЛЕКТРОННАЯ ПОДПИСЬ ДЕЙСТВИТЕЛЬНА")) {
        Thread.sleep(500);
        locator = "//div[@class='alertButtonBar center']/button";
        elements = wd.findElements(By.xpath(locator));
        if (elements.size() >= 1) {
          element = elements.iterator().next();
          waitForDisplayed(element);
          element.click();
        }
        return true;
      }
    }

    return false;
  }

  public int getNumberActiveTask(boolean isProdServer) throws InterruptedException {
    String xpathCheckResolvedTasks = "//td[@class='type']//img[contains(@src,'greenCube.png')]";

    waitLoadPage(isProdServer);
    Thread.sleep(200);
    List<WebElement> elements = new ArrayList<WebElement>();

    for (int i = 1; i < 10; i++) {
      elements = wd.findElements(By.xpath(xpathCheckResolvedTasks));
      if (elements.size() == 0) {
        Thread.sleep(500);
      } else {
        return elements.size();
      }
    }
    return elements.size();
  }
}