package ru.stqa.pft.gge.tests;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

/**
 * Created by manuhin on 25.05.2016.
 */
public class SaveAllToHTMLFiles {

  private WebDriver driver;

  @Test
  public void testSaveAllToHTMLFiles() {
    driver = new FirefoxDriver();
    driver.get("http://www.javascripttoolbox.com/lib/contextmenu/");
    driver.manage().timeouts().implicitlyWait(10, java.util.concurrent.TimeUnit.SECONDS);
    Actions actions = new Actions(driver);

// находим элемент, для которого определено контекстное меню
    WebElement trigger = driver.findElement(By.cssSelector("div.menu-default"));
// кликаем правой кнопкой мыши
    actions.contextClick(trigger).perform();

// ищем появившееся контекстное меню, это обычный элемент DOM
    //List<WebElement> menu = driver.findElements(By.cssSelector("body > table > tbody > tr > td > div.context-menu")).get(0);
// получаем список пунктов меню
    //List<WebElement> menuItems = menu.findElements(By.cssSelector("div.context-menu-item"));
// кликаем первый пункт списка, который вызывает появление alert'а
   // menuItems.get(0).click();

// обрабатываем появившийся alert
    Alert alert = driver.switchTo().alert();
    System.out.println(alert.getText());
    alert.accept();
  }

}
