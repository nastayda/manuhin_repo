package ru.stqa.pft.gge.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.ITestContext;
import org.testng.annotations.*;
import ru.stqa.pft.gge.appmanager.ApplicationManager;

import java.io.IOException;

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

  public void setUpGGEMGE(String username, String password, String baseUrl) throws Exception {
    app.init();
    app.session().loginGGEMGE(username, password, baseUrl);
  }

  public void setUpMGE(String username, String password, String baseUrl) throws Exception {
    app.initMGE();
    app.session().loginGGEMGE(username, password, baseUrl);
  }

  public void setUpUGD(String username, String password,
                       Boolean userIsChinovnik, String baseUrl,
                       String listVitrinasUrl) throws IOException {
    app.init();
    app.session().loginUGD(username, password, userIsChinovnik, baseUrl, listVitrinasUrl);
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
      app.stop();
  }

  public ApplicationManager getApp() {
    return app;
  }
}
