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


  public void logout(String domen, String password, String userlogout, String username) {
    //подключение и отключение админа
    String authS1 = domen + "/authS1/login.action";
    wd.get(authS1);
    click(By.id("content"));
    type(By.id("username"), userlogout); //"VASILENKORN" админ разлогинивающий пользователя, который будет тестировать
    type(By.id("password"), password);
    click(By.id("submitBtn"));
//    wd.get(domen +"/portal/activeUsers.action?logoutUser=SADOVIN_AL"); // на разработческом
    String logoutUser = domen+"/portal/activeUsers.action?logoutUser="+username;
    wd.get(logoutUser);  //на предбое
    String logouturl = domen +"/portal/logout.action";
    wd.get(logouturl);
    //завершение отключения админа
  }


  public void loginGGEMGE(String username, String password, String baseUrl) {

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
  public void logoutold() {
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
