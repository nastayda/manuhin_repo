package ru.stqa.pft.gge.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by manuhin on 14.05.2016.
 */
public class SeleniumSamples {

  @Test
  public void onLocalHost() {
    WebDriver wd = new FirefoxDriver();
    wd.get("http://selenium2.ru");
    wd.quit();
  }

  @Test
  public void inRemoteOnLocalHost() {
    WebDriver wd = new RemoteWebDriver(DesiredCapabilities.firefox());
    wd.get("http://selenium2.ru");
    wd.quit();
  }

  @Test
  public void inRemoteOnRemoteHost() throws MalformedURLException {
    WebDriver wd = new RemoteWebDriver(new URL("http://192.168.4.208:4444/wd/hub"), DesiredCapabilities.firefox());
    wd.get("http://selenium2.ru");
    wd.quit();
  }

  public static final String USERNAME = "yuriymanuhin1";
  public static final String AUTOMATE_KEY = "V2DteiKuxvATeZxmu7iG";
  public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";

  @Test
  public void inRemoteinCloud() throws MalformedURLException {
    WebDriver wd = new RemoteWebDriver(new URL(URL), DesiredCapabilities.firefox());
    wd.get("http://selenium2.ru");
    wd.quit();
  }

  @Test
  public void simpleIERun() {
    InternetExplorerDriver wd = new InternetExplorerDriver();
    wd.get("http://selenium2.ru");
    wd.quit();
  }
}
