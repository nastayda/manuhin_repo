package ru.stqa.pft.gge.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.Cookie;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.gge.appmanager.HttpHelper;
import ru.stqa.pft.gge.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by manuhin on 21.07.2016.
 */
public class ProcessSoglSluzhZapiskiTests extends TestBase {

  String fileName = "src/test/resources/processSoglSluzhZapiski_vm-082.json";
  String fileProcessTestCases = "src/test/resources/processSoglSluzhZapiski_test-cases_vm-082.json";
  String fileProcessTestCase = "src/test/resources/processSoglSluzhZapiski_actite-test-case_vm-082.json";
  String dbserver = "vm-082-oradb-gge.mdi.ru";
  String port = "1521";
  String sid = "db";
  String userDB = "galactica";
  String passwordDB = "galactica";

  @Test
  public void testInitFile() throws IOException {
    app.processGGE().deleteJsonZapis(fileName);
  }

  @DataProvider
  public Iterator<Object[]> testCasesProcessFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(
            new File(fileProcessTestCases)))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ProcessTestCases> processTestCases = gson.fromJson(json, new TypeToken<List<ProcessTestCases>>(){}.getType()); // List<ProcessTestCases>.class
      return processTestCases.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "testCasesProcessFromJson")
  public void testProcSoglSluzhZapCreateSlZap(ProcessTestCases processTestCase) throws Exception {
    String baseUrl = "https://vm-082-as-gge.mdi.ru/";
    String loginUser = "e.mironova";
    String urlAD = baseUrl + "portal/tabInfo.action?tab=OFFICEWORK#/tree::rel=4/" +
            "filter::id=E93EC358A69745469F6266C0275F7907/vitrina::viewId=E93EC358A69745469F6266C0275F7907" +
            "&offset=0&limit=50";
    String fileAttach = "c:/Downloads/30.docx";
    boolean isProdServer = false;

    // Запись тест-кейса прохождения БП в спец файл
    app.processGGE().writeActiveProcessDataToJson(isProdServer, processTestCase, fileProcessTestCase);

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

    assertThat(cookies.size() > 0, equalTo(true));

    assertThat(app.processGGE().razdelUrl(isProdServer, urlAD),
            equalTo(true));
    assertThat(app.processGGE().selectTypeDoc(isProdServer,
            "//span[contains(text(),'Внутренние документы')]",
            "//label[@for='item_SLZP_2']/input",
            "CREST_CORR_LIST",
            "formId=ADM_INNER"), equalTo(true));

    HttpHelper session = app.http();
    app.processGGE().fillForm(isProdServer, "SlZap");

    // Проверка, что кнопка загрузки видима и активна
    assertThat(app.processGGE().checkEnableButtonUpLoad(), equalTo(true));

    // Прикрепление файла к форме (через elib)
    UpLoadFileData upLoadFileDataBefore = new UpLoadFileData();
    upLoadFileDataBefore.withFilePath(fileAttach);
    UpLoadFileData upLoadFileDataAfter = app.http().upLoadFile(cookies, upLoadFileDataBefore);
    assertThat(upLoadFileDataAfter.getId().equals(""), equalTo(false));

    //Вызов js-script для показа загруженного файла на форме
    app.processGGE().showFile(isProdServer, upLoadFileDataAfter);
    assertThat(app.processGGE().checkUpLoadFile(isProdServer), equalTo(true));

    // Наложение ЭП
    assertThat(app.processGGE().writeEP(isProdServer), equalTo(true));

    // Проверка ЭП
    assertThat(app.processGGE().checkEP(isProdServer), equalTo(true));

    app.processGGE().submitForm(isProdServer);

    TaskProcessData taskProcess = new TaskProcessData();

    // В задачу процесса вносим вариант прохождения процесса
    taskProcess.withProcessTestCase(processTestCase.getProcessTestCase());

    assertThat(app.processGGE().openCardTabWithProcess(isProdServer,
            taskProcess,
            "//span[contains(text(),'Служебные записки ЦА')]",
            "(//ul[@id='tabs_group']//a)[2]"), equalTo(true));
    assertThat(app.processGGE().openCardProcess(isProdServer, taskProcess), equalTo(true));

    String actionWithTask = "Согласовать";

    DbConnect dbConnect = new DbConnect()
            .withDbserver(dbserver)
            .withPort(port)
            .withSid(sid)
            .withUser(userDB)
            .withPassword(passwordDB);
    app.processGGE().readActiveTaskProcessData(isProdServer, taskProcess, dbConnect);
    List<TaskProcessData> taskProcessDatas = app.processGGE().readActiveTaskProcessDataFromJson(fileName);
    app.processGGE().writeActiveTaskProcessDataToJson(isProdServer, taskProcessDatas, taskProcess, fileName);
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

//    app.processGGE().deleteJsonZapis(fileName);

    int numberActiveTaskFromProcessCard = app.processGGE().getNumberActiveTask(isProdServer);
    assertThat(app.processGGE().openCardTask(isProdServer, taskProcess), equalTo(true));

    assertThat(app.processGGE().fillFormTask(isProdServer, taskProcess, numberActiveTaskFromProcessCard)
            , equalTo(true));
    assertThat(app.processGGE().waitingOpenCardTask(isProdServer, taskProcess), equalTo(true));
    assertThat(app.processGGE().openCardProcessNext(isProdServer, taskProcess), equalTo(true));

    DbConnect dbConnect = new DbConnect()
            .withDbserver(dbserver)
            .withPort(port)
            .withSid(sid)
            .withUser(userDB)
            .withPassword(passwordDB);
    Boolean isProcessEnd = app.processGGE().readActiveTaskProcessDataNext(isProdServer, taskProcess,
            dbConnect);
    List<TaskProcessData> taskProcessDatas = app.processGGE().readActiveTaskProcessDataFromJson(fileName);
    if (isProcessEnd) {
      app.processGGE().deleteFirstTaskProcessDataInJson(isProdServer, taskProcessDatas, fileName);
    } else {
      app.processGGE().changeFirstTaskProcessDataInJson(isProdServer, taskProcessDatas, taskProcess, fileName);
    }
  }
}
