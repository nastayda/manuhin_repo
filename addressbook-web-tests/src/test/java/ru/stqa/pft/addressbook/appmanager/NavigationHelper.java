package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by Юрий on 05.03.2016.
 */
public class NavigationHelper {
  private FirefoxDriver wd;

  public NavigationHelper(FirefoxDriver wd) {
    this.wd = wd;
  }

  public void goToGroup() {
    wd.findElement(By.id("content")).click();
  }

  public void goToContacts() {
    wd.findElement(By.cssSelector("body")).click();
    wd.findElement(By.linkText("home")).click();
  }
}
