package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Юрий on 05.03.2016.
 */
public class SessionHelper extends HelperBase {

  public SessionHelper(WebDriver wd) {
    super(wd);
  }

  public void loginGGEMGE(String username, String password, String baseUrl) {
    //подключение и отключение админа

//    wd.get("https://expertiza-test.mos.ru/portal/logout.action");
//    click(By.id("content"));
//    type(By.id("username"), "VASILENKORN");
//    type(By.id("password"), password);
//    click(By.id("submitBtn"));
//    wd.get("https://expertiza-test.mos.ru/portal/activeUsers.action?logoutUser=SADOVIN_AL"); // на разработческом
//    //wd.get("https://expertiza-tech.mos.ru/portal/activeUsers.action?logoutUser=AdminMGE");  //на предбое
//    wd.get("https://expertiza-test.mos.ru/portal/logout.action");

    //завершение отключения админа

    wd.get(baseUrl);
    click(By.id("content"));
    type(By.id("username"), username);
    type(By.id("password"), password);
    click(By.id("submitBtn"));
  }

  public void loginProcess(Boolean isProdServer, String username, String password, String baseUrl)
          throws InterruptedException {
    wd.get(baseUrl);
    waitElement(By.id("username"));
    type(By.id("username"), username);
    waitElement(By.id("password"));
    type(By.id("password"), password);
    waitElement(By.id("submitBtn"));
    click(By.id("submitBtn"));
  }
  public void logout() {
    //waitElement(By.id("logoutMethod"));
    //click(By.id("logoutMethod"));
    wd.get("/portal/logout.action");
  }
  public void loginUGD(String username, String password,
                       Boolean userIsChinovnik, String baseUrl,
                       String listVitrinasUrl) {
    wd.get(baseUrl);
    if (userIsChinovnik) {
      click(By.xpath("(//div[@class=\"box-inline\"]/strong[1]/../a)[1]"));
      type(By.id("Email"), username);
      type(By.id("Passwd"), password);
      click(By.id("signIn"));
      wd.get(listVitrinasUrl);
    } else {
      click(By.xpath("(//div[@class=\"box-inline\"]/strong[1]/../a)[2]"));
    }
  }
}
