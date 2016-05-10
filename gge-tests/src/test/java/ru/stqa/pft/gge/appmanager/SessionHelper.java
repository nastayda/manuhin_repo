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

  public void login(String username, String password, String baseUrl) {
    wd.get(baseUrl);
    click(By.id("content"));
    type(By.id("username"), username);
    type(By.id("password"), password);
    click(By.id("submitBtn"));
  }
}
