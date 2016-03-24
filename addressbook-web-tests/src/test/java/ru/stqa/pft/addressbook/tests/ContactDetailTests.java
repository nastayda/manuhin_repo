package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by manuhin on 23.03.2016.
 */
public class ContactDetailTests extends TestBase {

  @Test
  public void testContactDetail() {
    app.goTo().contacts();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    String contactInfoFromDetails = app.contact().infoFromDetails(contact);

    assertThat(mergeFromEditForm(contactInfoFromEditForm), equalTo(contactInfoFromDetails));
  }

  private String mergeFromEditForm(ContactData contact) {
    String homePhone = contact.getHomePhone();
    String mobilePhone = contact.getMobilePhone();
    String workPhone = contact.getWorkPhone();
    if (homePhone != "") {
      homePhone = "H: " + homePhone;
    }
    if (mobilePhone != "") {
      mobilePhone = "M: " + mobilePhone;
    }
    if (workPhone != "") {
      workPhone = "W: " + workPhone;
    }
    return asList(contact.getFio(), contact.getAddress(), "",
            homePhone, mobilePhone, workPhone, "",
            contact.getEmail(), contact.getEmail2(), contact.getEmail3(), "\n\n")
            .stream().collect(Collectors.joining("\n"));
  }

}
