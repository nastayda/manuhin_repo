package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.gge.model.GeneratorData;

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
    fillFiltrAddress();
  }

  public void buttonFind() throws InterruptedException {
    waitElement(By.xpath("//div[@class='form']/input"));
    click(By.xpath("//div[@class='form']/input"));
  }

  private void fillFiltrAddress() throws InterruptedException {
    waitElement(By.xpath("//div[@class='form']/div[2]/span/input"));
    type(By.xpath("//div[@class='form']/div[2]/span/input"), "ав");
  }

  public void vizovRasshPoisk() throws InterruptedException {
    waitElement(By.id("0E0CADA75DC448959686DFC63BD2178A"));
    waitLoadPage();
    click(By.id("0E0CADA75DC448959686DFC63BD2178A"));
  }

  private void selectRazdel(GeneratorData vitrina) throws InterruptedException {
    waitElement(By.xpath(vitrina.getRazdXpath()));
    waitLoadPage();
    click(By.xpath(vitrina.getRazdXpath()));
  }
}
