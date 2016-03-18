package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void preConditions() {
        app.goTo().groups();
        if (app.group().list().size() == 0) {
            app.group().create(new GroupData().withName("11").withFooter("22").withHeader("33"));
        }
    }

    @Test
    public void testGroupDeletion() {
        List<GroupData> before = app.group().list();
        int index = before.size() - 1;
        app.group().select(index);
        app.group().delete();
        app.group().toGroups();
        List<GroupData> after = app.group().list();
        Assert.assertEquals(after.size(), index);

        before.remove(index);
        Assert.assertEquals(after, before);
    }



}
