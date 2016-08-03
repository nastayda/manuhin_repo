package ru.stqa.pft.gge.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.gge.model.TaskProcessData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by manuhin on 21.07.2016.
 */
public class ProcessSoglSluzhZapiskiTests extends TestBase {

  String fileName = "src/test/resources/processSoglSluzhZapiski_vm-082.json";

  @Test
  public void testProcSoglSluzhZapCreateSlZap() throws InterruptedException, IOException {
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

    TaskProcessData taskProcess = new TaskProcessData();
    taskProcess.withLogin(loginUser);

    app.processGGE().openCardTabWithProcess(isProdServer, taskProcess);
    app.processGGE().openCardProcess(isProdServer, taskProcess);

    String actionWithTask = "Согласовать";

    app.processGGE().readActiveTaskProcessData(isProdServer, taskProcess, actionWithTask);
    app.processGGE().writeActiveTaskProcessDataToJson(isProdServer, taskProcess, fileName);
  }

  @DataProvider
  public Iterator<Object[]> processTasksFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(
            new File(fileName)))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<TaskProcessData> tasks = gson.fromJson(json, new TypeToken<List<TaskProcessData>>(){}.getType()); // List<GroupData>.class
      return tasks.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "processTasksFromJson", timeOut = 250000)
  public void testProcessTaskGGE(TaskProcessData task) throws Exception {

    boolean isProdServer = false;
    boolean isContainsUrlProdServer =
            task.getUrlCardProcess().contains("https://eis.gge.ru/");
    if (isContainsUrlProdServer) {
      isProdServer = true;
    }

    String loginUser = task.getLogin();

    String password = "21";
    if (isProdServer) {
      password = "Ukfdujc21";
    }

    assertThat(app.successInit, equalTo(true));
    app.session().loginProcess(isProdServer, loginUser, password, task.getUrlCardProcess());



  }

}