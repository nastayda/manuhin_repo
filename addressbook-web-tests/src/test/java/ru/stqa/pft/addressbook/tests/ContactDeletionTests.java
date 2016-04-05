package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  @Test(enabled = true)
  public void testContactDeletion() {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();

    if (app.db().contacts().size() == 0) {
      if (app.db().groups().size() == 0) {
        app.goTo().groups();
        app.group().create(new GroupData().withName("11").withFooter("22").withHeader("33"));
      }
      app.goTo().contacts();
      app.contact().create(new ContactData().withFirstname("11").withLastname("22").withMiddlename("33")
              .withAddress("a").withMobilePhone("+7123").withHomePhone("+7234").withWorkPhone("+7345")
              .withEmail("1@2").withEmail2("2@3").withEmail3("3@4"), true);
    }

    app.goTo().contacts();
    app.contact().delete(deletedContact);
    app.goTo().contacts();
    assertThat(app.contact().count(), equalTo(before.size() - 1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(deletedContact)));
    verifyContactListInUI();
}

}
