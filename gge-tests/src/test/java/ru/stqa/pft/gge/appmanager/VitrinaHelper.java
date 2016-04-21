package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by manuhin on 21.04.2016.
 */
public class VitrinaHelper extends HelperBase {
  public VitrinaHelper(WebDriver wd) {
    super(wd);
  }

  public void selectVitrina() throws InterruptedException {
    selectRazdel();
    selectMenuVitrin();
    selectPodMenuVitrina();
  }

  private void selectPodMenuVitrina() throws InterruptedException {
    waitElement(By.xpath("//a[contains(text(),'Новые обращения')]"));
    wd.findElement(By.xpath("//a[contains(text(),'Новые обращения')]")).click();
  }

  private void selectMenuVitrin() throws InterruptedException {
    waitLoadPage();
    waitElement(By.xpath("//div[@id='secondMenu']/ul/li/a"));
    wd.findElement(By.xpath("//div[@id='secondMenu']/ul/li/a")).click();
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

  private void selectRazdel() throws InterruptedException {
    waitElement(By.linkText("Экспертиза"));
    waitLoadPage();
    wd.findElement(By.linkText("Экспертиза")).click();
  }
}
