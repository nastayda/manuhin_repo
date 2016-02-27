package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    url();
    fillContactForm(new ContactData("1","2","3","4","5","6","7","8","9","10"));
    submit();
    home();
  }

}
