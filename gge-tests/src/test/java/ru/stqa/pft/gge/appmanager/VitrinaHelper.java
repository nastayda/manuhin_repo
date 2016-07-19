package ru.stqa.pft.gge.appmanager;

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
public class VitrinaHelper extends HelperBase {
  public VitrinaHelper(WebDriver wd) {
    super(wd);
  }

  public boolean selectVitrinaUGD(GeneratorData vitrina, boolean isProdServer) throws InterruptedException {
    selectPodMenuVitrinaUGD(vitrina, isProdServer);
    if (checkVitrinaNameUGD(vitrina, isProdServer) && checkRazdelNameUGD(vitrina, isProdServer)) {
      return true;
    }
    return false;
  }

  private void selectPodMenuVitrinaUGD(GeneratorData vitrina, boolean isProdServer) throws InterruptedException {
    waitLoadPage(isProdServer);
    List<WebElement> elements = wd.findElements(By.xpath(vitrina.getVitrinaXpath()));
    if (elements.size() == 1) {
      WebElement next = elements.iterator().next();
      if (waitForDisplayed(next)) {
        By locator = By.xpath(vitrina.getVitrinaXpath());
        // Прокрутка скрола до нужной ссылки
        RemoteWebElement element = (RemoteWebElement) wd.findElement(locator);
        ((Locatable) element).getCoordinates().inViewPort();
        // Клик по ссылке
        for (int ii = 1; ii < 3; ii++) {
          try {
            waitLoadPage(isProdServer);
            // Получаем ID ссылки
            jscriptClickById(vitrina.getVitrinaID());
          } catch (WebDriverException e) {
            System.out.println(vitrina.getRazdel() + " "
                    + vitrina.getMenu() + " "
                    + vitrina.getVitrina() + " WebDriverException");
            Thread.sleep(500);
          }
        }
        waitLoadPage(isProdServer);
        for (int ii = 1; ii < 20; ii++) {
          List<WebElement> elementsDOMVitrina = wd.findElements(By.xpath("//body[@class=\"isVitrina\"]"));
          if (elementsDOMVitrina.size() == 1) {
            break;
          } else {
            System.out.println(vitrina.getRazdel() + " "
                    + vitrina.getMenu() + " "
                    + vitrina.getVitrina() + " DOM not found");
            Thread.sleep(500);
          }
        }
      }
    }
  }

  public void jscriptClickById(String id) {
    String jscriptString = "document.getElementById('" + id + "').click()";
    ((JavascriptExecutor) wd).executeScript(jscriptString);
  }

