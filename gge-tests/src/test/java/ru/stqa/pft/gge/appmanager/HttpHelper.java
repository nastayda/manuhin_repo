package ru.stqa.pft.gge.appmanager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.Cookie;
import ru.stqa.pft.gge.model.UpLoadFileData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Set;

/**
 * Created by manuhin on 08.08.2016.
 */
public class HttpHelper {
  private ApplicationManager app;
  private CloseableHttpClient httpClient;

  public HttpHelper(ApplicationManager app){
    this.app = app;
    this.httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
  }

  public UpLoadFileData upLoadFile (Set<Cookie> cookies, UpLoadFileData upLoadFileData)
          throws IOException, URISyntaxException {
    File file = new File(upLoadFileData.getFilePath());

    String auth = "";
    for (Cookie c : cookies) {
      if (c.getName().equals("AUTH")) {
        auth = c.getValue();
        break;
      }
    }

    HttpClient httpclient = new DefaultHttpClient();

    URI url = new URI("https://vm-082-as-gge.mdi.ru/elib/uploadFileForAppletJson.action");
    HttpPost post = new HttpPost(url);
    post.setHeader("Method", "POST");
    post.setHeader("X-File-Name", URLEncoder.encode(file.getName(), "UTF-8"));
    post.setHeader("Cookie", "AUTH=" + auth);

    FileInputStream inputStream = new FileInputStream(file);
    InputStreamEntity reqEntity = new InputStreamEntity(inputStream, -1);
    reqEntity.setChunked(true);
    post.setEntity(reqEntity);

    HttpResponse response = httpclient.execute(post);
    HttpEntity entity = response.getEntity();
    String responseString = EntityUtils.toString(entity, "UTF-8");

//    {"id":"19CC55C0579B4562B228AB960E727F7F","errors":"","name":"30.docx","isSigned":"false","detached":"unknown","success":"true","size":"18.0 КБ"}

    String id = getValueJson("\"id\":\"", "\"", responseString);
    String name = getValueJson("\"name\":\"", "\"", responseString);
    String size = getValueJson("\"size\":\"", "\"", responseString);

    upLoadFileData.withId(id).withName(name).withSize(size);

    return upLoadFileData;
  }

  public String getValueJson(String startOut, String finishOut, String responseString) {
    if (responseString.contains(startOut)) {
      int indexOf = responseString.indexOf(startOut);
      int startIndex = indexOf + startOut.length();
      String ss = responseString.substring(startIndex);
      int finishIndex = ss.indexOf(finishOut);// indexOf + startOut.length();
      String substring = ss.substring(0, finishIndex);
      return substring;
    }

    return "";
  }
}
