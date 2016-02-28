package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getGroupHelper().selectGroups();
    app.getGroupHelper().createNewGroup();
    app.getGroupHelper().fillGroupForm(new GroupData("test1", "test2", "test3"));
    app.getNavigationHelper().submit();
    app.getNavigationHelper().goToGroup();
  }

}
