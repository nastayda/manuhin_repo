package ru.stqa.pft.gge.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.gge.appmanager.ApplicationManager;

/**
 * Created by Юрий on 04.03.2016.
 */
public class TestBase {

  protected static final ApplicationManager app =
          new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX)) ;

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
    public void tearDown() {
      app.stop();
  }


}