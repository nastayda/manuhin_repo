package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

  @Test
    public void testGroupCreation() {
      app.getNavigationHelper().goToGroup();
      app.getGroupHelper().initGroupCreation();
      app.getGroupHelper().fillGroupForm(new GroupData("11", "22", "33"));
      app.getGroupHelper().submitGroupCreation();
      app.getGroupHelper().returnToGroupPage();
  }

}
