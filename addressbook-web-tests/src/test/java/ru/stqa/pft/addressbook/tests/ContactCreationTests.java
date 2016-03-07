package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().goToGroup();
        if (! app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("11", "22", "33"));
        }
        app.getNavigationHelper().goToContacts();
        app.getContactHelper().createContact(new ContactData("11", "22", "33", "44", "11.22@55", "11"), true);
    }



}
