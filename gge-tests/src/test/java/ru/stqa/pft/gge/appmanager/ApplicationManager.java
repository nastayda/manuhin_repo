package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.stqa.pft.gge.wrapper.HighlightingWrapper;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
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
//  WebDriver wd2;

  private String browser;
  private SessionHelper sessionHelper;
  private GeneratorHelperGGEMGE generatorHelperGGEMGE;
  private GeneratorHelperMGE generatorHelperMGE;
  private GeneratorHelperUGD generatorHelperUGD;
  private VitrinaHelper vitrinaHelper;
  private CardHelper cardHelper;

  private ProcessHelperGGE processHelperGGE;
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
          String folderString = "c:/Users/manuhin/AppData/Roaming/Mozilla/Firefox/Profiles";
          File folder = new File(folderString);
          String[] files = folder.list(new FilenameFilter() {
            @Override public boolean accept(File folder, String name) {
              return name.endsWith(".ep");
            }
          });
          for ( String fileName : files ) {
            System.out.println("File: " + fileName);

          }
          String profile = folderString + "/" + files[0].toString();
          FirefoxProfile ffProf = new FirefoxProfile(new File(profile));

          wd = new FirefoxDriver(ffProf);
//          wd2 = new HighlightingWrapper(new FirefoxDriver(), 200).getDriver();
//          wd = new FirefoxDriver();
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
      wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
      //wd.manage().window().maximize();

      sessionHelper = new SessionHelper(wd);
      generatorHelperGGEMGE = new GeneratorHelperGGEMGE(wd, properties);
      generatorHelperMGE = new GeneratorHelperMGE(wd, properties);
      generatorHelperUGD = new GeneratorHelperUGD(wd, properties);
      vitrinaHelper = new VitrinaHelper(wd);
      cardHelper = new CardHelper(wd);
      processHelperGGE = new ProcessHelperGGE(wd);


      successInit = true;
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  public void initMGE() throws IOException {
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
      wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

      sessionHelper = new SessionHelper(wd);
//      generatorHelperGGEMGE = new GeneratorHelperGGEMGE(wd, properties);
      generatorHelperMGE = new GeneratorHelperMGE(wd, properties);
//      generatorHelperUGD = new GeneratorHelperUGD(wd, properties);
      vitrinaHelper = new VitrinaHelper(wd);
      cardHelper = new CardHelper(wd);


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

  public HttpHelper http(){
    return new HttpHelper(this);
  }

  public SessionHelper session() {
    return sessionHelper;
  }

  public VitrinaHelper vitrina() {
    return vitrinaHelper;
  }

  public CardHelper card() {
    return cardHelper;
  }

  public ProcessHelperGGE processGGE() {
    return processHelperGGE;
  }

  public GeneratorHelperGGEMGE vitrinagenGGEMGE() {
    return generatorHelperGGEMGE;
  }

  public GeneratorHelperMGE vitrinagenMGE() {
    return generatorHelperMGE;
  }

  public GeneratorHelperUGD vitrinagenUGD() {
    return generatorHelperUGD;
  }

  public byte[] takeScreenshot() {
    return ((TakesScreenshot) wd).getScreenshotAs(OutputType.BYTES);
  }
}
