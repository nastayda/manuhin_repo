package ru.stqa.pft.gge.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.Cookie;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.gge.appmanager.HttpHelper;
import ru.stqa.pft.gge.model.TaskProcessData;
import ru.stqa.pft.gge.model.UpLoadFileData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by manuhin on 21.07.2016.
 */
public class ProcessSoglSluzhZapiskiTests extends TestBase {

  String fileName = "src/test/resources/processSoglSluzhZapiski_vm-082.json";

  @Test
  public void testProcSoglSluzhZapCreateSlZap() throws InterruptedException, IOException, URISyntaxException {
    String baseUrl = "https://vm-082-as-gge.mdi.ru/";
    String loginUser = "e.mironova";
    String urlAD = baseUrl + "portal/tabInfo.action?tab=OFFICEWORK#/tree::rel=4/" +
            "filter::id=E93EC358A69745469F6266C0275F7907/vitrina::viewId=E93EC358A69745469F6266C0275F7907" +
            "&offset=0&limit=50";
    String fileAttach = "c:/Users/manuhin/Downloads/30.docx";

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
    Set<Cookie> cookies = app.processGGE().getCookies();

    assertThat(app.processGGE().razdelUrl(isProdServer, urlAD),
            equalTo(true));
    assertThat(app.processGGE().selectTypeDoc(isProdServer), equalTo(true));

    HttpHelper session = app.http();
    app.processGGE().fillForm(isProdServer, "SlZap");

    // Прикрепление файла к форме (через elib)
    UpLoadFileData upLoadFileDataBefore = new UpLoadFileData();
    upLoadFileDataBefore.withFilePath(fileAttach);
    UpLoadFileData upLoadFileDataAfter = app.http().upLoadFile(cookies, upLoadFileDataBefore);
    assertThat(upLoadFileDataAfter.getId().equals(""), equalTo(false));

    //Вызов js-script для показа загруженного файла на форме
    app.processGGE().showFile(isProdServer, upLoadFileDataAfter);
    app.processGGE().checkUpLoadFile(isProdServer);

    // Наложение ЭП
//    app.processGGE().writeEP(isProdServer);

    app.processGGE().submitForm();

    TaskProcessData taskProcess = new TaskProcessData();
    taskProcess.withLogin(loginUser);

    app.processGGE().openCardTabWithProcess(isProdServer, taskProcess);
    app.processGGE().openCardProcess(isProdServer, taskProcess);

    String actionWithTask = "Согласовать";

    app.processGGE().readActiveTaskProcessData(isProdServer, taskProcess, actionWithTask);
    app.processGGE().writeActiveTaskProcessDataToJson(isProdServer, taskProcess, fileName);
  }

  class MyIterator implements Iterator<Object[]> {

    private Object[] data;

    private void readFile() throws IOException {
      try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
        String json = "";
        String line = reader.readLine();
        while (line != null) {
          json += line;
          line = reader.readLine();
        }
        Gson gson = new Gson();
        List<TaskProcessData> tasks = gson.fromJson(json, new TypeToken<List<TaskProcessData>>() {
        }.getType()); // List<GroupData>.class

        if (tasks.isEmpty())
          data = null;
        else
          data = new Object[] {tasks.get(0)};
      }
    }

    @Override
    public boolean hasNext() {
      try {
        readFile();
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }

      return data != null;
    }

    @Override
    public Object[] next() {
      return data;
    }
  }

  @DataProvider
  public Iterator<Object[]> processTasksFromJson() throws IOException {
    return new MyIterator();
  }

  @Test(dataProvider = "processTasksFromJson", timeOut = 250000)
  public void testProcessTaskGGE(TaskProcessData taskProcess) throws Exception {
    boolean isProdServer = false;
    boolean isContainsUrlProdServer =
            taskProcess.getUrlCardProcess().contains("https://eis.gge.ru/");
    if (isContainsUrlProdServer) {
      isProdServer = true;
    }

    String loginUser = taskProcess.getLogin();

    String password = "21";
    if (isProdServer) {
      password = "Ukfdujc21";
    }

    assertThat(app.successInit, equalTo(true));
    app.session().loginProcess(isProdServer, loginUser, password, taskProcess.getUrlCardProcess());

    app.processGGE().deleteJsonZapis(fileName);

    app.processGGE().openCardTask(isProdServer, taskProcess);
    app.processGGE().fillFormTask(isProdServer, taskProcess);
    app.processGGE().waitingOpenCardTask(isProdServer, taskProcess);
    app.processGGE().openCardProcessNext(isProdServer, taskProcess);

    String actionWithTask = "Согласовать";

    Boolean isProcessEnd = app.processGGE().readActiveTaskProcessDataNext(isProdServer, taskProcess, actionWithTask);
    if (!isProcessEnd) {
      app.processGGE().writeActiveTaskProcessDataToJson(isProdServer, taskProcess, fileName);
    }
  }

}
