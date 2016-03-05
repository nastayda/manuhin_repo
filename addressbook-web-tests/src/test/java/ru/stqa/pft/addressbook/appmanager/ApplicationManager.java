package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.concurrent.TimeUnit;

/**
 * Created by Юрий on 04.03.2016.
 */
public class ApplicationManager {

  FirefoxDriver wd;


  private GroupHelper groupHelper;

  public static boolean isAlertPresent(FirefoxDriver wd) {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

  public void init() {
    wd = new FirefoxDriver();
    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    wd.get("http://localhost/addressbook/group.php");
    groupHelper = new GroupHelper(wd);
    login("admin", "secret");
  }

  private void login(String username, String password) {
  wd.findElement(By.id("LoginForm")).click();
  wd.findElement(By.name("user")).click();
  wd.findElement(By.name("user")).clear();
  wd.findElement(By.name("user")).sendKeys(username);
  wd.findElement(By.name("pass")).click();
  wd.findElement(By.name("pass")).clear();
  wd.findElement(By.name("pass")).sendKeys(password);
  wd.findElement(By.xpath("//form[@id='LoginForm']/input[3]")).click();
}

  public void goToGroup() {
    wd.findElement(By.id("content")).click();
  }

  public void stop() {
    wd.quit();
  }

  public void returnToContacts() {
    wd.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
  }

  public void submitContactCreation() {
    wd.findElement(By.name("address2")).click();
  }

  public void fillContactForm(ContactData contactData) {
    wd.findElement(By.name("firstname")).click();
    wd.findElement(By.name("firstname")).clear();
    wd.findElement(By.name("firstname")).sendKeys(contactData.getFirstname());
    wd.findElement(By.name("middlename")).click();
    wd.findElement(By.name("lastname")).click();
    wd.findElement(By.name("lastname")).clear();
    wd.findElement(By.name("lastname")).sendKeys(contactData.getLastname());
    wd.findElement(By.name("nickname")).click();
    wd.findElement(By.name("address")).click();
    wd.findElement(By.name("address")).clear();
    wd.findElement(By.name("address")).sendKeys(contactData.getAddress());
    wd.findElement(By.name("mobile")).click();
    wd.findElement(By.name("mobile")).clear();
    wd.findElement(By.name("mobile")).sendKeys(contactData.getMobile());
    wd.findElement(By.name("email")).click();
    wd.findElement(By.name("email")).clear();
    wd.findElement(By.name("email")).sendKeys(contactData.getEmail());
  }

  public void initContactCreation() {
    wd.findElement(By.linkText("add new")).click();
  }

  public void alertContactDeletion() {
    wd.switchTo().alert().accept();
  }

  public void submitContactDeletion() {
    wd.findElement(By.xpath("//div[@id='content']/form[2]/div[2]/input")).click();
  }

  public void chooseContactDeletion(final int i) {
      wd.findElement(By.xpath("(.//*[@type='checkbox'])[" + i + "]")).click();
  }

  public void goToContacts() {
    wd.findElement(By.cssSelector("body")).click();
    wd.findElement(By.linkText("home")).click();
  }


  public GroupHelper getGroupHelper() {
    return groupHelper;
  }
}
