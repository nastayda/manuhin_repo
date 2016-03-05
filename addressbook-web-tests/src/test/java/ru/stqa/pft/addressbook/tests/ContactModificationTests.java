package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by Юрий on 05.03.2016.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification(){
    app.getNavigationHelper().goToContacts();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("111", "221", "331", "441", "11.22@551"));
    app.getContactHelper().submitContactModificstion();
    app.getContactHelper().returnToContacts();
  }

}
