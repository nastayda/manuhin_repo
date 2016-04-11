package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.UserMantis;

/**
 * Created by manuhin on 11.04.2016.
 */
public class AdminHelper extends HelperBase {

    public AdminHelper(ApplicationManager app) {
        super(app);
    }

    public void adminAutorization() {
        String adminLogin = app.getProperty("web.adminLogin");
        String adminPassword = app.getProperty("web.adminPassword");
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.name("username"), adminLogin);
        type(By.name("password"), adminPassword);
        click(By.cssSelector("input[value='Login']"));
    }

    public UserMantis chooseUser() {
        click(By.xpath("html/body/div[2]/p/span[1]/a"));
        click(By.xpath("html/body/table[3]/tbody/tr[4]/td[1]/a"));
        String email = wd.findElement(By.name("email")).getAttribute("value");
        click(By.cssSelector("input[value='Reset Password']"));
        return new UserMantis().withEmail(email);
    }

}