  public boolean checkVitrinaNameUGD(GeneratorData vitrina, boolean isProdServer) throws InterruptedException {
    waitLoadPage(isProdServer);
    List<WebElement> elements = wd.findElements(
            By.xpath("//*[@id='serviceBar']/h3"));
    if (elements.size() == 1) {
      WebElement next = elements.iterator().next();
      for (int ii = 1; ii < 20; ii++) {
        try {
          waitForDisplayed(next);
        } catch (StaleElementReferenceException e) {
          System.out.println(vitrina.getRazdel() + " "
          + vitrina.getMenu() + " "
          + vitrina.getVitrina() + " StaleElementReferenceException");
          Thread.sleep(500);
        }
      }

      if (waitForDisplayed(next)) {
        String nameVitrinaFromPage = next.getText();
        if (nameVitrinaFromPage.equals(vitrina.getVitrina())) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean checkRazdelNameUGD(GeneratorData vitrina, boolean isProdServer) throws InterruptedException {
    waitLoadPage(isProdServer);
    List<WebElement> elements = wd.findElements(
            By.xpath("//div[@class=\"search simpleSearchContainer TOP_MENU\"]/a[@class=\"tabName\"]"));
    if (elements.size() == 1) {
      String nameRazdelFromPage = elements.iterator().next().getText();
      if (nameRazdelFromPage.equals(vitrina.getRazdel())) {
        return true;
      }
    }
    return false;
  }

  public void fillAllFiltersUGD(boolean isProdServer) throws InterruptedException {
//    waitLoadPage(isProdServer);
//    String startXpath = ".//*[@class=\"form\"]";
//    List<WebElement> elements = wd.findElements(By.xpath(startXpath));
//    if (elements.size() == 1) {
//      WebElement element = elements.iterator().next();
//      fillFiltrCombobox(element);
//      fillFiltrCheckbox(element);
//      fillFiltrType(element, 0, "ав");
//      fillFiltrType(element, 49, "ав");
//      fillFiltrType(element, 47, "ав");
//      fillFiltrType(element, 6, "01.04.2015");
//      fillFiltrType(element, 9, "01.04.2015");
//      fillFiltrReference(element, 16, isProdServer);
//      fillFiltrReference(element, 15, isProdServer);
//    }
  }

  public boolean selectVitrinaMGE(GeneratorData vitrina, boolean isProdServer) throws InterruptedException {
    wd.get(vitrina.getBaseUrl());
    wd.manage().window().maximize();

    if (!checkVitrinaIs(vitrina, isProdServer)) {
      return true;
    }

    for (int i = 1; i < 100; i++) {
      waitLoadPage(isProdServer);
      Thread.sleep(500);

      if (checkVitrinaName(vitrina)) {
        return true;
      }
    }

    return false;
  }

  public boolean selectVitrina(GeneratorData vitrina, boolean isProdServer) throws InterruptedException {
    wd.get(vitrina.getBaseUrl());
    wd.manage().window().maximize();

    if (!checkVitrinaIs(vitrina, isProdServer)) {
      return true;
    }

    for (int i = 1; i < 20; i++) {
      waitLoadPage(isProdServer);
      Thread.sleep(500);

      if (checkVitrinaName(vitrina)) {
        return true;
      }
    }

    return false;
  }

  public boolean checkVitrinaIs(GeneratorData vitrina, boolean isProdServer) throws InterruptedException {
    Boolean isVitrina = false;
    for (int i = 1; i < 10; i++) {
      waitLoadPage(isProdServer);
      List<WebElement> elements = wd.findElements(
              By.xpath(".//div[@id=\"serviceBar\" and @class=\"serviceBar\"]//h3"));
      if (elements.size() == 1) {
        isVitrina = true;
        break;
      } else {
        List<WebElement> elementsOtchetName = wd.findElements(
                By.xpath("//h1[@class=\"header_prot\"]"));
        if (elementsOtchetName.size() == 1) {
          break;
        }
      }
      Thread.sleep(500);
    }
    return isVitrina;
  }

  public boolean checkVitrinaName(GeneratorData vitrina) {
    List<WebElement> elements = wd.findElements(
            By.xpath(".//div[@id=\"serviceBar\" and @class=\"serviceBar\"]//h3"));
    if (elements.size() == 1) {
      WebElement element = elements.iterator().next();

      for (int i = 1; i < 5; i++) {
        try {
          Thread.sleep(500);
          if (element.isDisplayed()) {
            if (element.getText().equals(vitrina.getVitrina())) {
              return true;
            }
          }
        } catch (Exception e) {
          System.out.println("Элемент наименования витрины невидим");
        }
      }
    }
    return false;
  }

  public void fillAllFilters(boolean isProdServer) throws InterruptedException {
    waitLoadPage(isProdServer);
    String startXpath = ".//*[@class=\"form\"]";
    List<WebElement> elements = wd.findElements(By.xpath(startXpath));
    if (elements.size() == 1) {
      WebElement element = elements.iterator().next();
      fillFiltrCombobox(element);
      fillFiltrCheckbox(element);
      fillFiltrType(element, 0, "ав");
      fillFiltrType(element, 49, "ав");
      fillFiltrType(element, 47, "ав");
      fillFiltrType(element, 6, "01.04.2015");
      fillFiltrType(element, 9, "01.04.2015");
      fillFiltrReference(element, 16, isProdServer);
      fillFiltrReference(element, 15, isProdServer);
    }
  }

  private void fillFiltrReference(WebElement element, int attr, boolean isProdServer) throws InterruptedException {
    String xpathlocator = ".//*[@class=\"folderIco\" and @attr_type=\"" + attr + "\"]";
    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    Set<String> winOld = wd.getWindowHandles();
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
          if (w.isDisplayed()) {

            w.click();

            Set<String> wNewSet = wd.getWindowHandles();
            int attempt = 0;
            while (wNewSet.size() < 2 && attempt < 15) {
              Thread.sleep(1000);
              wNewSet = wd.getWindowHandles();
              attempt++;
            }

            wNewSet.removeAll(winOld);
            String wNew = wNewSet.iterator().next();
            wd.switchTo().window(wNew);

            Thread.sleep(500);
            String xpathRadioButton = "//input[@class=\"RadioCheckElem\" and @type=\"radio\"]";
            List<WebElement> radioButtonElements = wd.findElements(By.xpath(xpathRadioButton));
            if (radioButtonElements.size() == 0) {
              String xpathAll = "//input[@id=\"collect_all\"]";
              waitElement(By.xpath(xpathAll));
              click(By.xpath(xpathAll));
            } else {
              WebElement next = radioButtonElements.iterator().next();
              Boolean clickRadioIs = false;
              attempt = 0;
              while (!clickRadioIs && attempt < 15) {
                Thread.sleep(500);
                try {
                  next.click();
                  clickRadioIs = true;
                } catch (Exception e) {
                  attempt++;
                }
              }
            }

            String xpathSubmit = "//input[@id=\"submitButton\"]";
            waitElement(By.xpath(xpathSubmit));
            click(By.xpath(xpathSubmit));

            wd.switchTo().window(winOld.iterator().next());
            waitLoadPage(isProdServer);
          }
        }
      }
    }
  }

