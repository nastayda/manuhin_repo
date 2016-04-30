package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.gge.model.GeneratorData;

import java.util.List;
import java.util.Set;

/**
 * Created by manuhin on 21.04.2016.
 */
public class VitrinaHelper extends HelperBase {
  public VitrinaHelper(WebDriver wd) {
    super(wd);
  }

  public void selectVitrina(GeneratorData vitrina) throws InterruptedException {
    selectRazdel(vitrina);
    selectMenuVitrin(vitrina);
    if (!vitrina.getMenuXpath().equals(vitrina.getVitrinaXpath())) {
      selectPodMenuVitrina(vitrina);
    }
  }

  private void selectPodMenuVitrina(GeneratorData vitrina) throws InterruptedException {
    recursiaVitrina(vitrina, 15);
  }

  private int recursiaVitrina(GeneratorData vitrina, int i) throws InterruptedException {
    try {
      click(By.xpath(vitrina.getVitrinaXpath()));
      return i;
    } catch (Exception e) {
      Thread.sleep(1000);
      i--;
      if (i > 0) {
        selectMenuVitrin(vitrina);
        return recursiaVitrina(vitrina, i);
      }
      return i;
    }
  }

  private int recursiaMenu(GeneratorData vitrina, int i) throws InterruptedException {
    try {
      click(By.xpath(vitrina.getMenuXpath()));
      return i;
    } catch (Exception e) {
      Thread.sleep(1000);
      i--;
      if (i > 0) {
        selectRazdel(vitrina);
        return recursiaMenu(vitrina, i);
      }
      return i;
    }
  }

  private void selectMenuVitrin(GeneratorData vitrina) throws InterruptedException {
    waitLoadPage();
    recursiaMenu(vitrina, 15);
  }

  public void fillAllFilters() throws InterruptedException {
    waitLoadPage();
    String startXpath = ".//*[@class=\"form\"]";
    List<WebElement> elements = wd.findElements(By.xpath(startXpath));
    WebElement element = elements.iterator().next();
    if (elements.size() == 1) {
      fillFiltrCombobox(element);
      fillFiltrType(element, 0, "ав");
      fillFiltrType(element, 49, "ав");
      fillFiltrType(element, 47, "ав");
      fillFiltrType(element, 6, "01.04.2015");
      fillFiltrType(element, 9, "01.04.2015");
      fillFiltrReference(element, 16);
      fillFiltrReference(element, 15);
    }
  }

  private int recursiaReference(WebElement webElement, int i) throws InterruptedException {
    try {
      webElement.click();
      return i;
    } catch (Exception e) {
      Thread.sleep(1000);
      i--;
      if (i > 0) {
        return recursiaReference(webElement, i);
      }
      return i;
    }
  }

  private void fillFiltrReference(WebElement element, int attr) throws InterruptedException {
    String xpathlocator = ".//*[@class=\"folderIco\" and @attr_type=\"" + attr + "\"]";
    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    Set<String> winOld = wd.getWindowHandles();
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
          if (w.isDisplayed()) {

            w.click();
            //recursiaReference(w, 15);

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

            String xpathAll = "//input[@id=\"collect_all\"]";
            waitElement(By.xpath(xpathAll));
            click(By.xpath(xpathAll));

            String xpathSubmit = "//input[@id=\"submitButton\"]";
            waitElement(By.xpath(xpathSubmit));
            click(By.xpath(xpathSubmit));

            wd.switchTo().window(winOld.iterator().next());
            waitLoadPage();
          }
        }
      }
    }
  }

  public void buttonFind() throws InterruptedException {
    waitElement(By.xpath("//div[@class='form']/input"));
    click(By.xpath("//div[@class='form']/input"));
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
    String xpathlocator = ".//*[@class=\"curSelect\"]";
    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
          if (w.isDisplayed()) {
            List<WebElement> elementsValue = w.findElements(By.xpath("./../select/option[last()]"));
            if (elementsValue.size() == 1) {
              WebElement ww = elementsValue.iterator().next();
              String text1 = ww.getText();
              //WebElement www = w.findElement(By.xpath("./span"));
              w.sendKeys(text1);
            }
          }
        }
      }
    }
  }

  public void vizovRasshPoisk() throws InterruptedException {
    String xpathRasshPoisk = ".//*[@class=\"singleButton SERVICE searchBtn default-btn left right MAIN\"]";
    List<WebElement> elements = wd.findElements(By.xpath(xpathRasshPoisk));
    if (elements.size() == 1) {
      waitLoadPage();
      waitElement(By.xpath(xpathRasshPoisk));
      click(By.xpath(xpathRasshPoisk));
    }
  }

  private void selectRazdel(GeneratorData vitrina) throws InterruptedException {
    waitLoadPage();
    WebElement w = wd.findElement(By.xpath(vitrina.getRazdXpath()));
    if (w.isDisplayed() && w.isEnabled()) {
      waitLoadPage();
      click(By.xpath(vitrina.getRazdXpath()));
    } else {
      System.out.println("Раздел \"" + vitrina.getRazdel() + "\" не найден!");
    }
  }
}
