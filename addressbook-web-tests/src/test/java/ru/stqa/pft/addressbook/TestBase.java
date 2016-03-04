package ru.stqa.pft.addressbook;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Created by Юрий on 04.03.2016.
 */
public class TestBase extends ApplicationManager {

  @BeforeMethod
  public void setUp() throws Exception {
    init();
  }

  @AfterMethod
    public void tearDown() {
      stop();
  }

}
