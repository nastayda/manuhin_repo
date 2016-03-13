package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

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
    app.getContactHelper().initContactModification(before.size() - 1);
    app.getContactHelper().fillContactForm(new ContactData("11", null, null, null, null, null), false);
    app.getContactHelper().submitContactModificstion();
    app.getContactHelper().returnToContacts();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

  }

}
