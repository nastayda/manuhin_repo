package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().goToContacts();
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactForm(new ContactData("11", "22", "33", "44", "11.22@55", "11"), true);
        app.getContactHelper().submitContactCreation();
        app.getContactHelper().returnToContacts();
    }


}
