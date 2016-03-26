package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups() {
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {"test 1'", "header 1", "footer 1"});
    list.add(new Object[] {"test 2", "header 2", "footer 2"});
    list.add(new Object[] {"test 3", "header 3", "footer 3"});
    return list.iterator();
  }

  @Test(dataProvider = "validGroups")
    public void testGroupCreation(String name, String header, String footer) {
      GroupData group = new GroupData().withName(name).withFooter(footer).withHeader(header);
      app.goTo().groups();
      Groups before = app.group().all();
      app.group().create(group);
      assertThat(app.group().count(), equalTo(before.size() + 1));

      Groups after = app.group().all();

      assertThat(after, equalTo(
              before.withAdded(group.withId(after.stream().mapToInt((g) -> (g.getId())).max().getAsInt()))));
  }

  @Test(enabled = false)
  public void testBadGroupCreation() {
    app.goTo().groups();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("11'").withFooter("22").withHeader("33");
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size()));

    Groups after = app.group().all();

    assertThat(after, equalTo(before));
  }
}
