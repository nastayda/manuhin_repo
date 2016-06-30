package ru.stqa.pft.gge.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.gge.model.GeneratorData;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by manuhin on 21.04.2016.
 */
public class GeneratorHelperMGE extends HelperBase {
  public GeneratorHelperMGE(WebDriver wd, Properties properties) {
    super(wd);
    this.properties = properties;
    //wd.manage().window().maximize();
  }

  private Properties properties;

  public List<GeneratorData> GenParam(int i, boolean isProdServer, String loginUser, String baseUrl) throws InterruptedException {
    List<WebElement> mainMenu;
    List<WebElement> dropDown;
    List <GeneratorData> listRazd = new ArrayList<>();

    String xpathRazdelStart = properties.getProperty("xpath.razdel.start");
    String xpathRazdelEnd = properties.getProperty("xpath.razdel.end");

    String xpathMenuStart = properties.getProperty("xpath.menu.start");
    String xpathMenuEnd = properties.getProperty("xpath.menu.end");

    String xpathSubmenuStart = properties.getProperty("xpath.submenu.start");
    String xpathSubmenuIn = properties.getProperty("xpath.submenu.in");
    String xpathSubmenuEnd = properties.getProperty("xpath.submenu.end");

    String xpathSubmenuDropEnd = properties.getProperty("xpath.submenu.drop.end");

    //String loginUser = properties.getProperty("web.adminLogin");
    //String baseUrl = properties.getProperty("web.baseUrl");

    //Раздел
    for (int r = 1; r <= i; r++){
      waitLoadPage(isProdServer);
      //Thread.sleep(3000);
      String xpath1 = xpathRazdelStart + "["+ r +"]" + xpathRazdelEnd;
      click(By.xpath(xpath1));
      String nameRazd = wd.findElement(By.xpath(xpath1)).getText();
      mainMenu =  wd.findElements(By.xpath(xpathMenuStart + xpathMenuEnd));
      int amountMenu = mainMenu.size();
      //Меню
      for(int m = 1; m <= amountMenu; m++) {
        waitLoadPage(isProdServer);
        Thread.sleep(1000);
        String xpath2 = xpathMenuStart + "["+ m +"]" + xpathMenuEnd;
        String nameMenu = wd.findElement(By.xpath(xpath2)).getText();
        String classMenu = wd.findElement(By.xpath(xpathMenuStart + "["+ m +"]"))
                .getAttribute("class");
        //Витрина
        if (classMenu.equals("link dropDown") || classMenu.equals("link dropDown active")){
          dropDown = wd.findElements(By.xpath(xpathMenuStart + "[" + m +"]" + xpathSubmenuDropEnd));
          int amountSubMenu = dropDown.size();
          waitLoadPage(isProdServer);
          Thread.sleep(1000);
          click(By.xpath(xpath2));
          for(int s = 1; s <= amountSubMenu; s++){
            String xpath3 = xpathSubmenuStart + "[" + m + "]" + xpathSubmenuIn + "[" + s + "]" + xpathSubmenuEnd;
            String nameSub = wd.findElement(By.xpath(xpath3)).getText();
            GeneratorData generatorData = new GeneratorData()
                    .withRazdelName(nameRazd).withRazdelXpath(xpath1)
                    .withMenuName(nameMenu).withMenuXpath(xpath2)
                    .withVitrinaName(nameSub).withVitrinaXpath(xpath3)
                    .withLoginUser(loginUser).withBaseUrl(baseUrl);
            listRazd.add(generatorData);
          }
        } else {
          GeneratorData generatorData = new GeneratorData()
                  .withRazdelName(nameRazd).withRazdelXpath(xpath1)
                  .withMenuName(nameMenu).withMenuXpath(xpath2)
                  .withVitrinaName(nameMenu).withVitrinaXpath(xpath2)
                  .withLoginUser(loginUser).withBaseUrl(baseUrl);
          listRazd.add(generatorData);
        }
      }
    }
    return listRazd;
  }

  public List<GeneratorData> GenParam2(int i, boolean isProdServer, String loginUser, String baseUrl) throws InterruptedException {
    wd.manage().window().maximize();

    List<WebElement> mainMenu;
    List<WebElement> dropDown;
    List <GeneratorData> listRazd = new ArrayList<>();

    String xpathRazdelStart = "(//ul[@id=\"firstMenu\"]//a)";
    String xpathRazdelEnd = "";

    String xpathMenuStart = "(//ul[@class=\"secondMenu\"]/li/a)";
    String xpathMenuEnd = "";

    String xpathSubmenuStart = "((//ul[@class=\"secondMenu\"]/li/a)";
    String xpathSubmenuIn = "/..//ul/li/a)";
    String xpathSubmenuEnd = "";

    String xpathSubmenuDropEnd = "/..//ul/li/a)";

    //String loginUser = properties.getProperty("web.adminLogin");
    //String baseUrl = properties.getProperty("web.baseUrl");

    //Раздел
    for (int r = 1; r <= i; r++){
      waitLoadPage(isProdServer);
      //Thread.sleep(3000);
      String xpath1 = xpathRazdelStart + "["+ r +"]" + xpathRazdelEnd;
      click(By.xpath(xpath1));
      String nameRazd = wd.findElement(By.xpath(xpath1)).getText();
      mainMenu =  wd.findElements(By.xpath(xpathMenuStart + xpathMenuEnd));
      int amountMenu = mainMenu.size();
      //Меню
      for(int m = 1; m <= amountMenu; m++) {
        waitLoadPage(isProdServer);
        Thread.sleep(1000);
        String xpath2 = xpathMenuStart + "["+ m +"]" + xpathMenuEnd;
        String nameMenu = wd.findElement(By.xpath(xpath2)).getText();
        String classMenu = wd.findElement(By.xpath(xpathMenuStart + "["+ m +"]/.."))
                .getAttribute("class");
        //Витрина
        if (classMenu.equals("link dropDown") || classMenu.equals("link dropDown active")){
          dropDown = wd.findElements(By.xpath(xpathSubmenuStart + "[" + m +"]" + xpathSubmenuDropEnd));
          int amountSubMenu = dropDown.size();
          waitLoadPage(isProdServer);
          Thread.sleep(1000);
          click(By.xpath(xpath2));
          for(int s = 1; s <= amountSubMenu; s++){
            String xpath3 = xpathSubmenuStart + "[" + m + "]" + xpathSubmenuIn + "[" + s + "]" + xpathSubmenuEnd;
            String nameSub = wd.findElement(By.xpath(xpath3)).getText();
            String urlVitrina = wd.findElement(By.xpath(xpath3)).getAttribute("href");
            GeneratorData generatorData = new GeneratorData()
                    .withRazdelName(nameRazd).withRazdelXpath(xpath1)
                    .withMenuName(nameMenu).withMenuXpath(xpath2)
                    .withVitrinaName(nameSub).withVitrinaXpath(xpath3)
                    .withLoginUser(loginUser).withBaseUrl(urlVitrina);
            listRazd.add(generatorData);
          }
        } else {
          String urlVitrina = wd.findElement(By.xpath(xpath2)).getAttribute("href");
          GeneratorData generatorData = new GeneratorData()
                  .withRazdelName(nameRazd).withRazdelXpath(xpath1)
                  .withMenuName(nameMenu).withMenuXpath(xpath2)
                  .withVitrinaName(nameMenu).withVitrinaXpath(xpath2)
                  .withLoginUser(loginUser).withBaseUrl(urlVitrina);
          listRazd.add(generatorData);
        }
      }
    }
    return listRazd;
  }
}
