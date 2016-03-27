package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void preConditions() {
    app.goTo().groups();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("11").withFooter("22").withHeader("33"));
    }
  }

  @Test(enabled = true)
  public void testGroupDeletion() {
    Groups before = app.group().all();
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);
    assertThat(app.group().count(), equalTo(before.size() - 1));

    Groups after = app.group().all();

    assertThat(after, equalTo(before.without(deletedGroup)));
  }


}
