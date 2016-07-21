package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

/**
 * Created by manuhin on 21.07.2016.
 */
public class ProcessHelperGGE extends HelperBase {
  public ProcessHelperGGE(WebDriver wd) {
    super(wd);
  }

  private Properties properties;

  public void razdel(Boolean isProdServer) throws InterruptedException {
    String xpathRazdelStart = properties.getProperty("xpath.razdel.start");
    String xpathRazdelEnd = properties.getProperty("xpath.razdel.end");

    waitLoadPage(isProdServer);
    Thread.sleep(1000);
    String xpath1 = xpathRazdelStart + "["+ 2 +"]" + xpathRazdelEnd;
    click(By.xpath(xpath1));
    waitLoadPage(isProdServer);
    Thread.sleep(1000);

  }
}
