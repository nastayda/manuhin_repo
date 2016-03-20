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
    app.goTo().contacts();
    List<ContactData> before = app.contact().list();
    if (app.contact().list().size() == 0) {
      app.goTo().groups();
      if (app.group().list().size() == 0) {
        app.goTo().groups();
        app.group().create(new GroupData().withName("11").withFooter("22").withHeader("33"));
      }
      app.contact().create(new ContactData().withFirstname("11").withLastname("22").
              withAddress("33").withMobile("44").withEmail("11.22@55").withGroup("11"), true);
    }

    int index = before.size() - 1;
    app.contact().modify(index);

    ContactData contact = new ContactData().withId(before.get(index).getId()).withFirstname("11").withLastname("22");
    app.contact().fillForm(contact, false);
    app.contact().submitModificstion();
    app.contact().toContacts();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);

    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before);
  }

}
