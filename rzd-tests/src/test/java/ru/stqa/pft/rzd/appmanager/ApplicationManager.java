package ru.stqa.pft.rzd.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by Юрий on 04.03.2016.
 */
public class ApplicationManager {

  private final Properties properties;
  WebDriver wd;

  private SessionHelper sessionHelper;
  private String browser;
  private TicketHelper ticketHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

    if (browser.equals(BrowserType.FIREFOX)) {
      wd = new FirefoxDriver();
    } else if (browser.equals(BrowserType.CHROME)) {
      wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.IE)) {
      wd = new InternetExplorerDriver();
    }
    wd.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    wd.get(properties.getProperty("web.baseUrl"));
    sessionHelper = new SessionHelper(wd);
    ticketHelper = new TicketHelper(wd);
   // sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
  }

  public void stop() {
    wd.quit();
  }

  public TicketHelper ticket() {
    return ticketHelper;
  }

  public SessionHelper session() {
    return sessionHelper;
  }
}
