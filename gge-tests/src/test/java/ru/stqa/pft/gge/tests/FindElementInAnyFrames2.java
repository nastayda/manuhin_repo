package ru.stqa.pft.gge.tests;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;

import static java.util.logging.Logger.getLogger;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.slf4j.LoggerFactory.getILoggerFactory;

/**
 * Created by Юрий on 05.06.2016.
 */
public class FindElementInAnyFrames2 {
//  private WebDriver wd;
  private static RemoteWebDriver wd;
  private String browser;
  private WebDriverWait wait;
  private List<WebElement> jumpers;
  private int jumpLevel = 0;
  private WebElement targetElement = null;
  private ProxyServer bmp;

  static {
    getLogger("").setLevel(Level.ALL);
//    for (Handler h: getLogger("").getHandlers()) {
//      getLogger("").removeHandler(h);
//    }
//    Slf4JBridgeHandler
//    SysOutOverSLF4J
    StatusPrinter.print((LoggerContext) getILoggerFactory());
  }

  @BeforeMethod
  private void init() throws InterruptedException {
    browser = BrowserType.CHROME;

    if (browser.equals(BrowserType.FIREFOX)) {
      wd = new FirefoxDriver();
    } else if (browser.equals(BrowserType.CHROME)) {
      wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.IE)) {
      wd = new InternetExplorerDriver();
    }

    wd.setLogLevel(Level.INFO);
    wd.get("file:///D:/ht/frames.html");
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    wait = new WebDriverWait(wd, 10);
  }

  @Test
  public void testFindElementInAnyFrames() {
    String locator = "//button/span[contains(text(),\"Find_3\")]";

    try {
      findElementInAnyFrame(wd, By.xpath(locator)).click();
    } catch (NullPointerException e) {
      System.out.println("Такого элемента на странице нет!");
    }
  }

  private WebElement findElementInAnyFrame(WebDriver wd, By locator) {
    jumpers = wd.findElements(locator);
    jumpers.clear();
    WebElement targetElement = findElementInFrames(wd, locator);
    return targetElement;
  }

  private WebElement findElementInFrames(WebDriver wd, By locator) {
    String frameXpath = "//frame";
    String iframeXpath = "//iframe";

    // Собираем список всех фреймов
    List<WebElement> allFrames = wd.findElements(By.xpath(frameXpath));
    List<WebElement> iframes = wd.findElements(By.xpath(iframeXpath));
    if (iframes.size() > 0) {
      for (WebElement f : iframes) {
        allFrames.add(f);
      }
    }

    if (allFrames.size() > 0) {
      int i = 0;
      for (WebElement f : allFrames) {
        i++;

        if (wd.findElements(By.tagName("body")).size() > 0) {
          String body = wd.findElement(By.tagName("body")).getText();
          System.out.println("body : " + body);
        }

        // В список прыжков вносим фрейм, в который прыгаем
        jumpers.add(f);
        if ((jumpLevel == 0) && (jumpers.size() > 1)) {
          for (WebElement jj : jumpers) {
            wd.switchTo().frame(jj);
            // Уровень вложенности фреймов увеличиваем
            jumpLevel++;
          }
        } else {
          wd.switchTo().frame(f);
          // Уровень вложенности фреймов увеличиваем
          jumpLevel++;
        }

        if (wd.findElements(By.tagName("body")).size() > 0) {
          String body = wd.findElement(By.tagName("body")).getText();
          System.out.println("body : " + body);
        }

        // Ищем эл-т, если находим - выход
        List<WebElement> targetElements = wd.findElements(locator);
        if (targetElements.size() > 0) {
          System.out.println("Глубина вложенности = " + jumpLevel + " Фрейм № " + i + " : найден искомый элемент");
          targetElement = wait.until(visibilityOf(targetElements.iterator().next()));
          return targetElement;
        } else {

          targetElement = findElementInFrames(wd, locator);
          if (targetElement != null) {
            return targetElement;
          }

          wd.switchTo().defaultContent();

          int ii = 0;
          for (WebElement j : jumpers) {
            if (f.equals(j)) {
              break;
            }
            ii++;
          }
          jumpers.remove(ii);
          jumpLevel = 0;
        }
      }
    }

    return targetElement;
  }

  @AfterMethod(alwaysRun = true)
  private void tearDown() {
    if (wd != null) {
      wd.quit();
    }
  }
}
