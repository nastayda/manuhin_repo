package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.navigateNewContact();
    app.fillContactForm(new ContactData("1","2","3","4","5","6","7","8","9","10"));
    app.submit();
    app.goToHome();
  }

}
