package ru.stqa.pft.gge.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.gge.pages.DisplayedElementLocatorFactory;
import ru.stqa.pft.gge.pages.PageFilms;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

/**
 * Created by Юрий on 05.06.2016.
 */
public class FilmCollection2 {
  private WebDriver wd;
  private List<WebElement> titles;
  private String browser;
  private WebDriverWait wait;
  private String xpathFind = "//div[@class=\"searchbox\"]/input[@id=\"q\"]";
  private String xpathTitles = "//div[@class=\"title\"]";
  private String xpathNoFound = "//div[@class=\"content\"]";

  @BeforeMethod
  private void init() throws InterruptedException {
    browser = BrowserType.FIREFOX;

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

    wd.get("http://barancev.w.pw/php4dvd/#!/sort/name%20asc/");
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    wait = new WebDriverWait(wd, 10);

    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("submit")).click();
  }

  @Test
  public void testFindFilms() throws InterruptedException {
    WebElement find = wd.findElement(By.xpath(xpathFind));

    String findTextFromCookies = find.getAttribute("value");
    String stringForSearch = findTextFromCookies;
    String[] titlesFilmsFromCookies = getTitlesFilms();

    if (findTextFromCookies.equals("") || findTextFromCookies.equals("Search for movies...")) {
      stringForSearch = "t";
    } else {
      find.clear();
      find.sendKeys(Keys.ENTER);
      wait.until(invisibilityOfElementLocated(By.xpath("(" + xpathTitles + ")[1]")));
    }

    try {
      String[] titlesFilmsStart = getTitlesFilms();
      String[] titleFilmsAlternativeTrue = getTrueFilmTitles(titlesFilmsStart, stringForSearch);
      find.clear();
      find.sendKeys(stringForSearch);
      find.sendKeys(Keys.ENTER);

      waitAfterFind(titleFilmsAlternativeTrue);
      String[] titlesFilmsAfterFind = getTitlesFilms();
      assertThat(titlesFilmsAfterFind, equalTo(titleFilmsAlternativeTrue));
      if (!(findTextFromCookies.equals("") || findTextFromCookies.equals("Search for movies..."))) {
        assertThat(titlesFilmsAfterFind, equalTo(titlesFilmsFromCookies));
      }

      System.out.println("\n" + "Искали: " + stringForSearch + "\n");
      System.out.println("Отобраны фильмы:");
      for (String s : titlesFilmsAfterFind) {
        System.out.println(s);
      }
    } catch (StaleElementReferenceException e) {
      System.out.println("ОШИБКА");
    }
  }

  private void waitAfterFind(String[] titleFilmsAlternativeTrue) throws InterruptedException {
    for (int i = 0; i < 30; i++) {
//      if (films.getTitles().size() == titleFilmsAlternativeTrue.length) {
//        break;
//      }
//      Thread.sleep(500);
    }
  }

  private String[] getTrueFilmTitles(String[] titlesFilmsStart, String stringForSearch) {
    String[] titleFilmsAlternative = new String[titlesFilmsStart.length];
    int i = 0;
    for (String s : titlesFilmsStart) {
      if (s.contains(stringForSearch)) {
        titleFilmsAlternative[i] = s;
        i++;
      };
    }
    String[] titleFilmsAlternativeTrue = new String[i];
    int j = 0;
    for (String s : titleFilmsAlternativeTrue) {
      titleFilmsAlternativeTrue[j] = titleFilmsAlternative[j];
      j++;
    }
    return titleFilmsAlternativeTrue;
  }

  private String[] getTitlesFilms() throws InterruptedException {
    List<WebElement> titles = wd.findElements(By.xpath(xpathTitles));
    String[] titleFilms = new String[titles.size()];
    int i = 0;
    for (WebElement t : titles) {
      titleFilms[i] = t.getText();
      i++;
    }
    return titleFilms;
  }

  @AfterMethod(alwaysRun = true)
  private void tearDown() {
    if (wd != null) {
      wd.quit();
    }
  }

}
