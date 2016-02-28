package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;

/**
 * Created by Юрий on 28.02.2016.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModify() {

    app.getContactHelper().editContact();
    app.getContactHelper().fillContactForm(new ContactData("11","21","31","41","51","61","71","81","91","101"));
    app.getNavigationHelper().update();
    app.getNavigationHelper().goToHome();

  }
}
