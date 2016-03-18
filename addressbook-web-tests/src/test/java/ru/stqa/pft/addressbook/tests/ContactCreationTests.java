package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @BeforeMethod
  public void preConditions() {
    app.goTo().groups();
    if (! app.group().isThereAGroup()) {
      app.group().create(new GroupData("11", "22", "33"));
    }
  }

  @Test
  public void testContactCreation() {
    app.goTo().goToContacts();
    List<ContactData> before = app.getContactHelper().getContactList();

    ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "11", "22", "33", "44", "11.22@55", "11");
    app.getContactHelper().createContact(contact, true);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(contact);

    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before);

  }



}
