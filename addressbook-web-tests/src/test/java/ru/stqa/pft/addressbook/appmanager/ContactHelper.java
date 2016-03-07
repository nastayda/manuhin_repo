package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by Юрий on 05.03.2016.
 */
public class ContactHelper extends HelperBase {

  private WebElement locator;

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToContacts() {
    click(By.xpath("(.//*[@id='nav']/ul/li[1]/a)"));
  }

  public void submitContactCreation() {
    wd.findElement(By.name("submit")).click();
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
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
    click(By.xpath("(.//*[@id='maintable']/tbody/tr[2]/td[8]/a/img)"));
  }

  public void submitContactModificstion() {
    click(By.xpath("(.//*[@id='content']/form[1]/input[1])"));
  }

  public boolean isThereAContact(final int i) {
    return isElementPresent(By.xpath("(.//*[@type='checkbox'])[" + i + "]"));
  }

  public void createContact(ContactData contactData, boolean creation) {
    initContactCreation();
    fillContactForm(contactData, creation);
    submitContactCreation();
    returnToContacts();
  }

}
