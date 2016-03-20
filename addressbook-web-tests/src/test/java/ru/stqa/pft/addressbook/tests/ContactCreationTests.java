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
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData().withName("11").withFooter("22").withHeader("33"));
    }
  }

  @Test
  public void testContactCreation() {
    app.goTo().contacts();
    List<ContactData> before = app.contact().list();

    int index = before.size() - 1;
    ContactData contact = new ContactData().withId(before.get(index).getId()).withFirstname("11").withLastname("22").
            withAddress("33").withMobile("44").withEmail("11.22@55").withGroup("11");
    app.contact().create(contact, true);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(contact);

    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before);

  }



}
