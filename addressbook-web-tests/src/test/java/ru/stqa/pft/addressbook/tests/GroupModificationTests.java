package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

/**
 * Created by Юрий on 05.03.2016.
 */
public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void preConditions() {
    app.goTo().groups();
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData().withName("11").withFooter("22").withHeader("33"));
    }
  }

  @Test
  public void testGroupModification() {
    Set<GroupData> before = app.group().all();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData().
            withId(modifiedGroup.getId()).withName("111").withFooter("222").withHeader("333");

    app.group().modify(group);
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedGroup);
    before.add(group);

    Assert.assertEquals(after, before);

  }

}
