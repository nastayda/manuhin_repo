package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by Юрий on 04.03.2016.
 */
public class ApplicationManager {

  private final Properties properties;
  WebDriver wd;

  private String browser;
  private SessionHelper sessionHelper;
  private GeneratorHelperGGEMGE generatorHelperGGEMGE;
  private GeneratorHelperUGD generatorHelperUGD;
  private VitrinaHelper vitrinaHelper;
  public boolean successInit = false;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    try {
      String target = System.getProperty("target", "local");
      properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
      if ("".equals(properties.getProperty("selenium.server"))) {
        if (browser.equals(BrowserType.FIREFOX)) {
          wd = new FirefoxDriver();
        } else if (browser.equals(BrowserType.CHROME)) {
          wd = new ChromeDriver();
        } else if (browser.equals(BrowserType.IE)) {
          wd = new InternetExplorerDriver();
        }
      } else {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win7")));
        wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
      }
      wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      //wd.manage().window().maximize();

      sessionHelper = new SessionHelper(wd);
      generatorHelperGGEMGE = new GeneratorHelperGGEMGE(wd, properties);
      generatorHelperUGD = new GeneratorHelperUGD(wd, properties);
      vitrinaHelper = new VitrinaHelper(wd);

      successInit = true;
    } catch (Throwable e) {
      e.printStackTrace();
    }
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

  public GeneratorHelperGGEMGE vitrinagenGGEMGE() {
    return generatorHelperGGEMGE;
  }

  public GeneratorHelperUGD vitrinagenUGD() {
    return generatorHelperUGD;
  }

  public byte[] takeScreenshot() {
    return ((TakesScreenshot) wd).getScreenshotAs(OutputType.BYTES);
  }

}
