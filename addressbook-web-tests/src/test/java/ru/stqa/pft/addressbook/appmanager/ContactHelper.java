package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

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
    click(By.name("submit"));
   // wd.findElement(By.name("submit")).click();
  }

  public void fillForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("middlename"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    //attach(By.name("photo"), contactData.getPhoto());

    if (creation) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
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
      String allphones = cells.get(5).getText();
      String address = cells.get(3).getText();
      String allemails = cells.get(4).getText();
      int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));

      contactCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname)
              .withAllphones(allphones).withAddress(address).withAllmails(allemails));
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

  public ContactData infoFromEditForm(ContactData contact) {
    initModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String middlename = wd.findElement(By.name("middlename")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String address = wd.findElement(By.xpath(".//*[@id='content']//textarea[1]")).getText();
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");

    wd.navigate().back();
    return new ContactData().
            withId(contact.getId())
            .withFirstname(firstname).withLastname(lastname).withMiddlename(middlename)
            .withMobilePhone(mobile).withHomePhone(home).withWorkPhone(work)
            .withAddress(address)
            .withEmail(email).withEmail2(email2).withEmail3(email3);
  }

  public String infoFromDetails(ContactData contact) {
    initDetailsById(contact.getId());
    String alldetails = wd.findElement(By.xpath("//*[@id='content']")).getText();
    String minusdetails = wd.findElement(By.xpath("//*[@id='content']/i")).getText();
    String neaddetails = alldetails.replace(minusdetails,"");
    String cleardetails = neaddetails.replaceAll("\\s*\\(www\\..*?\\)", "");

    return cleardetails;
  }

  private void initDetailsById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='view.php?id=%s']", id))).click();
  }

  public void toGroupButton() {
    click(By.xpath("//*[@id='content']/form[2]/div[4]/input"));

  }

  public void addToGroup(ContactData contact, GroupData group) {
    selectById(contact.getId());
    new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(group.getName());
    toGroupButton();
    contactCache = null;
    toContacts();
  }
}
