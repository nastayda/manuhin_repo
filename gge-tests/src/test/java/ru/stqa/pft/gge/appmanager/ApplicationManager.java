package ru.stqa.pft.gge.appmanager;

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

import static org.testng.Assert.fail;

/**
 * Created by Юрий on 04.03.2016.
 */
public class ApplicationManager {

  private final Properties properties;
  WebDriver wd;

  private String browser;
  private SessionHelper sessionHelper;
  private GeneratorHelper generatorHelper;
  private VitrinaHelper vitrinaHelper;

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
    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    wd.manage().window().maximize();
    wd.get(properties.getProperty("web.baseUrl"));
    sessionHelper = new SessionHelper(wd);
    sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    generatorHelper = new GeneratorHelper(wd, properties);
    vitrinaHelper = new VitrinaHelper(wd);
  }

  public void stop() {
    if (wd != null) {
      wd.quit();
    }
  }

  public SessionHelper session() {
    return sessionHelper;
  }

  public VitrinaHelper vitrina() {
    return vitrinaHelper;
  }

  public GeneratorHelper vitrinagen() {
    return generatorHelper;
  }

}
