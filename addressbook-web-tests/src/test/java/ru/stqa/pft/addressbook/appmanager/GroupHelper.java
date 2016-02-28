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
    type(By.name("group_name"), groupData.getGroupName());
    type(By.name("group_header"), groupData.getGroupHeader());
    type(By.name("group_footer"), groupData.getGroupFooter());
  }

  private void type(By locator, String text) {
    driver.findElement(locator).clear();
    driver.findElement(locator).sendKeys(text);
  }

  public void createNewGroup() {
    click(By.linkText("groups"));
    click(By.name("new"));
  }

  private void click(By locator) {
    driver.findElement(locator).click();
  }
}
