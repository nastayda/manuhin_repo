package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;

public class GroupCreationTests extends TestBase {

  @Test
    public void testGroupCreation() {
      app.goToGroup();
      app.initGroupCreation();
      app.fillGroupForm(new GroupData("11", "22", "33"));
      app.submitGroupCreation();
      app.returnToGroupPage();
  }

}
