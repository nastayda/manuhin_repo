package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by Юрий on 05.03.2016.
 */
public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void goToGroup() {
    click(By.id("content"));
  }

  public void goToContacts() {
    click(By.cssSelector("body"));
    click(By.linkText("home"));
  }
}
