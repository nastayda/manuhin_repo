package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @BeforeMethod
  public void preConditions() {
    app.goTo().groups();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("11").withFooter("22").withHeader("33"));
    }
  }

  @Test
  public void testContactCreation() {
    app.goTo().contacts();
    Contacts before = app.contact().all();

    ContactData contact = new ContactData().withFirstname("11").withLastname("22").
            withAddress("33").withMobile("44").withEmail("11.22@55").withGroup("11");
    app.contact().create(contact, true);
    Contacts after = app.contact().all();

    assertThat(after.size(), equalTo(before.size() + 1));

    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> (c.getId())).max().getAsInt()))));
  }
}
