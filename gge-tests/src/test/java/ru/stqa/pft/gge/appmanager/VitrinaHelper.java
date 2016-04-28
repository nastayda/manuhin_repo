package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.gge.model.GeneratorData;

import java.util.List;

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
    wd.findElement(By.xpath(vitrina.getVitrinaXpath())).click();
  }

  private void selectMenuVitrin(GeneratorData vitrina) throws InterruptedException {
    waitLoadPage();
    waitElement(By.xpath(vitrina.getMenuXpath()));
    wd.findElement(By.xpath(vitrina.getMenuXpath())).click();
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
          type(w, text);
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
