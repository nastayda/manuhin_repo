package ru.stqa.pft.addressbook414.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook414.model.TestBase;

/**
 * Created by Юрий on 28.02.2016.
 */
public class ContactDeletingTests extends TestBase {

  @Test
  public void testContactDeleting() {

    app.getContactHelper().editContact();
    app.getNavigationHelper().delete();
    app.getNavigationHelper().goToHome();

  }
}
