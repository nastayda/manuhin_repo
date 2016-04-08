package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Юрий on 04.03.2016.
 */
public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class);

  protected static final ApplicationManager app =
          new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX)) ;

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
    public void tearDown() {
      app.stop();
  }

  public ApplicationManager getApp() {
    return app;
  }

  @BeforeMethod
  public void logTestStart(Method m, Object[] p) {
    logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m, Object[] p) {
    logger.info("Stop test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  public void verifyContactsInGroupUI(GroupData group) {
    if (Boolean.getBoolean("verifyUI")) {
      Contacts contactsInGroupUI = app.contact().all();
      Contacts contactsInGroupBD = (Contacts) group.getContacts();
      assertThat(contactsInGroupUI, equalTo(contactsInGroupBD
              .stream().map((c) -> new ContactData().withId(c.getId())
                      .withFirstname(c.getFirstname()).withLastname(c.getLastname())
                      .withAddress(c.getAddress()).withEmail(c.getEmail()))
                      .collect(Collectors.toSet())));
    }
  }

  public void verifyGroupListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().all();
      assertThat(uiGroups, equalTo(dbGroups
              .stream().map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyContactListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Contacts dbContacts = app.db().contacts();
      Contacts uiContacts = app.contact().all();
      assertThat(uiContacts, equalTo(dbContacts
              .stream().map((c) -> new ContactData().withId(c.getId()).withFirstname(c.getFirstname())
              .withLastname(c.getLastname()).withMiddlename(c.getMiddlename()).withAddress(c.getAddress())
              .withMobilePhone(c.getMobilePhone()).withHomePhone(c.getHomePhone()).withWorkPhone(c.getWorkPhone())
              .withEmail(c.getEmail()).withEmail2(c.getEmail2()).withEmail3(c.getEmail3()))
              .collect(Collectors.toSet())));
    }
  }
}
