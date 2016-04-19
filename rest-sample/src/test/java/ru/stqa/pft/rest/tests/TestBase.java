package ru.stqa.pft.rest.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.rest.appmanager.ApplicationManager;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Юрий on 09.04.2016.
 */
public class TestBase {

  protected static final ApplicationManager app =
          new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX)) ;

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws IOException {
    app.stop();
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      System.out.println("Ignored because of issue " + issueId);
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  private boolean isIssueOpen(int issueId) throws IOException {
    boolean isIssueOpen = true;
    Set<Issue> issues = getIssue();
    Issue anyIssue = issues.iterator().next();
    String status = "";
    for (Issue issue : issues) {
      if (issue.getId() == issueId) {
        status = getIssueStatus(issueId);
        if (status.equals("resolved") || status.equals("closed"))
        isIssueOpen = false;
        break;
      }
    }
    return isIssueOpen;
  }

  private String getIssueStatus(int issueId) throws IOException {
    String requestString = "http://demo.bugify.com/api/issues/" + issueId + ".json";
    String json = getExecutor().execute(Request.Get(requestString))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issue = parsed.getAsJsonObject().get("issues");
    String status = parsed.getAsJsonObject().get("state_name").getAsString();

    return status;
  }

  Set<Issue> getIssue() throws IOException {
    String json = getExecutor().execute(Request.Get("http://demo.bugify.com/api/issues.json"))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

  private Executor getExecutor() {
    CloseableHttpClient httpClient = HttpClients.custom()
                .setProxy(new HttpHost("proxy.mdi.ru", 3128))
                .setRedirectStrategy(new LaxRedirectStrategy()).build();
    return Executor.newInstance(httpClient).auth("LSGjeU4yP1X493ud1hNniA==", "");
  }

  int createIssue(Issue newIssue) throws IOException {
    String json = getExecutor().execute(Request.Post("http://demo.bugify.com/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                      new BasicNameValuePair("description", newIssue.getDescription())))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

}
