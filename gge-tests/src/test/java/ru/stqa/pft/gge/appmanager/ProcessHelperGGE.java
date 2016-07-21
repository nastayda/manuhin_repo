package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

  public void openListWithPatternForm(boolean isProdServer) throws InterruptedException {
    // Клик по кнопке "Мои действия"
    String xpath = "//div[contains(@class,\"singleButton\")][contains(text(),\"Мои действия\")]";
    clickDifficalt(isProdServer, xpath);



    // Клик по кнопке "Создать документ"
    xpath = "//ul[@class=\"notUgd HEADER showSubMenu showme\"]/li/a";
    clickDifficalt(isProdServer, xpath);



  }

  private void selectTypeDocument(WebElement element, int attr, boolean isProdServer) throws InterruptedException {
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


}
