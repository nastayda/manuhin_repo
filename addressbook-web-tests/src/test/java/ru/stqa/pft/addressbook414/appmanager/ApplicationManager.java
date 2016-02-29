package ru.stqa.pft.addressbook414.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

/**
 * Created by Юрий on 27.02.2016.
 */
public class ApplicationManager {
  WebDriver driver;

  private ContactHelper contactHelper;
  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;
  private String baseUrl;
  private String bd;
  private StringBuffer verificationErrors = new StringBuffer();

  public void start() {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost/";
    bd = "/addressbookv4.1.4/";
    groupHelper = new GroupHelper(driver);
    navigationHelper = new NavigationHelper(driver);
    contactHelper = new ContactHelper(driver);
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    url();
  }

  public void url() {
    driver.get(baseUrl + bd);
  }

  public void stop() {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }


  public GroupHelper getGroupHelper() {
    return groupHelper;
  }

  public NavigationHelper getNavigationHelper() {
    return navigationHelper;
  }

  public ContactHelper getContactHelper() {
    return contactHelper;
  }
}
