package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by Юрий on 05.03.2016.
 */
public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToContacts() {
    click(By.xpath("(.//*[@id='nav']/ul/li[1]/a)"));
  }

  public void submitContactCreation() {
    wd.findElement(By.name("address2")).click();
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void alertContactDeletion() {
    wd.switchTo().alert().accept();
  }

  public void submitContactDeletion() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
  }

  public void chooseContactDeletion(final int i) {
    click(By.xpath("(.//*[@type='checkbox'])[" + i + "]"));
  }

  public void initContactModification() {
    click(By.xpath("(.//*[@id='maintable']/tbody/tr[3]/td[8]/a/img)"));
  }

  public void submitContactModificstion() {
    click(By.xpath("(.//*[@id='content']/form[1]/input[1])"));
  }
}
