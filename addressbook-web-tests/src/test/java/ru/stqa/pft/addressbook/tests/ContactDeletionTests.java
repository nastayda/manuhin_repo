package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.Set;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    app.goTo().contacts();
    Set<ContactData> before = app.contact().all();
    ContactData deletedContact = before.iterator().next();

    if (app.contact().all().size() == 0) {
      app.goTo().groups();
      if (app.group().all().size() == 0) {
        app.goTo().groups();
        app.group().create(new GroupData().withName("11").withFooter("22").withHeader("33"));
      }
      app.contact().create(new ContactData().withFirstname("11").withLastname("22").
              withAddress("33").withMobile("44").withEmail("11.22@55").withGroup("11"), true);
    }
    app.contact().delete(deletedContact);
    app.goTo().contacts();

    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(deletedContact);
    Assert.assertEquals(new HashSet<Object>(after), new HashSet<Object>(before));

  }

}
