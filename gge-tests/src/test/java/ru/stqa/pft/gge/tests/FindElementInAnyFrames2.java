package ru.stqa.pft.gge.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Created by Юрий on 05.06.2016.
 */
public class FindElementInAnyFrames2 {
  private WebDriver wd;
  private String browser;
  private WebDriverWait wait;

  @BeforeMethod
  private void init() throws InterruptedException {
    browser = BrowserType.CHROME;

    if (browser.equals(BrowserType.FIREFOX)) {
      FirefoxProfile firefoxProfile = new FirefoxProfile(
              new File("c:/Users/Юрий/AppData/Roaming/Mozilla/Firefox/Profiles/90zxmmsx.selenium"));
      firefoxProfile.setEnableNativeEvents(false);
      firefoxProfile.setPreference("network.cookie.prefsMigrated",true);
      wd = new FirefoxDriver(firefoxProfile);
    } else if (browser.equals(BrowserType.CHROME)) {
      ChromeOptions chromeOptions = new ChromeOptions();
      chromeOptions.addArguments("--user-data-dir=/home/user/.a5");
      wd = new ChromeDriver(chromeOptions);
    } else if (browser.equals(BrowserType.IE)) {
      InternetExplorerDriverService service = new InternetExplorerDriverService.Builder()
              .usingDriverExecutable(new File("d:/tools/IEDriverServer.exe")).build();
      DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
      capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
      wd = new InternetExplorerDriver(service,capabilities);
    }

//     frame
    wd.get("file:///C:/Users/%D0%AE%D1%80%D0%B8%D0%B9/Downloads/____%D0%9E%D0%B1%D1%83%D1%87%D0%B5%D0%BD%D0%B8%D0%B5/%D0%92%D1%81%D0%B5%20%D1%81%D0%B5%D0%BA%D1%80%D0%B5%D1%82%D1%8B%20%D0%B8%20%D1%82%D0%B0%D0%B9%D0%BD%D1%8B%20Selenium%202.0/Lesson_4/%D0%94%D0%975/Frames2.html");

    // iframe
//    wd.get("file:///C:/Users/%D0%AE%D1%80%D0%B8%D0%B9/Downloads/____%D0%9E%D0%B1%D1%83%D1%87%D0%B5%D0%BD%D0%B8%D0%B5/%D0%92%D1%81%D0%B5%20%D1%81%D0%B5%D0%BA%D1%80%D0%B5%D1%82%D1%8B%20%D0%B8%20%D1%82%D0%B0%D0%B9%D0%BD%D1%8B%20Selenium%202.0/Lesson_4/%D0%94%D0%975/iFrames.html");
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    wait = new WebDriverWait(wd, 10);
  }

  @Test
  public void testFindElementInAnyFrames() {
//    String locator = "//a[@class=\"page-up\"][contains(text(),\"Qwerty123\")]";
//    String locator = "//button";
    String locator = "//span[contains(text(),\"Kendo\")]";

    try {
      findElementInAnyFrame(wd, By.xpath(locator)).click();
    } catch (NullPointerException e) {
      System.out.println("Такого элемента на странице нет!");
    }
  }

  private WebElement findElementInAnyFrame(WebDriver wd, By locator) {
    String frameXpath = "//frame";
    String iframeXpath = "//iframe";
    WebElement targetElement = findElementInFrames(wd, locator, frameXpath, "frame");
    if (targetElement == null) {
      targetElement = findElementInFrames(wd, locator, iframeXpath, "iframe");
    }
    return targetElement;
  }

  private WebElement findElementInFrames(WebDriver wd, By locator, String frameXpath, String frameOutString) {
    WebElement targetElement = null;
    List<WebElement> frames = wd.findElements(By.xpath(frameXpath));
    if (frames.size() > 0) {
      int i = 0;
      for (WebElement f : frames) {
        i++;
        wd.switchTo().frame(f);
        List<WebElement> elementsToFind = wd.findElements(locator);
        if (elementsToFind.size() > 0) {
          System.out.println(frameOutString + " № " + i + " : найден искомый элемент");
          targetElement = wait.until(visibilityOfElementLocated(locator));
          return targetElement;
        } else {
          wd.switchTo().defaultContent();
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
