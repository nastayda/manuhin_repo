package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.goToContacts();
        app.getContactHelper().chooseContactDeletion(2);
        app.getContactHelper().submitContactDeletion();
        app.getContactHelper().alertContactDeletion();
    }



}
