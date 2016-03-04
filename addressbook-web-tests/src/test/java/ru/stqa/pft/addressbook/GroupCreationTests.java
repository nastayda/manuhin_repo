package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase {

  @Test
    public void testGroupCreation() {
      goToGroup();
      initGroupCreation();
      fillGroupForm(new GroupData("11", "22", "33"));
      submitGroupCreation();
      returnToGroupPage();
  }

}
