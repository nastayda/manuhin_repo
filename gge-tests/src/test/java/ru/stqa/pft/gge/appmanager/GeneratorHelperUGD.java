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
public class GeneratorHelperUGD extends HelperBase {
  public GeneratorHelperUGD(WebDriver wd, Properties properties) {
    super(wd);
    this.properties = properties;
    //wd.manage().window().maximize();
  }

  private Properties properties;

  public List<GeneratorData> GenParamUGD(int i, boolean isProdServer, String loginUser, String baseUrl) throws InterruptedException {
    List<WebElement> mainMenu;
    List<WebElement> dropDown;
    List <GeneratorData> listRazd = new ArrayList<>();

    String xpathRazdelStart = "(//*[@id='PortalTree']/li)";
    String xpathRazdelEnd = "";

    String xpathMenuStart = "(//*[@id='PortalTree']/li";
    String xpathMenuIn = "/ul/li/a)";
    String xpathMenuEnd = "";

    String xpathSubmenuStart = "(//*[@id='PortalTree']/li";
    String xpathSubmenuIn1 = "/ul/li";
    String xpathSubmenuIn2 = "/a/../ul/li/a)";
    String xpathSubmenuEnd = "";

    //Раздел
    for (int r = 1; r <= i; r++){
      waitLoadPage(isProdServer);
      //Thread.sleep(3000);
      String xpath1 = xpathRazdelStart + "["+ r +"]" + xpathRazdelEnd;
//      click(By.xpath(xpath1));
      String nameRazd = wd.findElement(By.xpath(xpath1)).getText();
      mainMenu =  wd.findElements(By.xpath(xpathMenuStart + xpathMenuEnd));
      int amountMenu = mainMenu.size();
      //Меню
      for(int m = 1; m <= amountMenu; m++) {
        waitLoadPage(isProdServer);
        Thread.sleep(1000);
        String xpath2 = xpathMenuStart + "[" + r + "]" + xpathMenuIn + "["+ m +"]" + xpathMenuEnd;
        String nameMenu = wd.findElement(By.xpath(xpath2)).getText();
        //Витрина
//        int amountSubMenu = dropDown.size();
//        for(int s = 1; s <= amountSubMenu; s++){
//          String xpath3 = xpathSubmenuStart + "[" + m + "]" + xpathSubmenuIn + "[" + s + "]" + xpathSubmenuEnd;
//          String nameSub = wd.findElement(By.xpath(xpath3)).getText();
//          GeneratorData generatorData = new GeneratorData()
//                  .withRazdelName(nameRazd).withRazdelXpath(xpath1)
//                  .withMenuName(nameMenu).withMenuXpath(xpath2)
//                  .withVitrinaName(nameSub).withVitrinaXpath(xpath3)
//                  .withLoginUser(loginUser).withBaseUrl(baseUrl);
//          listRazd.add(generatorData);
//        }
      }
    }
    return listRazd;
  }
}
