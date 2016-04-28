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
    waitElement(By.xpath(vitrina.getVitrinaXpath()));
    click(By.xpath(vitrina.getVitrinaXpath()));
  }

  private void selectMenuVitrin(GeneratorData vitrina) throws InterruptedException {
    waitLoadPage();
    waitElement(By.xpath(vitrina.getMenuXpath()));
    click(By.xpath(vitrina.getMenuXpath()));
  }

  public void fillAllFilters() throws InterruptedException {
    waitLoadPage();
    String startXpath = ".//*[@class=\"form\"]";
    List<WebElement> elements = wd.findElements(By.xpath(startXpath));
    WebElement element = elements.iterator().next();
    if (elements.size() == 1) {
      fillFiltrType(element, 0, "ав");
      fillFiltrType(element, 49, "ав");
      fillFiltrType(element, 47, "ав");
      fillFiltrType(element, 6, "01.04.2015");
      fillFiltrType(element, 9, "01.04.2015");
      fillFiltrReference(element, 16);
      fillFiltrReference(element, 15);
      fillFiltrCombobox(element);
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
            click(By.xpath(xpathlocator));

            Set<String> wNewSet = wd.getWindowHandles();
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
    String xpathlocator = ".//input[@class=\"finderForSelect\"]";
    List<WebElement> elements = element.findElements(By.xpath(xpathlocator));
    if (elements.size() > 0) {
      WebElement element2 = elements.iterator().next();
      if (elements.size() >= 1) {
        for (WebElement w : elements) {
          if (w.isDisplayed()) {
            w.click();
            click(By.xpath(".//*[@id='selectOptionLists']/div/div/div[2]"));
          }
        }
      }
    }
  }

  public void vizovRasshPoisk() throws InterruptedException {
    String xpathRasshPoisk = ".//*[@class=\"singleButton SERVICE searchBtn default-btn left right MAIN\"]";
    List<WebElement> elements = wd.findElements(By.xpath(xpathRasshPoisk));
    if (elements.size() == 1) {
      waitElement(By.xpath(xpathRasshPoisk));
      waitLoadPage();
      click(By.xpath(xpathRasshPoisk));
    }
  }

  private void selectRazdel(GeneratorData vitrina) throws InterruptedException {
    waitElement(By.xpath(vitrina.getRazdXpath()));
    waitLoadPage();
    click(By.xpath(vitrina.getRazdXpath()));
  }
}
