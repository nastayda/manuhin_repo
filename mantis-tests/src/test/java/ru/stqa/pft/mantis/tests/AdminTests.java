package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.UserMantis;
import java.io.IOException;

/**
 * Created by manuhin on 11.04.2016.
 */
public class AdminTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangeUserPassword() throws IOException {

        app.admin().adminAutorization();
        app.admin().chooseUser();
        UserMantis userMantis = app.admin().getUser();
        app.admin().resetPassword();


      /**String email = String.format("%s@localhost.localdomain", now);
       app.registration().start(user, email);
       List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
       String confirmationLink = findConfirmationLink(mailMessages, email);
       app.registration().finish(confirmationLink, password);
       assertTrue(app.newSession().login(user, password));
       */
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
