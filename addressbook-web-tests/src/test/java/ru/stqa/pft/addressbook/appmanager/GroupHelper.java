package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.tests.GroupData;

/**
 * Created by Юрий on 28.02.2016.
 */
public class GroupHelper extends HelperBase {

  public GroupHelper(WebDriver driver) {
    super(driver);
  }

  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getGroupName());
    type(By.name("group_header"), groupData.getGroupHeader());
    type(By.name("group_footer"), groupData.getGroupFooter());
  }

  public void createNewGroup() {
    click(By.linkText("groups"));
    click(By.name("new"));
  }

}
