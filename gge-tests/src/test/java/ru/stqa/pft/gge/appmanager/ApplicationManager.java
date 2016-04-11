package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

/**
 * Created by Юрий on 04.03.2016.
 */
public class ApplicationManager {

  private final Properties properties;
  WebDriver wd;

  private String browser;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  public void stop() {
    if (wd != null) {
      wd.quit();
    }
  }

  public WebDriver getDriver() {
    if (wd == null) {
      if (browser.equals(BrowserType.FIREFOX)) {
        wd = new FirefoxDriver();
      } else if (browser.equals(BrowserType.CHROME)) {
        wd = new ChromeDriver();
      } else if (browser.equals(BrowserType.IE)) {
        wd = new InternetExplorerDriver();
      }
      wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
      wd.get(properties.getProperty("web.baseUrl"));
    }
    return wd;
  }

  public static boolean isAlertPresent(FirefoxDriver wd) {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  @BeforeMethod
  public void setUp() throws Exception {
    wd = new FirefoxDriver();
    wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    wd.get("https://vm-082-as-gge.mdi.ru/auth/login.action");
    login("galactica_admin1", "21");
  }

  private void login(String userName, String userPassword) {
    wd.findElement(By.id("content")).click();
    wd.findElement(By.id("username")).click();
    wd.findElement(By.id("username")).clear();
    wd.findElement(By.id("username")).sendKeys(userName);
    wd.findElement(By.id("password")).click();
    wd.findElement(By.id("password")).sendKeys(userPassword);
    wd.findElement(By.id("submitBtn")).click();
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

  private void waitLoadPage() throws InterruptedException {
    waitElement(By.xpath("//div[@id=\"overlay\" and @style=\"display: none;\"]"));
  }

  private void waitElement(By locator) throws InterruptedException {
    for (int second = 0;; second++) {
      if (second >= 60) {
        fail("timeout");
      }
      try {
        if (isElementPresent(locator)) {
          break;
        }
      }
      catch (Exception e) {
      }
      Thread.sleep(1000);
    }
  }

  protected boolean isElementPresent(By locator) {
    try {
      wd.findElement(locator);
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  @AfterMethod
  public void tearDown() {
    wd.quit();
  }
}
