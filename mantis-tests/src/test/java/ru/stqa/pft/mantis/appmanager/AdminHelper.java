package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.mantis.model.UserMantis;

import java.util.List;

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

    public void chooseUser() {
        String adminLogin = app.getProperty("web.adminLogin");
        click(By.xpath("html/body/div[2]/p/span[1]/a"));
        List<WebElement> users = wd.findElements(By.xpath("//*[@class=\"row-2\" or @class=\"row-1\"]//a"));
        WebElement chosenUser = users.stream().filter((u) -> !(u.getText().equals(adminLogin))).findFirst().get();
        chosenUser.click();
    }

  public UserMantis getUser() {
    String name = wd.findElement(By.name("username")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    return new UserMantis().withName(name).withEmail(email);
  }

  public void resetPassword() {
    click(By.cssSelector("input[value='Reset Password']"));
  }

}
