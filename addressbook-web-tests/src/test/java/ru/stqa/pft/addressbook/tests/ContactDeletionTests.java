package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().goToContacts();
        List<ContactData> before = app.getContactHelper().getContactList();
        if (! app.getContactHelper().isThereAContact(2)) {
            app.getNavigationHelper().goToGroup();
            if (! app.getGroupHelper().isThereAGroup()) {
                app.getNavigationHelper().goToGroup();
                app.getGroupHelper().createGroup(new GroupData("11", "22", "33"));
            }
            app.getContactHelper().createContact(new ContactData("11", "22", "33", "44", "11.22@55", "11"), true);
        }
        app.getContactHelper().chooseContactDeletion(2);
        app.getContactHelper().submitContactDeletion();
        app.getContactHelper().alertContactDeletion();
        app.getNavigationHelper().goToContacts();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(after, before);

    }
}
