package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Юрий on 28.02.2016.
 */
public class NavigationHelper {
  private WebDriver driver;

  public NavigationHelper(WebDriver driver) {
    this.driver = driver;
  }

  public void goToGroup() {
    driver.findElement(By.linkText("group page")).click();
  }

  public void goToHome() {
    driver.findElement(By.linkText("home page")).click();
  }
}
