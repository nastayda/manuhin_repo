package ru.stqa.pft.gge.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by manuhin on 21.07.2016.
 */
public class ProcessSoglSluzhZapiskiTests extends TestBase {

  @Test
  public void testProcessSoglSluzhZapiski() throws InterruptedException {
    String baseUrl = "https://vm-082-as-gge.mdi.ru/";
    String loginUser = "e.mironova";

    boolean isProdServer = false;
    boolean isContainsUrlProdServer =
            baseUrl.contains("https://eis.gge.ru/");
    if (isContainsUrlProdServer) {
      isProdServer = true;
    }

    String password = "21";
    if (isProdServer) {
      password = "Ukfdujc21";
    }

    assertThat(app.successInit, equalTo(true));
    app.session().loginProcess(loginUser, password, baseUrl);

    app.processGGE().razdel(isProdServer);


  }
}
