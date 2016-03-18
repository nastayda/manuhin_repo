package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.goTo().goToContacts();
        List<ContactData> before = app.getContactHelper().getContactList();
        int indexOfDeletion = before.size() - 1;
        if (! app.getContactHelper().isThereAContact(indexOfDeletion)) {
            app.goTo().groups();
            if (! app.group().isThereAGroup()) {
                app.goTo().groups();
                app.group().create(new GroupData("11", "22", "33"));
            }
            app.getContactHelper().createContact(new ContactData("11", "22", "33", "44", "11.22@55", "11"), true);
        }
        app.getContactHelper().chooseContactDeletion(indexOfDeletion + 1);
        app.getContactHelper().submitContactDeletion();
        app.getContactHelper().alertContactDeletion();
        app.goTo().goToContacts();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), indexOfDeletion);

        before.remove(indexOfDeletion);
        Assert.assertEquals(new HashSet<Object>(after), new HashSet<Object>(before));

    }
}
