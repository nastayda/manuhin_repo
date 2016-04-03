package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Юрий on 05.03.2016.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification(){
    Contacts before = app.db().contacts();
    if (app.db().contacts().size() == 0) {
      if (app.db().groups().size() == 0) {
        app.goTo().groups();
        app.group().create(new GroupData().withName("11").withFooter("22").withHeader("33"));
      }
      app.goTo().contacts();
      app.contact().create(new ContactData().withFirstname("11").withLastname("22").
              withAddress("33").withMobilePhone("44").withEmail("11.22@55").withGroup("11"), true);
    }

    ContactData modifiedContact = before.iterator().next();

    ContactData contact = new ContactData().
            withId(modifiedContact.getId()).withFirstname("11").withLastname("22");

    app.goTo().contacts();
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));

    Contacts after = app.db().contacts();

    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }


}
