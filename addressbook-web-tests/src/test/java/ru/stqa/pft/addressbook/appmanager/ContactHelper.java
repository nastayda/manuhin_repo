package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

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

  public void modify(int index) {
    wd.findElements(By.xpath("(.//*[@id='maintable']/tbody/tr/td[8]/a/img)")).get(index).click();
  }

  public void submitModificstion() {
    click(By.xpath("(.//*[@id='content']/form[1]/input[1])"));
  }

  public void create(ContactData contactData, boolean creation) {
    initCreation();
    fillForm(contactData, creation);
    submitCreation();
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
}
