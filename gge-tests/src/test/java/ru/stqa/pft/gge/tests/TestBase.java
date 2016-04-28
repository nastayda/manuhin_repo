package ru.stqa.pft.gge.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.ITestContext;
import org.testng.annotations.*;
import ru.stqa.pft.gge.appmanager.ApplicationManager;

/**
 * Created by Юрий on 04.03.2016.
 */
@Listeners(MyTestListener.class)
public class TestBase {

  protected final ApplicationManager app =
          new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX)) ;

  @BeforeMethod
  public void setUp(ITestContext context) throws Exception {
    app.init();
    context.setAttribute("app", app);
  }

  public void setUp2() throws Exception {
    app.init();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
      app.stop();
  }

  public ApplicationManager getApp() {
    return app;
  }
}
