package ru.stqa.pft.gge.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.PageFactory;
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

/**
 * Created by Юрий on 28.05.2016.
 */
public class FilmCollection {

  private WebDriver wd;
  private PageFilms films;
  private List<WebElement> titles;

  @BeforeMethod
  private void init() {
    FirefoxProfile firefoxProfile = new FirefoxProfile(new File("c:/Users/Юрий/AppData/Roaming/Mozilla/Firefox/Profiles/rnps6h8z.default"));
    firefoxProfile.setEnableNativeEvents(false);
    wd = new FirefoxDriver(firefoxProfile);

    //FirefoxProfile firefoxProfile = new FirefoxProfile(new File("C:/Users/%D0%AE%D1%80%D0%B8%D0%B9/AppData/Roaming/Mozilla/Firefox/Profiles/rnps6h8z.default"));

    wd.get("http://barancev.w.pw/php4dvd/#!/sort/name%20asc/");
    films = new PageFilms();
    PageFactory.initElements(new DisplayedElementLocatorFactory(wd, 30), films);
    wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    wd.findElement(By.name("username")).sendKeys("admin");
    wd.findElement(By.name("password")).sendKeys("admin");
    wd.findElement(By.name("submit")).click();
  }

  @Test
  public void testFindFilms() throws InterruptedException {
    String[] titlesFilmsStart = getTitlesFilms();
    String stringForSearch = "an";
    String[] titleFilmsAlternativeTrue = getTrueFilmTitles(titlesFilmsStart, stringForSearch);
    films.find.sendKeys(stringForSearch);
    films.find.sendKeys(Keys.ENTER);
    try {
      waitAfterFind(titleFilmsAlternativeTrue);
      String[] titlesFilmsAfterFind = getTitlesFilms();
      assertThat(titlesFilmsAfterFind, equalTo(titleFilmsAlternativeTrue));

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
      if (films.getTitles().size() == titleFilmsAlternativeTrue.length) {
        break;
      }
      Thread.sleep(500);
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

  private String[] getTitlesFilms() {
    titles = films.getTitles();
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
