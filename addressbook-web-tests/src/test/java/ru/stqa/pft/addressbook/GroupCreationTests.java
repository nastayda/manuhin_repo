package ru.stqa.pft.addressbook;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase {
  
  @Test
  public void testGroupCreationTests() throws Exception {
    createNewGroup();
    fillGroupForm(new GroupData("test1", "test2", "test3"));
    submit();
    goToGroup();
  }

}
