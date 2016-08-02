package ru.stqa.pft.gge.tests;

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
    String urlAD = baseUrl + "portal/tabInfo.action?tab=OFFICEWORK#/tree::rel=4/" +
            "filter::id=E93EC358A69745469F6266C0275F7907/vitrina::viewId=E93EC358A69745469F6266C0275F7907" +
            "&offset=0&limit=50";

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
    app.session().loginProcess(isProdServer, loginUser, password, baseUrl);
//    assertThat(app.processGGE().razdel(isProdServer, "//a[@href=\"tabInfo.action?tab=OFFICEWORK\"]"),
//            equalTo(true));
    assertThat(app.processGGE().razdelUrl(isProdServer, urlAD),
            equalTo(true));
    assertThat(app.processGGE().selectTypeDoc(isProdServer), equalTo(true));
    app.processGGE().fillForm(isProdServer);

  }
}
