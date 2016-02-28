package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.tests.GroupData;

/**
 * Created by Юрий on 28.02.2016.
 */
public class GroupHelper {
  private WebDriver driver;

  public GroupHelper(WebDriver driver) {
    this.driver = driver;
  }

  public void fillGroupForm(GroupData groupData) {
    driver.findElement(By.name("group_name")).clear();
    driver.findElement(By.name("group_name")).sendKeys(groupData.getGroupName());
    driver.findElement(By.name("group_header")).clear();
    driver.findElement(By.name("group_header")).sendKeys(groupData.getGroupHeader());
    driver.findElement(By.name("group_footer")).clear();
    driver.findElement(By.name("group_footer")).sendKeys(groupData.getGroupFooter());
  }

  public void createNewGroup() {
    driver.findElement(By.linkText("groups")).click();
    driver.findElement(By.name("new")).click();
  }
}
