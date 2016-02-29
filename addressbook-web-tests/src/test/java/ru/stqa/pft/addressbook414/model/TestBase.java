package ru.stqa.pft.addressbook414.model;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ru.stqa.pft.addressbook414.appmanager.ApplicationManager;

/**
 * Created by Юрий on 27.02.2016.
 */
public class TestBase {

  protected final ApplicationManager app = new ApplicationManager();

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    app.start();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    app.stop();
  }

}
