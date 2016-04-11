package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserMantis;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

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
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 30000);
        long now = System.currentTimeMillis();
        String newPassword = "password" + now;
        String confirmationLink = app.admin().findConfirmationLink(mailMessages, userMantis.getEmail());
        app.registration().finish(confirmationLink, newPassword);
        assertTrue(app.newSession().login(userMantis.getUsername(), newPassword));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
