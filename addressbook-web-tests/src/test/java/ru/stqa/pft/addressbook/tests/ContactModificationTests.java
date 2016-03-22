package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Юрий on 05.03.2016.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification(){
    app.goTo().contacts();
    Contacts before = app.contact().all();
    if (app.contact().all().size() == 0) {
      app.goTo().groups();
      if (app.group().all().size() == 0) {
        app.goTo().groups();
        app.group().create(new GroupData().withName("11").withFooter("22").withHeader("33"));
      }
      app.contact().create(new ContactData().withFirstname("11").withLastname("22").
              withAddress("33").withMobile("44").withEmail("11.22@55").withGroup("11"), true);
    }

    ContactData modifiedContact = before.iterator().next();

    ContactData contact = new ContactData().
            withId(modifiedContact.getId()).withFirstname("11").withLastname("22");

    app.contact().modify(contact);
    Contacts after = app.contact().all();

    assertThat(after.size(), equalTo(before.size()));

    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }


}
