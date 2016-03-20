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
        app.goTo().contacts();
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        if (app.contact().list().size() == 0) {
            app.goTo().groups();
            if (app.group().list().size() == 0) {
                app.goTo().groups();
                app.group().create(new GroupData().withName("11").withFooter("22").withHeader("33"));
            }
            app.contact().create(new ContactData().withFirstname("11").withLastname("22").
                    withAddress("33").withMobile("44").withEmail("11.22@55").withGroup("11"), true);
        }
        app.contact().select(index + 1);
        app.contact().submitDeletion();
        app.contact().alertDeletion();
        app.goTo().contacts();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), index);

        before.remove(index);
        Assert.assertEquals(new HashSet<Object>(after), new HashSet<Object>(before));

    }
}
