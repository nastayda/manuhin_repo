package ru.stqa.pft.addressbook414.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook414.model.TestBase;

/**
 * Created by Юрий on 28.02.2016.
 */
public class GroupModificationTests extends TestBase {

  @Test
  public void testGroupModification() {
    app.getGroupHelper().selectGroups();
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().modifyGroup();
    app.getGroupHelper().fillGroupForm(new GroupData("test11", "test21", "test31"));
    app.getNavigationHelper().update();
    app.getNavigationHelper().goToGroup();

  }
}
