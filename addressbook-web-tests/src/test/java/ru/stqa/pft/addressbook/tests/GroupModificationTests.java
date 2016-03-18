package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

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
    List<GroupData> before = app.group().list();
    int index = before.size() - 1;
    app.group().select(index);
    app.group().modify();

    GroupData group = new GroupData().withId(before.get(index).getId()).withName("111").withFooter("222").withHeader("333");
    app.group().fillForm(group);
    app.group().submitModification();
    app.group().toGroups();
    List<GroupData> after = app.group().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(group);

    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before);

  }
}
