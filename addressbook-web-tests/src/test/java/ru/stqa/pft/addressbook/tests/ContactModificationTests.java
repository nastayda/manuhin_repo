package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Юрий on 05.03.2016.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification(){
    app.getNavigationHelper().goToContacts();
    List<ContactData> before = app.getContactHelper().getContactList();
    if (! app.getContactHelper().isThereAContact(2)) {
      app.getNavigationHelper().goToGroup();
      if (! app.getGroupHelper().isThereAGroup()) {
        app.getNavigationHelper().goToGroup();
        app.getGroupHelper().createGroup(new GroupData("11", "22", "33"));
      }
      app.getContactHelper().createContact(new ContactData("11", "22", "33", "44", "11.22@55", "11"), true);
    }

    int index = before.size() - 1;
    app.getContactHelper().initContactModification(index);

    ContactData contact = new ContactData(before.get(index).getId(), "11", "22", null, null, null, null);
    app.getContactHelper().fillContactForm(contact, false);
    app.getContactHelper().submitContactModificstion();
    app.getContactHelper().returnToContacts();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);

    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before);
  }

}
