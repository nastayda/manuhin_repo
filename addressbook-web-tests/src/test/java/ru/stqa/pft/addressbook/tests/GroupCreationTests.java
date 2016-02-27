package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.TestBase;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreationTests() throws Exception {
    app.createNewGroup();
    app.fillGroupForm(new GroupData("test1", "test2", "test3"));
    app.submit();
    app.goToGroup();
  }

}
