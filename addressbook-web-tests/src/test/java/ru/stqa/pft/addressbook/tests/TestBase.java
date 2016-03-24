package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

/**
 * Created by Юрий on 04.03.2016.
 */
public class TestBase {

  //protected static final ApplicationManager app = new ApplicationManager(BrowserType.FIREFOX);
  //protected final ApplicationManager app = new ApplicationManager(BrowserType.IE);
  protected static final ApplicationManager app = new ApplicationManager(BrowserType.CHROME);

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite
    public void tearDown() {
      app.stop();
  }

  public ApplicationManager getApp() {
    return app;
  }
}