  public void buttonFind(boolean isProdServer) throws InterruptedException {
    waitElement(By.xpath("//div[@class='form']/input"));
    click(By.xpath("//div[@class='form']/input"));
    Thread.sleep(3000);
    waitLoadPage(isProdServer);
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

  private void fillFiltrCombobox(WebElement element) throws InterruptedException {
    String xpathlocator = ".//select";

    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
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

  public void vizovRasshPoisk(boolean isProdServer) throws InterruptedException {
    String xpathRasshPoisk = "";
    if (isProdServer) {
      xpathRasshPoisk = ".//*[@class=\"singleButton searchBtn default-btn MAIN\"]"; // eis
    } else {
      xpathRasshPoisk = ".//*[@class=\"singleButton SERVICE searchBtn default-btn left right MAIN\"]"; // test-eis, 82-й
    }
    List<WebElement> elements = wd.findElements(By.xpath(xpathRasshPoisk));
    if (elements.size() == 1) {
      waitLoadPage(isProdServer);
      waitElement(By.xpath(xpathRasshPoisk));
      click(By.xpath(xpathRasshPoisk));
    }
  }

  public void vizovRasshPoiskMGE(boolean isProdServer) throws InterruptedException {
    String xpathRasshPoisk = "";
    if (isProdServer) {
      xpathRasshPoisk = "//*[@class=\"singleButton searchBtn default-btn left right MAIN\"]"; //
    } else {
      xpathRasshPoisk =
              "//*[@class=\"SERVICE_element singleButton SERVICE searchBtn default-btn left right MAIN\"]"; // 97-й
    }

    List<WebElement> elements = wd.findElements(By.xpath(xpathRasshPoisk));
    if (elements.size() == 1) {
      waitLoadPage(isProdServer);
      waitElement(By.xpath(xpathRasshPoisk));
      click(By.xpath(xpathRasshPoisk));
    }
  }

  public boolean isMistakes(boolean isProdServer) throws InterruptedException {
    Boolean isMistakes = true;
    for (int i = 1; i < 20; i++) {
      waitLoadPage(isProdServer);
      Thread.sleep(500);

      List<WebElement> elements = wd.findElements(By.xpath(".//*[@class=\"headTitle\"]"));
      if (elements.size() > 0) {
        isMistakes = false;
        break;
      }
    }

    return isMistakes;
  }

  public boolean isMistakesOtchet(boolean isProdServer) throws InterruptedException {
    Thread.sleep(500);
    waitLoadPage(isProdServer);
    List<WebElement> elements = wd.findElements(
            By.xpath("//table[@class=\"tableCardStyle nadzorTable\"]//tr"));
    if (elements.size() == 0) {
      return true;
    }
    return false;
  }

  public List<String> allLink(boolean isProdServer) throws InterruptedException {
    Boolean isLinks = false;
    List<WebElement> elements = new ArrayList<WebElement>();

    for (int i = 1; i < 20; i++) {
      waitLoadPage(isProdServer);
      Thread.sleep(500);

      elements = wd.findElements(By.xpath("//tbody[@id=\"vitrinaResultBody\"]//a"));
      if (elements.size() > 0) {
        isLinks = true;
        break;
      }
    }

    List<String> links = new ArrayList<String>();

    if (!isLinks) {
      return links;
    }

    for (WebElement element : elements) {
      if (element.isDisplayed()) {
        String href = element.getAttribute("href");
        if (href != null) {
          links.add(href);
          System.out.println(href + "\n");
        }
      }
    }

    return links;
  }

  public boolean isWithCatagorize(boolean isProdServer) throws InterruptedException {
    Boolean isWithCatagorize = false;

    for (int i = 1; i < 5; i++) {
      waitLoadPage(isProdServer);
      Thread.sleep(500);

      if (checkCategorize(isProdServer)) {
        isWithCatagorize = true;
        break;
      }
    }

    return isWithCatagorize;
  }

  private boolean checkCategorize(boolean isProdServer) {
    Boolean isWithCatagorize = false;

    List<WebElement> elements = wd.findElements(By.xpath("//a[@class=\"megatree-exp\"]"));
    if (elements.size() > 0) {
      isWithCatagorize = true;
    }

    return isWithCatagorize;
  }

  public void clickCategorizes(boolean isProdServer) throws InterruptedException {
    waitLoadPage(isProdServer);
    Thread.sleep(500);
    jscriptOpenAllCategorize();

    allLink(isProdServer);
    waitLoadPage(isProdServer);
    Thread.sleep(500);
  }

  public void jscriptOpenAllCategorize() {
    String jscriptString = "$('#caterorizeTree .row').each(function(){ categorizeLogic.trigger($(this).attr('rel')) });";
    ((JavascriptExecutor) wd).executeScript(jscriptString);
  }
}
