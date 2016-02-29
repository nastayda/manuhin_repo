package ru.stqa.pft.addressbook414.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook414.model.TestBase;

/**
 * Created by Юрий on 28.02.2016.
 */
public class GroupDeletingTests extends TestBase {

  @Test
  public void testGroupDeleting() {

    app.getGroupHelper().selectGroups();
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().deleteGroup();
    app.getNavigationHelper().goToGroup();

  }
}
