package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Юрий on 05.03.2016.
 */
public class ContactHelper extends HelperBase {

  private WebElement locator;

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void toContacts() {
    click(By.xpath("(.//*[@id='nav']/ul/li[1]/a)"));
  }

  public void submitCreation() {
    wd.findElement(By.name("submit")).click();
  }

  public void fillForm(ContactData contactData, boolean creation) {
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

  public void initCreation() {
    click(By.linkText("add new"));
  }

  public void alertDeletion() {
    wd.switchTo().alert().accept();
  }

  public void submitDeletion() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
  }

  public void select(final int i) {
    click(By.xpath("(.//*[@type='checkbox'])[" + i + "]"));
  }

  public void initModificationById(int id) {
   // WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
   // WebElement row = checkbox.findElement(By.xpath("./../.."));
   // List<WebElement> cells = row.findElements(By.tagName("td"));
   // cells.get(7).findElement(By.tagName("a")).click();
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

  public void submitModificstion() {
    click(By.xpath("(.//*[@id='content']/form[1]/input[1])"));
  }

  public void create(ContactData contactData, boolean creation) {
    initCreation();
    fillForm(contactData, creation);
    submitCreation();
    contactCache = null;
    toContacts();
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> rows = wd.findElements(By.name("entry"));
    for (WebElement row: rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));

      ContactData contact = new ContactData().withId(id).withFirstname(firstname).withLastname(lastname);
      contacts.add(contact);
    }
    return contacts;
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }

    contactCache = new Contacts();
    List<WebElement> rows = wd.findElements(By.name("entry"));
    for (WebElement row: rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));

      contactCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
    }
    return new Contacts(contactCache);
  }

  public void delete(ContactData contact) {
    selectById(contact.getId());
    submitDeletion();
    contactCache = null;
    alertDeletion();
  }

  private void selectById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void modify(ContactData contact) {
    initModificationById(contact.getId());
    fillForm(contact, false);
    submitModificstion();
    contactCache = null;
    toContacts();
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }
}
