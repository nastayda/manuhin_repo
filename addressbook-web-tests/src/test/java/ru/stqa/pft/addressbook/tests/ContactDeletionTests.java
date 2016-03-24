package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  @Test(enabled = false)
  public void testContactDeletion() {
    app.goTo().contacts();
    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();

    if (app.contact().all().size() == 0) {
      app.goTo().groups();
      if (app.group().all().size() == 0) {
        app.goTo().groups();
        app.group().create(new GroupData().withName("11").withFooter("22").withHeader("33"));
      }
      app.contact().create(new ContactData().withFirstname("11").withLastname("22").
              withAddress("33").withMobilePhone("44").withEmail("11.22@55").withGroup("11"), true);
    }
    app.contact().delete(deletedContact);
    app.goTo().contacts();
    assertThat(app.contact().count(), equalTo(before.size() - 1));

    Contacts after = app.contact().all();

    assertThat(after, equalTo(before.without(deletedContact)));
}

}
