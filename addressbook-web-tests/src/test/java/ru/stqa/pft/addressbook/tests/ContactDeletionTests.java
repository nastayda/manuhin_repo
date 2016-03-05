package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.goToContacts();
        app.chooseContactDeletion(2);
        app.submitContactDeletion();
        app.alertContactDeletion();
    }



}
