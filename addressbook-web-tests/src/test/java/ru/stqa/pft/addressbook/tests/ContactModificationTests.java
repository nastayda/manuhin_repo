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
import java.security.acl.Group;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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
    Integer contactsCount = beforeContacts.size();
    Integer groupsCount = beforeGroups.size();
    ContactData contactToGroup = app.db().contacts().iterator().next();
    GroupData groupForContact = app.db().groups().iterator().next();

    Boolean foundContactAndGroup = false;

    for (ContactData c : beforeContacts) {
      if (c.getGroups().size() < groupsCount) {
        for (GroupData g : beforeGroups) {
          if (g.getContacts().size() < contactsCount) {
            foundContactAndGroup = true;
            contactToGroup = c;
            groupForContact = g;
            break;
          }
        }
      }
    }

    if (!foundContactAndGroup) {
      app.goTo().groups();

      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
      Date currentTime = new Date();
      String nameNewGroup = sdf.format(currentTime);
      System.out.println("currentTime = " + sdf.format(currentTime));

      GroupData newGroup = new GroupData().withName(sdf.format(currentTime))
              .withFooter("22").withHeader("33");
      app.group().create(newGroup);

      beforeGroups = app.db().groups();

      for (GroupData g : beforeGroups ) {
        if (g.getName().equals(nameNewGroup)) {
          groupForContact = g;
          break;
        }
      }
    }

    Groups beforeContactToGroup = contactToGroup.getGroups();

    addContactToGroup(contactToGroup, groupForContact);

    Contacts afterContacts = app.db().contacts();
    Groups afterContactToGroup = null;

    for (ContactData c : afterContacts ) {
      if (c.getId() == contactToGroup.getId()) {
        afterContactToGroup = c.getGroups();
        break;
      }
    }

    assertThat(beforeContactToGroup, equalTo(afterContactToGroup.without(groupForContact)));
//      verifyContactsInGroupUI(groupForContact);
  }


  @Test
  public void testContactRemoveFromGroup(){

    Contacts beforeContacts = app.db().contacts();
    Groups beforeGroups = app.db().groups();
    ContactData contactToGroup = app.db().contacts().iterator().next();
    GroupData groupForContact = app.db().groups().iterator().next();

    Boolean foundContactAndGroup = false;

    for (ContactData c : beforeContacts) {
      if (c.getGroups().size() > 0) {
        for (GroupData g : beforeGroups) {
          if (g.getContacts().size() > 0) {
            foundContactAndGroup = true;
            contactToGroup = c;
            groupForContact = g;
            break;
          }
        }
      }
    }

    Groups beforeContactToGroup = contactToGroup.getGroups();

    if (!foundContactAndGroup) {
      addContactToGroup(contactToGroup, groupForContact);
      beforeContacts = app.db().contacts();

      for (ContactData c : beforeContacts ) {
        if (c.getId() == contactToGroup.getId()) {
          beforeContactToGroup = c.getGroups();
          break;
        }
      }
    }

    removeContactFromGroup(contactToGroup, groupForContact);

    Contacts afterContacts = app.db().contacts();
    Groups afterContactToGroup = null;

    for (ContactData c : afterContacts ) {
      if (c.getId() == contactToGroup.getId()) {
        afterContactToGroup = c.getGroups();
        break;
      }
    }

    assertThat(beforeContactToGroup, equalTo(afterContactToGroup.withAdded(groupForContact)));
//      verifyContactsInGroupUI(groupForContact);
  }

  public void removeContactFromGroup(ContactData contact, GroupData group) {
    app.contact().groupButton(group);
    app.contact().removeFromGroupByContactId(contact.getId());
    app.goTo().contacts();
  }

  public void addContactToGroup(ContactData contact, GroupData group) {
    app.goTo().contacts();
    app.contact().addToGroup(contact, group);
    app.contact().groupButton(group);
  }
}
