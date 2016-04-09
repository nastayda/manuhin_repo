package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;

/**
 * Created by Юрий on 09.04.2016.
 */
public class RegistrationTests extends TestBase {

  @Test
  public void testRegistratin(){
    app.registration().start("user1", "user1@localhost.localdomain");

  }
}
