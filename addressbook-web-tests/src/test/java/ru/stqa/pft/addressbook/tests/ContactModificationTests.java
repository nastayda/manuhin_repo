package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Юрий on 05.03.2016.
 */
public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void preConditions(){
    if (app.db().groups().size() == 0){
      app.goTo().groups();
      app.group().create( new GroupData()
              .withName("11").withFooter("22").withHeader("33"));
    }
    app.goTo().contacts();
    if (app.db().contacts().size() == 0) {
      app.goTo().contacts();
      app.contact().create(new ContactData()
              .withFirstname("11").withLastname("22").withMiddlename("33")
              .withAddress("a").withMobilePhone("+7123").withHomePhone("+7234").withWorkPhone("+7345")
              .withEmail("1@2").withEmail2("2@3").withEmail3("3@4"), false);
    }
  }

  @Test
  public void testContactModification(){
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
    Contacts before = app.db().contacts();

    ContactData modifiedContact = before.iterator().next();

    ContactData contact = new ContactData().
            withId(modifiedContact.getId()).withFirstname("11").withLastname("22").withMiddlename("33")
            .withAddress("a").withMobilePhone("+7123").withHomePhone("+7234").withWorkPhone("+7345")
            .withEmail("1@2").withEmail2("2@3").withEmail3("3@4");

    app.goTo().contacts();
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));

    Contacts after = app.db().contacts();

    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI();
  }

  @Test
  public void testContactToGroup(){

    Contacts beforeContacts = app.db().contacts();
    Groups beforeGroups = app.db().groups();
    ContactData contactToGroup = app.db().contacts().iterator().next();
    GroupData groupForContact = app.db().groups().iterator().next();

    Integer contactID = 0;
    Integer groupID = 0;

    for (ContactData contactInFor : beforeContacts ) {
      if (contactInFor.getGroups().size() < app.db().groups().size()) {
        for (GroupData groupInFor : beforeGroups) {
          if (groupInFor.getContacts().size() < app.db().contacts().size()) {
            contactID = contactInFor.getId();
            groupID = groupInFor.getId();
            break;
          }
        }
      }
    }

    if (contactID == 0 && groupID == 0) {
      app.goTo().groups();

      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
      Date currentTime = new Date();
      System.out.println("currentTime = " + sdf.format(currentTime));

      GroupData newGroup = new GroupData().withName(sdf.format(currentTime))
              .withFooter("22").withHeader("33");
      app.group().create(newGroup);
      ContactData contactAny = beforeContacts.iterator().next();

      contactID = contactAny.getId();
      groupID = newGroup.getId();
    }

    Contacts beforeContactsInGroupBD = new Contacts(app.db().groups().iterator().next()
            .withId(groupID).getContacts());

    app.contact().addToGroup(contactToGroup.withId(contactID), groupForContact.withId(groupID));
    app.goTo().contacts();
    app.contact().groupButton(groupForContact.withId(groupID));

    Contacts afterContactsInGroupBD = new Contacts(app.db().groups().iterator().next()
            .withId(contactID).getContacts());

    assertThat(beforeContactsInGroupBD, equalTo(afterContactsInGroupBD.without(contactToGroup.withId(contactID))));

    verifyContactsInGroupUI(groupForContact.withId(groupID));
  }
}
