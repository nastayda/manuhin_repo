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
    selectPodMenuVitrina(vitrina);
  }

  private void selectPodMenuVitrina(GeneratorData vitrina) throws InterruptedException {
    //waitElement(By.xpath("//a[contains(text(),'Новые обращения')]"));
    //wd.findElement(By.xpath("//a[contains(text(),'Новые обращения')]")).click();
    waitElement(By.xpath(vitrina.getVitrinaXpath()));
    wd.findElement(By.xpath(vitrina.getVitrinaXpath())).click();
  }

  private void selectMenuVitrin(GeneratorData vitrina) throws InterruptedException {
    //waitLoadPage();
    //waitElement(By.xpath("//div[@id='secondMenu']/ul/li/a"));
    //wd.findElement(By.xpath("//div[@id='secondMenu']/ul/li/a")).click();
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
    wd.findElement(By.xpath("//div[@class='form']/input")).click();
  }

  private void fillFiltrAddress() throws InterruptedException {
    waitElement(By.xpath("//div[@class='form']/div[2]/span/input"));
    wd.findElement(By.xpath("//div[@class='form']/div[2]/span/input")).click();
    wd.findElement(By.xpath("//div[@class='form']/div[2]/span/input")).clear();
    wd.findElement(By.xpath("//div[@class='form']/div[2]/span/input")).sendKeys("ав");
  }

  public void vizovRasshPoisk() throws InterruptedException {
    waitElement(By.id("0E0CADA75DC448959686DFC63BD2178A"));
    waitLoadPage();
    wd.findElement(By.id("0E0CADA75DC448959686DFC63BD2178A")).click();
  }

  private void selectRazdel(GeneratorData vitrina) throws InterruptedException {
    //waitElement(By.linkText("Экспертиза"));
    //waitLoadPage();
    //wd.findElement(By.linkText("Экспертиза")).click();
    waitElement(By.xpath(vitrina.getRazdXpath()));
    waitLoadPage();
    wd.findElement(By.xpath(vitrina.getRazdXpath())).click();
  }
}
